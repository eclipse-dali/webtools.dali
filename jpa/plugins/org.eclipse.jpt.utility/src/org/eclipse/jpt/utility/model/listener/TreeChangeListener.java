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

import org.eclipse.jpt.utility.model.event.TreeChangeEvent;

/**
 * A "tree change" event gets fired whenever a model changes a "bound"
 * tree. You can register a TreeChangeListener with a source
 * model so as to be notified of any bound tree updates.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TreeChangeListener extends ChangeListener {

	/**
	 * This method gets called when a node is added to a bound tree.
	 * 
	 * @param event A TreeChangeEvent describing the event source,
	 * the tree that changed, and the path to the node that was added.
	 * 
	 * @see TreeChangeEvent#getPath()
	 */
	void nodeAdded(TreeChangeEvent event);

	/**
	 * This method gets called when a node is removed from a bound tree.
	 * 
	 * @param event A TreeChangeEvent describing the event source,
	 * the tree that changed, and the path to the node that was removed.
	 * 
	 * @see TreeChangeEvent#getPath()
	 */
	void nodeRemoved(TreeChangeEvent event);

	/**
	 * This method gets called when a bound tree is cleared.
	 * 
	 * @param event A TreeChangeEvent describing the event source,
	 * the tree that changed, and an empty path.
	 */
	void treeCleared(TreeChangeEvent event);

	/**
	 * This method gets called when a portion of a bound tree is changed in
	 * a manner that is not easily characterized by the other methods in this
	 * interface.
	 * 
	 * @param event A TreeChangeEvent describing the event source,
	 * the tree that changed, and the path to the branch of the
	 * tree that changed.
	 */
	void treeChanged(TreeChangeEvent event);

	/**
	 * This method gets called when a node is changed in a bound tree.
	 * The tree itself has not changed, but some significant aspect(s) of a
	 * node contained by the tree has changed.
	 * 
	 * @param event A TreeChangeEvent describing the event source,
	 * the tree that changed, and the path to the node that changed.
	 * 
	 * @see TreeChangeEvent#getPath()
	 */
//	void nodeChanged(TreeChangeEvent event);

}
