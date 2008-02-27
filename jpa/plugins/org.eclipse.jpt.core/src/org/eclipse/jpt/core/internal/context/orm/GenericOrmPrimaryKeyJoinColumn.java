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

import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;

public class GenericOrmPrimaryKeyJoinColumn extends AbstractOrmNamedColumn<XmlPrimaryKeyJoinColumn>
	implements OrmPrimaryKeyJoinColumn
{
	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected XmlPrimaryKeyJoinColumn primaryKeyJoinColumn;
	
	public GenericOrmPrimaryKeyJoinColumn(JpaContextNode parent, AbstractJoinColumn.Owner owner) {
		super(parent, owner);
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

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}

	@Override
	public AbstractJoinColumn.Owner owner() {
		return (AbstractJoinColumn.Owner) this.owner;
	}

	public Column dbReferencedColumn() {
		Table table = this.dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(this.getReferencedColumnName());
	}

	public Table dbReferencedColumnTable() {
		return owner().dbReferencedColumnTable();
	}

	@Override
	protected String tableName() {
		return this.owner().typeMapping().tableName();
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

//	public ITextRange referencedColumnNameTextRange() {
//		if (node == null) {
//			return owner.validationTextRange();
//		}
//		IDOMNode referencedColumnNameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.REFERENCED_COLUMN_NAME);
//		return (referencedColumnNameNode == null) ? validationTextRange() : buildTextRange(referencedColumnNameNode);
//	}

		
	public boolean isVirtual() {
		return owner().isVirtual(this);
	}
	
	@Override
	public void initialize(XmlPrimaryKeyJoinColumn column) {
		this.primaryKeyJoinColumn = column;
		super.initialize(column);
		this.specifiedReferencedColumnName = column.getReferencedColumnName();
		this.defaultReferencedColumnName = defaultReferencedColumnName();
	}
	
	@Override
	public void update(XmlPrimaryKeyJoinColumn column) {
		this.primaryKeyJoinColumn = column;
		super.update(column);
		this.setSpecifiedReferencedColumnName(column.getReferencedColumnName());
		this.setDefaultReferencedColumnName(defaultReferencedColumnName());
	}
	
	protected String defaultReferencedColumnName() {
		//TODO
		return null;
	}
}
