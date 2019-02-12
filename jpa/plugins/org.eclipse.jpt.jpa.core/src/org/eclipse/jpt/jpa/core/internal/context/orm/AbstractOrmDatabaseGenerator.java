/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.DatabaseGenerator;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaDatabaseGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGenerator;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * <code>orm.xml</code> sequence or table generator
 */
public abstract class AbstractOrmDatabaseGenerator<X extends XmlGenerator>
	extends AbstractOrmGenerator<X>
	implements DatabaseGenerator
{
	protected Integer specifiedInitialValue;
	protected int defaultInitialValue;

	protected Integer specifiedAllocationSize;
	protected int defaultAllocationSize;


	protected AbstractOrmDatabaseGenerator(JpaContextModel parent, X xmlGenerator) {
		super(parent, xmlGenerator);
		this.specifiedInitialValue = xmlGenerator.getInitialValue();
		this.specifiedAllocationSize = xmlGenerator.getAllocationSize();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedInitialValue_(this.xmlGenerator.getInitialValue());
		this.setSpecifiedAllocationSize_(this.xmlGenerator.getAllocationSize());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultInitialValue(this.buildDefaultInitialValue());
		this.setDefaultAllocationSize(this.buildDefaultAllocationSize());
	}


	// ********** initial value **********

	public int getInitialValue() {
		return (this.specifiedInitialValue != null) ? this.specifiedInitialValue.intValue() : this.defaultInitialValue;
	}

	public Integer getSpecifiedInitialValue() {
		return this.specifiedInitialValue;
	}

	public void setSpecifiedInitialValue(Integer specifiedInitialValue) {
		this.setSpecifiedInitialValue_(specifiedInitialValue);
		this.xmlGenerator.setInitialValue(specifiedInitialValue);
	}

	protected void setSpecifiedInitialValue_(Integer specifiedInitialValue) {
		Integer old = this.specifiedInitialValue;
		this.specifiedInitialValue = specifiedInitialValue;
		this.firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, old, specifiedInitialValue);
	}

	public int getDefaultInitialValue() {
		return this.defaultInitialValue;
	}

	protected void setDefaultInitialValue(int defaultInitialValue) {
		int old = this.defaultInitialValue;
		this.defaultInitialValue = defaultInitialValue;
		this.firePropertyChanged(DEFAULT_INITIAL_VALUE_PROPERTY, old, defaultInitialValue);
	}

	protected abstract int buildDefaultInitialValue();


	// ********** allocation size **********

	public int getAllocationSize() {
		return (this.specifiedAllocationSize != null) ? this.specifiedAllocationSize.intValue() : this.defaultAllocationSize;
	}

	public Integer getSpecifiedAllocationSize() {
		return this.specifiedAllocationSize;
	}

	public void setSpecifiedAllocationSize(Integer specifiedAllocationSize) {
		this.setSpecifiedAllocationSize_(specifiedAllocationSize);
		this.xmlGenerator.setAllocationSize(specifiedAllocationSize);
	}

	protected void setSpecifiedAllocationSize_(Integer specifiedAllocationSize) {
		Integer old = this.specifiedAllocationSize;
		this.specifiedAllocationSize = specifiedAllocationSize;
		this.firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, old, specifiedAllocationSize);
	}

	public int getDefaultAllocationSize() {
		return this.defaultAllocationSize;
	}

	protected void setDefaultAllocationSize(int defaultAllocationSize) {
		int old = this.defaultAllocationSize;
		this.defaultAllocationSize = defaultAllocationSize;
		this.firePropertyChanged(DEFAULT_ALLOCATION_SIZE_PROPERTY, old, defaultAllocationSize);
	}

	protected int buildDefaultAllocationSize() {
		return DEFAULT_ALLOCATION_SIZE;
	}


	@Override
	protected boolean isEquivalentTo_(Generator other) {
		return super.isEquivalentTo_(other) &&
				this.isEquivalentTo_((DatabaseGenerator) other);
	}

	protected boolean isEquivalentTo_(DatabaseGenerator other) {
		return ObjectTools.equals(this.specifiedAllocationSize, other.getSpecifiedAllocationSize()) &&
				ObjectTools.equals(this.specifiedInitialValue, other.getSpecifiedInitialValue());
	}

	// ************* completion proposals *************
	/**
	 * called if the database is connected:
	 * table, schema, catalog, pkColumnName, valueColumnName
	 */
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
		return this.xmlGenerator.schemaTouches(pos);
	}

	protected Iterable<String> getCandidateSchemata() {
		SchemaContainer schemaContainer = this.getDbSchemaContainer();
		return (schemaContainer != null) ? schemaContainer.getSortedSchemaIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** content assist: catalog

	protected boolean catalogTouches(int pos) {
		return this.xmlGenerator.catalogTouches(pos);
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** database stuff **********

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
	}

	protected abstract String getSchema();

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}

	protected abstract String getCatalog();


	// ********** metadata conversion **********

	public void convertFrom(JavaDatabaseGenerator javaGenerator) {
		super.convertFrom(javaGenerator);
		this.setSpecifiedInitialValue(javaGenerator.getSpecifiedInitialValue());
		this.setSpecifiedAllocationSize(javaGenerator.getSpecifiedAllocationSize());
	}
}
