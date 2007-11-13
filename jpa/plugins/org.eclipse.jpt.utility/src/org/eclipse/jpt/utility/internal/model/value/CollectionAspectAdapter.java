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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;

/**
 * This extension of AspectAdapter provides CollectionChange support.
 * 
 * The typical subclass will override the following methods:
 * #getValueFromSubject()
 *     at the very minimum, override this method to return an iterator on the
 *     subject's collection aspect; it does not need to be overridden if
 *     #value() is overridden and its behavior changed
 * #sizeFromSubject()
 *     override this method to improve performance; it does not need to be overridden if
 *     #size() is overridden and its behavior changed
 * #addItem(Object) and #removeItem(Object)
 *     override these methods if the client code needs to *change* the contents of
 *     the subject's collection aspect; oftentimes, though, the client code
 *     (e.g. UI) will need only to *get* the value
 * #addItems(Collection) and #removeItems(Collection)
 *     override these methods to improve performance, if necessary
 * #values()
 *     override this method only if returning an empty iterator when the
 *     subject is null is unacceptable
 * #size()
 *     override this method only if returning a zero when the
 *     subject is null is unacceptable
 */
public abstract class CollectionAspectAdapter 
	extends AspectAdapter 
	implements CollectionValueModel 
{
	/**
	 * The name of the subject's collection that we use for the value.
	 */
	protected final String collectionName;

	/** A listener that listens to the subject's collection aspect. */
	protected final CollectionChangeListener collectionChangeListener;


	// ********** constructors **********

	/**
	 * Construct a CollectionAspectAdapter for the specified subject
	 * and collection.
	 */
	protected CollectionAspectAdapter(String collectionName, Model subject) {
		this(new ReadOnlyPropertyValueModel(subject), collectionName);
	}

	/**
	 * Construct a CollectionAspectAdapter for an "unchanging" collection in
	 * the specified subject. This is useful for a collection aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new collection.
	 */
	protected CollectionAspectAdapter(ValueModel subjectHolder) {
		this(subjectHolder, null);
	}

	/**
	 * Construct a CollectionAspectAdapter for the specified subject holder
	 * and collection.
	 */
	protected CollectionAspectAdapter(ValueModel subjectHolder, String collectionName) {
		super(subjectHolder);
		this.collectionName = collectionName;
		this.collectionChangeListener = this.buildCollectionChangeListener();
	}


	// ********** initialization **********

	/**
	 * The subject's collection aspect has changed, notify the listeners.
	 */
	protected CollectionChangeListener buildCollectionChangeListener() {
		// transform the subject's collection change events into VALUE collection change events
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				CollectionAspectAdapter.this.itemsAdded(e);
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				CollectionAspectAdapter.this.itemsRemoved(e);
			}
			public void collectionCleared(CollectionChangeEvent e) {
				CollectionAspectAdapter.this.collectionCleared(e);
			}
			public void collectionChanged(CollectionChangeEvent e) {
				CollectionAspectAdapter.this.collectionChanged(e);
			}
			@Override
			public String toString() {
				return "collection change listener: " + CollectionAspectAdapter.this.collectionName;
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	/**
	 * Return the value of the subject's collection aspect.
	 * This should be an *iterator* on the collection.
	 */
	public Iterator values() {
		if (this.subject == null) {
			return EmptyIterator.instance();
		}
		return this.getValueFromSubject();
	}

	/**
	 * Return the value of the subject's collection aspect.
	 * This should be an *iterator* on the collection.
	 * At this point we can be sure that the subject is not null.
	 * @see #values()
	 */
	protected Iterator getValueFromSubject() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Add the specified item to the subject's collection aspect.
	 */
	public void add(Object item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Add the specified items to the subject's collection aspect.
	 */
	public void addAll(Collection items) {
		for (Iterator stream = items.iterator(); stream.hasNext(); ) {
			this.add(stream.next());
		}
	}

	/**
	 * Remove the specified item from the subject's collection aspect.
	 */
	public void remove(Object item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Remove the specified items from the subject's collection aspect.
	 */
	public void removeAll(Collection items) {
		for (Iterator stream = items.iterator(); stream.hasNext(); ) {
			this.remove(stream.next());
		}
	}

	/**
	 * Return the size of the collection value.
	 */
	public int size() {
		return this.subject == null ? 0 : this.sizeFromSubject();
	}

	/**
	 * Return the size of the subject's collection aspect.
	 * At this point we can be sure that the subject is not null.
	 * @see #size()
	 */
	protected int sizeFromSubject() {
		return CollectionTools.size((Iterator) this.values());
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object value() {
		return this.values();
	}

	@Override
	protected Class<? extends ChangeListener> listenerClass() {
		return CollectionChangeListener.class;
	}

	@Override
	protected String listenerAspectName() {
		return VALUES;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyCollectionChangeListeners(VALUES);
	}

	@Override
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.fireCollectionChanged(VALUES);
	}

	@Override
	protected void engageNonNullSubject() {
		if (this.collectionName != null) {
			((Model) this.subject).addCollectionChangeListener(this.collectionName, this.collectionChangeListener);
		}
	}

	@Override
	protected void disengageNonNullSubject() {
		if (this.collectionName != null) {
			((Model) this.subject).removeCollectionChangeListener(this.collectionName, this.collectionChangeListener);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collectionName);
	}


	// ********** behavior **********

	protected void itemsAdded(CollectionChangeEvent e) {
		this.fireItemsAdded(e.cloneWithSource(this, VALUES));
	}

	protected void itemsRemoved(CollectionChangeEvent e) {
		this.fireItemsRemoved(e.cloneWithSource(this, VALUES));
	}

	protected void collectionCleared(CollectionChangeEvent e) {
		this.fireCollectionCleared(VALUES);
	}

	protected void collectionChanged(CollectionChangeEvent e) {
		this.fireCollectionChanged(VALUES);
	}

}
