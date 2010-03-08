/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;

/**
 * Part of mechanism to extend the types of ORM attribute mappings.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrmAttributeMappingDefinition
{
	/**
	 * Return the mapping key associated with this provider
	 * @see {@link MappingKeys}
	 */
	String getKey();
	
	/**
	 * Build a resource mapping
	 */
	XmlAttributeMapping buildResourceMapping(EFactory factory);
	
	/**
	 * Build a virtual resource mapping
	 */
	XmlAttributeMapping buildVirtualResourceMapping(
			OrmTypeMapping ormTypeMapping, 
			JavaAttributeMapping javaAttributeMapping, 
			OrmXmlContextNodeFactory factory);
	
	/**
	 * Build a context mapping
	 */
	OrmAttributeMapping buildContextMapping(
			OrmPersistentAttribute parent, 
			XmlAttributeMapping resourceMapping, 
			OrmXmlContextNodeFactory factory);	
}
