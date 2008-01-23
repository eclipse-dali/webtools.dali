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
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Extension of {@link DelegatingContentAndLabelProvider} that provides an extension
 * to provide tree content
 */
public class DelegatingTreeContentAndLabelProvider extends DelegatingContentAndLabelProvider
	implements ITreeContentProvider
{
	public DelegatingTreeContentAndLabelProvider(
			ITreeItemContentProviderFactory treeItemContentProviderFactory) {
		super(treeItemContentProviderFactory);
	}
	
	public DelegatingTreeContentAndLabelProvider(
			ITreeItemContentProviderFactory treeItemContentProviderFactory,
			IItemLabelProviderFactory itemLabelProviderFactory) {
		super(treeItemContentProviderFactory, itemLabelProviderFactory);
	}
	
	
	protected ITreeItemContentProvider itemContentProvider(Object item) {
		return (ITreeItemContentProvider) super.itemContentProvider(item);
	}
	
	public Object[] getChildren(Object parentElement) {
		return itemContentProvider(parentElement).getChildren();
	}

	public Object getParent(Object element) {
		return itemContentProvider(element).getParent();
	}
	
	public boolean hasChildren(Object element) {
		return itemContentProvider(element).hasChildren();
	}
}
