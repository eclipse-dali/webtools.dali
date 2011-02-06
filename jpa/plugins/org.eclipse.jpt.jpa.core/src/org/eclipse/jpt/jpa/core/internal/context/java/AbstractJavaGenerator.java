/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.java.JavaGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.resource.java.GeneratorAnnotation;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * Java sequence or table generator
 */
public abstract class AbstractJavaGenerator<A extends GeneratorAnnotation>
	extends AbstractJavaJpaContextNode
	implements JavaGenerator
{
	protected final A generatorAnnotation;

	protected String name;

	protected Integer specifiedInitialValue;
	protected int defaultInitialValue;

	protected Integer specifiedAllocationSize;
	protected int defaultAllocationSize;


	protected AbstractJavaGenerator(JavaJpaContextNode parent, A generatorAnnotation) {
		super(parent);
		this.generatorAnnotation = generatorAnnotation;
		this.name = generatorAnnotation.getName();
		this.specifiedInitialValue =  generatorAnnotation.getInitialValue();
		this.specifiedAllocationSize = generatorAnnotation.getAllocationSize();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.generatorAnnotation.getName());
		this.setSpecifiedInitialValue_(this.generatorAnnotation.getInitialValue());
		this.setSpecifiedAllocationSize_(this.generatorAnnotation.getAllocationSize());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultInitialValue(this.buildDefaultInitialValue());
		this.setDefaultAllocationSize(this.buildDefaultAllocationSize());
		this.getPersistenceUnit().addGenerator(this);
	}
	

	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.generatorAnnotation.setName(name);
		this.setName_(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
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
	

	// ********** text ranges **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getSelectionTextRange(astRoot);
	}

	public TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.generatorAnnotation.getTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.generatorAnnotation.getNameTextRange(astRoot);
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


	// ********** JpaNamedContextNode implementation **********

	public boolean overrides(Generator other) {
		return MappingTools.nodeOverrides(this, other, PRECEDENCE_TYPE_LIST);
	}

	public boolean duplicates(Generator other) {
		return MappingTools.nodesAreDuplicates(this, other);
	}


	// ********** misc **********

	public A getGeneratorAnnotation() {
		return this.generatorAnnotation;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}
