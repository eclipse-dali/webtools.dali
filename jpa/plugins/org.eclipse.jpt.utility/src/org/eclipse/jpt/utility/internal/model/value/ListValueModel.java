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

import org.eclipse.jpt.utility.internal.model.Model;

/**
 * Interface used to abstract list accessing and
 * change notification and make it more pluggable.
 */
public interface ListValueModel
	extends Model
{
	/**
	 * Return the list's values.
	 */
	ListIterator values();
		String LIST_VALUES = "list values";

	/**
	 * Add the specified item to the list at the specified index.
	 */
	void add(int index, Object item);

	/**
	 * Add the specified items to the list at the specified index.
	 */
	void addAll(int index, List items);

	/**
	 * Remove the item at the specified index from the list
	 * and return it.
	 */
	Object remove(int index);

	/**
	 * Remove the items from the list, starting at the specified index
	 * for the specified length. Return a list containing the removed items.
	 */
	List remove(int index, int length);

	/**
	 * Replace the item at the specified index of the list
	 * and return the item that was there previously.
	 */
	Object replace(int index, Object item);

	/**
	 * Replace the items at the specified index of the list
	 * and return the items that were there previously.
	 */
	List replaceAll(int index, List items);

	/**
	 * Return the item at the specified index of the list.
	 */
	Object get(int index);

	/**
	 * Return the size of the list.
	 */
	int size();

}
