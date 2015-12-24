/*******************************************************************************
* Copyright (c) 2009, 2015 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGeneratorAnnotation2_0;

/**
 * JPA 2.0
 * Java sequence generator
 */
public class GenericJavaSequenceGenerator2_0
	extends AbstractJavaSequenceGenerator<SequenceGeneratorAnnotation2_0>
	implements JavaSequenceGenerator2_0
{
	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;


	public GenericJavaSequenceGenerator2_0(JavaGeneratorContainer parent, SequenceGeneratorAnnotation2_0 generatorAnnotation) {
		super(parent, generatorAnnotation);
		this.specifiedCatalog = generatorAnnotation.getCatalog();
		this.specifiedSchema = generatorAnnotation.getSchema();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedCatalog_(this.generatorAnnotation.getCatalog());
		this.setSpecifiedSchema_(this.generatorAnnotation.getSchema());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setDefaultSchema(this.buildDefaultSchema());
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
		this.generatorAnnotation.setCatalog(catalog);
		this.setSpecifiedCatalog_(catalog);
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
		this.generatorAnnotation.setSchema(schema);
		this.setSpecifiedSchema_(schema);
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

	// ********** validation **********

	@Override
	protected boolean isEquivalentTo_(SequenceGenerator other) {
		return super.isEquivalentTo_(other) &&
				ObjectTools.equals(this.specifiedSchema, ((SequenceGenerator2_0) other).getSpecifiedSchema()) &&
				ObjectTools.equals(this.specifiedCatalog, ((SequenceGenerator2_0) other).getSpecifiedCatalog());
	}
}
