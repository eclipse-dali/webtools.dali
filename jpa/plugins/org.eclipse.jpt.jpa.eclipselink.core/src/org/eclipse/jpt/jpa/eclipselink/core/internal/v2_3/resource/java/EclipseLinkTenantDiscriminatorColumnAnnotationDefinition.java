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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.binary.BinaryEclipseLinkTenantDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.source.SourceEclipseLinkTenantDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;

public class EclipseLinkTenantDiscriminatorColumnAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE 
			= new EclipseLinkTenantDiscriminatorColumnAnnotationDefinition();


	/**
	 * Return the singleton
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkTenantDiscriminatorColumnAnnotationDefinition() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceEclipseLinkTenantDiscriminatorColumnAnnotation.buildSourceTenantDiscriminatorColumnAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryEclipseLinkTenantDiscriminatorColumnAnnotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME;
	}

	public String getContainerAnnotationName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMNS;
	}

	public String getElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMNS__VALUE;
	}
}
