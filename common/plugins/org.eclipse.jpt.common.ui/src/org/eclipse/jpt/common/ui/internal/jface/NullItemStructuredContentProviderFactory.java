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
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item tree content provider factory that returns a
 * <em>null</em> provider.
 * @see org.eclipse.jpt.common.ui.internal.jface.NullItemStructuredContentProvider
 */
public final class NullItemStructuredContentProviderFactory
	implements ItemStructuredContentProvider.Factory, Serializable
{
	public static final ItemStructuredContentProvider.Factory INSTANCE = new NullItemStructuredContentProviderFactory();

	public static ItemStructuredContentProvider.Factory instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemStructuredContentProviderFactory() {
		super();
	}

	public ItemStructuredContentProvider buildProvider(Object item, ItemStructuredContentProvider.Manager manager) {
		return NullItemStructuredContentProvider.instance();
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
