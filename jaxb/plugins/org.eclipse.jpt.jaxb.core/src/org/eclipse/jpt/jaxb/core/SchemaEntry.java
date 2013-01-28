/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;

/**
 * Schema configuration object for {@link SchemaLibrary}
 * 
 * @noimplement
 * @version 3.1
 * @since 3.1
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface SchemaEntry {
	
	/**
	 * Return the location key of the entry.
	 * This could be:
	 *  - a web location (that then redirects to a plugin resource location)
	 *  - a workspace location
	 *  - a namespace
	 */
	String getLocation();
	Transformer<SchemaEntry, String> LOCATION_TRANSFORMER = new LocationTransformer();
	class LocationTransformer
		extends TransformerAdapter<SchemaEntry, String>
	{
		@Override
		public String transform(SchemaEntry schemaEntry) {
			return schemaEntry.getLocation();
		}
	}
	
	/**
	 * Return the namespace of the schema 
	 */
	String getNamespace();
	Transformer<SchemaEntry, String> NAMESPACE_TRANSFORMER = new NamespaceTransformer();
	class NamespaceTransformer
		extends TransformerAdapter<SchemaEntry, String>
	{
		@Override
		public String transform(SchemaEntry schemaEntry) {
			return schemaEntry.getNamespace();
		}
	}
	
	/**
	 * Return whether the schema referred to by the entry is loaded
	 */
	boolean isLoaded();
	
	/**
	 * Return the {@link XsdSchema} associated with the entry.
	 * The provided namespace is a last failsafe check that the loaded schema does in fact
	 * have the namespace in question.
	 * (Sometimes on a refresh the schema will turn out to have a different namespace than expected)
	 */
	XsdSchema getXsdSchema(String namespace);
	
	/**
	 * Refresh the associated schema
	 */
	void refresh();
}
