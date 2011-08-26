/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Represents a JAXB xml element refs mapping (@XmlElementRefs)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public interface XmlElementRefsMapping
		extends JaxbAttributeMapping, XmlAdaptable {
	
	// ***** XmlElementRefs *****
	
	String XML_ELEMENT_REFS_LIST = "xmlElementRefs"; //$NON-NLS-1$
	
	ListIterable<XmlElementRef> getXmlElementRefs();
	
	int getXmlElementRefsSize();
	
	XmlElementRef addXmlElementRef(int index);
	
	void removeXmlElementRef(int index);
	
	void removeXmlElementRef(XmlElementRef xmlElementRef);
	
	void moveXmlElementRef(int targetIndex, int sourceIndex);
	
	
	// ***** XmlElementWrapper *****
	
	String XML_ELEMENT_WRAPPER_PROPERTY = "xmlElementWrapper"; //$NON-NLS-1$
	
	XmlElementWrapper getXmlElementWrapper();
	
	XmlElementWrapper addXmlElementWrapper();
	
	void removeXmlElementWrapper();
	
	
	// ***** XmlMixed *****
	
	String XML_MIXED_PROPERTY = "xmlMixed"; //$NON-NLS-1$
	
	XmlMixed getXmlMixed();
	
	XmlMixed addXmlMixed();
	
	void removeXmlMixed();
}
