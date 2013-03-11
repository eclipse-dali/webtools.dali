/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;

/**
 * This is used to provide annotations and nestable annotations. An exception will
 * be thrown on an attempt to build an annotation that does not exist.
 * 
 * @see SimpleAnnotationProvider
 * 
 * This interface is not intended to be implemented.
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

	/**
	 * Return all the supported "container" annotation names.
	 * These are defined via the list of NestableAnnotationDefinitions.
	 * A NestableAnnotationDefinition defines its corresponding "container" annotation name.
	 * You must also provide an AnnotationDefinition for the "container" annotation.
	 * 
	 * @see NestableAnnotationDefinition#getContainerAnnotationName()
	 */
	Iterable<String> getContainerAnnotationNames();

	/**
	 * Return all the supported "nestable" annotation names.
	 * These are defined via the list of NestableAnnotationDefinitions.
	 * 
	 * @see NestableAnnotationDefinition#getNestableAnnotationName()
	 */
	Iterable<String> getNestableAnnotationNames();

	/**
	 * Return the "nestable" annotation name for the given "container" annotation name.
	 * 
	 * @see NestableAnnotationDefinition
	 */
	String getNestableAnnotationName(String containerAnnotationName);

	/**
	 * Return the "container" annotation name for the given "nestable" annotation name.
	 * 
	 * @see NestableAnnotationDefinition
	 */
	String getContainerAnnotationName(String nestableAnnotationName);

	/**
	 * Return the annotation "element" name used for the "nestable" annotation 
	 * when it is nested within a "container" annotation. Typically "value".
	 * 
	 * @see NestableAnnotationDefinition#getElementName()
	 */
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
	 * Build a nestable annotation for the specified JDT annotation and index.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #getNestableAnnotationNames()
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index);

	/**
	 * Build a null annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #getAnnotationNames()
	 */
	Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent, String annotationName);
}
