/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Persistence unit metadata held by a mapping file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingFilePersistenceUnitMetadata
	extends XmlContextNode
{
	/**
	 * Covariant override.
	 */
	MappingFileRoot getParent();

	/**
	 * Return whether any annotations on the persistent types associated with
	 * the mapping file's persistence unit are to be ignored.
	 */
	boolean isXmlMappingMetadataComplete();

	/**
	 * Return the persistence unit defaults held by the mapping file.
	 */
	MappingFilePersistenceUnitDefaults getPersistenceUnitDefaults();

	/**
	 * Return whether the mapping file's underlying resource exists.
	 * If there is a node in the <code>orm.xml</code> file for the
	 * {@code <persistence-unit-metadata>} element,
	 * return <code>true</code>; otherwise <code>false</code>.
	 */
	boolean resourceExists();
}
