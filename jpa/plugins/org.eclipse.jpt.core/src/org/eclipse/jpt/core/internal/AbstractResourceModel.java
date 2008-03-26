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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

public abstract class AbstractResourceModel
	extends AbstractModel
	implements ResourceModel
{
	private final List<JpaStructureNode> rootStructureNodes;
	
	
	protected AbstractResourceModel() {
		this.rootStructureNodes = new ArrayList<JpaStructureNode>();
	}
	
	public abstract Object getResource();
	
	public ListIterator<JpaStructureNode> rootStructureNodes() {
		return new CloneListIterator<JpaStructureNode>(rootStructureNodes);
	}
	
	public int rootStructureNodesSize() {
		return rootStructureNodes.size();
	}
	
	/**
	 * Add the new node to the end of the list.
	 */
	public void addRootStructureNode(JpaStructureNode structureNode) {
		this.addRootStructureNode(rootStructureNodes.size(), structureNode);
	}
	
	public void addRootStructureNode(int index, JpaStructureNode structureNode) {
		if ( ! rootStructureNodes.contains(structureNode)) {
			this.addItemToList(index, structureNode, this.rootStructureNodes, ROOT_STRUCTURE_NODES_LIST);
		}
	}
	
	public void removeRootStructureNode(JpaStructureNode structureNode) {
		this.removeItemFromList(structureNode, rootStructureNodes, ROOT_STRUCTURE_NODES_LIST);
	}
	
	public void removeRootStructureNode(int index) {
		this.removeItemFromList(index, rootStructureNodes, ROOT_STRUCTURE_NODES_LIST);
	}
	
	public JpaStructureNode structureNode(int textOffset) {
		synchronized (rootStructureNodes) {
			for (JpaStructureNode rootNode : rootStructureNodes) {
				JpaStructureNode node = rootNode.structureNode(textOffset);
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
