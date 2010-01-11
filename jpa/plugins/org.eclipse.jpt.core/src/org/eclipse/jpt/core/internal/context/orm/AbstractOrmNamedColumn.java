/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;


public abstract class AbstractOrmNamedColumn<T extends AbstractXmlNamedColumn> extends AbstractOrmXmlContextNode
	implements OrmNamedColumn
{
	protected Owner owner;
	
	protected String specifiedName;
	
	protected String defaultName;

	protected String columnDefinition;

	protected AbstractOrmNamedColumn(XmlContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
	}

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}
	
	public void initializeFrom(NamedColumn oldColumn) {
		setSpecifiedName(oldColumn.getSpecifiedName());
		setColumnDefinition(oldColumn.getColumnDefinition());
	}

	protected abstract T getResourceColumn();
	
	protected abstract void removeResourceColumn();
	
	protected abstract void addResourceColumn();

	protected void removeResourceColumnIfFeaturesUnset() {
		if (this.getResourceColumn().isUnset()) {
			removeResourceColumn();
		}		
	}
	
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
		if (this.attributeValueHasChanged(oldSpecifiedName, newSpecifiedName)) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setName(newSpecifiedName);
				this.removeResourceColumnIfFeaturesUnset();
			}
			else if (newSpecifiedName != null) {
				addResourceColumn();
				getResourceColumn().setName(newSpecifiedName);
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
		if (this.attributeValueHasChanged(oldColumnDefinition, newColumnDefinition)) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setColumnDefinition(newColumnDefinition);
				this.removeResourceColumnIfFeaturesUnset();
			}
			else if (newColumnDefinition != null) {
				addResourceColumn();
				getResourceColumn().setColumnDefinition(newColumnDefinition);
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
		return (table == null) ? null : table.getColumnForIdentifier(getName());
	}

	public Table getDbTable() {
		return getOwner().getDbTable(this.getTable());
	}

	/**
	 * Return the name of the column's table. This is overridden
	 * in AbstractJavaBaseColumn where a table can be defined.
	 */
	protected String getTable() {
		return this.getOwner().getTypeMapping().getPrimaryTableName();
	}

	public boolean isResolved() {
		return getDbColumn() != null;
	}

	public TextRange getNameTextRange() {
		if (getResourceColumn() != null) {
			TextRange textRange = getResourceColumn().getNameTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return getOwner().getValidationTextRange();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = getResourceColumn().getValidationTextRange();
		if (textRange != null) {
			return textRange;
		}
		return getOwner().getValidationTextRange();
	}

	
	// ******************* initialization from orm xml resource model ********************
	
	protected void initialize(T column) {
		this.specifiedName = this.getResourceColumnName(column);
		this.defaultName = this.buildDefaultName();
		this.columnDefinition = this.getResourceColumnDefinition(column);
	}
	
	protected void update(T column) {
		setSpecifiedName_(this.getResourceColumnName(column));
		setDefaultName(this.buildDefaultName());
		setColumnDefinition_(this.getResourceColumnDefinition(column));	
	}

	protected String getResourceColumnName(T column) {
		return column == null ? null : column.getName();
	}
	
	protected String getResourceColumnDefinition(T column) {
		return column == null ? null : column.getColumnDefinition();
	}
	
	/**
	 * Return the default column name.
	 */
	protected String buildDefaultName() {
		return this.getOwner().getDefaultColumnName();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}

}