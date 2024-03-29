/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmPersistenceUnitDefaults2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-defaults</code> element
 */
public abstract class AbstractOrmPersistenceUnitDefaults
	extends AbstractOrmXmlContextModel<OrmPersistenceUnitMetadata>
	implements OrmPersistenceUnitDefaults2_0
{
	protected AccessType specifiedAccess;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected boolean cascadePersist;

	protected boolean delimitedIdentifiers;


	// ********** constructor/initialization **********

	protected AbstractOrmPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent) {
		super(parent);
		this.specifiedAccess = this.buildSpecifiedAccess();
		this.specifiedCatalog = this.buildSpecifiedCatalog();
		this.specifiedSchema = this.buildSpecifiedSchema();
		this.cascadePersist = this.buildCascadePersist();
		this.delimitedIdentifiers = this.buildDelimitedIdentifiers();

		//already available from JpaProject, so set it here, many things depend on these
		this.defaultCatalog = this.buildDefaultCatalog();
		this.defaultSchema = this.buildDefaultSchema();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.setSpecifiedCatalog_(this.buildSpecifiedCatalog());
		this.setSpecifiedSchema_(this.buildSpecifiedSchema());
		this.setCascadePersist_(this.buildCascadePersist());
		this.setDelimitedIdentifiers_(this.buildDelimitedIdentifiers());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setDefaultSchema(this.buildDefaultSchema());
	}


	// ********** access **********

	public AccessType getAccess() {
		return this.getSpecifiedAccess();
	}

	public AccessType getDefaultAccess() {
		return null;
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		if (ObjectTools.notEquals(this.specifiedAccess, access)) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			this.setSpecifiedAccess_(access);
			xmlDefaults.setAccess(AccessType.toOrmResourceModel(access));
			this.removeXmlDefaultsIfUnset();
		}
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults == null) ? null : AccessType.fromOrmResourceModel(xmlDefaults.getAccess(), this.getJpaPlatform(), this.getResourceType());
	}


	// ********** schema container **********

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
	}


	// ********** catalog **********

	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		if (ObjectTools.notEquals(this.specifiedCatalog, catalog)) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			this.setSpecifiedCatalog_(catalog);
			xmlDefaults.setCatalog(catalog);
			this.removeXmlDefaultsIfUnset();
		}
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildSpecifiedCatalog() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults == null) ? null : xmlDefaults.getCatalog();
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildDefaultCatalog() {
		return this.getJpaProject().getDefaultCatalog();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}


	// ********** schema **********

	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		if (ObjectTools.notEquals(this.specifiedSchema, schema)) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			this.setSpecifiedSchema_(schema);
			xmlDefaults.setSchema(schema);
			this.removeXmlDefaultsIfUnset();
		}
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildSpecifiedSchema() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults == null) ? null : xmlDefaults.getSchema();
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildDefaultSchema() {
		return this.getJpaProject().getDefaultSchema();
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
		if (this.cascadePersist != cascadePersist) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			this.setCascadePersist_(cascadePersist);
			xmlDefaults.setCascadePersist(cascadePersist);
			this.removeXmlDefaultsIfUnset();
		}
	}

	protected void setCascadePersist_(boolean cascadePersist) {
		boolean old = this.cascadePersist;
		this.cascadePersist = cascadePersist;
		this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, cascadePersist);
	}

	protected boolean buildCascadePersist() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults == null) ? false : xmlDefaults.isCascadePersist();
	}


	// ********** delimited identifiers **********

	public boolean isDelimitedIdentifiers() {
		return this.delimitedIdentifiers;
	}

	public void setDelimitedIdentifiers(boolean delimitedIdentifiers) {
		if (this.delimitedIdentifiers != delimitedIdentifiers) {
			XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaultsForUpdate();
			this.setDelimitedIdentifiers_(delimitedIdentifiers);
			xmlDefaults.setDelimitedIdentifiers(delimitedIdentifiers);
			this.removeXmlDefaultsIfUnset();
		}
	}

	protected void setDelimitedIdentifiers_(boolean di) {
		boolean old = this.delimitedIdentifiers;
		this.delimitedIdentifiers = di;
		this.firePropertyChanged(DELIMITED_IDENTIFIERS_PROPERTY, old, di);
	}

	protected boolean buildDelimitedIdentifiers() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults == null) ? false : xmlDefaults.isDelimitedIdentifiers();
	}


	// ********** XML defaults **********

	/**
	 * Return <code>null</code> if the XML defaults does not exist.
	 */
	protected XmlPersistenceUnitDefaults getXmlDefaults() {
		XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadata();
		return (xmlMetadata == null) ? null : xmlMetadata.getPersistenceUnitDefaults();
	}

	protected XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadata() {
		return this.getPersistenceUnitMetadata().getXmlPersistenceUnitMetadata();
	}

	/**
	 * Build the XML defaults (and XML metadata if necessary) if it does not exist.
	 */
	protected XmlPersistenceUnitDefaults getXmlDefaultsForUpdate() {
		XmlPersistenceUnitMetadata xmlMetadata = this.getXmlPersistenceUnitMetadataForUpdate();
		XmlPersistenceUnitDefaults xmlDefaults = xmlMetadata.getPersistenceUnitDefaults();
		return (xmlDefaults != null) ? xmlDefaults : this.buildXmlDefaults(xmlMetadata);
	}

	protected XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadataForUpdate() {
		return this.getPersistenceUnitMetadata().getXmlPersistenceUnitMetadataForUpdate();
	}

	protected XmlPersistenceUnitDefaults buildXmlDefaults(XmlPersistenceUnitMetadata xmlMetadata) {
		XmlPersistenceUnitDefaults xmlDefaults = this.buildXmlPersistenceUnitDefaults();
		xmlMetadata.setPersistenceUnitDefaults(xmlDefaults);
		return xmlDefaults;
	}

	protected abstract XmlPersistenceUnitDefaults buildXmlPersistenceUnitDefaults();

	/**
	 * clear the XML defaults (and the XML metadata) if appropriate
	 */
	protected void removeXmlDefaultsIfUnset() {
		if (this.getXmlDefaults().isUnset()) {
			this.getXmlPersistenceUnitMetadata().setPersistenceUnitDefaults(null);
			this.removeXmlPersistenceUnitMetadataIfUnset();
		}
	}

	protected void removeXmlPersistenceUnitMetadataIfUnset() {
		this.getPersistenceUnitMetadata().removeXmlPersistenceUnitMetadataIfUnset();
	}


	// ********** misc **********

	public OrmPersistenceUnitMetadata getPersistenceUnitMetadata() {
		return this.parent;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlTextRange();
		return (textRange != null) ? textRange : this.getPersistenceUnitMetadata().getValidationTextRange();
	}

	protected TextRange getXmlTextRange() {
		XmlPersistenceUnitDefaults xmlDefaults = this.getXmlDefaults();
		return (xmlDefaults == null) ? null : xmlDefaults.getValidationTextRange();
	}

	// ********** completion proposals *********
	
	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.schemaTouches(pos)) {
			return this.getCandidateSchemata();
		}
		if (this.catalogTouches(pos)) {
			return this.getCandidateCatalogs();
		}
		return null;
	}
	
	// ********** content assist: schema

	protected boolean schemaTouches(int pos) {
		return (this.getXmlDefaults() != null) && this.getXmlDefaults().schemaTouches(pos);
	}

	protected Iterable<String> getCandidateSchemata() {
		SchemaContainer schemaContainer = this.getDbSchemaContainer();
		return (schemaContainer != null) ? schemaContainer.getSortedSchemaIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** content assist: catalog

	protected boolean catalogTouches(int pos) {
		return (this.getXmlDefaults() != null) && this.getXmlDefaults().catalogTouches(pos);
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
	}
}
