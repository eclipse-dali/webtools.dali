/*******************************************************************************
 *  Copyright (c) 2007 Oracle.
 *  All rights reserved.  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProvider;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;


public class JavaItemContentProviderFactory extends GeneralJpaMappingItemContentProviderFactory
{
	public ITreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentProvider;
		if (item instanceof JavaResourceModel) {
			return new JavaResourceModelItemContentProvider((JavaResourceModel) item, treeContentProvider);
		}
		return super.buildItemContentProvider(item, treeContentProvider);
	}
	
	
	public static class JavaResourceModelItemContentProvider extends AbstractTreeItemContentProvider<IJpaContextNode>
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
		protected ListValueModel<IJpaContextNode> buildChildrenModel() {
			return new ListAspectAdapter<JavaResourceModel, IJpaContextNode>(
					IResourceModel.ROOT_CONTEXT_NODE_LIST, (JavaResourceModel) model()) {
				@Override
				protected ListIterator<IJpaContextNode> listIterator_() {
					return subject.rootContextNodes();
				}
			};
		}	
	}
}
