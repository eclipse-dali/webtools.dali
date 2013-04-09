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

public class JoinTableAttributes extends TableAnnotationAttributes
{
	private List<ColumnAttributes> joinColumns;
	private List<ColumnAttributes> inverseJoinColumns;
	
	public JoinTableAttributes()
	{
		super();
		joinColumns = new ArrayList<ColumnAttributes>();
		inverseJoinColumns = new ArrayList<ColumnAttributes>();		
	}
	
	public JoinTableAttributes(JoinTableAttributes another)
	{
		super(another);
		joinColumns = new ArrayList<ColumnAttributes>();
		inverseJoinColumns = new ArrayList<ColumnAttributes>();		
		joinColumns.addAll(another.joinColumns);
		inverseJoinColumns.addAll(another.inverseJoinColumns);
	}
	
	public JoinTableAttributes(String tableName, String catalog, String schema)
	{
		super(tableName, catalog, schema);
		joinColumns = new ArrayList<ColumnAttributes>();
		inverseJoinColumns = new ArrayList<ColumnAttributes>();
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
		for (ColumnAttributes col : joinColumns)
			col.dispose();
		joinColumns.clear();
	}
	
	public void setJoinColumns(List<ColumnAttributes> joinColumns)
	{
		removeAllJoinColumns();
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
		for (ColumnAttributes col : inverseJoinColumns)
			col.dispose();
		this.inverseJoinColumns.clear();
	}
	
	public void setInverseJoinColumns(List<ColumnAttributes> joinColumns)
	{
		removeAllInverseJoinColumns();
		if (joinColumns != null)
			this.inverseJoinColumns.addAll(joinColumns);
	}
	
	public void dispose()
	{
		super.dispose();
		removeAllJoinColumns();
		removeAllInverseJoinColumns();
	}
}
