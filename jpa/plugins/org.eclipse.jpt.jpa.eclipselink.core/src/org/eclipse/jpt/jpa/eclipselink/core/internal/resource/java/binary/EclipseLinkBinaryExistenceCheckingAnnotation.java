/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceCheckingAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceType;

/**
 * <code>org.eclipse.persistence.annotations.ExistenceChecking</code>
 */
public final class EclipseLinkBinaryExistenceCheckingAnnotation
	extends BinaryAnnotation
	implements ExistenceCheckingAnnotation
{
	private ExistenceType value;


	public EclipseLinkBinaryExistenceCheckingAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}


	// ********** ExistenceCheckingAnnotation implementation **********

	// ***** value
	public ExistenceType getValue() {
		return this.value;
	}

	public void setValue(ExistenceType value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(ExistenceType value) {
		ExistenceType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private ExistenceType buildValue() {
		return ExistenceType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.EXISTENCE_CHECKING__VALUE));
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
}
