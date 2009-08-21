/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.listener;

import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;

/**
 * Convenience implementation of {@link TreeChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class TreeChangeAdapter implements TreeChangeListener {

	/**
	 * Default constructor.
	 */
	public TreeChangeAdapter() {
		super();
	}

	public void nodeAdded(TreeAddEvent event) {
		// do nothing
	}

	public void nodeRemoved(TreeRemoveEvent event) {
		// do nothing
	}

	public void treeCleared(TreeClearEvent event) {
		// do nothing
	}

	public void treeChanged(TreeChangeEvent event) {
		// do nothing
	}

}
