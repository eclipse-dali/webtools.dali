/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlTable;

/**
 * 
 */
public class GenericOrmTable
	extends AbstractOrmTable
	implements OrmTable
{
	protected XmlEntity resourceEntity;
	
	public GenericOrmTable(OrmEntity parent, Owner owner) {
		super(parent, owner);
	}

	public OrmEntity getOrmEntity() {
		return (OrmEntity) super.getParent();
	}
	
	@Override
	protected XmlTable getResourceTable() {
		return this.resourceEntity.getTable();
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
	
	public boolean shouldValidateAgainstDatabase() {
		return this.connectionProfileIsActive();
	}
}
