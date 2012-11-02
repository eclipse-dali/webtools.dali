/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
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
 * This adapter can be used to keep an AbstractListViewer
 * (e.g. a ListViewer or ComboViewer) in synch with a ListValueModel
 * (or a CollectionValueModel).
 */
public class StructuredContentProviderAdapter
	implements IStructuredContentProvider
{
	/** The underlying model list. */
	protected ListValueModel listHolder;

	/** The list viewer we keep in synch with the model list. */
	protected final AbstractListViewer listViewer;

	/** A listener that allows us to forward changes made to the underlying model list. */
	protected final ListChangeListener listChangeListener;


	// ********** static **********

	/**
	 * Adapt the specified list viewer to the specified list holder so they
	 * stay in synch.
	 */
	public static StructuredContentProviderAdapter adapt(AbstractListViewer listViewer, ListValueModel listHolder) {
		// we need only construct the adapter and it will hook up to the list viewer etc.
		return new StructuredContentProviderAdapter(listViewer, listHolder);
	}

	/**
	 * Adapt the specified list viewer to the specified list holder so they
	 * stay in synch.
	 */
	public static StructuredContentProviderAdapter adapt(AbstractListViewer listViewer, CollectionValueModel collectionHolder) {
		// we need only construct the adapter and it will hook up to the list viewer etc.
		return new StructuredContentProviderAdapter(listViewer, collectionHolder);
	}


	// ********** constructors **********

	/**
	 * Constructor.
	 */
	protected StructuredContentProviderAdapter(AbstractListViewer listViewer, ListValueModel listHolder) {
		super();
		this.listChangeListener = this.buildListChangeListener();
		this.listViewer = listViewer;
		this.listViewer.setContentProvider(this);
		// the list viewer will call back to #inputChanged(Viewer, Object, Object)
		this.listViewer.setInput(listHolder);
	}

	/**
	 * Constructor.
	 */
	protected StructuredContentProviderAdapter(AbstractListViewer listViewer, CollectionValueModel collectionHolder) {
		this(listViewer, new CollectionListValueModelAdapter(collectionHolder));
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		return new SWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	protected ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent e) {
				StructuredContentProviderAdapter.this.itemsAdded(e);
			}
			public void itemsRemoved(ListRemoveEvent e) {
				StructuredContentProviderAdapter.this.itemsRemoved(e);
			}
			public void itemsReplaced(ListReplaceEvent e) {
				StructuredContentProviderAdapter.this.itemsReplaced(e);
			}
			public void itemsMoved(ListMoveEvent e) {
				StructuredContentProviderAdapter.this.itemsMoved(e);
			}
			public void listCleared(ListClearEvent e) {
				StructuredContentProviderAdapter.this.listCleared();
			}
			public void listChanged(ListChangeEvent e) {
				StructuredContentProviderAdapter.this.listChanged();
			}
			@Override
			public String toString() {
				return "list listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** IStructuredContentProvider implementation **********

	public Object[] getElements(Object inputElement) {
		if (inputElement != this.listHolder) {
			throw new IllegalArgumentException("invalid input element: " + inputElement); //$NON-NLS-1$
		}
		return this.listHolder.toArray();
	}

	/**
	 * This is called by the list viewer, so don't update the list viewer here.
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (viewer != this.listViewer) {
			throw new IllegalArgumentException("invalid viewer: " + viewer); //$NON-NLS-1$
		}
		if (oldInput != this.listHolder) {
			throw new IllegalArgumentException("invalid old input: " + oldInput); //$NON-NLS-1$
		}
		this.modelChanged((ListValueModel) oldInput, (ListValueModel) newInput);
	}

	public void dispose() {
		// do nothing - listeners should've already been removed in #inputChanged(Viewer, Object, Object)
	}


	// ********** internal methods **********

	protected void modelChanged(ListValueModel oldModel, ListValueModel newModel) {
		if (oldModel != null) {
			this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		}
		this.listHolder = newModel;
		if (newModel != null) {
			this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		}
	}


	// ********** public API **********

	/**
	 * Return the underlying list model.
	 */
	public ListValueModel model() {
		return this.listHolder;
	}

	/**
	 * Set the underlying list model.
	 */
	public void setModel(ListValueModel listHolder) {
		// the list viewer will call back to #inputChanged(Viewer, Object, Object)
		this.listViewer.setInput(listHolder);
	}

	/**
	 * Set the underlying collection model.
	 */
	public void setModel(CollectionValueModel collectionHolder) {
		this.setModel(new CollectionListValueModelAdapter(collectionHolder));
	}


	// ********** list change support **********

	/**
	 * Items were added to the underlying model list.
	 * Synchronize the list viewer.
	 */
	protected void itemsAdded(ListAddEvent e) {
		int i = e.getIndex();
		for (Object item : e.getItems()) {
			this.listViewer.insert(item, i++);
		}
	}

	/**
	 * Items were removed from the underlying model list.
	 * Synchronize the list viewer.
	 */
	protected void itemsRemoved(ListRemoveEvent e) {
		this.listViewer.remove(ArrayTools.array(e.getItems(), e.getItemsSize()));
	}

	/**
	 * Items were replaced in the underlying model list.
	 * Synchronize the list viewer.
	 */
	protected void itemsReplaced(ListReplaceEvent e) {
		this.listViewer.remove(ArrayTools.array(e.getOldItems(), e.getItemsSize()));
		int i = e.getIndex();
		for (Object item : e.getNewItems()) {
			this.listViewer.insert(item, i++);
		}
	}

	/**
	 * Items were moved in the underlying model list.
	 * Synchronize the list viewer.
	 */
	protected void itemsMoved(ListMoveEvent e) {
		int len = e.getLength();
		Object[] items = new Object[len];
		int offset = e.getSourceIndex();
		for (int i = 0; i < len; i++) {
			items[i] = this.listHolder.get(offset + i);
		}
		this.listViewer.remove(items);

		offset = e.getTargetIndex();
		for (int i = 0; i < len; i++) {
			this.listViewer.insert(items[i], offset + i);
		}
	}

	/**
	 * The underlying model list was cleared.
	 * Synchronize the list viewer.
	 */
	protected void listCleared() {
		this.listViewer.refresh();
	}

	/**
	 * The underlying model list has changed "dramatically".
	 * Synchronize the list viewer.
	 */
	protected void listChanged() {
		this.listViewer.refresh();
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.listHolder);
	}

}
