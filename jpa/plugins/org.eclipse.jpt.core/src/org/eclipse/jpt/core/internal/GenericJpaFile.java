/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;

/**
 * The transition between a JPA project and the resource model associated
 * with a file.
 * Hold the associated root structure nodes, which are hooks to the
 * context model.
 */
public class GenericJpaFile
	extends AbstractJpaNode
	implements JpaFile
{
	/**
	 * typically a .java or .xml file.
	 */
	protected final IFile file;

	/**
	 * cache the content type
	 */
	protected final IContentType contentType;

	/**
	 * the resource model corresponding to the file
	 */
	protected final JpaResourceModel resourceModel;

	/**
	 * the root structure (context model) nodes corresponding to the resource
	 * model
	 */
	protected final Hashtable<Object, JpaStructureNode> rootStructureNodes;


	// ********** construction **********

	public GenericJpaFile(JpaProject jpaProject, IFile file, IContentType contentType, JpaResourceModel resourceModel) {
		super(jpaProject);
		this.file = file;
		this.contentType = contentType;
		this.resourceModel = resourceModel;
		this.rootStructureNodes = new Hashtable<Object, JpaStructureNode>();
	}

	/**
	 * Changes to ROOT_STRUCTURE_NODES_COLLECTION do not need to trigger a
	 * project update. Only the UI cares about the root structure nodes.
	 * If a project update is allowed to happen, an infinite loop will result
	 * if any java class is specified in more than one location in the
	 * persistence unit.
	 */
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(ROOT_STRUCTURE_NODES_COLLECTION);
	}


	// ********** file **********

	public IFile getFile() {
		return this.file;
	}

	public IContentType getContentType() {
		return this.contentType;
	}

	public JpaResourceModel getResourceModel() {
		return this.resourceModel;
	}

	public JpaResourceModel getResourceModel(IContentType ct) {
		return this.contentType.isKindOf(ct) ? this.resourceModel : null;
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
		sb.append(this.contentType.getName());
		sb.append(']');
	}

}
