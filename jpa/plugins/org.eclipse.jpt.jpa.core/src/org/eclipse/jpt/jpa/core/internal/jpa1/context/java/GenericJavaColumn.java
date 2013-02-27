/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaBaseColumn;
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;

/**
 * Java column
 * <p>
 * Note: The <code>Column</code> annotation is one of only 2 annotations that
 * can be nested outside of an array (i.e. in an <code>AttributeOverride</code>
 * annotation); the other is {@link GenericJavaJoinTable JoinTable}.
 */
public class GenericJavaColumn
	extends AbstractJavaBaseColumn<CompleteColumnAnnotation, JavaSpecifiedColumn.Owner>
	implements JavaSpecifiedColumn
{
	protected Integer specifiedLength;
	protected int defaultLength;

	protected Integer specifiedPrecision;
	protected int defaultPrecision;

	protected Integer specifiedScale;
	protected int defaultScale;


	public GenericJavaColumn(JpaContextModel parent, JavaSpecifiedColumn.Owner owner) {
		super(parent, owner);
		//build defaults during construction for performance
		this.defaultLength = this.buildDefaultLength();
		this.defaultPrecision = this.buildDefaultPrecision();
		this.defaultScale = this.buildDefaultScale();
	}

	@Override
	protected void initialize(CompleteColumnAnnotation columnAnnotation) {
		super.initialize(columnAnnotation);
		this.specifiedLength = this.buildSpecifiedLength(columnAnnotation);
		this.specifiedPrecision = this.buildSpecifiedPrecision(columnAnnotation);
		this.specifiedScale = this.buildSpecifiedScale(columnAnnotation);
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(CompleteColumnAnnotation columnAnnotation) {
		super.synchronizeWithResourceModel(columnAnnotation);
		this.setSpecifiedLength_(this.buildSpecifiedLength(columnAnnotation));
		this.setSpecifiedPrecision_(this.buildSpecifiedPrecision(columnAnnotation));
		this.setSpecifiedScale_(this.buildSpecifiedScale(columnAnnotation));
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultLength(this.buildDefaultLength());
		this.setDefaultPrecision(this.buildDefaultPrecision());
		this.setDefaultScale(this.buildDefaultScale());
	}


	// ********** column annotation **********

	@Override
	public CompleteColumnAnnotation getColumnAnnotation() {
		return this.owner.getColumnAnnotation();
	}

	@Override
	protected void removeColumnAnnotation() {
		this.owner.removeColumnAnnotation();
	}


	// ********** length **********

	public int getLength() {
		return (this.specifiedLength != null) ? this.specifiedLength.intValue() : this.defaultLength;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer length) {
		if (this.valuesAreDifferent(this.specifiedLength, length)) {
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

	protected Integer buildSpecifiedLength(CompleteColumnAnnotation columnAnnotation) {
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
		return DEFAULT_LENGTH;
	}


	// ********** precision **********

	public int getPrecision() {
		return (this.specifiedPrecision != null) ? this.specifiedPrecision.intValue() : this.defaultPrecision;
	}

	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(Integer precision) {
		if (this.valuesAreDifferent(this.specifiedPrecision, precision)) {
			this.getColumnAnnotation().setPrecision(precision);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedPrecision_(precision);
		}
	}

	protected void setSpecifiedPrecision_(Integer precision) {
		Integer old = this.specifiedPrecision;
		this.specifiedPrecision = precision;
		this.firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, old, precision);
	}

	protected Integer buildSpecifiedPrecision(CompleteColumnAnnotation columnAnnotation) {
		return columnAnnotation.getPrecision();
	}

	public int getDefaultPrecision() {
		return this.defaultPrecision;
	}

	protected void setDefaultPrecision(int precision) {
		int old = this.defaultPrecision;
		this.defaultPrecision = precision;
		this.firePropertyChanged(DEFAULT_PRECISION_PROPERTY, old, precision);
	}

	protected int buildDefaultPrecision() {
		return DEFAULT_PRECISION;
	}


	// ********** scale **********

	public int getScale() {
		return (this.specifiedScale != null) ? this.specifiedScale.intValue() : this.defaultScale;
	}

	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(Integer scale) {
		if (this.valuesAreDifferent(this.specifiedScale, scale)) {
			this.getColumnAnnotation().setScale(scale);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedScale_(scale);
		}
	}

	protected void setSpecifiedScale_(Integer scale) {
		Integer old = this.specifiedScale;
		this.specifiedScale = scale;
		this.firePropertyChanged(SPECIFIED_SCALE_PROPERTY, old, scale);
	}

	protected Integer buildSpecifiedScale(CompleteColumnAnnotation columnAnnotation) {
		return columnAnnotation.getScale();
	}

	public int getDefaultScale() {
		return this.defaultScale;
	}

	protected void setDefaultScale(int scale) {
		int old = this.defaultScale;
		this.defaultScale = scale;
		this.firePropertyChanged(DEFAULT_SCALE_PROPERTY, old, scale);
	}

	protected int buildDefaultScale() {
		return DEFAULT_SCALE;
	}


	// ********** misc **********

	public void initializeFrom(ReadOnlyColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedLength(oldColumn.getSpecifiedLength());
		this.setSpecifiedPrecision(oldColumn.getSpecifiedPrecision());
		this.setSpecifiedScale(oldColumn.getSpecifiedScale());
	}

	public void initializeFromVirtual(ReadOnlyColumn virtualColumn) {
		super.initializeFromVirtual(virtualColumn);
		// ignore other settings?
	}
}
