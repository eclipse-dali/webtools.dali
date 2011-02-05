/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.event;

import org.eclipse.jpt.common.utility.model.Model;

/**
 * A "tree clear" event gets delivered whenever a model clears
 * a "bound" or "constrained" tree. A <code>TreeClearEvent</code> is sent
 * as an argument to the {@link org.eclipse.jpt.common.utility.model.listener.TreeChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class TreeClearEvent extends TreeEvent {

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new tree clear event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the tree that was changed.
	 */
	public TreeClearEvent(Model source, String treeName) {
		super(source, treeName);
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public TreeClearEvent clone(Model newSource) {
		return this.clone(newSource, this.treeName);
	}

	/**
	 * Return a copy of the event with the specified source and collection name
	 * replacing the current source and collection name.
	 */
	public TreeClearEvent clone(Model newSource, String newCollectionName) {
		return new TreeClearEvent(newSource, newCollectionName);
	}

}
