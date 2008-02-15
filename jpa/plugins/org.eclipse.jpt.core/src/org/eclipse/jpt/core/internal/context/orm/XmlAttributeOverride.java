/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.orm.Column;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.db.internal.Table;


public class XmlAttributeOverride extends JpaContextNode
	implements IAttributeOverride, IXmlColumn.Owner
{

	protected String name;

	private final Owner owner;

	protected AttributeOverride attributeOverride;
	

	protected final XmlColumn column;

	protected XmlAttributeOverride(IJpaContextNode parent, IAttributeOverride.Owner owner) {
		super(parent);
		this.owner = owner;
		this.column = new XmlColumn(this, this);
	}

	public Owner owner() {
		return this.owner;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.attributeOverride.setName(newName);
		firePropertyChanged(IOverride.NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(IOverride.NAME_PROPERTY, oldName, newName);
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}

	public ITypeMapping typeMapping() {
		return owner().typeMapping();
	}

	public Table dbTable(String tablename) {
		return this.typeMapping().dbTable(getColumn().getTable());
	}
	
	public String defaultColumnName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String defaultTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isVirtual() {
		return owner().isVirtual(this);
	}

//	@Override
//	public ITextRange validationTextRange() {
//		if (node == null) {
//			return getOwner().validationTextRange();
//		}
//		return super.validationTextRange();
//	}

	//***************** IXmlColumn.Owner implementation ****************
	
	public Column columnResource() {
		return this.attributeOverride.getColumn();
	}
	
	public void addColumnResource() {
		this.attributeOverride.setColumn(OrmFactory.eINSTANCE.createColumnImpl());
	}
	
	public void removeColumnResource() {
		this.attributeOverride.setColumn(null);
	}
	
	
	//***************** updating ****************
	
	public void initialize(AttributeOverride attributeOverride) {
		this.attributeOverride = attributeOverride;
		this.name = attributeOverride.getName();
		this.column.initialize(attributeOverride.getColumn());
	}
	
	public void update(AttributeOverride attributeOverride) {
		this.attributeOverride = attributeOverride;
		this.setName_(attributeOverride.getName());
		this.column.update(attributeOverride.getColumn());
	}
}
