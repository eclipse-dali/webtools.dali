/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Straightforward implementation of {@link ItemTreeStateProviderFactoryProvider}.
 */
public class SimpleItemTreeStateProviderFactoryProvider
	implements ItemTreeStateProviderFactoryProvider
{
	private final ItemTreeContentProviderFactory contentProviderFactory;
	private final ItemExtendedLabelProviderFactory labelProviderFactory;


	public SimpleItemTreeStateProviderFactoryProvider(
			ItemTreeContentProviderFactory contentProviderFactory,
			ItemExtendedLabelProviderFactory labelProviderFactory
	) {
		super();
		this.contentProviderFactory = contentProviderFactory;
		this.labelProviderFactory = labelProviderFactory;
	}

	public ItemTreeContentProviderFactory getItemContentProviderFactory() {
		return this.contentProviderFactory;
	}

	public ItemExtendedLabelProviderFactory getItemLabelProviderFactory() {
		return this.labelProviderFactory;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
