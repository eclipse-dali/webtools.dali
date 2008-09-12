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

	protected XmlPrimaryKeyJoinColumn resourcePkJoinColumn;
	
	public GenericOrmPrimaryKeyJoinColumn(OrmJpaContextNode parent, OrmBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		super(parent, owner);
		this.initialize(resourcePkJoinColumn);
	}

	public void initializeFrom(PrimaryKeyJoinColumn oldPkJoinColumn) {
		super.initializeFrom(oldPkJoinColumn);
		setSpecifiedReferencedColumnName(oldPkJoinColumn.getSpecifiedReferencedColumnName());
	}
	
	@Override
	protected XmlPrimaryKeyJoinColumn getResourceColumn() {
		return this.resourcePkJoinColumn;
	}
	
	@Override
	protected void addResourceColumn() {
		//primaryKeyJoinColumns are part of a collection, the pk-join-column element will be removed/added
		//when the XmlPrimaryKeyJoinColumn is removed/added to the XmlEntity collection
	}
	
	@Override
	protected void removeResourceColumn() {
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
		getResourceColumn().setReferencedColumnName(newSpecifiedReferencedColumnName);
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

	public Column getReferencedDbColumn() {
		Table table = this.getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getReferencedColumnName());
	}

	protected Table getReferencedColumnDbTable() {
		return getOwner().getReferencedColumnDbTable();
	}

	@Override
	protected String tableName() {
		return this.getOwner().getTypeMapping().getPrimaryTableName();
	}

	public boolean isReferencedColumnResolved() {
		return getReferencedDbColumn() != null;
	}

	public TextRange getReferencedColumnNameTextRange() {
		if (getResourceColumn() != null) {
			TextRange textRange = getResourceColumn().getReferencedColumnNameTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return getOwner().getValidationTextRange();
	}

		
	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}
	
	@Override
	protected void initialize(XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		this.resourcePkJoinColumn = resourcePkJoinColumn;
		super.initialize(resourcePkJoinColumn);
		this.specifiedReferencedColumnName = specifiedReferencedColumnName(resourcePkJoinColumn);
		this.defaultReferencedColumnName = defaultReferencedColumnName();
	}
	
	@Override
	public void update(XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		this.resourcePkJoinColumn = resourcePkJoinColumn;
		super.update(resourcePkJoinColumn);
		this.setSpecifiedReferencedColumnName_(specifiedReferencedColumnName(resourcePkJoinColumn));
		this.setDefaultReferencedColumnName(defaultReferencedColumnName());
	}
	
	protected String specifiedReferencedColumnName(XmlPrimaryKeyJoinColumn column) {
		return column == null ? null : column.getReferencedColumnName();
	}
	
	//TODO not correct when we start supporting primaryKeyJoinColumns in 1-1 mappings
	protected String defaultReferencedColumnName() {
		return buildDefaultName();
	}
}
