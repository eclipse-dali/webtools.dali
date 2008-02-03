/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.java.IJavaTable;
import org.eclipse.jpt.core.internal.resource.orm.AbstractTable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlTable extends AbstractXmlTable
{

	protected Entity entity;
	
	public XmlTable(XmlEntity parent) {
		super(parent);
	}

	public XmlEntity xmlEntity() {
		return (XmlEntity) super.parent();
	}
	
	@Override
	protected AbstractTable table() {
		return this.entity.getTable();
	}
	
	@Override
	protected void removeTableResource() {
		this.entity.setTable(null);
	}
	
	@Override
	protected void addTableResource() {
		this.entity.setTable(OrmFactory.eINSTANCE.createTable());
		
	}
	
	protected IJavaTable javaTable() {
		IJavaEntity javaEntity = xmlEntity().javaEntity();
		if (javaEntity != null) {
			return javaEntity.getTable();
		}
		return null;
	}
	
	
	public void initialize(Entity entity) {
		this.entity = entity;
		this.initialize(this.table());
	}
	
	public void update(Entity entity) {
		this.entity = entity;
		this.update(this.table());
	}

	@Override
	protected String defaultName() {
		IJavaTable javaTable = javaTable();
		if (javaTable != null) {
			if (xmlEntity().isMetadataComplete() || (table() != null)) {
				return javaTable.getDefaultName();
			}
			return javaTable.getName();
		}
		IEntity rootEntity = xmlEntity().rootEntity();
		if (rootEntity != xmlEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getName();
			}
		}
		return xmlEntity().getName();
	}
	
	@Override
	protected String defaultSchema() {
		IJavaTable javaTable = javaTable();
		if (javaTable != null ) {
			if (xmlEntity().isMetadataComplete() || (table() != null)) {
				return javaTable.getDefaultSchema();
			}
			return javaTable.getSchema();
		}
		IEntity rootEntity = xmlEntity().rootEntity();
		if (rootEntity != xmlEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getSchema();
			}
		}
		return entityMappings().getSchema();
	}
	
	@Override
	protected String defaultCatalog() {
		IJavaTable javaTable = javaTable();
		if (javaTable != null) {
			if (xmlEntity().isMetadataComplete() || (table() != null)) {
				return javaTable.getDefaultCatalog();
			}
			return javaTable.getCatalog();
		}
		IEntity rootEntity = xmlEntity().rootEntity();
		if (rootEntity != xmlEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getCatalog();
			}
		}
		return entityMappings().getCatalog();
	}
	
	//******* Validation *******************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		boolean doContinue = isConnected();
		String schema = this.getSchema();
		
		if (doContinue && ! this.hasResolvedSchema()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, this.getName()}, 
						this, this.schemaTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! this.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_NAME,
						new String[] {this.getName()}, 
						this, this.nameTextRange(astRoot))
				);
		}
	}
}
