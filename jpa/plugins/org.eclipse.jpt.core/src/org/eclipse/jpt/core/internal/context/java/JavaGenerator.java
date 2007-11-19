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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IGenerator;
import org.eclipse.jpt.core.internal.resource.java.Generator;


public abstract class JavaGenerator<T extends Generator> extends JavaContextModel implements IJavaGenerator<T>
{
	protected String name;

	protected int specifiedInitialValue;
	
	protected int specifiedAllocationSize;

	protected T generatorResource;
	
	protected JavaGenerator(IJavaJpaContextNode parent) {
		super(parent);
	}

	public void initializeFromResource(T generatorResource) {
		this.generatorResource = generatorResource;
		this.name = this.name(generatorResource);
		this.specifiedInitialValue = this.specifiedInitialValue(generatorResource);
		this.specifiedAllocationSize = this.specifiedAllocationSize(generatorResource);
	}
	
	protected T generatorResource() {
		return this.generatorResource;
	}
	
	protected String name(Generator generatorResource) {
		return generatorResource.getName();
	}
	
	protected int specifiedInitialValue(Generator generatorResource) {
		return generatorResource.getInitialValue();
	}
	
	protected int specifiedAllocationSize(Generator generatorResource) {
		return generatorResource.getAllocationSize();
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

	public int getInitialValue() {
		return (this.getSpecifiedInitialValue() == Generator.DEFAULT_INITIAL_VALUE) ? this.getDefaultInitialValue() : this.getSpecifiedInitialValue();
	}

	public int getSpecifiedInitialValue() {
		return this.specifiedInitialValue;
	}

	public void setSpecifiedInitialValue(int newSpecifiedInitialValue) {
		int oldSpecifiedInitialValue = this.specifiedInitialValue;
		this.specifiedInitialValue = newSpecifiedInitialValue;
		generatorResource().setInitialValue(newSpecifiedInitialValue);
		firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, oldSpecifiedInitialValue, newSpecifiedInitialValue);
	}

	public int getAllocationSize() {
		return (this.getSpecifiedAllocationSize() == -1) ? this.getDefaultAllocationSize() : this.getSpecifiedAllocationSize();
	}

	public int getSpecifiedAllocationSize() {
		return this.specifiedAllocationSize;
	}

	public void setSpecifiedAllocationSize(int newSpecifiedAllocationSize) {
		int oldSpecifiedAllocationSize = this.specifiedAllocationSize;
		this.specifiedAllocationSize = newSpecifiedAllocationSize;
		generatorResource().setAllocationSize(newSpecifiedAllocationSize);
		firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, oldSpecifiedAllocationSize, newSpecifiedAllocationSize);
	}

	public int getDefaultAllocationSize() {
		return IGenerator.DEFAULT_ALLOCATION_SIZE;
	}

	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		return this.selectionTextRange(astRoot);
	}

	public ITextRange selectionTextRange(CompilationUnit astRoot) {
		return this.generatorResource.textRange(astRoot);
	}

	public void update(T generatorResource) {
		this.generatorResource = generatorResource;
		this.setName(this.name(generatorResource));
		this.setSpecifiedInitialValue(this.specifiedInitialValue(generatorResource));
		this.setSpecifiedAllocationSize(this.specifiedAllocationSize(generatorResource));
	}

}
