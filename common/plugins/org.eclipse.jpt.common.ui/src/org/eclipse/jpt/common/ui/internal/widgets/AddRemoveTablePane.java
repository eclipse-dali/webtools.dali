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
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jpt.common.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.common.ui.internal.swt.TableModelAdapter;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * This implementation of the <code>AddRemovePane</code> uses a <code>Table</code>
 * as its main widget.
 * <p>
 * Here the layout of this pane:
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
public abstract class AddRemoveTablePane<T extends Model, E extends Object> extends AddRemovePane<T, E>
{

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
	public AddRemoveTablePane(Pane<? extends T> parentPane,
	                          Composite parent,
	                          Adapter<E> adapter,
	                          ListValueModel<E> listHolder,
	                          ModifiableCollectionValueModel<E> selectedItemsHolder,
	                          ITableLabelProvider labelProvider) {

		super(parentPane,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemsHolder,
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
	public AddRemoveTablePane(Pane<? extends T> parentPane,
	                          Composite parent,
	                          Adapter<E> adapter,
	                          ListValueModel<E> listHolder,
	                          ModifiableCollectionValueModel<E> selectedItemsHolder,
	                          ITableLabelProvider labelProvider,
	                          String helpId) {

		super(parentPane,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemsHolder,
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
	public AddRemoveTablePane(Pane<?> parentPane,
	                          PropertyValueModel<? extends T> subjectHolder,
	                          Composite parent,
	                          Adapter<E> adapter,
	                          ListValueModel<E> listHolder,
	                          ModifiableCollectionValueModel<E> selectedItemsHolder,
	                          ITableLabelProvider labelProvider) {

		super(parentPane,
		      subjectHolder,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemsHolder,
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
	public AddRemoveTablePane(Pane<?> parentPane,
	                          PropertyValueModel<? extends T> subjectHolder,
	                          Composite parent,
	                          Adapter<E> adapter,
	                          ListValueModel<E> listHolder,
	                          ModifiableCollectionValueModel<E> selectedItemsHolder,
	                          ITableLabelProvider labelProvider,
	                          String helpId) {

		super(parentPane,
		      subjectHolder,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemsHolder,
		      labelProvider,
		      helpId);
	}

	protected abstract ColumnAdapter<E> buildColumnAdapter();

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getMainControl() {
		return this.table;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void initializeMainComposite(Composite container,
	                                       Adapter<E> adapter,
	                                       ListValueModel<?> listHolder,
	                                       ModifiableCollectionValueModel<E> selectedItemsHolder,
	                                       IBaseLabelProvider labelProvider,
	                                       String helpId)
	{
		this.table = addUnmanagedTable(container, helpId);
		this.table.setHeaderVisible(true);

		TableModelAdapter.adapt(
			(ListValueModel<E>) listHolder,
			selectedItemsHolder,
			this.table,
			buildColumnAdapter(),
			(ITableLabelProvider) labelProvider
		);
	}

}
