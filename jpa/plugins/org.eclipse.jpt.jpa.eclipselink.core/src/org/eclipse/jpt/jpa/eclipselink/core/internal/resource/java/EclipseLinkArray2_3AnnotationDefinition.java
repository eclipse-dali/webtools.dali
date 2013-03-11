/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.BinaryEclipseLinkArrayAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.SourceEclipseLinkArrayAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ArrayAnnotation2_3;

/**
 * org.eclipse.persistence.annotations.Array
 */
public class EclipseLinkArray2_3AnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new EclipseLinkArray2_3AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkArray2_3AnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceEclipseLinkArrayAnnotation(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryEclipseLinkArrayAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ArrayAnnotation2_3.ANNOTATION_NAME;
	}
}
