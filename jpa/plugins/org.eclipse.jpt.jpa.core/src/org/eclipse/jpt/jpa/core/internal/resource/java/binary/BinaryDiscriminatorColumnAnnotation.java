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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * javax.persistence.DiscriminatorColumn
 */
public final class BinaryDiscriminatorColumnAnnotation
	extends BinaryNamedColumnAnnotation
	implements DiscriminatorColumnAnnotation
{
	private DiscriminatorType discriminatorType;
	private Integer length;


	public BinaryDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.discriminatorType = this.buildDiscriminatorType();
		this.length = this.buildLength();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setDiscriminatorType_(this.buildDiscriminatorType());
		this.setLength_(this.buildLength());
	}


	// ********** BinaryNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}


	// ********** DiscriminatorColumnAnnotation implementation **********

	// ***** discriminator type
	public DiscriminatorType getDiscriminatorType() {
		return null;
	}

	public void setDiscriminatorType(DiscriminatorType discriminatorType) {
		throw new UnsupportedOperationException();
	}

	private void setDiscriminatorType_(DiscriminatorType discriminatorType) {
		DiscriminatorType old = this.discriminatorType;
		this.discriminatorType = discriminatorType;
		this.firePropertyChanged(DISCRIMINATOR_TYPE_PROPERTY, old, discriminatorType);
	}

	private DiscriminatorType buildDiscriminatorType() {
		return DiscriminatorType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE));
	}

	// ***** length
	public Integer getLength() {
		return null;
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
		return (Integer) this.getJdtMemberValue(JPA.DISCRIMINATOR_COLUMN__LENGTH);
	}

}
