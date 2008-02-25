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

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;

public class GenericPersistenceUnitDefaults extends AbstractJpaContextNode
	implements PersistenceUnitDefaults
{
	protected String schema;

	protected String catalog;

	protected AccessType access;

	protected boolean cascadePersist;

	protected org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings;
	
	public GenericPersistenceUnitDefaults(PersistenceUnitMetadata parent) {
		super(parent);
	}

	public AccessType getAccess() {
		return this.access;
	}

	public void setAccess(AccessType newAccess) {
		AccessType oldAccess = this.access;
		this.access = newAccess;
		if (oldAccess != newAccess) {
			if (this.persistenceUnitDefaults() != null) {
				this.persistenceUnitDefaults().setAccess(AccessType.toXmlResourceModel(newAccess));
				if (this.persistenceUnitDefaults().isAllFeaturesUnset()) {
					this.persistenceUnitMetadata().setPersistenceUnitDefaults(null);
					if (this.persistenceUnitMetadata().isAllFeaturesUnset()) {
						this.entityMappings.setPersistenceUnitMetadata(null);
					}
				}
			}
			else {
				if (persistenceUnitMetadata() == null) {
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
				persistenceUnitDefaults().setAccess(AccessType.toXmlResourceModel(newAccess));
			}
		}
		firePropertyChanged(PersistenceUnitDefaults.ACCESS_PROPERTY, oldAccess, newAccess);
	}
	
	protected void setAccess_(AccessType newAccess) {
		AccessType oldAccess = this.access;
		this.access = newAccess;
		firePropertyChanged(PersistenceUnitDefaults.ACCESS_PROPERTY, oldAccess, newAccess);
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String newCatalog) {
		String oldCatalog = this.catalog;
		this.catalog = newCatalog;
		if (oldCatalog != newCatalog) {
			if (this.persistenceUnitDefaults() != null) {
				this.persistenceUnitDefaults().setCatalog(newCatalog);
				if (this.persistenceUnitDefaults().isAllFeaturesUnset()) {
					this.persistenceUnitMetadata().setPersistenceUnitDefaults(null);
					if (this.persistenceUnitMetadata().isAllFeaturesUnset()) {
						this.entityMappings.setPersistenceUnitMetadata(null);
					}
				}
			}
			else {
				if (persistenceUnitMetadata() == null) {
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
				persistenceUnitDefaults().setCatalog(newCatalog);
			}
		}
		firePropertyChanged(PersistenceUnitDefaults.CATALOG_PROPERTY, oldCatalog, newCatalog);
	}
	
	protected void setCatalog_(String newCatalog) {
		String oldCatalog = this.catalog;
		this.catalog = newCatalog;
		firePropertyChanged(PersistenceUnitDefaults.CATALOG_PROPERTY, oldCatalog, newCatalog);
	}

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String newSchema) {
		String oldSchema = this.schema;
		this.schema = newSchema;
		if (oldSchema != newSchema) {
			if (this.persistenceUnitDefaults() != null) {
				this.persistenceUnitDefaults().setSchema(newSchema);
				if (this.persistenceUnitDefaults().isAllFeaturesUnset()) {
					this.persistenceUnitMetadata().setPersistenceUnitDefaults(null);
					if (this.persistenceUnitMetadata().isAllFeaturesUnset()) {
						this.entityMappings.setPersistenceUnitMetadata(null);
					}
				}
			}
			else {
				if (persistenceUnitMetadata() == null) {
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
				persistenceUnitDefaults().setSchema(newSchema);
			}
		}
		firePropertyChanged(PersistenceUnitDefaults.SCHEMA_PROPERTY, oldSchema, newSchema);
	}
	
	protected void setSchema_(String newSchema) {
		String oldSchema = this.schema;
		this.schema = newSchema;
		firePropertyChanged(PersistenceUnitDefaults.SCHEMA_PROPERTY, oldSchema, newSchema);
	}
	
	public boolean isCascadePersist() {
		return this.cascadePersist;
	}

	public void setCascadePersist(boolean newCascadePersist) {
		boolean oldCascadePersist = this.cascadePersist;
		this.cascadePersist = newCascadePersist;
		if (oldCascadePersist != newCascadePersist) {
			if (this.persistenceUnitDefaults() != null) {
				this.persistenceUnitDefaults().setCascadePersist(newCascadePersist);
				if (this.persistenceUnitDefaults().isAllFeaturesUnset()) {
					this.persistenceUnitMetadata().setPersistenceUnitDefaults(null);
					if (this.persistenceUnitMetadata().isAllFeaturesUnset()) {
						this.entityMappings.setPersistenceUnitMetadata(null);
					}
				}
			}
			else if (newCascadePersist) {
				if (persistenceUnitMetadata() == null) {
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
				persistenceUnitDefaults().setCascadePersist(newCascadePersist);
			}
		}
		firePropertyChanged(PersistenceUnitDefaults.CASCADE_PERSIST_PROPERTY, oldCascadePersist, newCascadePersist);
	}

	protected void setCascadePersist_(boolean newCascadePersist) {
		boolean oldCascadePersist = this.cascadePersist;
		this.cascadePersist = newCascadePersist;
		firePropertyChanged(PersistenceUnitDefaults.CASCADE_PERSIST_PROPERTY, oldCascadePersist, newCascadePersist);
	}
	
	public void initialize(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitDefaults() != null) {
			this.access = AccessType.fromXmlResourceModel(this.persistenceUnitDefaults().getAccess());
			this.cascadePersist = this.persistenceUnitDefaults().isCascadePersist();
			this.catalog = this.persistenceUnitDefaults().getCatalog();
			this.schema = this.persistenceUnitDefaults().getSchema();
		}
	}
	
	
	public void update(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitDefaults() != null) {
			this.setAccess_(AccessType.fromXmlResourceModel(this.persistenceUnitDefaults().getAccess()));
			this.setCascadePersist_(this.persistenceUnitDefaults().isCascadePersist());
			this.setCatalog_(this.persistenceUnitDefaults().getCatalog());
			this.setSchema_(this.persistenceUnitDefaults().getSchema());
		}
		else {
			this.setAccess_(null);
			this.setCascadePersist_(false);
			this.setCatalog_(null);
			this.setSchema_(null);
		}
	}
	
	protected org.eclipse.jpt.core.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults() {
		if (persistenceUnitMetadata() != null) {
			return persistenceUnitMetadata().getPersistenceUnitDefaults();
		}
		return null;
	}

	protected org.eclipse.jpt.core.resource.orm.PersistenceUnitMetadata persistenceUnitMetadata() {
		return this.entityMappings.getPersistenceUnitMetadata();
	}

}
