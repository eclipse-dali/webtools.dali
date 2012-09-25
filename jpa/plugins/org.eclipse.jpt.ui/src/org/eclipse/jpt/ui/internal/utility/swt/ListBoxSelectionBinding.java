/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.utility.swt;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jpt.ui.internal.listeners.SWTCollectionChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritableCollectionValueModel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

/**
 * This binding can be used to keep a list box's selection
 * synchronized with a model. The selection can be modified by either the list
 * box or the model, so changes must be coordinated.
 * 
 * @see ListValueModel
 * @see WritableCollectionValueModel
 * @see List
 * @see SWTTools
 */
@SuppressWarnings("nls")
final class ListBoxSelectionBinding<E>
	implements ListWidgetModelBinding.SelectionBinding
{
	// ***** model
	/**
	 * The underlying list model.
	 */
	private final ListValueModel<E> listModel;

	/**
	 * A writable value model on the underlying model selections.
	 */
	private final WritableCollectionValueModel<E> selectedItemsModel;

	/**
	 * A listener that allows us to synchronize the list box's selection with
	 * the model selections.
	 */
	private final CollectionChangeListener selectedItemsChangeListener;

	// ***** UI
	/**
	 * The list box whose selection we keep synchronized with the model selections.
	 */
	private final List listBox;

	/**
	 * A listener that allows us to synchronize our selected items holder
	 * with the list box's selection.
	 */
	private final SelectionListener listBoxSelectionListener;


	// ********** constructor **********

	/**
	 * Constructor - all parameters are required.
	 */
	ListBoxSelectionBinding(
			ListValueModel<E> listModel,
			WritableCollectionValueModel<E> selectedItemsModel,
			List listBox
	) {
		super();
		if ((listModel == null) || (selectedItemsModel == null) || (listBox == null)) {
			throw new NullPointerException();
		}
		this.listModel = listModel;
		this.selectedItemsModel = selectedItemsModel;
		this.listBox = listBox;

		this.selectedItemsChangeListener = this.buildSelectedItemsChangeListener();
		this.selectedItemsModel.addCollectionChangeListener(CollectionValueModel.VALUES, this.selectedItemsChangeListener);

		this.listBoxSelectionListener = this.buildListBoxSelectionListener();
		this.listBox.addSelectionListener(this.listBoxSelectionListener);
	}


	// ********** initialization **********

	private CollectionChangeListener buildSelectedItemsChangeListener() {
		return new SWTCollectionChangeListenerWrapper(this.buildSelectedItemsChangeListener_());
	}

	private CollectionChangeListener buildSelectedItemsChangeListener_() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				ListBoxSelectionBinding.this.selectedItemsAdded(event);
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				ListBoxSelectionBinding.this.selectedItemsRemoved(event);
			}
			public void collectionCleared(CollectionClearEvent event) {
				ListBoxSelectionBinding.this.selectedItemsCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				ListBoxSelectionBinding.this.selectedItemsChanged(event);
			}
			@Override
			public String toString() {
				return "selected items listener";
			}
		};
	}

	private SelectionListener buildListBoxSelectionListener() {
		return new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				ListBoxSelectionBinding.this.listBoxSelectionChanged(event);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				ListBoxSelectionBinding.this.listBoxDoubleClicked(event);
			}
			@Override
			public String toString() {
				return "list box selection listener";
			}
		};
	}


	// ********** ListWidgetModelBinding.SelectionBinding implementation **********

	/**
	 * Modifying the list box's selected items programmatically does not
	 * trigger a SelectionEvent.
	 * 
	 * Pre-condition: The list-box is not disposed.
	 */
	public void synchronizeListWidgetSelection() {
		int selectedItemsSize = this.selectedItemsModel.size();
		int[] select = new int[selectedItemsSize];
		int i = 0;
		for (E item : this.selectedItemsModel) {
			select[i++] = this.indexOf(item);
		}

		int listSize = this.listModel.size();
		int[] deselect = new int[listSize - selectedItemsSize];
		i = 0;
		for (int j = 0; j < listSize; j++) {
			if ( ! ArrayTools.contains(select, j)) {
				deselect[i++] = j;
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
		this.selectedItemsModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this.selectedItemsChangeListener);
	}


	// ********** selected items **********

	void selectedItemsAdded(CollectionAddEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsAdded_(event);
		}
	}

	/**
	 * Modifying the list box's selected items programmatically does not
	 * trigger a SelectionEvent.
	 */
	private void selectedItemsAdded_(CollectionAddEvent event) {
		int[] indices = new int[event.getItemsSize()];
		int i = 0;
		for (E item : this.getItems(event)) {
			indices[i++] = this.indexOf(item);
		}
		this.listBox.select(indices);
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private Iterable<E> getItems(CollectionAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	void selectedItemsRemoved(CollectionRemoveEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsRemoved_(event);
		}
	}

	/**
	 * Modifying the list box's selected items programmatically does not
	 * trigger a SelectionEvent.
	 */
	private void selectedItemsRemoved_(CollectionRemoveEvent event) {
		int[] indices = new int[event.getItemsSize()];
		int i = 0;
		for (E item : this.getItems(event)) {
			indices[i++] = this.indexOf(item);
		}
		this.listBox.deselect(indices);
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private Iterable<E> getItems(CollectionRemoveEvent event) {
		return (Iterable<E>) event.getItems();
	}

	void selectedItemsCleared(CollectionClearEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsCleared_(event);
		}
	}

	/**
	 * Modifying the list box's selected items programmatically does not
	 * trigger a SelectionEvent.
	 */
	private void selectedItemsCleared_(@SuppressWarnings("unused") CollectionClearEvent event) {
		this.listBox.deselectAll();
	}

	void selectedItemsChanged(CollectionChangeEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.selectedItemsChanged_(event);
		}
	}

	private void selectedItemsChanged_(@SuppressWarnings("unused") CollectionChangeEvent event) {
		this.synchronizeListWidgetSelection();
	}

	private int indexOf(E item) {
		int len = this.listModel.size();
		for (int i = 0; i < len; i++) {
			if (Tools.valuesAreEqual(this.listModel.get(i), item)) {
				return i;
			}
		}
		// see comment in DropDownListBoxSelectionBinding.indexOf(E)
		return -1;
	}


	// ********** list box events **********

	void listBoxSelectionChanged(SelectionEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.listBoxSelectionChanged_(event);
		}
	}

	void listBoxDoubleClicked(SelectionEvent event) {
		if ( ! this.listBox.isDisposed()) {
			this.listBoxSelectionChanged_(event);
		}
	}

	private void listBoxSelectionChanged_(@SuppressWarnings("unused") SelectionEvent event) {
		this.selectedItemsModel.setValues(this.getListBoxSelectedItems());
	}

	private Iterable<E> getListBoxSelectedItems() {
		ArrayList<E> selectedItems = new ArrayList<E>(this.listBox.getSelectionCount());
		for (int selectionIndex : this.listBox.getSelectionIndices()) {
			selectedItems.add(this.listModel.get(selectionIndex));
		}
		return selectedItems;
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.selectedItemsModel);
	}

}
