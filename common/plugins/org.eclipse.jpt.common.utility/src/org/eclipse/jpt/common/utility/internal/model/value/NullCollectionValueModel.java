/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.io.Serializable;
import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.iterator.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * A read-only collection value model for when you
 * don't need to support a collection. In particular, this
 * is useful for the leaf nodes of a tree that never have
 * children.
 * <p>
 * We don't use a singleton because we hold on to listeners.
 */
public final class NullCollectionValueModel<E>
	extends AbstractModel
	implements CollectionValueModel<E>, Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public NullCollectionValueModel() {
		super();
	}
	

	// ********** CollectionValueModel implementation **********

	public int size() {
		return 0;
	}

	public Iterator<E> iterator() {
		return EmptyIterator.instance();
	}


	// ********** Object overrides **********

    @Override
	public String toString() {
    	return this.getClass().getSimpleName();
	}
}
