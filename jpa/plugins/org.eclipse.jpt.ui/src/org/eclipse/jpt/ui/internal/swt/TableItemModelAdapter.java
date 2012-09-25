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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * This adapter can be used to keep a table item in synch with the properties of
 * a model.
 */
@SuppressWarnings("nls")
public class TableItemModelAdapter {

	/** The table item we synchronize with the model. */
	protected final TableItem tableItem;

	/**
	 * A listener that allows us to stop listening to stuff when the button
	 * is disposed.
	 */
	protected final DisposeListener tableItemDisposeListener;

	/**
	 * Client-supplied adapter that provides with the various column settings and
	 * converts the objects in the LVM into an array of cell models.
	 */
	private ColumnAdapter<Object> columnAdapter;

	/**
	 * The value models used to listen to each property that are display by the
	 * table item.
	 */
	private WritablePropertyValueModel<?>[] valueHolders;

	/**
	 * The list of <code>PropertyChangeListener</code>s used to be notified when
	 * the properties of the model being display into a row change.
	 */
	private PropertyChangeListener[] propertyChangeListeners;

	/**
	 * The label used to format the objects into a string representation.
	 */
	private ITableLabelProvider labelProvider;

	// ********** static methods **********

	/**
	 * Adapt the specified boolean to the specified button.
	 * If the boolean is null, the button's value will be "unselected".
	 */
	public static TableItemModelAdapter adapt(TableItem tableItem, ColumnAdapter<?> columnAdapter, ITableLabelProvider labelProvider) {
		return new TableItemModelAdapter(tableItem, columnAdapter, labelProvider);
	}


	// ********** constructors **********

	/**
	 * Constructor - the boolean holder and button are required.
	 */
	@SuppressWarnings("unchecked")
	protected TableItemModelAdapter(TableItem tableItem, ColumnAdapter<?> columnAdapter, ITableLabelProvider labelProvider) {
		super();
		if (tableItem == null || columnAdapter == null || labelProvider == null) {
			throw new NullPointerException();
		}
		this.tableItem = tableItem;
		this.labelProvider = labelProvider;
		this.columnAdapter = (ColumnAdapter<Object>) columnAdapter;

		this.tableItemDisposeListener = this.buildTableItemDisposeListener();
		this.tableItem.addDisposeListener(this.tableItemDisposeListener);

		this.valueHolders = this.columnAdapter.cellModels(tableItem.getData());
		this.propertyChangeListeners = this.buildPropertyChangeListeners();

		for (int index = this.columnAdapter.columnCount(); --index >= 0; ) {
			tableItemChanged(index, tableItem.getData(), false);
			valueHolders[index].addPropertyChangeListener(PropertyValueModel.VALUE, propertyChangeListeners[index]);
		}
	}


	// ********** initialization **********

	private PropertyChangeListener[] buildPropertyChangeListeners() {
		PropertyChangeListener[] listeners = new PropertyChangeListener[columnAdapter.columnCount()];
		for (int index = listeners.length; --index >= 0; ) {
			listeners[index] = buildPropertyChangeListener(index);
		}
		return listeners;
	}


	protected PropertyChangeListener buildPropertyChangeListener(int index) {
		return new SWTPropertyChangeListenerWrapper(
			this.buildPropertyChangeListener_(index)
		);
	}

	protected PropertyChangeListener buildPropertyChangeListener_(int index) {
		return new TableItemPropertyChangeListener(index);
	}

	protected DisposeListener buildTableItemDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				TableItemModelAdapter.this.tableItemDisposed(event);
			}
		    @Override
			public String toString() {
				return "TableItem dispose listener";
			}
		};
	}


	// ********** behavior **********

	protected void tableItemChanged(int index, Object subject, boolean revalidate) {

		if (!this.tableItem.isDisposed()) {
			this.updateTableItemText(index, subject);
			this.updateTableItemImage(index, subject);

			if (revalidate) {
				this.layoutTable();
			}
		}
	}

	private void updateTableItemText(int index, Object subject) {
		String text = this.labelProvider.getColumnText(subject, index);
		if (text == null) {
			text = "";
		}
		this.tableItem.setText(index, text);
	}

	private void updateTableItemImage(int index, Object subject) {
		Image image = this.labelProvider.getColumnImage(subject, index);
		this.tableItem.setImage(index, image);
	}

	private void layoutTable() {
		// Refresh the table in order to show the scrollbar if required
		Composite container = this.tableItem.getParent().getParent();
		container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		container.layout();
	}

	// ********** dispose **********

	protected void tableItemDisposed(DisposeEvent event) {
		// the button is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.tableItem.removeDisposeListener(this.tableItemDisposeListener);

		for (int index = valueHolders.length; --index >= 0; ) {
			valueHolders[index].removePropertyChangeListener(PropertyValueModel.VALUE, propertyChangeListeners[index]);
		}
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

   private class TableItemPropertyChangeListener implements PropertyChangeListener {

   	private final int index;

   	TableItemPropertyChangeListener(int index) {
   		super();
   		this.index = index;
   	}

   	public void propertyChanged(PropertyChangeEvent event) {
   		if (!tableItem.isDisposed()) {
   			Table table = tableItem.getParent();
   			tableItemChanged(index, tableItem.getData(), table.getColumnCount() == 0);
   		}
   	}
   }
}