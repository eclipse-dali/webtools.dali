/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.ChangeTracking</code>
 */
public final class EclipseLinkBinaryChangeTrackingAnnotation
	extends BinaryAnnotation
	implements ChangeTrackingAnnotation
{
	private ChangeTrackingType value;


	public EclipseLinkBinaryChangeTrackingAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// TODO
	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}


	// ********** ChangeTrackingAnnotation implementation **********

	// ***** value
	public ChangeTrackingType getValue() {
		return this.value;
	}

	public void setValue(ChangeTrackingType value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(ChangeTrackingType value) {
		ChangeTrackingType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private ChangeTrackingType buildValue() {
		return ChangeTrackingType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.CHANGE_TRACKING__VALUE));
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
}
