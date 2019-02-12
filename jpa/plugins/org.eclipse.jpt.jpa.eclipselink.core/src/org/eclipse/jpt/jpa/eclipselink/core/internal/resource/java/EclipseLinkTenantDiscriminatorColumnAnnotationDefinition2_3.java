/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary.EclipseLinkBinaryTenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.EclipseLinkSourceTenantDiscriminatorColumnAnnotation2_3;
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
		return EclipseLinkSourceTenantDiscriminatorColumnAnnotation2_3.buildSourceTenantDiscriminatorColumnAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new EclipseLinkBinaryTenantDiscriminatorColumnAnnotation2_3(parent, jdtAnnotation);
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
