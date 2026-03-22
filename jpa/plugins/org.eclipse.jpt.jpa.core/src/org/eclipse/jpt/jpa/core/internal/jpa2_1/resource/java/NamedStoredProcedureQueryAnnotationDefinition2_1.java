/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.binary.BinaryNamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.source.SourceNamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.NamedStoredProcedureQuery</code>
 */
public final class NamedStoredProcedureQueryAnnotationDefinition2_1
	implements NestableAnnotationDefinition
{
	// default singleton (javax.persistence)
	private static final NestableAnnotationDefinition INSTANCE = new NamedStoredProcedureQueryAnnotationDefinition2_1(JPA.JAVAX_PACKAGE);

	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	public static NestableAnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new NamedStoredProcedureQueryAnnotationDefinition2_1(jpaPackage);
	}

	private final String jpaPackage;

	private NamedStoredProcedureQueryAnnotationDefinition2_1(String jpaPackage) {
		super();
		this.jpaPackage = jpaPackage;
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		if (JPA.JAVAX_PACKAGE.equals(this.jpaPackage)) {
			return SourceNamedStoredProcedureQueryAnnotation2_1.buildSourceNamedStoredProcedureQuery2_1Annotation(parent, annotatedElement, index);
		}
		return SourceNamedStoredProcedureQueryAnnotation2_1.buildJakartaSourceNamedStoredProcedureQuery2_1Annotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryNamedStoredProcedureQueryAnnotation2_1(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return this.jpaPackage + JPA2_1.NAMED_STORED_PROCEDURE_QUERY.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getContainerAnnotationName() {
		return this.jpaPackage + JPA2_1.NAMED_STORED_PROCEDURE_QUERIES.substring(JPA.JAVAX_PACKAGE.length());
	}

	public String getElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERIES__VALUE;
	}
}
