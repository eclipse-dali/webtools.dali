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
