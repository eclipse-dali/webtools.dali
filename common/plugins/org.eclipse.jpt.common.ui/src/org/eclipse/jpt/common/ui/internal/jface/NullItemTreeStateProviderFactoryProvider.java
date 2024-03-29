/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.io.Serializable;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item tree state provider factory provider that returns
 * <em>null</em> factories.
 * @see org.eclipse.jpt.common.ui.internal.jface.NullItemTreeContentProviderFactory
 * @see org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider.Factory
 */
public final class NullItemTreeStateProviderFactoryProvider
	implements ItemTreeStateProviderFactoryProvider, Serializable
{
	public static final ItemTreeStateProviderFactoryProvider INSTANCE = new NullItemTreeStateProviderFactoryProvider();

	public static ItemTreeStateProviderFactoryProvider instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemTreeStateProviderFactoryProvider() {
		super();
	}

	public ItemTreeContentProvider.Factory getItemContentProviderFactory() {
		return NullItemTreeContentProviderFactory.instance();
	}

	public ItemExtendedLabelProvider.Factory getItemLabelProviderFactory() {
		return NullItemExtendedLabelProviderFactory.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
