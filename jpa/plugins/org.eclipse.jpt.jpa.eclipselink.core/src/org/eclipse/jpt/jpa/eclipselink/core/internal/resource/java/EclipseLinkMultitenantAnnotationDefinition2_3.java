/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.EclipseLinkBinaryMultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.EclipseLinkSourceMultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantAnnotation2_3;

public class EclipseLinkMultitenantAnnotationDefinition2_3
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE 
			= new EclipseLinkMultitenantAnnotationDefinition2_3();
	
	
	/**
	 * Return the singleton
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkMultitenantAnnotationDefinition2_3() {
		super();
	}
	
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new EclipseLinkSourceMultitenantAnnotation2_3(parent, annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		return new EclipseLinkNullMultitenantAnnotation2_3(parent);
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new EclipseLinkBinaryMultitenantAnnotation2_3(parent, jdtAnnotation);
	}
	
	public String getAnnotationName() {
		return MultitenantAnnotation2_3.ANNOTATION_NAME;
	}
}
