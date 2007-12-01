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

import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;

public class PersistenceUnitDefaultsImpl extends JpaContextNode
	implements PersistenceUnitDefaults
{
	protected String schema;

	protected String catalog;

	protected AccessType access;

	protected boolean cascadePersist;

	protected org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings;
	
	public PersistenceUnitDefaultsImpl(PersistenceUnitMetadata parent) {
		super(parent);
	}

	public AccessType getAccess() {
		return this.access;
	}

	public void setAccess(AccessType newAccess) {
		AccessType oldAccess = this.access;
		this.access = newAccess;
		this.persistenceUnitDefaults().setAccess(AccessType.toXmlResourceModel(newAccess));
		firePropertyChanged(PersistenceUnitDefaults.ACCESS_PROPERTY, oldAccess, newAccess);
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String newCatalog) {
		String oldCatalog = this.catalog;
		this.catalog = newCatalog;
		this.persistenceUnitDefaults().setCatalog(newCatalog);
		firePropertyChanged(PersistenceUnitDefaults.CATALOG_PROPERTY, oldCatalog, newCatalog);
	}

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String newSchema) {
		String oldSchema = this.schema;
		this.schema = newSchema;
		this.persistenceUnitDefaults().setSchema(newSchema);
		firePropertyChanged(PersistenceUnitDefaults.SCHEMA_PROPERTY, oldSchema, newSchema);
	}
	
	public boolean isCascadePersist() {
		return this.cascadePersist;
	}

	public void setCascadePersist(boolean newCascadePersist) {
		boolean oldCascadePersist = this.cascadePersist;
		this.cascadePersist = newCascadePersist;
		this.persistenceUnitDefaults().setCascadePersist(newCascadePersist);
		firePropertyChanged(PersistenceUnitDefaults.CASCADE_PERSIST_PROPERTY, oldCascadePersist, newCascadePersist);
	}
	
	public void initialize(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitDefaults() != null) {
			this.access = AccessType.fromXmlResourceModel(this.persistenceUnitDefaults().getAccess());
			this.cascadePersist = this.persistenceUnitDefaults().isCascadePersist();
			this.catalog = this.persistenceUnitDefaults().getCatalog();
			this.schema = this.persistenceUnitDefaults().getSchema();
		}
	}
	
	
	public void update(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitDefaults() != null) {
			this.setAccess(AccessType.fromXmlResourceModel(this.persistenceUnitDefaults().getAccess()));
			this.setCascadePersist(this.persistenceUnitDefaults().isCascadePersist());
			this.setCatalog(this.persistenceUnitDefaults().getCatalog());
			this.setSchema(this.persistenceUnitDefaults().getSchema());
		}
//		else {
//			this.setAccess(null);
//			this.setCascadePersist(false);
//			this.setCatalog(null);
//			this.setSchema(null);
//		}
	}
	
	protected org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults() {
		if (this.entityMappings.getPersistenceUnitMetadata() != null) {
			return this.entityMappings.getPersistenceUnitMetadata().getPersistenceUnitDefaults();
		}
		return null;
	}

}
