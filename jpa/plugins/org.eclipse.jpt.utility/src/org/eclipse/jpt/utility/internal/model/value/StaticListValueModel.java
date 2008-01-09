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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

/**
 * Implementation of ListValueModel that can be used for
 * returning a list iterator on a static list, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 */
public class StaticListValueModel
	extends AbstractModel
	implements ListValueModel
{
	/** The value. */
	protected final List list;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static ListValueModel for the specified list.
	 */
	public StaticListValueModel(List list) {
		super();
		if (list == null) {
			throw new NullPointerException();
		}
		this.list = list;
	}


	// ********** ListValueModel implementation **********

	public Iterator iterator() {
		return this.list.iterator();
	}

	public ListIterator listIterator() {
		return this.list.listIterator();
	}

	public int size() {
		return this.list.size();
	}

	public Object get(int index) {
		return this.list.get(index);
	}

	public Object[] toArray() {
		return this.list.toArray();
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.list);
	}

}
