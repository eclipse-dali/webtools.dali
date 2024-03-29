/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;

/**
 * <code>orm.xml</code> definition
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
public interface OrmXmlDefinition
	extends MappingFileDefinition
{
	/**
	 * Return the factory for building XML resource nodes.
	 */
	EFactory getResourceModelFactory();

	/**
	 * Return the factory for building XML context models.
	 */
	OrmXmlContextModelFactory getContextModelFactory();


	// ********** managed types **********

	/**
	 * Return the OrmManagedTypeDefinition corresponding to the given resource type
	 * @throws IllegalArgumentException if the resource type is not supported
	 * @see OrmManagedTypeDefinition#getResourceManagedTypeType()
	 */
	OrmManagedTypeDefinition getManagedTypeDefinition(Class<? extends XmlManagedType> resourceType);


	// ********** type/attribute mappings **********

	/**
	 * Return a type mapping definiton for the specified mapping key.
	 * @throws IllegalArgumentException if the mapping key is not
	 * supported
	 */
	OrmTypeMappingDefinition getTypeMappingDefinition(String mappingKey);

	/**
	 * Return an attribute mapping definiton for the specified mapping key.
	 * Never return <code>null</code>.
	 */
	OrmAttributeMappingDefinition getAttributeMappingDefinition(String mappingKey);
}
