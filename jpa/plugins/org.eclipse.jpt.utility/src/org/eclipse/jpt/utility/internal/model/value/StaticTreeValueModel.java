/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Iterator;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.model.value.TreeValueModel;

/**
 * Implementation of TreeValueModel that can be used for
 * returning an iterator on a static tree, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 */
public class StaticTreeValueModel<E>
	extends AbstractModel
	implements TreeValueModel<E>
{
	/** The tree's nodes. */
	protected final Iterable<? extends E> nodes;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a read-only TreeValueModel for the specified nodes.
	 */
	public StaticTreeValueModel(Iterable<? extends E> nodes) {
		super();
		if (nodes == null) {
			throw new NullPointerException();
		}
		this.nodes = nodes;
	}

	// ********** TreeValueModel implementation **********

	public Iterator<E> nodes() {
		return new ReadOnlyIterator<E>(this.nodes.iterator());
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, CollectionTools.collection(this.nodes()));
	}

}
