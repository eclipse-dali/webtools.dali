/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryAccessAnnotation2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceAccess2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AccessAnnotation2_0;

/**
 * <code>javax.persistence.Access</code>
 */
public final class AccessAnnotationDefinition2_0
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new AccessAnnotationDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private AccessAnnotationDefinition2_0() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceAccess2_0Annotation(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		return new NullAccessAnnotation2_0(parent);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryAccessAnnotation2_0(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return AccessAnnotation2_0.ANNOTATION_NAME;
	}
}
