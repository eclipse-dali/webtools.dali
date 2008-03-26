/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
 * A "tree change" event gets delivered whenever a model changes a "bound"
 * or "constrained" tree. A TreeChangeEvent is sent as an
 * argument to the TreeChangeListener.
 * 
 * Normally a TreeChangeEvent is accompanied by the tree name and a path
 * to the part of the tree that was changed.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class TreeChangeEvent extends ChangeEvent {

	/** Name of the tree that changed. */
	private final String treeName;

    /**
     * Path to the parent of the part of the tree that was changed.
     * May be empty, if not known or if the entire tree changed.
     */
	protected final Object[] path;

	private static final Object[] EMPTY_PATH = new Object[0];

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new tree change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param treeName The programmatic name of the tree that was changed.
	 * @param path The path to the part of the tree that was changed.
	 */
	public TreeChangeEvent(Model source, String treeName, Object[] path) {
		super(source);
		if ((treeName == null) || (path == null)) {
			throw new NullPointerException();
		}
		this.treeName = treeName;
		this.path = path;
	}
	
	/**
	 * Construct a new tree change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param treeName The programmatic name of the tree that was changed.
	 */
	public TreeChangeEvent(Model source, String treeName) {
		this(source, treeName, EMPTY_PATH);
	}
	

	// ********** standard state **********

	/**
	 * Return the programmatic name of the tree that was changed.
	 */
	public String getTreeName() {
		return this.treeName;
	}

	@Override
	public String getAspectName() {
		return this.treeName;
	}

	/**
	 * Return the path to the part of the tree that was changed.
	 * May be empty, if not known.
	 */
	public Object[] getPath() {
		return this.path;
	}


	// ********** cloning **********

	@Override
	public TreeChangeEvent cloneWithSource(Model newSource) {
		return new TreeChangeEvent(newSource, this.treeName, this.path);
	}

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source and the tree name.
	 */
	public TreeChangeEvent cloneWithSource(Model newSource, String newTreeName) {
		return new TreeChangeEvent(newSource, newTreeName, this.path);
	}

}
