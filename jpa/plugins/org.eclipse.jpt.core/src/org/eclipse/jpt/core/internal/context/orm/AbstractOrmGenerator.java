/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaGenerator;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlGenerator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * 
 */
public abstract class AbstractOrmGenerator<T extends XmlGenerator>
	extends AbstractXmlContextNode 
	implements OrmGenerator
{

	protected String name;

	protected Integer specifiedInitialValue;
	protected Integer defaultInitialValue;

	protected Integer specifiedAllocationSize;
	protected Integer defaultAllocationSize;

	protected T resourceGenerator;


	protected AbstractOrmGenerator(XmlContextNode parent) {
		super(parent);
	}

	protected T getResourceGenerator() {
		return this.resourceGenerator;
	}
	

	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.getResourceGenerator().setName(name);
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}
	
	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** initial value **********

	public Integer getInitialValue() {
		return (this.specifiedInitialValue != null) ? this.specifiedInitialValue : this.defaultInitialValue;
	}

	public Integer getSpecifiedInitialValue() {
		return this.specifiedInitialValue;
	}

	public void setSpecifiedInitialValue(Integer specifiedInitialValue) {
		Integer old = this.specifiedInitialValue;
		this.specifiedInitialValue = specifiedInitialValue;
		this.getResourceGenerator().setInitialValue(specifiedInitialValue);
		this.firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, old, specifiedInitialValue);
	}
	
	protected void setSpecifiedInitialValue_(Integer specifiedInitialValue) {
		Integer old = this.specifiedInitialValue;
		this.specifiedInitialValue = specifiedInitialValue;
		this.firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, old, specifiedInitialValue);
	}
	
	public Integer getDefaultInitialValue() {
		return this.defaultInitialValue;
	}
	
	protected void setDefaultInitialValue(Integer defaultInitialValue) {
		Integer old = this.defaultInitialValue;
		this.defaultInitialValue = defaultInitialValue;
		this.firePropertyChanged(DEFAULT_INITIAL_VALUE_PROPERTY, old, defaultInitialValue);
	}
	

	// ********** allocation size **********

	public Integer getAllocationSize() {
		return (this.specifiedAllocationSize != null) ? this.specifiedAllocationSize : this.defaultAllocationSize;
	}

	public Integer getSpecifiedAllocationSize() {
		return this.specifiedAllocationSize;
	}

	public void setSpecifiedAllocationSize(Integer specifiedAllocationSize) {
		Integer old = this.specifiedAllocationSize;
		this.specifiedAllocationSize = specifiedAllocationSize;
		this.getResourceGenerator().setAllocationSize(specifiedAllocationSize);
		this.firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, old, specifiedAllocationSize);
	}
	
	protected void setSpecifiedAllocationSize_(Integer specifiedAllocationSize) {
		Integer old = this.specifiedAllocationSize;
		this.specifiedAllocationSize = specifiedAllocationSize;
		this.firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, old, specifiedAllocationSize);
	}

	public Integer getDefaultAllocationSize() {
		return this.defaultAllocationSize;
	}
	
	protected void setDefaultAllocationSize(Integer defaultAllocationSize) {
		Integer old = this.defaultAllocationSize;
		this.defaultAllocationSize = defaultAllocationSize;
		this.firePropertyChanged(DEFAULT_ALLOCATION_SIZE_PROPERTY, old, defaultAllocationSize);
	}


	// ********** text ranges **********

	public TextRange getValidationTextRange() {
		TextRange validationTextRange = this.getResourceGenerator().getValidationTextRange();
		return validationTextRange != null ? validationTextRange : getParent().getValidationTextRange();
	}
	
	public TextRange getNameTextRange() {
		TextRange nameTextRange = this.getResourceGenerator().getNameTextRange();
		return nameTextRange != null ? nameTextRange : getValidationTextRange();
	}


	// ********** resource => context **********

	protected void initialize(T xmlResourceGenerator) {
		this.resourceGenerator = xmlResourceGenerator;
		this.name = xmlResourceGenerator.getName();
		this.specifiedInitialValue = xmlResourceGenerator.getInitialValue();
		this.specifiedAllocationSize = xmlResourceGenerator.getAllocationSize();
		//TODO defaults
	}
	
	protected void update(T xmlResourceGenerator) {
		this.resourceGenerator = xmlResourceGenerator;
		this.setName_(xmlResourceGenerator.getName());
		this.setSpecifiedInitialValue_(xmlResourceGenerator.getInitialValue());
		this.setSpecifiedAllocationSize_(xmlResourceGenerator.getAllocationSize());
		//TODO defaults
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
		return getResourceGenerator().isVirtual();
	}
	
	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}
	
	public boolean overrides(Generator generator) {
		if (getName() == null) {
			return false;
		}
		// this isn't ideal, but it will have to do until we have further adopter input
		return this.getName().equals(generator.getName()) && generator instanceof JavaGenerator;
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
