/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * This abstract class provides the infrastructure needed to wrap
 * another list value model, "lazily" listen to it, and propagate
 * its change notifications.
 */
public abstract class ListValueModelWrapper
	extends AbstractModel
	implements ListValueModel
{

	/** The wrapped list value model. */
	protected ListValueModel listHolder;

	/** A listener that allows us to synch with changes to the wrapped list holder. */
	protected ListChangeListener listChangeListener;


	// ********** constructors **********

	/**
	 * Construct a list value model with the specified wrapped
	 * list value model.
	 */
	protected ListValueModelWrapper(ListValueModel listHolder) {
		super();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
	}
	

	// ********** initialization **********

	@Override
	protected void initialize() {
		super.initialize();
		this.listChangeListener = this.buildListChangeListener();
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, VALUE);
	}

	protected ListChangeListener buildListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				ListValueModelWrapper.this.itemsAdded(e);
			}
			public void itemsRemoved(ListChangeEvent e) {
				ListValueModelWrapper.this.itemsRemoved(e);
			}
			public void itemsReplaced(ListChangeEvent e) {
				ListValueModelWrapper.this.itemsReplaced(e);
			}
			public void itemsMoved(ListChangeEvent e) {
				ListValueModelWrapper.this.itemsMoved(e);
			}
			public void listCleared(ListChangeEvent e) {
				ListValueModelWrapper.this.listCleared(e);
			}
			public void listChanged(ListChangeEvent e) {
				ListValueModelWrapper.this.listChanged(e);
			}
			@Override
			public String toString() {
				return "list change listener";
			}
		};
	}


	// ********** extend change support **********

	/**
	 * Extend to start listening to the nested model if necessary.
	 */
	@Override
	public synchronized void addListChangeListener(ListChangeListener listener) {
		if (this.hasNoListChangeListeners(VALUE)) {
			this.engageModel();
		}
		super.addListChangeListener(listener);
	}
	
	/**
	 * Extend to start listening to the nested model if necessary.
	 */
	@Override
	public synchronized void addListChangeListener(String listName, ListChangeListener listener) {
		if (listName == VALUE && this.hasNoListChangeListeners(VALUE)) {
			this.engageModel();
		}
		super.addListChangeListener(listName, listener);
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
	@Override
	public synchronized void removeListChangeListener(ListChangeListener listener) {
		super.removeListChangeListener(listener);
		if (this.hasNoListChangeListeners(VALUE)) {
			this.disengageModel();
		}
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
	@Override
	public synchronized void removeListChangeListener(String listName, ListChangeListener listener) {
		super.removeListChangeListener(listName, listener);
		if (listName == VALUE && this.hasNoListChangeListeners(VALUE)) {
			this.disengageModel();
		}
	}
	

	// ********** behavior **********

	/**
	 * Start listening to the list holder.
	 */
	protected void engageModel() {
		this.listHolder.addListChangeListener(VALUE, this.listChangeListener);
	}

	/**
	 * Stop listening to the list holder.
	 */
	protected void disengageModel() {
		this.listHolder.removeListChangeListener(VALUE, this.listChangeListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.listHolder);
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsAdded(ListChangeEvent e);

	/**
	 * Items were removed from the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsRemoved(ListChangeEvent e);

	/**
	 * Items were replaced in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsReplaced(ListChangeEvent e);

	/**
	 * Items were moved in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsMoved(ListChangeEvent e);

	/**
	 * The wrapped list holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected abstract void listCleared(ListChangeEvent e);

	/**
	 * The value of the wrapped list holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected abstract void listChanged(ListChangeEvent e);

}
