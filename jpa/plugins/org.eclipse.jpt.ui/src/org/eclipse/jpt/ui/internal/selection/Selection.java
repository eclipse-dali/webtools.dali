/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.jpt.core.internal.IJpaContentNode;


public class Selection 
{
	public static Selection NULL_SELECTION = new Selection();
	
	private IJpaContentNode selectedNode;
	
	
	/* Used internally - only for NULL_SELECTION */
	private Selection() {}
	
	public Selection(IJpaContentNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Must have non-null node.  Use NULL_SELECTION otherwise.");
		}
		
		selectedNode = node;
	}
	
	public IJpaContentNode getSelectedNode() {
		return selectedNode;
	}
	
	public boolean equals(Object obj) {
		if (! (obj instanceof Selection)) {
			return false;
		}
		
		if (this == NULL_SELECTION && obj == NULL_SELECTION) {
			return true;
		}
		
		if (this == NULL_SELECTION || obj == NULL_SELECTION) {
			return false;
		}
		
		return this.selectedNode.equals(((Selection) obj).selectedNode);
	}
	
	public int hashCode() {
		if (this == NULL_SELECTION) {
			return super.hashCode();
		}
		else {
			return this.selectedNode.hashCode();
		}
	}
	
	public String toString() {
		if (this == NULL_SELECTION) {
			return "NULL_SELECTION";
		}
		else {
			return selectedNode.toString();
		}
	}
}
