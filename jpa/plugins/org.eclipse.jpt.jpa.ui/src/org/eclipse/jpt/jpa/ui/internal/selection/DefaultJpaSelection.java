/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import org.eclipse.jpt.jpa.core.JpaStructureNode;

/**
 * Straightforward implementation of the JpaSelection interface.
 */
public class DefaultJpaSelection
	implements JpaSelection
{
	private final JpaStructureNode selectedNode;


	public DefaultJpaSelection(JpaStructureNode selectedNode) {
		super();
		if (selectedNode == null) {
			throw new NullPointerException("A 'selectedNode' is required; otherwise use NULL_SELECTION.");
		}
		this.selectedNode = selectedNode;
	}

	public JpaStructureNode getSelectedNode() {
		return this.selectedNode;
	}

	public boolean isEmpty() {
		// by definition, this selection is never empty
		// use IJpaSelection.NULL_SELECTION for empty selections
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof JpaSelection)) {
			return false;
		}
		JpaSelection other = (JpaSelection) obj;
		return ( ! other.isEmpty()) && this.selectedNode.equals(other.getSelectedNode());
	}

	@Override
	public int hashCode() {
		return this.selectedNode.hashCode();
	}

	@Override
	public String toString() {
		return this.selectedNode.toString();
	}

}
