/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import java.util.List;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;

/**
 * Entry point for accessing project schema resources
 * 
 * @noimplement
 * @version 3.1
 * @since 3.0
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface SchemaLibrary {
	
	/**
	 * Return the schema entries that have been added to the project settings for validation purposes
	 */
	public List<SchemaEntry> getSchemaEntries();
	
	/**
	 * Return the locations associated with the schema entries
	 */
	public List<String> getSchemaLocations();
	
	/**
	 * Set the schema locations
	 */
	public void setSchemaLocations(List<String> schemaLocations);
	
	/**
	 * Return the XsdSchema identified by the given namespace, if it exists and is resolvable.
	 * If there are multiple schemas with the given namespace, return the first one found.
	 * Return null otherwise.
	 */
	public XsdSchema getSchema(String namespace);
	
	/**
	 * Refresh the schema with the given namespace, if it exists and is resolvable.
	 * Refresh the first one found if there are multiple schemas for the namespace.
	 * Do nothing otherwise.
	 */
	public void refreshSchema(String namespace);
	
	/**
	 * Refresh all schemas within the library.
	 */
	public void refreshAllSchemas();
}
