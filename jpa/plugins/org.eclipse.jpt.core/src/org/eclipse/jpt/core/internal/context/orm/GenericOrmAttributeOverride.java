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

import java.util.List;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmAttributeOverride extends AbstractOrmJpaContextNode
	implements OrmAttributeOverride, OrmColumn.Owner
{

	protected String name;

	private final Owner owner;

	protected XmlAttributeOverride attributeOverride;
	

	protected final OrmColumn column;

	public GenericOrmAttributeOverride(OrmJpaContextNode parent, AttributeOverride.Owner owner, XmlAttributeOverride xmlAttributeOverride) {
		super(parent);
		this.owner = owner;
		this.column = jpaFactory().buildOrmColumn(this, this);
		this.initialize(xmlAttributeOverride);
	}
	
	public OrmAttributeOverride setVirtual(boolean virtual) {
		return (OrmAttributeOverride) owner().setVirtual(virtual, this);
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

	public OrmColumn getColumn() {
		return this.column;
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
	public TextRange validationTextRange() {
		// TODO Auto-generated method stub
		return null;
	}


	//***************** IXmlColumn.Owner implementation ****************
	
	public XmlColumn columnResource() {
		return this.attributeOverride.getColumn();
	}
	
	public void addColumnResource() {
		this.attributeOverride.setColumn(OrmFactory.eINSTANCE.createXmlColumnImpl());
	}
	
	public void removeColumnResource() {
		this.attributeOverride.setColumn(null);
	}
	
	
	//***************** updating ****************
	
	protected void initialize(XmlAttributeOverride attributeOverride) {
		this.attributeOverride = attributeOverride;
		this.name = attributeOverride.getName();
		this.column.initialize(attributeOverride.getColumn());
	}
	
	public void update(XmlAttributeOverride attributeOverride) {
		this.attributeOverride = attributeOverride;
		this.setName_(attributeOverride.getName());
		this.column.update(attributeOverride.getColumn());
	}
	
	//****************** validation ********************
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
	
		addColumnMessages(messages);
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		OrmColumn column = getColumn();
		String table = column.getTable();
		boolean doContinue = connectionProfileIsActive();
		
		if (doContinue && typeMapping().tableNameIsInvalid(table)) {
			if (isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_TABLE,
						new String[] {getName(), table, column.getName()},
						column, 
						column.tableTextRange())
				);
			}
			else {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
							new String[] {table, column.getName()}, 
							column,
							column.tableTextRange())
					);
			}
			doContinue = false;
		}
		
		if (doContinue && !column.isResolved()) {
			if (isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
						new String[] {getName(), column.getName()}, 
						column,
						column.nameTextRange())
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
							new String[] {column.getName()}, 
							column,
							column.nameTextRange())
					);
			}
		}
	}
	
}
