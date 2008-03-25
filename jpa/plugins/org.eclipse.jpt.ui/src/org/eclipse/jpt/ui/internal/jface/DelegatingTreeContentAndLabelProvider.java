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
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;

/**
 * Extension of {@link DelegatingContentAndLabelProvider} that provides an extension
 * to provide tree content
 */
public class DelegatingTreeContentAndLabelProvider extends DelegatingContentAndLabelProvider
	implements ITreeContentProvider
{
	public DelegatingTreeContentAndLabelProvider(
			TreeItemContentProviderFactory treeItemContentProviderFactory) {
		super(treeItemContentProviderFactory);
	}
	
	public DelegatingTreeContentAndLabelProvider(
			TreeItemContentProviderFactory treeItemContentProviderFactory,
			ItemLabelProviderFactory itemLabelProviderFactory) {
		super(treeItemContentProviderFactory, itemLabelProviderFactory);
	}
	
	
	protected TreeItemContentProvider itemContentProvider(Object item) {
		return (TreeItemContentProvider) super.itemContentProvider(item);
	}
	
	public Object[] getChildren(Object parentElement) {
		TreeItemContentProvider provider = itemContentProvider(parentElement);
		return (provider == null) ? new Object[0] : provider.getChildren();
	}

	public Object getParent(Object element) {
		TreeItemContentProvider provider = itemContentProvider(element);
		return (provider == null) ? null : provider.getParent();
	}
	
	public boolean hasChildren(Object element) {
		TreeItemContentProvider provider = itemContentProvider(element);
		return (provider == null) ? false : provider.hasChildren();
	}
}
