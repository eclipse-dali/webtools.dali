/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt;

import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

/**
 * This adapter provides a more object-oriented interface to the items and
 * selected item in a <code>Combo</code>.
 * <p>
 * <b>listHolder</b> contains the items in the <code>Combo</code>.<br>
 * <b>selectedItemHolder</b> contains the items in 'listHolder' that are
 * selected in the <code>Combo</code>.
 *
 * @param <E> The type of the items in <b>listHolder</b>
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class ComboModelAdapter<E> extends AbstractComboModelAdapter<E> {

	// ********** static methods **********

	/**
	 * Adapt the specified model list and selection to the specified combo.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the combo, which calls #toString() on the
	 * items in the model list.
	 */
	public static <T> ComboModelAdapter<T> adapt(
			ListValueModel<T> listHolder,
			WritablePropertyValueModel<T> selectedItemHolder,
			Combo combo)
	{
		return adapt(
			listHolder,
			selectedItemHolder,
			combo,
			StringConverter.Default.<T>instance()
		);
	}

	/**
	 * Adapt the specified model list and selection to the specified combo.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the combo.
	 */
	public static <T> ComboModelAdapter<T> adapt(
			ListValueModel<T> listHolder,
			WritablePropertyValueModel<T> selectedItemHolder,
			Combo combo,
			StringConverter<T> stringConverter)
	{
		return new ComboModelAdapter<T>(
			listHolder,
			selectedItemHolder,
			combo,
			stringConverter
		);
	}


	// ********** constructors **********

	/**
	 * Constructor - the list holder, selections holder, combo, and
	 * string converter are required.
	 */
	protected ComboModelAdapter(
			ListValueModel<E> listHolder,
			WritablePropertyValueModel<E> selectedItemHolder,
			Combo combo,
			StringConverter<E> stringConverter)
	{
		super(listHolder,
		      selectedItemHolder,
		      new SWTComboHolder(combo),
		      stringConverter);
	}


	// ********** Internal member **********

	private static class SWTComboHolder implements ComboHolder {
		private final Combo combo;
		private final boolean editable;
		private String selectedItem;

		SWTComboHolder(Combo combo) {
			super();
			this.combo    = combo;
			this.editable = (combo.getStyle() & SWT.READ_ONLY) == 0;
		}

		public void add(String item, int index) {
			this.combo.add(item, index);

			// It is possible the selected item was set before the combo is being
			// populated, update the selected item if it's matches the item being
			// added
			if ((this.selectedItem != null) && this.selectedItem.equals(item)) {
				this.setText(this.selectedItem);
				this.selectedItem = null;
			}
		}

		public void addDisposeListener(DisposeListener disposeListener) {
			this.combo.addDisposeListener(disposeListener);
		}

		public void addModifyListener(ModifyListener modifyListener) {
			this.combo.addModifyListener(modifyListener);
		}

		public void addSelectionListener(SelectionListener selectionListener) {
			this.combo.addSelectionListener(selectionListener);
		}

		public void deselectAll() {
			this.combo.deselectAll();
		}

		public int getItemCount() {
			return this.combo.getItemCount();
		}

		public String[] getItems() {
			return this.combo.getItems();
		}

		public int getSelectionIndex() {
			return this.combo.getSelectionIndex();
		}

		public String getText() {
			return this.combo.getText();
		}

		public boolean isDisposed() {
			return this.combo.isDisposed();
		}

		public boolean isEditable() {
			return this.editable;
		}

		public boolean isPopulating() {
			return this.combo.getData("populating") == Boolean.TRUE;
		}

		public void remove(int start, int end) {
			this.combo.remove(start, end);
		}

		public void removeAll() {
			this.combo.removeAll();
		}

		public void removeDisposeListener(DisposeListener disposeListener) {
			this.combo.removeDisposeListener(disposeListener);
		}

		public void removeModifyListener(ModifyListener modifyListener) {
			this.combo.removeModifyListener(modifyListener);
		}

		public void removeSelectionListener(SelectionListener selectionListener) {
			this.combo.removeSelectionListener(selectionListener);
		}

		public void setItem(int index, String item) {
			this.combo.setItem(index, item);
		}

		public void setItems(String[] items) {
			this.combo.setItems(items);
		}

		public void setPopulating(boolean populating) {
			this.combo.setData("populating", Boolean.valueOf(populating));
		}

		public void setText(String item) {

			// Keep track of the selected item since it's possible the selected
			// item is before the combo is populated
			if (this.combo.getItemCount() == 0) {
				this.selectedItem = item;
			}
			else {
				this.selectedItem = null;
			}
			this.combo.setText(item);
		}
	}
}
