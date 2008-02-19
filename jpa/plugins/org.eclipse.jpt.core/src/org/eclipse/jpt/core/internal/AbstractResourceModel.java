/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

public abstract class AbstractResourceModel
	extends AbstractModel
	implements IResourceModel
{
	private final List<IJpaStructureNode> rootStructureNodes;


	protected AbstractResourceModel() {
		this.rootStructureNodes = new ArrayList<IJpaStructureNode>();
	}

	public abstract Object resource();

	public ListIterator<IJpaStructureNode> rootStructureNodes() {
		return new CloneListIterator<IJpaStructureNode>(this.rootStructureNodes);
	}

	public int rootStructureNodesSize() {
		return this.rootStructureNodes.size();
	}

	/**
	 * Add the new node to the end of the list.
	 */
	public void addRootStructureNode(IJpaStructureNode structureNode) {
		this.addRootStructureNode(this.rootStructureNodes.size(), structureNode);
	}

	public void addRootStructureNode(int index, IJpaStructureNode structureNode) {
		if ( ! this.rootStructureNodes.contains(structureNode)) {
			this.addItemToList(index, structureNode, this.rootStructureNodes, ROOT_STRUCTURE_NODES_LIST);
		}
	}

	public void removeRootStructureNode(IJpaStructureNode structureNode) {
		this.removeItemFromList(structureNode, this.rootStructureNodes, ROOT_STRUCTURE_NODES_LIST);
	}

	public void removeRootStructureNode(int index) {
		this.removeItemFromList(index, this.rootStructureNodes, ROOT_STRUCTURE_NODES_LIST);
	}

	public IJpaStructureNode structureNode(int textOffset) {
		synchronized (this.rootStructureNodes) {
			for (IJpaStructureNode rootNode : this.rootStructureNodes) {
				IJpaStructureNode node = rootNode.structureNode(textOffset);
				if (node != null) {
					return node;
				}
			}
		}
		return null;
	}

	public void dispose() {
		this.clearList(this.rootStructureNodes, ROOT_STRUCTURE_NODES_LIST);
	}

}
