/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.jpt.core.utility.TextRange;


/**
 * Implement this interface for objects that appear in the Structure view
 * This is used by JpaSelection to determine selection in the editor.
 * Details pages are also provided for each IJpaStructureNode.
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
	JpaStructureNode structureNode(int textOffset);
	
	/**
	 * Return the text range do be used to select text in the editor
	 * corresponding to this node.
	 */
	TextRange getSelectionTextRange();
	
	/**
	 * Return a unique identifier for all of this class of structure nodes
	 */
	String getId();
}
