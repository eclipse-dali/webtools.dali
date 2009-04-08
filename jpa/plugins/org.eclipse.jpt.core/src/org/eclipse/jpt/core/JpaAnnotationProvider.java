/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.ListIterator;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * This is used to provide type and attribute mapping and supporting annotations.
 * Also provides list of supported annotation names, check the appropriate list
 * before trying to build an annotation with that name. An exception will
 * be thrown on an attempt to build an annotation that does not exist.
 * 
 * This interface is not intended to be implemented.  Instead implement 
 * JpaAnnotationDefinitionProvider to extend the list of supported annotation definitions.
 * 
 * @see JpaAnnotationDefinitionProvider
 * @version 2.2
 * @since 2.0?
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaAnnotationProvider {

	// ********** type annotations **********

	/**
	 * Return the names of the mapping annotations that can modify a type.
	 */
	ListIterator<String> typeMappingAnnotationNames();

	/**
	 * Build a type mapping annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #typeMappingAnnotationNames()
	 */
	Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);

	/**
	 * Build a type mapping annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #typeMappingAnnotationNames()
	 */
	Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, IAnnotation jdtAnnotation);

	/**
	 * Return the names of the supporting annotations that can modify a type.
	 */
	ListIterator<String> typeSupportingAnnotationNames();

	/**
	 * Build a type supporting annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #typeSupportingAnnotationNames()
	 */
	Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);

	/**
	 * Build a type supporting annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #typeSupportingAnnotationNames()
	 */
	Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, IAnnotation jdtAnnotation);

	/**
	 * Build a null type supporting annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #typeSupportingAnnotationNames()
	 */
	Annotation buildNullTypeSupportingAnnotation(JavaResourcePersistentType parent, String annotationName);


	// ********** attribute annotations **********

	/**
	 * Return the names of the mapping annotations that can modify an attribute.
	 */
	ListIterator<String> attributeMappingAnnotationNames();

	/**
	 * Build an attribute mapping annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #attributeMappingAnnotationNames()
	 */
	Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);

	/**
	 * Build an attribute mapping annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #attributeMappingAnnotationNames()
	 */
	Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation);

	/**
	 * Build a null attribute mapping annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * This is used by an attribute's "default" mapping, since there is no
	 * annotation present (thus the "default" part...).
	 * @see #attributeMappingAnnotationNames()
	 */
	Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, String annotationName);

	/**
	 * Return the names of the supporting annotations that can modify an attribute.
	 */
	ListIterator<String> attributeSupportingAnnotationNames();

	/**
	 * Build an attribute supporting annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified name is unsupported.
	 * @see #attributeSupportingAnnotationNames()
	 */
	Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);

	/**
	 * Build an attribute supporting annotation for the specified JDT annotation.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #attributeSupportingAnnotationNames()
	 */
	Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation);

	/**
	 * Build a null attribute supporting annotation with the specified name.
	 * Throw an IllegalArgumentException if the specified annotation is unsupported.
	 * @see #attributeSupportingAnnotationNames()
	 */
	Annotation buildNullAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, String annotationName);

}
