/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.NamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.VirtualNamedDiscriminatorColumn;

public abstract class AbstractJavaVirtualNamedDiscriminatorColumn<PA extends NamedDiscriminatorColumn.ParentAdapter, C extends NamedDiscriminatorColumn>
	extends AbstractJavaVirtualNamedColumn<PA, C>
	implements VirtualNamedDiscriminatorColumn
{
	protected Integer specifiedLength;
	protected int defaultLength;

	protected DiscriminatorType specifiedDiscriminatorType;
	protected DiscriminatorType defaultDiscriminatorType;

	protected AbstractJavaVirtualNamedDiscriminatorColumn(PA parentAdapter) {
		super(parentAdapter);
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setSpecifiedDiscriminatorType(this.buildSpecifiedDiscriminatorType());
		this.setDefaultDiscriminatorType(this.buildDefaultDiscriminatorType());

		this.setSpecifiedLength(this.buildSpecifiedLength());
		this.setDefaultLength(this.buildDefaultLength());
	}


	// ********** discriminator type **********

	public DiscriminatorType getDiscriminatorType() {
		return (this.specifiedDiscriminatorType != null) ? this.specifiedDiscriminatorType : this.defaultDiscriminatorType;
	}

	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}

	protected void setSpecifiedDiscriminatorType(DiscriminatorType type) {
		DiscriminatorType old = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = type;
		this.firePropertyChanged(SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, old, type);
	}

	protected DiscriminatorType buildSpecifiedDiscriminatorType() {
		return this.getOverriddenColumn().getSpecifiedDiscriminatorType();
	}

	public DiscriminatorType getDefaultDiscriminatorType() {
		return this.defaultDiscriminatorType;
	}

	protected void setDefaultDiscriminatorType(DiscriminatorType type) {
		DiscriminatorType old = this.defaultDiscriminatorType;
		this.defaultDiscriminatorType = type;
		this.firePropertyChanged(DEFAULT_LENGTH_PROPERTY, old, type);
	}

	protected DiscriminatorType buildDefaultDiscriminatorType() {
		return this.parentAdapter.getDefaultDiscriminatorType();
	}


	// ********** length **********

	public int getLength() {
		return (this.specifiedLength != null) ? this.specifiedLength.intValue() : this.defaultLength;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	protected void setSpecifiedLength(Integer length) {
		Integer old = this.specifiedLength;
		this.specifiedLength = length;
		this.firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, old, length);
	}

	protected Integer buildSpecifiedLength() {
		return this.getOverriddenColumn().getSpecifiedLength();
	}

	public int getDefaultLength() {
		return this.defaultLength;
	}

	protected void setDefaultLength(int length) {
		int old = this.defaultLength;
		this.defaultLength = length;
		this.firePropertyChanged(DEFAULT_LENGTH_PROPERTY, old, length);
	}

	protected int buildDefaultLength() {
		return this.parentAdapter.getDefaultLength();
	}
}
