/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.jpt.common.ui.internal.swt.events.SelectionAdapter;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

/**
 * This binding can be used to keep a list box's selection
 * synchronized with a model.
 * <p>
 * <strong>NB:</strong> This binding is bi-directional. As a result, we modify
 * our {@link #selectedItems cached list} <em>only</em> via
 * the {@link #selectedItemsModel model collection} change events;
 * and we {@link #listBoxSelectionChanged() simply pass through} the list box
 * selection events (by calling
 * {@link ModifiableCollectionValueModel#setValues(Iterable)},
 * which will loop back to us as a collection change event).
 * <p>
 * <strong>NB2:</strong> Changes to the underlying list model can imply changes
 * to the selection collection model. But these changes occur
 * <em>asynchronously</em> (as do their resulting events).
 * If the models are designed correctly, the selection model will be modified,
 * if necessary, whenever the underlying list model changes
 * (typically when elements are removed from the underlying list model).
 * But <em>all</em> changes to the underlying list model can cause changes to
 * the <em>indices</em> of the selected items; so all changes to the underlying
 * list model result in calls to {@link #synchronizeListWidgetSelection()}.
 * Unfortunately, since the <em>selection</em> change event might not have
 * arrived yet, the selection item list can contain elements that are not in
 * the underlying list. So we must gracefully handle missing elements, even
 * though this may hide coding errors (i.e. something that should <em>not</em>
 * happen and should trigger an exception). Likewise, we can receive
 * <em>selection</em> change events before the underlying list model is updated,
 * resulting, again, in temporarily invalid state (which will be rectified once
 * the underlying list model is updated and the binding calls
 * {@link #synchronizeListWidgetSelection()}).
 * 
 * @see ModifiableCollectionValueModel
 * @see List
 * @see SWTBindingTools
 */
final class ListBoxSelectionBinding<E>
	implements ListWidgetModelBinding.SelectionBinding
{
	// ***** model
	/**
	 * The underlying list (maintained by {@link ListWidgetModelBinding}).
	 */
	private final ArrayList<E> list;

	/**
	 * A modifiable value model on the underlying model selections.
	 */
	private final ModifiableCollectionValueModel<E> selectedItemsModel;

	/**
	 * Cache of model selections.
	 */
	private final ArrayList<E> selectedItems = new ArrayList<E>();

	/**
	 * A listener that allows us to synchronize the list box's selection with
	 * the model selections.
	 */
	private final CollectionChangeListener selectedItemsListener;

	// ***** UI
	/**
	 * The list box whose selection we keep synchronized
	 * with the model selections.
	 */
	private final List listBox;

	/**
	 * A listener that allows us to synchronize our selected items model
	 * with the list box's selection.
	 */
	private final SelectionListener listBoxSelectionListener;


	/**
	 * Constructor - all parameters are required.
	 */
	ListBoxSelectionBinding(
			ArrayList<E> list,
			ModifiableCollectionValueModel<E> selectedItemsModel,
			List listBox
	) {
		super();
		if ((list == null) || (selectedItemsModel == null) || (listBox == null)) {
			throw new NullPointerException();
		}
		this.list = list;
		this.selectedItemsModel = selectedItemsModel;
		this.listBox = listBox;

		this.selectedItemsListener = this.buildSelectedItemsListener();
		this.selectedItemsModel.addCollectionChangeListener(CollectionValueModel.VALUES, this.selectedItemsListener);

		this.listBoxSelectionListener = this.buildListBoxSelectionListener();
		this.listBox.addSelectionListener(this.listBoxSelectionListener);

		this.selectedItems.ensureCapacity(this.selectedItemsModel.size());
		CollectionTools.addAll(this.selectedItems, this.selectedItemsModel);
	}


	// ********** initialization **********

	private CollectionChangeListener buildSelectedItemsListener() {
		return SWTListenerTools.wrap(new SelectedItemsListener(), this.listBox);
	}

	/* CU private */ class SelectedItemsListener
		extends CollectionChangeAdapter
	{
		@Override
		public void itemsAdded(CollectionAddEvent event) {
			ListBoxSelectionBinding.this.selectedItemsAdded(event);
		}
		@Override
		public void itemsRemoved(CollectionRemoveEvent event) {
			ListBoxSelectionBinding.this.selectedItemsRemoved(event);
		}
		@Override
		public void collectionCleared(CollectionClearEvent event) {
			ListBoxSelectionBinding.this.selectedItemsCleared();
		}
		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			ListBoxSelectionBinding.this.selectedItemsChanged(event);
		}
	}

	private SelectionListener buildListBoxSelectionListener() {
		return new ListBoxSelectionListener();
	}

	/* CU private */ class ListBoxSelectionListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent event) {
			ListBoxSelectionBinding.this.listBoxSelectionChanged();
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			ListBoxSelectionBinding.this.listBoxDoubleClicked();
		}
	}


	// ********** ListWidgetModelBinding.SelectionBinding implementation **********

	/**
	 * <strong>NB:</strong> The elements in the selection model may be out of
	 * sync with the underlying list model. (See the class comment.)
	 * <p>
	 * Modifying the list box's selected items programmatically does not
	 * trigger a {@link SelectionEvent}.
	 * <p>
	 * Pre-condition: The list-box is not disposed.
	 */
	public void synchronizeListWidgetSelection() {
		int selectedItemsSize = this.selectedItems.size();
		int[] select = ArrayTools.EMPTY_INT_ARRAY;
		if (selectedItemsSize > 0) {
			select = new int[selectedItemsSize];
			int i = 0;
			for (E item : this.selectedItems) {
				select[i++] = this.indexOf(item);
			}
		}

		int listSize = this.list.size();
		int[] deselect = ArrayTools.EMPTY_INT_ARRAY;
		if (listSize > 0) {
			deselect = ArrayTools.fill(new int[listSize], -1);
			int i = 0;
			for (int j = 0; j < listSize; j++) {
				if ( ! ArrayTools.contains(select, j)) {
					deselect[i++] = j;
				}
			}
		}

		int[] old = ArrayTools.sort(this.listBox.getSelectionIndices());
		select = ArrayTools.sort(select);
		if ( ! Arrays.equals(select, old)) {
			this.listBox.deselect(deselect);
			this.listBox.select(select);
		}
	}

	public void dispose() {
		this.listBox.removeSelectionListener(this.listBoxSelectionListener);
		this.selectedItemsModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this.selectedItemsListener);
		this.selectedItems.clear();
	}


	// ********** selected items **********

	/* CU private */ void selectedItemsAdded(CollectionAddEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsAdded_(event);
		}
	}

	/**
	 * Modifying the list box's selected items programmatically does not
	 * trigger a {@link SelectionEvent}.
	 */
	private void selectedItemsAdded_(CollectionAddEvent event) {
		@SuppressWarnings("unchecked")
		Iterable<E> items = (Iterable<E>) event.getItems();
		this.selectedItems.ensureCapacity(this.selectedItems.size() + event.getItemsSize());
		CollectionTools.addAll(this.selectedItems, items);

		int[] indices = new int[event.getItemsSize()];
		int i = 0;
		for (E item : items) {
			indices[i++] = this.indexOf(item);
		}
		this.listBox.select(indices);
	}

	/* CU private */ void selectedItemsRemoved(CollectionRemoveEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsRemoved_(event);
		}
	}

	/**
	 * Modifying the list box's selected items programmatically does not
	 * trigger a {@link SelectionEvent}.
	 */
	private void selectedItemsRemoved_(CollectionRemoveEvent event) {
		@SuppressWarnings("unchecked")
		Iterable<E> items = (Iterable<E>) event.getItems();
		CollectionTools.removeAll(this.selectedItems, items);

		int[] indices = new int[event.getItemsSize()];
		int i = 0;
		for (E item : items) {
			indices[i++] = this.indexOf(item);
		}
		this.listBox.deselect(indices);
	}

	/* CU private */ void selectedItemsCleared() {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsCleared_();
		}
	}

	/**
	 * Modifying the list box's selected items programmatically does not
	 * trigger a {@link SelectionEvent}.
	 */
	private void selectedItemsCleared_() {
		this.selectedItems.clear();
		this.listBox.deselectAll();
	}

	/* CU private */ void selectedItemsChanged(CollectionChangeEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsChanged_(event);
		}
	}

	private void selectedItemsChanged_(CollectionChangeEvent event) {
		this.selectedItems.clear();
		this.selectedItems.ensureCapacity(event.getCollectionSize());
		@SuppressWarnings("unchecked")
		Iterable<E> eventCollection = (Iterable<E>) event.getCollection();
		CollectionTools.addAll(this.selectedItems, eventCollection);
		this.synchronizeListWidgetSelection();
	}

	/**
	 * <strong>NB:</strong> an index of <code>-1</code> is ignored by
	 * {@link List} (lucky for us).
	 */
	private int indexOf(E item) {
		int i = 0;
		for (E each : this.list) {
			if (ObjectTools.equals(each, item)) {
				return i;
			}
			i++;
		}
		return -1;
	}


	// ********** list box events **********

	/* CU private */ void listBoxSelectionChanged() {
		this.selectedItemsModel.setValues(this.getListBoxSelectedItems());
	}

	/* CU private */ void listBoxDoubleClicked() {
		this.listBoxSelectionChanged();
	}

	private Iterable<E> getListBoxSelectedItems() {
		ArrayList<E> lbSelectedItems = new ArrayList<E>(this.listBox.getSelectionCount());
		for (int selectionIndex : this.listBox.getSelectionIndices()) {
			lbSelectedItems.add(this.list.get(selectionIndex));
		}
		return lbSelectedItems;
	}


	// ********** misc **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.selectedItems);
	}
}
