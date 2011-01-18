/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Persistence unit defaults held by a mapping file.
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
public interface MappingFilePersistenceUnitDefaults
	extends XmlContextNode
{
	/**
	 * Covariant override.
	 */
	MappingFilePersistenceUnitMetadata getParent();

	/**
	 * Return the access type for all managed classes in the persistence unit,
	 * unless overridden by a local annotation or XML setting.
	 */
	AccessType getAccess();

	/**
	 * Return the catalog for all database objects referenced in the
	 * persistence unit, unless overridden by a local annotation or XML setting.
	 * <p>
	 * Return the specified catalog if present, otherwise return the
	 * default catalog as determined by the database connection.
	 */
	String getCatalog();

	/**
	 * Return the schema for all database objects referenced in the
	 * persistence unit, unless overridden by a local annotation or XML setting.
	 * <p>
	 * Return the specified schema if present, otherwise return the
	 * default schema as determined by the database connection.
	 */
	String getSchema();

	/**
	 * Return all the relationhips in the persistence unit are to be cascade
	 * persist, unless overridden by a local annotation or XML setting.
	 */
	boolean isCascadePersist();
}
