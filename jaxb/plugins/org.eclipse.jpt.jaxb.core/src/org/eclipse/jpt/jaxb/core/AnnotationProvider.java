/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;

/**
 * This is used to provide type and attribute annotations.
 * Also provides list of supported annotation names, check the appropriate list
 * before trying to build an annotation with that name. An exception will
 * be thrown on an attempt to build an annotation that does not exist.
 * 
 * This interface is not intended to be implemented.  Instead implement 
 * AnnotationDefinitionProvider to extend the list of supported annotation definitions.
 * 
 * @see AnnotationDefinitionProvider
 * @version 3.0
 * @since 3.0
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AnnotationProvider 
{
	// ********** type annotations **********
	
	/**
	 * Return the names of the annotations that can appear on a type.
	 */
	Iterable<String> getTypeAnnotationNames();

	/**
	 * Return the names of the annotations that can appear on a type and are used to 
	 * determine whether and how the type is persisted (how it is "mapped").
	 * This should be a subset of {@link #typeAnnotationNames()}.
	 */
	Iterable<String> getTypeMappingAnnotationNames();

	/**
	 * Build a type annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #typeAnnotationNames()
	 */
	Annotation buildTypeAnnotation(
			JavaResourceType parent, Type type, String annotationName);

	/**
	 * Build a type annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #typeAnnotationNames()
	 */
	Annotation buildTypeAnnotation(
			JavaResourceType parent, IAnnotation jdtAnnotation);

	/**
	 * Build a null type annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #typeAnnotationNames()
	 */
	Annotation buildNullTypeAnnotation(
			JavaResourceType parent, String annotationName);


	// ********** attribute annotations **********
	
	/**
	 * Return the names of the annotations that can appear on an attribute.
	 */
	Iterable<String> getAttributeAnnotationNames();

	/**
	 * Build an attribute annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #attributeAnnotationNames()
	 */
	Annotation buildAttributeAnnotation(
			JavaResourceAttribute parent, Attribute attribute, String annotationName);

	/**
	 * Build an attribute annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #attributeAnnotationNames()
	 */
	Annotation buildAttributeAnnotation(
			JavaResourceAttribute parent, IAnnotation jdtAnnotation);

	/**
	 * Build a null attribute annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #attributeMappingAnnotationNames()
	 */
	Annotation buildNullAttributeAnnotation(
			JavaResourceAttribute parent, String annotationName);


	// ********** package annotations **********

	/**
	 * Return the names of the annotations that can appear on a package.
	 */
	Iterable<String> getPackageAnnotationNames();

	/**
	 * Build an package annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #packageAnnotationNames()
	 */
	Annotation buildPackageAnnotation(
			JavaResourcePackage parent, AnnotatedPackage pack, String annotationName);

	/**
	 * Build a package annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #packageAnnotationNames()
	 */
	Annotation buildPackageAnnotation(
			JavaResourcePackage parent, IAnnotation jdtAnnotation);

	/**
	 * Build a null package annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #packageAnnotationNames()
	 */
	Annotation buildNullPackageAnnotation(
			JavaResourcePackage parent, String annotationName);
}
