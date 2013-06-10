/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.navigator;

import java.util.HashMap;
import org.eclipse.jpt.common.ui.internal.jface.NullItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;

/**
 * @see JaxbNavigatorItemContentProviderFactory
 */
public class JaxbNavigatorItemLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory
{
	/**
	 * Delegate factories, keyed by JAXB platform.
	 */
	private final HashMap<JaxbPlatform, ItemExtendedLabelProvider.Factory> delegates = new HashMap<JaxbPlatform, ItemExtendedLabelProvider.Factory>();
	
	
	public JaxbNavigatorItemLabelProviderFactory() {
		super();
	}
	
	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		return this.getDelegate(item).buildProvider(item, manager);
	}
	

	// ********** delegates **********

	private ItemExtendedLabelProvider.Factory getDelegate(Object item) {
		return (item instanceof JaxbContextNode) ?
				this.getDelegate((JaxbContextNode) item) :
				NullItemExtendedLabelProviderFactory.instance();
	}
		
	private ItemExtendedLabelProvider.Factory getDelegate(JaxbContextNode contextNode) {
		JaxbPlatform jaxbPlatform = contextNode.getJaxbProject().getPlatform();
		ItemExtendedLabelProvider.Factory delegate = this.delegates.get(jaxbPlatform);
		if (delegate == null) {
			delegate = this.buildDelegate(jaxbPlatform);
			this.delegates.put(jaxbPlatform, delegate);
		}
		return delegate;
	}

	private ItemExtendedLabelProvider.Factory buildDelegate(JaxbPlatform jaxbPlatform) {
		JaxbPlatformUi platformUI = (JaxbPlatformUi) jaxbPlatform.getAdapter(JaxbPlatformUi.class);
		return (platformUI != null) ?
				platformUI.getNavigatorUi().getItemLabelProviderFactory() :
				NullItemExtendedLabelProviderFactory.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
