/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.EclipseLinkBinaryMutableAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.EclipseLinkSourceMutableAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MutableAnnotation;

/**
 * org.eclipse.persistence.annotations.Mutable
 */
public class EclipseLinkMutableAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new EclipseLinkMutableAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkMutableAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new EclipseLinkSourceMutableAnnotation(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new EclipseLinkBinaryMutableAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return MutableAnnotation.ANNOTATION_NAME;
	}
}
