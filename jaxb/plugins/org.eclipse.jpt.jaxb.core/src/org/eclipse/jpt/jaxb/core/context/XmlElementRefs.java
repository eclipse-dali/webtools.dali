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
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;


public interface XmlElementRefs
		extends JavaContextNode {
	
	// ***** XmlElementRefs *****
	
	String XML_ELEMENT_REFS_LIST = "xmlElementRefs"; //$NON-NLS-1$
	
	ListIterable<XmlElementRef> getXmlElementRefs();
	
	int getXmlElementRefsSize();
	
	XmlElementRef addXmlElementRef(int index);
	
	void removeXmlElementRef(int index);
	
	void removeXmlElementRef(XmlElementRef xmlElementRef);
	
	void moveXmlElementRef(int targetIndex, int sourceIndex);
	
	
	// ***** misc *****
	
	/**
	 * Return all type names that are directly referenced (for purposes of building a JAXB context)
	 */
	Iterable<String> getDirectlyReferencedTypeNames();
}
