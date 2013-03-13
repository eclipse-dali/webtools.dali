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
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>null</em> item tree content provider that returns
 * an empty array of elements, an empty array of children, and a
 * <code>null</code> parent.
 */
public final class NullItemTreeContentProvider
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

	public Object[] getElements() {
		return ObjectTools.EMPTY_OBJECT_ARRAY;
	}

	public Object getParent() {
		return null;
	}

	public Object[] getChildren() {
		return ObjectTools.EMPTY_OBJECT_ARRAY;
	}

	public boolean hasChildren() {
		return false;
	}

	public void dispose() {
		// NOP
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
