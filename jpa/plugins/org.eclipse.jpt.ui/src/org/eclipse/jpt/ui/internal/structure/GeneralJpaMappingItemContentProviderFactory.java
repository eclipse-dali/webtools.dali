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

import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProviderFactory;

public abstract class GeneralJpaMappingItemContentProviderFactory
	implements ITreeItemContentProviderFactory
{
	public ITreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		if (item instanceof IPersistentType) {
			return buildPersistentTypeItemContentProvider((IPersistentType) item, treeContentProvider);
		}
		else if (item instanceof IPersistentAttribute) {
			return new PersistentAttributeItemContentProvider((IPersistentAttribute) item, treeContentProvider);
		}
		return null;
	}
	
	protected abstract ITreeItemContentProvider buildPersistentTypeItemContentProvider(IPersistentType persistentType, DelegatingTreeContentAndLabelProvider treeContentProvider);

	
	
	@SuppressWarnings("unchecked")
	public static class PersistentAttributeItemContentProvider extends AbstractTreeItemContentProvider
	{
		public PersistentAttributeItemContentProvider(
				IPersistentAttribute persistentAttribute, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistentAttribute, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((IPersistentAttribute) model()).parent();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
}
