/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt;

import java.util.EventListener;
import java.util.EventObject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.common.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.StringConverter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * This adapter provides a more object-oriented interface to the items and
 * selected item in a combo.
 * <p>
 * <b>listHolder</b> contains the items in the combo.<br>
 * <b>selectedItemHolder</b> contains the items in 'listHolder' that are
 * selected in the combo.
 *
 * @param <E> The type of the items in <b>listHolder</b>
 * @see ComboModelAdapter
 * @see CComboModelAdapter
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class AbstractComboModelAdapter<E> {

	// ********** model **********
	/**
	 * A value model on the underlying model list.
	 */
	protected final ListValueModel<E> listHolder;

	/**
	 * A listener that allows us to synchronize the combo's contents with
	 * the model list.
	 */
	protected final ListChangeListener listChangeListener;

	/**
	 * A value model on the underlying model selection.
	 */
	protected final WritablePropertyValueModel<E> selectedItemHolder;

	/**
	 * A listener that allows us to synchronize the combo's selection with the
	 * model selection.
	 */
	protected final PropertyChangeListener selectedItemChangeListener;

	/**
	 * A converter that converts items in the model list
	 * to strings that can be put in the combo.
	 */
	protected StringConverter<E> stringConverter;

	// ********** UI **********
	/**
	 * The combo we keep synchronized with the model list.
	 */
	protected final ComboHolder comboHolder;

	/**
	 * A listener that allows us to synchronize our selection list holder
	 * with the combo's text.
	 */
	protected ModifyListener comboModifyListener;

	/**
	 * A listener that allows us to synchronize our selection list holder
	 * with the combo's selection.
	 */
	protected SelectionListener comboSelectionListener;

	/**
	 * Clients that are interested in selection change events.
	 */
	@SuppressWarnings("unchecked")
	protected final ListenerList<SelectionChangeListener> selectionChangeListenerList;

	/**
	 * Clients that are interested in double click events.
	 */
	@SuppressWarnings("unchecked")
	protected final ListenerList<DoubleClickListener> doubleClickListenerList;

	/**
	 * A listener that allows us to stop listening to stuff when the combo
	 * is disposed.
	 */
	protected final DisposeListener comboDisposeListener;


	// ********** constructors **********

	/**
	 * Constructor - the list holder, selections holder, combo, and
	 * string converter are required.
	 */
	protected AbstractComboModelAdapter(
			ListValueModel<E> listHolder,
			WritablePropertyValueModel<E> selectedItemHolder,
			ComboHolder comboHolder,
			StringConverter<E> stringConverter)
	{
		super();

		Assert.isNotNull(listHolder,         "The holder of the items");
		Assert.isNotNull(selectedItemHolder, "The holder of the selected item cannot be null");
		Assert.isNotNull(comboHolder,        "The holder of the combo widget cannot be null");
		Assert.isNotNull(stringConverter,    "The string converter cannot be null");

		this.listHolder         = listHolder;
		this.selectedItemHolder = selectedItemHolder;
		this.comboHolder        = comboHolder;
		this.stringConverter    = stringConverter;

		this.listChangeListener = this.buildListChangeListener();
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);

		this.selectedItemChangeListener = this.buildSelectedItemChangeListener();
		this.selectedItemHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.selectedItemChangeListener);

		if (this.comboHolder.isEditable()) {
			this.comboModifyListener = this.buildComboModifyListener();
			this.comboHolder.addModifyListener(this.comboModifyListener);
		}
		else {
			this.comboSelectionListener = this.buildComboSelectionListener();
			this.comboHolder.addSelectionListener(this.comboSelectionListener);
		}

		this.selectionChangeListenerList = this.buildSelectionChangeListenerList();
		this.doubleClickListenerList = this.buildDoubleClickListenerList();

		this.comboDisposeListener = this.buildComboDisposeListener();
		this.comboHolder.addDisposeListener(this.comboDisposeListener);

		this.synchronizeCombo();
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		return new SWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	protected ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				AbstractComboModelAdapter.this.listItemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				AbstractComboModelAdapter.this.listItemsRemoved(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				AbstractComboModelAdapter.this.listItemsMoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				AbstractComboModelAdapter.this.listItemsReplaced(event);
			}
			public void listCleared(ListClearEvent event) {
				AbstractComboModelAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				AbstractComboModelAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list listener";
			}
		};
	}

	protected PropertyChangeListener buildSelectedItemChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildSelectedItemChangeListener_());
	}

	protected PropertyChangeListener buildSelectedItemChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				AbstractComboModelAdapter.this.selectedItemChanged(e);
			}
		};
	}

	protected ModifyListener buildComboModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				AbstractComboModelAdapter.this.comboSelectionChanged(event);
			}

			@Override
			public String toString() {
				return "combo modify listener";
			}
		};
	}

	protected SelectionListener buildComboSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				AbstractComboModelAdapter.this.comboSelectionChanged(event);
			}

			@Override
			public String toString() {
				return "combo modify listener";
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected ListenerList<DoubleClickListener> buildDoubleClickListenerList() {
		return new ListenerList<DoubleClickListener>(DoubleClickListener.class);
	}

	@SuppressWarnings("unchecked")
	protected ListenerList<SelectionChangeListener> buildSelectionChangeListenerList() {
		return new ListenerList<SelectionChangeListener>(SelectionChangeListener.class);
	}

	protected DisposeListener buildComboDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				AbstractComboModelAdapter.this.comboDisposed(event);
			}

			@Override
			public String toString() {
				return "combo dispose listener";
			}
		};
	}

	protected void synchronizeCombo() {
		this.synchronizeComboItems();
		this.synchronizeComboSelection();
	}


	// ********** string converter **********

	public void setStringConverter(StringConverter<E> stringConverter) {
		Assert.isNotNull(stringConverter, "The StringConverter cannot be null");
		this.stringConverter = stringConverter;
		this.synchronizeCombo();
	}


	// ********** list **********

	/**
	 * Use the string converter to convert the specified item to a
	 * string that can be added to the combo.
	 */
	protected String convert(E item) {
		return this.stringConverter.convertToString(item);
	}

	/**
	 * Brute force synchronization of combo with the model list.
	 */
	protected void synchronizeComboItems() {
		if (this.comboHolder.isDisposed()) {
			return;
		}
		int len = this.listHolder.size();
		String[] items = new String[len];
		for (int index = 0; index < len; index++) {
			items[index] = this.convert(this.listHolder.get(index));
		}
		try {
			this.comboHolder.setPopulating(true);
			this.comboHolder.setItems(items);
		}
		finally {
			this.comboHolder.setPopulating(false);
		}
	}

	/**
	 * The model has changed - synchronize the combo.
	 */
	protected void listItemsAdded(ListAddEvent event) {
		if (this.comboHolder.isDisposed()) {
			return;
		}

		int count = this.comboHolder.getItemCount();
		int index = event.getIndex();

		for (E item : this.getItems(event)) {
			this.comboHolder.add(this.convert(item), index++);
		}

		// When the combo is populated, it's possible the selection was already
		// set but no items was found, resync the selected item
		synchronizeComboSelection();
	}

	/**
	 * The model has changed - synchronize the combo.
	 */
	protected void listItemsRemoved(ListRemoveEvent event) {
		if (this.comboHolder.isDisposed()) {
			return;
		}
		this.comboHolder.remove(event.getIndex(), event.getIndex() + event.getItemsSize() - 1);
		this.synchronizeComboSelection();
	}

	/**
	 * The model has changed - synchronize the combo.
	 */
	protected void listItemsMoved(ListMoveEvent event) {
		if (this.comboHolder.isDisposed()) {
			return;
		}
		int target = event.getTargetIndex();
		int source = event.getSourceIndex();
		int len = event.getLength();
		int loStart = Math.min(target, source);
		int hiStart = Math.max(target, source);
		// make a copy of the affected items...
		String[] subArray = ArrayTools.subArray(this.comboHolder.getItems(), loStart, hiStart + len - loStart);
		// ...move them around...
		subArray = ArrayTools.move(subArray, target - loStart, source - loStart, len);
		// ...and then put them back
		for (int index = 0; index < subArray.length; index++) {
			this.comboHolder.setItem(loStart + index, subArray[index]);
		}
	}

	/**
	 * The model has changed - synchronize the combo.
	 */
	protected void listItemsReplaced(ListReplaceEvent event) {
		if (this.comboHolder.isDisposed()) {
			return;
		}
		int index = event.getIndex();
		int selectionIndex = this.comboHolder.getSelectionIndex();
		//fixing bug 269100 by setting the populating flag to true
		this.comboHolder.setPopulating(true);
		try {
			for (E item : this.getNewItems(event)) {
				this.comboHolder.setItem(index++, this.convert(item));
			}
			if (selectionIndex == 0) {
				this.comboHolder.setText(this.comboHolder.getItems()[0]);
			}
		}
		finally {
			this.comboHolder.setPopulating(false);
		}
	}

	/**
	 * The model has changed - synchronize the combo.
	 */
	protected void listCleared(ListClearEvent event) {
		if (this.comboHolder.isDisposed()) {
			return;
		}
		this.comboHolder.setPopulating(true);
		try {
			this.comboHolder.removeAll();
		}
		finally {
			this.comboHolder.setPopulating(false);
		}
	}

	/**
	 * The model has changed - synchronize the combo.
	 */
	protected void listChanged(ListChangeEvent event) {
		this.synchronizeCombo();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(ListAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getNewItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getNewItems();
	}


	// ********** selected items **********

	protected int indexOf(E item) {
		int length = this.listHolder.size();
		for (int index = 0; index < length; index++) {
			if (valuesAreEqual(this.listHolder.get(index), item)) {
				return index;
			}
		}
		return -1;
	}

	protected void synchronizeComboSelection() {
		if (this.comboHolder.isDisposed() || this.comboHolder.isPopulating()) {
			return;
		}

		E selectedValue = this.selectedItemHolder.getValue();
		if (this.comboHolder.getText().equals(selectedValue)) {
			//if the selection is still the same, don't reset it
			return;
		}
		this.comboHolder.setPopulating(true);
		try {
			this.comboHolder.deselectAll();
			String selectedItem = this.convert(selectedValue);
			if (selectedItem == null) {
				selectedItem = "";
			}
			this.comboHolder.setText(selectedItem);
			this.notifyListeners(selectedValue);
		}
		finally {
			this.comboHolder.setPopulating(false);
		}
	}

	protected void selectedItemChanged(PropertyChangeEvent event) {
		this.synchronizeComboSelection();
	}

	/**
	 * Return whether the values are equal, with the appropriate null checks.
	 * Convenience method for checking whether an attribute value has changed.
	 */
	protected final boolean valuesAreEqual(Object value1, Object value2) {
		if ((value1 == null) && (value2 == null)) {
			return true;	// both are null
		}
		if ((value1 == null) || (value2 == null)) {
			return false;	// one is null but the other is not
		}
		return value1.equals(value2);
	}

	// ********** combo events **********

	protected void comboSelectionChanged(SelectionEvent event) {
		this.selectionChanged();
	}

	protected void comboSelectionChanged(ModifyEvent event) {
		this.selectionChanged();
	}

	protected void selectionChanged() {
		if (!this.comboHolder.isPopulating()) {
			E selectedItem = this.selectedItem();
			this.comboHolder.setPopulating(true);
			try {
				this.selectedItemHolder.setValue(selectedItem);
				this.notifyListeners(selectedItem);
			}
			finally {
				this.comboHolder.setPopulating(false);
			}
		}
	}

	private void notifyListeners(E selectedItem) {
		if (this.selectionChangeListenerList.size() > 0) {
			SelectionChangeEvent<E> scEvent = new SelectionChangeEvent<E>(this, selectedItem);
			for (SelectionChangeListener<E> selectionChangeListener : this.selectionChangeListenerList.getListeners()) {
				selectionChangeListener.selectionChanged(scEvent);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected E selectedItem() {
		if (this.comboHolder.isDisposed()) {
			return null;
		}

		if (this.comboHolder.isEditable()) {
			String text = this.comboHolder.getText();

			if (text.length() == 0) {
				return null;
			}

			for (int index = this.listHolder.size(); --index >= 0; ) {
				E item = this.listHolder.get(index);
				String value = this.convert(item);
				if (valuesAreEqual(text, value)) {
					return item;
				}
			}

			// TODO: Find a way to prevent this type cast (it'll work if E is
			// String but it won't work if E is something else), maybe use a
			// BidiStringConverter instead of StringConverter
			try {
				return (E) text;
			}
			catch (ClassCastException e) {
				return null;
			}
		}

		int index = this.comboHolder.getSelectionIndex();

		if (index == -1) {
			return null;
		}

		return this.listHolder.get(index);
	}

	protected void comboDoubleClicked(SelectionEvent event) {
		if (this.comboHolder.isDisposed()) {
			return;
		}
		if (this.doubleClickListenerList.size() > 0) {
			// there should be only a single item selected during a double-click(?)
			E selection = this.listHolder.get(this.comboHolder.getSelectionIndex());
			DoubleClickEvent<E> dcEvent = new DoubleClickEvent<E>(this, selection);
			for (DoubleClickListener<E> doubleClickListener : this.doubleClickListenerList.getListeners()) {
				doubleClickListener.doubleClick(dcEvent);
			}
		}
	}


	// ********** dispose **********

	protected void comboDisposed(DisposeEvent event) {
		// the combo is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.comboHolder.removeDisposeListener(this.comboDisposeListener);
		if (this.comboHolder.isEditable()) {
			this.comboHolder.removeModifyListener(this.comboModifyListener);
		}
		else {
			this.comboHolder.removeSelectionListener(this.comboSelectionListener);
		}
		this.selectedItemHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.selectedItemChangeListener);
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listHolder);
	}


	// ********** double click support **********

	public void addDoubleClickListener(DoubleClickListener<E> listener) {
		this.doubleClickListenerList.add(listener);
	}

	public void removeDoubleClickListener(DoubleClickListener<E> listener) {
		this.doubleClickListenerList.remove(listener);
	}

	public interface DoubleClickListener<E> extends EventListener {
		void doubleClick(DoubleClickEvent<E> event);
	}

	public static class DoubleClickEvent<E> extends EventObject {
		private final E selection;
		private static final long serialVersionUID = 1L;

		protected DoubleClickEvent(AbstractComboModelAdapter<E> source, E selection) {
			super(source);
			if (selection == null) {
				throw new NullPointerException();
			}
			this.selection = selection;
		}

		@Override
		@SuppressWarnings("unchecked")
		public AbstractComboModelAdapter<E> getSource() {
			return (AbstractComboModelAdapter<E>) super.getSource();
		}

		public E selection() {
			return this.selection;
		}
	}


	// ********** selection support **********

	public void addSelectionChangeListener(SelectionChangeListener<E> listener) {
		this.selectionChangeListenerList.add(listener);
	}

	public void removeSelectionChangeListener(SelectionChangeListener<E> listener) {
		this.selectionChangeListenerList.remove(listener);
	}

	public interface SelectionChangeListener<E> extends EventListener {
		void selectionChanged(SelectionChangeEvent<E> event);
	}

	public static class SelectionChangeEvent<E> extends EventObject {
		private final E selectedItem;
		private static final long serialVersionUID = 1L;

		protected SelectionChangeEvent(AbstractComboModelAdapter<E> source, E selectedItem) {
			super(source);
			this.selectedItem = selectedItem;
		}

		@Override
		@SuppressWarnings("unchecked")
		public AbstractComboModelAdapter<E> getSource() {
			return (AbstractComboModelAdapter<E>) super.getSource();
		}

		public E selectedItem() {
			return this.selectedItem;
		}
	}

	// ********** Internal member **********

	/**
	 * This holder is required for supporting <code>Combo</code> and
	 * <code>CCombo</code> transparently.
	 */
	protected static interface ComboHolder {
		void add(String item, int index);
		void addDisposeListener(DisposeListener disposeListener);
		void addModifyListener(ModifyListener modifyListener);
		void addSelectionListener(SelectionListener selectionListener);
		void deselectAll();
		int getItemCount();
		String[] getItems();
		int getSelectionIndex();
		String getText();
		boolean isDisposed();
		boolean isEditable();
		boolean isPopulating();
		void removeDisposeListener(DisposeListener disposeListener);
		void removeModifyListener(ModifyListener modifyListener);
		void removeSelectionListener(SelectionListener selectionListener);
		void setItem(int index, String item);
		void setItems(String[] items);
		void setPopulating(boolean populating);
		void setText(String item);
		void remove(int start, int end);
		void removeAll();
	}
}
