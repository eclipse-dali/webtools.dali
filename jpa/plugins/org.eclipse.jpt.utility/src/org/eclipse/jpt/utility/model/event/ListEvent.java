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

// TODO add "item/original/nested event" for item changed?
/**
 * A "list" event gets delivered whenever a model changes a "bound"
 * or "constrained" list. A <code>ListEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.utility.model.listener.ListChangeListener}.
 * The intent is that any listener
 * can keep itself synchronized with the model's list via the list
 * events it receives and need not maintain a reference to the original
 * list.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class ListEvent extends ChangeEvent {

	/** Name of the list that changed. */
	final String listName;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a new list event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 */
	public ListEvent(Model source, String listName) {
		super(source);
		if (listName == null) {
			throw new NullPointerException();
		}
		this.listName = listName;
	}

	/**
	 * Return the programmatic name of the list that was changed.
	 */
	public String getListName() {
		return this.listName;
	}

	@Override
	protected void toString(StringBuilder sb) {
		sb.append(this.listName);
	}

}
