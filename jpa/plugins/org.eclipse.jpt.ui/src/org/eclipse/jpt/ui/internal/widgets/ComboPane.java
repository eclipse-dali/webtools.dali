/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import java.util.Iterator;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Pane with combo box support for automatic updating of:
 *  - selected value
 *  - default value
 *  - value choices
 */
public abstract class ComboPane<T extends Model> extends Pane<T>
{
	/**
	 * The main (only) widget of this pane.
	 */
	protected CCombo comboBox;
	
	
	// **************** constructors ******************************************
	
	protected ComboPane(
			Pane<? extends T> parentPane, 
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	protected ComboPane(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {
	
		super(parentPane, subjectHolder, parent);
	}
	
	protected ComboPane(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	// **************** initialization ****************************************
	
	@Override
	protected void initializeLayout(Composite container) {
		this.comboBox = this.addEditableCCombo(container);
		this.comboBox.addModifyListener(this.buildModifyListener());
		SWTUtil.attachDefaultValueHandler(this.comboBox);
	}
	
	protected ModifyListener buildModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				ComboPane.this.comboBoxModified();
			}
		};
	}
	
	
	// **************** abstract methods **************************************
	
	/**
	 * Return the possible values to be added to the combo during
	 * population.
	 */
	protected abstract Iterator<String> values();
	
	/**
	 * Return the default value, or <code>null</code> if no default is
	 * specified. This method is only called when the subject is non-null.
	 */
	protected abstract String getDefaultValue();
	
	/**
	 * Return the current value from the subject.
	 * This method is only called when the subject is non-null.
	 */
	protected abstract String getValue();
	
	/**
	 * Set the specified value as the new value on the subject.
	 */
	protected abstract void setValue(String value);
	
	
	// **************** overrides *********************************************
	
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);

		if ( ! this.comboBox.isDisposed()) {
			this.comboBox.setEnabled(enabled);
		}
	}
	
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);
		this.updateSelectedItem();
	}
	
	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.populateComboBox();
	}
	
	
	// **************** populating ********************************************
	
	/**
	 * Populate the combo-box list by clearing it, then adding first the default
	 * value, if available, and then the possible choices.
	 */
	protected void populateComboBox() {
		this.comboBox.removeAll();
		
		this.comboBox.add(this.buildDefaultValueEntry());
		
		for (Iterator<String> stream = this.values(); stream.hasNext(); ) {
			this.comboBox.add(stream.next());
		}
		
		this.updateSelectedItem_();
	}
	
	protected String buildDefaultValueEntry() {
		if (getSubject() == null) {
			return JptUiDetailsMessages.NoneSelected;
		}
		String defaultValue = this.getDefaultValue();
		return (defaultValue == null) ? this.buildNullDefaultValueEntry() : this.buildNonNullDefaultValueEntry(defaultValue);
	}
	
	protected String buildNullDefaultValueEntry() {
		return JptUiDetailsMessages.DefaultEmpty;
	}
	
	protected String buildNonNullDefaultValueEntry(String defaultValue) {
		return NLS.bind(
				JptUiDetailsMessages.DefaultWithOneParam,
				defaultValue);
	}
	
	protected void updateSelectedItem() {
		// make sure the default value is up to date (??? ~bjv)
		String defaultValueEntry = this.buildDefaultValueEntry();
		if ( ! this.comboBox.getItem(0).equals(defaultValueEntry)) {
			this.comboBox.remove(0);
			this.comboBox.add(defaultValueEntry, 0);
		}
		
		this.updateSelectedItem_();
	}
	
	/**
	 * Updates the selected item by selecting the current value, if not
	 * <code>null</code>, or select the default value if one is available,
	 * otherwise remove the selection.
	 */
	protected void updateSelectedItem_() {
		String value = (this.getSubject() == null) ? null : this.getValue();
		if (value == null) {
			// select the default value
			this.comboBox.select(0);
		} else {
			// select the new value
			if ( ! value.equals(this.comboBox.getText())) {
				// This prevents the cursor from being set back to the beginning of the line (bug 234418).
				// The reason we are hitting this method at all is because the
				// context model is updating from the resource model in a way
				// that causes change notifications to be fired (the annotation
				// is added to the resource model, change notification occurs
				// on the update thread, and then the name is set, these 2
				// threads can get in the wrong order).
				// The #valueChanged() method sets the populating flag to true,
				// but in this case it is already set back to false when we
				// receive notification back from the model because it has
				// moved to the update thread and then jumps back on the UI thread.
				this.comboBox.setText(value);
			}
		}
	}
	
	protected void repopulateComboBox() {
		if ( ! this.comboBox.isDisposed()) {
			this.repopulate();
		}
	}
	
	
	// **************** combo-box listener callback ***************************
	
	protected void comboBoxModified() {
		if ( ! this.isPopulating()) {
			this.valueChanged(this.comboBox.getText());
		}
	}
	
	/**
	 * The combo-box selection has changed, update the model if necessary.
	 * If the value has changed and the subject is null, we can build a subject
	 * before setting the value.
	 */
	protected void valueChanged(String newValue) {
		T subject = this.getSubject();
		String oldValue;
		if (subject == null) {
			if (this.nullSubjectIsNotAllowed()) {
				return;  // no subject to set the value on
			}
			oldValue = null;
		} else {
			oldValue = this.getValue();
		}
		
		// convert empty string or default to null
		if (StringTools.stringIsEmpty(newValue) || this.valueIsDefault(newValue)) {
			newValue = null;
		}
		
		// set the new value if it is different from the old value
		if (this.valuesAreDifferent(oldValue, newValue)) {
			this.setPopulating(true);
			
			try {
				this.setValue(newValue);
			} finally {
				this.setPopulating(false);
			}
		}
	}
	
	/**
	 * Return whether we can set the value when the subject is null
	 * (i.e. #setValue(String) will construct the subject if necessary).
	 */
	protected boolean nullSubjectIsAllowed() {
		return false;
	}
	
	protected final boolean nullSubjectIsNotAllowed() {
		return ! this.nullSubjectIsAllowed();
	}
	
	/**
	 * pre-condition: value is not null
	 */
	protected boolean valueIsDefault(String value) {
		return (this.comboBox.getItemCount() > 0)
				&& value.equals(this.comboBox.getItem(0));
	}
	
	protected boolean valuesAreEqual(String value1, String value2) {
		if ((value1 == null) && (value2 == null)) {
			return true;	// both are null
		}
		if ((value1 == null) || (value2 == null)) {
			return false;	// one is null but the other is not
		}
		return value1.equals(value2);
	}
	
	protected boolean valuesAreDifferent(String value1, String value2) {
		return ! this.valuesAreEqual(value1, value2);
	}
}
