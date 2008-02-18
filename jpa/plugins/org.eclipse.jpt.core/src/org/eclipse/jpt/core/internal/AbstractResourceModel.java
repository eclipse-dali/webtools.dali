package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

public abstract class AbstractResourceModel extends AbstractModel
	implements IResourceModel
{
	private final List<IJpaStructureNode> rootStructureNodes;
	
	
	protected AbstractResourceModel() {
		this.rootStructureNodes = new ArrayList<IJpaStructureNode>();
	}
	
	
	public abstract Object resource();
	
	public ListIterator<IJpaStructureNode> rootStructureNodes() {
		return new CloneListIterator<IJpaStructureNode>(rootStructureNodes);
	}
	
	public void addRootStructureNode(IJpaStructureNode contextNode) {
		addRootStructureNode(rootStructureNodes.size(), contextNode);
	}
	
	public void addRootStructureNode(int index, IJpaStructureNode contextNode) {
		if (! rootStructureNodes.contains(contextNode)) {
			rootStructureNodes.add(index, contextNode);
			fireItemAdded(ROOT_STRUCTURE_NODE_LIST, index, contextNode); 
		}
	}
	
	public void removeRootStructureNode(IJpaStructureNode contextNode) {
		if (rootStructureNodes.contains(contextNode)) {
			removeRootStructureNode(rootStructureNodes.indexOf(contextNode));
		}
	}
	
	public void removeRootStructureNode(int index) {
		IJpaStructureNode contextNode = rootStructureNodes.remove(index);
		fireItemRemoved(ROOT_STRUCTURE_NODE_LIST, index, contextNode);
	}
	
	public IJpaStructureNode structureNode(int textOffset) {
		for (IJpaStructureNode rootNode : CollectionTools.iterable(rootStructureNodes())) {
			IJpaStructureNode node = rootNode.structureNode(textOffset);
			if (node != null) {
				return node;
			}
		}
		return null;
	}
	
	public void dispose() {
		this.rootStructureNodes.clear();
	}
}
