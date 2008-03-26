/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.java.JavaGenerator;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlGenerator;
import org.eclipse.jpt.core.utility.TextRange;


public abstract class AbstractOrmGenerator<T extends XmlGenerator> extends AbstractOrmJpaContextNode 
	implements OrmGenerator
{

	protected String name;

	protected Integer specifiedInitialValue;
	protected Integer defaultInitialValue;

	protected Integer specifiedAllocationSize;
	protected Integer defaultAllocationSize;

	protected T generatorResource;

	protected AbstractOrmGenerator(OrmJpaContextNode parent) {
		super(parent);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		generatorResource().setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public Integer getInitialValue() {
		return (this.getSpecifiedInitialValue() == null) ? this.getDefaultInitialValue() : this.getSpecifiedInitialValue();
	}

	public Integer getSpecifiedInitialValue() {
		return this.specifiedInitialValue;
	}

	public void setSpecifiedInitialValue(Integer newSpecifiedInitialValue) {
		Integer oldSpecifiedInitialValue = this.specifiedInitialValue;
		this.specifiedInitialValue = newSpecifiedInitialValue;
		generatorResource().setInitialValue(newSpecifiedInitialValue);
		firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, oldSpecifiedInitialValue, newSpecifiedInitialValue);
	}
	
	protected void setSpecifiedInitialValue_(Integer newSpecifiedInitialValue) {
		Integer oldSpecifiedInitialValue = this.specifiedInitialValue;
		this.specifiedInitialValue = newSpecifiedInitialValue;
		firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, oldSpecifiedInitialValue, newSpecifiedInitialValue);
	}
	
	public Integer getDefaultInitialValue() {
		return this.defaultInitialValue;
	}
	
	protected void setDefaultInitialValue(Integer newDefaultInitialValue) {
		Integer oldSpecifiedInitialValue = this.defaultInitialValue;
		this.defaultInitialValue = newDefaultInitialValue;
		firePropertyChanged(DEFAULT_INITIAL_VALUE_PROPERTY, oldSpecifiedInitialValue, newDefaultInitialValue);
	}
	
	public Integer getAllocationSize() {
		return (this.getSpecifiedAllocationSize() == null) ? this.getDefaultAllocationSize() : this.getSpecifiedAllocationSize();
	}

	public Integer getSpecifiedAllocationSize() {
		return this.specifiedAllocationSize;
	}

	public void setSpecifiedAllocationSize(Integer newSpecifiedAllocationSize) {
		Integer oldSpecifiedAllocationSize = this.specifiedAllocationSize;
		this.specifiedAllocationSize = newSpecifiedAllocationSize;
		generatorResource().setAllocationSize(newSpecifiedAllocationSize);
		firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, oldSpecifiedAllocationSize, newSpecifiedAllocationSize);
	}
	
	protected void setSpecifiedAllocationSize_(Integer newSpecifiedAllocationSize) {
		Integer oldSpecifiedAllocationSize = this.specifiedAllocationSize;
		this.specifiedAllocationSize = newSpecifiedAllocationSize;
		firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, oldSpecifiedAllocationSize, newSpecifiedAllocationSize);
	}

	public Integer getDefaultAllocationSize() {
		return this.defaultAllocationSize;
	}
	
	protected void setDefaultAllocationSize(Integer newDefaultAllocationSize) {
		Integer oldSpecifiedAllocationSize = this.defaultAllocationSize;
		this.defaultAllocationSize = newDefaultAllocationSize;
		firePropertyChanged(DEFAULT_ALLOCATION_SIZE_PROPERTY, oldSpecifiedAllocationSize, newDefaultAllocationSize);
	}

	
	public void initialize(T generatorResource) {
		this.generatorResource = generatorResource;
		this.name = this.name(generatorResource);
		this.specifiedInitialValue = this.specifiedInitialValue(generatorResource);
		this.specifiedAllocationSize = this.specifiedAllocationSize(generatorResource);
		//TODO defaults
	}
	
	public void update(T generatorResource) {
		this.generatorResource = generatorResource;
		this.setName_(this.name(generatorResource));
		this.setSpecifiedInitialValue_(this.specifiedInitialValue(generatorResource));
		this.setSpecifiedAllocationSize_(this.specifiedAllocationSize(generatorResource));
		//TODO defaults
	}
	
	public boolean overrides(Generator generator) {
		// this isn't ideal, but it will have to do until we have further adopter input
		return this.getName().equals(generator.getName()) && generator instanceof JavaGenerator;
	}

	protected T generatorResource() {
		return this.generatorResource;
	}
	
	protected String name(XmlGenerator generatorResource) {
		return generatorResource.getName();
	}
	
	protected Integer specifiedInitialValue(XmlGenerator generatorResource) {
		return generatorResource.getInitialValue();
	}
	
	protected Integer specifiedAllocationSize(XmlGenerator generatorResource) {
		return generatorResource.getAllocationSize();
	}

	public TextRange getValidationTextRange() {
		return this.generatorResource().getValidationTextRange();
	}
	
	public TextRange getNameTextRange() {
		return this.generatorResource().getNameTextRange();
	}
}
