/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.java;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.context.XmlNamedNodeMapping;


/**
 * Common interface for ELXmlElementMapping and ELXmlAttributeMapping
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface ELXmlNamedNodeMapping
		extends XmlNamedNodeMapping, ELXmlPathMapping {
	
	// ***** XmlKey *****
	
	/**
	 * String associated with changes to the xmlKey property
	 */
	String XML_KEY_PROPERTY = "xmlKey";  ///$NON-NLS-1$
	
	/**
	 * Return the xmlKey property value.
	 * A null indicates it is not specified.
	 */
	ELXmlKey getXmlKey();
	
	/**
	 * Add (and return) an xmlKey property value.
	 * (Specifies the property)
	 */
	ELXmlKey addXmlKey();
	
	/**
	 * Remove the xmlKey property value.
	 * (Unspecifies the property)
	 */
	void removeXmlKey();
	
	
	// ***** misc *****
	
	/**
	 * Return an XPath to represent this attribute mapping.
	 * Return <code>null</code> if no valid XPath can be constructed.
	 * (Trivial if this mapping has an XmlPath annotation)
	 */
	String getXPath();
	Transformer<ELXmlNamedNodeMapping, String> X_PATH_TRANSFORMER = new XPathTransformer();
	class XPathTransformer
		extends TransformerAdapter<ELXmlNamedNodeMapping, String>
	{
		@Override
		public String transform(ELXmlNamedNodeMapping mapping) {
			return mapping.getXPath();
		}
	}
}
