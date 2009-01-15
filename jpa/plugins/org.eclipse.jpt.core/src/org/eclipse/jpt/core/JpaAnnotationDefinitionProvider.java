/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.ListIterator;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;

/**
 * Provides annotationDefinitions for types and attributes. JpaAnnotationProvider
 * then uses a collection of these to build annotations.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JpaAnnotationDefinitionProvider {

	/**
	 * Return a ListIterator of mapping annotation definitions which can 
	 * be placed on a type
	 */
	ListIterator<AnnotationDefinition> typeMappingAnnotationDefinitions();
	
	/**
	 * Return a ListIterator of supporting annotation definitions which can 
	 * be placed on a type.  These annotations in conjuction with the 
	 * type mapping annotations
	 */
	ListIterator<AnnotationDefinition> typeSupportingAnnotationDefinitions();
	
	/**
	 * Return a ListIterator of mapping annotation definitions which can 
	 * be placed on an attribute
	 */
	ListIterator<AnnotationDefinition> attributeMappingAnnotationDefinitions();
	
	/**
	 * Return a ListIterator of supporting annotation definitions which can 
	 * be placed on a attribute.  These annotations in conjuction with the 
	 * attribute mapping annotations
	 */
	ListIterator<AnnotationDefinition> attributeSupportingAnnotationDefinitions();
}
