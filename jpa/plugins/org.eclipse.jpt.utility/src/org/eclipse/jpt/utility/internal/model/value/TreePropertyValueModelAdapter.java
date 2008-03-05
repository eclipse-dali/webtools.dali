/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.utility.model.value.TreeValueModel;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a tree value model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * 
 * Subclasses must override:
 * - #buildValue()
 *     to return the current property value, as derived from the
 *     current collection value
 * 
 * Subclasses might want to override:
 * - #stateChanged(StateChangeEvent event)
 *     to improve performance (by not recalculating the value, if possible)
 */
public abstract class TreePropertyValueModelAdapter<T>
	extends AspectPropertyValueModelAdapter<T>
{
	/** The wrapped tree value model. */
	protected final TreeValueModel<?> treeHolder;

	/** A listener that allows us to synch with changes to the wrapped tree holder. */
	protected final TreeChangeListener treeChangeListener;


	// ********** constructor/initialization **********

	/**
	 * Construct a property value model with the specified wrapped
	 * tree value model.
	 */
	protected TreePropertyValueModelAdapter(TreeValueModel<?> treeHolder) {
		super();
		this.treeHolder = treeHolder;
		this.treeChangeListener = this.buildTreeChangeListener();
	}

	protected TreeChangeListener buildTreeChangeListener() {
		return new TreeChangeListener() {
			public void nodeAdded(TreeChangeEvent event) {
				TreePropertyValueModelAdapter.this.nodeAdded(event);
			}
			public void nodeRemoved(TreeChangeEvent event) {
				TreePropertyValueModelAdapter.this.nodeRemoved(event);
			}
			public void treeCleared(TreeChangeEvent event) {
				TreePropertyValueModelAdapter.this.treeCleared(event);
			}
			public void treeChanged(TreeChangeEvent event) {
				TreePropertyValueModelAdapter.this.treeChanged(event);
			}
			@Override
			public String toString() {
				return "tree change listener";
			}
		};
	}


	// ********** behavior **********

	/**
	 * Start listening to the tree holder.
	 */
	@Override
	protected void engageModel_() {
		this.treeHolder.addTreeChangeListener(this.treeChangeListener);
	}

	/**
	 * Stop listening to the tree holder.
	 */
	@Override
	protected void disengageModel_() {
		this.treeHolder.removeTreeChangeListener(this.treeChangeListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.treeHolder);
	}

	
	// ********** state change support **********

	/**
	 * Nodes were added to the wrapped tree holder;
	 * propagate the change notification appropriately.
	 */
	protected void nodeAdded(TreeChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Nodes were removed from the wrapped tree holder;
	 * propagate the change notification appropriately.
	 */
	protected void nodeRemoved(TreeChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped tree holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected void treeCleared(TreeChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped tree holder changed;
	 * propagate the change notification appropriately.
	 */
	protected void treeChanged(TreeChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

}
