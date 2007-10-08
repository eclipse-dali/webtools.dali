/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;

/**
 * Used for building new Annotations. 
 * These should be used to define non-mapping annotations.  If you
 * want to provide new AnnotationDefinitions you will need
 * to create a new JpaPlatform by extending BaseJpaPlatform.
 * 
 * @see MappingAnnotation
 * @see BaseJpaPlatform
 */
public interface AnnotationDefinition
{
	/**
	 * Return the fully qualified annotation name
	 */
	String getAnnotationName();
	
	/**
	 * Build and return an Annotation given the Member
	 */
	Annotation buildAnnotation(JavaResource parent, Member member);
}
