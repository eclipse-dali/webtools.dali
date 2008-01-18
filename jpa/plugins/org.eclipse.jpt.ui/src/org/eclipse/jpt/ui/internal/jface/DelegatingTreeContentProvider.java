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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * Implementation of {@link ITreeContentProvider} that maintains a collection 
 * (Map, actually) of {@link ITreeItemContentProvider} delegates that perform
 * the function of providing tree structure information for each node
 */
public class DelegatingTreeContentProvider implements ITreeContentProvider
{
	private final ITreeItemContentProviderFactory treeItemContentProviderFactory;
	
	private final Map<Object, ITreeItemContentProvider> treeItemContentProviders;
	
	private TreeViewer tree;
	
	
	public DelegatingTreeContentProvider(ITreeItemContentProviderFactory treeItemContentProviderFactory) {
		this.treeItemContentProviderFactory = treeItemContentProviderFactory;
		this.treeItemContentProviders = new HashMap<Object, ITreeItemContentProvider>();
	}
	
	
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	public Object[] getChildren(Object parentElement) {
		return treeItemContentProvider(parentElement).getChildren();
	}

	public Object getParent(Object element) {
		return treeItemContentProvider(element).getParent();
	}
	
	public boolean hasChildren(Object element) {
		return treeItemContentProvider(element).hasChildren();
	}
	
	protected ITreeItemContentProvider treeItemContentProvider(Object element) {
		ITreeItemContentProvider treeItemContentProvider = treeItemContentProviders.get(element);
		if (treeItemContentProvider != null) {
			return treeItemContentProvider;
		}
		treeItemContentProvider = treeItemContentProviderFactory.buildTreeItemContentProvider(element, this);
		if (treeItemContentProvider == null) {
			return null;
		}
		treeItemContentProviders.put(element, treeItemContentProvider);
		return treeItemContentProvider;
	}
	
	/**
	 * Disposes all elements
	 */
	public void dispose() {
		// coded this way to allow some item providers to dispose of their child 
		// elements without disrupting the entire process
		while (! treeItemContentProviders.isEmpty()) {
			dispose(treeItemContentProviders.keySet().iterator().next());
		}
	}
	
	/**
	 * Disposes element
	 */
	protected void dispose(Object element) {
		if (treeItemContentProviders.containsKey(element)) {
			treeItemContentProviders.get(element).dispose();
			treeItemContentProviders.remove(element);
		}
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != newInput) {
			dispose();
		}
		this.tree = (TreeViewer) viewer;
	}
	
	/**
	 * Refresh the tree using this content provider
	 */
	public void refreshTree(Object objectToRefresh) {
		tree.refresh(objectToRefresh);
	}
}
