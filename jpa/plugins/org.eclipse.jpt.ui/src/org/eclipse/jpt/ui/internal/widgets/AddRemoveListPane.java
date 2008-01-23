/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this
 *  distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.swt.ListBoxModelAdapter;
import org.eclipse.jpt.ui.internal.swt.ListBoxModelAdapter.SelectionChangeEvent;
import org.eclipse.jpt.ui.internal.swt.ListBoxModelAdapter.SelectionChangeListener;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

/**
 * This implementation of the <code>AddRemovePane</code> uses a <code>List</code>
 * as its main widget.
 *
 * @version 2.0
 * @since 1.0
 */
public class AddRemoveListPane<T extends Model> extends AddRemovePane<T>
{
	/**
	 * The main widget of this add/remove pane.
	 */
	private List list;

	/**
	 * Creates a new <code>AddRemoveListPane</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	public AddRemoveListPane(BaseJpaController<? extends T> parentController,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel/*<?>*/ listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider) {

		super(parentController,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider);
	}

	/**
	 * Creates a new <code>AddRemoveListPane</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	public AddRemoveListPane(BaseJpaController<? extends T> parentController,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel/*<?>*/ listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider,
	                         String helpId) {

		super(parentController,
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
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	public AddRemoveListPane(BaseJpaController<?> parentController,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel/*<?>*/ listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider) {

		super(parentController,
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
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	public AddRemoveListPane(BaseJpaController<?> parentController,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent,
	                         Adapter adapter,
	                         ListValueModel/*<?>*/ listHolder,
	                         WritablePropertyValueModel<?> selectedItemHolder,
	                         ILabelProvider labelProvider,
	                         String helpId) {

		super(parentController,
		      subjectHolder,
		      parent,
		      adapter,
		      listHolder,
		      selectedItemHolder,
		      labelProvider,
		      helpId);
	}

	private WritablePropertyValueModel<String> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<String>();
	}

	private SelectionChangeListener<Object> buildSelectionListener() {
		return new SelectionChangeListener<Object>() {
			public void selectionChanged(SelectionChangeEvent<Object> e) {
				AddRemoveListPane.this.selectionChanged();
			}
		};
	}

	private StringConverter<Object> buildStringConverter(final ILabelProvider labelProvider) {
		return new StringConverter<Object>() {
			public String convertToString(Object item) {
				return labelProvider.getText(item);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeMainComposite(Composite container,
	                                       Adapter adapter,
	                                       ListValueModel/*<?>*/ listHolder,
	                                       WritablePropertyValueModel<?> selectedItemHolder,
	                                       ILabelProvider labelProvider,
	                                       String helpId) {

		list = buildList(container, buildSelectedItemHolder());

		if (helpId != null) {
			helpSystem().setHelp(list, helpId);
		}

		ListBoxModelAdapter<Object> listModel = ListBoxModelAdapter.adapt(
			listHolder,
			new SimplePropertyValueModel<Object>(),
			list,
			buildStringConverter(labelProvider)
		);

		listModel.addSelectionChangeListener(buildSelectionListener());
	}

	/**
	 * The selection has changed, update (1) the selected item holder, (2) the
	 * selection model and (3) the buttons.
	 */
	private void selectionChanged() {

		WritablePropertyValueModel<Object> selectedItemHolder = getSelectedItemHolder();
		ObjectListSelectionModel selectionModel = getSelectionModel();
		int selectionCount = list.getSelectionCount();

		if (selectionCount == 0) {
			selectedItemHolder.setValue(null);
			selectionModel.clearSelection();
		}
		else if (selectionCount != 1) {
			selectedItemHolder.setValue(null);
			selectionModel.clearSelection();

			for (int index : list.getSelectionIndices()) {
				selectionModel.addSelectionInterval(index, index);
			}
		}
		else {
			int selectedIndex = list.getSelectionIndex();
			Object selectedItem = getListHolder().get(selectedIndex);

			selectedItemHolder.setValue(selectedItem);
			selectionModel.setSelectedValue(selectedItem);
		}

		updateButtons();
	}
}
