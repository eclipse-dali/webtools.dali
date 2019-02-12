/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryGeneratedAnnotation2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceGeneratedAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.GeneratedAnnotation2_0;

/**
 * <code>javax.annotation.Generated</code>
 */
public final class GeneratedAnnotationDefinition2_0
	implements AnnotationDefinition
{
	// singleton
	private static final GeneratedAnnotationDefinition2_0 INSTANCE = new GeneratedAnnotationDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static GeneratedAnnotationDefinition2_0 instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GeneratedAnnotationDefinition2_0() {
		super();
	}

	public GeneratedAnnotation2_0 buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceGeneratedAnnotation2_0(parent, annotatedElement);
	}

	public GeneratedAnnotation2_0 buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public GeneratedAnnotation2_0 buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryGeneratedAnnotation2_0(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return GeneratedAnnotation2_0.ANNOTATION_NAME;
	}
}
