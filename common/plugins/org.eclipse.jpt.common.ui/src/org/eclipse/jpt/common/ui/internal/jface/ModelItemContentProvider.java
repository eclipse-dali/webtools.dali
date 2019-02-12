/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.util.ConcurrentModificationException;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.ui.jface.ItemContentProvider;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * This abstract class provides the behavior common to item structure and tree
 * content providers (i.e. maintaining a list of an item's elements/children).
 * 
 * @param <M> the type of the provider's manager
 */
abstract class ModelItemContentProvider<M extends ItemContentProvider.Manager>
	implements ItemContentProvider
{
	/* private-protected */ final Object item;

	private final CollectionValueModel<?> childrenModel;
	private final CollectionChangeListener childrenListener;

	/**
	 * The children must be accessed on the manager's UI thread.
	 * This is <code>null</code> when the provider is disposed.
	 */
	/* private-protected */ Object[] children;

	/* private-protected */ final M manager;

	/* private-protected */ static final Iterable<?> EMPTY_ITERABLE = EmptyIterable.instance();


	ModelItemContentProvider(Object item, CollectionValueModel<?> childrenModel, M manager) {
		super();
		if (item == null) {
			throw new NullPointerException();
		}
		this.item = item;
		if (childrenModel == null) {
			throw new NullPointerException();
		}
		this.childrenModel = childrenModel;
		if (manager == null) {
			throw new NullPointerException();
		}
		this.manager = manager;

		this.childrenListener = this.buildChildrenListener();
		this.childrenModel.addCollectionChangeListener(CollectionValueModel.VALUES, this.childrenListener);
		this.children = this.buildChildren();
	}
	


	// ********** children **********

	/* private-protected */ Object[] getChildren() {
		return this.children;
	}

	private CollectionChangeListener buildChildrenListener() {
		return SWTListenerTools.wrap(this.buildChildrenListener_(), this.manager.getViewer());
	}

	private CollectionChangeListener buildChildrenListener_() {
		return new ChildrenListener();
	}

	/* CU private */ class ChildrenListener
		extends CollectionChangeAdapter
	{
		@Override
		public void itemsAdded(CollectionAddEvent event) {
			ModelItemContentProvider.this.childrenAdded(event.getItems());
		}

		@Override
		public void itemsRemoved(CollectionRemoveEvent event) {
			ModelItemContentProvider.this.childrenRemoved(event.getItems());
		}

		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			ModelItemContentProvider.this.childrenChanged();
		}

		@Override
		public void collectionCleared(CollectionClearEvent event) {
			ModelItemContentProvider.this.childrenCleared();
		}
	}

	/* CU private */ void childrenAdded(Iterable<?> addedChildren) {
		if (this.isAlive()) {
			this.childrenAdded_(addedChildren);
		}
	}

	private void childrenAdded_(Iterable<?> addedChildren) {
		this.children = ArrayTools.addAll(this.children, addedChildren);
		this.notifyManager(addedChildren, EMPTY_ITERABLE);
	}

	/* CU private */ void childrenRemoved(Iterable<?> removedChildren) {
		if (this.isAlive()) {
			this.childrenRemoved_(removedChildren);
		}
	}

	private void childrenRemoved_(Iterable<?> removedChildren) {
		this.children = ArrayTools.removeAll(this.children, removedChildren);
		this.notifyManager(EMPTY_ITERABLE, removedChildren);
	}

	/* CU private */ void childrenChanged() {
		if (this.isAlive()) {
			this.childrenChanged_();
		}
	}

	private void childrenChanged_() {
		Object[] old = this.children;
		this.children = this.buildChildren();
		Iterable<?> addedChildren = ArrayTools.iterable(ArrayTools.removeAll(this.children, old));
		Iterable<?> removedChildren = ArrayTools.iterable(ArrayTools.removeAll(old, this.children));
		this.notifyManager(addedChildren, removedChildren);
	}

	/* CU private */ void childrenCleared() {
		if (this.isAlive()) {
			this.childrenCleared_();
		}
	}

	private void childrenCleared_() {
		Object[] old = this.children;
		this.children = ObjectTools.EMPTY_OBJECT_ARRAY;
		if (old.length != 0) {
			this.notifyManager(EMPTY_ITERABLE, ArrayTools.iterable(old));
		}
	}

	/**
	 * Notify the item content provider's manager the item's list of children
	 * has changed.
	 */
	/* private-protected */ abstract void notifyManager(Iterable<?> addedChildren, Iterable<?> removedChildren);

	private Object[] buildChildren() {
		while (true) {
			try {
				return ArrayTools.array(this.childrenModel);
			} catch (ConcurrentModificationException ex) {
				// try again - hack: need to make value models thread-safe... TODO bjv
			}
		}
	}


	// ********** dispose **********

	public void dispose() {
		this.childrenModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this.childrenListener);
		this.children = null;  // NB: disposed!
	}


	// ********** misc **********

	/**
	 * Check whether the provider was disposed between the time an event was
	 * fired and the time the event is handled on the UI thread.
	 */
	private boolean isAlive() {
		return this.children != null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.item);
	}
}
