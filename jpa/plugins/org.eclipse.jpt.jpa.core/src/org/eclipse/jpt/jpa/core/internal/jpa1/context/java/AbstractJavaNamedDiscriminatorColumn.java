/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0, which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.NamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;

/**
 * Java named discriminator column
 */
public abstract class AbstractJavaNamedDiscriminatorColumn<PA extends NamedDiscriminatorColumn.ParentAdapter, A extends DiscriminatorColumnAnnotation>
	extends AbstractJavaNamedColumn<PA, A>
	implements SpecifiedNamedDiscriminatorColumn
{
	protected DiscriminatorType specifiedDiscriminatorType;
	protected DiscriminatorType defaultDiscriminatorType;

	protected Integer specifiedLength;
	protected int defaultLength = DEFAULT_LENGTH;


	protected AbstractJavaNamedDiscriminatorColumn(PA parentAdapter) {
		this(parentAdapter, null);
	}

	protected AbstractJavaNamedDiscriminatorColumn(PA parentAdapter, A columnAnnotation) {
		super(parentAdapter, columnAnnotation);
	}

	@Override
	protected void initialize(A columnAnnotation) {
		super.initialize(columnAnnotation);
		this.specifiedDiscriminatorType = this.buildSpecifiedDiscriminatorType(columnAnnotation);
		this.specifiedLength = this.buildSpecifiedLength(columnAnnotation);
	}
	// ********** synchronize/update **********

	@Override
	protected void synchronizeWithResourceModel(A columnAnnotation) {
		super.synchronizeWithResourceModel(columnAnnotation);
		this.setSpecifiedDiscriminatorType_(this.buildSpecifiedDiscriminatorType(columnAnnotation));
		this.setSpecifiedLength_(this.buildSpecifiedLength(columnAnnotation));
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultDiscriminatorType(this.buildDefaultDiscriminatorType());
		this.setDefaultLength(this.buildDefaultLength());
	}


	// ********** column annotation **********

	@Override
	public abstract A getColumnAnnotation();


	// ********** discriminator type **********

	public DiscriminatorType getDiscriminatorType() {
		return (this.specifiedDiscriminatorType != null) ? this.specifiedDiscriminatorType : this.defaultDiscriminatorType;
	}

	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}

	public void setSpecifiedDiscriminatorType(DiscriminatorType discriminatorType) {
		if (ObjectTools.notEquals(this.specifiedDiscriminatorType, discriminatorType)) {
			this.getColumnAnnotation().setDiscriminatorType(DiscriminatorType.toJavaResourceModel(discriminatorType));
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedDiscriminatorType_(discriminatorType);
		}
	}

	protected void setSpecifiedDiscriminatorType_(DiscriminatorType discriminatorType) {
		DiscriminatorType old = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = discriminatorType;
		this.firePropertyChanged(SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, old, discriminatorType);
	}

	protected DiscriminatorType buildSpecifiedDiscriminatorType(A columnAnnotation) {
		return DiscriminatorType.fromJavaResourceModel(columnAnnotation.getDiscriminatorType());
	}

	public DiscriminatorType getDefaultDiscriminatorType() {
		return this.defaultDiscriminatorType;
	}

	protected void setDefaultDiscriminatorType(DiscriminatorType discriminatorType) {
		DiscriminatorType old = this.defaultDiscriminatorType;
		this.defaultDiscriminatorType = discriminatorType;
		this.firePropertyChanged(DEFAULT_DISCRIMINATOR_TYPE_PROPERTY, old, discriminatorType);
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

	public void setSpecifiedLength(Integer length) {
		if (ObjectTools.notEquals(this.specifiedLength, length)) {
			this.getColumnAnnotation().setLength(length);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedLength_(length);
		}
	}

	protected void setSpecifiedLength_(Integer length) {
		Integer old = this.specifiedLength;
		this.specifiedLength = length;
		this.firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, old, length);
	}

	protected Integer buildSpecifiedLength(A columnAnnotation) {
		return columnAnnotation.getLength();
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
