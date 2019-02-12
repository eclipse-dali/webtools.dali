/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.annotate.mapping.EntityPropertyElem;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentType;

public class JavaClassMapping implements PersistentType.Config
{
	private IType jdtType;
	private String mappingKey;
	private String dbTable;
	private String primaryKeyProperty;
	private List<EntityPropertyElem> propertyMappings;
	
	public JavaClassMapping(IType itype)
	{
		super();
		this.jdtType = itype;
		this.mappingKey = MappingKeys.ENTITY_TYPE_MAPPING_KEY;
		this.propertyMappings = new ArrayList<EntityPropertyElem>();
	}
	
	public IType getJDTType()
	{
		return this.jdtType;
	}
	
	public String getFullyQualifiedName() 
	{			
		return this.jdtType.getFullyQualifiedName();
	}

	public String getName() 
	{			
		return this.jdtType.getFullyQualifiedName();
	}

	public String getMappingKey() 
	{
		return this.mappingKey;
	}

	protected void setMappingKey(String mappingKey) 
	{
		this.mappingKey = mappingKey;
	}

	public void setDBTable(String dbTable)
	{
		this.dbTable = dbTable;
	}
	
	public String getDBTable()
	{
		return this.dbTable;
	}
	
	public void setPrimaryKeyProperty(String prop)
	{
		this.primaryKeyProperty = prop;
	}
	
	public String getPrimaryKeyProperty()
	{
		return this.primaryKeyProperty;
	}
	
	public List<EntityPropertyElem> getPropertyMappings()
	{
		return this.propertyMappings;
	}
	
	public void setPropertyMappings(List<EntityPropertyElem> propMappings)
	{
		clearPropertyMappings();
		this.propertyMappings.addAll(propMappings);
	}
	
	public void clearPropertyMappings()
	{
		this.propertyMappings.clear();
	}
}
