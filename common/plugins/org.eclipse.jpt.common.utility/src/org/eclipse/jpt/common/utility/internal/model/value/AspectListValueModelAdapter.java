/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.EventListener;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This {@link AspectAdapter} provides basic list change support.
 * This converts an "aspect" (as defined by subclasses) into
 * a single {@link #LIST_VALUES} list.
 * <p>
 * The typical subclass will override the following methods:<ul>
 * <li>{@link #engageSubject_()}<p>
 *     implement this method to add the appropriate listener to the subject
 * <li>{@link #disengageSubject_()}<p>
 *     implement this method to remove the appropriate listener from the subject
 * <li>{@link #getListIterable()}<p>
 *     at the very minimum, override this method to return a list iterable containing the
 *     subject's list aspect; it does not need to be overridden if either
 *     {@link #listIterator_()} or {@link #listIterator()} is overridden and its behavior changed
 * <li>{@link #get(int)}<p>
 *     override this method to improve performance
 * <li>{@link #size_()}<p>
 *     override this method to improve performance; it does not need to be overridden if
 *     {@link #size()} is overridden and its behavior changed
 * <li>{@link #toArray_()}<p>
 *     override this method to improve performance; it does not need to be overridden if
 *     {@link #toArray()} is overridden and its behavior changed
 * <li>{@link #listIterator_()}<p>
 *     override this method to return a list iterator on the subject's list
 *     aspect if it is not possible to implement {@link #getListIterable()};
 *     it does not need to be overridden if
 *     {@link #listIterator()} is overridden and its behavior changed
 * <li>{@link #listIterator()}<p>
 *     override this method only if returning an empty list iterator when the
 *     subject is null is unacceptable
 * <li>{@link #size()}<p>
 *     override this method only if returning a zero when the
 *     subject is null is unacceptable
 * <li>{@link #toArray()}<p>
 *     override this method only if returning an empty array when the
 *     subject is null is unacceptable
 * </ul>
 * To notify listeners, subclasses can call {@link #aspectChanged()}
 * whenever the aspect has changed.
 * 
 * @param <S> the type of the model's subject
 * @param <E> the type of the subject's list aspect's elements
 */
public abstract class AspectListValueModelAdapter<S, E>
	extends AspectAdapter<S, List<E>>
	implements ListValueModel<E>
{
	// ********** constructors **********

	/**
	 * Construct a list value model adapter for an aspect of the
	 * specified subject.
	 */
	protected AspectListValueModelAdapter(PropertyValueModel<? extends S> subjectModel) {
		super(subjectModel);
	}


	// ********** ListValueModel implementation **********

	/**
	 * Return the elements of the subject's list aspect.
	 */
	public ListIterator<E> iterator() {
		return this.listIterator();
	}

	/**
	 * Return the elements of the subject's list aspect.
	 */
	public ListIterator<E> listIterator() {
		return (this.subject == null) ? EmptyListIterator.<E>instance() : this.listIterator_();
	}

	/**
	 * Return the elements of the subject's list aspect.
	 * At this point we can be sure the {@link #subject} is
	 * <em>not</em> <code>null</code>.
	 * @see #listIterator()
	 */
	protected ListIterator<E> listIterator_() {
		return this.getListIterable().iterator();
	}

	/**
	 * Return the elements of the subject's list aspect.
	 * At this point we can be sure the {@link #subject} is
	 * <em>not</em> <code>null</code>.
	 * @see #listIterator_()
	 */
	protected ListIterable<E> getListIterable() {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	/**
	 * Return the element at the specified index of the subject's list aspect.
	 */
	public E get(int index) {
		return IteratorTools.get(this.listIterator(), index);
	}

	/**
	 * Return the size of the subject's list aspect.
	 */
	public int size() {
		return this.subject == null ? 0 : this.size_();
	}

	/**
	 * Return the size of the subject's list aspect.
	 * At this point we can be sure the {@link #subject} is
	 * <em>not</em> <code>null</code>.
	 * @see #size()
	 */
	protected int size_() {
		return IteratorTools.size(this.listIterator());
	}

	/**
	 * Return an array manifestation of the subject's list aspect.
	 */
	public Object[] toArray() {
		return this.subject == null ? ObjectTools.EMPTY_OBJECT_ARRAY : this.toArray_();
	}

	/**
	 * Return an array manifestation of the subject's list aspect.
	 * At this point we can be sure the subject is not null.
	 * @see #toArray()
	 */
	protected Object[] toArray_() {
		return ArrayTools.array(this.listIterator(), this.size());
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected List<E> getAspectValue() {
		return this.buildValueList();
	}

	@Override
	protected Class<? extends EventListener> getListenerClass() {
		return ListChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return LIST_VALUES;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyListChangeListeners(LIST_VALUES);
	}

	@Override
	protected void fireAspectChanged(List<E> oldValue, List<E> newValue) {
		this.fireListChanged(LIST_VALUES, newValue);
	}

	protected void aspectChanged() {
		this.fireListChanged(LIST_VALUES, this.buildValueList());
	}

	protected List<E> buildValueList() {
		return ListTools.arrayList(this.iterator());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildValueList());
	}
}
