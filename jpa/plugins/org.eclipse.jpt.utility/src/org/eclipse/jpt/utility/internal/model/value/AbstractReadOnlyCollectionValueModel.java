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
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.NullModel;

/**
 * Implementation of CollectionValueModel that can be subclassed and used for
 * returning an iterator on a static collection, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 * Subclasses need only implement the #value() method to
 * return an iterator on the static values required by the client code. This class is
 * really only useful for simplifying the building of anonymous inner
 * classes that implement the CollectionValueModel interface:
 * 	private CollectionValueModel buildCacheUsageOptionsHolder() {
 * 		return new AbstractReadOnlyCollectionValueModel() {
 * 			public Object value() {
 * 				return MWQuery.cacheUsageOptions();
 * 			}
 * 			public int size() {
 * 				return MWQuery.cacheUsageOptionsSize();
 * 			}
 * 		};
 * 	}
 */
public abstract class AbstractReadOnlyCollectionValueModel
	extends NullModel
	implements CollectionValueModel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	protected AbstractReadOnlyCollectionValueModel() {
		super();
	}


	// ********** CollectionValueModel implementation **********

	public int size() {
		return CollectionTools.size((Iterator) this.iterator());
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, CollectionTools.collection((Iterator) this.iterator()));
	}

}
