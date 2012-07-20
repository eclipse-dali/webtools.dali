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

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jpt.common.utility.internal.Tools;

/**
 * Convenience implementation of {@link IStructuredContentProvider}.
 */
public class StructuredContentProvider
	extends ContentProvider
	implements IStructuredContentProvider
{
	private static final IStructuredContentProvider NULL_PROVIDER = new StructuredContentProvider();

	public static IStructuredContentProvider nullProvider() {
		return NULL_PROVIDER;
	}

	public Object[] getElements(Object inputElement) {
		return Tools.EMPTY_OBJECT_ARRAY;
	}
}
