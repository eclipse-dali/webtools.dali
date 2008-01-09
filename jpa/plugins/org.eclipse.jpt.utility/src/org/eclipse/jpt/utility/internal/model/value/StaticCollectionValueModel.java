/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

/**
 * Implementation of CollectionValueModel that can be used for
 * returning an iterator on a static collection, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 */
public class StaticCollectionValueModel
	extends AbstractModel
	implements CollectionValueModel
{
	/** The collection. */
	protected final Collection collection;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static CollectionValueModel for the specified collection.
	 */
	public StaticCollectionValueModel(Collection collection) {
		super();
		if (collection == null) {
			throw new NullPointerException();
		}
		this.collection = collection;
	}

	// ********** CollectionValueModel implementation **********

	public int size() {
		return this.collection.size();
	}

	public Iterator iterator() {
		return this.collection.iterator();
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.collection);
	}

}
