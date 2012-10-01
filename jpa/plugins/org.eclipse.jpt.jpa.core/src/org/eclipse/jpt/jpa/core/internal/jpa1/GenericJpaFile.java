/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaNode;

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
	 * cache the content type - if the content type changes, the JPA project
	 * will throw out the JPA file and build a new one
	 */
	protected final IContentType contentType;

	/**
	 * the resource model corresponding to the file
	 */
	protected final JptResourceModel resourceModel;

	/**
	 * the root structure (context model) nodes corresponding to the resource
	 * model
	 */
	protected final HashSet<JpaStructureNode> rootStructureNodes = new HashSet<JpaStructureNode>();


	// ********** construction **********

	public GenericJpaFile(JpaProject jpaProject, IFile file, IContentType contentType, JptResourceModel resourceModel) {
		super(jpaProject);
		this.file = file;
		this.contentType = contentType;
		this.resourceModel = resourceModel;
	}

	/**
	 * Changes to {@link #ROOT_STRUCTURE_NODES_COLLECTION} do not need to trigger a
	 * project update. Only the UI cares about the root structure nodes.
	 */
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(ROOT_STRUCTURE_NODES_COLLECTION);
	}

	@Override
	public JpaProject getParent() {
		return (JpaProject) super.getParent();
	}


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


	// ********** root structure nodes **********

	public Iterable<JpaStructureNode> getRootStructureNodes() {
		return new LiveCloneIterable<JpaStructureNode>(this.rootStructureNodes);
	}

	public int getRootStructureNodesSize() {
		return this.rootStructureNodes.size();
	}

	protected PersistenceXml getPersistenceXml() {
		return this.getParent().getRootContextNode().getPersistenceXml();
	}

	/**
	 * <li>The JPA file for a persistence.xml will have one root structure node: Persistence
	 * <li>The JPA file for an orm xml file will have one root structure node: EntityMappings
	 * <li>The JPA file for a java file can have multiple root structure nodes only if there
	 * are inner classes. The top-level class and inner classes will be included only 
	 * if they are either annotated or listed in the persistence.xml. The root structure
	 * node will be an instanceof JavaPersistentType
	 * <br><br>
	 * If a class is listed in both the persistence.xml and also in an orm.xml file
	 * the root structure node will be the JavaPersistentType that was built by the OrmPersistentType
	 * listed in the orm.xml file. Mapping files will take precendence over the class-ref
	 * in the persistence.xml, but you will currently only have 1 root structure node. There
	 * are validation warnings for these scenarios.
	 * <br><br>
	 * TODO we have discussed having a "primary root" node along with a collection of
	 * root structure nodes that includes all the JavaPersistentTypes as they are
	 * listed via the persistence.xml class-refs, any of the mapping-file-refs, or the jar-file-refs.
	 * <br><br>
	 * bug 390616 is about selection  problems when there are static inner classes. When
	 * fixing this bug we need to make sure to handle the possibility that there will
	 * be an unannotated top-level class with an annotated static inner class. Would like
	 * to change the inner classes to not be root structure nodes, but instead be 
	 * nested under the top-level class in the structure view. 
	 */
	public void updateRootStructureNodes() {
		PersistenceXml persistenceXml = this.getPersistenceXml();
		if (persistenceXml == null) {
			this.clearCollection(this.rootStructureNodes, ROOT_STRUCTURE_NODES_COLLECTION);
			return;
		}
		Collection<JpaStructureNode> newRootStructureNodes = new HashSet<JpaStructureNode>();
		persistenceXml.gatherRootStructureNodes(this, newRootStructureNodes);
		this.synchronizeCollection(newRootStructureNodes, this.rootStructureNodes, ROOT_STRUCTURE_NODES_COLLECTION);
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (JpaStructureNode rootNode : this.getRootStructureNodes()) {
			JpaStructureNode node = rootNode.getStructureNode(textOffset);
			if (node != null) {
				return node;
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
