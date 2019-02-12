/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model;

import org.eclipse.jpt.common.utility.internal.ListenerList;

public final class ModelTools {

	// ********** listener list **********

	/**
	 * Build a listener list that does not allow adding
	 * duplicate listeners or removing non-listeners.
	 * @param <L> the type of listeners held by the list
	 */
	public static <L> ListenerList<L> listenerList() {
		return new ListenerList<L>();
	}

	/**
	 * Build a listener list that does not allow adding
	 * duplicate listeners or removing non-listeners
	 * and is initialized with the specified listener. 
	 * @param <L> the type of listeners held by the list
	 */
	public static <L> ListenerList<L> listenerList(L listener) {
		return new ListenerList<L>(listener);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ModelTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
