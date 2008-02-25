/*******************************************************************************
 *  Copyright (c) 2008 Oracle.
 *  All rights reserved.  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.TreeItemContentProviderFactory;

public abstract class GeneralJpaMappingItemContentProviderFactory
	implements TreeItemContentProviderFactory
{
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		if (item instanceof PersistentType) {
			return buildPersistentTypeItemContentProvider((PersistentType) item, treeContentProvider);
		}
		else if (item instanceof PersistentAttribute) {
			return new PersistentAttributeItemContentProvider((PersistentAttribute) item, treeContentProvider);
		}
		return null;
	}
	
	protected abstract TreeItemContentProvider buildPersistentTypeItemContentProvider(PersistentType persistentType, DelegatingTreeContentAndLabelProvider treeContentProvider);

	
	
	@SuppressWarnings("unchecked")
	public static class PersistentAttributeItemContentProvider extends AbstractTreeItemContentProvider
	{
		public PersistentAttributeItemContentProvider(
				PersistentAttribute persistentAttribute, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistentAttribute, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((PersistentAttribute) model()).parent();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
}
