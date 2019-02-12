/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import java.io.Serializable;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;

/**
 * Abstract class that should have been provided by the JDK
 * (a la {@link javax.swing.AbstractListModel}). This class provides:<ul>
 * <li>support for a collection of listeners
 * <li>a number of convenience methods for firing events for those listeners
 * </ul>
 */
public abstract class AbstractTreeModel
	implements TreeModel, Serializable
{
	/** Our listeners. */
	protected final EventListenerList listenerList;

	private static final long serialVersionUID = 1L;


	// ********** constructors/initialization **********

	protected AbstractTreeModel() {
		super();
		this.listenerList = new EventListenerList();
	}


	// ********** partial TreeModel implementation **********

	public void addTreeModelListener(TreeModelListener l) {
		this.listenerList.add(TreeModelListener.class, l);
	}

	public void removeTreeModelListener(TreeModelListener l) {
		this.listenerList.remove(TreeModelListener.class, l);
	}


	// ********** queries **********

	/**
	 * Return the model's current collection of listeners.
	 * (There seems to be a pattern of making this type of method public;
	 * although it should probably be protected....)
	 */
	public TreeModelListener[] treeModelListeners() {
 		return this.listenerList.getListeners(TreeModelListener.class);
	}

	/**
	 * Return whether this model has no listeners.
	 */
	protected boolean hasNoTreeModelListeners() {
		return this.listenerList.getListenerCount(TreeModelListener.class) == 0;
	}

	/**
	 * Return whether this model has any listeners.
	 */
	protected boolean hasTreeModelListeners() {
		return ! this.hasNoTreeModelListeners();
	}


	// ********** behavior **********

	/**
	 * Notify listeners of a model change.
	 * A significant property of the nodes changed, but the nodes themselves
	 * are still the same objects.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeNodesChanged(Object[] path, int[] childIndices, Object[] children) {
		// guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		TreeModelEvent event = null;
		// process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==TreeModelListener.class) {
				// lazily create the event
				if (event == null) {
					event = new TreeModelEvent(this, path, childIndices, children);
				}
				((TreeModelListener) listeners[i+1]).treeNodesChanged(event);
			}
		}
	}


	/**
	 * Notify listeners of a model change.
	 * A significant property of the node changed, but the node itself is the same object.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeNodeChanged(Object[] path, int childIndex, Object child) {
		this.fireTreeNodesChanged(path, new int[] {childIndex}, new Object[] {child});
	}

	/**
	 * Notify listeners of a model change.
	 * A significant property of the root changed, but the root itself is the same object.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeRootChanged(Object root) {
		this.fireTreeNodesChanged(new Object[] {root}, null, null);
	}

	/**
	 * Notify listeners of a model change.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeNodesInserted(Object[] path, int[] childIndices, Object[] children) {
		// guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		TreeModelEvent event = null;
		// process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==TreeModelListener.class) {
				// lazily create the event
				if (event == null) {
					event = new TreeModelEvent(this, path, childIndices, children);
				}
				((TreeModelListener) listeners[i+1]).treeNodesInserted(event);
			}
		}
	}

	/**
	 * Notify listeners of a model change.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeNodeInserted(Object[] path, int childIndex, Object child) {
		this.fireTreeNodesInserted(path, new int[] {childIndex}, new Object[] {child});
	}

	/**
	 * Notify listeners of a model change.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeNodesRemoved(Object[] path, int[] childIndices, Object[] children) {
		// guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		TreeModelEvent event = null;
		// process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==TreeModelListener.class) {
				// lazily create the event
				if (event == null) {
					event = new TreeModelEvent(this, path, childIndices, children);
				}
				((TreeModelListener) listeners[i+1]).treeNodesRemoved(event);
			}
		}
	}

	/**
	 * Notify listeners of a model change.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeNodeRemoved(Object[] path, int childIndex, Object child) {
		this.fireTreeNodesRemoved(path, new int[] {childIndex}, new Object[] {child});
	}

	/**
	 * Notify listeners of a model change.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeStructureChanged(Object[] path) {
		// guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		TreeModelEvent event = null;
		// process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==TreeModelListener.class) {
				// lazily create the event
				if (event == null) {
					event = new TreeModelEvent(this, path);
				}
				((TreeModelListener) listeners[i+1]).treeStructureChanged(event);
			}
		}
	}

	/**
	 * Notify listeners of a model change.
	 * @see javax.swing.event.TreeModelEvent
	 * @see javax.swing.event.TreeModelListener
	 */
	protected void fireTreeRootReplaced(Object newRoot) {
		this.fireTreeStructureChanged(new Object[] {newRoot});
	}

}
