/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

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
		return ObjectTools.EMPTY_OBJECT_ARRAY;
	}
}
