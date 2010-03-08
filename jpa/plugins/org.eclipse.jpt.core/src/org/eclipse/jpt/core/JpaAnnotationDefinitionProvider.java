/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Iterator;
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
 * @version 2.3
 * @since 2.2
 */
public interface JpaAnnotationDefinitionProvider
{
	/**
	 * Return all annotation definitions which can appear on a type
	 */
	Iterator<AnnotationDefinition> typeAnnotationDefinitions();
	
	/**
	 * Return all annotation definitions which can appear on a type and are used to determine
	 * whether and how the type is persisted (how it is "mapped").
	 * This should be a subset of {@link #typeAnnotationDefinitions()}.
	 */
	Iterator<AnnotationDefinition> typeMappingAnnotationDefinitions();
	
	/**
	 * Return all annotation definitions which can appear on an attribute
	 */
	Iterator<AnnotationDefinition> attributeAnnotationDefinitions();
}
