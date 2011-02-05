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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jpt.common.ui.internal.listeners.SWTCollectionChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritableCollectionValueModel;
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
 * {@link #listHolder} contains the data of a single column in the table.
 * {@link #selectedItemsHolder} contains the data of a single column in
 * {@link #listHolder} that are selected in the table.
 */
// TODO bjv
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
	protected final ListenerList<SelectionChangeListener<E>> selectionChangeListenerList;

	/**
	 * Clients that are interested in double click events.
	 */
	protected final ListenerList<DoubleClickListener<E>> doubleClickListenerList;

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
	 * Adapt the specified list model and selection to the specified table.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the table.
	 */
	public static <T> TableModelAdapter<T> adapt(
			ListValueModel<T> listHolder,
			PropertyValueModel<T> selectedItemHolder,
			Table table,
			ColumnAdapter<T> columnAdapter,
			ITableLabelProvider labelProvider) {
		
		return new TableModelAdapter<T>(
			listHolder,
			new PropertyCollectionValueModelAdapter<T>(selectedItemHolder),
			table,
			columnAdapter,
			labelProvider);
	}
	
	/**
	 * Adapt the specified list model and selection to the specified table.
	 * The specified selection model will be kept in sync with the table.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the table.
	 */
	public static <T> TableModelAdapter<T> adapt(
			ListValueModel<T> listHolder,
			WritableCollectionValueModel<T> selectionModel,
			Table table,
			ColumnAdapter<T> columnAdapter,
			ITableLabelProvider labelProvider) {
		
		TableModelAdapter adapter = 
				new TableModelAdapter<T>(
					listHolder,
					selectionModel,
					table,
					columnAdapter,
					labelProvider);
		adapter.addSelectionChangeListener(buildSyncListener(selectionModel));
		return adapter;
	}
	
	private static <T> SelectionChangeListener buildSyncListener(
				final WritableCollectionValueModel<T> selectionModel) {
		
		return new SelectionChangeListener() {
			public void selectionChanged(SelectionChangeEvent event) {
				selectionModel.setValues(CollectionTools.iterable(event.selection()));
			}
		};
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

		this.selectionChangeListenerList = this.buildSelectionChangeListenerList();
		this.doubleClickListenerList = this.buildDoubleClickListenerList();

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
			public void itemsAdded(ListAddEvent event) {
				TableModelAdapter.this.listItemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				TableModelAdapter.this.listItemsRemoved(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				TableModelAdapter.this.listItemsMoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				TableModelAdapter.this.listItemsReplaced(event);
			}
			public void listCleared(ListClearEvent event) {
				TableModelAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				TableModelAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "TableModelAdapter list listener";
			}
		};
	}

	protected CollectionChangeListener buildSelectedItemsChangeListener() {
		return new SWTCollectionChangeListenerWrapper(this.buildSelectedItemsChangeListener_());
	}

	protected CollectionChangeListener buildSelectedItemsChangeListener_() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				TableModelAdapter.this.selectedItemsAdded(event);
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				TableModelAdapter.this.selectedItemsRemoved(event);
			}
			public void collectionCleared(CollectionClearEvent event) {
				TableModelAdapter.this.selectedItemsCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				TableModelAdapter.this.selectedItemsChanged(event);
			}
			@Override
			public String toString() {
				return "TableModelAdapter selected items listener";
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
				return "TableModelAdapter table selection listener";
			}
		};
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	protected ListenerList<DoubleClickListener<E>> buildDoubleClickListenerList() {
		return new ListenerList(DoubleClickListener.class);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	protected ListenerList<SelectionChangeListener<E>> buildSelectionChangeListenerList() {
		return new ListenerList(SelectionChangeListener.class);
	}

	protected DisposeListener buildTableDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				TableModelAdapter.this.tableDisposed(event);
			}
			@Override
			public String toString() {
				return "TableModelAdapter table dispose listener";
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
			tableColumn.setWidth(100);

			String columnName = this.columnAdapter.columnName(index);

			if (columnName == null) {
				columnName = "";
			}

			tableColumn.setText(columnName);
		}
	}

	/**
	 * Brute force synchronization of table with the model list.
	 */
	protected void synchronizeTableItems() {
		if (this.table.isDisposed()) {
			return;
		}

		for (int index = this.table.getItemCount(); --index >= 0; ) {
			this.table.remove(index);
			this.tableItemModelAdapters.remove(index);
		}

		int itemCount = this.listHolder.size();

		for (int index = 0; index < itemCount; index++) {

			TableItem tableItem = new TableItem(this.table, SWT.NULL, index);
			tableItem.setData(this.listHolder.get(index));

			TableItemModelAdapter adapter = this.buildItemModel(tableItem);
			this.tableItemModelAdapters.add(adapter);
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsAdded(ListAddEvent event) {

		if (this.table.isDisposed()) {
			return;
		}

		int index = event.getIndex();

		for (E item : this.getItems(event)) {

			TableItem tableItem = new TableItem(this.table, SWT.NULL, index);
			tableItem.setData(item);

			TableItemModelAdapter adapter = this.buildItemModel(tableItem);
			this.tableItemModelAdapters.add(index++, adapter);
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsRemoved(ListRemoveEvent event) {

		if (this.table.isDisposed()) {
			return;
		}

		this.table.remove(event.getIndex(), event.getIndex() + event.getItemsSize() - 1);

		for (int index = event.getIndex() + event.getItemsSize(); --index >= event.getIndex(); ) {
			this.tableItemModelAdapters.remove(index);
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsMoved(ListMoveEvent event) {

		if (this.table.isDisposed()) {
			return;
		}

		int length        = event.getLength();
		int sourceIndex   = event.getSourceIndex();
		int targetIndex   = event.getTargetIndex();
		int lowStartIndex = Math.min(targetIndex, sourceIndex);
		int hiStartIndex  = Math.max(targetIndex, sourceIndex);

		Object[] items = new Object[hiStartIndex - lowStartIndex + length];
		int itemsIndex = items.length;

		// Remove the TableItems wrapping the moved items
		for (int index = hiStartIndex + length; --index >= lowStartIndex; ) {

			TableItemModelAdapter tableItemModel = this.tableItemModelAdapters.get(index);
			items[--itemsIndex] = tableItemModel.tableItem.getData();

			// Remove the TableItem, which will also dispose TableItemModelAdapter
			this.table.remove(index);
		}

		// Move the items so they can retrieved in the right order when
		// re-creating the TableItems
		ArrayTools.move(
			items,
			targetIndex - lowStartIndex,
			sourceIndex - lowStartIndex,
			length
		);

		itemsIndex = 0;

		// Add TableItems for the moved items
		for (int index = lowStartIndex; index <= hiStartIndex + length - 1; index++) {

			// Create the new TableItem
			TableItem tableItem = new TableItem(this.table, SWT.NULL, index);
			tableItem.setData(items[itemsIndex++]);

			// Adapt it with a model adapter
			TableItemModelAdapter adapter = this.buildItemModel(tableItem);
			this.tableItemModelAdapters.set(index, adapter);
		}
	}


	private TableItemModelAdapter buildItemModel(TableItem tableItem) {
		return TableItemModelAdapter.adapt(
			tableItem,
			this.columnAdapter,
			this.labelProvider
		);
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listItemsReplaced(ListReplaceEvent event) {
		if (this.table.isDisposed()) {
			return;
		}

		int rowIndex = event.getIndex();

		for (E item : this.getNewItems(event)) {
			TableItem tableItem = this.table.getItem(rowIndex);
			tableItem.setData(item);

			TableItemModelAdapter adapter = this.tableItemModelAdapters.get(rowIndex);

			int columnCount = this.columnAdapter.columnCount();
			boolean revalidate = (columnCount == 1);

			for (int columnIndex = columnCount; --columnIndex >= 0; ) {
				adapter.tableItemChanged(columnIndex, tableItem.getData(), revalidate);
			}

			rowIndex++;
		}
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listCleared(@SuppressWarnings("unused") ListClearEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		this.table.removeAll();
	}

	/**
	 * The model has changed - synchronize the table.
	 */
	protected void listChanged(@SuppressWarnings("unused") ListChangeEvent event) {
		this.synchronizeTableItems();
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
		for (E selectedItemHolder : this.selectedItemsHolder) {
			indices[i++] = this.indexOf(selectedItemHolder);
		}
		this.table.deselectAll();
		this.table.select(indices);
	}

	protected void selectedItemsAdded(CollectionAddEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		this.table.select(this.getIndices(event.getItemsSize(), this.getItems(event)));
	}

	protected void selectedItemsRemoved(CollectionRemoveEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		this.table.deselect(this.getIndices(event.getItemsSize(), this.getItems(event)));
	}

	protected int[] getIndices(int itemsSize, Iterable<E> items) {
		int[] indices = new int[itemsSize];
		int i = 0;
		for (E item : items) {
			indices[i++] = this.indexOf(item);
		}
		return indices;
	}

	protected void selectedItemsCleared(@SuppressWarnings("unused") CollectionClearEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		this.table.deselectAll();
	}

	protected void selectedItemsChanged(@SuppressWarnings("unused") CollectionChangeEvent event) {
		this.synchronizeTableSelection();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(CollectionAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(CollectionRemoveEvent event) {
		return (Iterable<E>) event.getItems();
	}


	// ********** list box events **********

	protected void tableSelectionChanged(@SuppressWarnings("unused") SelectionEvent event) {
		if (this.selectionChangeListenerList.size() > 0) {
			SelectionChangeEvent<E> scEvent = new SelectionChangeEvent<E>(this, this.selectedItems());
			for (SelectionChangeListener<E> selectionChangeListener : this.selectionChangeListenerList.getListeners()) {
				selectionChangeListener.selectionChanged(scEvent);
			}
		}
	}

	protected Collection<E> selectedItems() {
		if (this.table.isDisposed()) {
			return Collections.emptySet();
		}
		ArrayList<E> selectedItems = new ArrayList<E>(this.table.getSelectionCount());
		for (int selectionIndex : this.table.getSelectionIndices()) {
			selectedItems.add(this.listHolder.get(selectionIndex));
		}
		return selectedItems;
	}

	protected void tableDoubleClicked(@SuppressWarnings("unused") SelectionEvent event) {
		if (this.table.isDisposed()) {
			return;
		}
		if (this.doubleClickListenerList.size() > 0) {
			// there should be only a single item selected during a double-click(?)
			E selection = this.listHolder.get(this.table.getSelectionIndex());
			DoubleClickEvent<E> dcEvent = new DoubleClickEvent<E>(this, selection);
			for (DoubleClickListener<E> doubleClickListener : this.doubleClickListenerList.getListeners()) {
				doubleClickListener.doubleClick(dcEvent);
			}
		}
	}


	// ********** dispose **********

	protected void tableDisposed(@SuppressWarnings("unused") DisposeEvent event) {
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
		this.selectionChangeListenerList.add(listener);
	}

	public void removeSelectionChangeListener(SelectionChangeListener<E> listener) {
		this.selectionChangeListenerList.remove(listener);
	}

	public interface SelectionChangeListener<E> extends EventListener {
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
