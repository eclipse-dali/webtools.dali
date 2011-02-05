/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.model.value.TreeValueModel;

/**
 * Implementation of {@link TreeValueModel} that can be used for
 * returning an iterator on a static tree, but still allows listeners to be added.
 * Listeners will <em>never</em> be notified of any changes, because there should be none.
 */
public class StaticTreeValueModel<E>
	extends AbstractModel
	implements TreeValueModel<E>
{
	/** The tree's nodes. */
	protected final Iterable<? extends E> nodes;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a read-only tree value model for the specified nodes.
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
	public void toString(StringBuilder sb) {
		sb.append(this.nodes);
	}

}
