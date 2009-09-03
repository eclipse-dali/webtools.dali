/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.utility.swt;

import java.util.ArrayList;

import org.eclipse.jpt.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

/**
 * This binding can be used to keep a list widget's contents
 * synchronized with a model. The list widget never alters
 * its contents directly; all changes are driven by the model.
 * 
 * @see ListValueModel
 * @see StringConverter
 * @see ListWidget
 * @see SelectionBinding
 * @see SWTTools
 */
@SuppressWarnings("nls")
final class ListWidgetModelBinding<E> {

	// ***** model
	/**
	 * A value model on the underlying model list.
	 */
	private final ListValueModel<E> listHolder;

	/**
	 * A listener that allows us to synchronize the list widget's contents with
	 * the model list.
	 */
	private final ListChangeListener listChangeListener;

	/**
	 * A converter that converts items in the model list
	 * to strings that can be put in the list widget.
	 */
	private final StringConverter<E> stringConverter;

	// ***** UI
	/**
	 * An adapter on the list widget we keep synchronized with the model list.
	 */
	private final ListWidget listWidget;

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


	// ********** constructor **********

	/**
	 * Constructor - all parameters are required.
	 */
	ListWidgetModelBinding(
			ListValueModel<E> listHolder,
			ListWidget listWidget,
			StringConverter<E> stringConverter,
			SelectionBinding selectionBinding
	) {
		super();
		if ((listHolder == null) || (listWidget == null) || (stringConverter == null) || (selectionBinding == null)) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.listWidget = listWidget;
		this.stringConverter = stringConverter;
		this.selectionBinding = selectionBinding;

		this.listChangeListener = this.buildListChangeListener();
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);

		this.listWidgetDisposeListener = this.buildListWidgetDisposeListener();
		this.listWidget.addDisposeListener(this.listWidgetDisposeListener);

		this.synchronizeListWidget();
	}


	// ********** initialization **********

	private ListChangeListener buildListChangeListener() {
		return new SWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	private ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				ListWidgetModelBinding.this.listItemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				ListWidgetModelBinding.this.listItemsRemoved(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ListWidgetModelBinding.this.listItemsMoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ListWidgetModelBinding.this.listItemsReplaced(event);
			}
			public void listCleared(ListClearEvent event) {
				ListWidgetModelBinding.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListWidgetModelBinding.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list listener";
			}
		};
	}

	private DisposeListener buildListWidgetDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				ListWidgetModelBinding.this.listWidgetDisposed(event);
			}
			@Override
			public String toString() {
				return "list widget dispose listener";
			}
		};
	}


	// ********** list **********

	/**
	 * Brute force synchronization of list widget with the model list.
	 */
	private void synchronizeListWidget() {
		if ( ! this.listWidget.isDisposed()) {
			this.synchronizeListWidget_();
		}
	}

	private void synchronizeListWidget_() {
		ArrayList<String> items = new ArrayList<String>(this.listHolder.size());
		for (E item : this.listHolder) {
			items.add(this.convert(item));
		}
		this.listWidget.setItems(items.toArray(new String[items.size()]));

		// now that the list has changed, we need to synch the selection
		this.selectionBinding.synchronizeListWidgetSelection();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	void listItemsAdded(ListAddEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listItemsAdded_(event);
		}
	}

	private void listItemsAdded_(ListAddEvent event) {
		int i = event.getIndex();
		for (E item : this.getItems(event)) {
			this.listWidget.add(this.convert(item), i++);
		}

		// now that the list has changed, we need to synch the selection
		this.selectionBinding.synchronizeListWidgetSelection();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private Iterable<E> getItems(ListAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	void listItemsRemoved(ListRemoveEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listItemsRemoved_(event);
		}
	}

	private void listItemsRemoved_(ListRemoveEvent event) {
		this.listWidget.remove(event.getIndex(), event.getIndex() + event.getItemsSize() - 1);

		// now that the list has changed, we need to synch the selection
		this.selectionBinding.synchronizeListWidgetSelection();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	void listItemsMoved(ListMoveEvent event) {
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
		// make a copy of the affected items...
		String[] subArray = ArrayTools.subArray(this.listWidget.getItems(), loStart, hiStart + len);
		// ...move them around...
		subArray = ArrayTools.move(subArray, target - loStart, source - loStart, len);
		// ...and then put them back
		int i = loStart;
		for (String item : subArray) {
			this.listWidget.setItem(i++, item);
		}

		// now that the list has changed, we need to synch the selection
		this.selectionBinding.synchronizeListWidgetSelection();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	void listItemsReplaced(ListReplaceEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listItemsReplaced_(event);
		}
	}

	private void listItemsReplaced_(ListReplaceEvent event) {
		int i = event.getIndex();
		for (E item : this.getNewItems(event)) {
			this.listWidget.setItem(i++, this.convert(item));
		}

		// now that the list has changed, we need to synch the selection
		this.selectionBinding.synchronizeListWidgetSelection();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private Iterable<E> getNewItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getNewItems();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	void listCleared(ListClearEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listCleared_(event);
		}
	}

	private void listCleared_(@SuppressWarnings("unused") ListClearEvent event) {
		this.listWidget.removeAll();
	}

	/**
	 * The model has changed - synchronize the list widget.
	 */
	void listChanged(ListChangeEvent event) {
		if ( ! this.listWidget.isDisposed()) {
			this.listChanged_(event);
		}
	}

	private void listChanged_(@SuppressWarnings("unused") ListChangeEvent event) {
		this.synchronizeListWidget_();
	}

	/**
	 * Use the string converter to convert the specified item to a
	 * string that can be added to the list widget.
	 */
	private String convert(E item) {
		return this.stringConverter.convertToString(item);
	}


	// ********** list widget events **********

	void listWidgetDisposed(@SuppressWarnings("unused") DisposeEvent event) {
		// the list widget is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.listWidget.removeDisposeListener(this.listWidgetDisposeListener);
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		this.selectionBinding.dispose();
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listHolder);
	}


	// ********** adapter interfaces **********

	/**
	 * Adapter used by the list widget model binding to query and manipulate
	 * the widget.
	 */
	interface ListWidget {

		/**
		 * Return whether the list widget is "disposed".
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
		 */
		void remove(int start, int end);

		/**
		 * Remove all the items from the list widget.
		 */
		void removeAll();

	}


	/**
	 * Widget-specific selection binding that is controlled by the list widget
	 * model binding.
	 */
	interface SelectionBinding {

		/**
		 * Synchronize the selection binding's widget with the selection model.
		 * <p>
		 * Pre-condition: The widget is not disposed.
		 */
		void synchronizeListWidgetSelection();

		/**
		 * The widget has been disposed; dispose the selection binding.
		 */
		void dispose();


		/**
		 * Useful for list boxes that ignore the selection.
		 */
		final class Null implements SelectionBinding {
			public static final SelectionBinding INSTANCE = new Null();
			public static SelectionBinding instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public void synchronizeListWidgetSelection() {
				// do nothing
			}
			public void dispose() {
				// do nothing
			}
			@Override
			public String toString() {
				return "SelectionBinding.Null"; //$NON-NLS-1$
			}
		}

	}

}
