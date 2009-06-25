/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.swing;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.listener.awt.AWTListChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * This javax.swing.ListModel can be used to keep a ListDataListener
 * (e.g. a JList) in synch with a ListValueModel (or a CollectionValueModel).
 * 
 * An instance of this ListModel *must* be supplied with a value model,
 * which is a ListValueModel on the bound list or a CollectionValueModel
 * on the bound collection. This is required - the list (or collection)
 * itself can be null, but the value model that holds it cannot.
 */
public class ListModelAdapter
	extends AbstractListModel
{
	/** A value model on the underlying model list. */
	protected ListValueModel<?> listHolder;

	/**
	 * Cache the size of the list for "dramatic" changes.
	 * @see #listChanged(ListChangeEvent)
	 */
	protected int listSize;

	/** A listener that allows us to forward changes made to the underlying model list. */
	protected final ListChangeListener listChangeListener;


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
	public ListModelAdapter(ListValueModel<?> listHolder) {
		this();
		this.setModel(listHolder);
	}

	/**
	 * Constructor - the collection holder is required.
	 */
	public ListModelAdapter(CollectionValueModel<?> collectionHolder) {
		this();
		this.setModel(collectionHolder);
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		return new AWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	protected ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				ListModelAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(ListChangeEvent event) {
				ListModelAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				ListModelAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				ListModelAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListChangeEvent event) {
				ListModelAdapter.this.listCleared();
			}
			public void listChanged(ListChangeEvent event) {
				ListModelAdapter.this.listChanged();
			}
			@Override
			public String toString() {
				return "list listener";
			}
		};
	}


	// ********** ListModel implementation **********

	public int getSize() {
		return this.listHolder.size();
	}

	public Object getElementAt(int index) {
		return this.listHolder.get(index);
	}

	/**
	 * Extend to start listening to the underlying model list if necessary.
	 */
    @Override
	public void addListDataListener(ListDataListener l) {
		if (this.hasNoListDataListeners()) {
			this.engageModel();
			this.listSize = this.listHolder.size();
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
	public ListValueModel<?> model() {
		return this.listHolder;
	}
	
	/**
	 * Set the underlying list model.
	 */
	public void setModel(ListValueModel<?> listHolder) {
		if (listHolder == null) {
			throw new NullPointerException();
		}
		boolean hasListeners = this.hasListDataListeners();
		if (hasListeners) {
			this.disengageModel();
		}
		this.listHolder = listHolder;
		if (hasListeners) {
			this.engageModel();
			this.listChanged();
		}
	}

	/**
	 * Set the underlying collection model.
	 */
	@SuppressWarnings("unchecked")
	public void setModel(CollectionValueModel<?> collectionHolder) {
		this.setModel(new CollectionListValueModelAdapter(collectionHolder));
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
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	protected void disengageModel() {
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}



	// ********** list change support **********

	/**
	 * Items were added to the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsAdded(ListChangeEvent event) {
		int start = event.getIndex();
		int end = start + event.getItemsSize() - 1;
		this.fireIntervalAdded(this, start, end);
		this.listSize += event.getItemsSize();
	}

	/**
	 * Items were removed from the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsRemoved(ListChangeEvent event) {
		int start = event.getIndex();
		int end = start + event.getItemsSize() - 1;
		this.fireIntervalRemoved(this, start, end);
		this.listSize -= event.getItemsSize();
	}

	/**
	 * Items were replaced in the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsReplaced(ListChangeEvent event) {
		int start = event.getIndex();
		int end = start + event.getItemsSize() - 1;
		this.fireContentsChanged(this, start, end);
	}

	/**
	 * Items were moved in the underlying model list.
	 * Notify listeners of the changes.
	 */
	protected void itemsMoved(ListChangeEvent event) {
		int start = Math.min(event.getSourceIndex(), event.getTargetIndex());
		int end = Math.max(event.getSourceIndex(), event.getTargetIndex()) + event.getMoveLength() - 1;
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
		this.listSize = this.listHolder.size();
		if (this.listSize != 0) {
			this.fireIntervalAdded(this, 0, this.listSize - 1);
		}
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listHolder);
	}

}
