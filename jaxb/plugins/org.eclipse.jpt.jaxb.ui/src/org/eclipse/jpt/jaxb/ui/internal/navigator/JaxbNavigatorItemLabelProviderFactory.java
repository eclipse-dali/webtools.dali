/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.navigator;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;

public class JaxbNavigatorItemLabelProviderFactory
		implements ItemLabelProviderFactory {
	
	/**
	 * Exactly *one* of these factories is created for each view that utilizes it.  
	 * Therefore, as we delegate to the platform UI for each project, we should 
	 * maintain the same multiplicity.  That is, if there is a delegate for each 
	 * platform UI, we should maintain *one* delegate for each view.
	 * 
	 * Key: platform description,  Value: delegate content provider factory
	 */
	private final Map<JaxbPlatformDescription, ItemLabelProviderFactory> delegates;
	
	
	public JaxbNavigatorItemLabelProviderFactory() {
		super();
		this.delegates = new HashMap<JaxbPlatformDescription, ItemLabelProviderFactory>();
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
		
		JaxbContextNode contextNode = (JaxbContextNode) ((IAdaptable) element).getAdapter(JaxbContextNode.class);
		
		if (contextNode == null) {
			return null;
		}
		
		JaxbPlatformDescription platformDesc = contextNode.getJaxbProject().getPlatform().getDescription();
		if (delegates.containsKey(platformDesc)) {
			return delegates.get(platformDesc);
		}
		JaxbPlatformUi platformUi = JptJaxbUiPlugin.getJaxbPlatformUiManager().getJaxbPlatformUi(platformDesc);
		ItemLabelProviderFactory delegate = 
				(platformUi == null) ? null : platformUi.getNavigatorUi().getItemLabelProviderFactory();
		delegates.put(platformDesc, delegate);
		return delegate;
	}
}
