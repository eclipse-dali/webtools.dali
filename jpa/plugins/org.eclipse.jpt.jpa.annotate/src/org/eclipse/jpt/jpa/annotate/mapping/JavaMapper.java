/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.jpa.annotate.internal.plugin.JptJpaAnnotatePlugin;
import org.eclipse.jpt.jpa.annotate.util.AnnotateMappingUtil;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;

public class JavaMapper
{
	private IProject _project;
	private String _fqEntityType;
	private Table _table;
	private String _pkProp;
	private List<EntityPropertyElem> _entityProperties;
	private IType _entityType;
	
	public JavaMapper(IProject project, String fqEntityType, Table table, String pkProp)
	{
		_project = project;
		_fqEntityType = fqEntityType;
		_table = table;
		_pkProp = pkProp;
		_entityProperties = new ArrayList<EntityPropertyElem>();
		try
		{
		 _entityType = AnnotateMappingUtil.getType(_fqEntityType, _project);
		}
		catch (JavaModelException e)
		{
			JptJpaAnnotatePlugin.instance().logError(e);
		}
		
	}
	
	public void map()
	{
		try 
		{
			IField[] fields = _entityType.getFields();
			for (IField field : fields)
			{
				// filter out static/inherited fields
				if (Flags.isStatic(field.getFlags()) || Flags.isSuper(field.getFlags()))
					continue;
				if (_pkProp != null && _pkProp.equals(field.getElementName()))
				{
					EntityPropertyElem entityProp = mapProperty(field, true /* isId */);
					_entityProperties.add(entityProp);
				}
				else
				{
					EntityPropertyElem entityProp = mapField(field);
					_entityProperties.add(entityProp);
				}
			}
		}
		catch (JavaModelException je)
		{
			JptJpaAnnotatePlugin.instance().logError(je);
		}		
	}

	public List<EntityPropertyElem> getEntityProperties()
	{
		return _entityProperties;
	}
	
	private void setColumnAttrs(ColumnAttributes colAttrs, Column col)
	{
		colAttrs.setName(col.getName());
		colAttrs.setNullable(col.isNullable());
		colAttrs.setUnique(col.isPartOfUniqueConstraint());
		if (col.isNumeric())
		{
			if (col.getPrecision() != 0)
				colAttrs.setPrecision(col.getPrecision());
			if (col.getScale() != 0)
				colAttrs.setScale(col.getScale());
		}
		else if (col.isTimeDataType()) 
		{
			if (col.getPrecision() != 0)
				colAttrs.setPrecision(col.getPrecision());
		}
		else if (!col.isDateDataType())
		{
			colAttrs.setLength(col.getLength());
		}	
	}
	
	private EntityPropertyElem mapProperty(IField field, boolean isId)
	{
		try
		{
			String propName = field.getElementName();
			String fqType = AnnotateMappingUtil.getFieldType(field, _entityType);
	
			EntityPropertyElem entityProp;						
			String tagName;
			if (isId)
			{
				tagName = JPA.ID;
				entityProp = new IdEntityPropertyElement(this._fqEntityType, this._table, propName, fqType);				
			}
			else
			{
				tagName = JPA.BASIC;
				entityProp = new BasicEntityPropertyElem(this._fqEntityType, this._table, propName, fqType);
			}
			entityProp.setTagName(tagName);
			Column col = computeDefaultCol(propName);
			if (col != null)
			{
				entityProp.setDBColumn(col);
				ColumnAttributes colAttrs = new ColumnAttributes();
				setColumnAttrs(colAttrs, col);
				entityProp.setColumnAnnotationAttrs(colAttrs);
			}
			return entityProp;
		}
		catch (JavaModelException je)
		{
			JptJpaAnnotatePlugin.instance().logError(je);
		}
		return null;
	}
	
	private EntityPropertyElem mapOneToMany(IField field)
	{
		// TODO find out whether array type is supported
		try
		{
			String type = AnnotateMappingUtil.getFieldType(field, _entityType);
			String componentType = AnnotateMappingUtil.getGenericsComponentTypeName(type);
			if (componentType != null)
			{
				componentType = AnnotateMappingUtil.resolveType(componentType, _entityType);
				if (isSimpleType(componentType))
					return mapProperty(field, false);
			}
			
			EntityRefPropertyElem erpe = new EntityRefPropertyElem(this._fqEntityType, this._table, field.getElementName(), type);
			erpe.setTagName(JPA.ONE_TO_MANY);
			erpe.setRefEntityClassName(componentType);
			if (componentType != null)
			{
				IType refType = AnnotateMappingUtil.getType(componentType, _project);
				if (refType != null)
				{
					Set<String> propNames = AnnotateMappingUtil.getMappedByList(_fqEntityType, refType, false);
					if (propNames.size() == 1)
					{
						AnnotationAttribute attr = new AnnotationAttribute(AnnotationAttributeNames.MAPPEDBY,
								propNames.iterator().next());
						erpe.setAnnotationAttr(attr);
						erpe.setJoinStrategy(JoinStrategy.MAPPED_BY);
					}
				}
			}
			return erpe;
		}
		catch (JavaModelException e)
		{
			JptJpaAnnotatePlugin.instance().logError(e);
			return null;
		}
	}
	
	private EntityPropertyElem mapManyToOne(IField field)
	{
		try 
		{
			String fqType = AnnotateMappingUtil.getFieldType(field, _entityType);
			EntityRefPropertyElem erpe = new EntityRefPropertyElem(this._fqEntityType, this._table, field.getElementName(), fqType);
			erpe.setTagName(JPA.MANY_TO_ONE);
			erpe.setRefEntityClassName(AnnotateMappingUtil.getFieldType(field, _entityType));
			erpe.setJoinStrategy(JoinStrategy.JOIN_COLUMNS);
			return erpe;
		}
		catch (JavaModelException je)
		{
			JptJpaAnnotatePlugin.instance().logError(je);
		}		
		return null;
	}

	private EntityPropertyElem mapField(IField field)
	{
		try
		{
			String type = AnnotateMappingUtil.getFieldType(field, _entityType);
			boolean isSimpleProperty;
			if (AnnotateMappingUtil.isRepeatingType(type, _project))
			{
				if (AnnotateMappingUtil.isArrayType(type) && 
				    AnnotateMappingUtil.stripArrayDimensions(type).equals("byte"))
				{
					isSimpleProperty = true;
				}
				else
				{
					return mapOneToMany(field);
				}
			}
			else
			{
				isSimpleProperty = isSimpleType(type);
			}
			if (isSimpleProperty)
				return mapProperty(field, false);
			else
				return mapManyToOne(field);
		}
		catch (JavaModelException je)
		{
			JptJpaAnnotatePlugin.instance().logError(je);
		}		
		return null;
	}
	
	private Column computeDefaultCol(String propName)
	{
		for( Column col : _table.getColumns() )
		{
			String colName = col.getName();
			if (propName.equalsIgnoreCase(colName) || propName.equalsIgnoreCase(AnnotateMappingUtil.dbNameToJavaName(colName)))
			{
				return col;
			}
		}
		return null;
	}
	
	private boolean isSimpleType(String type)
	{
		boolean isSimpleType = ClassNameTools.isPrimitive(type) || 
			ClassNameTools.isPrimitiveWrapper(type) ||
			type.startsWith("java."); //$NON-NLS-1$
		return isSimpleType;
		
	}
	
}
