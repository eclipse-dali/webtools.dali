/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryStaticMetamodelAnnotation;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceStaticMetamodelAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.StaticMetamodelAnnotation;

/**
 * javax.persistence.metamodel.StaticMetamodel
 */
public final class StaticMetamodelAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final StaticMetamodelAnnotationDefinition INSTANCE = new StaticMetamodelAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static StaticMetamodelAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private StaticMetamodelAnnotationDefinition() {
		super();
	}

	public StaticMetamodelAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceStaticMetamodelAnnotation(parent, annotatedElement);
	}

	public StaticMetamodelAnnotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public StaticMetamodelAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryStaticMetamodelAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return StaticMetamodelAnnotation.ANNOTATION_NAME;
	}
}
