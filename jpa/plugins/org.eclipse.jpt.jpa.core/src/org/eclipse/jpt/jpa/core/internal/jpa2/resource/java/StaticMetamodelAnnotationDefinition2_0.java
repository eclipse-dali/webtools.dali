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
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryStaticMetamodelAnnotation2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceStaticMetamodelAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.StaticMetamodelAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.metamodel.StaticMetamodel</code>
 */
public final class StaticMetamodelAnnotationDefinition2_0
	implements AnnotationDefinition
{
	// singleton for javax.persistence (default)
	private static final StaticMetamodelAnnotationDefinition2_0 INSTANCE = new StaticMetamodelAnnotationDefinition2_0(JPA.JAVAX_PACKAGE);

	/**
	 * Return the singleton.
	 */
	public static StaticMetamodelAnnotationDefinition2_0 instance() {
		return INSTANCE;
	}

	public static StaticMetamodelAnnotationDefinition2_0 instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new StaticMetamodelAnnotationDefinition2_0(jpaPackage);
	}

	/**
	 * Ensure single instance.
	 */
	private final String annotationName;

	private StaticMetamodelAnnotationDefinition2_0(String jpaPackage) {
		super();
		this.annotationName = jpaPackage + StaticMetamodelAnnotation2_0.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public StaticMetamodelAnnotation2_0 buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		if (StaticMetamodelAnnotation2_0.ANNOTATION_NAME.equals(this.annotationName)) {
			return new SourceStaticMetamodelAnnotation2_0(parent, annotatedElement);
		}
		return new SourceStaticMetamodelAnnotation2_0(parent, annotatedElement,
				org.eclipse.jpt.common.core.internal.utility.jdt.JakartaAwareDeclarationAnnotationAdapter.forJakarta(this.annotationName));
	}

	public StaticMetamodelAnnotation2_0 buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public StaticMetamodelAnnotation2_0 buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryStaticMetamodelAnnotation2_0(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return this.annotationName;
	}
}
