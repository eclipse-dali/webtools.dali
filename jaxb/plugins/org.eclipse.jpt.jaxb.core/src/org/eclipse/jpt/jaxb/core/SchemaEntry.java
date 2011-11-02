package org.eclipse.jpt.jaxb.core;

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
	
	/**
	 * Return the namespace of the schema 
	 */
	String getNamespace();
	
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
