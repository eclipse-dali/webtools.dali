/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.ResourceModelListener;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;

/**
 * The transition between a JPA project and the resource model associated
 * with a file.
 * Maintain a list of resource model listeners (namely, the JPA project).
 * Hold the associated root structure nodes, which are hooks to the
 * context model.
 */
public abstract class AbstractJpaFile
	extends AbstractJpaNode
	implements JpaFile
{
	private final IFile file;

	private final Hashtable<Object, JpaStructureNode> rootStructureNodes;

	private final ResourceModelListener resourceModelListener;

	private final ListenerList<ResourceModelListener> resourceModelListenerList;


	// ********** construction **********

	protected AbstractJpaFile(JpaProject jpaProject, IFile file) {
		super(jpaProject);
		this.file = file;
		this.rootStructureNodes = new Hashtable<Object, JpaStructureNode>();
		this.resourceModelListener = this.buildResourceModelListener();
		this.resourceModelListenerList = new ListenerList<ResourceModelListener>(ResourceModelListener.class);
	}

	/**
	 * Changes to ROOT_STRUCTURE_NODES_COLLECTION do not need to trigger a
	 * project update. Only the UI cares about the root structure nodes.
	 * If an project update is allowed to happen, an infinite loop will result
	 * if any java class is specified in more than one location in the
	 * persistence unit.
	 */
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(ROOT_STRUCTURE_NODES_COLLECTION);
	}

	protected ResourceModelListener buildResourceModelListener() {
		return new ResourceModelListener() {
			public void resourceModelChanged() {
				AbstractJpaFile.this.resourceModelChanged();
			}
		};
	}


	// ********** file **********

	public IFile getFile() {
		return this.file;
	}


	// ********** resource model listeners **********

	protected ResourceModelListener getResourceModelListener() {
		return this.resourceModelListener;
	}

	public void addResourceModelListener(ResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(ResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}

	protected void resourceModelChanged() {
		for (ResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged();
		}
	}


	// ********** root structure nodes **********

	public Iterator<JpaStructureNode> rootStructureNodes() {
		return new CloneIterator<JpaStructureNode>(this.rootStructureNodes.values());
	}

	public int rootStructureNodesSize() {
		return this.rootStructureNodes.size();
	}

	public void addRootStructureNode(Object key, JpaStructureNode rootStructureNode) {
		JpaStructureNode old = this.rootStructureNodes.put(key, rootStructureNode);
		if (rootStructureNode != old) {
			if (old != null) {
				this.fireItemRemoved(ROOT_STRUCTURE_NODES_COLLECTION, old);
			}
			this.fireItemAdded(ROOT_STRUCTURE_NODES_COLLECTION, rootStructureNode);
		}
	}

	public void removeRootStructureNode(Object key) {
		this.fireItemRemoved(ROOT_STRUCTURE_NODES_COLLECTION, this.rootStructureNodes.remove(key));
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		synchronized (this.rootStructureNodes) {
			for (JpaStructureNode rootNode : this.rootStructureNodes.values()) {
				JpaStructureNode node = rootNode.getStructureNode(textOffset);
				if (node != null) {
					return node;
				}
			}
		}
		return null;
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.file);
		sb.append('[');
		sb.append(this.getResourceType());
		sb.append(']');
	}

}
