/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.binary.BinaryNamedStoredProcedureQuery2_1Annotation;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.source.SourceNamedStoredProcedureQuery2_1Annotation;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;

/**
 * javax.persistence.NamedStoredProcedureQuery
 */
public final class NamedStoredProcedureQuery2_1AnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new NamedStoredProcedureQuery2_1AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NamedStoredProcedureQuery2_1AnnotationDefinition() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceNamedStoredProcedureQuery2_1Annotation.buildSourceNamedStoredProcedureQuery2_1Annotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryNamedStoredProcedureQuery2_1Annotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY;
	}

	public String getContainerAnnotationName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERIES;
	}

	public String getElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERIES__VALUE;
	}
}
