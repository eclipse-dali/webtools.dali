/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.emfutility;

import org.w3c.dom.Node;

public class DOMUtilities
	extends org.eclipse.wst.common.internal.emf.utilities.DOMUtilities
{
	/**
	 * Get the attribute Node with the specified name 
	 */
	static public Node getChildAttributeNode(Node node, String attributeName) {
		return node.getAttributes().getNamedItem(attributeName);
	}
}
