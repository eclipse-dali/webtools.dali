/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.DatabaseGeneratorAnnotation;

/**
 * <ul>
 * <li>javax.persistence.SequenceGenerator
 * <li>javax.persistence.TableGenerator
 * </ul>
 */
abstract class BinaryDatabaseGeneratorAnnotation
	extends BinaryGeneratorAnnotation
	implements DatabaseGeneratorAnnotation
{
	Integer initialValue;
	Integer allocationSize;


	BinaryDatabaseGeneratorAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.initialValue = this.buildInitialValue();
		this.allocationSize = this.buildAllocationSize();
	}

	@Override
	public void update() {
		super.update();
		this.setInitialValue_(this.buildInitialValue());
		this.setAllocationSize_(this.buildAllocationSize());
	}

	// ********** DbGeneratorAnnotation implementation **********

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

	public TextRange getInitialValueTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** allocation size
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

	public TextRange getAllocationSizeTextRange() {
		throw new UnsupportedOperationException();
	}
}
