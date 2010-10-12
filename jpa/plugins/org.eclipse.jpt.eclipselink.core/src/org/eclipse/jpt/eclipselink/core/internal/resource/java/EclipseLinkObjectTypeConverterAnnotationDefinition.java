/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.binary.BinaryEclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.source.SourceEclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.ObjectTypeConverter
 */
public class EclipseLinkObjectTypeConverterAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new EclipseLinkObjectTypeConverterAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkObjectTypeConverterAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceEclipseLinkObjectTypeConverterAnnotation((JavaResourcePersistentMember) parent, (Member) annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryEclipseLinkObjectTypeConverterAnnotation((JavaResourcePersistentMember) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME;
	}

}
