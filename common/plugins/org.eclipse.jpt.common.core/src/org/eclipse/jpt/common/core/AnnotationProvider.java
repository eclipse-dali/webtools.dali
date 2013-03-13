/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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
 * This is used to provide annotations and <em>nestable</em> annotations.
 * The provider will throw an exception with any
 * attempt to build an unknown annotation.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see JavaResourceAnnotatedElement
 * @see org.eclipse.jpt.common.core.internal.SimpleAnnotationProvider
 */
public interface AnnotationProvider {

	/**
	 * Return the names of the supported annotations.
	 */
	Iterable<String> getAnnotationNames();

	/**
	 * Return the names of the supported <em>container</em> annotations.
	 * @see org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition#getContainerAnnotationName()
	 */
	Iterable<String> getContainerAnnotationNames();

	/**
	 * Return the names of the supported <em>nestable</em> annotations.
	 * @see org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition#getNestableAnnotationName()
	 */
	Iterable<String> getNestableAnnotationNames();

	/**
	 * Return the name of the <em>nestable</em> annotation for the specified
	 * <em>container</em> annotation.
	 * @see org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition
	 */
	String getNestableAnnotationName(String containerAnnotationName);

	/**
	 * Return the name of the <em>container</em> annotation for the specified
	 * <em>nestable</em> annotation.
	 * @see org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition
	 */
	String getContainerAnnotationName(String nestableAnnotationName);

	/**
	 * Return the name of the <em>container</em> annotation's element whose
	 * value is the <em>nestable</em> annotation(s)
	 * (typically <code>"value"</code>).
	 * @see org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition#getElementName()
	 */
	String getNestableElementName(String nestableAnnotationName);

	/**
	 * Build an annotation with the specified name.
	 * @exception IllegalArgumentException if the specified annotation is unsupported
	 * @see #getAnnotationNames()
	 */
	Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName);

	/**
	 * Build a <em>nestable</em> annotation with the specified name and index.
	 * @exception IllegalArgumentException if the specified annotation is unsupported
	 * @see #getNestableAnnotationNames()
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, String annotationName, int index);

	/**
	 * Build an annotation for the specified JDT annotation.
	 * @exception IllegalArgumentException if the specified annotation is unsupported
	 * @see #getAnnotationNames()
	 */
	Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation);

	/**
	 * Build a <em>nestable</em> annotation for the specified JDT annotation and index.
	 * @exception IllegalArgumentException if the specified annotation is unsupported
	 * @see #getNestableAnnotationNames()
	 */
	NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index);

	/**
	 * Build a <em>null</em> annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #getAnnotationNames()
	 */
	Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent, String annotationName);
}
