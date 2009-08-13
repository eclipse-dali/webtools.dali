/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Implementation of {@link ListValueModel} that can be used for
 * returning a list iterator on a static list, but still allows listeners to be added.
 * Listeners will <em>never</em> be notified of any changes, because there should be none.
 */
public class StaticListValueModel<E>
	extends AbstractModel
	implements ListValueModel<E>
{
	/** The value. */
	protected final List<E> list;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static list value model for the specified array.
	 */
	public StaticListValueModel(E... array) {
		this(Arrays.asList(array));
	}

	/**
	 * Construct a static list value model for the specified list.
	 */
	public StaticListValueModel(List<? extends E> list) {
		super();
		this.list = new ArrayList<E>(list);
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return new ReadOnlyIterator<E>(this.list.iterator());
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.list.listIterator());
	}

	public int size() {
		return this.list.size();
	}

	public E get(int index) {
		return this.list.get(index);
	}

	public Object[] toArray() {
		return this.list.toArray();
	}


	// ********** Object overrides **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.list);
	}

}
