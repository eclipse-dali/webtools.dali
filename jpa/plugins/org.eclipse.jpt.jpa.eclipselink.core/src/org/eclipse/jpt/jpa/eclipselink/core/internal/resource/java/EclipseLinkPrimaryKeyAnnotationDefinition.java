/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.BinaryEclipseLinkPrimaryKeyAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.SourceEclipseLinkPrimaryKeyAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkPrimaryKeyAnnotation;

public class EclipseLinkPrimaryKeyAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE 
			= new EclipseLinkPrimaryKeyAnnotationDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkPrimaryKeyAnnotationDefinition() {
		super();
	}
	
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceEclipseLinkPrimaryKeyAnnotation((JavaResourcePersistentType) parent, (Type) annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryEclipseLinkPrimaryKeyAnnotation((JavaResourcePersistentType) parent, jdtAnnotation);
	}
	
	public String getAnnotationName() {
		return EclipseLinkPrimaryKeyAnnotation.ANNOTATION_NAME;
	}
}
