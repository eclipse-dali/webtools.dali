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

import org.eclipse.jpt.utility.internal.CollectionTools;
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
	/** The value. */
	protected final Collection value;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a read-only CollectionValueModel for the specified value.
	 */
	public StaticCollectionValueModel(Collection value) {
		super();
		if (value == null) {
			throw new NullPointerException();
		}
		this.value = value;
	}

	// ********** CollectionValueModel implementation **********

	public int size() {
		return this.value.size();
	}

	public Iterator iterator() {
		return this.value.iterator();
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, CollectionTools.collection((Iterator) this.iterator()));
	}

}
