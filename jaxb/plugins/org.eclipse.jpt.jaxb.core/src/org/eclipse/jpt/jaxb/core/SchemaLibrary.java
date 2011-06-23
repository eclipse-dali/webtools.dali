/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import java.util.Map;
import org.eclipse.xsd.XSDSchema;

/**
 * Entry point for accessing project schema resources
 * 
 * @noimplement
 * @version 3.0
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
	 * Return the project settings that indicate how schema keys are mapped to actual
	 * resolvable schemas
	 */
	public Map<String, String> getSchemaLocations();
	
	/**
	 * Set the project settings that indicate how schema keys are mapped to actual
	 * resolvable schemas.
	 */
	public void setSchemaLocations(Map<String, String> schemaLocations);
	
	/**
	 * Return the XSDSchema identified by the given namespace, if it exists and is resolvable.
	 * Return null otherwise.
	 */
	public XSDSchema getSchema(String namespace);
	
	/**
	 * Refresh the schema with the given namespace, if it exists and is resolvable.
	 * Do nothing otherwise.
	 */
	public void refreshSchema(String namespace);
	
	/**
	 * Refresh all schemas within the library.
	 */
	public void refreshAllSchemas();
	
}
