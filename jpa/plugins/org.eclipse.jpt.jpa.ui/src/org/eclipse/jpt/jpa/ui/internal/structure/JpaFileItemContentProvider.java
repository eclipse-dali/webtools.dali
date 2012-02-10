/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;

public class JpaFileItemContentProvider
	extends AbstractItemTreeContentProvider<JpaFile, JpaStructureNode>
{
	public JpaFileItemContentProvider(JpaFile jpaFile, Manager manager) {
		super(jpaFile, manager);
	}

	public Object getParent() {
		return null;
	}

	@Override
	protected CollectionValueModel<JpaStructureNode> buildChildrenModel() {
		return new ChildrenModel(this.item);
	}

	public static class ChildrenModel
		extends CollectionAspectAdapter<JpaFile, JpaStructureNode>
	{
		public ChildrenModel(JpaFile jpaFile) {
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
}
