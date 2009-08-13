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
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;
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
	/** The collection. */
	protected final Collection<E> collection;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static collection value model for the specified array.
	 */
	public StaticCollectionValueModel(E... array) {
		this(Arrays.asList(array));
	}

	/**
	 * Construct a static collection value model for the specified collection.
	 */
	public StaticCollectionValueModel(Collection<? extends E> collection) {
		super();
		this.collection = new ArrayList<E>(collection);
	}


	// ********** CollectionValueModel implementation **********

	public int size() {
		return this.collection.size();
	}

	public Iterator<E> iterator() {
		return this.collection.iterator();
	}


	// ********** Object overrides **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collection);
	}

}
