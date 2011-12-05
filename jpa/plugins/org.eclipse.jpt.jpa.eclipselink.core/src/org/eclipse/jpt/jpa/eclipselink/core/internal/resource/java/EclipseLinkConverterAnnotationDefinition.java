/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.BinaryEclipseLinkConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.SourceEclipseLinkConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;

/**
 * org.eclipse.persistence.annotations.Converter
 */
public class EclipseLinkConverterAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new EclipseLinkConverterAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkConverterAnnotationDefinition() {
		super();
	}


	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceEclipseLinkConverterAnnotation.buildSourceConverterAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryEclipseLinkConverterAnnotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return EclipseLink.CONVERTER;
	}

	public String getContainerAnnotationName() {
		return EclipseLink.CONVERTERS;
	}

	public String getElementName() {
		return EclipseLink.CONVERTERS__VALUE;
	}
}
