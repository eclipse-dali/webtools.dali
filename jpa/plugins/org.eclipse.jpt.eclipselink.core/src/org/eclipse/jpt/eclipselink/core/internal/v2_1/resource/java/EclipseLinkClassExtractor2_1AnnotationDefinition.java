/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_1.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.internal.v2_1.resource.java.binary.BinaryEclipseLinkClassExtractorAnnotation2_1;
import org.eclipse.jpt.eclipselink.core.internal.v2_1.resource.java.source.SourceEclipseLinkClassExtractorAnnotation2_1;
import org.eclipse.jpt.eclipselink.core.v2_0.resource.java.EclipseLinkClassExtractorAnnotation2_1;

/**
 * org.eclipse.persistence.annotations.ClassExtractor
 */
public class EclipseLinkClassExtractor2_1AnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new EclipseLinkClassExtractor2_1AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkClassExtractor2_1AnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceEclipseLinkClassExtractorAnnotation2_1((JavaResourcePersistentType) parent, (Type) annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryEclipseLinkClassExtractorAnnotation2_1((JavaResourcePersistentType) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return EclipseLinkClassExtractorAnnotation2_1.ANNOTATION_NAME;
	}

}
