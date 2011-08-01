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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryAssociationOverride2_0Annotation;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceAssociationOverride2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * javax.persistence.AssociationOverride
 */
public final class AssociationOverride2_0AnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new AssociationOverride2_0AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private AssociationOverride2_0AnnotationDefinition() {
		super();
	}


	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceAssociationOverride2_0Annotation.buildSourceAssociationOverrideAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryAssociationOverride2_0Annotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return JPA.ASSOCIATION_OVERRIDE;
	}

	public String getContainerAnnotationName() {
		return JPA.ASSOCIATION_OVERRIDES;
	}

	public String getElementName() {
		return JPA.ASSOCIATION_OVERRIDES__VALUE;
	}
}
