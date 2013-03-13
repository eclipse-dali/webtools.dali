/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.io.Serializable;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item tree state provider factory provider that returns
 * <em>null</em> factories.
 * @see org.eclipse.jpt.common.ui.internal.jface.NullItemTreeContentProviderFactory
 * @see org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory.Null
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

	public ItemTreeContentProviderFactory getItemContentProviderFactory() {
		return NullItemTreeContentProviderFactory.instance();
	}

	public ItemExtendedLabelProviderFactory getItemLabelProviderFactory() {
		return ItemExtendedLabelProviderFactory.Null.instance();
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
