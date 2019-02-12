/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;

abstract class StaticItemContentProvider<M>
	implements ItemContentProvider
{
	/* private-protected */ final Object item;

	/* private-protected */ final Object[] children;

	/* private-protected */ final M manager;

	/* private-protected */ static final Iterable<?> EMPTY_ITERABLE = EmptyIterable.instance();


	StaticItemContentProvider(Object item, Object[] children, M manager) {
		super();
		if (item == null) {
			throw new NullPointerException();
		}
		this.item = item;
		if (children == null) {
			throw new NullPointerException();
		}
		this.children = children;
		if (manager == null) {
			throw new NullPointerException();
		}
		this.manager = manager;
	}

	/* private-protected */ Object[] getChildren() {
		return this.children;
	}


	// ********** dispose **********

	public void dispose() {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.item);
	}
}
