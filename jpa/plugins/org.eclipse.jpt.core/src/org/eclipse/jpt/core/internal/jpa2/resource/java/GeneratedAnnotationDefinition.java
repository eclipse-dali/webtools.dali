/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.binary.BinaryGeneratedAnnotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.source.SourceGeneratedAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.GeneratedAnnotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.annotation.Generated
 * <p>
 * This annotation definition is not really required; it's just here for a bit
 * of consistency....
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

	public GeneratedAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceGeneratedAnnotation((JavaResourcePersistentType) parent, (Type) annotatedElement);
	}

	public GeneratedAnnotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		return null;
	}

	public GeneratedAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryGeneratedAnnotation((JavaResourcePersistentType) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return GeneratedAnnotation.ANNOTATION_NAME;
	}

}
