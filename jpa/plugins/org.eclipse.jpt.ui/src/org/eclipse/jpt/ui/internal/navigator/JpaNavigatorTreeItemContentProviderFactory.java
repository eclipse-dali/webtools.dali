/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.navigator;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;

public class JpaNavigatorTreeItemContentProviderFactory
	implements TreeItemContentProviderFactory
{
	/**
	 * Exactly *one* of these factories is created for each view that utilizes it.  
	 * Therefore, as we delegate to the platform UI for each project, we should 
	 * maintain the same multiplicity.  That is, if there is a delegate for each 
	 * platform UI, we should maintain *one* delegate for each view.
	 * 
	 * Key: platform id,  Value: delegate content provider factory
	 */
	private Map<String, TreeItemContentProviderFactory> delegates;
	
	
	public JpaNavigatorTreeItemContentProviderFactory() {
		super();
		this.delegates = new HashMap<String, TreeItemContentProviderFactory>();
	}
	
	public TreeItemContentProvider buildItemContentProvider(Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		TreeItemContentProviderFactory delegate = getDelegate(item);
		if (delegate != null) {
			return delegate.buildItemContentProvider(item, contentAndLabelProvider);
		}
		return null;
	}
	
	
	private TreeItemContentProviderFactory getDelegate(Object element) {
		if (! (element instanceof IAdaptable)) {
			return null;
		}
		
		JpaContextNode contextNode = (JpaContextNode) ((IAdaptable) element).getAdapter(JpaContextNode.class);
		
		if (contextNode == null) {
			return null;
		}
		
		JpaPlatform platform = contextNode.getJpaProject().getJpaPlatform();
		String platformId = platform.getId();
		if (delegates.containsKey(platformId)) {
			return delegates.get(platformId);
		}
		JpaNavigatorProvider navigatorProvider = JptUiPlugin.instance().getJpaNavigatorProvider(platform);
		TreeItemContentProviderFactory delegate = null;
		if (navigatorProvider != null) {
			delegate = navigatorProvider.getTreeItemContentProviderFactory();
		}
		delegates.put(platformId, delegate);
		return delegate;
	}
}
