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
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
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
		this.column = getJpaFactory().buildOrmColumn(this, this);
		this.initialize(xmlAttributeOverride);
	}
	@Override
	public OrmJpaContextNode getParent() {
		return (OrmJpaContextNode) super.getParent();
	}

	public OrmAttributeOverride setVirtual(boolean virtual) {
		return (OrmAttributeOverride) getOwner().setVirtual(virtual, this);
	}

	public Owner getOwner() {
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

	public TypeMapping getTypeMapping() {
		return getOwner().getTypeMapping();
	}

	public Table getDbTable(String tablename) {
		return this.getTypeMapping().getDbTable(getColumn().getTable());
	}
	
	public String getDefaultColumnName() {
		ColumnMapping columnMapping = getColumnMapping();
		if (columnMapping == null) {
			return null;
		}
		return columnMapping.getColumn().getName();
	}
	
	public String getDefaultTableName() {
		ColumnMapping columnMapping = getColumnMapping();
		if (columnMapping == null) {
			return null;
		}
		String tableName = columnMapping.getColumn().getSpecifiedTable();
		if (tableName != null) {
			return tableName;
		}
		return getOwner().getTypeMapping().getTableName();
	}
	
	protected ColumnMapping getColumnMapping() {
		return getOwner().getColumnMapping(getName());
	}

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.attributeOverride.getValidationTextRange();
		return textRange == null ? getParent().getValidationTextRange() : textRange;
	}


	//***************** IXmlColumn.Owner implementation ****************
	
	public XmlColumn getColumnResource() {
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
		
		if (doContinue && getTypeMapping().tableNameIsInvalid(table)) {
			if (isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_TABLE,
						new String[] {getName(), table, column.getName()},
						column, 
						column.getTableTextRange())
				);
			}
			else {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
							new String[] {table, column.getName()}, 
							column,
							column.getTableTextRange())
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
						column.getNameTextRange())
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
							new String[] {column.getName()}, 
							column,
							column.getNameTextRange())
					);
			}
		}
	}
	
}
