/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.swt.events.DisposeAdapter;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;

/**
 * This binding can be used to keep a list widget's contents and selection
 * synchronized with a model. The list widget never alters
 * its contents directly; all changes are driven by the model.
 * <p>
 * <strong>NB:</strong> The selection binding is bi-directional.
 * 
 * @see ListValueModel
 * @see Transformer
 * @see ListWidget
 * @see SelectionBinding
 * @see SWTBindingTools
 */
final class ListWidgetModelBinding<E> {

	// ***** model
	/**
	 * The underlying list model.
	 */
	private final ListValueModel<E> listModel;

	/**
	 * A listener that allows us to synchronize the list widget's contents with
	 * the model list.
	 */
	private final ListChangeListener listListener;

	/**
	 * Cache of model list. Shared with the {@link #selectionBinding}.
	 */
	final ArrayList<E> list = new ArrayList<E>();

	/**
	 * A transformer that converts items in the model list
	 * to strings that can be put in the list widget.
	 */
	private final Transformer<E, String> transformer;

	// ***** UI
	/**
	 * An adapter on the SWT list widget we keep synchronized
	 * with the model list.
	 */
	private final ListWidget<E> listWidget;

	/**
	 * A listener that allows us to stop listening to stuff when the list widget
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener listWidgetDisposeListener;

	// ***** selection
	/**
	 * Widget-specific selection binding.
	 */
	private final SelectionBinding selectionBinding;


	/**
	 * Constructor - all parameters are required.
	 */
	ListWidgetModelBinding(
			ListValueModel<E> listModel,
			Object selectionModel,
			ListWidget<E> listWidget,
			Transformer<E, String> transformer
	) {
		super();
		if ((listModel == null) || (selectionModel == null) || (listWidget == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.listModel = listModel;
		this.listWidget = listWidget;
		this.transformer = transformer;
		this.selectionBinding = listWidget.buildSelectionBinding(this.list, selectionModel);

		this.listListener = this.buildListListener();
		this.listModel.addListChangeListener(ListValueModel.LIST_VALUES, this.listListener);

		this.listWidgetDisposeListener = this.buildListWidgetDisposeListener();
		this.listWidget.addDisposeListener(this.listWidgetDisposeListener);

		this.list.ensureCapacity(this.listModel.size());
		CollectionTools.addAll(this.list, this.listModel);
		this.synchronizeListWidget();
	}


	// ********** initialization **********

	private ListChangeListener buildListListener() {
		return SWTListenerTools.wrap(new ListListener(), this.listWidget.getDisplay());
	}

	/* CU private */ class ListListener
		extends ListChangeAdapter
	{
		@Override
		public void itemsAdded(ListAddEvent event) {
			ListWidgetModelBinding.this.listItemsAdded(event);
		}
		@Override
		public void itemsRemoved(ListRemoveEvent event) {
			ListWidgetModelBinding.this.listItemsRemoved(event);
		}
		@Override
		public void itemsMoved(ListMoveEvent event) {
			ListWidgetModelBinding.this.listItemsMoved(event);
		}
		@Override
		public void itemsReplaced(ListReplaceEvent event) {
			ListWidgetModelBinding.this.listItemsReplaced(event);
		}
		@Override
		public void listCleared(ListClearEvent event) {
			ListWidgetModelBinding.this.listCleared();
		}
		@Override
		public void listChanged(ListChangeEvent event) {
			ListWidgetModelBinding.this.listChanged(event);
		}
	}

	private DisposeListener buildListWidgetDisposeListener() {
		return new ListWidgetDisposeListener();
	}

	/* CU private */ class ListWidgetDisposeListener
		extends DisposeAdapter
	{
		@Override
		public void widgetDisposed(DisposeEvent event) {
			ListWidgetModelBinding.this.listWidgetDisposed();
		}
	}


	// ********** list **********

	/**
	 * Brute force synchronization of list widget with the list.
	 */
	private void synchronizeListWidget() {
		String[] items = new String[this.list.size()];
		int i = 0;
		for (E item : this.list) {
			items[i++] = this.transformer.transform(item);
		}
		this.listWidget.setItems(items);

		// now that the list has changed, notify the selection binding
		this.selectionBinding.listChanged();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	/* CU private */ void listItemsAdded(ListAddEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listItemsAdded_(event);
		}
	}

	private void listItemsAdded_(ListAddEvent event) {
		int i = event.getIndex();
		this.list.ensureCapacity(this.list.size() + event.getItemsSize());
		@SuppressWarnings("unchecked")
		Iterable<E> items = (Iterable<E>) event.getItems();
		for (E item : items) {
			this.list.add(i, item);
			this.listWidget.add(this.transformer.transform(item), i);
			i++;
		}

		// now that the list has changed, we need to synchronize the selection
		this.selectionBinding.listChanged();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	/* CU private */ void listItemsRemoved(ListRemoveEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listItemsRemoved_(event);
		}
	}

	private void listItemsRemoved_(ListRemoveEvent event) {
		int start = event.getIndex();
		int end = start + event.getItemsSize();
		this.list.subList(start, end).clear();
		this.listWidget.remove(start, end - 1);  // widget range is *inclusive*

		// now that the list has changed, we need to synchronize the selection
		this.selectionBinding.listChanged();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	/* CU private */ void listItemsMoved(ListMoveEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listItemsMoved_(event);
		}
	}

	private void listItemsMoved_(ListMoveEvent event) {
		int target = event.getTargetIndex();
		int source = event.getSourceIndex();
		int len = event.getLength();
		int loStart = Math.min(target, source);
		int hiStart = Math.max(target, source);
		int hiEnd = hiStart + len;
		// make a copy of the affected items...
		ArrayList<E> subList = new ArrayList<E>(this.list.subList(loStart, hiEnd));
		String[] subArray = ArrayTools.subArray(this.listWidget.getItems(), loStart, hiEnd);
		// ...move them around...
		int subTarget = target - loStart;
		int subSource = source - loStart;
		subList = ListTools.move(subList, subTarget, subSource, len);
		subArray = ArrayTools.move(subArray, subTarget, subSource, len);
		// ...and then put them back
		int subIndex = 0;
		for (int i = loStart; i < hiEnd; i++) {
			this.list.set(i, subList.get(subIndex));
			this.listWidget.setItem(i, subArray[subIndex]);
			subIndex++;
		}

		// now that the list has changed, we need to synchronize the selection
		this.selectionBinding.listChanged();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	/* CU private */ void listItemsReplaced(ListReplaceEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listItemsReplaced_(event);
		}
	}

	private void listItemsReplaced_(ListReplaceEvent event) {
		int i = event.getIndex();
		@SuppressWarnings("unchecked")
		Iterable<E> newItems = (Iterable<E>) event.getNewItems();
		for (E item : newItems) {
			this.list.set(i, item);
			this.listWidget.setItem(i, this.transformer.transform(item));
			i++;
		}

		// now that the list has changed, we need to synchronize the selection
		this.selectionBinding.listChanged();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	/* CU private */ void listCleared() {
		if ( ! this.listWidget.isDisposed()) {
			this.listCleared_();
		}
	}

	private void listCleared_() {
		this.list.clear();
		this.listWidget.removeAll();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	/* CU private */ void listChanged(ListChangeEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listChanged_(event);
		}
	}

	private void listChanged_(ListChangeEvent event) {
		this.list.clear();
		this.list.ensureCapacity(event.getListSize());
		@SuppressWarnings("unchecked")
		Iterable<E> eventList = (Iterable<E>) event.getList();
		CollectionTools.addAll(this.list, eventList);
		this.synchronizeListWidget();
	}


	// ********** list widget events **********

	/* CU private */ void listWidgetDisposed() {
		// the list widget is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.listWidget.removeDisposeListener(this.listWidgetDisposeListener);
		this.listModel.removeListChangeListener(ListValueModel.LIST_VALUES, this.listListener);
		this.selectionBinding.dispose();
		this.list.clear();
	}


	// ********** misc **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.list);
	}


	// ********** adapter interfaces **********

	/**
	 * Adapter used by the binding to query and manipulate the SWT widget.
	 */
	interface ListWidget<E> {
		/**
		 * Build a selection binding for the widget.
		 */
		SelectionBinding buildSelectionBinding(ArrayList<E> list, Object selectionModel);

		/**
		 * Return the list widget's display.
		 */
		Display getDisplay();

		/**
		 * Return whether the list widget is <em>disposed</em>.
		 */
		boolean isDisposed();

		/**
		 * Add the specified dispose listener to the list widget.
		 */
		void addDisposeListener(DisposeListener listener);

		/**
		 * Remove the specified dispose listener from the list widget.
		 */
		void removeDisposeListener(DisposeListener listener);

		/**
		 * Return the list widget's items.
		 */
		String[] getItems();

		/**
		 * Set the list widget's item at the specified index to the specified item.
		 */
		void setItem(int index, String item);

		/**
		 * Set the list widget's items.
		 */
		void setItems(String[] items);

		/**
		 * Add the specified item to the list widget's items at the specified index.
		 */
		void add(String item, int index);

		/**
		 * Remove the specified range of items from the list widget's items.
		 * Both indices are <em>inclusive</em>.
		 */
		void remove(int start, int end);

		/**
		 * Remove all the items from the list widget.
		 */
		void removeAll();
	}


	/**
	 * Widget-specific selection binding that is controlled by the binding.
	 */
	interface SelectionBinding {
		/**
		 * The list has changed; update the selection if necessary.
		 */
		void listChanged();

		/**
		 * The widget has been disposed; dispose the selection binding.
		 */
		void dispose();
	}
}
