/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkChangeTrackingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;

/**
 * org.eclipse.persistence.annotations.ChangeTracking
 */
public final class BinaryEclipseLinkChangeTrackingAnnotation
	extends BinaryAnnotation
	implements EclipseLinkChangeTrackingAnnotation
{
	private ChangeTrackingType value;


	public BinaryEclipseLinkChangeTrackingAnnotation(JavaResourcePersistentType parent, IAnnotation jdtAnnotation) {
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
		return ChangeTrackingType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLinkJPA.CHANGE_TRACKING__VALUE));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
