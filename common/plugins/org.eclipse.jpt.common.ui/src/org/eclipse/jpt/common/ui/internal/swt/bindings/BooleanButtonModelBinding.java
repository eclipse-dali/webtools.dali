/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.jpt.common.ui.internal.swt.events.DisposeAdapter;
import org.eclipse.jpt.common.ui.internal.swt.events.SelectionAdapter;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * This binding can be used to keep a check-box, toggle button, or radio button
 * <em>selection</em> synchronized with a model <code>boolean</code>.
 * <p>
 * <strong>NB:</strong> This binding is bi-directional.
 * 
 * @see ModifiablePropertyValueModel
 * @see Button
 */
final class BooleanButtonModelBinding {

	// ***** model
	/** A value model on the underlying model <code>boolean</code>. */
	private final ModifiablePropertyValueModel<Boolean> booleanModel;

	/**
	 * A listener that allows us to synchronize the button's selection state with
	 * the model <code>boolean</code>.
	 */
	private final PropertyChangeListener booleanListener;

	/**
	 * The default setting for the check-box/toggle button/radio button;
	 * for when the underlying model is <code>null</code>.
	 * The default [default value] is <code>false</code> (i.e. the check-box
	 * is unchecked/toggle button popped out/radio button unchecked).
	 */
	private final boolean defaultValue;

	// ***** UI
	/**
	 * The check-box/toggle button/radio button we synchronize with
	 * the model <code>boolean</code>.
	 */
	private final Button button;

	/**
	 * A listener that allows us to synchronize the model <code>boolean</code> with
	 * the button's selection state.
	 */
	private final SelectionListener buttonSelectionListener;

	/**
	 * A listener that allows us to stop listening to stuff when the button
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener buttonDisposeListener;


	// ********** constructor **********

	/**
	 * Constructor - the boolean <code>boolean</code> and button are required.
	 */
	BooleanButtonModelBinding(ModifiablePropertyValueModel<Boolean> booleanModel, Button button, boolean defaultValue) {
		super();
		if ((booleanModel == null) || (button == null)) {
			throw new NullPointerException();
		}
		this.booleanModel = booleanModel;
		this.button = button;
		this.defaultValue = defaultValue;

		this.booleanListener = this.buildBooleanListener();
		this.booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanListener);

		this.buttonSelectionListener = this.buildButtonSelectionListener();
		this.button.addSelectionListener(this.buttonSelectionListener);

		this.buttonDisposeListener = this.buildButtonDisposeListener();
		this.button.addDisposeListener(this.buttonDisposeListener);

		this.setButtonSelection(this.booleanModel.getValue());
	}


	// ********** initialization **********

	private PropertyChangeListener buildBooleanListener() {
		return SWTListenerTools.wrap(new BooleanListener(), this.button);
	}

	/* CU private */ class BooleanListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			BooleanButtonModelBinding.this.booleanChanged(event);
		}
	}

	private SelectionListener buildButtonSelectionListener() {
		return new ButtonSelectionListener();
	}

	/* CU private */ class ButtonSelectionListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent event) {
			BooleanButtonModelBinding.this.buttonSelected();
		}
	}

	private DisposeListener buildButtonDisposeListener() {
		return new ButtonDisposeListener();
	}

	/* CU private */ class ButtonDisposeListener
		extends DisposeAdapter
	{
		@Override
		public void widgetDisposed(DisposeEvent event) {
			BooleanButtonModelBinding.this.buttonDisposed();
		}
	}


	// ********** boolean model events **********

	/**
	 * The model has changed - synchronize the button.
	 * If the new model value is <code>null</code>, use the binding's <em>default value</em>
	 * (which is typically <code>false</code>).
	 */
	/* CU private */ void booleanChanged(PropertyChangeEvent event) {
		if ( ! this.button.isDisposed()) {
			this.setButtonSelection((Boolean) event.getNewValue());
		}
	}

	private void setButtonSelection(Boolean b) {
		this.button.setSelection(this.booleanValue(b));
	}

	private boolean booleanValue(Boolean b) {
		return (b != null) ? b.booleanValue() : this.defaultValue;
	}


	// ********** button events **********

	/**
	 * The button has been "selected" - synchronize the model.
	 */
	/* CU private */ void buttonSelected() {
		this.booleanModel.setValue(Boolean.valueOf(this.button.getSelection()));
	}

	/* CU private */ void buttonDisposed() {
		// the button is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.button.removeSelectionListener(this.buttonSelectionListener);
		this.button.removeDisposeListener(this.buttonDisposeListener);
		this.booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanListener);
	}


	// ********** misc **********

    @Override
	public String toString() {
		return ObjectTools.toString(this, this.booleanModel);
	}
}
