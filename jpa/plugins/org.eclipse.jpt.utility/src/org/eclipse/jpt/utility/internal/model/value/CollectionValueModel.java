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

import org.eclipse.jpt.utility.internal.model.Model;

/**
 * Interface used to abstract collection accessing and
 * change notification and make it more pluggable.
 */
public interface CollectionValueModel
	extends Model
{

	/**
	 * Return the collection's values.
	 */
	Iterator values();
		String VALUES = "values";

	/**
	 * Add the specified item to the collection.
	 */
	void add(Object item);

	/**
	 * Add the specified items to the collection.
	 */
	void addAll(Collection items);

	/**
	 * Remove the specified item from the collection.
	 */
	void remove(Object item);

	/**
	 * Remove the specified items from the collection.
	 */
	void removeAll(Collection items);

	/**
	 * Return the size of the collection value.
	 */
	int size();

}
