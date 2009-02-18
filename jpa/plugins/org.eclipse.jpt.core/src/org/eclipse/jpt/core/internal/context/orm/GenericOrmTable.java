/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlTable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * 
 */
public class GenericOrmTable
	extends AbstractOrmTable
	implements OrmTable
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

	public boolean hasSpecifiedResourceTable() {
		return this.getResourceTable() != null;
	}

	@Override
	protected XmlTable addResourceTable() {
		XmlTable resourceTable = OrmFactory.eINSTANCE.createXmlTable();
		this.resourceEntity.setTable(resourceTable);
		return resourceTable;
	}
	
	@Override
	protected void removeResourceTable() {
		this.resourceEntity.setTable(null);
	}
	
	public void initialize(XmlEntity xmlEntity) {
		this.resourceEntity = xmlEntity;
		this.initialize(this.getResourceTable());
	}
	
	public void update(XmlEntity xmlEntity) {
		this.resourceEntity = xmlEntity;
		this.update(this.getResourceTable());
	}

	@Override
	protected String buildDefaultName() {
		return this.getOrmEntity().getDefaultTableName();
	}
	
	@Override
	protected String buildDefaultSchema() {
		return this.getOrmEntity().getDefaultSchema();
	}
	
	@Override
	protected String buildDefaultCatalog() {
		return this.getOrmEntity().getDefaultCatalog();
	}
	
	//*********** Validation *******************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.connectionProfileIsActive()) {
			this.validateAgainstDatabase(messages);
		}
	}

	protected void validateAgainstDatabase(List<IMessage> messages) {
		if ( ! this.hasResolvedCatalog()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TABLE_UNRESOLVED_CATALOG,
					new String[] {this.getCatalog(), this.getName()}, 
					this,
					this.getCatalogTextRange()
				)
			);
			return;
		}
		
		if ( ! this.hasResolvedSchema()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
					new String[] {this.getSchema(), this.getName()}, 
					this,
					this.getSchemaTextRange()
				)
			);
			return;
		}
		
		if ( ! this.isResolved()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TABLE_UNRESOLVED_NAME,
					new String[] {this.getName()}, 
					this, 
					this.getNameTextRange()
				)
			);
			return;
		}
	}

}
