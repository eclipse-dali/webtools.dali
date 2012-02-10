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

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Convenience implementation of {@link IContentProvider}.
 */
public class ContentProvider
	implements IContentProvider
{
	private static final IContentProvider NULL_PROVIDER = new ContentProvider();

	public static IContentProvider nullProvider() {
		return NULL_PROVIDER;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// do nothing
	}

	public void dispose() {
		// do nothing
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
