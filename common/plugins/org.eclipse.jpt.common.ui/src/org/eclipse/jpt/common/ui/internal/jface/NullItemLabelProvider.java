/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.io.Serializable;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;

/**
 * A <em>null</em> item label provider that returns a <code>null</code>
 * image and a <code>null</code> text string.
 */
public final class NullItemLabelProvider
	extends AbstractNullItemLabelProvider
	implements Serializable
{
	public static final ItemLabelProvider INSTANCE = new NullItemLabelProvider();

	public static ItemLabelProvider instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemLabelProvider() {
		super();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
