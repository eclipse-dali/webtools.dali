/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.swing;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * This javax.swing.ButtonModel can be used to keep a listener
 * (e.g. a JCheckBox or a JRadioButton) in synch with a PropertyValueModel
 * on a boolean.
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
	protected final WritablePropertyValueModel<Boolean> booleanHolder;

	/**
	 * A listener that allows us to synchronize with
	 * changes made to the underlying model boolean.
	 */
	protected final PropertyChangeListener booleanChangeListener;


	// ********** constructors **********

	/**
	 * Constructor - the boolean holder is required.
	 */
	public ToggleButtonModelAdapter(WritablePropertyValueModel<Boolean> booleanHolder, boolean defaultValue) {
		super();
		if (booleanHolder == null) {
			throw new NullPointerException();
		}
		this.booleanHolder = booleanHolder;
		this.booleanChangeListener = this.buildBooleanChangeListener();
		// postpone listening to the underlying model
		// until we have listeners ourselves...
		this.defaultValue = defaultValue;
	}

	/**
	 * Constructor - the boolean holder is required.
	 * The default value will be false.
	 */
	public ToggleButtonModelAdapter(WritablePropertyValueModel<Boolean> booleanHolder) {
		this(booleanHolder, false);
	}


	// ********** initialization **********

	protected PropertyChangeListener buildBooleanChangeListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildBooleanChangeListener_());
	}

	protected PropertyChangeListener buildBooleanChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				ToggleButtonModelAdapter.this.booleanChanged(event);
			}
		    @Override
			public String toString() {
				return "boolean listener";
			}
		};
	}


	// ********** ButtonModel implementation **********

	/**
	 * Extend to update the underlying model if necessary.
	 */
    @Override
	public void setSelected(boolean b) {
		if (this.isSelected() != b) {	// stop the recursion!
			super.setSelected(b);//put the super call first, otherwise the following gets called twice
			this.booleanHolder.setValue(Boolean.valueOf(b));
		}
	}

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


	// ********** queries **********

	/**
	 * Return whether we have no listeners at all.
	 */
	protected boolean hasNoListeners() {
		return this.listenerList.getListenerCount() == 0;
	}

	protected boolean defaultValue() {
		return this.defaultValue;
	}


	// ********** behavior **********

	/**
	 * Synchronize with the specified value.
	 * If it is null, use the default value (which is typically false).
	 */
	protected void setSelected(Boolean value) {
		if (value == null) {
			this.setSelected(this.defaultValue());
		} else {
			this.setSelected(value.booleanValue());
		}
	}

	/**
	 * The underlying model has changed - synchronize accordingly.
	 */
	protected void booleanChanged(PropertyChangeEvent event) {
		this.setSelected((Boolean) event.newValue());
	}

	protected void engageModel() {
		this.booleanHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
		this.setSelected(this.booleanHolder.value());
	}

	protected void disengageModel() {
		this.booleanHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
	}


	// ********** standard methods **********

    @Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.booleanHolder);
	}

}
