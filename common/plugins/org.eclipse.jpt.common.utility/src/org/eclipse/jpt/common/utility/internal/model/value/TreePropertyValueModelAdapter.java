/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.common.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.common.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.common.utility.model.value.TreeValueModel;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a tree value model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * <p>
 * Subclasses must override:<ul>
 * <li>{@link #buildValue()}<p>
 *     to return the current property value, as derived from the
 *     current collection value
 * </ul>
 * Subclasses might want to override the following methods
 * to improve performance (by not recalculating the value, if possible):<ul>
 * <li>{@link #nodeAdded(TreeChangeEvent event)}
 * <li>{@link #nodeRemoved(TreeChangeEvent event)}
 * <li>{@link #treeCleared(TreeChangeEvent event)}
 * <li>{@link #treeChanged(TreeChangeEvent event)}
 * </ul>
 */
public abstract class TreePropertyValueModelAdapter<T>
	extends AbstractPropertyValueModelAdapter<T>
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
			public void nodeAdded(TreeAddEvent event) {
				TreePropertyValueModelAdapter.this.nodeAdded(event);
			}
			public void nodeRemoved(TreeRemoveEvent event) {
				TreePropertyValueModelAdapter.this.nodeRemoved(event);
			}
			public void treeCleared(TreeClearEvent event) {
				TreePropertyValueModelAdapter.this.treeCleared(event);
			}
			public void treeChanged(TreeChangeEvent event) {
				TreePropertyValueModelAdapter.this.treeChanged(event);
			}
			@Override
			public String toString() {
				return "tree change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** behavior **********

	/**
	 * Start listening to the tree holder.
	 */
	@Override
	protected void engageModel_() {
		this.treeHolder.addTreeChangeListener(TreeValueModel.NODES, this.treeChangeListener);
	}

	/**
	 * Stop listening to the tree holder.
	 */
	@Override
	protected void disengageModel_() {
		this.treeHolder.removeTreeChangeListener(TreeValueModel.NODES, this.treeChangeListener);
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
	protected void nodeAdded(@SuppressWarnings("unused") TreeAddEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Nodes were removed from the wrapped tree holder;
	 * propagate the change notification appropriately.
	 */
	protected void nodeRemoved(@SuppressWarnings("unused") TreeRemoveEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped tree holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected void treeCleared(@SuppressWarnings("unused") TreeClearEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped tree holder changed;
	 * propagate the change notification appropriately.
	 */
	protected void treeChanged(@SuppressWarnings("unused") TreeChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

}
