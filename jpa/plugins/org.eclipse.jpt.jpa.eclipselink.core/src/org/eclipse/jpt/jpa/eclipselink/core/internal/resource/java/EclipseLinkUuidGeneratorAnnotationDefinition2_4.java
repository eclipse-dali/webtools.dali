/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.EclipseLinkBinaryUuidGeneratorAnnotation2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.EclipseLinkSourceUuidGeneratorAnnotation2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.UuidGeneratorAnnotation2_4;

public class EclipseLinkUuidGeneratorAnnotationDefinition2_4
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new EclipseLinkUuidGeneratorAnnotationDefinition2_4();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	protected EclipseLinkUuidGeneratorAnnotationDefinition2_4() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new EclipseLinkSourceUuidGeneratorAnnotation2_4(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new EclipseLinkBinaryUuidGeneratorAnnotation2_4(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return UuidGeneratorAnnotation2_4.ANNOTATION_NAME;
	}
}
