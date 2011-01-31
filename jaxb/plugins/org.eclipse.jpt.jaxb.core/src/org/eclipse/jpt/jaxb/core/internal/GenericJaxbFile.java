/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;

/**
 * The transition between a JAXB project and the resource model associated
 * with a file.
 * Hold the associated root structure nodes, which are hooks to the
 * context model.
 */
public class GenericJaxbFile
	extends AbstractJaxbNode
	implements JaxbFile
{
	/**
	 * typically a .java or .xml file.
	 */
	protected final IFile file;

	/**
	 * cache the content type - if the content type changes, the JAXB project
	 * will throw out the JAXB file and build a new one
	 */
	protected final IContentType contentType;

	/**
	 * the resource model corresponding to the file
	 */
	protected final JptResourceModel resourceModel;

//	/**
//	 * the root structure (context model) nodes corresponding to the resource
//	 * model
//	 */
//	protected final Hashtable<Object, JpaStructureNode> rootStructureNodes;


	// ********** construction **********

	public GenericJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType, JptResourceModel resourceModel) {
		super(jaxbProject);
		this.file = file;
		this.contentType = contentType;
		this.resourceModel = resourceModel;
//		this.rootStructureNodes = new Hashtable<Object, JpaStructureNode>();
	}
//
//	/**
//	 * Changes to ROOT_STRUCTURE_NODES_COLLECTION do not need to trigger a
//	 * project update. Only the UI cares about the root structure nodes.
//	 * If a project update is allowed to happen, an infinite loop will result
//	 * if any java class is specified in more than one location in the
//	 * persistence unit.
//	 */
//	@Override
//	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
//		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
//		nonUpdateAspectNames.add(ROOT_STRUCTURE_NODES_COLLECTION);
//	}


	// ********** file **********

	public IFile getFile() {
		return this.file;
	}

	public IContentType getContentType() {
		return this.contentType;
	}

	public JptResourceModel getResourceModel() {
		return this.resourceModel;
	}

	public JptResourceModel getResourceModel(IContentType ct) {
		return this.contentType.isKindOf(ct) ? this.resourceModel : null;
	}


//	// ********** root structure nodes **********
//
//	public Iterator<JpaStructureNode> rootStructureNodes() {
//		return this.getRootStructureNodes().iterator();
//	}
//
//	protected Iterable<JpaStructureNode> getRootStructureNodes() {
//		return new LiveCloneIterable<JpaStructureNode>(this.rootStructureNodes.values());
//	}
//
//	public int rootStructureNodesSize() {
//		return this.rootStructureNodes.size();
//	}
//
//	public void addRootStructureNode(Object key, JpaStructureNode rootStructureNode) {
//		JpaStructureNode old = this.rootStructureNodes.put(key, rootStructureNode);
//		if (rootStructureNode != old) {
//			if (old != null) {
//				this.fireItemRemoved(ROOT_STRUCTURE_NODES_COLLECTION, old);
//			}
//			this.fireItemAdded(ROOT_STRUCTURE_NODES_COLLECTION, rootStructureNode);
//		}
//	}
//
//	public void removeRootStructureNode(Object key) {
//		this.fireItemRemoved(ROOT_STRUCTURE_NODES_COLLECTION, this.rootStructureNodes.remove(key));
//	}
//
//	public JpaStructureNode getStructureNode(int textOffset) {
//		for (JpaStructureNode rootNode : this.getRootStructureNodes()) {
//			JpaStructureNode node = rootNode.getStructureNode(textOffset);
//			if (node != null) {
//				return node;
//			}
//		}
//		return null;
//	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.file);
		sb.append('[');
		sb.append(this.contentType.getName());
		sb.append(']');
	}

}
