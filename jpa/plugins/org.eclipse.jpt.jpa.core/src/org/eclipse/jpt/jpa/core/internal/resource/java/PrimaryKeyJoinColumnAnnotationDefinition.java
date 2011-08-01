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
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryPrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourcePrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * javax.persistence.PrimaryKeyJoinColumn
 */
public final class PrimaryKeyJoinColumnAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new PrimaryKeyJoinColumnAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private PrimaryKeyJoinColumnAnnotationDefinition() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourcePrimaryKeyJoinColumnAnnotation.buildSourcePrimaryKeyJoinColumnAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryPrimaryKeyJoinColumnAnnotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN;
	}

	public String getContainerAnnotationName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMNS;
	}

	public String getElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMNS__VALUE;
	}
}
