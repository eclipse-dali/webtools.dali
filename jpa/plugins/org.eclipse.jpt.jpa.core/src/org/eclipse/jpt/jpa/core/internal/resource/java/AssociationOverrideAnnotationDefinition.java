/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.binary.BinaryAssociationOverrideAnnotation1_0;
import org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.source.SourceAssociationOverrideAnnotation1_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * javax.persistence.AssociationOverride
 */
public final class AssociationOverrideAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new AssociationOverrideAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private AssociationOverrideAnnotationDefinition() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceAssociationOverrideAnnotation1_0.buildSourceAssociationOverrideAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryAssociationOverrideAnnotation1_0(parent, jdtAnnotation);
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
