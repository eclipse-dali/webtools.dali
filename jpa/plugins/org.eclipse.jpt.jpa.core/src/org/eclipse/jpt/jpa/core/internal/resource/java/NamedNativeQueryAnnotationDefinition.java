/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryNamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceNamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;

/**
 * javax.persistence.NamedNativeQuery
 */
public final class NamedNativeQueryAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// default singleton (javax.persistence)
	private static final NestableAnnotationDefinition INSTANCE = new NamedNativeQueryAnnotationDefinition(JPA.JAVAX_PACKAGE);

	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	public static NestableAnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new NamedNativeQueryAnnotationDefinition(jpaPackage);
	}

	private final String jpaPackage;

	private NamedNativeQueryAnnotationDefinition(String jpaPackage) {
		super();
		this.jpaPackage = jpaPackage;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		if (JPA.JAVAX_PACKAGE.equals(this.jpaPackage)) {
			return SourceNamedNativeQueryAnnotation.buildSourceNamedNativeQueryAnnotation(parent, annotatedElement, index);
		}
		return SourceNamedNativeQueryAnnotation.buildJakartaSourceNamedNativeQueryAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryNamedNativeQueryAnnotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return this.jpaPackage + NamedNativeQueryAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getContainerAnnotationName() {
		return this.jpaPackage + JPA.NAMED_NATIVE_QUERIES.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getElementName() {
		return JPA.NAMED_NATIVE_QUERIES__VALUE;
	}
}
