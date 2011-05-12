/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistentTypeContainer;

/**
 * The root of a JPA mapping file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface MappingFileRoot
	extends XmlContextNode, JpaStructureNode, PersistentTypeContainer
{
	/**
	 * covariant override
	 */
	MappingFile getParent();

	/**
	 * Return the specified access if present, otherwise return the default
	 * access.
	 */
	AccessType getAccess();

	/**
	 * Return the specified catalog if present, otherwise return the default
	 * catalog.
	 */
	String getCatalog();

	/**
	 * Return the specified schema if present, otherwise return the default
	 * schema.
	 */
	String getSchema();

	/**
	 * Return the metadata defined within the mapping file
	 * <em>for the persistence unit</em>.
	 * Return <code>null</code> if none exists.
	 * 
	 * @see MappingFilePersistenceUnitMetadata#resourceExists()
	 */
	MappingFilePersistenceUnitMetadata getPersistenceUnitMetadata();
}
