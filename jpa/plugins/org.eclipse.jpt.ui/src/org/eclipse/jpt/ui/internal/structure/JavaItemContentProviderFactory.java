/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.TreeItemContentProvider;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;


public class JavaItemContentProviderFactory extends GeneralJpaMappingItemContentProviderFactory
{
	@Override
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentProvider;
		if (item instanceof JavaResourceModel) {
			return new JavaResourceModelItemContentProvider((JavaResourceModel) item, treeContentProvider);
		}
		return super.buildItemContentProvider(item, treeContentProvider);
	}
	
	@Override
	protected TreeItemContentProvider buildPersistentTypeItemContentProvider(PersistentType persistentType, DelegatingTreeContentAndLabelProvider treeContentProvider) {
		return new PersistentTypeItemContentProvider(persistentType, treeContentProvider);
	}
	
	public static class PersistentTypeItemContentProvider extends AbstractTreeItemContentProvider<PersistentAttribute>
	{
		public PersistentTypeItemContentProvider(
				PersistentType persistentType, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistentType, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((PersistentType) model()).parent();
		}
		
		@Override
		protected ListValueModel<PersistentAttribute> buildChildrenModel() {
			return new ListAspectAdapter<PersistentType, PersistentAttribute>(new String[]{PersistentType.SPECIFIED_ATTRIBUTES_LIST}, (PersistentType) model()) {
				@Override
				protected ListIterator<PersistentAttribute> listIterator_() {
					return subject.attributes();
				}
				
				@Override
				protected int size_() {
					return subject.attributesSize();
				}
			};
		}
	}
	public static class JavaResourceModelItemContentProvider extends AbstractTreeItemContentProvider<JpaStructureNode>
	{
		public JavaResourceModelItemContentProvider(
				JavaResourceModel javaResourceModel, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(javaResourceModel, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return null;
		}
		
		@Override
		protected ListValueModel<JpaStructureNode> buildChildrenModel() {
			return new ListAspectAdapter<JavaResourceModel, JpaStructureNode>(
					ResourceModel.ROOT_STRUCTURE_NODES_LIST, (JavaResourceModel) model()) {
				@Override
				protected ListIterator<JpaStructureNode> listIterator_() {
					return subject.rootStructureNodes();
				}
			};
		}	
	}
}
