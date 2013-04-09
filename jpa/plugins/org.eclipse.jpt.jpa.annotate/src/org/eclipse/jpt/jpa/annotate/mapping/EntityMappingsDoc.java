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

import org.eclipse.core.resources.IProject;

/**
 * 
 * An EntityMappingsDoc stores all the mapping information needed to convert a java 
 * class into an jpa entity
 */
public class EntityMappingsDoc
{
	// fully qualified class name
	private String _className;
	private IProject _project;
	private TableAnnotationAttributes _tableAttrs;
	private EntityPropertyElem[] _entityProperties;
	// flag to indicate whether annotations should be added to property
	// getters or fields.
	private boolean _isPropertyAccess;
	
	public EntityMappingsDoc(String className, IProject proj,
			TableAnnotationAttributes tableAttrs,
			EntityPropertyElem[] entityProps, boolean isPropertyAccess)
	{
		_className = className;
		_project = proj;
		_tableAttrs = tableAttrs;
		_entityProperties = entityProps;
		_isPropertyAccess = isPropertyAccess;		
	}
	
	public void setClassName(String className)
	{
		_className = className;
	}
	public String getClassName()
	{
		return _className;
	}
	public void setProject(IProject project)
	{
		_project = project;
	}
	public IProject getProject()
	{
		return _project;
	}
	public void setTableAttrs(TableAnnotationAttributes tableAttr)
	{
		_tableAttrs = tableAttr;
	}
	public TableAnnotationAttributes getTableAttrs()
	{
		return _tableAttrs;
	}
	public void setEntityProperties(EntityPropertyElem[] entityProperties)
	{
		_entityProperties = entityProperties;
	}
	public EntityPropertyElem[] getEntityProperties()
	{
		return _entityProperties;
	}
	public void setPropertyAccess(boolean isPropertyAccess)
	{
		_isPropertyAccess = isPropertyAccess;
	}
	public boolean isPropertyAccess()
	{
		return _isPropertyAccess;
	}
	
	public EntityPropertyElem getProperty(String propName)
	{
		EntityPropertyElem ret = null;
		for (EntityPropertyElem propElem : _entityProperties)
		{
			if (propElem.getPropertyName().equals(propName))
			{
				ret = propElem;
				break;
			}
		}
		return ret;
	}
	
}
