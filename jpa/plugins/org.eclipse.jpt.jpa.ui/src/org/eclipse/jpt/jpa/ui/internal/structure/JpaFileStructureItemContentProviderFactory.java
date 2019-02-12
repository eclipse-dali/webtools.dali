/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.internal.jface.ModelItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemTreeContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;

/**
 * JPA Structure View content provider factory
 */
public class JpaFileStructureItemContentProviderFactory
	implements ItemTreeContentProvider.Factory
{
	// singleton
	private static final ItemTreeContentProvider.Factory INSTANCE = new JpaFileStructureItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProvider.Factory instance() {
		return INSTANCE;
	}


	protected JpaFileStructureItemContentProviderFactory() {
		super();
	}

	public ItemStructuredContentProvider buildProvider(Object input, ItemStructuredContentProvider.Manager manager) {
		return this.buildJpaFileProvider((JpaFile) input, manager);			
	}

	protected ItemStructuredContentProvider buildJpaFileProvider(JpaFile jpaFile, ItemStructuredContentProvider.Manager manager) {
		return new ModelItemStructuredContentProvider(jpaFile, this.buildJpaFileChildrenModel(jpaFile), manager);
	}

	protected CollectionValueModel<JpaStructureNode> buildJpaFileChildrenModel(JpaFile jpaFile) {
		return new JpaFileChildrenModel(jpaFile);
	}

	public static class JpaFileChildrenModel
		extends CollectionAspectAdapter<JpaFile, JpaStructureNode>
	{
		public JpaFileChildrenModel(JpaFile jpaFile) {
			super(JpaFile.ROOT_STRUCTURE_NODES_COLLECTION, jpaFile);
		}

		@Override
		protected Iterable<JpaStructureNode> getIterable() {
			return this.subject.getRootStructureNodes();
		}

		@Override
		public int size_() {
			return this.subject.getRootStructureNodesSize();
		}
	}

	public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
		if (item instanceof JpaStructureNode) {
			return this.buildJpaStructureNodeProvider((JpaStructureNode) item, parent, manager);
		}
		return NullItemTreeContentProvider.instance();
	}

	protected ItemTreeContentProvider buildJpaStructureNodeProvider(JpaStructureNode node, Object parent, ItemTreeContentProvider.Manager manager) {
		return new ModelItemTreeContentProvider(node, parent, this.buildJpaStructureNodeChildrenModel(node), manager);
	}

	protected CollectionValueModel<JpaStructureNode> buildJpaStructureNodeChildrenModel(JpaStructureNode node) {
		return new JpaStructureNodeChildrenModel(node);
	}

	public static class JpaStructureNodeChildrenModel
		extends CollectionAspectAdapter<JpaStructureNode, JpaStructureNode>
	{
		public JpaStructureNodeChildrenModel(JpaStructureNode node) {
			super(JpaStructureNode.STRUCTURE_CHILDREN_COLLECTION, node);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Iterable<JpaStructureNode> getIterable() {
			return (Iterable<JpaStructureNode>) this.subject.getStructureChildren();
		}

		@Override
		public int size_() {
			return this.subject.getStructureChildrenSize();
		}
	}
}
