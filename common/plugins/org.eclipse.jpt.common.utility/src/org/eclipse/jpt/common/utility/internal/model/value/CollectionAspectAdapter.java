/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Collection;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeAdapter;
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
 * <li>{@link #getIterable()} - preferred
 * <li>{@link #size_()} - semi-preferred
 * <li>{@link #iterator_()}
 * <li>{@link #iterator()}
 * <li>{@link #size()}
 * </ul>
 * 
 * @param <S> the type of the adapter's subject
 * @param <E> the type of the adapter's collection's elements
 */
public abstract class CollectionAspectAdapter<S extends Model, E>
	extends AspectCollectionValueModelAdapter<S, E>
{
	/**
	 * The name of the subject's collections that we use for the value.
	 */
	protected final String[] aspectNames;
		protected static final String[] EMPTY_ASPECT_NAMES = new String[0];

	/** A listener that listens to the subject's collection aspects. */
	protected final CollectionChangeListener aspectChangeListener;


	// ********** constructors **********

	/**
	 * Construct a collection aspect adapter for the specified subject
	 * and collection aspect.
	 */
	protected CollectionAspectAdapter(String aspectName, S subject) {
		this(new String[] {aspectName}, subject);
	}

	/**
	 * Construct a collection aspect adapter for the specified subject
	 * and collection aspects.
	 */
	protected CollectionAspectAdapter(String[] aspectNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), aspectNames);
	}

	/**
	 * Construct a collection aspect adapter for the specified subject model
	 * and collection apects.
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectModel, String... aspectNames) {
		super(subjectModel);
		if (aspectNames == null) {
			throw new NullPointerException();
		}
		this.aspectNames = aspectNames;
		this.aspectChangeListener = this.buildAspectChangeListener();
	}

	/**
	 * Construct a collection aspect adapter for the specified subject holder
	 * and collection aspects.
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectModel, Collection<String> aspectNames) {
		this(subjectModel, aspectNames.toArray(new String[aspectNames.size()]));
	}

	/**
	 * Construct a collection aspect adapter for an "unchanging" collection in
	 * the specified subject. This is useful for a collection aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new collection.
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectModel) {
		this(subjectModel, EMPTY_ASPECT_NAMES);
	}


	// ********** initialization **********

	protected CollectionChangeListener buildAspectChangeListener() {
		return new AspectChangeListener();
	}

	/**
	 * Transform the subject's collection change events into {@link #VALUES}
	 * collection change events
	 */
	protected class AspectChangeListener
		extends CollectionChangeAdapter
	{
		@Override
		public void itemsAdded(CollectionAddEvent event) {
			CollectionAspectAdapter.this.aspectItemsAdded(event);
		}
		@Override
		public void itemsRemoved(CollectionRemoveEvent event) {
			CollectionAspectAdapter.this.aspectItemsRemoved(event);
		}
		@Override
		public void collectionCleared(CollectionClearEvent event) {
			CollectionAspectAdapter.this.aspectCollectionCleared(event);
		}
		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			CollectionAspectAdapter.this.aspectCollectionChanged(event);
		}
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected void engageSubject_() {
    	for (String collectionName : this.aspectNames) {
			this.subject.addCollectionChangeListener(collectionName, this.aspectChangeListener);
		}
	}

	@Override
	protected void disengageSubject_() {
    	for (String collectionName : this.aspectNames) {
			this.subject.removeCollectionChangeListener(collectionName, this.aspectChangeListener);
		}
	}


	// ********** events **********

	protected void aspectItemsAdded(CollectionAddEvent event) {
		this.fireItemsAdded(event.clone(this, VALUES));
	}

	protected void aspectItemsRemoved(CollectionRemoveEvent event) {
		this.fireItemsRemoved(event.clone(this, VALUES));
	}

	protected void aspectCollectionCleared(CollectionClearEvent event) {
		this.fireCollectionCleared(event.clone(this, VALUES));
	}

	protected void aspectCollectionChanged(CollectionChangeEvent event) {
		this.fireCollectionChanged(event.clone(this, VALUES));
	}
}
