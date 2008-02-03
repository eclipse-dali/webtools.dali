/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Arrays;

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.TreeChangeListener;

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
			public void nodeAdded(TreeChangeEvent e) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void nodeRemoved(TreeChangeEvent e) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void treeCleared(TreeChangeEvent e) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void treeChanged(TreeChangeEvent e) {
				ItemTreeListValueModelAdapter.this.itemAspectChanged(e);
			}
			@Override
			public String toString() {
				return "item tree listener: " + Arrays.asList(ItemTreeListValueModelAdapter.this.treeNames);
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
