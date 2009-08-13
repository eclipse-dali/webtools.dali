/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.event;

import java.util.Collection;

import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.model.Model;

/**
 * A "tree change" event gets delivered whenever a model changes a "bound"
 * or "constrained" tree. A <code>TreeChangeEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.utility.model.listener.TreeChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class TreeChangeEvent extends TreeEvent {

    /**
     * The current nodes in the changed tree.
     */
	protected final Object[] nodes;

	private static final long serialVersionUID = 1L;


	// ********** constructor **********

	/**
	 * Construct a new tree change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param treeName The programmatic name of the tree that was changed.
	 * @param nodes The current nodes in the changed tree.
	 */
	public TreeChangeEvent(Model source, String treeName, Collection<?> nodes) {
		this(source, treeName, nodes.toArray());  // NPE if 'nodes' is null
	}
	
	private TreeChangeEvent(Model source, String treeName, Object[] nodes) {
		super(source, treeName);
		this.nodes = nodes;
	}
	

	// ********** standard state **********

	/**
	 * Return the current nodes in the changed tree.
	 */
	public Iterable<?> getNodes() {
		return new ArrayIterable<Object>(this.nodes);
	}

	/**
	 * Return the current nodes in the changed tree.
	 */
	public int getNodesSize() {
		return this.nodes.length;
	}


	// ********** cloning **********

	public TreeChangeEvent clone(Model newSource) {
		return this.clone(newSource, this.treeName);
	}

	/**
	 * Return a copy of the event with the specified source and tree name
	 * replacing the current source and tree name.
	 */
	public TreeChangeEvent clone(Model newSource, String newTreeName) {
		return new TreeChangeEvent(newSource, newTreeName, this.nodes);
	}

}
