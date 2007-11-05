/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Collection;

/**
 * Implementation of CollectionValueModel that can be used for
 * returning an iterator on a static collection, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 */
public class ReadOnlyCollectionValueModel
	extends AbstractReadOnlyCollectionValueModel
{
	/** The value. */
	protected Collection value;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a read-only CollectionValueModel for the specified value.
	 */
	public ReadOnlyCollectionValueModel(Collection value) {
		super();
		if (value == null) {
			throw new NullPointerException();
		}
		this.value = value;
	}

	// ********** CollectionValueModel implementation **********

    @Override
	public int size() {
		return this.value.size();
	}


	// ********** ValueModel implementation **********

	public Object value() {
		return this.value.iterator();
	}

}
