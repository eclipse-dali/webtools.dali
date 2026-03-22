/*******************************************************************************
* Copyright (c) 2009, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryNamedQueryAnnotation2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceNamedQueryAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;

/**
 * <code>javax.persistence.NamedQuery</code>
 */
public class NamedQueryAnnotationDefinition2_0
	implements NestableAnnotationDefinition
{
	// default singleton (javax.persistence)
	private static final NestableAnnotationDefinition INSTANCE = new NamedQueryAnnotationDefinition2_0(JPA.JAVAX_PACKAGE);

	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	public static NestableAnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new NamedQueryAnnotationDefinition2_0(jpaPackage);
	}

	private final String jpaPackage;

	private NamedQueryAnnotationDefinition2_0(String jpaPackage) {
		super();
		this.jpaPackage = jpaPackage;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		if (JPA.JAVAX_PACKAGE.equals(this.jpaPackage)) {
			return SourceNamedQueryAnnotation2_0.buildSourceNamedQueryAnnotation(parent, annotatedElement, index);
		}
		return SourceNamedQueryAnnotation2_0.buildJakartaSourceNamedQueryAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryNamedQueryAnnotation2_0(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return this.jpaPackage + NamedQueryAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getContainerAnnotationName() {
		return this.jpaPackage + JPA.NAMED_QUERIES.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getElementName() {
		return JPA.NAMED_QUERIES__VALUE;
	}
}
