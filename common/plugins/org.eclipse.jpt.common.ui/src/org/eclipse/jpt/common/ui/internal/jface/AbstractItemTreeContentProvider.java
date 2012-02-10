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

import java.util.ConcurrentModificationException;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * Abstract {@link ItemTreeContentProvider} that provides support for listening to an
 * {@link #item} and notifying the
 * {@link org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager manager}
 * whenever the item's children change in a significant way.
 * <p>
 * Subclasses must implement the following method:<ul>
 * <li>{@link #buildChildrenModel()}<br>
 * 	   Return a {@link CollectionValueModel} that represents the item's children
 * </ul>
 * 
 * @see StaticItemTreeContentProvider
 */
public abstract class AbstractItemTreeContentProvider<I, C>
	implements ItemTreeContentProvider
{
	protected final Manager manager;

	protected final I item;

	protected volatile CollectionValueModel<C> childrenModel;

	protected volatile CollectionChangeListener childrenListener;


	protected AbstractItemTreeContentProvider(I item, Manager manager) {
		this.item = item;
		this.manager = manager;
	}


	// ********** children **********

	/**
	 * Typical implementation for a tree.
	 */
	public Object[] getElements() {
		return this.getChildren();
	}

	// TODO bjv
	public Object[] getChildren() {
		while (true) {
			try {
				return ArrayTools.array(this.getChildrenModel());
			} catch (ConcurrentModificationException ex) {
				// try again - hack: need to make value model stuff thread-safe...
			}
		}
	}

	/**
	 * Return the children model (lazy-initialized).
	 */
	protected synchronized CollectionValueModel<C> getChildrenModel() {
		if (this.childrenModel == null) {
			this.childrenModel = this.buildChildrenModel();
			this.childrenListener = this.buildChildrenListener();
			this.engageChildrenModel();
		}
		return this.childrenModel;
	}

	/**
	 * Construct a children model.
	 */
	protected abstract CollectionValueModel<C> buildChildrenModel();

	/**
	 * Override with potentially more efficient logic.
	 */
	public boolean hasChildren() {
		return this.getChildrenModel().iterator().hasNext();
	}

	protected void engageChildrenModel() {
		this.childrenModel.addCollectionChangeListener(CollectionValueModel.VALUES, this.childrenListener);
	}

	protected void disengageChildrenModel() {
		this.childrenModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this.childrenListener);
	}


	// ********** listener **********

	protected CollectionChangeListener buildChildrenListener() {
		return new ChildrenListener();
	}

	protected class ChildrenListener
		extends CollectionChangeAdapter
	{
		@Override
		@SuppressWarnings("unchecked")
		public void itemsAdded(CollectionAddEvent event) {
			AbstractItemTreeContentProvider.this.childrenAdded((Iterable<C>) event.getItems());
		}

		@Override
		@SuppressWarnings("unchecked")
		public void itemsRemoved(CollectionRemoveEvent event) {
			AbstractItemTreeContentProvider.this.childrenRemoved((Iterable<C>) event.getItems());
		}

		/**
		 * <strong>NB:</strong> Any removed children will malinger until the
		 * manager itself is disposed.
		 */
		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			AbstractItemTreeContentProvider.this.childrenChanged();
		}

		/**
		 * <strong>NB:</strong> Any removed children will malinger until the
		 * manager itself is disposed.
		 */
		@Override
		public void collectionCleared(CollectionClearEvent event) {
			AbstractItemTreeContentProvider.this.childrenChanged();
		}
	}

	protected void childrenAdded(@SuppressWarnings("unused") Iterable<C> children) {
		this.manager.updateChildren(this.item);
	}

	protected void childrenRemoved(Iterable<C> children) {
		this.manager.updateChildren(this.item);
		for (Object child : children) {
			this.manager.dispose(child);
		}
	}

	protected void childrenChanged() {
		this.manager.updateChildren(this.item);
	}


	// ********** dispose **********

	public synchronized void dispose() {
		if (this.childrenModel != null) {
			this.dispose_();
		}
	}

	/**
	 * Pre-condition: {@link #childrenModel} is not <code>null</code>.
	 */
	protected void dispose_() {
		for (Object child : this.childrenModel) {
			this.manager.dispose(child);
		}
		this.disengageChildrenModel();
		this.childrenModel = null;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.item);
	}
}
