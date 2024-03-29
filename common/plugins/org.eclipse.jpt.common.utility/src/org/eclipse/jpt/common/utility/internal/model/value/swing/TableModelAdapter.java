/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTListChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * This TableModel can be used to keep a TableModelListener (e.g. a JTable)
 * in synch with a ListValueModel that holds a collection of model objects,
 * each of which corresponds to a row in the table.
 * Typically, each column of the table will be bound to a different aspect
 * of the contained model objects.
 * 
 * For example, a MWTable has an attribute 'databaseFields' that holds
 * a collection of MWDatabaseFields that would correspond to the rows of
 * a JTable; and each MWDatabaseField has a number
 * of attributes (e.g. name, type, size) that can be bound to the columns of
 * a row in the JTable. As these database fields are added, removed, and
 * changed, this model will keep the listeners aware of the changes.
 * 
 * An instance of this TableModel must be supplied with a
 * list holder (e.g. the 'databaseFields'), which is a value
 * model on the bound collection This is required - the
 * collection itself can be null, but the list value model that
 * holds it is required. Typically this list will be sorted (@see
 * SortedListValueModelAdapter).
 * 
 * This TableModel must also be supplied with a ColumnAdapter that
 * will be used to configure the headers, renderers, editors, and contents
 * of the various columns.
 * 
 * Design decision:
 * Cell listener options (from low space/high time to high space/low time):
 * 	- 1 cell listener listening to every cell (this is the current implementation)
 * 	- 1 cell listener per row
 * 	- 1 cell listener per cell
 */
public class TableModelAdapter<E>
	extends AbstractTableModel
{
	/**
	 * a list of user objects that are converted to
	 * rows via the column adapter
	 */
	private ListValueModel<? extends E> listHolder;
	private final ListChangeListener listChangeListener;

	/**
	 * each row is an array of cell models
	 */
	// declare as ArrayList so we can use #ensureCapacity(int)
	private final ArrayList<ModifiablePropertyValueModel<Object>[]> rows;

	/**
	 * client-supplied adapter that provides with the various column
	 * settings and converts the objects in the LVM
	 * into an array of cell models
	 */
	private final ColumnAdapter columnAdapter;

	/**
	 * the single listener that listens to every cell's model
	 */
	private final PropertyChangeListener cellListener;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a table model adapter for the specified objects
	 * and adapter.
	 */
	public TableModelAdapter(ListValueModel<? extends E> listHolder, ColumnAdapter columnAdapter) {
		super();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.columnAdapter = columnAdapter;
		this.listChangeListener = this.buildListChangeListener();
		this.rows = new ArrayList<ModifiablePropertyValueModel<Object>[]>();
		this.cellListener = this.buildCellListener();
	}

	/**
	 * Construct a table model adapter for the specified objects
	 * and adapter.
	 */
	public TableModelAdapter(CollectionValueModel<? extends E> collectionHolder, ColumnAdapter columnAdapter) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), columnAdapter);
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		return new AWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	protected ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				TableModelAdapter.this.addRows(event.getIndex(), event.getItemsSize(), this.getItems(event));
			}
			public void itemsRemoved(ListRemoveEvent event) {
				TableModelAdapter.this.removeRows(event.getIndex(), event.getItemsSize());
			}
			public void itemsReplaced(ListReplaceEvent event) {
				TableModelAdapter.this.replaceRows(event.getIndex(), this.getNewItems(event));
			}
			public void itemsMoved(ListMoveEvent event) {
				TableModelAdapter.this.moveRows(event.getTargetIndex(), event.getSourceIndex(), event.getLength());
			}
			public void listCleared(ListClearEvent event) {
				TableModelAdapter.this.clearTable();
			}
			public void listChanged(ListChangeEvent event) {
				TableModelAdapter.this.rebuildTable();
			}
			// minimized scope of suppressed warnings
			@SuppressWarnings("unchecked")
			protected Iterable<Object> getItems(ListAddEvent event) {
				return (Iterable<Object>) event.getItems();
			}
			// minimized scope of suppressed warnings
			@SuppressWarnings("unchecked")
			protected Iterable<Object> getNewItems(ListReplaceEvent event) {
				return (Iterable<Object>) event.getNewItems();
			}
			@Override
			public String toString() {
				return "list listener"; //$NON-NLS-1$
			}
		};
	}


	protected PropertyChangeListener buildCellListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildCellListener_());
	}

	protected PropertyChangeListener buildCellListener_() {
		return new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent event) {
				TableModelAdapter.this.cellChanged((ModifiablePropertyValueModel<Object>) event.getSource());
			}
			@Override
			public String toString() {
				return "cell listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** TableModel implementation **********

	public int getColumnCount() {
		return this.columnAdapter.columnCount();
	}

	public int getRowCount() {
		return this.rows.size();
	}

    @Override
	public String getColumnName(int column) {
		return this.columnAdapter.columnName(column);
	}

    @Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.columnAdapter.columnClass(columnIndex);
	}

    @Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return this.columnAdapter.columnIsEditable(columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ModifiablePropertyValueModel<Object>[] row = this.rows.get(rowIndex);
		return row[columnIndex].getValue();
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		ModifiablePropertyValueModel<Object>[] row = this.rows.get(rowIndex);
		row[columnIndex].setValue(value);
	}

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
    @Override
	public void addTableModelListener(TableModelListener l) {
		if (this.hasNoTableModelListeners()) {
			this.engageModel();
		}
		super.addTableModelListener(l);
	}

	/**
	 * Extend to stop listening to the underlying model if necessary.
	 */
    @Override
	public void removeTableModelListener(TableModelListener l) {
		super.removeTableModelListener(l);
		if (this.hasNoTableModelListeners()) {
			this.disengageModel();
		}
	}


	// ********** public API **********

	/**
	 * Return the underlying list model.
	 */
	public ListValueModel<? extends E> getModel() {
		return this.listHolder;
	}

	/**
	 * Set the underlying list model.
	 */
	public void setModel(ListValueModel<E> listHolder) {
		if (listHolder == null) {
			throw new NullPointerException();
		}
		boolean hasListeners = this.hasTableModelListeners();
		if (hasListeners) {
			this.disengageModel();
		}
		this.listHolder = listHolder;
		if (hasListeners) {
			this.engageModel();
			this.fireTableDataChanged();
		}
	}

	/**
	 * Set the underlying collection model.
	 */
	public void setModel(CollectionValueModel<E> collectionHolder) {
		this.setModel(new CollectionListValueModelAdapter<E>(collectionHolder));
	}


	// ********** queries **********

	/**
	 * Return whether this model has no listeners.
	 */
	protected boolean hasNoTableModelListeners() {
		return this.listenerList.getListenerCount(TableModelListener.class) == 0;
	}

	/**
	 * Return whether this model has any listeners.
	 */
	protected boolean hasTableModelListeners() {
		return ! this.hasNoTableModelListeners();
	}


	// ********** behavior **********

	/**
	 * Start listening to the list of objects and the various aspects
	 * of the objects that make up the rows.
	 */
	private void engageModel() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		this.engageAllCells();
	}

	/**
	 * Convert the objects into rows and listen to the cells.
	 */
	private void engageAllCells() {
		this.rows.ensureCapacity(this.listHolder.size());
		for (Iterator<? extends E> stream = this.listHolder.iterator(); stream.hasNext(); ) {
			ModifiablePropertyValueModel<Object>[] row = this.columnAdapter.cellModels(stream.next());
			this.engageRow(row);
			this.rows.add(row);
		}
	}

	/**
	 * Listen to the cells in the specified row.
	 */
	private void engageRow(ModifiablePropertyValueModel<Object>[] row) {
		for (int i = row.length; i-- > 0; ) {
			row[i].addPropertyChangeListener(PropertyValueModel.VALUE, this.cellListener);
		}
	}

	/**
	 * Stop listening.
	 */
	private void disengageModel() {
		this.disengageAllCells();
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	private void disengageAllCells() {
		for (ModifiablePropertyValueModel<Object>[] row : this.rows) {
			this.disengageRow(row);
		}
		this.rows.clear();
	}

	private void disengageRow(ModifiablePropertyValueModel<Object>[] row) {
		for (int i = row.length; i-- > 0; ) {
			row[i].removePropertyChangeListener(PropertyValueModel.VALUE, this.cellListener);
		}
	}

	/**
	 * brute-force search for the cell(s) that changed...
	 */
	void cellChanged(ModifiablePropertyValueModel<Object> cellHolder) {
		for (int i = this.rows.size(); i-- > 0; ) {
			ModifiablePropertyValueModel<Object>[] row = this.rows.get(i);
			for (int j = row.length; j-- > 0; ) {
				if (row[j] == cellHolder) {
					this.fireTableCellUpdated(i, j);
				}
			}
		}
	}

	/**
	 * convert the items to rows
	 */
	void addRows(int index, int size, Iterable<Object> items) {
		List<ModifiablePropertyValueModel<Object>[]> newRows = new ArrayList<ModifiablePropertyValueModel<Object>[]>(size);
		for (Object item : items) {
			ModifiablePropertyValueModel<Object>[] row = this.columnAdapter.cellModels(item);
			this.engageRow(row);
			newRows.add(row);
		}
		this.rows.addAll(index, newRows);
		this.fireTableRowsInserted(index, index + size - 1);
	}

	void removeRows(int index, int size) {
		for (int i = 0; i < size; i++) {
			this.disengageRow(this.rows.remove(index));
		}
		this.fireTableRowsDeleted(index, index + size - 1);
	}

	void replaceRows(int index, Iterable<Object> items) {
		int i = index;
		for (Object item : items) {
			ModifiablePropertyValueModel<Object>[] row = this.rows.get(i);
			this.disengageRow(row);
			row = this.columnAdapter.cellModels(item);
			this.engageRow(row);
			this.rows.set(i, row);
			i++;
		}
		this.fireTableRowsUpdated(index, i - 1);
	}

	void moveRows(int targetIndex, int sourceIndex, int length) {
		ArrayList<ModifiablePropertyValueModel<Object>[]> temp = new ArrayList<ModifiablePropertyValueModel<Object>[]>(length);
		for (int i = 0; i < length; i++) {
			temp.add(this.rows.remove(sourceIndex));
		}
		this.rows.addAll(targetIndex, temp);

		int start = Math.min(targetIndex, sourceIndex);
		int end = Math.max(targetIndex, sourceIndex) + length - 1;
		this.fireTableRowsUpdated(start, end);
	}

	void clearTable() {
		this.disengageAllCells();
		this.fireTableDataChanged();
	}

	void rebuildTable() {
		this.disengageAllCells();
		this.engageAllCells();
		this.fireTableDataChanged();
	}


	/**
	 * This adapter is used by the table model adapter to
	 * convert a model object into the models used for each of
	 * the cells for the object's corresponding row in the table.
	 */
	public interface ColumnAdapter {
		/**
		 * Return the number of columns in the table.
		 * Typically this is static.
		 */
		int columnCount();

		/**
		 * Return the name of the specified column.
		 */
		String columnName(int index);

		/**
		 * Return the class of the specified column.
		 */
		Class<?> columnClass(int index);

		/**
		 * Return whether the specified column is editable.
		 * Typically this is the same for every row.
		 */
		boolean columnIsEditable(int index);

		/**
		 * Return the cell models for the specified subject
		 * that corresponds to a single row in the table.
		 */
		ModifiablePropertyValueModel<Object>[] cellModels(Object subject);
	}
}
