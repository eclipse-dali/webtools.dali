/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.JpaStructureNode;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingFileRoot extends XmlContextNode, JpaStructureNode
{
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
	 * Return the defaults defined within this mapping file *for the persistence unit*.
	 * Return null if none exists.
	 * @see MappingFilePersistenceUnitDefaults.resourceExists()
	 */
	MappingFilePersistenceUnitDefaults getPersistenceUnitDefaults();
}
