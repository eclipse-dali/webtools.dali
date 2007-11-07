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
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;

/**
 * Extend ValueAspectPropertyValueModelAdapter to listen to one or more collection
 * aspects of the value in the wrapped value model.
 */
public class ValueCollectionPropertyValueModelAdapter
	extends ValueAspectPropertyValueModelAdapter
{

	/** The names of the value's collections that we listen to. */
	protected final String[] collectionNames;

	/** Listener that listens to the value. */
	protected final CollectionChangeListener valueCollectionListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value collection.
	 */
	public ValueCollectionPropertyValueModelAdapter(PropertyValueModel valueHolder, String collectionName) {
		this(valueHolder, new String[] {collectionName});
	}

	/**
	 * Construct an adapter for the specified value collections.
	 */
	public ValueCollectionPropertyValueModelAdapter(PropertyValueModel valueHolder, String collectionName1, String collectionName2) {
		this(valueHolder, new String[] {collectionName1, collectionName2});
	}

	/**
	 * Construct an adapter for the specified value collections.
	 */
	public ValueCollectionPropertyValueModelAdapter(PropertyValueModel valueHolder, String collectionName1, String collectionName2, String collectionName3) {
		this(valueHolder, new String[] {collectionName1, collectionName2, collectionName3});
	}

	/**
	 * Construct an adapter for the specified value collections.
	 */
	public ValueCollectionPropertyValueModelAdapter(PropertyValueModel valueHolder, String[] collectionNames) {
		super(valueHolder);
		this.collectionNames = collectionNames;
		this.valueCollectionListener = this.buildValueCollectionListener();
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that a Collection aspect has 
	 * changed. Do the same thing no matter which event occurs.
	 */
	protected CollectionChangeListener buildValueCollectionListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				ValueCollectionPropertyValueModelAdapter.this.valueAspectChanged();
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				ValueCollectionPropertyValueModelAdapter.this.valueAspectChanged();
			}
			public void collectionCleared(CollectionChangeEvent e) {
				ValueCollectionPropertyValueModelAdapter.this.valueAspectChanged();
			}
			public void collectionChanged(CollectionChangeEvent e) {
				ValueCollectionPropertyValueModelAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value collection listener: " + Arrays.asList(ValueCollectionPropertyValueModelAdapter.this.collectionNames);
			}
		};
	}

	@Override
	protected void startListeningToValue() {
		Model v = (Model) this.value;
		for (int i = this.collectionNames.length; i-- > 0; ) {
			v.addCollectionChangeListener(this.collectionNames[i], this.valueCollectionListener);
		}
	}

	@Override
	protected void stopListeningToValue() {
		Model v = (Model) this.value;
		for (int i = this.collectionNames.length; i-- > 0; ) {
			v.removeCollectionChangeListener(this.collectionNames[i], this.valueCollectionListener);
		}
	}

}
