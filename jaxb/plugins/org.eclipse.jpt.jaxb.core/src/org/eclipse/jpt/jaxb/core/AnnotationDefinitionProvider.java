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

import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;

/**
 * Provides annotationDefinitions for types and attributes. AnnotationProvider
 * then uses a collection of these to build annotations.
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
public interface AnnotationDefinitionProvider
{
	/**
	 * Return all annotation definitions which can appear on a type
	 */
	Iterable<AnnotationDefinition> getTypeAnnotationDefinitions();
	
	/**
	 * Return all annotation definitions which can appear on a type and are used to determine
	 * whether and how the type is persisted (how it is "mapped").
	 * This should be a subset of {@link #typeAnnotationDefinitions()}.
	 */
	Iterable<AnnotationDefinition> getTypeMappingAnnotationDefinitions();
	
	/**
	 * Return all annotation definitions which can appear on an attribute
	 */
	Iterable<AnnotationDefinition> getAttributeAnnotationDefinitions();

	/**
	 * Return all annotation definitions which can appear on a package.
	 */
	Iterable<AnnotationDefinition> getPackageAnnotationDefinitions();
}
