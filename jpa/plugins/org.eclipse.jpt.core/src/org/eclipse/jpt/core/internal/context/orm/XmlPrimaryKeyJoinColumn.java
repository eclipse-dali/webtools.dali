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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;

public class XmlPrimaryKeyJoinColumn extends AbstractXmlNamedColumn<PrimaryKeyJoinColumn>
	implements IPrimaryKeyJoinColumn
{
	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected PrimaryKeyJoinColumn primaryKeyJoinColumn;
	
	protected XmlPrimaryKeyJoinColumn(IJpaContextNode parent, IAbstractJoinColumn.Owner owner) {
		super(parent, owner);
	}

	@Override
	protected PrimaryKeyJoinColumn columnResource() {
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
	public IAbstractJoinColumn.Owner owner() {
		return (IAbstractJoinColumn.Owner) this.owner;
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
		return this.owner().typeMapping().getTableName();
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public ITextRange referencedColumnNameTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
//	public ITextRange referencedColumnNameTextRange() {
//		if (node == null) {
//			return owner.validationTextRange();
//		}
//		IDOMNode referencedColumnNameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.REFERENCED_COLUMN_NAME);
//		return (referencedColumnNameNode == null) ? validationTextRange() : buildTextRange(referencedColumnNameNode);
//	}
//
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		setDefaultReferencedColumnName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_REFERENCED_COLUMN_NAME_KEY));
//		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_NAME_KEY));
//	}

		
	public boolean isVirtual() {
		return owner().isVirtual(this);
	}
	
	@Override
	protected void initialize(PrimaryKeyJoinColumn column) {
		this.primaryKeyJoinColumn = column;
		super.initialize(column);
		this.specifiedReferencedColumnName = column.getReferencedColumnName();
		this.defaultReferencedColumnName = defaultReferencedColumnName();
	}
	
	@Override
	protected void update(PrimaryKeyJoinColumn column) {
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
