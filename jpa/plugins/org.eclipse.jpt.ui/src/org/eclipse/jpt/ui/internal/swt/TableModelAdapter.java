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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jpt.ui.internal.listeners.SWTCollectionChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * This adapter provides a more object-oriented interface to the items and
 * selected items in a table.
 * 'listHolder' contains the data of a single column in the column.
 * 'selectedItemsHolder' contains the data of a single column in 'listHolder'
 * that are selected in the table.
 */
@SuppressWarnings("nls")
public class TableModelAdapter<E> {

	// ********** model **********
	/**
	 * A value model on the underlying model list.
	 */
	protected final ListValueModel<E> listHolder;

	/**
	 * A listener that allows us to synchronize the table's contents with
	 * the model list.
	 */
	protected final ListChangeListener listChangeListener;

	/**
	 * A value model on the underlying model selections.
	 */
	protected final CollectionValueModel<E> selectedItemsHolder;

	/**
	 * A listener that allows us to synchronize the table's selection with
	 * the model selections.
	 */
	protected final CollectionChangeListener selectedItemsChangeListener;

	/**
	 * The table we keep synchronized with the model list.
	 */
	protected final Table table;

	/**
	 * A listener that allows us to synchronize our selection list holder
	 * with the table's selection.
	 */
	protected final SelectionListener tableSelectionListener;

	/**
	 * Clients that are interested in selection change events.
	 */
	protected SelectionChangeListener<E>[] selectionChangeListeners;

	/**
	 * Clients that are interested in double click events.
	 */
	protected DoubleClickListener<E>[] doubleClickListeners;

	/**
	 * A listener that allows us to stop listening to stuff when the table
	 * is disposed.
	 */
	protected final DisposeListener tableDisposeListener;

	/**
	 * This label provider is responsible to convert a property at a column index
	 * to a string value.
	 */
	protected final ITableLabelProvider labelProvider;

	/**
	 * The column adapter is responsible to return the count of columns and to
	 * create the value holders for all the properties.
	 */
	private ColumnAdapter<E> columnAdapter;

	/**
	 * Keeps track of the <code>TableItemModelAdapter</code>s that were created
	 * for each item of the list holder.
	 */
	private List<TableItemModelAdapter> tableItemModelAdapters;


	// ********** static methods **********

	/**
	 * Adapt the specified model list and selection to the specified table.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the table.
	 */
	public static <T> TableModelAdapter<T> adapt(
			ListValueModel<T> listHolder,
			PropertyValueModel<T> selectedItemHolder,
			Table table,
			ColumnAdapter<T> columnAdapter,
			ITableLabelProvider labelProvider)
	{
		return new TableModelAdapter<T>(
			listHolder,
			new PropertyCollectionValueModelAdapter<T>(selectedItemHolder),
			table,
			columnAdapter,
			labelProvider
		);
	}


	// ********** constructors **********

	/**
	 * Constructor - the list holder, selections holder, table, and
	 * string converter are required.
	 */
	protected TableModelAdapter(
			ListValueModel<E> listHolder,
			CollectionValueModel<E> selectedItemsHolder,
			Table table,
			ColumnAdapter<E> columnAdapter,
			ITableLabelProvider labelProvider)
	{
		super();
		if ((listHolder == null) || (selectedItemsHolder == null) || (table == null) || (labelProvider == null)) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.selectedItemsHolder = selectedItemsHolder;
		this.table = table;
		this.columnAdapter = columnAdapter;
		this.labelProvider = labelProvider;
		this.tableItemModelAdapters = new ArrayList<TableItemModelAdapter>(columnAdapter.columnCount());

		this.listChangeListener = this.buildListChangeListener();
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);

		this.selectedItemsChangeListener = this.buildSelectedItemsChangeListener();
		this.selectedItemsHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.selectedItemsChangeListener);

		this.tableSelectionListener = this.buildTableSelectionListener();
		this.table.addSelectionListener(this.tableSelectionListener);

		this.selectionChangeListeners = this.buildSelectionChangeListeners();
		this.doubleClickListeners = this.buildDoubleClickListeners();

		this.tableDisposeListener = this.buildTableDisposeListener();
		this.table.addDisposeListener(this.tableDisposeListener);

		this.synchronizeTable();
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		return new SWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	protected ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				TableModelAdapter.this.listItemsAdded(event);
			}
			public void itemsRemoved(ListChangeEvent event) {
				TableModelAdapter.this.listItemsRemoved(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				TableModelAdapter.this.listItemsMoved(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				TableModelAdapter.this.listItemsReplaced(event);
			}
			public void listCleared(ListChangeEvent event) {
				TableModelAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				TableModelAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list listener";
			}
		};
	}

	protected CollectionChangeListener buildSelectedItemsChangeListener() {
		return new SWTCollectionChangeListenerWrapper(this.buildSelectedItemsChangeListener_());
	}

	protected CollectionChangeListener buildSelectedItemsChangeListener_() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				TableModelAdapter.this.selectedItemsAdded(event);
			}
			public void itemsRemoved(CollectionChangeEvent event) {
				TableModelAdapter.this.selectedItemsRemoved(event);
			}
			public void collectionCleared(CollectionChangeEvent event) {
				TableModelAdapter.this.selectedItemsCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				TableModelAdapter.this.selectedItemsChanged(event);
			}
			@Override
			public String toString() {
				return "selected items listener";
			}
		};
	}

	protected SelectionListener buildTableSelectionListener() {
		return new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				TableModelAdapter.this.tableSelectionChanged(event);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				TableModelAdapter.this.tableDoubleClicked(event);
			}
			@Override
			public String toString() {
				return "table selection listener";
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected DoubleClickListener<E>[] buildDoubleClickListeners() {
		return new DoubleClickListener[0];
	}

	@SuppressWarnings("unchecked")
	protected SelectionChangeListener<E>[] buildSelectionChangeListeners() {
		return new SelectionChangeListener[0];
	}

	protected DisposeListener buildTableDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				TableModelAdapter.this.tableDisposed(event);
			}
			@Override
			public String toString() {
				return "table dispose listener";
			}
		};
	}

	protected void synchronizeTable() {
		this.synchronizeTableColumns();
		this.synchronizeTableItems();
		this.synchronizeTableSelection();
	}


	// ********** list **********

	/**
	 * Creates the table colums.
	 */
	protected void synchronizeTableColumns() {
		if (this.table.isDisposed()) {
			return;
		}

		int columnCount = this.columnAdapter.columnCount();

		for (int index = 0; index < columnCount; index++) {
			TableColumn tableColumn = new TableColumn(this.table, SWT.NULL, index);
			tableColumn.setMoveable(false);
			tableColumn.setResizable(true);
			tableColumn.setText(this.columnAdapter.columnName(index));
			tableColumn.setWidth(100);
		}
	}

	/**
	 * Brute force synchronization of table with the model list.
	 */
	protected void synchronizeTableItems() {
		if (this.table.isDisposed()) {
			return;
		}

		for (int index = table.getItemCount(); --index >= 0; ) {
			this.table.remove(index);
			this.tableItemModelAdapters.remove(index);
		}

		int itemCount = this.listHolder.size();

		for (int index = 0; index < itemCount; index++) {
			TableItem tableItem = new TableItem(this.table, SWT.NULL, index);
			tableItem.setData(this.listHolder.get(index));

			TableItemModelAdapter adapter =
				TableItemModelAdapter.adapt(tableItem, columnAdapter, labelProvider);

			tableItemModelAdapters.add(adapter);
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsAdded(ListChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		int i = event.getIndex();
		for (ListIterator<E> stream = this.items(event); stream.hasNext(); i++) {
			TableItem tableItem = new TableItem(this.table, SWT.NULL, i);
			tableItem.setData(stream.next());

			TableItemModelAdapter adapter =
				TableItemModelAdapter.adapt(tableItem, columnAdapter, labelProvider);

			tableItemModelAdapters.add(i, adapter);
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsRemoved(ListChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		this.table.remove(event.getIndex(), event.getIndex() + event.itemsSize() - 1);

		for (int index = event.getIndex() + event.itemsSize(); --index >= event.getIndex(); ) {
			tableItemModelAdapters.remove(index);
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsMoved(ListChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		int target = event.getTargetIndex();
		int source = event.getSourceIndex();
		int len = event.getMoveLength();
		int loStart = Math.min(target, source);
		int hiStart = Math.max(target, source);
		// make a copy of the affected items...
		TableItem[] subArray = CollectionTools.subArray(this.table.getItems(), loStart, hiStart + len - loStart);
		// ...move them around...
		subArray = CollectionTools.move(subArray, target - loStart, source - loStart, len);
		// ...and then put them back
		for (int i = 0; i < subArray.length; i++) {
			// TODO
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsReplaced(ListChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}

		int rowIndex = event.getIndex();

		for (ListIterator<E> stream = this.items(event); stream.hasNext(); ) {
			TableItem tableItem = this.table.getItem(rowIndex);
			tableItem.setData(stream.next());

			TableItemModelAdapter adapter = tableItemModelAdapters.get(rowIndex);

			for (int columnIndex = this.columnAdapter.columnCount(); --columnIndex >= 0; ) {
				adapter.tableItemChanged(columnIndex, tableItem.getData(), true);
			}

			rowIndex++;
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listCleared(ListChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		this.table.removeAll();
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listChanged(ListChangeEvent event) {
		this.synchronizeTableItems();
	}

	// minimized unchecked code
	@SuppressWarnings("unchecked")
	protected ListIterator<E> items(ListChangeEvent event) {
		return ((ListIterator<E>) event.items());
	}


	// ********** selected items **********

	protected int indexOf(E item) {
		int len = this.listHolder.size();
		for (int i = 0; i < len; i++) {
			if (this.listHolder.get(i) == item) {
				return i;
			}
		}
		return -1;
	}

	protected void synchronizeTableSelection() {
		if (this.table.isDisposed()) {
			return;
		}
		int[] indices = new int[this.selectedItemsHolder.size()];
		int i = 0;
		for (Iterator<E> stream = this.selectedItemsHolder.iterator(); stream.hasNext(); ) {
			indices[i++] = this.indexOf(stream.next());
		}
		this.table.deselectAll();
		this.table.select(indices);
	}

	protected void selectedItemsAdded(CollectionChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		int[] indices = new int[event.itemsSize()];
		int i = 0;
		for (Iterator<E> stream = this.items(event); stream.hasNext(); ) {
			indices[i++] = this.indexOf(stream.next());
		}
		this.table.select(indices);
	}

	protected void selectedItemsRemoved(CollectionChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		int[] indices = new int[event.itemsSize()];
		int i = 0;
		for (Iterator<E> stream = this.items(event); stream.hasNext(); ) {
			indices[i++] = this.indexOf(stream.next());
		}
		this.table.deselect(indices);
	}

	protected void selectedItemsCleared(CollectionChangeEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		this.table.deselectAll();
	}

	protected void selectedItemsChanged(CollectionChangeEvent event) {
		this.synchronizeTableSelection();
	}

	// minimized unchecked code
	@SuppressWarnings("unchecked")
	protected Iterator<E> items(CollectionChangeEvent event) {
		return ((Iterator<E>) event.items());
	}


	// ********** list box events **********

	protected void tableSelectionChanged(SelectionEvent event) {
		if (this.selectionChangeListeners.length > 0) {
			@SuppressWarnings("unchecked")
			SelectionChangeEvent<E> scEvent = new SelectionChangeEvent(this, this.selectedItems());
			for (SelectionChangeListener<E> selectionChangeListener : this.selectionChangeListeners) {
				selectionChangeListener.selectionChanged(scEvent);
			}
		}
	}

	protected Collection<E> selectedItems() {
		if (this.table.isDisposed()) {
			return Collections.emptySet();
		}
		@SuppressWarnings("unchecked")
		ArrayList<E> selectedItems = new ArrayList(this.table.getSelectionCount());
		for (int selectionIndex : this.table.getSelectionIndices()) {
			selectedItems.add(this.listHolder.get(selectionIndex));
		}
		return selectedItems;
	}

	protected void tableDoubleClicked(SelectionEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		if (this.doubleClickListeners.length > 0) {
			// there should be only a single item selected during a double-click(?)
			E selection = this.listHolder.get(this.table.getSelectionIndex());
			@SuppressWarnings("unchecked")
			DoubleClickEvent<E> dcEvent = new DoubleClickEvent(this, selection);
			for (DoubleClickListener<E> doubleClickListener : this.doubleClickListeners) {
				doubleClickListener.doubleClick(dcEvent);
			}
		}
	}


	// ********** dispose **********

	protected void tableDisposed(DisposeEvent event) {
		// the table is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.table.removeDisposeListener(this.tableDisposeListener);
		this.table.removeSelectionListener(this.tableSelectionListener);
		this.selectedItemsHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.selectedItemsChangeListener);
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listHolder);
	}


	// ********** double click support **********

	public void addDoubleClickListener(DoubleClickListener<E> listener) {
		this.doubleClickListeners = CollectionTools.add(this.doubleClickListeners, listener);
	}

	public void removeDoubleClickListener(DoubleClickListener<E> listener) {
		this.doubleClickListeners = CollectionTools.remove(this.doubleClickListeners, listener);
	}

	public interface DoubleClickListener<E> {
		void doubleClick(DoubleClickEvent<E> event);
	}

	public static class DoubleClickEvent<E> extends EventObject {
		private final E selection;
		private static final long serialVersionUID = 1L;

		protected DoubleClickEvent(TableModelAdapter<E> source, E selection) {
			super(source);
			if (selection == null) {
				throw new NullPointerException();
			}
			this.selection = selection;
		}

		@Override
		@SuppressWarnings("unchecked")
		public TableModelAdapter<E> getSource() {
			return (TableModelAdapter<E>) super.getSource();
		}

		public E selection() {
			return this.selection;
		}

	}


	// ********** selection support **********

	public void addSelectionChangeListener(SelectionChangeListener<E> listener) {
		this.selectionChangeListeners = CollectionTools.add(this.selectionChangeListeners, listener);
	}

	public void removeSelectionChangeListener(SelectionChangeListener<E> listener) {
		this.selectionChangeListeners = CollectionTools.remove(this.selectionChangeListeners, listener);
	}

	public interface SelectionChangeListener<E> {
		void selectionChanged(SelectionChangeEvent<E> event);
	}

	public static class SelectionChangeEvent<E> extends EventObject {
		private final Collection<E> selection;
		private static final long serialVersionUID = 1L;

		protected SelectionChangeEvent(TableModelAdapter<E> source, Collection<E> selection) {
			super(source);
			if (selection == null) {
				throw new NullPointerException();
			}
			this.selection = selection;
		}

		@Override
		@SuppressWarnings("unchecked")
		public TableModelAdapter<E> getSource() {
			return (TableModelAdapter<E>) super.getSource();
		}

		public Iterator<E> selection() {
			return this.selection.iterator();
		}
	}
}
