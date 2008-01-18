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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * A <code>ControlEnabler</code> keeps the "enabled" state of a collection of
 * widgets in synch with the provided boolean holder.
 *
 * @see BaseControllerEnabler
 * @see ControlEnabler
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
class AbstractControlEnabler
{
	/**
	 * A listener that allows us to synchronize the controlHolders with changes made
	 * to the underlying boolean model.
	 */
	private PropertyChangeListener booleanChangeListener;

	/**
	 * A value model on the underlying boolean model
	 */
	private PropertyValueModel<Boolean> booleanHolder;

	/**
	 * The collection of <code>IControlHolder</code>s whose "enabled" state is
	 * kept in sync with the boolean holder's value.
	 */
	private Collection<IControlHolder> controlHolders;

	/**
	 * The default setting for the "enabled" state; for when the underlying model
	 * is <code>null</code>. The default [default value] is <code>false<code>
	 * (i.e. the controlHolders are disabled).
	 */
	private boolean defaultValue;

	/**
	 * Creates a new <code>AbstractControlEnabler</code>.
	 */
	AbstractControlEnabler() {
		super();
		initialize();
	}

	/**
	 * Creates a new <code>AbstractControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of controlHolders whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                       Collection<IControlHolder> controlHolders) {

		this(booleanHolder, controlHolders, false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of <code>IControlHolder</code>s whose
	 * "enabled" state is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                       Collection<IControlHolder> controlHolders,
	                       boolean defaultValue) {

		this();
		initialize(booleanHolder, controlHolders, defaultValue);
	}

	/**
	 * Creates a new <code>ControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolder The <code>IControlHolder</code> whose "enabled" state
	 * is kept in sync with the boolean holder's value
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                       IControlHolder controlHolder) {

		this(booleanHolder, controlHolder, false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of controlHolders whose "enabled"
	 * state is kept in sync with the boolean holder's value
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                       IControlHolder... controlHolders) {

		this(booleanHolder, CollectionTools.collection(controlHolders), false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolder The <code>IControlHolder</code>s whose "enabled" state
	 * is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                       IControlHolder controlHolder,
	                       boolean defaultValue) {

		this(booleanHolder, new IControlHolder[] { controlHolder }, false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders The collection of controlHolders whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                       IControlHolder[] controlHolders,
	                       boolean defaultValue) {

		this();
		this.initialize(booleanHolder, CollectionTools.collection(controlHolders), defaultValue);
	}

	/**
	 * Creates a new <code>ControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders An iterator on the collection of controlHolders whose
	 * "enabled" state is kept in sync with the boolean holder's value
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                       Iterator<IControlHolder> controlHolders) {

		this(booleanHolder, CollectionTools.collection(controlHolders), false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controlHolders An iterator on the collection of controlHolders whose
	 * "enabled" state is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	AbstractControlEnabler(PropertyValueModel<Boolean> booleanHolder,
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
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				updateEnableState(booleanValue(booleanHolder.value()));
			}

			@Override
			public String toString() {
				return "boolean change listener";
			}
		};
	}

	/**
	 * Returns an <code>Iterator</code> over the collection of
	 * <code>IControlHolder</code>s.
	 *
	 * @return The iteration of controlHolders
	 */
	final Iterator<IControlHolder> controlHolders() {
		return this.controlHolders.iterator();
	}

	/**
	 * Initializes this <code>ControlEnabler</code> by building the
	 * appropriate listeners.
	 */
	protected void initialize() {
		this.booleanChangeListener = this.buildBooleanChangeListener();
	}

	/**
	 * Initializes this <code>ControlEnabler</code> with the given state.
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

		this.controlHolders      = new ArrayList<IControlHolder>(controlHolders);
		this.defaultValue  = defaultValue;
		this.booleanHolder = booleanHolder;
		this.booleanHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
		this.updateEnableState();
	}

	/**
	 * Updates the enablement state of the controlHolders.
	 */
	protected void updateEnableState() {
		this.updateEnableState(booleanValue(booleanHolder.value()));
	}

	/**
	 * Updates the enable state of the <code>IControlHolder</code>s.
	 *
	 * @param enabledState The new enable state the widgets need to have
	 */
	protected void updateEnableState(boolean enabled) {
		for (IControlHolder controlHolder : this.controlHolders) {
			controlHolder.setEnabled(enabled);
		}
	}

	/**
	 * This holder of the actual widget.
	 */
	static interface IControlHolder {
		void setEnabled(boolean enabled);
	}
}
