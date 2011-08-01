/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;

/**
 * Used to build NestableAnnotations discovered in the Java source code.
 * To provide new NestableAnnotationDefinitions, create a new JaxbPlatform
 * by implementing JaxbPlatformDefinition.
 * 
 * @see NestableAnnotation
 * @see org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface NestableAnnotationDefinition
{
	/**
	 * Return the name of the annotation the definition will build in the
	 * various #build...(...) methods.
	 */
	String getNestableAnnotationName();

	String getContainerAnnotationName();

	String getElementName();

	/**
	 * Build and return an annotation for the specified annotated element.
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index);

	/**
	 * Build and return an annotation for the specified JDT annotation
	 * on the specified annotated element.
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index);
}
