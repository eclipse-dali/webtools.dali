/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.common.ui.internal.swt.TableModelAdapter;
import org.eclipse.jpt.common.ui.internal.swt.TableModelAdapter.SelectionChangeEvent;
import org.eclipse.jpt.common.ui.internal.swt.TableModelAdapter.SelectionChangeListener;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
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
 * @version 3.2
 * @since 1.0
 */
@SuppressWarnings("nls")
public class AddRemoveListPane<T extends Model> extends AddRemovePane<T>
{

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
	public AddRemoveListPane(Pane<? extends T> parentPane,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         ModifiablePropertyValueModel<?> selectedItemHolder,
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
	public AddRemoveListPane(Pane<? extends T> parentPane,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         ModifiablePropertyValueModel<?> selectedItemHolder,
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
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the table holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 * @param parentManagePane <code>true</code> to have the parent pane manage
	 * the enabled state of this pane
	 */
	public AddRemoveListPane(Pane<? extends T> parentPane,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         ModifiablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider,
	                         String helpId,
	                         boolean parentManagePane) {

		super(parentPane,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider,
		      helpId,
		      parentManagePane);
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
	public AddRemoveListPane(Pane<?> parentPane,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         ModifiablePropertyValueModel<?> selectedItemHolder,
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
	public AddRemoveListPane(Pane<?> parentPane,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel<?> listHolder,
	                         ModifiablePropertyValueModel<?> selectedItemHolder,
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
			public ModifiablePropertyValueModel<?>[] cellModels(Object subject) {
				ModifiablePropertyValueModel<?>[] valueHolders = new ModifiablePropertyValueModel<?>[1];
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

	@Override
	protected void itemsAdded(ListAddEvent e) {
		super.itemsAdded(e);
		revalidateLayout();
	}

	@Override
	protected void itemsMoved(ListMoveEvent e) {
		super.itemsMoved(e);
		revalidateLayout();
	}

	@Override
	protected void itemsRemoved(ListRemoveEvent e) {
		super.itemsRemoved(e);
		revalidateLayout();
	}

	@Override
	protected void itemsReplaced(ListReplaceEvent e) {
		super.itemsReplaced(e);
		revalidateLayout();
	}

	@Override
	protected void listChanged(ListChangeEvent e) {
		super.listChanged(e);
		revalidateLayout();
	}

	@Override
	protected void listCleared(ListClearEvent e) {
		super.listCleared(e);
		revalidateLayout();
	}

	/**
	 * Revalidates the table layout after the list of items has changed. The
	 * layout has to be done in a new UI thread because our listener might be
	 * notified before the table has been updated (table column added or removed).
	 */
	private void revalidateLayout() {
		SWTUtil.asyncExec(new Runnable() { public void run() {
			if (!table.isDisposed()) {
				table.getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				table.getParent().layout();
			}
		}});
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
				getSelectionModel().setSelectedValue(e.getNewValue());
				updateButtons();
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

	private Composite addTableContainer(Composite container) {

		container = addPane(container, buildTableContainerLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
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
				TableColumn tableColumn = table.getColumn(0);
				int columnWidth = tableColumn.getWidth();
				packColumn(table);

				// Calculate the table size and adjust it with the hints
				Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);

				if (widthHint != SWT.DEFAULT) {
					size.x = widthHint;
				}

				if (heightHint != SWT.DEFAULT) {
					size.y = heightHint;
				}

				// Revert the column's width to its current value
				table.setRedraw(false);
				table.setLayoutDeferred(true);
				tableColumn.setWidth(columnWidth);
				table.setLayoutDeferred(false);
				table.setRedraw(true);

				return size;
			}

			private boolean isVerticalScrollbarBarVisible(Table table,
			                                              Rectangle clientArea) {

				// Get the height of all the rows
				int height = table.getItemCount() * table.getItemHeight();

				// Remove the border from the height
				height += (table.getBorderWidth() * 2);

				return (clientArea.height < height);
			}

			@Override
			protected void layout(Composite composite, boolean flushCache) {

				Rectangle bounds = composite.getClientArea();

				if (bounds.width > 0) {

					Table table = (Table) composite.getChildren()[0];
					table.setBounds(0, 0, bounds.width, bounds.height);

					updateTableColumnWidth(
						table,
						bounds.width,
						isVerticalScrollbarBarVisible(table, bounds)
					);
				}
			}

			private void packColumn(Table table) {

				TableColumn tableColumn = table.getColumn(0);

				table.setRedraw(false);
				table.setLayoutDeferred(true);
				tableColumn.pack();
				table.setLayoutDeferred(false);
				table.setRedraw(true);

				// Cache the column width so it can be used in
				// updateTableColumnWidth() when determine which width to use
				table.setData(
					"column.width",
					Integer.valueOf(tableColumn.getWidth())
				);
			}

			private void updateTableColumnWidth(Table table,
			                                    int width,
			                                    boolean verticalScrollbarBarVisible) {

				// Remove the border from the width
				width -= (table.getBorderWidth() * 2);

				// Remove the scrollbar from the width if it is shown
				if (verticalScrollbarBarVisible) {
					width -= table.getVerticalBar().getSize().x;
				}

				TableColumn tableColumn = table.getColumn(0);

				// Retrieve the cached column width, which is required for
				// determining which width to use (the column width or the
				// calculated width)
				Integer columnWitdh = (Integer) table.getData("column.width");

				// Use the calculated width if the column is smaller, otherwise
				// use the column width and a horizontal scroll bar will show up
				width = Math.max(width, columnWitdh);

				// Adjust the column width
				tableColumn.setWidth(width);
			}
		};
	}

	private ITableLabelProvider buildTableLabelProvider(IBaseLabelProvider labelProvider) {
		return new TableLabelProvider((ILabelProvider) labelProvider);
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
	                                       ModifiablePropertyValueModel<?> selectedItemHolder,
	                                       IBaseLabelProvider labelProvider,
	                                       String helpId) {

		table = addUnmanagedTable(
			addTableContainer(container),
			SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI,
			helpId
		);


		TableModelAdapter model = TableModelAdapter.adapt(
			(ListValueModel<Object>) listHolder,
			getSelectedItemHolder(),
			table,
			buildColumnAdapter(),
			buildTableLabelProvider(labelProvider)
		);

		model.addSelectionChangeListener(buildSelectionListener());

		selectedItemHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildSelectedItemPropertyChangeListener()
		);

		initializeTable(table);
	}

	/**
	 * Initializes the given table, which acts like a list in our case.
	 *
	 * @param table The main widget of this pane
	 */
	protected void initializeTable(Table table) {

		table.setData("column.width", new Integer(0));
		table.setHeaderVisible(false);
		table.setLinesVisible(false);

		Composite container = table.getParent();
		GridData gridData   = (GridData) container.getLayoutData();
		gridData.heightHint = 75;
	}

	/**
	 * The selection has changed, update (1) the selected item holder, (2) the
	 * selection model and (3) the buttons.
	 */
	private void selectionChanged() {
		ModifiablePropertyValueModel<Object> selectedItemHolder = getSelectedItemHolder();
		ObjectListSelectionModel selectionModel = getSelectionModel();
		int selectionCount = this.table.getSelectionCount();

		if (selectionCount == 0) {
			selectedItemHolder.setValue(null);
			selectionModel.clearSelection();
		}
		else if (selectionCount != 1) {
			selectedItemHolder.setValue(null);
			selectionModel.clearSelection();

			for (int index : this.table.getSelectionIndices()) {
				selectionModel.addSelectionInterval(index, index);
			}
		}
		else {
			int selectedIndex = this.table.getSelectionIndex();
			Object selectedItem = getListHolder().get(selectedIndex);

			selectedItemHolder.setValue(selectedItem);
			selectionModel.setSelectedValue(selectedItem);
		}

		updateButtons();
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