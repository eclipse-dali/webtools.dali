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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.BinaryEclipseLinkTenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TenantDiscriminatorColumnAnnotation2_3;

public class EclipseLinkTenantDiscriminatorColumnAnnotationDefinition2_3
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE 
			= new EclipseLinkTenantDiscriminatorColumnAnnotationDefinition2_3();


	/**
	 * Return the singleton
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkTenantDiscriminatorColumnAnnotationDefinition2_3() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceEclipseLinkTenantDiscriminatorColumnAnnotation2_3.buildSourceTenantDiscriminatorColumnAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryEclipseLinkTenantDiscriminatorColumnAnnotation2_3(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME;
	}

	public String getContainerAnnotationName() {
		return EclipseLink.TENANT_DISCRIMINATOR_COLUMNS;
	}

	public String getElementName() {
		return EclipseLink.TENANT_DISCRIMINATOR_COLUMNS__VALUE;
	}
}
