/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context.java;

import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;


/**
 * Map a string key to an attribute mapping and its corresponding
 * Java annotation.
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
public interface JavaAttributeMappingDefinition 
{
	/**
	 * Return the attribute mapping's key.
	 */
	String getKey();

	/**
	 * Return the attribute mapping's Java annotation name.
	 */
	String getAnnotationName();

	/**
	 * Build a Java attribute mapping for the specified attribute. Use the specified
	 * factory for creation so extenders can simply override the appropriate
	 * creation method instead of building a provider for the same key.
	 */
	JaxbAttributeMapping buildMapping(JaxbPersistentAttribute attribute, JaxbFactory factory);

	/**
	 * Return all fully qualified annotation names that are supported with this mapping type.
	 * This includes all possible annotations, not just the ones that currently exist on the attribute.
	 */
	Iterable<String> getSupportingAnnotationNames();

	/**
	 * Return whether this mapping provider should be used for the given {@link PersistentAttribute} 
	 * in the specified (observing all mapping annotations) case.
	 */
	boolean isSpecified(JaxbPersistentAttribute persistentAttribute);
}
