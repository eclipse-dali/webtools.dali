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
 * A "tree" event gets delivered whenever a model changes a "bound"
 * or "constrained" tree. A <code>TreeEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.common.utility.model.listener.TreeChangeListener}.
 * The intent is that any listener
 * can keep itself synchronized with the model's tree via the tree events
 * it receives and need not maintain a reference to the original tree.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class TreeEvent extends ChangeEvent {

	/** Name of the tree that changed. */
	final String treeName;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a new tree event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param treeName The programmatic name of the tree that was changed.
	 */
	public TreeEvent(Model source, String treeName) {
		super(source);
		if (treeName == null) {
			throw new NullPointerException();
		}
		this.treeName = treeName;
	}

	/**
	 * Return the programmatic name of the tree that was changed.
	 */
	public String getTreeName() {
		return this.treeName;
	}

	@Override
	protected void toString(StringBuilder sb) {
		sb.append(this.treeName);
	}

}
