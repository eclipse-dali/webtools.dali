/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * This abstract class provides the infrastructure for "lazily" adding listeners
 * to an underlying model as necessary. Subclasses will need to engage and
 * disegage the underlying model and fire the appropriate list change
 * events. Subclasses must implement the appropriate {@link ListValueModel}.
 * <p>
 * Subclasses must implement the following methods:<ul>
 * <li>{@link #engageModel()}<p>
 *     implement this method to add the appropriate listener to the underlying model
 * <li>{@link #disengageModel()}<p>
 *     implement this method to remove the appropriate listener from the underlying model
 * </ul>
 */
public abstract class AbstractListValueModel
	extends AbstractModel
{

	// ********** constructor/initialization **********

	protected AbstractListValueModel() {
		super();
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, ListChangeListener.class, ListValueModel.LIST_VALUES);
	}


	// ********** extend change support **********

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
	@Override
	public void addChangeListener(ChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addChangeListener(listener);
	}

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
	@Override
	public void addListChangeListener(String listName, ListChangeListener listener) {
		if (listName.equals(ListValueModel.LIST_VALUES) && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addListChangeListener(listName, listener);
	}

	/**
	 * Extend to stop listening to the underlying model if necessary.
	 */
	@Override
	public void removeChangeListener(ChangeListener listener) {
		super.removeChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Extend to stop listening to the underlying model if necessary.
	 */
	@Override
	public void removeListChangeListener(String listName, ListChangeListener listener) {
		super.removeListChangeListener(listName, listener);
		if (listName.equals(ListValueModel.LIST_VALUES) && this.hasNoListeners()) {
			this.disengageModel();
		}
	}


	// ********** queries **********

	/**
	 * Return whether the model has no collection value listeners.
	 */
	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}

	/**
	 * Return whether the model has any collection value listeners.
	 */
	protected boolean hasListeners() {
		return this.hasAnyListChangeListeners(ListValueModel.LIST_VALUES);
	}


	// ********** behavior **********

	/**
	 * Engage the underlying model.
	 */
	protected abstract void engageModel();

	/**
	 * Stop listening to the underlying model.
	 */
	protected abstract void disengageModel();

}
