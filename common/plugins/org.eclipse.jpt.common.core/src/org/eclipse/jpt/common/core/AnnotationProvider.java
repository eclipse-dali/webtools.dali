/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;

/**
 * This is used to provide annotations and nestable annotations. An exception will
 * be thrown on an attempt to build an annotation that does not exist.
 * 
 * This interface is not intended to be implemented.
 * 
 * @version 3.0
 * @since 3.0
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AnnotationProvider {

	/**
	 * Return the names of the annotations.
	 */
	Iterable<String> getAnnotationNames();

	Iterable<String> getContainerAnnotationNames();

	Iterable<String> getNestableAnnotationNames();

	String getNestableAnnotationName(String containerAnnotationName);

	String getNestableElementName(String nestableAnnotationName);

	/**
	 * Build an annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #getAnnotationNames()
	 */
	Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName);

	/**
	 * Build a nestable annotation with the specified name and index.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #getNestableAnnotationNames()
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName, int index);

	/**
	 * Build an annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #getAnnotationNames()
	 */
	Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation);

	/**
	 * Build a null annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #getAnnotationNames()
	 */
	Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent, String annotationName);
}
