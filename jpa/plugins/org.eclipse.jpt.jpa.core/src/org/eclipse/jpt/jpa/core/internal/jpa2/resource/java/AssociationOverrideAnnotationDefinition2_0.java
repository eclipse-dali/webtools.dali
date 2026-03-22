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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryAssociationOverrideAnnotation2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceAssociationOverrideAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.AssociationOverride</code>
 */
public final class AssociationOverrideAnnotationDefinition2_0
	implements NestableAnnotationDefinition
{
	// default singleton (javax.persistence)
	private static final NestableAnnotationDefinition INSTANCE = new AssociationOverrideAnnotationDefinition2_0(JPA.JAVAX_PACKAGE);

	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	public static NestableAnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new AssociationOverrideAnnotationDefinition2_0(jpaPackage);
	}

	private final String jpaPackage;

	private AssociationOverrideAnnotationDefinition2_0(String jpaPackage) {
		super();
		this.jpaPackage = jpaPackage;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		if (JPA.JAVAX_PACKAGE.equals(this.jpaPackage)) {
			return SourceAssociationOverrideAnnotation2_0.buildSourceAssociationOverrideAnnotation(parent, annotatedElement, index);
		}
		return SourceAssociationOverrideAnnotation2_0.buildJakartaSourceAssociationOverrideAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryAssociationOverrideAnnotation2_0(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return this.jpaPackage + AssociationOverrideAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getContainerAnnotationName() {
		return this.jpaPackage + JPA.ASSOCIATION_OVERRIDES.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getElementName() {
		return JPA.ASSOCIATION_OVERRIDES__VALUE;
	}
}
