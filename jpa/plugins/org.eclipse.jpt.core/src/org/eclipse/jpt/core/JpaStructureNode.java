/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.utility.TextRange;


/**
 * Implement this interface for objects that appear in the Structure view
 * This is used by JpaSelection to determine selection in the editor.
 * Details pages are also provided for each JpaStructureNode.
 * 
 * I did not implement JpaContextNode and I'm not even sure we should implement
 * JpaNode.  It is possibly someone could want a structure node that is
 * not actually a contextNode in the model.//TODO
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaStructureNode extends JpaNode
{
	/**
	 * Return the structure node at the given offset.
	 */
	JpaStructureNode getStructureNode(int textOffset);
	
	/**
	 * Return the text range to be used to select text in the editor
	 * corresponding to this node.
	 */
	TextRange getSelectionTextRange();
	
	/**
	 * Return a unique identifier for all of this class of structure nodes
	 */
	String getId();
	
	/**
	 * Return the content type of the structure node's resource.
	 * This is used to find the appropriate ui provider for building composites 
	 */
	IContentType getContentType();

	/**
	 * Dispose of this structureNode and dispose of child structureNodes.
	 * Typically this would be used to update the JpaFile rootStructureNodes.
	 */
	void dispose();
}
