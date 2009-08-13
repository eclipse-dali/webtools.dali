/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.event;

import org.eclipse.jpt.utility.model.Model;

/**
 * A "list clear" event gets delivered whenever a model clears
 * a "bound" or "constrained" list. A <code>ListClearEvent</code> is sent
 * as an argument to the {@link org.eclipse.jpt.utility.model.listener.ListChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class ListClearEvent extends ListEvent {

	private static final long serialVersionUID = 1L;


	// ********** constructor **********

	/**
	 * Construct a new list clear event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 */
	public ListClearEvent(Model source, String listName) {
		super(source, listName);
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public ListClearEvent clone(Model newSource) {
		return this.clone(newSource, this.listName);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name.
	 */
	public ListClearEvent clone(Model newSource, String newListName) {
		return new ListClearEvent(newSource, newListName);
	}

}
