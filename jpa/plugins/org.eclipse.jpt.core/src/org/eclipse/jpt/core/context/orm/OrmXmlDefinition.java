/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.emf.ecore.EFactory;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrmXmlDefinition 
	extends MappingFileDefinition
{
	/**
	 * Return the factory for building xml resource nodes
	 */
	EFactory getResourceNodeFactory();
	
	/**
	 * Return the factory for building xml context nodes
	 */
	OrmXmlContextNodeFactory getContextNodeFactory();
	
	
	// ********** ORM type/attribute mappings **********
	
	/**
	 * Return an {@link OrmTypeMappingDefinition} for the given type mapping key.
	 * Throws an {@link IllegalArgumentException} if the mapping key is not supported.
	 * 
	 * @param mappingKey The type mapping key
	 * @return The mapping provider for the given mapping key
	 */
	OrmTypeMappingDefinition getOrmTypeMappingDefinition(String mappingKey);
	
	/**
	 * Return an {@link OrmAttributeMappingDefinition} for the given attribute mapping key.
	 * This must not return null.  (@see {@link NullOrmAttributeMappingDefinition})
	 * Throws an {@link IllegalArgumentException} if the mapping key is not supported.
	 * 
	 * @param mappingKey The attribute mapping key
	 * @return The mapping definition for the given mapping key
	 */
	OrmAttributeMappingDefinition getOrmAttributeMappingDefinition(String mappingKey);
}
