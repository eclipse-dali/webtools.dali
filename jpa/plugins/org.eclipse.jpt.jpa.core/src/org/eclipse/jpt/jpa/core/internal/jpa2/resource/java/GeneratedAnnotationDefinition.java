/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryGeneratedAnnotation;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceGeneratedAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.GeneratedAnnotation2_0;

/**
 * javax.annotation.Generated
 */
public final class GeneratedAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final GeneratedAnnotationDefinition INSTANCE = new GeneratedAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static GeneratedAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GeneratedAnnotationDefinition() {
		super();
	}

	public GeneratedAnnotation2_0 buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceGeneratedAnnotation(parent, annotatedElement);
	}

	public GeneratedAnnotation2_0 buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public GeneratedAnnotation2_0 buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryGeneratedAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return GeneratedAnnotation2_0.ANNOTATION_NAME;
	}
}
