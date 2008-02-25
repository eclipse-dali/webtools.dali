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
package org.eclipse.jpt.core;


/**
 * Implement this interface for objects that appear in the Structure view
 * This is used by JpaSelection to determine selection in the editor.
 * Details pages are also provided for each IJpaStructureNode.
 * 
 * I did not implement JpaContextNode and I'm not even sure we should implement
 * JpaNode.  It is possibly someone could want a structure node that is
 * not actually a contextNode in the model.//TODO
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
	TextRange selectionTextRange();
	
	/**
	 * Return a unique identifier for all of this class of structure nodes
	 */
	String getId();
}
