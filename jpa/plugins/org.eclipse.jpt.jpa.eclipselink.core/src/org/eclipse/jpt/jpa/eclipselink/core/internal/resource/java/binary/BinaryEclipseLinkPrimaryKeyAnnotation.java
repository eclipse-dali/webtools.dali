/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
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
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.PrimaryKeyAnnotation;

/**
 * org.eclipse.persistence.annotations.PrimaryKey
 */
public class BinaryEclipseLinkPrimaryKeyAnnotation
	extends BinaryAnnotation
	implements PrimaryKeyAnnotation
{
	public BinaryEclipseLinkPrimaryKeyAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}
	
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
}
