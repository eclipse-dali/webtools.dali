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

import java.util.List;

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * A "tree add" event gets delivered whenever a model adds a node to a "bound"
 * or "constrained" tree. A <code>TreeChangeEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.common.utility.model.listener.TreeChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class TreeAddEvent extends TreeEvent {

	/**
     * Path to the node added to the tree.
     */
	protected final Object[] path;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new tree add event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param treeName The programmatic name of the tree that was changed.
	 * @param path The path to the part of the tree that was added.
	 */
	public TreeAddEvent(Model source, String treeName, List<?> path) {
		this(source, treeName, path.toArray());  // NPE if 'path' is null
	}

	private TreeAddEvent(Model source, String treeName, Object[] path) {
		super(source, treeName);
		this.path = path;
	}


	// ********** standard state **********

	/**
	 * Return the path to the part of the tree that was added.
	 */
	public Iterable<?> getPath() {
		return new ArrayIterable<Object>(this.path);
	}


	// ********** cloning **********

	public TreeAddEvent clone(Model newSource) {
		return this.clone(newSource, this.treeName);
	}

	/**
	 * Return a copy of the event with the specified source and tree name
	 * replacing the current source and tree name.
	 */
	public TreeAddEvent clone(Model newSource, String newTreeName) {
		return new TreeAddEvent(newSource, newTreeName, this.path);
	}

}
