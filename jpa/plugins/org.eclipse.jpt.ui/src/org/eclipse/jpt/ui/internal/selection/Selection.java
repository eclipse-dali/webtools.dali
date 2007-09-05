/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.jpt.core.internal.IJpaContentNode;


public class Selection 
{
	public static Selection NULL_SELECTION = new Selection();
	
	private IJpaContentNode selectedNode;
	
	
	/* Used internally - only for NULL_SELECTION */
	private Selection() {
		super();
	}
	
	public Selection(IJpaContentNode selectedNode) {
		if (selectedNode == null) {
			throw new NullPointerException("A 'selectedNode' is required; otherwise use NULL_SELECTION.");
		}
		this.selectedNode = selectedNode;
	}
	
	public IJpaContentNode getSelectedNode() {
		return selectedNode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Selection)) {
			return false;
		}
		
		if ((this == NULL_SELECTION) && (obj == NULL_SELECTION)) {
			return true;
		}
		
		if ((this == NULL_SELECTION) || (obj == NULL_SELECTION)) {
			return false;
		}
		
		return this.selectedNode.equals(((Selection) obj).selectedNode);
	}
	
	@Override
	public int hashCode() {
		return (this == NULL_SELECTION) ?
			super.hashCode()
		:
			this.selectedNode.hashCode();
	}

	@Override
	public String toString() {
		return (this == NULL_SELECTION) ?
			"NULL_SELECTION"
		:
			selectedNode.toString();
	}

}
