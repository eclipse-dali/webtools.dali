/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.utility.swt;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.custom.CCombo;

/**
 * Adapt an SWT {@link CCombo} to the list widget expected by
 * {@link ListWidgetModelBinding} and the
 * drop-down list box expected by {@link DropDownListBoxSelectionBinding}.
 */
final class SWTCComboAdapter
	extends AbstractListWidgetAdapter<CCombo>
	implements DropDownListBoxSelectionBinding.DropDownListBox
{
	SWTCComboAdapter(CCombo combo) {
		super(combo);
	}

	// ********** ListWidgetModelBinding.ListWidget implementation **********

	public String[] getItems() {
		return this.widget.getItems();
	}

	/**
	 * {@link CCombo#setItem(int)} does not update the text field if we are
	 * changing the currently selected item. So if we are changing the
	 * currently selected item, we deselect it, set it in the list, and
	 * re-select it so the text field updates.
	 */
	public void setItem(int index, String item) {
		int currentIndex = this.widget.getSelectionIndex();
		if (currentIndex == index) {
			this.widget.deselectAll();
		}
		this.widget.setItem(index, item);
		if (currentIndex == index) {
			this.widget.select(currentIndex);
		}
	}

	/**
	 * {@link CCombo#setItems(String[])} will clear the text field if it is
	 * read-only.
	 */
	public void setItems(String[] items) {
		this.widget.setItems(items);
	}

	/**
	 * The text field will be updated when we synch the selection after an
	 * item is added to the list.
	 */
	public void add(String item, int index) {
		this.widget.add(item, index);
	}

	/**
	 * The text field will be updated when we synch the selection after
	 * items are removed from the list. If the selection was among the removed
	 * items, the model should have already cleared the selection <em>before</em>
	 * the master list was modified.
	 */
	public void remove(int start, int end) {
		this.widget.remove(start, end);
	}

	/**
	 * {@link CCombo#removeAll()} will clear the text field.
	 * The model selection should have already been cleared.
	 */
	public void removeAll() {
		this.widget.removeAll();
	}


	// ********** ComboBoxSelectionBinding.ComboBox implementation **********

	public void addSelectionListener(SelectionListener listener) {
		this.widget.addSelectionListener(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		this.widget.removeSelectionListener(listener);
	}

	public int getSelectionIndex() {
		return this.widget.getSelectionIndex();
	}

	public void select(int index) {
		this.widget.select(index);
	}

	/**
	 * {@link CCombo#deselect(int)} will clear the text field if the index
	 * matches the current selection index.
	 */
	public void deselect(int index) {
		this.widget.deselect(index);
	}

	/**
	 * {@link CCombo#deselectAll()} will clear the text field.
	 * The model selection should have already been cleared.
	 */
	public void deselectAll() {
		this.widget.deselectAll();
	}

}

