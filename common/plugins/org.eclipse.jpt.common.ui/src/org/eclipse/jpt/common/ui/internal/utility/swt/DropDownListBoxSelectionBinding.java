/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.utility.swt;

import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * This binding can be used to keep a drop-down list box's selection
 * synchronized with a model. The selection can be modified by either the
 * drop-down list box or the model, so changes must be coordinated.
 * <p>
 * <strong>NB:</strong> A selected item value of <code>null</code> can be used
 * to clear the drop-down list box's selection. If <code>null</code> is a
 * valid item in the model list, an invalid selected item can be used to clear
 * the selection.
 * 
 * @see ListValueModel
 * @see WritablePropertyValueModel
 * @see DropDownListBox
 * @see SWTTools
 */
@SuppressWarnings("nls")
final class DropDownListBoxSelectionBinding<E>
	implements ListWidgetModelBinding.SelectionBinding
{
	// ***** model
	/**
	 * The underlying list model.
	 */
	private final ListValueModel<E> listModel;

	/**
	 * A writable value model on the underlying model selection.
	 */
	private final WritablePropertyValueModel<E> selectedItemModel;

	/**
	 * A listener that allows us to synchronize the drop-down list box's
	 * selection with the model selection.
	 */
	private final PropertyChangeListener selectedItemChangeListener;

	// ***** UI
	/**
	 * The drop-down list box whose selection we keep synchronized
	 * with the model selection.
	 */
	private final DropDownListBox dropdownListBox;

	/**
	 * A listener that allows us to synchronize our selected item holder
	 * with the drop-down list box's selection.
	 */
	private final SelectionListener dropdownListBoxSelectionListener;


	// ********** constructor **********

	/**
	 * Constructor - all parameters are required.
	 */
	DropDownListBoxSelectionBinding(
			ListValueModel<E> listModel,
			WritablePropertyValueModel<E> selectedItemModel,
			DropDownListBox dropdownListBox
	) {
		super();
		if ((listModel == null) || (selectedItemModel == null) || (dropdownListBox == null)) {
			throw new NullPointerException();
		}
		this.listModel = listModel;
		this.selectedItemModel = selectedItemModel;
		this.dropdownListBox = dropdownListBox;

		this.selectedItemChangeListener = this.buildSelectedItemChangeListener();
		this.selectedItemModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.selectedItemChangeListener);

		this.dropdownListBoxSelectionListener = this.buildDropDownListBoxSelectionListener();
		this.dropdownListBox.addSelectionListener(this.dropdownListBoxSelectionListener);
	}


	// ********** initialization **********

	private PropertyChangeListener buildSelectedItemChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildSelectedItemChangeListener_());
	}

	private PropertyChangeListener buildSelectedItemChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				DropDownListBoxSelectionBinding.this.selectedItemChanged(event);
			}
			@Override
			public String toString() {
				return "selected item listener";
			}
		};
	}

	private SelectionListener buildDropDownListBoxSelectionListener() {
		return new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				DropDownListBoxSelectionBinding.this.dropDownListBoxSelectionChanged(event);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				DropDownListBoxSelectionBinding.this.dropDownListBoxDoubleClicked(event);
			}
			@Override
			public String toString() {
				return "drop-down list box selection listener";
			}
		};
	}


	// ********** ListWidgetModelBinding.SelectionBinding implementation **********

	/**
	 * Modifying the drop-down lisb box's selected item programmatically does
	 * not trigger a SelectionEvent.
	 * <p>
	 * Pre-condition: The drop-down list box is not disposed.
	 */
	public void synchronizeListWidgetSelection() {
		int oldIndex = this.dropdownListBox.getSelectionIndex();
		E value = this.selectedItemModel.getValue();
		int newIndex = this.indexOf(value);
		if ((oldIndex != -1) && (newIndex != -1) && (newIndex != oldIndex)) {
			this.dropdownListBox.deselect(oldIndex);
		}
		if (newIndex == -1) {
			this.dropdownListBox.deselectAll();
		} else {
			if (newIndex != oldIndex) {
				this.dropdownListBox.select(newIndex);
			}
		}
	}

	public void dispose() {
		this.dropdownListBox.removeSelectionListener(this.dropdownListBoxSelectionListener);
		this.selectedItemModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.selectedItemChangeListener);
	}


	// ********** selected item **********

	void selectedItemChanged(PropertyChangeEvent event) {
		if ( ! this.dropdownListBox.isDisposed()) {
			this.selectedItemChanged_(event);
		}
	}

	/**
	 * Modifying the drop-down list box's selected item programmatically does
	 * not trigger a SelectionEvent.
	 */
	private void selectedItemChanged_(@SuppressWarnings("unused") PropertyChangeEvent event) {
		this.synchronizeListWidgetSelection();
	}

	private int indexOf(E item) {
		int len = this.listModel.size();
		for (int i = 0; i < len; i++) {
			if (Tools.valuesAreEqual(this.listModel.get(i), item)) {
				return i;
			}
		}
		// if 'null' is not in the list, use it to clear the selection
		if (item == null) {
			return -1;
		}
		// We can get here via one of the following:
		// 1. The selected item model is invalid and not in sync with the list
		//    model. This is not good and we don't make this (programming
		//    error) obvious (e.g. via an exception). :-(
		// 2. If both the selected item model and the list model are dependent
		//    on the same underlying model, the selected item model may receive
		//    its event first, resulting in a missing item. This will resolve
		//    itself once the list model receives its event and synchronizes
		//    with the same underlying model. This situation is acceptable.
		return -1;

// This is what we used to do:
//		throw new IllegalStateException("selected item not found: " + item);
	}


	// ********** combo-box events **********

	void dropDownListBoxSelectionChanged(SelectionEvent event) {
		if ( ! this.dropdownListBox.isDisposed()) {
			this.dropDownListBoxSelectionChanged_(event);
		}
	}

	void dropDownListBoxDoubleClicked(SelectionEvent event) {
		if ( ! this.dropdownListBox.isDisposed()) {
			this.dropDownListBoxSelectionChanged_(event);
		}
	}

	private void dropDownListBoxSelectionChanged_(@SuppressWarnings("unused") SelectionEvent event) {
		this.selectedItemModel.setValue(this.getDropDownListBoxSelectedItem());
	}

	private E getDropDownListBoxSelectedItem() {
		int selectionIndex = this.dropdownListBox.getSelectionIndex();
		return (selectionIndex == -1) ? null : this.listModel.get(selectionIndex);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.selectedItemModel);
	}


	// ********** adapter interface **********

	/**
	 * Adapter used by the drop-down list box selection binding to query and manipulate
	 * the drop-down list box.
	 */
	interface DropDownListBox {

		/**
		 * Return whether the combo-box is "disposed".
		 */
		boolean isDisposed();

		/**
		 * Add the specified selection listener to the combo-box.
		 */
		void addSelectionListener(SelectionListener listener);

		/**
		 * Remove the specified selection listener from the combo-box.
		 */
		void removeSelectionListener(SelectionListener listener);

		/**
		 * Return the index of the combo-box's selection.
		 */
		int getSelectionIndex();

		/**
		 * Select the item at the specified index in the combo-box.
		 */
		void select(int index);

		/**
		 * Deselect the item at the specified index in the combo-box.
		 */
		void deselect(int index);

		/**
		 * Clear the combo-box's selection.
		 */
		void deselectAll();

	}

}
