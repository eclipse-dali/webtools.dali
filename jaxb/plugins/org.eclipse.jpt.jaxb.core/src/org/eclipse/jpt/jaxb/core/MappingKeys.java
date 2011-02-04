/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
@SuppressWarnings("nls")
public interface MappingKeys {

	String XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY = "xml-attribute";
	String XML_ELEMENT_ATTRIBUTE_MAPPING_KEY = "xml-element";
	String XML_TRANSIENT_ATTRIBUTE_MAPPING_KEY = "xml-transient";
	String XML_VALUE_ATTRIBUTE_MAPPING_KEY = "xml-value";
	String NULL_ATTRIBUTE_MAPPING_KEY = null;
}
