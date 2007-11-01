/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;

/**
 * Extend ItemAspectListValueModelAdapter to listen to one or more
 * properties of each item in the wrapped list model.
 */
public class ItemPropertyListValueModelAdapter extends ItemAspectListValueModelAdapter {

	/** The names of the items' properties that we listen to. */
	protected final String[] propertyNames;

	/** Listener that listens to all the items in the list. */
	protected PropertyChangeListener itemPropertyListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item property.
	 */
	public ItemPropertyListValueModelAdapter(ListValueModel listHolder, String propertyName) {
		this(listHolder, new String[] {propertyName});
	}

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(ListValueModel listHolder, String propertyName1, String propertyName2) {
		this(listHolder, new String[] {propertyName1, propertyName2});
	}

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(ListValueModel listHolder, String propertyName1, String propertyName2, String propertyName3) {
		this(listHolder, new String[] {propertyName1, propertyName2, propertyName3});
	}

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(ListValueModel listHolder, String[] propertyNames) {
		super(listHolder);
		this.propertyNames = propertyNames;
	}

	/**
	 * Construct an adapter for the specified item property.
	 */
	public ItemPropertyListValueModelAdapter(CollectionValueModel collectionHolder, String propertyName) {
		this(collectionHolder, new String[] {propertyName});
	}

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(CollectionValueModel collectionHolder, String propertyName1, String propertyName2) {
		this(collectionHolder, new String[] {propertyName1, propertyName2});
	}

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(CollectionValueModel collectionHolder, String propertyName1, String propertyName2, String propertyName3) {
		this(collectionHolder, new String[] {propertyName1, propertyName2, propertyName3});
	}

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(CollectionValueModel collectionHolder, String[] propertyNames) {
		super(collectionHolder);
		this.propertyNames = propertyNames;
	}


	// ********** initialization **********

	@Override
	protected void initialize() {
		super.initialize();
		this.itemPropertyListener = this.buildItemPropertyListener();
	}

	protected PropertyChangeListener buildItemPropertyListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				ItemPropertyListValueModelAdapter.this.itemAspectChanged(e);
			}
			@Override
			public String toString() {
				return "item property listener: " + Arrays.asList(ItemPropertyListValueModelAdapter.this.propertyNames);
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		for (int i = this.propertyNames.length; i-- > 0; ) {
			item.addPropertyChangeListener(this.propertyNames[i], this.itemPropertyListener);
		}
	}

	@Override
	protected void stopListeningToItem(Model item) {
		for (int i = this.propertyNames.length; i-- > 0; ) {
			item.removePropertyChangeListener(this.propertyNames[i], this.itemPropertyListener);
		}
	}

}
