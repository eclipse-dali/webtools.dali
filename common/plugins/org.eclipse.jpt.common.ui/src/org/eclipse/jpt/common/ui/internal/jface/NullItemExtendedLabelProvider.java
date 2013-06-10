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

import java.io.Serializable;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;

/**
 * A <em>null</em> item extended label provider that returns a
 * <code>null</code> image, a <code>null</code> text string, and
 * a <code>null</code> description string.
 */
public final class NullItemExtendedLabelProvider
	extends AbstractNullItemLabelProvider
	implements ItemExtendedLabelProvider, Serializable
{
	public static final ItemExtendedLabelProvider INSTANCE = new NullItemExtendedLabelProvider();

	public static ItemExtendedLabelProvider instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemExtendedLabelProvider() {
		super();
	}

	public String getDescription() {
		return null;
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
