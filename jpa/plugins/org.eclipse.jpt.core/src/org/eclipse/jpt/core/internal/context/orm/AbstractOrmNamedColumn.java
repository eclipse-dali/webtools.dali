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

import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.resource.orm.XmlNamedColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;


public abstract class AbstractOrmNamedColumn<T extends XmlNamedColumn>  extends AbstractOrmJpaContextNode
	implements OrmNamedColumn
{
	protected Owner owner;
	
	protected String specifiedName;
	
	protected String defaultName;

	protected String columnDefinition;

	protected AbstractOrmNamedColumn(OrmJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
	}

	@Override
	public OrmJpaContextNode getParent() {
		return (OrmJpaContextNode) super.getParent();
	}
	
	public void initializeFrom(NamedColumn oldColumn) {
		setSpecifiedName(oldColumn.getSpecifiedName());
		setColumnDefinition(oldColumn.getColumnDefinition());
	}

	protected abstract T getColumnResource();
	
	protected abstract void removeColumnResource();
	
	protected abstract void addColumnResource();

	public Owner getOwner() {
		return this.owner;
	}

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		if (oldSpecifiedName != newSpecifiedName) {
			if (this.getColumnResource() != null) {
				this.getColumnResource().setName(newSpecifiedName);						
				if (this.getColumnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedName != null) {
				addColumnResource();
				getColumnResource().setName(newSpecifiedName);
			}
		}
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	protected void setSpecifiedName_(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}
	
	public String getColumnDefinition() {
		return this.columnDefinition;
	}
	
	public void setColumnDefinition(String newColumnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = newColumnDefinition;
		if (oldColumnDefinition != newColumnDefinition) {
			if (this.getColumnResource() != null) {
				this.getColumnResource().setColumnDefinition(newColumnDefinition);						
				if (this.getColumnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newColumnDefinition != null) {
				addColumnResource();
				getColumnResource().setColumnDefinition(newColumnDefinition);
			}
		}
		firePropertyChanged(COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, newColumnDefinition);
	}
	
	protected void setColumnDefinition_(String newColumnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = newColumnDefinition;
		firePropertyChanged(COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, newColumnDefinition);
	}

	public Column getDbColumn() {
		Table table = this.getDbTable();
		return (table == null) ? null : table.getColumnNamed(getName());
	}

	public Table getDbTable() {
		return getOwner().getDbTable(this.tableName());
	}

	protected abstract String tableName();

	public boolean isResolved() {
		return getDbColumn() != null;
	}

	public TextRange getNameTextRange() {
		if (getColumnResource() != null) {
			TextRange textRange = getColumnResource().getNameTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return getOwner().getValidationTextRange();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = getColumnResource().getValidationTextRange();
		if (textRange != null) {
			return textRange;
		}
		return getOwner().getValidationTextRange();
	}

	
	// ******************* initialization from orm xml resource model ********************
	
	protected void initialize(T column) {
		this.specifiedName = this.specifiedName(column);
		this.defaultName = this.defaultName();
		this.columnDefinition = this.specifiedColumnDefinition(column);
	}
	
	protected void update(T column) {
		setSpecifiedName_(this.specifiedName(column));
		setDefaultName(this.defaultName());
		setColumnDefinition_(this.specifiedColumnDefinition(column));	
	}

	protected String specifiedName(T column) {
		return column == null ? null : column.getName();
	}
	
	protected String specifiedColumnDefinition(T column) {
		return column == null ? null : column.getColumnDefinition();
	}
	
	/**
	 * Return the default column name.
	 */
	protected String defaultName() {
		return this.getOwner().getDefaultColumnName();
	}


}