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
 * A "list move" event gets delivered whenever a model moves the elements in
 * a "bound" or "constrained" list. A <code>ListMoveEvent</code> is sent
 * as an argument to the {@link org.eclipse.jpt.utility.model.listener.ListChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class ListMoveEvent extends ListEvent {

	/** The index to which the items were moved. */
	private final int targetIndex;

	/** The index from which the items were moved. */
	private final int sourceIndex;

	/** The number of items moved. */
	private final int length;

	private static final long serialVersionUID = 1L;


	// ********** constructor **********

	/**
	 * Construct a new list move event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param targetIndex The index to which the items were moved.
	 * @param sourceIndex The index from which the items were moved.
	 * @param length The number of items moved.
	 */
	public ListMoveEvent(Model source, String listName, int targetIndex, int sourceIndex, int length) {
		super(source, listName);
		this.targetIndex = targetIndex;
		this.sourceIndex = sourceIndex;
		this.length = length;
	}


	// ********** standard state **********

	/**
	 * Return the index to which the items were moved.
	 */
	public int getTargetIndex() {
		return this.targetIndex;
	}

	/**
	 * Return the index from which the items were moved.
	 */
	public int getSourceIndex() {
		return this.sourceIndex;
	}

	/**
	 * Return the number of items moved.
	 */
	public int getLength() {
		return this.length;
	}

	@Override
	protected void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(": "); //$NON-NLS-1$
		sb.append(this.sourceIndex);
		sb.append(" => "); //$NON-NLS-1$
		sb.append(this.targetIndex);
		sb.append(" length="); //$NON-NLS-1$
		sb.append(this.length);
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public ListMoveEvent clone(Model newSource) {
		return this.clone(newSource, this.listName);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name.
	 */
	public ListMoveEvent clone(Model newSource, String newListName) {
		return this.clone(newSource, newListName, 0);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name and displacing
	 * the index by the specified amount.
	 */
	public ListMoveEvent clone(Model newSource, String newListName, int offset) {
		return new ListMoveEvent(newSource, newListName, this.targetIndex + offset, this.sourceIndex + offset, this.length);
	}

}
