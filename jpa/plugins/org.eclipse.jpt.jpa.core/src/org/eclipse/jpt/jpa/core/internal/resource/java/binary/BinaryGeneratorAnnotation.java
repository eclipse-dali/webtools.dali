/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.GeneratorAnnotation;

/**
 * javax.persistence.SequenceGenerator
 * javax.persistence.TableGenerator
 */
abstract class BinaryGeneratorAnnotation
	extends BinaryAnnotation
	implements GeneratorAnnotation
{
	String name;
	Integer initialValue;
	Integer allocationSize;


	BinaryGeneratorAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.initialValue = this.buildInitialValue();
		this.allocationSize = this.buildAllocationSize();
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setInitialValue_(this.buildInitialValue());
		this.setAllocationSize_(this.buildAllocationSize());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** GeneratorAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(this.getNameElementName());
	}

	abstract String getNameElementName();

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** initial value
	public Integer getInitialValue() {
		return this.initialValue;
	}

	public void setInitialValue(Integer initialValue) {
		throw new UnsupportedOperationException();
	}

	private void setInitialValue_(Integer initialValue) {
		Integer old = this.initialValue;
		this.initialValue = initialValue;
		this.firePropertyChanged(INITIAL_VALUE_PROPERTY, old, initialValue);
	}

	private Integer buildInitialValue() {
		return (Integer) this.getJdtMemberValue(this.getInitialValueElementName());
	}

	abstract String getInitialValueElementName();

	public TextRange getInitialValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** name
	public Integer getAllocationSize() {
		return this.allocationSize;
	}

	public void setAllocationSize(Integer allocationSize) {
		throw new UnsupportedOperationException();
	}

	private void setAllocationSize_(Integer allocationSize) {
		Integer old = this.allocationSize;
		this.allocationSize = allocationSize;
		this.firePropertyChanged(NAME_PROPERTY, old, allocationSize);
	}

	private Integer buildAllocationSize() {
		return (Integer) this.getJdtMemberValue(this.getAllocationSizeElementName());
	}

	abstract String getAllocationSizeElementName();

	public TextRange getAllocationSizeTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
