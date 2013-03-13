/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;

/**
 * Represents an Oxm XmlJoinNodes attribute mapping in either
 * java annotations or oxm file.
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
public interface ELXmlJoinNodesMapping
		extends JaxbAttributeMapping {
	
	
	// ***** XmlJoinNodes *****
	
	String XML_JOIN_NODES_LIST = "xmlJoinNodes"; //$NON-NLS-1$
	
	ListIterable<ELXmlJoinNode> getXmlJoinNodes();
	
	int getXmlJoinNodesSize();
	
	ELXmlJoinNode addXmlJoinNode(int index);
	
	void removeXmlJoinNode(int index);
	
	void moveXmlJoinNode(int targetIndex, int sourceIndex);
	
	
	// ***** misc *****
	
	ELClassMapping getReferencedClassMapping();
	
	XsdTypeDefinition getReferencedXsdTypeDefinition();
}
