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
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter.SelectionChangeEvent;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter.SelectionChangeListener;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This implementation of the <code>AddRemovePane</code> uses a <code>Table</code>
 * as its main widget, a <code>List</code> can't be used because it doesn't
 * support showing images. However, the table is displayed like a list.
 * <p>
 * Here the layot of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------- ----------- |
 * | | Item 1                                                    | | Add...  | |
 * | | ...                                                       | ----------- |
 * | | Item n                                                    | ----------- |
 * | |                                                           | | Edit... | |
 * | |                                                           | ----------- |
 * | |                                                           | ----------- |
 * | |                                                           | | Remove  | |
 * | |                                                           | ----------- |
 * | -------------------------------------------------------------             |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class AddRemoveListPane<T extends Model> extends AddRemovePane<T>
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
	 * Creates a new <code>AddRemoveListPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the table holder's items
	 */
	public AddRemoveListPane(AbstractPane<? extends T> parentPane,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider) {

		super(parentPane,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider);
	}

	/**
	 * Creates a new <code>AddRemoveListPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the table holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	public AddRemoveListPane(AbstractPane<? extends T> parentPane,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider,
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
	 * Creates a new <code>AddRemoveListPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the table holder's items
	 */
	public AddRemoveListPane(AbstractPane<?> parentPane,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider) {

		super(parentPane,
		      subjectHolder,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider);
	}

	/**
	 * Creates a new <code>AddRemoveListPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the table holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	public AddRemoveListPane(AbstractPane<?> parentPane,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider,
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

	private ColumnAdapter<Object> buildColumnAdapter() {
		return new ColumnAdapter<Object>() {
			public WritablePropertyValueModel<?>[] cellModels(Object subject) {
				WritablePropertyValueModel<?>[] valueHolders = new WritablePropertyValueModel<?>[1];
				valueHolders[0] = new SimplePropertyValueModel<Object>(subject);
				return valueHolders;
			}

			public int columnCount() {
				return 1;
			}

			public String columnName(int columnIndex) {
				return "";
			}
		};
	}

	private ListChangeListener buildListChangeListener() {
		return new SWTListChangeListenerWrapper(buildListChangeListener_());
	}

	private ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				if (!table.isDisposed()) {
					table.getParent().layout();
				}
			}

			public void itemsMoved(ListChangeEvent e) {
				if (!table.isDisposed()) {
					table.getParent().layout();
				}
			}

			public void itemsRemoved(ListChangeEvent e) {
				if (!table.isDisposed()) {
					table.getParent().layout();
				}
			}

			public void itemsReplaced(ListChangeEvent e) {
				if (!table.isDisposed()) {
					table.getParent().layout();
				}
			}

			public void listChanged(ListChangeEvent e) {
				if (!table.isDisposed()) {
					table.getParent().layout();
				}
			}

			public void listCleared(ListChangeEvent e) {
				if (!table.isDisposed()) {
					table.getParent().layout();
				}
			}
		};
	}

	private SimplePropertyValueModel<Object> buildSelectedItemHolder() {
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
						Object value = e.newValue();
						getSelectionModel().setSelectedValue(e.newValue());
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
				AddRemoveListPane.this.selectionChanged();
			}
		};
	}

	private Composite buildTableContainer(Composite container) {

		container = buildPane(container, buildTableContainerLayout());

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		container.setLayoutData(gridData);

		return container;
	}

	private Layout buildTableContainerLayout() {
		return new Layout() {
			@Override
			protected Point computeSize(Composite composite,
			                            int widthHint,
			                            int heightHint,
			                            boolean flushCache) {

				Table table = (Table) composite.getChildren()[0];

				// Pack the column so we can get the right table size
				TableColumn tableColumn = table.getColumn(0);
				table.setRedraw(false);
				table.setLayoutDeferred(true);
				tableColumn.pack();
				table.setRedraw(true);
				table.setLayoutDeferred(false);

				// Calculate the table size and adjust it with the hints
				Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);

				if (widthHint != SWT.DEFAULT) {
					size.x = widthHint;
				}

				if (heightHint != SWT.DEFAULT) {
					size.y = heightHint;
				}

				return size;
			}

			@Override
			protected void layout(Composite composite, boolean flushCache) {

				Rectangle bounds = composite.getClientArea();

				if (bounds.width > 0) {
					Table table = (Table) composite.getChildren()[0];
					table.setBounds(0, 0, bounds.width, bounds.height);
					updateTableColumnWidth(table, bounds.width);
				}
			}

			private void updateTableColumnWidth(Table table, int width) {

				TableColumn tableColumn = table.getColumn(0);

				// Remove the border from the width
				width -= (table.getBorderWidth() * 2);

				// Remove the scrollbar from the width if it is shown
				if (table.getVerticalBar().isVisible()) {
					width -= table.getVerticalBar().getSize().x;
				}

				// Use the table width if the table column is smaller, otherwise
				// use the table column since the rows are never smaller than the
				// smallest row
				width = Math.max(width, tableColumn.getWidth());
				tableColumn.setWidth(width);
			}
		};
	}

	private ITableLabelProvider buiTableLabelProvider(IBaseLabelProvider labelProvider) {
		return new TableLabelProvider((ILabelProvider) labelProvider);
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
	                                       String helpId) {

		table = buildTable(
			buildTableContainer(container),
			SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI,
			helpId
		);

		table.setHeaderVisible(false);
		table.setLinesVisible(false);

		TableModelAdapter model = TableModelAdapter.adapt(
			(ListValueModel<Object>) listHolder,
			buildSelectedItemHolder(),
			table,
			buildColumnAdapter(),
			buiTableLabelProvider(labelProvider)
		);

		model.addSelectionChangeListener(buildSelectionListener());

		selectedItemHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildSelectedItemPropertyChangeListener()
		);

		listHolder.addListChangeListener(
			ListValueModel.LIST_VALUES,
			buildListChangeListener()
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

	/**
	 * This label provider simply delegates the rendering to the provided
	 * <code>ILabelProvider</code>.
	 */
	private class TableLabelProvider extends LabelProvider
	                                 implements ITableLabelProvider {

		private ILabelProvider labelProvider;

		TableLabelProvider(ILabelProvider labelProvider) {
			super();
			this.labelProvider = labelProvider;
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return labelProvider.getImage(element);
		}

		public String getColumnText(Object element, int columnIndex) {
			return labelProvider.getText(element);
		}
	}
}