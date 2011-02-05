/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.listener;

import java.util.EventListener;

import org.eclipse.jpt.common.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.common.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.common.utility.model.event.TreeRemoveEvent;

/**
 * A "tree change" event gets fired whenever a model changes a "bound"
 * tree. You can register a <code>TreeChangeListener</code> with a source
 * model so as to be notified of any bound tree updates.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TreeChangeListener extends EventListener {

	/**
	 * This method gets called when a node is added to a bound tree.
	 * 
	 * @param event An event describing the event source,
	 * the tree that changed, and the path to the node that was added.
	 */
	void nodeAdded(TreeAddEvent event);

	/**
	 * This method gets called when a node is removed from a bound tree.
	 * 
	 * @param event An event describing the event source,
	 * the tree that changed, and the path to the node that was removed.
	 */
	void nodeRemoved(TreeRemoveEvent event);

	/**
	 * This method gets called when a bound tree is cleared.
	 * 
	 * @param event An event describing the event source,
	 * the tree that changed, and an empty path.
	 */
	void treeCleared(TreeClearEvent event);

	/**
	 * This method gets called when a portion of a bound tree is changed in
	 * a manner that is not easily characterized by the other methods in this
	 * interface.
	 * 
	 * @param event An event describing the event source,
	 * the tree that changed, and the current state of the
	 * tree that changed.
	 */
	void treeChanged(TreeChangeEvent event);

}
