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
import java.util.Collection;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This extension of {@link AspectCollectionValueModelAdapter} provides
 * basic collection change support.
 * This converts a set of one or more collections into
 * a single {@link #VALUES} collection.
 * <p>
 * The typical subclass will override the following methods (see the descriptions
 * in {@link AspectCollectionValueModelAdapter}):<ul>
 * <li>{@link #getIterable()}
 * <li>{@link #size_()}
 * <li>{@link #iterator_()}
 * <li>{@link #iterator()}
 * <li>{@link #size()}
 * </ul>
 */
public abstract class CollectionAspectAdapter<S extends Model, E>
	extends AspectCollectionValueModelAdapter<S, E>
{
	/**
	 * The name of the subject's collections that we use for the value.
	 */
	protected final String[] collectionNames;
		protected static final String[] EMPTY_COLLECTION_NAMES = new String[0];

	/** A listener that listens to the subject's collection aspects. */
	protected final CollectionChangeListener collectionChangeListener;


	// ********** constructors **********

	/**
	 * Construct a collection aspect adapter for the specified subject
	 * and collection.
	 */
	protected CollectionAspectAdapter(String collectionName, S subject) {
		this(new String[] {collectionName}, subject);
	}

	/**
	 * Construct a collection aspect adapter for the specified subject
	 * and collections.
	 */
	protected CollectionAspectAdapter(String[] collectionNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), collectionNames);
	}

	/**
	 * Construct a collection aspect adapter for the specified subject holder
	 * and collections.
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... collectionNames) {
		super(subjectHolder);
		this.collectionNames = collectionNames;
		this.collectionChangeListener = this.buildCollectionChangeListener();
	}

	/**
	 * Construct a collection aspect adapter for the specified subject holder
	 * and collections.
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> collectionNames) {
		this(subjectHolder, collectionNames.toArray(new String[collectionNames.size()]));
	}

	/**
	 * Construct a collection aspect adapter for an "unchanging" collection in
	 * the specified subject. This is useful for a collection aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new collection.
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_COLLECTION_NAMES);
	}


	// ********** initialization **********

	protected CollectionChangeListener buildCollectionChangeListener() {
		// transform the subject's collection change events into VALUES collection change events
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				CollectionAspectAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				CollectionAspectAdapter.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionClearEvent event) {
				CollectionAspectAdapter.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CollectionAspectAdapter.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "collection change listener: " + Arrays.asList(CollectionAspectAdapter.this.collectionNames); //$NON-NLS-1$
			}
		};
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected void engageSubject_() {
    	for (String collectionName : this.collectionNames) {
			((Model) this.subject).addCollectionChangeListener(collectionName, this.collectionChangeListener);
		}
	}

	@Override
	protected void disengageSubject_() {
    	for (String collectionName : this.collectionNames) {
			((Model) this.subject).removeCollectionChangeListener(collectionName, this.collectionChangeListener);
		}
	}


	// ********** behavior **********

	protected void itemsAdded(CollectionAddEvent event) {
		this.fireItemsAdded(event.clone(this, VALUES));
	}

	protected void itemsRemoved(CollectionRemoveEvent event) {
		this.fireItemsRemoved(event.clone(this, VALUES));
	}

	protected void collectionCleared(CollectionClearEvent event) {
		this.fireCollectionCleared(event.clone(this, VALUES));
	}

	protected void collectionChanged(CollectionChangeEvent event) {
		this.fireCollectionChanged(event.clone(this, VALUES));
	}

}
