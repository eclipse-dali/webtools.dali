/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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

	protected XmlEntity resourceEntity;
	
	public GenericOrmTable(OrmEntity parent) {
		super(parent);
	}

	public OrmEntity getOrmEntity() {
		return (OrmEntity) super.getParent();
	}
	
	@Override
	protected XmlTable getResourceTable() {
		return this.resourceEntity.getTable();
	}
	
	@Override
	protected void removeResourceTable() {
		this.resourceEntity.setTable(null);
	}
	
	@Override
	protected void addResourceTable() {
		this.resourceEntity.setTable(OrmFactory.eINSTANCE.createXmlTable());
		
	}
	
	protected JavaTable getJavaTable() {
		JavaEntity javaEntity = getOrmEntity().getJavaEntity();
		if (javaEntity != null) {
			return javaEntity.getTable();
		}
		return null;
	}
	
	public void initialize(XmlEntity resourceEntity) {
		this.resourceEntity = resourceEntity;
		this.initialize(this.getResourceTable());
	}
	
	public void update(XmlEntity resourceEntity) {
		this.resourceEntity = resourceEntity;
		this.update(this.getResourceTable());
	}

	@Override
	protected String defaultName() {
		JavaTable javaTable = getJavaTable();
		if (javaTable != null) {
			if (!getOrmEntity().isMetadataComplete() && getResourceTable() == null && javaTable.getSpecifiedName() != null) {
				return javaTable.getSpecifiedName();
			}
		}
		Entity rootEntity = getOrmEntity().getRootEntity();
		if (rootEntity != getOrmEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getName();
			}
		}
		return getOrmEntity().getName();
	}
	
	@Override
	protected String defaultSchema() {
		JavaTable javaTable = getJavaTable();
		if (javaTable != null ) {
			if (getOrmEntity().isMetadataComplete() || (getResourceTable() != null)) {
				return javaTable.getDefaultSchema();
			}
			return javaTable.getSchema();
		}
		Entity rootEntity = getOrmEntity().getRootEntity();
		if (rootEntity != getOrmEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getSchema();
			}
		}
		return getEntityMappings().getSchema();
	}
	
	@Override
	protected String defaultCatalog() {
		JavaTable javaTable = getJavaTable();
		if (javaTable != null) {
			if (getOrmEntity().isMetadataComplete() || (getResourceTable() != null)) {
				return javaTable.getDefaultCatalog();
			}
			return javaTable.getCatalog();
		}
		Entity rootEntity = getOrmEntity().getRootEntity();
		if (rootEntity != getOrmEntity()) {
			if (rootEntity.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
				return rootEntity.getTable().getCatalog();
			}
		}
		return getEntityMappings().getCatalog();
	}
	
	//*********** Validation *******************************
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		boolean doContinue = connectionProfileIsActive();
		String schema = this.getSchema();
		
		if (doContinue && ! this.hasResolvedSchema()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, this.getName()}, 
						this,
						this.getSchemaTextRange())
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
						this.getNameTextRange())
				);
		}
	}
}
