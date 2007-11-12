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

import java.util.List;
import java.util.ListIterator;

/**
 * Implementation of ListValueModel that can be used for
 * returning a list iterator on a static list, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 */
public class ReadOnlyListValueModel
	extends AbstractReadOnlyListValueModel
{
	/** The value. */
	protected final List value;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a ListValueModel for the specified value.
	 */
	public ReadOnlyListValueModel(List value) {
		super();
		if (value == null) {
			throw new NullPointerException();
		}
		this.value = value;
	}


	// ********** ListValueModel implementation **********

    @Override
	public int size() {
		return this.value.size();
	}

	public ListIterator values() {
		return this.value.listIterator();
	}

}
