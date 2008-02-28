/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlNamedColumn;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;


public abstract class AbstractOrmNamedColumn<T extends XmlNamedColumn>  extends AbstractOrmJpaContextNode
	implements NamedColumn
{
	protected Owner owner;
	
	protected String specifiedName;
	
	protected String defaultName;

	protected String columnDefinition;

	protected AbstractOrmNamedColumn(OrmJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
	}
	
	public void initializeFrom(NamedColumn oldColumn) {
		setSpecifiedName(oldColumn.getSpecifiedName());
		setColumnDefinition(oldColumn.getColumnDefinition());
	}

	protected abstract T columnResource();
	
	protected abstract void removeColumnResource();
	
	protected abstract void addColumnResource();

	public Owner owner() {
		return this.owner;
	}

//	@Override
//	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
//		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.INAMED_COLUMN__NAME);
//	}


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
			if (this.columnResource() != null) {
				this.columnResource().setName(newSpecifiedName);						
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedName != null) {
				addColumnResource();
				columnResource().setName(newSpecifiedName);
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
			if (this.columnResource() != null) {
				this.columnResource().setColumnDefinition(newColumnDefinition);						
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newColumnDefinition != null) {
				addColumnResource();
				columnResource().setColumnDefinition(newColumnDefinition);
			}
		}
		firePropertyChanged(COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, newColumnDefinition);
	}
	
	protected void setColumnDefinition_(String newColumnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = newColumnDefinition;
		firePropertyChanged(COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, newColumnDefinition);
	}

	public Column dbColumn() {
		Table table = this.dbTable();
		return (table == null) ? null : table.columnNamed(getName());
	}

	public Table dbTable() {
		return owner().dbTable(this.tableName());
	}

	protected abstract String tableName();

	public boolean isResolved() {
		return dbColumn() != null;
	}

	public TextRange nameTextRange() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public ITextRange nameTextRange() {
//		if (node == null) {
//			return owner.validationTextRange();
//		}
//		IDOMNode nameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.NAME);
//		return (nameNode == null) ? validationTextRange() : buildTextRange(nameNode);
//	}

	public TextRange validationTextRange() {
		return columnResource().validationTextRange();
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
		return this.owner().defaultColumnName();
	}


}