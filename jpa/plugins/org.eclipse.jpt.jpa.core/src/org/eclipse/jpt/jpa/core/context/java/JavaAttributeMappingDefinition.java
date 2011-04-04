/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.jpa.core.JpaFactory;

/**
 * Map a string key to an attribute mapping and its corresponding
 * Java annotations.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.3
 * @since 2.3
 */
public interface JavaAttributeMappingDefinition
{
	/**
	 * Return the attribute mapping's key.
	 */
	String getKey();

	/**
	 * Return the name of the attribute mapping's annotation.
	 */
	String getAnnotationName();

	/**
	 * Return the names of the attribute mapping's "supporting" annotations.
	 */
	Iterable<String> getSupportingAnnotationNames();

	/**
	 * Return whether the definition's mapping is
	 * the "specified" mapping for the specified persistent attribute.
	 * <p>
	 * <strong>NB:</strong> A mapping is not necessarily "specified" if its
	 * annotation is present
	 * (see {@link org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaIdMappingDefinition2_0#isSpecified(JavaPersistentAttribute)})
	 */
	boolean isSpecified(JavaPersistentAttribute persistentAttribute);

	/**
	 * Build a Java attribute mapping for the specified persistent attribute.
	 * Use the specified factory for creation so extenders can simply override
	 * the appropriate factory method instead of building a definition for the
	 * same key.
	 */
	JavaAttributeMapping buildMapping(JavaPersistentAttribute persistentAttribute, JpaFactory factory);
}
