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
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item structured content provider that returns
 * an empty list of elements.
 */
public final class NullItemStructuredContentProvider
	extends NullItemContentProvider
	implements ItemStructuredContentProvider, Serializable
{
	public static final ItemStructuredContentProvider INSTANCE = new NullItemStructuredContentProvider();

	public static ItemStructuredContentProvider instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemStructuredContentProvider() {
		super();
	}

	public Object[] getElements() {
		return ObjectTools.EMPTY_OBJECT_ARRAY;
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
