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
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;

public class GenericPersistenceUnitDefaults extends AbstractOrmJpaContextNode
	implements PersistenceUnitDefaults
{
	protected String specifiedSchema;
	
	protected String defaultSchema;

	protected String specifiedCatalog;

	protected String defaultCatalog;

	protected AccessType access;

	protected boolean cascadePersist;

	protected XmlEntityMappings entityMappings;
	
	public GenericPersistenceUnitDefaults(PersistenceUnitMetadata parent, XmlEntityMappings xmlEntityMappings) {
		super(parent);
		this.initialize(xmlEntityMappings);
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
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
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
		return (this.getSpecifiedCatalog() == null) ? this.getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}
	

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String newCatalog) {
		String oldCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newCatalog;
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
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
				persistenceUnitDefaults().setCatalog(newCatalog);
			}
		}
		firePropertyChanged(PersistenceUnitDefaults.SPECIFIED_CATALOG_PROPERTY, oldCatalog, newCatalog);
	}
	
	protected void setSpecifiedCatalog_(String newCatalog) {
		String oldCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newCatalog;
		firePropertyChanged(PersistenceUnitDefaults.SPECIFIED_CATALOG_PROPERTY, oldCatalog, newCatalog);
	}


	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? this.getDefaultSchema() : this.getSpecifiedSchema();
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String newSchema) {
		String oldSchema = this.specifiedSchema;
		this.specifiedSchema = newSchema;
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
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
				persistenceUnitDefaults().setSchema(newSchema);
			}
		}
		firePropertyChanged(PersistenceUnitDefaults.SPECIFIED_SCHEMA_PROPERTY, oldSchema, newSchema);
	}
	
	protected void setSpecifiedSchema_(String newSchema) {
		String oldSchema = this.specifiedSchema;
		this.specifiedSchema = newSchema;
		firePropertyChanged(PersistenceUnitDefaults.SPECIFIED_SCHEMA_PROPERTY, oldSchema, newSchema);
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
					this.entityMappings.setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
				}
				persistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
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
	
	protected void initialize(XmlEntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitDefaults() != null) {
			this.access = AccessType.fromXmlResourceModel(this.persistenceUnitDefaults().getAccess());
			this.cascadePersist = this.persistenceUnitDefaults().isCascadePersist();
			this.specifiedCatalog = this.persistenceUnitDefaults().getCatalog();
			this.specifiedSchema = this.persistenceUnitDefaults().getSchema();
		}
	}
	
	
	public void update(XmlEntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		if (this.persistenceUnitDefaults() != null) {
			this.setAccess_(AccessType.fromXmlResourceModel(this.persistenceUnitDefaults().getAccess()));
			this.setCascadePersist_(this.persistenceUnitDefaults().isCascadePersist());
			this.setSpecifiedCatalog_(this.persistenceUnitDefaults().getCatalog());
			this.setSpecifiedSchema_(this.persistenceUnitDefaults().getSchema());
		}
		else {
			this.setAccess_(null);
			this.setCascadePersist_(false);
			this.setSpecifiedCatalog_(null);
			this.setSpecifiedSchema_(null);
		}
		this.setDefaultSchema(this.projectDefaultSchemaName());
		this.setDefaultCatalog(this.projectDefaultCatalogName());
	}
	
	protected String projectDefaultSchemaName() {
		Schema projectDefaultSchema = getJpaProject().getDefaultSchema();
		return projectDefaultSchema == null ? null : projectDefaultSchema.getName();
	}

	protected String projectDefaultCatalogName() {
		Catalog catalog = getJpaProject().getConnectionProfile().getDefaultCatalog();
		return (catalog == null) ? null : catalog.getName();
	}

	protected XmlPersistenceUnitDefaults persistenceUnitDefaults() {
		if (persistenceUnitMetadata() != null) {
			return persistenceUnitMetadata().getPersistenceUnitDefaults();
		}
		return null;
	}

	protected XmlPersistenceUnitMetadata persistenceUnitMetadata() {
		return this.entityMappings.getPersistenceUnitMetadata();
	}

	public TextRange validationTextRange() {
		if (persistenceUnitDefaults() != null) {
			return persistenceUnitDefaults().validationTextRange();
		}
		return this.entityMappings.validationTextRange();
	}
}
