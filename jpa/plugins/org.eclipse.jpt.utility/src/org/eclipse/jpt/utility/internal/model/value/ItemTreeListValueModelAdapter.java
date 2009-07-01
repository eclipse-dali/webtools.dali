/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Arrays;

import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Extend ItemAspectListValueModelAdapter to listen to one or more tree
 * aspects of each item in the wrapped list model.
 */
public class ItemTreeListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{

	/** The names of the items' tree that we listen to. */
	protected final String[] treeNames;

	/** Listener that listens to all the items in the list. */
	protected final TreeChangeListener itemTreeListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item trees.
	 */
	public ItemTreeListValueModelAdapter(ListValueModel<E> listHolder, String... treeNames) {
		super(listHolder);
		this.treeNames = treeNames;
		this.itemTreeListener = this.buildItemTreeListener();
	}

	/**
	 * Construct an adapter for the specified item trees.
	 */
	public ItemTreeListValueModelAdapter(CollectionValueModel<E> collectionHolder, String... treeNames) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), treeNames);
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that a tree aspect has 
	 * changed.  Do the same thing no matter which event occurs.
	 */
	protected TreeChangeListener buildItemTreeListener() {
		return new TreeChangeListener() {
			public void nodeAdded(TreeAddEvent event) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void nodeRemoved(TreeRemoveEvent event) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void treeCleared(TreeClearEvent event) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void treeChanged(TreeChangeEvent event) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item tree listener: " + Arrays.asList(ItemTreeListValueModelAdapter.this.treeNames); //$NON-NLS-1$
			}
		};
	}


	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		for (String treeName : this.treeNames) {
			item.addTreeChangeListener(treeName, this.itemTreeListener);
		}
	}

	@Override
	protected void stopListeningToItem(Model item) {
		for (String treeName : this.treeNames) {
			item.removeTreeChangeListener(treeName, this.itemTreeListener);
		}
	}

}
