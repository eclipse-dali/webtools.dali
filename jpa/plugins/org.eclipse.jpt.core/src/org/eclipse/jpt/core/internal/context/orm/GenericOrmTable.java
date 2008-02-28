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
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlTable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericOrmTable extends AbstractOrmTable implements OrmTable
{

	protected XmlEntity entity;
	
	public GenericOrmTable(OrmEntity parent) {
		super(parent);
	}

	public OrmEntity ormEntity() {
		return (OrmEntity) super.parent();
	}
	
	@Override
	protected XmlTable table() {
		return this.entity.getTable();
	}
	
	@Override
	protected void removeTableResource() {
		this.entity.setTable(null);
	}
	
	@Override
	protected void addTableResource() {
		this.entity.setTable(OrmFactory.eINSTANCE.createXmlTable());
		
	}
	
	protected JavaTable javaTable() {
		JavaEntity javaEntity = ormEntity().javaEntity();
		if (javaEntity != null) {
			return javaEntity.getTable();
		}
		return null;
	}
	
	public void initialize(XmlEntity entity) {
		this.entity = entity;
		this.initialize(this.table());
	}
	
	public void update(XmlEntity entity) {
		this.entity = entity;
		this.update(this.table());
	}

	@Override
	protected String defaultName() {
		JavaTable javaTable = javaTable();
		if (javaTable != null) {
			if (ormEntity().isMetadataComplete() || (table() != null)) {
				return javaTable.getDefaultName();
			}
			return javaTable.getName();
		}
		Entity rootEntity = ormEntity().rootEntity();
		if (rootEntity != ormEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getName();
			}
		}
		return ormEntity().getName();
	}
	
	@Override
	protected String defaultSchema() {
		JavaTable javaTable = javaTable();
		if (javaTable != null ) {
			if (ormEntity().isMetadataComplete() || (table() != null)) {
				return javaTable.getDefaultSchema();
			}
			return javaTable.getSchema();
		}
		Entity rootEntity = ormEntity().rootEntity();
		if (rootEntity != ormEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getSchema();
			}
		}
		return entityMappings().getSchema();
	}
	
	@Override
	protected String defaultCatalog() {
		JavaTable javaTable = javaTable();
		if (javaTable != null) {
			if (ormEntity().isMetadataComplete() || (table() != null)) {
				return javaTable.getDefaultCatalog();
			}
			return javaTable.getCatalog();
		}
		Entity rootEntity = ormEntity().rootEntity();
		if (rootEntity != ormEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getCatalog();
			}
		}
		return entityMappings().getCatalog();
	}
	
	//******* Validation *******************************
	
	//******* Validation *******************************
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		boolean doContinue = isConnected();
		String schema = this.getSchema();
		
		if (doContinue && ! this.hasResolvedSchema()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, this.getName()}, 
						this,
						this.schemaTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! this.isResolved()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TABLE_UNRESOLVED_NAME,
						new String[] {this.getName()}, 
						this, 
						this.nameTextRange())
				);
		}
	}
}
