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

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.NullModel;

/**
 * Implementation of ListValueModel that can be subclassed and used for
 * returning a list iterator on a static list, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 * Subclasses need only implement the #value() method to
 * return a list iterator on the static values required by the client code. This class is
 * really only useful for simplifying the building of anonymous inner
 * classes that implement the ListValueModel interface:
 * 	private ListValueModel buildCacheUsageOptionsHolder() {
 * 		return new AbstractReadOnlyListValueModel() {
 * 			public Object value() {
 * 				return MWQuery.cacheUsageOptions();
 * 			}
 * 			public int size() {
 * 				return MWQuery.cacheUsageOptionsSize();
 * 			}
 * 		};
 * 	}
 */
public abstract class AbstractReadOnlyListValueModel
	extends NullModel
	implements ListValueModel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	protected AbstractReadOnlyListValueModel() {
		super();
	}


	// ********** ListValueModel implementation **********

	public void add(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	public void addAll(int index, List items) {
		throw new UnsupportedOperationException();
	}

	public Object remove(int index) {
		throw new UnsupportedOperationException();
	}

	public List remove(int index, int length) {
		throw new UnsupportedOperationException();
	}

	public Object replace(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	public List replaceAll(int index, List items) {
		throw new UnsupportedOperationException();
	}

	public Object get(int index) {
		return CollectionTools.get((ListIterator) this.values(), index);
	}

	public int size() {
		return CollectionTools.size((ListIterator) this.values());
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, CollectionTools.collection((ListIterator) this.values()));
	}

}
