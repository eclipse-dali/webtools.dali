/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * A <code>StateController</code> keeps the state of a collection of widgets in
 * synch with the provided boolean holder.
 *
 * @see ControlEnabler
 * @see ControlVisibilityEnabler
 * @see PaneEnabler
 * @see PaneVisibilityEnabler
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
abstract class StateController
{
	/**
	 * A listener that allows us to synchronize the controlHolders with changes
	 * made to the underlying boolean model.
	 */
	private PropertyChangeListener booleanChangeListener;

	/**
	 * A value model on the underlying boolean model
	 */
	private PropertyValueModel<Boolean> booleanHolder;

	/**
	 * The collection of <code>IControlHolder</code>s whose state is kept in sync
	 * with the boolean holder's value.
	 */
	private Collection<IControlHolder> controlHolders;

	/**
	 * The default setting for the state; for when the underlying model is
	 * <code>null</code>. The default [default value] is <code>false<code>.
	 */
	private boolean defaultValue;

	/**
	 * Creates a new <code>StateController</code>.
	 */
	StateController() {
		super();
		initialize();
	}

	/**
	 * Creates a new <code>StateController</code> with a default value of
	 * <code>false</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of <code>IControlHolder</code>s whose
	 * state is kept in sync with the boolean holder's value
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                Collection<IControlHolder> controlHolders) {

		this(booleanHolder, controlHolders, false);
	}

	/**
	 * Creates a new <code>StateController</code> with a default value of
	 * <code>false</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of <code>IControlHolder</code>s whose
	 * state is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                Collection<IControlHolder> controlHolders,
	                boolean defaultValue) {

		this();
		initialize(booleanHolder, controlHolders, defaultValue);
	}

	/**
	 * Creates a new <code>StateController</code> with a default value of
	 * <code>false</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolder The <code>IControlHolder</code> whose state is kept
	 * in sync with the boolean holder's value
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                IControlHolder controlHolder) {

		this(booleanHolder, controlHolder, false);
	}

	/**
	 * Creates a new <code>StateController</code> with a default value of
	 * <code>false</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of <code>IControlHolder</code>s whose
	 * state is kept in sync with the boolean holder's value
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                IControlHolder... controlHolders) {

		this(booleanHolder, CollectionTools.collection(controlHolders), false);
	}

	/**
	 * Creates a new <code>StateController</code> with a default value of
	 * <code>false</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolder The <code>IControlHolder</code> whose state is kept
	 * in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                IControlHolder controlHolder,
	                boolean defaultValue) {

		this(booleanHolder, new IControlHolder[] { controlHolder }, false);
	}

	/**
	 * Creates a new <code>StateController</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of <code>IControlHolder</code>s whose
	 * state is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                IControlHolder[] controlHolders,
	                boolean defaultValue) {

		this();
		this.initialize(booleanHolder, CollectionTools.collection(controlHolders), defaultValue);
	}

	/**
	 * Creates a new <code>StateController</code> with a default value of
	 * <code>false</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders An iterator on the collection of
	 * <code>IControlHolder</code>s whose state is kept in sync with the boolean
	 * holder's value
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                Iterator<IControlHolder> controlHolders) {

		this(booleanHolder, CollectionTools.collection(controlHolders), false);
	}

	/**
	 * Creates a new <code>StateController</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders An iterator on the collection of
	 * <code>IControlHolder</code>s whose state is kept in sync with the boolean
	 * holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	StateController(PropertyValueModel<Boolean> booleanHolder,
	                Iterator<IControlHolder> controlHolders,
	                boolean defaultValue) {

		this();
		initialize(booleanHolder, CollectionTools.collection(controlHolders), defaultValue);
	}

	/**
	 * Returns the boolean primitive of the given <code>Boolean</code> value but
	 * also checks for <code>null</code>, if that is the case, then
	 * {@link #defaultValue} is returned.
	 *
	 * @param value The <code>Boolean</code> value to be returned as a primitive
	 * @return The primitive of the given value or {@link #defaultValue}when the
	 * value is <code>null</code>
	 */
	protected boolean booleanValue(Boolean value) {
		return (value == null) ? this.defaultValue : value;
	}

	/**
	 * Creates a listener for the boolean holder.
	 *
	 * @return A new <code>PropertyChangeListener</code>
	 */
	private PropertyChangeListener buildBooleanChangeListener() {
		return new SWTPropertyChangeListenerWrapper(
			buildBooleanChangeListener_()
		)
		{
			@Override
			public String toString() {
				return "StateController.SWTPropertyChangeListenerWrapper";
			}
		};
	}

	/**
	 * Creates a listener for the boolean holder.
	 *
	 * @return A new <code>PropertyChangeListener</code>
	 */
	private PropertyChangeListener buildBooleanChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				updateState(booleanValue(booleanHolder.value()));
			}

			@Override
			public String toString() {
				return "StateController.PropertyChangeListener";
			}
		};
	}

	/**
	 * Returns an <code>Iterator</code> over the collection of
	 * <code>IControlHolder</code>s.
	 *
	 * @return The iteration of <code>IControlHolder</code>s
	 */
	protected final Iterator<IControlHolder> controlHolders() {
		return new CloneIterator<IControlHolder>(this.controlHolders);
	}

	/**
	 * Initializes this <code>StateController</code> by building the appropriate
	 * listeners.
	 */
	protected void initialize() {
		this.booleanChangeListener = this.buildBooleanChangeListener();
	}

	/**
	 * Initializes this <code>StateController</code> with the given state.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders A <code>IControlHolder</code>s whose enablement state
	 * is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	protected void initialize(PropertyValueModel<Boolean> booleanHolder,
									  Collection<IControlHolder> controlHolders,
									  boolean defaultValue) {

		Assert.isNotNull(booleanHolder,  "The holder of the boolean value cannot be null");
		Assert.isNotNull(controlHolders, "The collection of ControlHolders cannot be null");

		this.controlHolders = new ArrayList<IControlHolder>(controlHolders);
		this.defaultValue   = defaultValue;
		this.booleanHolder  = booleanHolder;

		this.booleanHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			this.booleanChangeListener
		);

		this.updateState();
	}

	/**
	 * Updates the state of the control holders.
	 */
	protected void updateState() {
		this.updateState(booleanValue(booleanHolder.value()));
	}

	/**
	 * Updates the state of the <code>Control</code>s.
	 *
	 * @param state The new state the widgets need to have
	 */
	protected void updateState(boolean state) {
		for (IControlHolder controlHolder : this.controlHolders) {
			controlHolder.updateState(state);
		}
	}

	/**
	 * The holder of the actual widget.
	 */
	static interface IControlHolder {

		/**
		 * Updates the state of the wrapped control.
		 *
		 * @param state The new state the control should have
		 */
		void updateState(boolean state);
	}
}
