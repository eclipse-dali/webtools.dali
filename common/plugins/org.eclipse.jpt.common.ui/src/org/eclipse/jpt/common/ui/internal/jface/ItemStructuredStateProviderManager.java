/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;

/**
 * @see AbstractItemStructuredStateProviderManager
 */
public class ItemStructuredStateProviderManager
	extends AbstractItemStructuredStateProviderManager<StructuredViewer, ItemStructuredContentProvider>
{
	/**
	 * Never <code>null</code>.
	 */
	protected final ItemStructuredContentProvider.Factory itemContentProviderFactory;


	public ItemStructuredStateProviderManager(ItemStructuredContentProvider.Factory itemContentProviderFactory, ResourceManager resourceManager) {
		this(itemContentProviderFactory, null, resourceManager);
	}

	public ItemStructuredStateProviderManager(ItemStructuredContentProvider.Factory itemContentProviderFactory, ItemExtendedLabelProvider.Factory itemLabelProviderFactory, ResourceManager resourceManager) {
		super(itemLabelProviderFactory, resourceManager);
		if (itemContentProviderFactory == null) {
			throw new NullPointerException();
		}
		this.itemContentProviderFactory = itemContentProviderFactory;
	}

	@Override
	protected ItemStructuredContentProvider buildItemContentProvider(Object item) {
		return this.itemContentProviderFactory.buildProvider(item, this);
	}
}
