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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;

public class GenericJpaFile extends AbstractJpaNode implements JpaFile
{
	/**
	 * The IFile associated with this JPA file
	 */
	protected final IFile file;
	
	/**
	 * The resource model represented by this JPA file
	 */
	protected final ResourceModel resourceModel;
	
	/**
	 * The context model root node represented by this JPA file
	 */
	protected final Map<Object, JpaStructureNode> rootStructureNodes;
	
	public GenericJpaFile(JpaProject jpaProject, IFile file, ResourceModel resourceModel) {
		super(jpaProject);
		this.file = file;
		this.resourceModel = resourceModel;
		this.rootStructureNodes = new HashMap<Object, JpaStructureNode>();
	}

	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		//JpaFile.ROOT_STRUCTURE_NODES_COLLECTION does not need to trigger a project update, 
		//only the UI cares about the rootStructureNode.
		//If you do a project update on this you get in an infinite loop if you
		//specify a java class in more than one location in your persistence unit.
		nonUpdateAspectNames.add(JpaFile.ROOT_STRUCTURE_NODES_COLLECTION);
	}
	
	public IFile getFile() {
		return this.file;
	}
	
	public ResourceModel getResourceModel() {
		return this.resourceModel;
	}
	
	public Iterator<JpaStructureNode> rootStructureNodes() {
		return new CloneIterator<JpaStructureNode>(this.rootStructureNodes.values());
	}

	public int rootStructureNodesSize() {
		return this.rootStructureNodes.size();
	}
	
	public void addRootStructureNode(Object key, JpaStructureNode rootStructureNode) {
		if (this.rootStructureNodes.get(key) == rootStructureNode) {
			return;
		}
		if (this.rootStructureNodes.containsKey(key)) {
			JpaStructureNode removedStructureNode = this.rootStructureNodes.remove(key);
			fireItemRemoved(JpaFile.ROOT_STRUCTURE_NODES_COLLECTION, removedStructureNode);
		}
		this.rootStructureNodes.put(key, rootStructureNode);
		fireItemAdded(JpaFile.ROOT_STRUCTURE_NODES_COLLECTION, rootStructureNode);
	}
	
	public void removeRootStructureNode(Object key) {
		JpaStructureNode removedStructureNode = this.rootStructureNodes.remove(key);
		fireItemRemoved(JpaFile.ROOT_STRUCTURE_NODES_COLLECTION, removedStructureNode);
	}
	
	public JpaStructureNode getStructureNode(int textOffset) {
		for (JpaStructureNode rootNode : CollectionTools.iterable(rootStructureNodes())) {
			JpaStructureNode node = rootNode.getStructureNode(textOffset);
			if (node != null) {
				return node;
			}
		}
		return null;
	}
	
	public String getResourceType() {
		return getResourceModel().getResourceType();
	}
	
	public void dispose() {
		getResourceModel().dispose();
		
		Set<Object> keys = this.rootStructureNodes.keySet();
		for (Object key : keys) {
			removeRootStructureNode(key);
		}
	}
	
	public void javaElementChanged(ElementChangedEvent event) {
		getResourceModel().javaElementChanged(event);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getFile().toString());
		sb.append(" (resourceType: ");
		sb.append(getResourceType());
		sb.append(")");
	}

	public void updateFromResource() {
		this.resourceModel.updateFromResource();
	}

}
