/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;

public class GenericOrmPrimaryKeyJoinColumn extends AbstractOrmNamedColumn<XmlPrimaryKeyJoinColumn>
	implements OrmPrimaryKeyJoinColumn
{
	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected XmlPrimaryKeyJoinColumn primaryKeyJoinColumn;
	
	public GenericOrmPrimaryKeyJoinColumn(OrmJpaContextNode parent, OrmBaseJoinColumn.Owner owner) {
		super(parent, owner);
	}

	public void initializeFrom(PrimaryKeyJoinColumn oldPkJoinColumn) {
		super.initializeFrom(oldPkJoinColumn);
		setSpecifiedReferencedColumnName(oldPkJoinColumn.getSpecifiedReferencedColumnName());
	}
	
	@Override
	protected XmlPrimaryKeyJoinColumn columnResource() {
		return this.primaryKeyJoinColumn;
	}
	
	@Override
	protected void addColumnResource() {
		//primaryKeyJoinColumns are part of a collection, the pk-join-column element will be removed/added
		//when the XmlPrimaryKeyJoinColumn is removed/added to the XmlEntity collection
	}
	
	@Override
	protected void removeColumnResource() {
		//primaryKeyJoinColumns are part of a collection, the pk-join-column element will be removed/added
		//when the XmlPrimaryKeyJoinColumn is removed/added to the XmlEntity collection
	}
	
	public String getReferencedColumnName() {
		return (this.getSpecifiedReferencedColumnName() == null) ? getDefaultReferencedColumnName() : this.getSpecifiedReferencedColumnName();
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		columnResource().setReferencedColumnName(newSpecifiedReferencedColumnName);
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}
	
	protected void setSpecifiedReferencedColumnName_(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}

	@Override
	public OrmBaseJoinColumn.Owner getOwner() {
		return (OrmBaseJoinColumn.Owner) this.owner;
	}

	public Column dbReferencedColumn() {
		Table table = this.dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(this.getReferencedColumnName());
	}

	public Table dbReferencedColumnTable() {
		return getOwner().dbReferencedColumnTable();
	}

	@Override
	protected String tableName() {
		return this.getOwner().getTypeMapping().getTableName();
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public TextRange referencedColumnNameTextRange() {
		if (columnResource() != null) {
			TextRange textRange = columnResource().referencedColumnNameTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return getOwner().validationTextRange();
	}

		
	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}
	
	@Override
	public void initialize(XmlPrimaryKeyJoinColumn column) {
		this.primaryKeyJoinColumn = column;
		super.initialize(column);
		this.specifiedReferencedColumnName = specifiedReferencedColumnName(column);
		this.defaultReferencedColumnName = defaultReferencedColumnName();
	}
	
	@Override
	public void update(XmlPrimaryKeyJoinColumn column) {
		this.primaryKeyJoinColumn = column;
		super.update(column);
		this.setSpecifiedReferencedColumnName_(specifiedReferencedColumnName(column));
		this.setDefaultReferencedColumnName(defaultReferencedColumnName());
	}
	
	protected String specifiedReferencedColumnName(XmlPrimaryKeyJoinColumn column) {
		return column == null ? null : column.getReferencedColumnName();
	}
	
	//TODO not correct when we start supporting primaryKeyJoinColumns in 1-1 mappings
	protected String defaultReferencedColumnName() {
		return defaultName();
	}
}
