/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.TreeNodeValueModel;

/**
 * Subclasses need only implement the following methods:
 * 
 * #value()
 *	    return the user-determined "value" of the node,
 *     i.e. the object "wrapped" by the node
 * 
 * #setValue(Object)
 *     set the user-determined "value" of the node,
 *     i.e. the object "wrapped" by the node;
 *     typically only overridden for nodes with "primitive" values
 * 
 * #parent()
 *     return the parent of the node, which should be another
 *     TreeNodeValueModel
 * 
 * #childrenModel()
 *     return a ListValueModel for the node's children
 * 
 * #engageValue() and #disengageValue()
 *     override these methods to listen to the node's value if
 *     it can change in a way that should be reflected in the tree
 */
public abstract class AbstractTreeNodeValueModel<V>
	extends AbstractModel
	implements TreeNodeValueModel<V>
{


	// ********** constructors **********
	
	/**
	 * Default constructor.
	 */
	protected AbstractTreeNodeValueModel() {
		super();
	}
	
	@Override
	protected ChangeSupport buildChangeSupport() {
		// this model fires *both* "value property change" and "state change" events...
//		return new SingleAspectChangeSupport(this, PropertyChangeListener.class, PropertyValueModel.VALUE);
		return super.buildChangeSupport();
	}


	// ********** extend AbstractModel implementation **********

	/**
	 * Clients should be adding both "state change" and "value property change"
	 * listeners.
	 */
	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		if (this.hasNoStateChangeListeners()) {
			this.engageValue();
		}
		super.addStateChangeListener(listener);
	}

	/**
	 * Begin listening to the node's value's state. If the state of the node changes
	 * in a way that should be reflected in the tree, fire a "state change" event.
	 */
	protected abstract void engageValue();

	/**
	 * @see #addStateChangeListener(StateChangeListener)
	 */
	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		super.removeStateChangeListener(listener);
		if (this.hasNoStateChangeListeners()) {
			this.disengageValue();
		}
	}

	/**
	 * Stop listening to the node's value.
	 * @see #engageValue()
	 */
	protected abstract void disengageValue();


	// ********** WritablePropertyValueModel implementation **********
	
	public void setValue(V value) {
		throw new UnsupportedOperationException();
	}


	// ********** TreeNodeValueModel implementation **********
	
	@SuppressWarnings("unchecked")
	public TreeNodeValueModel<V>[] path() {
		List<TreeNodeValueModel<V>> path = ListTools.reverse(ListTools.arrayList(this.backPath()));
		return path.toArray(new TreeNodeValueModel[path.size()]);
	}

	/**
	 * Return an iterator that climbs up the node's path,
	 * starting with, and including, the node
	 * and up to, and including, the root node.
	 */
	@SuppressWarnings("unchecked")
	protected Iterator<TreeNodeValueModel<V>> backPath() {
		return IteratorTools.chainIterator(this, PARENT_TRANSFORMER);
	}

	public TreeNodeValueModel<V> child(int index) {
		return this.childrenModel().get(index);
	}

	public int childrenSize() {
		return this.childrenModel().size();
	}

	public int indexOfChild(TreeNodeValueModel<V> child) {
		ListValueModel<TreeNodeValueModel<V>> children = this.childrenModel();
		int size = children.size();
		for (int i = 0; i < size; i++) {
			if (children.get(i) == child) {
				return i;
			}
		}
		return -1;
	}

	public boolean isLeaf() {
		return this.childrenModel().size() == 0;
	}


	// ********** standard methods **********

	/**
	 * We implement #equals(Object) so that TreePaths containing these nodes
	 * will resolve properly when the nodes contain the same values. This is
	 * necessary because nodes are dropped and rebuilt willy-nilly when dealing
	 * with a sorted list of children; and this allows us to save and restore
	 * a tree's expanded paths. The nodes in the expanded paths that are
	 * saved before any modification (e.g. renaming a node) will be different
	 * from the nodes in the tree's paths after the modification, if the modification
	 * results in a possible change in the node sort order.  ~bjv
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		AbstractTreeNodeValueModel<V> other = (AbstractTreeNodeValueModel<V>) o;
		return this.getValue().equals(other.getValue());
	}

	@Override
	public int hashCode() {
		return this.getValue().hashCode();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}

}
