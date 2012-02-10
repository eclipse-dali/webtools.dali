/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.AbstractChangeListener;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * <p>
 * Subclasses must implement:<ul>
 * <li>{@link #buildValue()}<p>
 *     to return the current property value, as derived from the
 *     current model
 * </ul>
 * Subclasses might want to override the following methods
 * to improve performance (by not recalculating the value, if possible):<ul>
 * <li>{@link #modelChanged(ChangeEvent event)}
 * <li>{@link #buildChangeListener()}
 * </ul>
 */
public abstract class ChangePropertyValueModelAdapter<T>
	extends AbstractPropertyValueModelAdapter<T>
{
	/** The wrapped model. */
	protected final Model model;

	/** A listener that allows us to sync with changes to the wrapped collection model. */
	protected final ChangeListener modelListener;


	// ********** constructor/initialization **********

	/**
	 * Construct a property value model with the specified wrapped model.
	 */
	protected ChangePropertyValueModelAdapter(Model model) {
		super();
		if (model == null) {
			throw new NullPointerException();
		}
		this.model = model;
		this.modelListener = this.buildChangeListener();
	}

	protected ChangeListener buildChangeListener() {
		return new ModelListener();
	}

	protected class ModelListener
		extends AbstractChangeListener
	{
		@Override
		protected void modelChanged(ChangeEvent event) {
			ChangePropertyValueModelAdapter.this.modelChanged(event);
		}
	}


	// ********** behavior **********

	/**
	 * Start listening to the model.
	 */
	@Override
	protected void engageModel_() {
		this.model.addChangeListener(this.modelListener);
	}

	/**
	 * Stop listening to the model.
	 */
	@Override
	protected void disengageModel_() {
		this.model.removeChangeListener(this.modelListener);
	}

	
	// ********** change support **********

	/**
	 * The wrapped model has changed;
	 * propagate the change notification appropriately.
	 */
	protected void modelChanged(@SuppressWarnings("unused") ChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}
}
