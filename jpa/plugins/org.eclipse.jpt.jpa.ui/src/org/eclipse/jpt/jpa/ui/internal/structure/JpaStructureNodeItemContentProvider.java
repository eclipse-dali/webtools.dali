/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.XmlFile;

public class JpaStructureNodeItemContentProvider
	extends AbstractItemTreeContentProvider<JpaStructureNode, JpaStructureNode>
{
	public JpaStructureNodeItemContentProvider(JpaStructureNode jpaStructureNode, Manager manager) {
		super(jpaStructureNode, manager);
	}

	public Object getParent() {
		if (this.item instanceof XmlFile.Root) {
			// I'd like to return the resource model here, but that involves a hefty
			// API change - we'll see what happens with this code first
			return null;
		}
		return this.item.getParent();
	}

	@Override
	protected CollectionValueModel<JpaStructureNode> buildChildrenModel() {
		return new ChildrenModel<JpaStructureNode>(this.item);
	}

	public static class ChildrenModel<C extends JpaStructureNode>
		extends CollectionAspectAdapter<JpaStructureNode, C>
	{
		public ChildrenModel(JpaStructureNode jpaStructureNode) {
			super(JpaStructureNode.CHILDREN_COLLECTION, jpaStructureNode);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Iterable<C> getIterable() {
			return (Iterable<C>) this.subject.getChildren();
		}

		@Override
		public int size_() {
			return this.subject.getChildrenSize();
		}
	}
}
