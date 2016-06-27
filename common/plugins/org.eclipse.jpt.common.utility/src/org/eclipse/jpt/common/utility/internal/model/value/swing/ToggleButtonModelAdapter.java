/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * This {@link javax.swing.ButtonModel} can be used to keep a listener
 * (e.g. a {@link javax.swing.JCheckBox} or a {@link javax.swing.JRadioButton})
 * in sync with a boolean model.
 */
public class ToggleButtonModelAdapter
	extends ToggleButtonModel
{
	/**
	 * The default setting for the toggle button; for when the underlying model is null.
	 * The default [default value] is false (i.e. the toggle button is unchecked/empty).
	 */
	protected final boolean defaultValue;

	/** A value model on the underlying model boolean. */
	protected final ModifiablePropertyValueModel<Boolean> booleanModel;

	/**
	 * A listener that allows us to synchronize with
	 * changes made to the underlying model boolean.
	 */
	protected final PropertyChangeListener booleanChangeListener;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Constructor - the model is required.
	 * The default value will be <code>false</code>.
	 */
	public ToggleButtonModelAdapter(ModifiablePropertyValueModel<Boolean> booleanModel) {
		this(booleanModel, false);
	}

	/**
	 * Constructor - the model is required.
	 */
	public ToggleButtonModelAdapter(ModifiablePropertyValueModel<Boolean> booleanModel, boolean defaultValue) {
		super();
		if (booleanModel == null) {
			throw new NullPointerException();
		}
		this.booleanModel = booleanModel;
		this.booleanChangeListener = this.buildBooleanChangeListener();
		// postpone listening to the underlying model
		// until we have listeners ourselves...
		this.defaultValue = defaultValue;
	}


	// ********** initialization **********

	protected PropertyChangeListener buildBooleanChangeListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildBooleanChangeListener_());
	}

	protected PropertyChangeListener buildBooleanChangeListener_() {
		return new BooleanChangeListener();
	}

	protected class BooleanChangeListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			ToggleButtonModelAdapter.this.booleanChanged(event);
		}
	}

	/**
	 * The underlying model has changed - synchronize accordingly.
	 */
	protected void booleanChanged(PropertyChangeEvent event) {
		this.setSelected((Boolean) event.getNewValue());
	}


	// ********** selection **********

	/**
	 * Synchronize with the specified value.
	 * If it is <code>null</code>, use the default value
	 * (which is typically <code>false</code>).
	 */
	protected void setSelected(Boolean value) {
		if (value == null) {
			this.setSelected(this.getDefaultValue());
		} else {
			this.setSelected(value.booleanValue());
		}
	}

	/**
	 * Extend to update the underlying model if necessary.
	 */
    @Override
	public void setSelected(boolean b) {
		if (this.isSelected() != b) { // stop the recursion!
			super.setSelected(b); // put the super call first, otherwise the following gets called twice
			this.booleanModel.setValue(Boolean.valueOf(b));
		}
	}

	protected boolean getDefaultValue() {
		return this.defaultValue;
	}


	// ********** listeners **********

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
    @Override
	public void addActionListener(ActionListener l) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addActionListener(l);
	}

	/**
	 * Extend to stop listening to the underlying model if appropriate.
	 */
    @Override
	public void removeActionListener(ActionListener l) {
		super.removeActionListener(l);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
    @Override
	public void addItemListener(ItemListener l) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addItemListener(l);
	}

	/**
	 * Extend to stop listening to the underlying model if appropriate.
	 */
    @Override
	public void removeItemListener(ItemListener l) {
		super.removeItemListener(l);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
    @Override
	public void addChangeListener(ChangeListener l) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addChangeListener(l);
	}

	/**
	 * Extend to stop listening to the underlying model if appropriate.
	 */
    @Override
	public void removeChangeListener(ChangeListener l) {
		super.removeChangeListener(l);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Return whether we have no listeners at all.
	 */
	protected boolean hasNoListeners() {
		return this.listenerList.getListenerCount() == 0;
	}

	protected void engageModel() {
		this.booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
		this.setSelected(this.booleanModel.getValue());
	}

	protected void disengageModel() {
		this.booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
	}


	// ********** misc **********

    @Override
	public String toString() {
		return ObjectTools.toString(this, this.booleanModel);
	}
}
