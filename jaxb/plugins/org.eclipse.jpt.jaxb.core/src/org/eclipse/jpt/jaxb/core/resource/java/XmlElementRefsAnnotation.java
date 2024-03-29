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
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlElementRefs
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
public interface XmlElementRefsAnnotation
		extends Annotation {
	
	/**
	 * Corresponds to the 'value' element of the XmlElementRefs annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<XmlElementRefAnnotation> getXmlElementRefs();
		String XML_ELEMENT_REFS_LIST = "xmlElementRefs"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the XmlElementRefs annotation.
	 */
	int getXmlElementRefsSize();
	
	/**
	 * Corresponds to the 'value' element of the XmlElementRefs annotation.
	 */
	XmlElementRefAnnotation xmlElementRefAt(int index);
	
	/**
	 * Corresponds to the 'value' element of the XmlElementRefs annotation.
	 */
	XmlElementRefAnnotation addXmlElementRef(int index);
	
	/**
	 * Corresponds to the 'value' element of the XmlElementRefs annotation.
	 */
	void moveXmlElementRef(int targetIndex, int sourceIndex);
	
	/**
	 * Corresponds to the 'value' element of the XmlElementRefs annotation.
	 */
	void removeXmlElementRef(int index);
}
