/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTListChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * This javax.swing.ListModel can be used to keep a ListDataListener
 * (e.g. a JList) in synch with a ListValueModel (or a CollectionValueModel).
 * 
 * An instance of this ListModel *must* be supplied with a value model,
 * which is a ListValueModel on the bound list or a CollectionValueModel
 * on the bound collection. This is required - the list (or collection)
 * itself can be null, but the value model that holds it cannot.
 */
public class ListModelAdapter<E>
	extends AbstractListModel<E>
{
	/** A value model on the underlying model list. */
	protected ListValueModel<E> listModel;

	/**
	 * Cache the size of the list for "dramatic" changes.
	 * @see #listChanged()
	 */
	protected int listSize;

	/** A listener that allows us to forward changes made to the underlying model list. */
	protected final ListChangeListener listChangeListener;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Default constructor - initialize stuff.
	 */
	private ListModelAdapter() {
		super();
		this.listSize = 0;
		this.listChangeListener = this.buildListChangeListener();
	}

	/**
	 * Constructor - the list holder is required.
	 */
	public ListModelAdapter(ListValueModel<E> listModel) {
		this();
		this.setModel(listModel);
	}

	/**
	 * Constructor - the collection holder is required.
	 */
	public ListModelAdapter(CollectionValueModel<E> collectionModel) {
		this();
		this.setModel(collectionModel);
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		return new AWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	protected ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				ListModelAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				ListModelAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ListModelAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ListModelAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListClearEvent event) {
				ListModelAdapter.this.listCleared();
			}
			public void listChanged(ListChangeEvent event) {
				ListModelAdapter.this.listChanged();
			}
			@Override
			public String toString() {
				return "list listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** ListModel implementation **********

	public int getSize() {
		return this.listModel.size();
	}

	public E getElementAt(int index) {
		return this.listModel.get(index);
	}

	/**
	 * Extend to start listening to the underlying model list if necessary.
	 */
    @Override
	public void addListDataListener(ListDataListener l) {
		if (this.hasNoListDataListeners()) {
			this.engageModel();
			this.listSize = this.listModel.size();
		}
		super.addListDataListener(l);
	}

	/**
	 * Extend to stop listening to the underlying model list if appropriate.
	 */
    @Override
	public void removeListDataListener(ListDataListener l) {
		super.removeListDataListener(l);
		if (this.hasNoListDataListeners()) {
			this.disengageModel();
			this.listSize = 0;
		}
	}


	// ********** public API **********

	/**
	 * Return the underlying list model.
	 */
	public ListValueModel<E> model() {
		return this.listModel;
	}
	
	/**
	 * Set the underlying list model.
	 */
	public void setModel(ListValueModel<E> listModel) {
		if (listModel == null) {
			throw new NullPointerException();
		}
		boolean hasListeners = this.hasListDataListeners();
		if (hasListeners) {
			this.disengageModel();
		}
		this.listModel = listModel;
		if (hasListeners) {
			this.engageModel();
			this.listChanged();
		}
	}

	/**
	 * Set the underlying collection model.
	 */
	public void setModel(CollectionValueModel<E> collectionModel) {
		this.setModel(new CollectionListValueModelAdapter<>(collectionModel));
	}


	// ********** queries **********

	/**
	 * Return whether this model has no listeners.
	 */
	protected boolean hasNoListDataListeners() {
		return this.getListDataListeners().length == 0;
	}

	/**
	 * Return whether this model has any listeners.
	 */
	protected boolean hasListDataListeners() {
		return ! this.hasNoListDataListeners();
	}


	// ********** behavior **********

	protected void engageModel() {
		this.listModel.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	protected void disengageModel() {
		this.listModel.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}



	// ********** list change support **********

	/**
	 * Items were added to the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsAdded(ListAddEvent event) {
		int start = event.getIndex();
		int end = start + event.getItemsSize() - 1;
		this.fireIntervalAdded(this, start, end);
		this.listSize += event.getItemsSize();
	}

	/**
	 * Items were removed from the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsRemoved(ListRemoveEvent event) {
		int start = event.getIndex();
		int end = start + event.getItemsSize() - 1;
		this.fireIntervalRemoved(this, start, end);
		this.listSize -= event.getItemsSize();
	}

	/**
	 * Items were replaced in the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsReplaced(ListReplaceEvent event) {
		int start = event.getIndex();
		int end = start + event.getItemsSize() - 1;
		this.fireContentsChanged(this, start, end);
	}

	/**
	 * Items were moved in the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsMoved(ListMoveEvent event) {
		int start = Math.min(event.getSourceIndex(), event.getTargetIndex());
		int end = Math.max(event.getSourceIndex(), event.getTargetIndex()) + event.getLength() - 1;
		this.fireContentsChanged(this, start, end);
	}

	/**
	 * The underlying model list was cleared.
	 * Notify listeners of the changes.
	 */
	protected void listCleared() {
		if (this.listSize != 0) {
			this.fireIntervalRemoved(this, 0, this.listSize - 1);
			this.listSize = 0;
		}
	}

	/**
	 * The underlying model list has changed "dramatically".
	 * Notify listeners of the changes.
	 */
	protected void listChanged() {
		if (this.listSize != 0) {
			this.fireIntervalRemoved(this, 0, this.listSize - 1);
		}
		this.listSize = this.listModel.size();
		if (this.listSize != 0) {
			this.fireIntervalAdded(this, 0, this.listSize - 1);
		}
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.listModel);
	}

}
