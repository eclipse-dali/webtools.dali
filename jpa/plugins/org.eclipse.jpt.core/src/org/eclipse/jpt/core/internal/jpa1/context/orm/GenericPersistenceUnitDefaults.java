/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmPersistenceUnitDefaults2_0;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-defaults</code> element
 */
public class GenericPersistenceUnitDefaults
	extends AbstractOrmXmlContextNode
	implements OrmPersistenceUnitDefaults2_0
{
	protected AccessType access;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected boolean cascadePersist;
	protected boolean delimitedIdentifiers;


	// ********** constructor/initialization **********

	public GenericPersistenceUnitDefaults(PersistenceUnitMetadata parent) {
		super(parent);
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		if (xmlDefaults != null) {
			this.access = AccessType.fromOrmResourceModel(xmlDefaults.getAccess());
			this.specifiedCatalog = xmlDefaults.getCatalog();
			this.specifiedSchema = xmlDefaults.getSchema();
			this.cascadePersist = xmlDefaults.isCascadePersist();
			this.delimitedIdentifiers = xmlDefaults.isDelimitedIdentifiers();
		}
		this.defaultCatalog = this.getJpaProject().getDefaultCatalog();
		this.defaultSchema = this.getJpaProject().getDefaultSchema();
	}

	public boolean resourceExists() {
		return this.getXmlDefaults() != null;
	}

	@Override
	public PersistenceUnitMetadata getParent() {
		return (PersistenceUnitMetadata) super.getParent();
	}

	protected XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadata() {
		return this.getParent().getXmlPersistenceUnitMetadata();
	}

	protected XmlEntityMappings getXmlEntityMappings() {
		return this.getParent().getXmlEntityMappings();
	}

	// ********** access **********

	public AccessType getAccess() {
		return this.access;
	}

	public void setAccess(AccessType access) {
		AccessType old = this.access;
		this.access = access;
		if (access != old) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			xmlDefaults.setAccess(AccessType.toOrmResourceModel(access));
			this.checkXmlDefaults(xmlDefaults);
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
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
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
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			xmlDefaults.setCatalog(catalog);
			this.checkXmlDefaults(xmlDefaults);
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

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) ? null : this.getDbCatalog(catalog);
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
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			xmlDefaults.setSchema(schema);
			this.checkXmlDefaults(xmlDefaults);
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


	// ********** cascade persist **********

	public boolean isCascadePersist() {
		return this.cascadePersist;
	}

	public void setCascadePersist(boolean cascadePersist) {
		boolean old = this.cascadePersist;
		this.cascadePersist = cascadePersist;
		if (cascadePersist != old) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			xmlDefaults.setCascadePersist(cascadePersist);
			this.checkXmlDefaults(xmlDefaults);
			this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, cascadePersist);
		}
	}

	protected void setCascadePersist_(boolean cp) {
		boolean old = this.cascadePersist;
		this.cascadePersist = cp;
		this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, cp);
	}


	// ********** delimited identifiers **********

	public boolean isDelimitedIdentifiers() {
		return this.delimitedIdentifiers;
	}

	public void setDelimitedIdentifiers(boolean delimitedIdentifiers) {
		boolean old = this.delimitedIdentifiers;
		this.delimitedIdentifiers = delimitedIdentifiers;
		if (delimitedIdentifiers != old) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			xmlDefaults.setDelimitedIdentifiers(delimitedIdentifiers);
			this.checkXmlDefaults(xmlDefaults);
			this.firePropertyChanged(DELIMITED_IDENTIFIERS_PROPERTY, old, delimitedIdentifiers);
		}
	}

	protected void setDelimitedIdentifiers_(boolean di) {
		boolean old = this.delimitedIdentifiers;
		this.delimitedIdentifiers = di;
		this.firePropertyChanged(DELIMITED_IDENTIFIERS_PROPERTY, old, di);
	}


	// ********** behavior **********

	/**
	 * If the XML does not exist, build it before returning it
	 */
	protected XmlPersistenceUnitDefaults getXmlDefaultsForUpdate() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults != null) ? xmlDefaults : this.buildXmlDefaults();
	}

	/**
	 * build the XML defaults and the XML metadata if necessary
	 */
	protected XmlPersistenceUnitDefaults buildXmlDefaults() {
		XmlPersistenceUnitMetadata resourceMetadata = this.getXmlPersistenceUnitMetadata();
		if (resourceMetadata == null) {
			resourceMetadata = this.buildXmlPersistenceUnitMetadata();
			this.getXmlEntityMappings().setPersistenceUnitMetadata(resourceMetadata);
		}
		XmlPersistenceUnitDefaults xmlDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		resourceMetadata.setPersistenceUnitDefaults(xmlDefaults);
		return xmlDefaults;
	}

	protected XmlPersistenceUnitMetadata buildXmlPersistenceUnitMetadata() {
		return this.getParent().buildXmlPersistenceUnitMetadata();
	}

	/**
	 * clear the resource defaults and the resource metadata if appropriate
	 */
	protected void checkXmlDefaults(XmlPersistenceUnitDefaults xmlDefaults) {
		if (xmlDefaults.isUnset()) {
			XmlPersistenceUnitMetadata metadata = this.getXmlEntityMappings().getPersistenceUnitMetadata();
			metadata.setPersistenceUnitDefaults(null);
			if (metadata.isUnset()) {
				this.getXmlEntityMappings().setPersistenceUnitMetadata(null);
			}
		}
	}

	public void update() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		if (xmlDefaults == null) {
			this.setAccess_(null);
			this.setSpecifiedCatalog_(null);
			this.setSpecifiedSchema_(null);
			this.setCascadePersist_(false);
			this.setDelimitedIdentifiers_(false);
		} else {
			this.setAccess_(AccessType.fromOrmResourceModel(xmlDefaults.getAccess()));
			this.setSpecifiedCatalog_(xmlDefaults.getCatalog());
			this.setSpecifiedSchema_(xmlDefaults.getSchema());
			this.setCascadePersist_(xmlDefaults.isCascadePersist());
			this.setDelimitedIdentifiers_(xmlDefaults.isDelimitedIdentifiers());
		}
		this.setDefaultCatalog(this.getJpaProject().getDefaultCatalog());
		this.setDefaultSchema(this.getJpaProject().getDefaultSchema());
	}

	public TextRange getValidationTextRange() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults != null) ? xmlDefaults.getValidationTextRange() : this.getXmlEntityMappings().getValidationTextRange();
	}

	protected XmlPersistenceUnitDefaults getXmlDefaults() {
		XmlPersistenceUnitMetadata metadata = this.getXmlPersistenceUnitMetadata();
		return (metadata == null) ? null : metadata.getPersistenceUnitDefaults();
	}

}
