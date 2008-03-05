/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.swt;

import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * 
 */
@SuppressWarnings("nls")
public class TextFieldModelAdapter {

	/**
	 * A value model on the underlying model list.
	 */
	protected final WritablePropertyValueModel<String> textHolder;

	/**
	 * A listener that allows us to synchronize the text field's contents with
	 * the model list.
	 */
	protected final PropertyChangeListener propertyChangeListener;

	/**
	 * The text field we keep synchronized with the model string.
	 */
	protected final Text textField;

	/**
	 * A listener that allows us to synchronize our selection list holder
	 * with the list box's selection.
	 */
	protected final ModifyListener textFieldModifyListener;

	/**
	 * A listener that allows us to stop listening to stuff when the list box
	 * is disposed.
	 */
	protected final DisposeListener textFieldDisposeListener;


	// ********** static methods **********

	/**
	 * Adapt the specified model list and selections to the specified list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	public static TextFieldModelAdapter adapt(
			WritablePropertyValueModel<String> textHolder,
			Text textField)
	{
		return new TextFieldModelAdapter(textHolder, textField);
	}


	// ********** constructors **********

	/**
	 * Constructor - the list holder, selections holder, list box, and
	 * string converter are required.
	 */
	protected TextFieldModelAdapter(WritablePropertyValueModel<String> textHolder, Text textField) {
		super();
		if ((textHolder == null) || (textField == null)) {
			throw new NullPointerException();
		}
		this.textHolder = textHolder;
		this.textField = textField;

		this.propertyChangeListener = this.buildPropertyChangeListener();
		this.textHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);

		this.textFieldModifyListener = this.buildTextFieldModifyListener();
		this.textField.addModifyListener(this.textFieldModifyListener);

		this.textFieldDisposeListener = this.buildTextFieldDisposeListener();
		this.textField.addDisposeListener(this.textFieldDisposeListener);

		String text = textHolder.value();
		this.textField.setText((text == null) ? "" : text);
	}


	// ********** initialization **********

	protected PropertyChangeListener buildPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildPropertyChangeListener_());
	}

	protected PropertyChangeListener buildPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				TextFieldModelAdapter.this.textChanged(event);
			}
			@Override
			public String toString() {
				return "text listener";
			}
		};
	}

	protected ModifyListener buildTextFieldModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				TextFieldModelAdapter.this.textFieldModified(event);
			}
			@Override
			public String toString() {
				return "text field modify listener";
			}
		};
	}

	protected DisposeListener buildTextFieldDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				TextFieldModelAdapter.this.textFieldDisposed(event);
			}
			@Override
			public String toString() {
				return "text field dispose listener";
			}
		};
	}


	// ********** model events **********

	protected void textChanged(PropertyChangeEvent event) {
		String text = (String) event.newValue();
		// the model can be null, but the text field cannot
		if (text == null) {
			text = "";
		}
		if ( ! text.equals(this.textField.getText())) {  // ???
			this.textField.setText(text);
		}
	}


	// ********** text field events **********

	protected void textFieldModified(ModifyEvent event) {
		this.textHolder.setValue(this.textField.getText());
	}

	protected void textFieldDisposed(DisposeEvent event) {
		// the text field is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.textField.removeDisposeListener(this.textFieldDisposeListener);
		this.textField.removeModifyListener(this.textFieldModifyListener);
		this.textHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.textHolder);
	}

}
