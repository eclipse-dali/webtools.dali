/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter.SelectionChangeEvent;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter.SelectionChangeListener;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * This implementation of the <code>AddRemovePane</code> uses a <code>Table</code>
 * as its main widget.
 * <p>
 * Here the layot of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------- ----------- |
 * | | Column 1 | Column 2 | ...   | Column i | ...   | Colunm n | | Add...  | |
 * | |-----------------------------------------------------------| ----------- |
 * | |          |          |       |          |       |          | ----------- |
 * | |-----------------------------------------------------------| | Edit... | |
 * | |          |          |       |          |       |          | ----------- |
 * | |-----------------------------------------------------------| ----------- |
 * | |          |          |       |          |       |          | | Remove  | |
 * | |-----------------------------------------------------------| ----------- |
 * | -------------------------------------------------------------             |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class AddRemoveTablePane<T extends Model> extends AddRemovePane<T>
{
	/**
	 * Flag used to prevent circular
	 */
	private boolean locked;

	/**
	 * The main widget of this add/remove pane.
	 */
	private Table table;

	/**
	 * Creates a new <code>AddRemoveTablePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	public AddRemoveTablePane(AbstractPane<? extends T> parentPane,
	                          Composite parent,
	                          Adapter adapter,
	                          ListValueModel<?> listHolder,
	                          WritablePropertyValueModel<?> selectedItemHolder,
	                          ITableLabelProvider labelProvider) {

		super(parentPane,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider);

	}

	/**
	 * Creates a new <code>AddRemoveTablePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	public AddRemoveTablePane(AbstractPane<? extends T> parentPane,
	                          Composite parent,
	                          Adapter adapter,
	                          ListValueModel<?> listHolder,
	                          WritablePropertyValueModel<?> selectedItemHolder,
	                          ITableLabelProvider labelProvider,
	                          String helpId) {

		super(parentPane,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider,
		      helpId);
	}

	/**
	 * Creates a new <code>AddRemoveTablePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	public AddRemoveTablePane(AbstractPane<?> parentPane,
	                          PropertyValueModel<? extends T> subjectHolder,
	                          Composite parent,
	                          Adapter adapter,
	                          ListValueModel<?> listHolder,
	                          WritablePropertyValueModel<?> selectedItemHolder,
	                          ITableLabelProvider labelProvider) {

		super(parentPane,
		      subjectHolder,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider);
	}

	/**
	 * Creates a new <code>AddRemoveTablePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	public AddRemoveTablePane(AbstractPane<?> parentPane,
	                          PropertyValueModel<? extends T> subjectHolder,
	                          Composite parent,
	                          Adapter adapter,
	                          ListValueModel<?> listHolder,
	                          WritablePropertyValueModel<?> selectedItemHolder,
	                          ITableLabelProvider labelProvider,
	                          String helpId) {

		super(parentPane,
		      subjectHolder,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider,
		      helpId);
	}

	protected abstract ColumnAdapter<?> buildColumnAdapter();

	private WritablePropertyValueModel<Object> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<Object>();
	}

	private PropertyChangeListener buildSelectedItemPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(
			buildSelectedItemPropertyChangeListener_()
		);
	}

	private PropertyChangeListener buildSelectedItemPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (table.isDisposed()) {
					return;
				}

				if (!locked) {
					locked = true;

					try {
						Object value = e.getNewValue();
						getSelectionModel().setSelectedValue(e.getNewValue());
						int index = -1;

						if (value != null) {
							index = CollectionTools.indexOf(getListHolder().iterator(), value);
						}

						table.select(index);
						updateButtons();
					}
					finally {
						locked = false;
					}
				}
			}
		};
	}

	private SelectionChangeListener<Object> buildSelectionListener() {
		return new SelectionChangeListener<Object>() {
			public void selectionChanged(SelectionChangeEvent<Object> e) {
				AddRemoveTablePane.this.selectionChanged();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getMainControl() {
		return table;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void initializeMainComposite(Composite container,
	                                       Adapter adapter,
	                                       ListValueModel<?> listHolder,
	                                       WritablePropertyValueModel<?> selectedItemHolder,
	                                       IBaseLabelProvider labelProvider,
	                                       String helpId)
	{
		table = buildTable(container, helpId);
		table.setHeaderVisible(true);

		removeFromEnablementControl(table);

		TableModelAdapter<Object> tableModel = TableModelAdapter.adapt(
			(ListValueModel<Object>) listHolder,
			buildSelectedItemHolder(),
			table,
			(ColumnAdapter<Object>) buildColumnAdapter(),
			(ITableLabelProvider) labelProvider
		);

		tableModel.addSelectionChangeListener(buildSelectionListener());

		selectedItemHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildSelectedItemPropertyChangeListener()
		);
	}

	/**
	 * The selection has changed, update (1) the selected item holder, (2) the
	 * selection model and (3) the buttons.
	 */
	private void selectionChanged() {

		if (locked) {
			return;
		}

		locked = true;

		try {
			WritablePropertyValueModel<Object> selectedItemHolder = getSelectedItemHolder();
			ObjectListSelectionModel selectionModel = getSelectionModel();
			int selectionCount = table.getSelectionCount();

			if (selectionCount == 0) {
				selectedItemHolder.setValue(null);
				selectionModel.clearSelection();
			}
			else if (selectionCount != 1) {
				selectedItemHolder.setValue(null);
				selectionModel.clearSelection();

				for (int index : table.getSelectionIndices()) {
					selectionModel.addSelectionInterval(index, index);
				}
			}
			else {
				int selectedIndex = table.getSelectionIndex();
				Object selectedItem = getListHolder().get(selectedIndex);

				selectedItemHolder.setValue(selectedItem);
				selectionModel.setSelectedValue(selectedItem);
			}

			updateButtons();
		}
		finally {
			locked = false;
		}
	}
}
