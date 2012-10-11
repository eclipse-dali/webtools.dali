/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;

public class JaxbNavigatorItemLabelProviderFactory
		implements ItemExtendedLabelProviderFactory {
	
	/**
	 * Exactly *one* of these factories is created for each view that utilizes it.  
	 * Therefore, as we delegate to the platform UI for each project, we should 
	 * maintain the same multiplicity.  That is, if there is a delegate for each 
	 * platform UI, we should maintain *one* delegate for each view.
	 * 
	 * Key: platform description,  Value: delegate content provider factory
	 */
	private final Map<JaxbPlatformConfig, ItemExtendedLabelProviderFactory> delegates = new HashMap<JaxbPlatformConfig, ItemExtendedLabelProviderFactory>();
	
	
	public JaxbNavigatorItemLabelProviderFactory() {
		super();
	}
	
	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		ItemExtendedLabelProviderFactory delegate = getDelegate(item);
		if (delegate != null) {
			return delegate.buildProvider(item, manager);
		}
		return null;
	}
	
	
	private ItemExtendedLabelProviderFactory getDelegate(Object element) {
		if (! (element instanceof IAdaptable)) {
			return null;
		}
		
		JaxbContextNode contextNode = (JaxbContextNode) ((IAdaptable) element).getAdapter(JaxbContextNode.class);
		
		if (contextNode == null) {
			return null;
		}
		
		JaxbPlatform jaxbPlatform = contextNode.getJaxbProject().getPlatform();
		JaxbPlatformConfig jaxbPlatformConfig = jaxbPlatform.getConfig();
		if (delegates.containsKey(jaxbPlatformConfig)) {
			return delegates.get(jaxbPlatformConfig);
		}
		JaxbPlatformUi platformUi = (JaxbPlatformUi) jaxbPlatform.getAdapter(JaxbPlatformUi.class);
		ItemExtendedLabelProviderFactory delegate = 
				(platformUi == null) ? null : platformUi.getNavigatorUi().getItemLabelProviderFactory();
		delegates.put(jaxbPlatformConfig, delegate);
		return delegate;
	}
}
