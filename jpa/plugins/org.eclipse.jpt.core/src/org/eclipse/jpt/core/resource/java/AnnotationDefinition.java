/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * Used for building new Annotations. 
 * These should be used to define non-mapping annotations.  If you
 * want to provide new AnnotationDefinitions you will need
 * to create a new JpaPlatform by extending GenericJpaPlatform.
 * 
 * @see MappingAnnotation
 * @see org.eclipse.jpt.core.internal.platform.GenericJpaPlatform
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AnnotationDefinition
{
	/**
	 * Return the annotation's name.
	 */
	String getAnnotationName();
	
	/**
	 * Build and return an annotation for the specified member.
	 */
	Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member);
	
//	/**
//	 * Build and return an annotation for the specified JDT annotation.
//	 */
//	Annotation buildAnnotation(JarResourcePersistentMember parent, IAnnotation jdtAnnotation);
//	
	/**
	 * Build and return a null annotation for the specified member.
	 */
	Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member);

//	/**
//	 * Build and return a null annotation.
//	 */
//	Annotation buildNullAnnotation(JarResourcePersistentMember parent);
//
}
