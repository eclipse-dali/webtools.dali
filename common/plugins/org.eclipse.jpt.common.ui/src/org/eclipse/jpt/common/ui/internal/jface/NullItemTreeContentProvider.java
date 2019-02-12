/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.io.Serializable;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item tree content provider that returns
 * a <code>null</code> parent and an empty list of children.
 */
public final class NullItemTreeContentProvider
	extends NullItemContentProvider
	implements ItemTreeContentProvider, Serializable
{
	public static final ItemTreeContentProvider INSTANCE = new NullItemTreeContentProvider();

	public static ItemTreeContentProvider instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullItemTreeContentProvider() {
		super();
	}

	public Object getParent() {
		return null;
	}

	public boolean hasChildren() {
		return false;
	}

	public Object[] getChildren() {
		return ObjectTools.EMPTY_OBJECT_ARRAY;
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
