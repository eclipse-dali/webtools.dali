/*******************************************************************************
* Copyright (c) 2009, 2016 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;

/**
 * JPA 2.0
 * <code>orm.xml</code> sequence generator
 */
public class GenericOrmSequenceGenerator2_0
	extends AbstractOrmSequenceGenerator
	implements OrmSequenceGenerator2_0
{
	protected String specifiedSchema;
	protected String defaultSchema;
	protected String schema;

	protected String specifiedCatalog;
	protected String defaultCatalog;
	protected String catalog;


	public GenericOrmSequenceGenerator2_0(JpaContextModel parent, XmlSequenceGenerator xmlSequenceGenerator) {
		super(parent, xmlSequenceGenerator);
		this.specifiedSchema = xmlSequenceGenerator.getSchema();
		this.specifiedCatalog = xmlSequenceGenerator.getCatalog();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedSchema_(this.xmlGenerator.getSchema());
		this.setSpecifiedCatalog_(this.xmlGenerator.getCatalog());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSchema(this.buildSchema());

		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setCatalog(this.buildCatalog());
	}


	// ********** schema **********

	@Override
	public String getSchema() {
		return this.schema;
	}

	protected void setSchema(String schema) {
		String old = this.schema;
		this.firePropertyChanged(SCHEMA_PROPERTY, old, this.schema = schema);
	}

	protected String buildSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		this.setSpecifiedSchema_(schema);
		this.xmlGenerator.setSchema(schema);
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, this.specifiedSchema = schema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, this.defaultSchema = schema);
	}

	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}


	// ********** catalog **********

	@Override
	public String getCatalog() {
		return this.catalog;
	}

	protected void setCatalog(String catalog) {
		String old = this.catalog;
		this.firePropertyChanged(CATALOG_PROPERTY, old, this.catalog = catalog);
	}

	protected String buildCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		this.setSpecifiedCatalog_(catalog);
		this.xmlGenerator.setCatalog(catalog);
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, this.specifiedCatalog = catalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, this.defaultCatalog = catalog);
	}

	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** validation **********

	@Override
	protected boolean isEquivalentTo_(SequenceGenerator other) {
		return super.isEquivalentTo_(other) &&
				ObjectTools.equals(this.specifiedSchema, ((SequenceGenerator2_0) other).getSpecifiedSchema()) &&
				ObjectTools.equals(this.specifiedCatalog, ((SequenceGenerator2_0) other).getSpecifiedCatalog());
	}

	// ********** metadata conversion **********
	
	@Override
	public void convertFrom(JavaSequenceGenerator javaGenerator) {
		super.convertFrom(javaGenerator);
		this.setSpecifiedCatalog(((JavaSequenceGenerator2_0) javaGenerator).getSpecifiedCatalog());
		this.setSpecifiedSchema(((JavaSequenceGenerator2_0) javaGenerator).getSpecifiedSchema());
	}
}
