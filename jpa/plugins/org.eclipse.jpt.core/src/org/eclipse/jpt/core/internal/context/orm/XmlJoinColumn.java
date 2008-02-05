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
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.JoinColumn;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;

public class XmlJoinColumn extends AbstractXmlColumn<JoinColumn> implements IJoinColumn
{

	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected JoinColumn joinColumn;

	protected XmlJoinColumn(IJpaContextNode parent, IJoinColumn.Owner owner) {
		super(parent, owner);
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
	
	public void setSpecifiedReferencedColumnName_(String newSpecifiedReferencedColumnName) {
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

	public boolean isVirtual() {
		return owner().isVirtual(this);
	}
	
	@Override
	public IJoinColumn.Owner owner() {
		return (IJoinColumn.Owner) this.owner;
	}

	public Table dbReferencedColumnTable() {
		return owner().dbReferencedColumnTable();
	}

	public Column dbReferencedColumn() {
		Table table = dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(getReferencedColumnName());
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}
//
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
//		setDefaultTable((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_TABLE_KEY));
//	}

	@Override
	protected JoinColumn columnResource() {
		return this.joinColumn;
	}

	@Override
	protected void addColumnResource() {
		//joinColumns are part of a collection, the join-column element will be removed/added
		//when the XmlJoinColumn is removed/added to the XmlEntity collection
	}
	
	@Override
	protected void removeColumnResource() {
		//joinColumns are part of a collection, the pk-join-column element will be removed/added
		//when the XmlJoinColumn is removed/added to the XmlEntity collection
	}

	public ITextRange referencedColumnNameTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	protected void initialize(JoinColumn column) {
		this.joinColumn = column;
		super.initialize(column);
		this.specifiedReferencedColumnName = column.getReferencedColumnName();
		this.defaultReferencedColumnName = defaultReferencedColumnName();
	}
	
	@Override
	protected void update(JoinColumn column) {
		this.joinColumn = column;
		super.update(column);
		this.setSpecifiedReferencedColumnName_(column.getReferencedColumnName());
		this.setDefaultReferencedColumnName(defaultReferencedColumnName());
	}
	
	protected String defaultReferencedColumnName() {
		if (!owner().relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		//TODO
		return null;
	}
	
	@Override
	protected String defaultName() {
		if (!owner().relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		//TODO
		return super.defaultName();
	}
	
	@Override
	protected String defaultTable() {
		if (!owner().relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		//TODO
		return super.defaultTable();
	}

}
