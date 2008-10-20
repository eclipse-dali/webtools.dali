/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.ListIterator;

import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * 
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
	 */
	Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);

	/**
	 * Build a null type mapping annotation with the specified name.
	 */
	Annotation buildNullTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);

	/**
	 * Return the names of the supporting annotations that can modify a type.
	 */
	ListIterator<String> typeSupportingAnnotationNames();
	
	/**
	 * Build a type supporting annotation with the specified name.
	 */
	Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);
	
	/**
	 * Build a null type supporting annotation with the specified name.
	 */
	Annotation buildNullTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);
	

	// ********** attribute annotations **********

	/**
	 * Return the names of the mapping annotations that can modify an attribute.
	 */
	ListIterator<String> attributeMappingAnnotationNames();
	
	/**
	 * Build an attribute mapping annotation with the specified name.
	 */
	Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);

	/**
	 * Build a null attribute mapping annotation with the specified name.
	 */
	Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);

	/**
	 * Return the names of the supporting annotations that can modify an attribute.
	 */
	ListIterator<String> attributeSupportingAnnotationNames();
	
	/**
	 * Build an attribute supporting annotation with the specified name.
	 */
	Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);
	
	/**
	 * Build a null attribute supporting annotation with the specified name.
	 */
	Annotation buildNullAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);
	
}
