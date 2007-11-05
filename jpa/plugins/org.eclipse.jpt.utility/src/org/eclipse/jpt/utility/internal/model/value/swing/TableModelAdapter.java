/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.swing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;

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
public class TableModelAdapter extends AbstractTableModel {

	/**
	 * a list of user objects that are converted to
	 * rows via the column adapter
	 */
	private ListValueModel listHolder;
	private ListChangeListener listChangeListener;

	/**
	 * each row is an array of cell models
	 */
	private ArrayList<PropertyValueModel[]> rows;	// declare as ArrayList so we can use #ensureCapacity(int)

	/**
	 * client-supplied adapter that provides with the various column
	 * settings and converts the objects in the LVM
	 * into an array of cell models
	 */
	private ColumnAdapter columnAdapter;

	/**
	 * the single listener that listens to every cell's model
	 */
	private PropertyChangeListener cellListener;


	// ********** constructors **********

	/**
	 * internal constructor
	 */
	private TableModelAdapter() {
		super();
		this.initialize();
	}

	/**
	 * Construct a table model adapter for the specified objects
	 * and adapter.
	 */
	public TableModelAdapter(ListValueModel listHolder, ColumnAdapter columnAdapter) {
		this();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.columnAdapter = columnAdapter;
	}

	/**
	 * Construct a table model adapter for the specified objects
	 * and adapter.
	 */
	public TableModelAdapter(CollectionValueModel collectionHolder, ColumnAdapter columnAdapter) {
		this(new CollectionListValueModelAdapter(collectionHolder), columnAdapter);
	}


	// ********** initialization **********

	private void initialize() {
		this.listChangeListener = this.buildListChangeListener();
		this.rows = new ArrayList<PropertyValueModel[]>();
		this.cellListener = this.buildCellListener();
	}

	private ListChangeListener buildListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				TableModelAdapter.this.addRows(e.index(), e.itemsSize(), e.items());
			}
			public void itemsRemoved(ListChangeEvent e) {
				TableModelAdapter.this.removeRows(e.index(), e.itemsSize());
			}
			public void itemsReplaced(ListChangeEvent e) {
				TableModelAdapter.this.replaceRows(e.index(), e.items());
			}
			public void itemsMoved(ListChangeEvent e) {
				TableModelAdapter.this.moveRows(e.targetIndex(), e.sourceIndex(), e.moveLength());
			}
			public void listCleared(ListChangeEvent e) {
				TableModelAdapter.this.clearTable();
			}
			public void listChanged(ListChangeEvent e) {
				TableModelAdapter.this.rebuildTable();
			}
			@Override
			public String toString() {
				return "list listener";
			}
		};
	}

	private PropertyChangeListener buildCellListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent evt) {
				TableModelAdapter.this.cellChanged((PropertyValueModel) evt.getSource());
			}
			@Override
			public String toString() {
				return "cell listener";
			}
		};
	}


	// ********** TableModel implementation **********

	public int getColumnCount() {
		return this.columnAdapter.getColumnCount();
	}

	public int getRowCount() {
		return this.rows.size();
	}

    @Override
	public String getColumnName(int column) {
		return this.columnAdapter.getColumnName(column);
	}

    @Override
	public Class getColumnClass(int columnIndex) {
		return this.columnAdapter.getColumnClass(columnIndex);
	}

    @Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return this.columnAdapter.isColumnEditable(columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		PropertyValueModel[] row = this.rows.get(rowIndex);
		return row[columnIndex].value();
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		PropertyValueModel[] row = this.rows.get(rowIndex);
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
	public ListValueModel getModel() {
		return this.listHolder;
	}

	/**
	 * Set the underlying list model.
	 */
	public void setModel(ListValueModel listHolder) {
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
	public void setModel(CollectionValueModel collectionHolder) {
		this.setModel(new CollectionListValueModelAdapter(collectionHolder));
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
		this.listHolder.addListChangeListener(ValueModel.VALUE, this.listChangeListener);
		this.engageAllCells();
	}

	/**
	 * Convert the objects into rows and listen to the cells.
	 */
	private void engageAllCells() {
		this.rows.ensureCapacity(this.listHolder.size());
		for (Iterator stream = (Iterator) this.listHolder.value(); stream.hasNext(); ) {
			PropertyValueModel[] row = this.columnAdapter.cellModels(stream.next());
			this.engageRow(row);
			this.rows.add(row);
		}
	}

	/**
	 * Listen to the cells in the specified row.
	 */
	private void engageRow(PropertyValueModel[] row) {
		for (int i = row.length; i-- > 0; ) {
			row[i].addPropertyChangeListener(ValueModel.VALUE, this.cellListener);
		}
	}

	/**
	 * Stop listening.
	 */
	private void disengageModel() {
		this.disengageAllCells();
		this.listHolder.removeListChangeListener(ValueModel.VALUE, this.listChangeListener);
	}

	private void disengageAllCells() {
		for (PropertyValueModel[] row : this.rows) {
			this.disengageRow(row);
		}
		this.rows.clear();
	}

	private void disengageRow(PropertyValueModel[] row) {
		for (int i = row.length; i-- > 0; ) {
			row[i].removePropertyChangeListener(ValueModel.VALUE, this.cellListener);
		}
	}

	/**
	 * brute-force search for the cell(s) that changed...
	 */
	void cellChanged(PropertyValueModel cellHolder) {
		for (int i = this.rows.size(); i-- > 0; ) {
			PropertyValueModel[] row = this.rows.get(i);
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
	void addRows(int index, int size, Iterator items) {
		List<PropertyValueModel[]> newRows = new ArrayList<PropertyValueModel[]>(size);
		while (items.hasNext()) {
			PropertyValueModel[] row = this.columnAdapter.cellModels(items.next());
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

	void replaceRows(int index, Iterator items) {
		int i = index;
		while (items.hasNext()) {
			PropertyValueModel[] row = this.rows.get(i);
			this.disengageRow(row);
			row = this.columnAdapter.cellModels(items.next());
			this.engageRow(row);
			this.rows.set(i, row);
			i++;
		}
		this.fireTableRowsUpdated(index, i - 1);
	}

	void moveRows(int targetIndex, int sourceIndex, int length) {
		ArrayList<PropertyValueModel[]> temp = new ArrayList<PropertyValueModel[]>(length);
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

}
