/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmSequenceGenerator;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSequenceGenerator2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;

/**
 *  Generic2_0OrmSequenceGenerator
 */
public class Generic2_0OrmSequenceGenerator extends AbstractOrmSequenceGenerator
	implements OrmSequenceGenerator2_0
{
	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	public Generic2_0OrmSequenceGenerator(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator) {
		super(parent, resourceSequenceGenerator);
	}

	// ********** catalog **********

	@Override
	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.getResourceGenerator().setCatalog(catalog);
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
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
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}

	// ********** schema **********

	@Override
	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.getResourceGenerator().setSchema(schema);
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
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

	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	// ********** resource => context **********

	@Override
	protected void initialize(org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator xmlResource) {
		super.initialize(xmlResource);
		XmlSequenceGenerator xmlResource2_0 = (XmlSequenceGenerator) xmlResource;

		this.defaultCatalog = this.buildDefaultCatalog();
		this.specifiedCatalog = xmlResource2_0.getCatalog();
		this.defaultSchema = this.buildDefaultSchema();
		this.specifiedSchema = xmlResource2_0.getSchema();
	}

	@Override
	public void update(org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator xmlResource) {
		super.update(xmlResource);
		XmlSequenceGenerator xmlResource2_0 = (XmlSequenceGenerator) xmlResource;

		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setSpecifiedCatalog_(xmlResource2_0.getCatalog());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSpecifiedSchema_(xmlResource2_0.getSchema());
	}

	@Override
	protected XmlSequenceGenerator getResourceGenerator() {
		return (XmlSequenceGenerator) super.getResourceGenerator();
	}

}
