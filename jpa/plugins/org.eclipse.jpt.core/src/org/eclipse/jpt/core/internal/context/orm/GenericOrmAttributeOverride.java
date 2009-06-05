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

import java.util.List;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericOrmAttributeOverride extends AbstractXmlContextNode
	implements OrmAttributeOverride, OrmColumn.Owner
{

	protected String name;

	private final Owner owner;

	protected XmlAttributeOverride resourceAttributeOverride;
	

	protected final OrmColumn column;

	public GenericOrmAttributeOverride(XmlContextNode parent, AttributeOverride.Owner owner, XmlAttributeOverride resourceAttributeOverride) {
		super(parent);
		this.owner = owner;
		this.column = getJpaFactory().buildOrmColumn(this, this);
		this.resourceAttributeOverride = resourceAttributeOverride;
		this.name = resourceAttributeOverride.getName();
		this.column.initialize(resourceAttributeOverride.getColumn());
	}
	
	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
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
		this.resourceAttributeOverride.setName(newName);
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
		return getOwner().getTypeMapping().getPrimaryTableName();
	}
	
	protected ColumnMapping getColumnMapping() {
		return getOwner().getColumnMapping(getName());
	}

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.resourceAttributeOverride.getValidationTextRange();
		return textRange == null ? getParent().getValidationTextRange() : textRange;
	}


	//***************** IXmlColumn.Owner implementation ****************
	
	public XmlColumn getResourceColumn() {
		return this.resourceAttributeOverride.getColumn();
	}
	
	public void addResourceColumn() {
		this.resourceAttributeOverride.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
	}
	
	public void removeResourceColumn() {
		this.resourceAttributeOverride.setColumn(null);
	}
	
	
	//***************** updating ****************
	
	public void update(XmlAttributeOverride xmlAttributeOverride) {
		this.resourceAttributeOverride = xmlAttributeOverride;
		this.setName_(xmlAttributeOverride.getName());
		this.column.update(xmlAttributeOverride.getColumn());
	}
	
	//****************** validation ********************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.connectionProfileIsActive()) {
			this.validateColumn(messages);
		}
	}
	
	protected void validateColumn(List<IMessage> messages) {
		String tableName = this.column.getTable();
		if (this.getTypeMapping().tableNameIsInvalid(tableName)) {
			if (this.isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_TABLE,
						new String[] {this.getName(), tableName, this.column.getName()},
						this.column, 
						this.column.getTableTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {tableName, this.column.getName()}, 
						this.column,
						this.column.getTableTextRange()
					)
				);
			}
			return;
		}
		
		if ( ! this.column.isResolved() && this.column.getDbTable() != null) {
			if (this.isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName(), this.column.getName()}, 
						this.column,
						this.column.getNameTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {this.column.getName()}, 
						this.column,
						this.column.getNameTextRange()
					)
				);
			}
		}
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}
