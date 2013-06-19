/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.DbGenerator;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.java.JavaDbGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.resource.java.DbGeneratorAnnotation;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * Java sequence or table generator
 */
public abstract class AbstractJavaDbGenerator<A extends DbGeneratorAnnotation>
	extends AbstractJavaGenerator<JavaGeneratorContainer, A>
	implements JavaDbGenerator
{

	protected Integer specifiedInitialValue;
	protected int defaultInitialValue;

	protected Integer specifiedAllocationSize;
	protected int defaultAllocationSize;


	protected AbstractJavaDbGenerator(JavaGeneratorContainer parent, A generatorAnnotation) {
		super(parent, generatorAnnotation);
		this.specifiedInitialValue = generatorAnnotation.getInitialValue();
		this.specifiedAllocationSize = generatorAnnotation.getAllocationSize();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedInitialValue_(this.generatorAnnotation.getInitialValue());
		this.setSpecifiedAllocationSize_(this.generatorAnnotation.getAllocationSize());
	}

	@Override
	public void update() {
		super.update();
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

	public void setSpecifiedInitialValue(Integer value) {
		this.generatorAnnotation.setInitialValue(value);
		this.setSpecifiedInitialValue_(value);
	}

	protected void setSpecifiedInitialValue_(Integer value) {
		Integer old = this.specifiedInitialValue;
		this.specifiedInitialValue = value;
		this.firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, old, value);
	}

	public int getDefaultInitialValue() {
		return this.defaultInitialValue;
	}

	protected void setDefaultInitialValue(int value) {
		int old = this.defaultInitialValue;
		this.defaultInitialValue = value;
		this.firePropertyChanged(DEFAULT_INITIAL_VALUE_PROPERTY, old, value);
	}

	protected abstract int buildDefaultInitialValue();


	// ********** allocation size **********

	public int getAllocationSize() {
		return (this.specifiedAllocationSize != null) ? this.specifiedAllocationSize.intValue() : this.defaultAllocationSize;
	}

	public Integer getSpecifiedAllocationSize() {
		return this.specifiedAllocationSize;
	}

	public void setSpecifiedAllocationSize(Integer size) {
		this.generatorAnnotation.setAllocationSize(size);
		this.setSpecifiedAllocationSize_(size);
	}

	protected void setSpecifiedAllocationSize_(Integer size) {
		Integer old = this.specifiedAllocationSize;
		this.specifiedAllocationSize = size;
		this.firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, old, size);
	}

	public int getDefaultAllocationSize() {
		return this.defaultAllocationSize;
	}

	protected void setDefaultAllocationSize(int size) {
		int old = this.defaultAllocationSize;
		this.defaultAllocationSize = size;
		this.firePropertyChanged(DEFAULT_ALLOCATION_SIZE_PROPERTY, old, size);
	}

	protected int buildDefaultAllocationSize() {
		return DEFAULT_ALLOCATION_SIZE;
	}

	// ********** validation **********

	@Override
	protected boolean isEquivalentTo_(Generator other) {
		return super.isEquivalentTo_(other) &&
				this.isEquivalentTo_((DbGenerator) other);
	}

	protected boolean isEquivalentTo_(DbGenerator other) {
		return ObjectTools.equals(this.specifiedAllocationSize, other.getSpecifiedAllocationSize()) &&
				ObjectTools.equals(this.specifiedInitialValue, other.getSpecifiedInitialValue());
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

}
