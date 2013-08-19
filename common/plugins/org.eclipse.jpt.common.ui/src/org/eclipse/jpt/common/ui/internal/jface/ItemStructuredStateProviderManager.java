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
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;

/**
 * @see AbstractItemStructuredStateProviderManager
 */
public class ItemStructuredStateProviderManager
	extends AbstractItemStructuredStateProviderManager<StructuredViewer, ItemStructuredContentProvider, ItemStructuredContentProvider.Factory>
{
	public ItemStructuredStateProviderManager(ItemStructuredContentProvider.Factory itemContentProviderFactory, ResourceManager resourceManager) {
		this(itemContentProviderFactory, NullItemExtendedLabelProviderFactory.instance(), resourceManager);
	}

	public ItemStructuredStateProviderManager(
			ItemStructuredContentProvider.Factory itemContentProviderFactory,
			ItemExtendedLabelProvider.Factory itemLabelProviderFactory,
			ResourceManager resourceManager
	) {
		this(itemContentProviderFactory, itemLabelProviderFactory, resourceManager, JptCommonUiPlugin.exceptionHandler());
	}

	public ItemStructuredStateProviderManager(
			ItemStructuredContentProvider.Factory itemContentProviderFactory,
			ItemExtendedLabelProvider.Factory itemLabelProviderFactory,
			ResourceManager resourceManager,
			ExceptionHandler exceptionHandler
	) {
		super(itemContentProviderFactory, itemLabelProviderFactory, resourceManager, exceptionHandler);
	}
}
