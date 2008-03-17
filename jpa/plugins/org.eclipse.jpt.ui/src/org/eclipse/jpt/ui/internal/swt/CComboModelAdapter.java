/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.swt;

import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;

/**
 * This adapter provides a more object-oriented interface to the items and
 * selected item in a <code>CCombo</code>.
 * <p>
 * <b>listHolder</b> contains the items in the <code>CCombo</code>.<br>
 * <b>selectedItemHolder</b> contains the items in 'listHolder' that are
 * selected in the <code>CCombo</code>.
 *
 * @param <E> The type of the items in <b>listHolder</b>
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class CComboModelAdapter<E> extends AbstractComboModelAdapter<E> {

	// ********** static methods **********

	/**
	 * Adapt the specified model list and selection to the specified combo.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the combo, which calls #toString() on the
	 * items in the model list.
	 */
	public static <T> CComboModelAdapter<T> adapt(
			ListValueModel<T> listHolder,
			WritablePropertyValueModel<T> selectedItemHolder,
			CCombo combo)
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
	public static <T> CComboModelAdapter<T> adapt(
			ListValueModel<T> listHolder,
			WritablePropertyValueModel<T> selectedItemHolder,
			CCombo combo,
			StringConverter<T> stringConverter)
	{
		return new CComboModelAdapter<T>(
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
	protected CComboModelAdapter(
			ListValueModel<E> listHolder,
			WritablePropertyValueModel<E> selectedItemHolder,
			CCombo combo,
			StringConverter<E> stringConverter)
	{
		super(listHolder,
		      selectedItemHolder,
		      new CComboHolder(combo),
		      stringConverter);
	}


	// ********** Internal member **********

	private static class CComboHolder implements ComboHolder {
		private final CCombo combo;
		private final boolean editable;

		CComboHolder(CCombo combo) {
			super();
			this.combo    = combo;
			this.editable = combo.getEditable();
		}

		public void add(String item, int index) {
			this.combo.add(item, index);
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
			return editable;
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
			this.combo.setText(item);
		}
	}
}
