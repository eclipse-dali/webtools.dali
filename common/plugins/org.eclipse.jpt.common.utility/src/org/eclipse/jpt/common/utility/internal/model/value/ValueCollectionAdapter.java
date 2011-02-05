/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Arrays;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * Extend {@link ValueAspectAdapter} to listen to one or more collection
 * aspects of the value in the wrapped value model.
 */
public class ValueCollectionAdapter<V extends Model>
	extends ValueAspectAdapter<V>
{
	/** The names of the value's collections that we listen to. */
	protected final String[] collectionNames;

	/** Listener that listens to the value. */
	protected final CollectionChangeListener valueCollectionListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value collections.
	 */
	public ValueCollectionAdapter(WritablePropertyValueModel<V> valueHolder, String... collectionNames) {
		super(valueHolder);
		this.collectionNames = collectionNames;
		this.valueCollectionListener = this.buildValueCollectionListener();
	}


	// ********** initialization **********

	protected CollectionChangeListener buildValueCollectionListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				ValueCollectionAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				ValueCollectionAdapter.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionClearEvent event) {
				ValueCollectionAdapter.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				ValueCollectionAdapter.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "value collection listener: " + Arrays.asList(ValueCollectionAdapter.this.collectionNames); //$NON-NLS-1$
			}
		};
	}


	// ********** ValueAspectAdapter implementation **********

	@Override
	protected void engageValue_() {
		for (String collectionName : this.collectionNames) {
			this.value.addCollectionChangeListener(collectionName, this.valueCollectionListener);
		}
	}

	@Override
	protected void disengageValue_() {
		for (String collectionName : this.collectionNames) {
			this.value.removeCollectionChangeListener(collectionName, this.valueCollectionListener);
		}
	}


	// ********** change events **********

	protected void itemsAdded(@SuppressWarnings("unused") CollectionAddEvent event) {
		this.valueAspectChanged();
	}

	protected void itemsRemoved(@SuppressWarnings("unused") CollectionRemoveEvent event) {
		this.valueAspectChanged();
	}

	protected void collectionCleared(@SuppressWarnings("unused") CollectionClearEvent event) {
		this.valueAspectChanged();
	}

	protected void collectionChanged(@SuppressWarnings("unused") CollectionChangeEvent event) {
		this.valueAspectChanged();
	}

}
