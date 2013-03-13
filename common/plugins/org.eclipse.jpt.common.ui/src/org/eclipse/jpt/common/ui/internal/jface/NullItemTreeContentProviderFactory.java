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
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item tree content provider factory that returns a
 * <em>null</em> provider.
 * @see org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Null
 */
public final class NullItemTreeContentProviderFactory
	implements ItemTreeContentProviderFactory, Serializable
{
	public static final ItemTreeContentProviderFactory INSTANCE = new NullItemTreeContentProviderFactory();

	public static ItemTreeContentProviderFactory instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemTreeContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager manager) {
		return ItemTreeContentProvider.Null.instance();
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
