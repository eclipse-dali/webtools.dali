/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;

/**
 * Implementation of {@link CollectionValueModel} that can be used for
 * returning an iterator on a static collection, but still allows listeners to be added.
 * Listeners will <em>never</em> be notified of any changes, because there should be none.
 */
public class StaticCollectionValueModel<E>
	extends AbstractModel
	implements CollectionValueModel<E>
{
	/** The elements. */
	protected final Object[] elements;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static collection value model for the specified array.
	 */
	public StaticCollectionValueModel(E... elements) {
		super();
		this.elements = elements.clone();
	}

	/**
	 * Construct a static collection value model for the specified elements.
	 */
	public StaticCollectionValueModel(Iterable<? extends E> elements) {
		super();
		this.elements = ArrayTools.array(elements);
	}


	// ********** CollectionValueModel implementation **********

	public int size() {
		return this.elements.length;
	}

	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		// we can cast here since our constructors require the elements to be
		// of type E and ArrayIterator is read-only
		return (Iterator<E>) new ArrayIterator<Object>(this.elements);
	}


	// ********** Object overrides **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(Arrays.toString(this.elements));
	}

}
