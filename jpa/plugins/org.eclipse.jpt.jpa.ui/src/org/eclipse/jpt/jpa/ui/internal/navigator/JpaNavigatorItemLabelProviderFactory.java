/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.navigator.JpaNavigatorProvider;

public class JpaNavigatorItemLabelProviderFactory
	implements ItemLabelProviderFactory
{
	/**
	 * Exactly *one* of these factories is created for each view that utilizes it.  
	 * Therefore, as we delegate to the platform UI for each project, we should 
	 * maintain the same multiplicity.  That is, if there is a delegate for each 
	 * platform UI, we should maintain *one* delegate for each view.
	 * 
	 * Key: platform id,  Value: delegate content provider factory
	 */
	private final Map<String, ItemLabelProviderFactory> delegates;
	
	
	public JpaNavigatorItemLabelProviderFactory() {
		super();
		this.delegates = new HashMap<String, ItemLabelProviderFactory>();
	}
	
	public ItemLabelProvider buildItemLabelProvider(Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		ItemLabelProviderFactory delegate = getDelegate(item);
		if (delegate != null) {
			return delegate.buildItemLabelProvider(item, contentAndLabelProvider);
		}
		return null;
	}
	
	
	private ItemLabelProviderFactory getDelegate(Object element) {
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
		JpaNavigatorProvider navigatorProvider = JptJpaUiPlugin.instance().getJpaNavigatorProvider(platform);
		ItemLabelProviderFactory delegate = null;
		if (navigatorProvider != null) {
			delegate = navigatorProvider.getItemLabelProviderFactory();
		}
		delegates.put(platformId, delegate);
		return delegate;
	}
}
