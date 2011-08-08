/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
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

public abstract class BinaryBaseDiscriminatorColumnAnnotation
	extends BinaryNamedColumnAnnotation
	implements DiscriminatorColumnAnnotation
{
	private DiscriminatorType discriminatorType;
	private Integer length;


	protected BinaryBaseDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.discriminatorType = this.buildDiscriminatorType();
		this.length = this.buildLength();
	}

	@Override
	public void update() {
		super.update();
		this.setDiscriminatorType_(this.buildDiscriminatorType());
		this.setLength_(this.buildLength());
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
		return DiscriminatorType.fromJavaAnnotationValue(this.getJdtMemberValue(this.getDiscriminatorTypeElementName()));
	}

	protected abstract String getDiscriminatorTypeElementName();

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
		return (Integer) this.getJdtMemberValue(this.getLengthElementName());
	}

	protected abstract String getLengthElementName();

}
