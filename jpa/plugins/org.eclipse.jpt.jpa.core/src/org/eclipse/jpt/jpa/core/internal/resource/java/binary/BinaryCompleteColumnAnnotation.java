/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;

/**
 * Abstract implementation of ColumnAnnotation to be used for JPA annotations:
 * javax.persistence.Column
 * javax.persistence.MapKeyColumn
 */
public abstract class BinaryCompleteColumnAnnotation
	extends BinaryBaseColumnAnnotation
	implements CompleteColumnAnnotation
{
	protected Integer length;
	protected Integer precision;
	protected Integer scale;


	protected BinaryCompleteColumnAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.length = this.buildLength();
		this.precision = this.buildPrecision();
		this.scale = this.buildScale();
	}

	@Override
	public void update() {
		super.update();
		this.setLength_(this.buildLength());
		this.setPrecision_(this.buildPrecision());
		this.setScale_(this.buildScale());
	}


	 // ********** ColumnAnnotation implementation **********

	// ***** length
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		throw new UnsupportedOperationException();
	}

	private void setLength_(Integer length) {
		Integer old = this.length;
		this.length = length;
		this.firePropertyChanged(LENGTH_PROPERTY, old, length);
	}

	private Integer buildLength() {
		return (Integer) this.getJdtMemberValue(this.getLengthElementName());
	}

	public TextRange getLengthTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	protected abstract String getLengthElementName();

	// ***** precision
	public Integer getPrecision() {
		return this.precision;
	}

	public void setPrecision(Integer precision) {
		throw new UnsupportedOperationException();
	}

	private void setPrecision_(Integer precision) {
		Integer old = this.precision;
		this.precision = precision;
		this.firePropertyChanged(PRECISION_PROPERTY, old, precision);
	}

	private Integer buildPrecision() {
		return (Integer) this.getJdtMemberValue(this.getPrecisionElementName());
	}

	public TextRange getPrecisionTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	protected abstract String getPrecisionElementName();

	// ***** scale
	public Integer getScale() {
		return this.scale;
	}

	public void setScale(Integer scale) {
		throw new UnsupportedOperationException();
	}

	private void setScale_(Integer scale) {
		Integer old = this.scale;
		this.scale = scale;
		this.firePropertyChanged(SCALE_PROPERTY, old, scale);
	}

	private Integer buildScale() {
		return (Integer) this.getJdtMemberValue(this.getScaleElementName());
	}

	public TextRange getScaleTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	protected abstract String getScaleElementName();
}
