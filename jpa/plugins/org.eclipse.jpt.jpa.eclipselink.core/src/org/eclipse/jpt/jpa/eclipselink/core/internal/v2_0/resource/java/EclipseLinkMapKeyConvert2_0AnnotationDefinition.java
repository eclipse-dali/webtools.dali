/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.resource.java.binary.BinaryEclipseLinkMapKeyConvertAnnotation2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.resource.java.source.SourceEclipseLinkMapKeyConvertAnnotation2_0;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.resource.java.EclipseLinkMapKeyConvertAnnotation2_0;

/**
 * org.eclipse.persistence.annotations.MapKeyConvert
 */
public class EclipseLinkMapKeyConvert2_0AnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new EclipseLinkMapKeyConvert2_0AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkMapKeyConvert2_0AnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceEclipseLinkMapKeyConvertAnnotation2_0(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryEclipseLinkMapKeyConvertAnnotation2_0(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return EclipseLinkMapKeyConvertAnnotation2_0.ANNOTATION_NAME;
	}
}
