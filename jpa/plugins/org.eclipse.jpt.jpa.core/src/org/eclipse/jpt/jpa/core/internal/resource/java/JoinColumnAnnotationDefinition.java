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
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

/**
 * javax.persistence.JoinColumn
 */
public final class JoinColumnAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// default singleton (javax.persistence)
	private static final NestableAnnotationDefinition INSTANCE = new JoinColumnAnnotationDefinition(JPA.JAVAX_PACKAGE);

	/**
	 * Return the singleton for the default (javax) package.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Return an instance for the given JPA annotations package
	 * (either {@link JPA#JAVAX_PACKAGE} or {@link JPA#JAKARTA_PACKAGE}).
	 */
	public static NestableAnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new JoinColumnAnnotationDefinition(jpaPackage);
	}

	private final String jpaPackage;

	private JoinColumnAnnotationDefinition(String jpaPackage) {
		super();
		this.jpaPackage = jpaPackage;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		if (JPA.JAVAX_PACKAGE.equals(this.jpaPackage)) {
			return SourceJoinColumnAnnotation.buildSourceJoinColumnAnnotation(parent, annotatedElement, index);
		}
		return SourceJoinColumnAnnotation.buildJakartaSourceJoinColumnAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryJoinColumnAnnotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return this.jpaPackage + JoinColumnAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getContainerAnnotationName() {
		return this.jpaPackage + JPA.JOIN_COLUMNS.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getElementName() {
		return JPA.JOIN_COLUMNS__VALUE;
	}
}
