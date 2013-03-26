/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.binary.BinaryConverterAnnotation2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.source.SourceConverterAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ConverterAnnotation2_1;

/**
 * <code>javax.persistence.Converter</code>
 */
public final class ConverterAnnotationDefinition2_1
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new ConverterAnnotationDefinition2_1();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private ConverterAnnotationDefinition2_1() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceConverterAnnotation2_1(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryConverterAnnotation2_1(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ConverterAnnotation2_1.ANNOTATION_NAME;
	}
}
