/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.binary.BinaryNamedQuery1_0Annotation;
import org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.source.SourceNamedQuery1_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * javax.persistence.NamedQuery
 */
public final class NamedQueryAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new NamedQueryAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NamedQueryAnnotationDefinition() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceNamedQuery1_0Annotation.buildSourceNamedQueryAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryNamedQuery1_0Annotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return JPA.NAMED_QUERY;
	}

	public String getContainerAnnotationName() {
		return JPA.NAMED_QUERIES;
	}

	public String getElementName() {
		return JPA.NAMED_QUERIES__VALUE;
	}
}
