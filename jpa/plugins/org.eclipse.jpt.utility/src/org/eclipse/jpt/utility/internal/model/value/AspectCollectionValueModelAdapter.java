/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Collection;
import java.util.EventListener;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * This {@link AspectAdapter} provides basic collection change support.
 * This converts an "aspect" (as defined by subclasses) into
 * a single {@link #VALUES} collection.
 * <p>
 * The typical subclass will override the following methods:<ul>
 * <li>{@link #engageSubject_()}<p>
 *     implement this method to add the appropriate listener to the subject
 * <li>{@link #disengageSubject_()}<p>
 *     implement this method to remove the appropriate listener from the subject
 * <li>{@link #iterator_()}<p>
 *     at the very minimum, override this method to return an iterator on the
 *     subject's collection aspect; it does not need to be overridden if
 *     {@link #iterator()} is overridden and its behavior changed
 * <li>{@link #size_()}<p>
 *     override this method to improve performance; it does not need to be overridden if
 *     {@link #size()} is overridden and its behavior changed
 * <li>{@link #iterator()}<p>
 *     override this method only if returning an empty iterator when the
 *     subject is null is unacceptable
 * <li>{@link #size()}<p>
 *     override this method only if returning a zero when the
 *     subject is null is unacceptable
 * </ul>
 * To notify listeners, subclasses can call {@link #collectionChanged()}
 * whenever the aspect has changed.
 */
public abstract class AspectCollectionValueModelAdapter<S, E>
	extends AspectAdapter<S>
	implements CollectionValueModel<E>
{

	// ********** constructors **********

	/**
	 * Construct a collection value model adapter for an aspect of the
	 * specified subject.
	 */
	protected AspectCollectionValueModelAdapter(PropertyValueModel<? extends S> subjectHolder) {
		super(subjectHolder);
	}


	// ********** CollectionValueModel implementation **********

	/**
	 * Return the elements of the subject's collection aspect.
	 */
	public Iterator<E> iterator() {
		return (this.subject == null) ? EmptyIterator.<E>instance() : this.iterator_();
	}

	/**
	 * Return the elements of the subject's collection aspect.
	 * At this point we can be sure the subject is not null.
	 * @see #iterator()
	 */
	protected Iterator<E> iterator_() {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	/**
	 * Return the size of the subject's collection aspect.
	 */
	public int size() {
		return (this.subject == null) ? 0 : this.size_();
	}

	/**
	 * Return the size of the subject's collection aspect.
	 * At this point we can be sure the subject is not null.
	 * @see #size()
	 */
	protected int size_() {
		return CollectionTools.size(this.iterator());
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object getValue() {
		return this.buildValueCollection();
	}

	@Override
	protected Class<? extends EventListener> getListenerClass() {
		return CollectionChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return VALUES;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyCollectionChangeListeners(VALUES);
	}

	@Override
	protected void fireAspectChanged(Object oldValue, Object newValue) {
		@SuppressWarnings("unchecked") Collection<E> newCollection = (Collection<E>) newValue;
		this.fireCollectionChanged(VALUES, newCollection);
	}

	protected void collectionChanged() {
		this.fireCollectionChanged(VALUES, this.buildValueCollection());
	}

	protected Collection<E> buildValueCollection() {
		return CollectionTools.collection(this.iterator());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildValueCollection());
	}

}
