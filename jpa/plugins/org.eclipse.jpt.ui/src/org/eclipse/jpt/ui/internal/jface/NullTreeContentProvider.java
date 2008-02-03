/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Null implementation of the ILabelProvider interface.
 * Implemented as a singleton.
 */
public final class NullTreeContentProvider
	implements ITreeContentProvider 
{
	private static final Object[] EMPTY_ARRAY = new Object[0];
	public static final NullTreeContentProvider INSTANCE = new NullTreeContentProvider();

	public static ITreeContentProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure a single instance.
	 */
	private NullTreeContentProvider() {
		super();
	}

	public Object[] getChildren(Object parentElement) {
		return EMPTY_ARRAY;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return EMPTY_ARRAY;
	}

	public void dispose() {
		// do nothing
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// do nothing
	}

}
