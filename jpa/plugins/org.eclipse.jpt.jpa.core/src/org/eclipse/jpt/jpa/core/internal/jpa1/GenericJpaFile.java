/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaModel;

/**
 * The transition between a JPA project and the resource model associated
 * with a file.
 * Hold the associated root structure nodes, which are hooks to the
 * context model.
 */
public class GenericJpaFile
	extends AbstractJpaModel<JpaProject>
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
	protected final HashSet<JpaStructureNode> rootStructureNodes = new HashSet<>();


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
		return IterableTools.cloneLive(this.rootStructureNodes);
	}

	public int getRootStructureNodesSize() {
		return this.rootStructureNodes.size();
	}

	protected PersistenceXml getPersistenceXml() {
		return this.parent.getContextRoot().getPersistenceXml();
	}

	/**
	 * <ul>
	 * <li>The JPA file for a <code>persistence.xml</code> will have one root
	 * structure node: <code>Persistence</code>
	 * <li>The JPA file for an <code>orm.xml</code> file will have one root
	 * structure node: <code>EntityMappings</code>
	 * <li>The JPA file for a java file can have multiple root structure nodes
	 * only if there are static member classes (in EclipseLink!).
	 * The top-level class and member
	 * classes will be included only if they are either annotated or listed in
	 * the <code>persistence.xml</code>. The root structure nodes will be
	 * instances of <code>JavaPersistentType</code>.
	 * </ul>
	 * If a class is listed in both the <code>persistence.xml</code> and an
	 * <code>orm.xml</code> file, the root structure node will be the
	 * <code>JavaPersistentType</code> that was built by the
	 * <code>OrmPersistentType</code> listed in the <code>orm.xml</code> file.
	 * Mapping files will take precendence over the class ref in the
	 * <code>persistence.xml</code>. Likewise, if the same class is listed in
	 * multiple mapping files, only the first <code>JavaPersistentType</code>
	 * will be added to the list of root structure nodes.
	 * There are validation warnings for these scenarios.
	 * <br><br>
	 * TODO we have discussed having a "primary" root node along with a
	 * collection of "secondary" root nodes that includes all the
	 * <code>JavaPersistentType</code>s described above.
	 */
	public void updateRootStructureNodes() {
		PersistenceXml persistenceXml = this.getPersistenceXml();
		if (persistenceXml == null) {
			this.clearCollection(this.rootStructureNodes, ROOT_STRUCTURE_NODES_COLLECTION);
			return;
		}
		Collection<JpaStructureNode> newRootStructureNodes = new HashSet<>();
		persistenceXml.addRootStructureNodesTo(this, newRootStructureNodes);
		this.synchronizeCollection(newRootStructureNodes, this.rootStructureNodes, ROOT_STRUCTURE_NODES_COLLECTION);
	}

	/**
	 * If we have multiple root nodes, it's reasonable to assume their text
	 * ranges are either exclusive or nested; therefore we need only find the
	 * closest text range "start".
	 */
	public JpaStructureNode getStructureNode(int textOffset) {
		JpaStructureNode closestNode = null;
		int closestDistance = -1;
		for (JpaStructureNode currentNode : this.getRootStructureNodes()) {
			if (currentNode.containsOffset(textOffset)) {
				int currentDistance = textOffset - currentNode.getFullTextRange().getOffset();
				if (closestNode == null) {
					closestNode = currentNode;
					closestDistance = currentDistance;
				} else {
					if (currentDistance < closestDistance) {
						closestNode = currentNode;
						closestDistance = currentDistance;
					}
				}
			}
		}
		return (closestNode == null) ? null : closestNode.getStructureNode(textOffset);
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
