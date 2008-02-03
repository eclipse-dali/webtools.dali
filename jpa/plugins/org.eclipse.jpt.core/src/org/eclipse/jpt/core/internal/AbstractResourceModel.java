package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

public abstract class AbstractResourceModel extends AbstractModel
	implements IResourceModel
{
	private final List<IJpaContextNode> rootContextNodes;
	
	
	protected AbstractResourceModel() {
		this.rootContextNodes = new ArrayList<IJpaContextNode>();
	}
	
	
	public abstract Object resource();
	
	public ListIterator<IJpaContextNode> rootContextNodes() {
		return new CloneListIterator<IJpaContextNode>(rootContextNodes);
	}
	
	public void addRootContextNode(IJpaContextNode contextNode) {
		addRootContextNode(rootContextNodes.size(), contextNode);
	}
	
	public void addRootContextNode(int index, IJpaContextNode contextNode) {
		if (! rootContextNodes.contains(contextNode)) {
			rootContextNodes.add(index, contextNode);
			fireItemAdded(ROOT_CONTEXT_NODE_LIST, index, contextNode); 
		}
	}
	
	public void removeRootContextNode(IJpaContextNode contextNode) {
		if (rootContextNodes.contains(contextNode)) {
			removeRootContextNode(rootContextNodes.indexOf(contextNode));
		}
	}
	
	public void removeRootContextNode(int index) {
		IJpaContextNode contextNode = rootContextNodes.remove(index);
		fireItemRemoved(ROOT_CONTEXT_NODE_LIST, index, contextNode);
	}
	
	public void dispose() {
		this.rootContextNodes.clear();
	}
}
