/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Straightforward implementation of {@link ItemTreeStateProviderFactoryProvider}.
 */
public class SimpleItemTreeStateProviderFactoryProvider
	implements ItemTreeStateProviderFactoryProvider
{
	private final ItemTreeContentProvider.Factory contentProviderFactory;
	private final ItemExtendedLabelProvider.Factory labelProviderFactory;


	/**
	 * Neither factory can be <code>null</code>.
	 * @see NullItemTreeContentProviderFactory
	 * @see NullItemExtendedLabelProviderFactory
	 */
	public SimpleItemTreeStateProviderFactoryProvider(
			ItemTreeContentProvider.Factory contentProviderFactory,
			ItemExtendedLabelProvider.Factory labelProviderFactory
	) {
		super();
		if (contentProviderFactory == null) {
			throw new NullPointerException();
		}
		this.contentProviderFactory = contentProviderFactory;
		if (labelProviderFactory == null) {
			throw new NullPointerException();
		}
		this.labelProviderFactory = labelProviderFactory;
	}

	public ItemTreeContentProvider.Factory getItemContentProviderFactory() {
		return this.contentProviderFactory;
	}

	public ItemExtendedLabelProvider.Factory getItemLabelProviderFactory() {
		return this.labelProviderFactory;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
