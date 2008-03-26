/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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


public abstract class AbstractJavaGenerator extends AbstractJavaJpaContextNode 
	implements JavaGenerator
{
	protected String name;

	protected Integer specifiedInitialValue;
	
	protected Integer specifiedAllocationSize;

	protected GeneratorAnnotation generatorResource;
	
	protected AbstractJavaGenerator(JavaJpaContextNode parent) {
		super(parent);
	}

	public void initializeFromResource(GeneratorAnnotation generatorResource) {
		this.generatorResource = generatorResource;
		this.name = this.name(generatorResource);
		this.specifiedInitialValue = this.specifiedInitialValue(generatorResource);
		this.specifiedAllocationSize = this.specifiedAllocationSize(generatorResource);
	}
	
	protected GeneratorAnnotation generatorResource() {
		return this.generatorResource;
	}
	
	protected String name(GeneratorAnnotation generatorResource) {
		return generatorResource.getName();
	}
	
	protected Integer specifiedInitialValue(GeneratorAnnotation generatorResource) {
		return generatorResource.getInitialValue();
	}
	
	protected Integer specifiedAllocationSize(GeneratorAnnotation generatorResource) {
		return generatorResource.getAllocationSize();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		generatorResource().setName(newName);
		firePropertyChanged(Generator.NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(Generator.NAME_PROPERTY, oldName, newName);
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
		firePropertyChanged(Generator.SPECIFIED_INITIAL_VALUE_PROPERTY, oldSpecifiedInitialValue, newSpecifiedInitialValue);
	}

	protected void setSpecifiedInitialValue_(Integer newSpecifiedInitialValue) {
		Integer oldSpecifiedInitialValue = this.specifiedInitialValue;
		this.specifiedInitialValue = newSpecifiedInitialValue;
		firePropertyChanged(Generator.SPECIFIED_INITIAL_VALUE_PROPERTY, oldSpecifiedInitialValue, newSpecifiedInitialValue);
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
		firePropertyChanged(Generator.SPECIFIED_ALLOCATION_SIZE_PROPERTY, oldSpecifiedAllocationSize, newSpecifiedAllocationSize);
	}

	protected void setSpecifiedAllocationSize_(Integer newSpecifiedAllocationSize) {
		Integer oldSpecifiedAllocationSize = this.specifiedAllocationSize;
		this.specifiedAllocationSize = newSpecifiedAllocationSize;
		firePropertyChanged(Generator.SPECIFIED_ALLOCATION_SIZE_PROPERTY, oldSpecifiedAllocationSize, newSpecifiedAllocationSize);
	}

	public Integer getDefaultAllocationSize() {
		return Generator.DEFAULT_ALLOCATION_SIZE;
	}

	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.selectionTextRange(astRoot);
	}

	public TextRange selectionTextRange(CompilationUnit astRoot) {
		return this.generatorResource.textRange(astRoot);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.generatorResource.nameTextRange(astRoot);
	}

	protected void update(GeneratorAnnotation generatorResource) {
		this.generatorResource = generatorResource;
		this.setName_(this.name(generatorResource));
		this.setSpecifiedInitialValue_(this.specifiedInitialValue(generatorResource));
		this.setSpecifiedAllocationSize_(this.specifiedAllocationSize(generatorResource));
	}
	
	public boolean overrides(Generator generator) {
		// java is at the base of the tree
		return false;
	}
}
