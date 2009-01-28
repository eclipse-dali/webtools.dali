/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.java.JavaGenerator;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.GeneratorAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * 
 */
public abstract class AbstractJavaGenerator
	extends AbstractJavaJpaContextNode 
	implements JavaGenerator
{
	protected String name;

	protected Integer specifiedInitialValue;
	
	protected Integer specifiedAllocationSize;

	protected GeneratorAnnotation resourceGenerator;
	

	protected AbstractJavaGenerator(JavaJpaContextNode parent) {
		super(parent);
	}

	protected GeneratorAnnotation getResourceGenerator() {
		return this.resourceGenerator;
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.resourceGenerator.setName(name);
		this.firePropertyChanged(Generator.NAME_PROPERTY, old, name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(Generator.NAME_PROPERTY, old, name);
	}


	// ********** initial value **********

	public int getInitialValue() {
		return (this.specifiedInitialValue != null) ? this.specifiedInitialValue.intValue() : this.getDefaultInitialValue();
	}

	public Integer getSpecifiedInitialValue() {
		return this.specifiedInitialValue;
	}

	public void setSpecifiedInitialValue(Integer specifiedInitialValue) {
		Integer old = this.specifiedInitialValue;
		this.specifiedInitialValue = specifiedInitialValue;
		this.resourceGenerator.setInitialValue(specifiedInitialValue);
		this.firePropertyChanged(Generator.SPECIFIED_INITIAL_VALUE_PROPERTY, old, specifiedInitialValue);
	}

	protected void setSpecifiedInitialValue_(Integer specifiedInitialValue) {
		Integer old = this.specifiedInitialValue;
		this.specifiedInitialValue = specifiedInitialValue;
		this.firePropertyChanged(Generator.SPECIFIED_INITIAL_VALUE_PROPERTY, old, specifiedInitialValue);
	}


	// ********** allocation size **********

	public int getAllocationSize() {
		return (this.specifiedAllocationSize != null) ? this.specifiedAllocationSize.intValue() : this.getDefaultAllocationSize();
	}

	public Integer getSpecifiedAllocationSize() {
		return this.specifiedAllocationSize;
	}

	public void setSpecifiedAllocationSize(Integer specifiedAllocationSize) {
		Integer old = this.specifiedAllocationSize;
		this.specifiedAllocationSize = specifiedAllocationSize;
		this.resourceGenerator.setAllocationSize(specifiedAllocationSize);
		this.firePropertyChanged(Generator.SPECIFIED_ALLOCATION_SIZE_PROPERTY, old, specifiedAllocationSize);
	}

	protected void setSpecifiedAllocationSize_(Integer specifiedAllocationSize) {
		Integer old = this.specifiedAllocationSize;
		this.specifiedAllocationSize = specifiedAllocationSize;
		this.firePropertyChanged(Generator.SPECIFIED_ALLOCATION_SIZE_PROPERTY, old, specifiedAllocationSize);
	}

	public int getDefaultAllocationSize() {
		return Generator.DEFAULT_ALLOCATION_SIZE;
	}


	// ********** text ranges **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getSelectionTextRange(astRoot);
	}

	public TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.resourceGenerator.getTextRange(astRoot);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.resourceGenerator.getNameTextRange(astRoot);
	}


	// ********** resource => context **********

	protected void initialize(GeneratorAnnotation generatorAnnotation) {
		this.resourceGenerator = generatorAnnotation;
		this.name = generatorAnnotation.getName();
		this.specifiedInitialValue =  generatorAnnotation.getInitialValue();
		this.specifiedAllocationSize = generatorAnnotation.getAllocationSize();
	}
	
	protected void update(GeneratorAnnotation generatorAnnotation) {
		this.resourceGenerator = generatorAnnotation;
		this.setName_(generatorAnnotation.getName());
		this.setSpecifiedInitialValue_(generatorAnnotation.getInitialValue());
		this.setSpecifiedAllocationSize_(generatorAnnotation.getAllocationSize());
		getPersistenceUnit().addGenerator(this);
	}
	

	// ********** database stuff **********

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a *default* catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.getDbCatalog(catalog) : this.getDatabase();
	}

	protected abstract String getSchema();

	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		if (catalog == null) {
			return null;  // not even a default catalog (i.e. database probably does not support catalogs)
		}
		return this.getDbCatalog(catalog);
	}

	protected abstract String getCatalog();


	// ********** misc **********

	public boolean isVirtual() {
		return false;
	}
	
	public boolean overrides(Generator generator) {
		// java is at the base of the tree
		return false;
	}
	
	public boolean duplicates(Generator other) {
		return (this != other)
				&& ! StringTools.stringIsEmpty(this.name)
				&& this.name.equals(other.getName())
				&& ! this.overrides(other)
				&& ! other.overrides(this);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}

}
