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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.db.internal.Table;


public class GenericOrmAttributeOverride extends AbstractJpaContextNode
	implements AttributeOverride, OrmColumn.Owner
{

	protected String name;

	private final Owner owner;

	protected XmlAttributeOverride attributeOverride;
	

	protected final GenericOrmColumn column;

	protected GenericOrmAttributeOverride(JpaContextNode parent, AttributeOverride.Owner owner) {
		super(parent);
		this.owner = owner;
		this.column = new GenericOrmColumn(this, this);
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
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	public GenericOrmColumn getColumn() {
		return this.column;
	}

	public TextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeMapping typeMapping() {
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
	
	public XmlColumn columnResource() {
		return this.attributeOverride.getColumn();
	}
	
	public void addColumnResource() {
		this.attributeOverride.setColumn(OrmFactory.eINSTANCE.createColumnImpl());
	}
	
	public void removeColumnResource() {
		this.attributeOverride.setColumn(null);
	}
	
	
	//***************** updating ****************
	
	public void initialize(XmlAttributeOverride attributeOverride) {
		this.attributeOverride = attributeOverride;
		this.name = attributeOverride.getName();
		this.column.initialize(attributeOverride.getColumn());
	}
	
	public void update(XmlAttributeOverride attributeOverride) {
		this.attributeOverride = attributeOverride;
		this.setName_(attributeOverride.getName());
		this.column.update(attributeOverride.getColumn());
	}
}
