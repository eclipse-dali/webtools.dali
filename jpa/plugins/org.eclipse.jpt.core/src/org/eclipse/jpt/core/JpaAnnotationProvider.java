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
 * This is used to provide type and attribute mapping and supporting annotations.
 * Also provides list of supported annotation names, check the appropriate list
 * before trying to build an annotation with that name.  An exception will
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
	 * An IllegalArgumentException is thrown if this is not a valid type
	 * mapping annotationName, check that it is valid using typeMappingAnnotationNames()
	 */
	Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);

	/**
	 * Build a null type mapping annotation with the specified name.
	 * An IllegalArgumentException is thrown if this is not a valid type mapping annotationName,
	 * check that it is valid using typeMappingAnnotationNames()
	 */
	Annotation buildNullTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);

	/**
	 * Return the names of the supporting annotations that can modify a type.
	 */
	ListIterator<String> typeSupportingAnnotationNames();
	
	/**
	 * Build a type supporting annotation with the specified name.
	 * An IllegalArgumentException is thrown if this is not a valid type supporting annotationName,
	 * check that it is valid using typeSupportingAnnotationNames()
	 */
	Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);
	
	/**
	 * Build a null type supporting annotation with the specified name.
	 * An IllegalArgumentException is thrown if this is not a valid type supporting annotationName,
	 * check that it is valid using typeSupportingAnnotationNames()
	 */
	Annotation buildNullTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);
	

	// ********** attribute annotations **********

	/**
	 * Return the names of the mapping annotations that can modify an attribute.
	 */
	ListIterator<String> attributeMappingAnnotationNames();
	
	/**
	 * Build an attribute mapping annotation with the specified name.
	 * An IllegalArgumentException is thrown if this is not a valid attribute mapping annotationName,
	 * check that it is valid using attributeMappingAnnotationNames()
	 */
	Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);

	/**
	 * Build a null attribute mapping annotation with the specified name.
	 * An IllegalArgumentException is thrown if this is not a valid attribute mapping annotationName,
	 * check that it is valid using attributeMappingAnnotationNames()
	 */
	Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);

	/**
	 * Return the names of the supporting annotations that can modify an attribute.
	 */
	ListIterator<String> attributeSupportingAnnotationNames();
	
	/**
	 * Build an attribute supporting annotation with the specified name.
	 * An IllegalArgumentException is thrown if this is not a valid attribute supporting annotationName,
	 * check that it is valid using attributeSupportingAnnotationNames()
	 */
	Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);
	
	/**
	 * Build a null attribute supporting annotation with the specified name.
	 * An IllegalArgumentException is thrown if this is not a valid attribute supporting annotationName,
	 * check that it is valid using attributeSupportingAnnotationNames()
	 */
	Annotation buildNullAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);
	
}
