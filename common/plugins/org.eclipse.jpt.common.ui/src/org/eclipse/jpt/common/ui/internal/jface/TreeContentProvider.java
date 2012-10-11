/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Convenience implementation of {@link ITreeContentProvider}.
 */
public class TreeContentProvider
	extends StructuredContentProvider
	implements ITreeContentProvider
{
	private static final ITreeContentProvider NULL_PROVIDER = new TreeContentProvider();

	public static ITreeContentProvider nullProvider() {
		return NULL_PROVIDER;
	}

	@Override
	public Object[] getElements(Object input) {
		// typical tree implementation
		return this.getChildren(input);
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return false;
	}

	public Object[] getChildren(Object element) {
		return ObjectTools.EMPTY_OBJECT_ARRAY;
	}
}
