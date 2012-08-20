/*******************************************************************************
 *  Copyright (c) 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryGeneratorAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkUuidGeneratorAnnotation2_4;

/**
 * org.eclipse.persistence.annotations.UuidGenerator
 */
public class BinaryEclipseLinkUuidGeneratorAnnotation2_4
	extends BinaryGeneratorAnnotation
	implements EclipseLinkUuidGeneratorAnnotation2_4
{


	public BinaryEclipseLinkUuidGeneratorAnnotation2_4(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected String getNameElementName() {
		return EclipseLink.UUID_GENERATOR__NAME;
	}

}
