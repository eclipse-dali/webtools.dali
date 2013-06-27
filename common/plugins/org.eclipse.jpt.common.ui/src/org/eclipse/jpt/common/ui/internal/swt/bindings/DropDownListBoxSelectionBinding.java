/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.swt.events.SelectionEvent;

/**
 * This binding can be used to keep a drop-down list box's selection
 * synchronized with a model.
 * <p>
 * <strong>NB:</strong> This binding is bi-directional.
 * <p>
 * <strong>NB2:</strong> A selected item value of <code>null</code> can be used
 * to clear the drop-down list box's selection. If <code>null</code> is a
 * valid item in the model list, an invalid selected item can be used to clear
 * the selection.
 * <p>
 * <strong>NB3:</strong> See the class comment for
 * {@link ListBoxSelectionBinding}.
 * 
 * @see ModifiablePropertyValueModel
 * @see DropDownListBox
 * @see SWTBindingTools
 */
final class DropDownListBoxSelectionBinding<E>
	extends AbstractComboSelectionBinding<E, DropDownListBoxSelectionBinding.DropDownListBox>
{
	// ***** model
	/**
	 * Cache of the model selection.
	 */
	private E selectedItem;


	/**
	 * Constructor - all parameters are required.
	 */
	DropDownListBoxSelectionBinding(
			ArrayList<E> list,
			ModifiablePropertyValueModel<E> selectedItemModel,
			DropDownListBox dropdownListBox
	) {
		super(list, selectedItemModel, dropdownListBox);
		this.selectedItem = this.valueModel.getValue();
	}

	/**
	 * <strong>NB:</strong> The elements in the selection model may be out of
	 * sync with the underlying list model. (See the class comment.)
	 * <p>
	 * Modifying the drop-down list box's selected item programmatically does
	 * not trigger a {@link SelectionEvent}.
	 * <p>
	 * Pre-condition: The drop-down list box is not disposed.
	 */
	public void listChanged() {
		this.setComboSelection();
	}

	@Override
	void valueChanged_(E item) {
		this.selectedItem = item;
		this.setComboSelection();
	}

	private void setComboSelection() {
		int oldIndex = this.combo.getSelectionIndex();
		int newIndex = this.indexOf(this.selectedItem);
		if (newIndex == -1) {
			if (oldIndex != -1) {
				this.combo.deselectAll();
			}
		} else {
			if (newIndex != oldIndex) {
				if (oldIndex != -1) {
					this.combo.deselect(oldIndex);
				}
				this.combo.select(newIndex);
			}
		}
	}

	/**
	 * <strong>NB:</strong> an index of <code>-1</code> is ignored by
	 * {@link org.eclipse.swt.widgets.Combo} (lucky for us).
	 */
	private int indexOf(E item) {
		int i = 0;
		for (E each : this.list) {
			if (ObjectTools.equals(each, item)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public void dispose() {
		super.dispose();
		this.selectedItem = null;
	}


	// ********** adapter interface **********

	/**
	 * Adapter used by the drop-down list box selection binding to query and manipulate
	 * the drop-down list box.
	 */
	interface DropDownListBox
		extends AbstractComboSelectionBinding.ComboAdapter
	{
		/**
		 * Select the item at the specified index in the drop-down list box.
		 */
		void select(int index);

		/**
		 * Deselect the item at the specified index in the drop-down list box.
		 */
		void deselect(int index);

		/**
		 * Clear the drop-down list box's selection.
		 */
		void deselectAll();
	}
}
