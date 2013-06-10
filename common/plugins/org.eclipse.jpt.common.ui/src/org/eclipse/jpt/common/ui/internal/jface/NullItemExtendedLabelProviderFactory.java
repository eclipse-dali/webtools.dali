/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.io.Serializable;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item extended label provider factory that returns a
 * <em>null</em> provider.
 * @see NullItemExtendedLabelProvider
 */
public final class NullItemExtendedLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory, Serializable
{
	public static final ItemExtendedLabelProvider.Factory INSTANCE = new NullItemExtendedLabelProviderFactory();

	public static ItemExtendedLabelProvider.Factory instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemExtendedLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		return NullItemExtendedLabelProvider.instance();
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
