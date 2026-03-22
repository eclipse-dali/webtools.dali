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
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryAttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceAttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * javax.persistence.AttributeOverride
 */
public final class AttributeOverrideAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// default singleton (javax.persistence)
	private static final NestableAnnotationDefinition INSTANCE = new AttributeOverrideAnnotationDefinition(JPA.JAVAX_PACKAGE);

	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	public static NestableAnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new AttributeOverrideAnnotationDefinition(jpaPackage);
	}

	private final String jpaPackage;

	private AttributeOverrideAnnotationDefinition(String jpaPackage) {
		super();
		this.jpaPackage = jpaPackage;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		if (JPA.JAVAX_PACKAGE.equals(this.jpaPackage)) {
			return SourceAttributeOverrideAnnotation.buildSourceAttributeOverrideAnnotation(parent, annotatedElement, index);
		}
		return SourceAttributeOverrideAnnotation.buildJakartaSourceAttributeOverrideAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryAttributeOverrideAnnotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return this.jpaPackage + AttributeOverrideAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getContainerAnnotationName() {
		return this.jpaPackage + JPA.ATTRIBUTE_OVERRIDES.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getElementName() {
		return JPA.ATTRIBUTE_OVERRIDES__VALUE;
	}
}
