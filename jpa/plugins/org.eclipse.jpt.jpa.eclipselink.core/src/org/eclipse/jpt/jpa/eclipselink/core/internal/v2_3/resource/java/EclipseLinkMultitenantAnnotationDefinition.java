/*******************************************************************************
 *  Copyright (c) 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.binary.BinaryEclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.source.SourceEclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;

public class EclipseLinkMultitenantAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE 
			= new EclipseLinkMultitenantAnnotationDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkMultitenantAnnotationDefinition() {
		super();
	}
	
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceEclipseLinkMultitenantAnnotation(parent, annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryEclipseLinkMultitenantAnnotation(parent, jdtAnnotation);
	}
	
	public String getAnnotationName() {
		return EclipseLinkMultitenantAnnotation.ANNOTATION_NAME;
	}
}
