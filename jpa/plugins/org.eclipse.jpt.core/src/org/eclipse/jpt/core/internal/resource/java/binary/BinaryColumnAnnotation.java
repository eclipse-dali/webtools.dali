/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Column
 */
public final class BinaryColumnAnnotation
	extends BinaryBaseColumnAnnotation
	implements ColumnAnnotation
{
	private Integer length;
	private Integer precision;
	private Integer scale;


	public BinaryColumnAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.length = this.buildLength();
		this.precision = this.buildPrecision();
		this.scale = this.buildScale();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setLength_(this.buildLength());
		this.setPrecision_(this.buildPrecision());
		this.setScale_(this.buildScale());
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.COLUMN__COLUMN_DEFINITION;
	}


	// ********** JavaSourceBaseColumnAnnotation implementation **********

	@Override
	String getTableElementName() {
		return JPA.COLUMN__TABLE;
	}

	@Override
	String getUniqueElementName() {
		return JPA.COLUMN__UNIQUE;
	}

	@Override
	String getNullableElementName() {
		return JPA.COLUMN__NULLABLE;
	}

	@Override
	String getInsertableElementName() {
		return JPA.COLUMN__INSERTABLE;
	}

	@Override
	String getUpdatableElementName() {
		return JPA.COLUMN__UPDATABLE;
	}


	 // ********** Column implementation **********

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
		return (Integer) this.getJdtMemberValue(JPA.COLUMN__LENGTH);
	}

	public TextRange getLengthTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

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
		return (Integer) this.getJdtMemberValue(JPA.COLUMN__PRECISION);
	}

	public TextRange getPrecisionTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

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
		return (Integer) this.getJdtMemberValue(JPA.COLUMN__SCALE);
	}

	public TextRange getScaleTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
