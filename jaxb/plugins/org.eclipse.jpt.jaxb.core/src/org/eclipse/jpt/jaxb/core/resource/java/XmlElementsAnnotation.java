/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlElement
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
public interface XmlElementsAnnotation
		extends Annotation {
	
	/**
	 * Corresponds to the 'value' element of the XmlElements annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<XmlElementAnnotation> getXmlElements();
		String XML_ELEMENTS_LIST = "xmlElements"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the XmlElements annotation.
	 */
	int getXmlElementsSize();
	
	/**
	 * Corresponds to the 'value' element of the XmlElements annotation.
	 */
	XmlElementAnnotation xmlElementAt(int index);
	
	/**
	 * Corresponds to the 'value' element of the XmlElements annotation.
	 */
	XmlElementAnnotation addXmlElement(int index);
	
	/**
	 * Corresponds to the 'value' element of the XmlElements annotation.
	 */
	void moveXmlElement(int targetIndex, int sourceIndex);
	
	/**
	 * Corresponds to the 'value' element of the XmlElements annotation.
	 */
	void removeXmlElement(int index);
}
