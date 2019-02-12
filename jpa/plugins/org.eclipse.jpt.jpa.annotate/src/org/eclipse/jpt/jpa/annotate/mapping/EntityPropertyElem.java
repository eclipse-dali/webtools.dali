/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.mapping;

import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;


public class EntityPropertyElem
{
	private String _fqClassName;
	private Table _dbTable;
	private String _propertyName;
	private String _propertyType;
	private String _tagName;
	private boolean _unmapped;
	private Column _column;
	private ColumnAttributes _columnAnnotationAttrs;
	private AnnotationAttributes _annotationAttrs;
	
	public EntityPropertyElem(String fqClassName, Table table) 
	{
		_fqClassName = fqClassName;
		_dbTable = table; 
		_columnAnnotationAttrs = null;
		_annotationAttrs = new AnnotationAttributes();
	}
	
	public EntityPropertyElem(String fqClassName, Table table, String propName, String propType)
	{
		this(fqClassName, table);
		_propertyName = propName;
		_propertyType = propType;
	}
	
	// Copy constructor
	public EntityPropertyElem(EntityPropertyElem another)
	{
		this(another.getClassName(), another.getDBTable());
		_propertyName = another._propertyName;
		_propertyType = another._propertyType;
		_tagName = another._tagName;
		_unmapped = another._unmapped;
		_column = another._column;
		if (another._columnAnnotationAttrs != null)
			_columnAnnotationAttrs = new ColumnAttributes(another._columnAnnotationAttrs);
		if (another._annotationAttrs != null)
			_annotationAttrs = new AnnotationAttributes(another._annotationAttrs);
	}
	
	public String getClassName()
	{
		return this._fqClassName;
	}
	
	public Table getDBTable()
	{
		return this._dbTable;
	}
	
	public String getPropertyName()
	{
		return _propertyName;
	}
	
	public void setPropertyName(String propName)
	{
		_propertyName = propName;
	}

	public String getPropertyType()
	{
		return _propertyType;
	}
	
	public void setPropertyType(String propType)
	{
		_propertyType = propType;
	}

	public String getTagName()
	{
		return _tagName;
	}
	
	public void setTagName(String tagName)
	{
		_tagName = tagName;
	}
	
	public boolean isUnmapped()
	{
		return _unmapped;
	}
	
	public void setUnmapped(boolean unmapped)
	{
		_unmapped = unmapped;
	}
	
	public void setColumnAnnotationAttrs(ColumnAttributes columnAnnotationAttrs)
	{
		this._columnAnnotationAttrs = columnAnnotationAttrs;
	}

	public ColumnAttributes getColumnAnnotationAttrs()
	{
		return _columnAnnotationAttrs;
	}

	public void setAnnotationAttr(AnnotationAttribute attr)
	{
		_annotationAttrs.setAnnotationAttribute(attr);
	}
	public AnnotationAttributes getAnnotationAttrs()
	{
		return _annotationAttrs;
	}
	
	public AnnotationAttribute[] getAnnotationAttrs(String[] attrNames)
	{
		return _annotationAttrs.getAnnotationAttributes(attrNames);
	}

	public AnnotationAttribute getAnnotationAttribute(String attrName)
	{
		return _annotationAttrs.getAnnotationAttribute(attrName);
	}
	
	public void setDBColumn(Column column)
	{
		_column = column;
	}

	public Column getDBColumn()
	{
		return _column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_fqClassName == null) ? 0 : _fqClassName.hashCode());
		result = prime * result
				+ ((_propertyName == null) ? 0 : _propertyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EntityPropertyElem))
			return false;
		EntityPropertyElem other = (EntityPropertyElem) obj;
		if (_fqClassName == null) {
			if (other._fqClassName != null)
				return false;
		} else if (!_fqClassName.equals(other._fqClassName))
			return false;
		if (_propertyName == null) {
			if (other._propertyName != null)
				return false;
		} else if (!_propertyName.equals(other._propertyName))
			return false;
		return true;
	}
	
}
