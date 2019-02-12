/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Represents a JAXB xml elements mapping (@XmlElements)
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
public interface XmlElementsMapping
		extends JaxbAttributeMapping, XmlAdaptableMapping {
	
	// ***** XmlElements *****
	
	String XML_ELEMENTS_LIST = "xmlElements"; //$NON-NLS-1$
	
	ListIterable<XmlElement> getXmlElements();
	
	int getXmlElementsSize();
	
	XmlElement addXmlElement(int index);
	
	void removeXmlElement(int index);
	
	void removeXmlElement(XmlElement xmlElement);
	
	void moveXmlElement(int targetIndex, int sourceIndex);
	
	
	// ***** XmlElementWrapper *****
	
	String XML_ELEMENT_WRAPPER_PROPERTY = "xmlElementWrapper"; //$NON-NLS-1$
	
	XmlElementWrapper getXmlElementWrapper();
	
	XmlElementWrapper addXmlElementWrapper();
	
	void removeXmlElementWrapper();
	
	
	// ***** XmlIDREF *****
	
	String XML_IDREF_PROPERTY = "xmlIDREF"; //$NON-NLS-1$
	
	XmlIDREF getXmlIDREF();
	
	XmlIDREF addXmlIDREF();
	
	void removeXmlIDREF();
}
