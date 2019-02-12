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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.db.Table;

public class EntityRefPropertyElem extends EntityPropertyElem
{
	public static final String ASCENDING = "Ascending";
	public static final String DESCENDING = "Descending";
	private String refEntityClassName;
	private Table refTable;
	private JoinTableAttributes joinTable;
	private List<ColumnAttributes> joinColumns;
	private List<ColumnAttributes> inverseJoinColumns;
	private List<ColumnAttributes> pkJoinColumns;
	private List<String> cascades;
	private String mapKey;
	private String orderBy;
	private JoinStrategy joinStrategy;
	
	public EntityRefPropertyElem(String fqClassName, Table table) 
	{
		super(fqClassName, table);
		cascades = new ArrayList<String>();
		joinColumns = new ArrayList<ColumnAttributes>();
		inverseJoinColumns = new ArrayList<ColumnAttributes>();
		pkJoinColumns = new ArrayList<ColumnAttributes>();
	}
	
	public EntityRefPropertyElem(String fqClassName, Table table, String propName, String propType)
	{
		super(fqClassName, table, propName, propType);
		cascades = new ArrayList<String>();
		joinColumns = new ArrayList<ColumnAttributes>();
		inverseJoinColumns = new ArrayList<ColumnAttributes>();
		pkJoinColumns = new ArrayList<ColumnAttributes>();		
	}
	
	// Copy constructor
	public EntityRefPropertyElem(EntityRefPropertyElem another)
	{
		super(another);
		refEntityClassName = another.refEntityClassName;
		refTable = another.refTable;
		mapKey = another.mapKey;
		orderBy = another.orderBy;
		joinStrategy = another.joinStrategy;

		if (another.joinTable != null)
			joinTable = new JoinTableAttributes(another.joinTable);
		cascades = new ArrayList<String>();
		joinColumns = new ArrayList<ColumnAttributes>();
		inverseJoinColumns = new ArrayList<ColumnAttributes>();
		pkJoinColumns = new ArrayList<ColumnAttributes>();
		cascades.addAll(another.cascades);
		joinColumns.addAll(another.joinColumns);
		inverseJoinColumns.addAll(another.inverseJoinColumns);
		pkJoinColumns.addAll(another.pkJoinColumns);
	}
	
	public String getRefEntityClassName()
	{
		return refEntityClassName;
	}
	
	public void setRefEntityClassName(String refClass)
	{
		refEntityClassName = refClass;
	}
	
	public Table getReferencedTable()
	{
		return refTable;
	}
	
	public void setReferencedTable(Table refTable)
	{
		this.refTable = refTable;
	}
	
	public boolean isManyToOne()
	{
		return getTagName().equals(JPA.MANY_TO_ONE);
	}

	public boolean isOneToOne()
	{
		return getTagName().equals(JPA.ONE_TO_ONE);
	}

	public boolean isOneToMany()
	{
		return getTagName().equals(JPA.ONE_TO_MANY);
	}

	public boolean isManyToMany()
	{
		return getTagName().equals(JPA.MANY_TO_MANY);
	}
	
	public boolean isEntityCollection()
	{
		return isOneToMany() || isManyToMany();
	}

	public void setJoinTable(JoinTableAttributes joinTable)
	{
		this.joinTable = joinTable;
	}

	public JoinTableAttributes getJoinTable()
	{
		return joinTable;
	}

	public void removeJoinTable()
	{
		if (joinTable != null)
			joinTable.dispose();
		joinTable = null;
			
	}
	public void removeMappedBy()
	{
		if (getAnnotationAttrs() != null)
		{
			getAnnotationAttrs().removeAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY);
		}

	}
	public void addJoinColumn(ColumnAttributes joinColumn)
	{
		joinColumns.add(joinColumn);
	}
	
	public List<ColumnAttributes> getJoinColumns()
	{
		return joinColumns;
	}
	
	public void removeAllJoinColumns()
	{
		joinColumns.clear();
	}
	
	public void setJoinColumns(List<ColumnAttributes> joinColumns)
	{
		this.joinColumns.clear();
		if (joinColumns != null)
			this.joinColumns.addAll(joinColumns);
	}

	public void addInverseJoinColumn(ColumnAttributes joinColumn)
	{
		inverseJoinColumns.add(joinColumn);
	}
	
	public List<ColumnAttributes> getInverseJoinColumns()
	{
		return inverseJoinColumns;
	}
	
	public void removeAllInverseJoinColumns()
	{
		inverseJoinColumns.clear();
	}
	
	public void setInverseJoinColumns(List<ColumnAttributes> joinColumns)
	{
		this.inverseJoinColumns.clear();
		if (joinColumns != null)
			this.inverseJoinColumns.addAll(joinColumns);
	}
	
	public void addPkJoinColumn(ColumnAttributes joinColumn)
	{
		pkJoinColumns.add(joinColumn);
	}
	
	public List<ColumnAttributes> getPkJoinColumns()
	{
		return pkJoinColumns;
	}
	
	public void removeAllPKJoinColumns()
	{
		pkJoinColumns.clear();
	}
	
	public void setPKJoinColumns(List<ColumnAttributes> joinColumns)
	{
		this.pkJoinColumns.clear();
		if (joinColumns != null)
			this.pkJoinColumns.addAll(joinColumns);
	}

	/**
	 * Convenience method to retrieve "mappedBy" attribute
	 */
	public String getMappedBy()
	{
		String mappedBy = null;
		if (getAnnotationAttrs() != null)
		{
			AnnotationAttribute attr = getAnnotationAttrs().getAnnotationAttribute(AnnotationAttributeNames.MAPPEDBY);
			if (attr != null)
				mappedBy = attr.attrValue;
		}
		return mappedBy;		
	}	

	public void setMappedBy(String mappedBy)
	{
		AnnotationAttribute attr = new AnnotationAttribute(AnnotationAttributeNames.MAPPEDBY, mappedBy);
		setAnnotationAttr(attr);		
	}
	
	public List<String> getAllCascades()
	{
		return cascades;
	}
	
	public void addCascade(String cascade)
	{
		cascades.add(cascade);
	}

	public void removeAllCascades()
	{
		cascades.clear();
	}
	
	public void setCascades(List<String> cascades)
	{
		assert cascades != null;
		this.cascades.clear();
		this.cascades.addAll(cascades);
	}
	
	public void setMapKey(String mapKey)
	{
		this.mapKey = mapKey;
	}

	public String getMapKey()
	{
		return mapKey;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	public String getOrderBy()
	{
		return orderBy;
	}
	
	public JoinStrategy getJoinStrategy()
	{
		return this.joinStrategy;
	}
	
	public void setJoinStrategy(JoinStrategy joinStrategy)
	{
		this.joinStrategy = joinStrategy;
	}
}
