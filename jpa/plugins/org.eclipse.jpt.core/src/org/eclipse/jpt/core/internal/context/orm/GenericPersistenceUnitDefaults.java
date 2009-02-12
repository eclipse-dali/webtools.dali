/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;

/**
 * 
 */
public class GenericPersistenceUnitDefaults
	extends AbstractXmlContextNode
	implements OrmPersistenceUnitDefaults
{
	protected final XmlEntityMappings xmlEntityMappings;
	
	protected AccessType access;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected boolean cascadePersist;


	// ********** constructor/initialization **********

	public GenericPersistenceUnitDefaults(PersistenceUnitMetadata parent, XmlEntityMappings xmlEntityMappings) {
		super(parent);
		this.xmlEntityMappings = xmlEntityMappings;
		XmlPersistenceUnitDefaults resourceDefaults = this.getResourceDefaults();
		if (resourceDefaults != null) {
			this.access = AccessType.fromOrmResourceModel(resourceDefaults.getAccess());
			this.specifiedCatalog = resourceDefaults.getCatalog();
			this.specifiedSchema = resourceDefaults.getSchema();
			this.cascadePersist = resourceDefaults.isCascadePersist();
		}
		this.defaultCatalog = this.getJpaProject().getDefaultCatalog();
		this.defaultSchema = this.getJpaProject().getDefaultSchema();
	}
	
	public boolean resourceExists() {
		return getResourceDefaults() != null;
	}
	
	@Override
	public PersistenceUnitMetadata getParent() {
		return (PersistenceUnitMetadata) super.getParent();
	}
	
	protected XmlPersistenceUnitMetadata getResourcePersistenceUnitMetadata() {
		return getParent().getResourcePersistenceUnitMetadata();
	}
	
	protected XmlPersistenceUnitMetadata createResourcePersistenceUnitMetadata() {
		return getParent().createResourcePersistenceUnitMetadata();
	}
	
	// ********** access **********

	public AccessType getAccess() {
		return this.access;
	}

	public void setAccess(AccessType access) {
		AccessType old = this.access;
		this.access = access;
		if (access != old) {
			XmlPersistenceUnitDefaults resourceDefaults = this.getResourceDefaults();
			if (resourceDefaults == null) {
				resourceDefaults = this.buildResourceDefaults();
			}
			resourceDefaults.setAccess(AccessType.toOrmResourceModel(access));
			this.checkResourceDefaults(resourceDefaults);
			this.firePropertyChanged(ACCESS_PROPERTY, old, access);
		}
	}

	protected void setAccess_(AccessType access) {
		AccessType old = this.access;
		this.access = access;
		this.firePropertyChanged(ACCESS_PROPERTY, old, access);
	}
	

	// ********** schema container **********

	/**
	 * If we don't have a catalog (i.e. we don't even have a *default* catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.getDbCatalog(catalog) : this.getDatabase();
	}


	// ********** catalog **********

	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		if (this.attributeValueHasChanged(old, catalog)) {
			XmlPersistenceUnitDefaults resourceDefaults = this.getResourceDefaults();
			if (resourceDefaults == null) {
				resourceDefaults = this.buildResourceDefaults();
			}
			resourceDefaults.setCatalog(catalog);
			this.checkResourceDefaults(resourceDefaults);
			this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
		}
	}
	
	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}
	
	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		if (catalog == null) {
			return null;  // not even a default catalog (i.e. database probably does not support catalogs)
		}
		return this.getDbCatalog(catalog);
	}


	// ********** schema **********

	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		if (this.attributeValueHasChanged(old, schema)) {
			XmlPersistenceUnitDefaults resourceDefaults = this.getResourceDefaults();
			if (resourceDefaults == null) {
				resourceDefaults = this.buildResourceDefaults();
			}
			resourceDefaults.setSchema(schema);
			this.checkResourceDefaults(resourceDefaults);
			this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
		}
	}
	
	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}
	
	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}


	// ********** cascadePersist **********

	public boolean isCascadePersist() {
		return this.cascadePersist;
	}

	public void setCascadePersist(boolean cascadePersist) {
		boolean old = this.cascadePersist;
		this.cascadePersist = cascadePersist;
		if (cascadePersist != old) {
			XmlPersistenceUnitDefaults resourceDefaults = this.getResourceDefaults();
			if (resourceDefaults == null) {
				resourceDefaults = this.buildResourceDefaults();
			}
			resourceDefaults.setCascadePersist(cascadePersist);
			this.checkResourceDefaults(resourceDefaults);
			this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, cascadePersist);
		}
	}

	protected void setCascadePersist_(boolean cp) {
		boolean old = this.cascadePersist;
		this.cascadePersist = cp;
		this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, cp);
	}
	

	// ********** behavior **********

	/**
	 * build the resource defaults and the resource metadata if necessary
	 */
	protected XmlPersistenceUnitDefaults buildResourceDefaults() {
		XmlPersistenceUnitMetadata resourceMetadata = getResourcePersistenceUnitMetadata();
		if (resourceMetadata == null) {
			resourceMetadata = createResourcePersistenceUnitMetadata();
			this.xmlEntityMappings.setPersistenceUnitMetadata(resourceMetadata);
		}
		XmlPersistenceUnitDefaults resourceDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		resourceMetadata.setPersistenceUnitDefaults(resourceDefaults);
		return resourceDefaults;
	}

	/**
	 * clear the resource defaults and the resource metadata if appropriate
	 */
	protected void checkResourceDefaults(XmlPersistenceUnitDefaults resourceDefaults) {
		if (resourceDefaults.isUnset()) {
			XmlPersistenceUnitMetadata metadata = this.xmlEntityMappings.getPersistenceUnitMetadata();
			metadata.setPersistenceUnitDefaults(null);
			if (metadata.isUnset()) {
				this.xmlEntityMappings.setPersistenceUnitMetadata(null);
			}
		}
	}

	public void update() {
		XmlPersistenceUnitDefaults resourceDefaults = this.getResourceDefaults();
		if (resourceDefaults == null) {
			this.setAccess_(null);
			this.setSpecifiedCatalog_(null);
			this.setSpecifiedSchema_(null);
			this.setCascadePersist_(false);
		} else {
			this.setAccess_(AccessType.fromOrmResourceModel(resourceDefaults.getAccess()));
			this.setSpecifiedCatalog_(resourceDefaults.getCatalog());
			this.setSpecifiedSchema_(resourceDefaults.getSchema());
			this.setCascadePersist_(resourceDefaults.isCascadePersist());
		}
		this.setDefaultCatalog(this.getJpaProject().getDefaultCatalog());
		this.setDefaultSchema(this.getJpaProject().getDefaultSchema());
	}

	public TextRange getValidationTextRange() {
		XmlPersistenceUnitDefaults resourceDefaults = this.getResourceDefaults();
		return (resourceDefaults != null) ? resourceDefaults.getValidationTextRange() : this.xmlEntityMappings.getValidationTextRange();
	}

	protected XmlPersistenceUnitDefaults getResourceDefaults() {
		XmlPersistenceUnitMetadata metadata = getResourcePersistenceUnitMetadata();
		return (metadata == null) ? null : metadata.getPersistenceUnitDefaults();
	}

}
