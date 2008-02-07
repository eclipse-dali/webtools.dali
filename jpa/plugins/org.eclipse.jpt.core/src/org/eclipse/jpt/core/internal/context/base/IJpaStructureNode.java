/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.core.internal.ITextRange;

/**
 * Implement this interface for objects that appear in the Structure view
 * This is used by IJpaSelection to determine selection in the editor.
 * Details pages are also provided for each IJpaStructureNode.
 * 
 * I did not implement IJpaContextNode and I'm not even sure we should implement
 * IJpaNode.  It is possibly someone could want a structure node that is
 * not actually a contextNode in the model.//TODO
 */
public interface IJpaStructureNode extends IJpaNode
{
	/**
	 * Return the structure node at the given offset.
	 */
	IJpaStructureNode structureNode(int textOffset);
	
	/**
	 * Return the text range do be used to select text in the editor
	 * corresponding to this node.
	 */
	ITextRange selectionTextRange();
	
	/**
	 * Return a unique identifier for all of this class of structure nodes
	 */
	String getId();
}
