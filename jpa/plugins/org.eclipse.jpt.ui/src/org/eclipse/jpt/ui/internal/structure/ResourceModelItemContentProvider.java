/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import java.util.Iterator;

import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

public class ResourceModelItemContentProvider extends AbstractTreeItemContentProvider<JpaStructureNode>
{
	public ResourceModelItemContentProvider(
			JpaFile jpaFile, 
			DelegatingTreeContentAndLabelProvider contentProvider) {
		super(jpaFile, contentProvider);
	}
	
	@Override
	public Object getParent() {
		return null;
	}
	
	@Override
	public JpaFile model() {
		return (JpaFile) super.model();
	}
	
	@Override
	protected ListValueModel<JpaStructureNode> buildChildrenModel() {
		return new CollectionListValueModelAdapter<JpaStructureNode>(buildChildrenCollectionModel());
	}

	protected CollectionValueModel<JpaStructureNode> buildChildrenCollectionModel() {
		return new CollectionAspectAdapter<JpaFile, JpaStructureNode>(
			buildJpaFileValueModel(), JpaFile.ROOT_STRUCTURE_NODES_COLLECTION) {
			@Override
			protected Iterator<JpaStructureNode> iterator_() {
				return subject.rootStructureNodes();
			}
		};
	}
	
	protected PropertyValueModel<JpaFile> buildJpaFileValueModel() {
		return new SimplePropertyValueModel<JpaFile>(this.model());
	}
}