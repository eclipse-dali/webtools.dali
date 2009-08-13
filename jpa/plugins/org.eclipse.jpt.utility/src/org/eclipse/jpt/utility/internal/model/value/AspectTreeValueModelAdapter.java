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
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.TreeValueModel;

/**
 * This {@link AspectAdapter} provides basic tree change support.
 * This converts an "aspect" (as defined by subclasses) into
 * a single {@link #NODES} tree.
 * <p>
 * The typical subclass will override the following methods:<ul>
 * <li>{@link #engageSubject_()}<p>
 *     implement this method to add the appropriate listener to the subject
 * <li>{@link #disengageSubject_()}<p>
 *     implement this method to remove the appropriate listener from the subject
 * <li>{@link #nodes_()}<p>
 *     at the very minimum, override this method to return an iterator
 *     on the subject's tree aspect; it does not need to be overridden if
 *     {@link #nodes()} is overridden and its behavior changed
 * <li>{@link #nodes()}<p>
 *     override this method only if returning an empty iterator when the
 *     subject is null is unacceptable
 * </ul>
 * To notify listeners, subclasses can call {@link #treeChanged()}
 * whenever the aspect has changed.
 */
public abstract class AspectTreeValueModelAdapter<S, E>
	extends AspectAdapter<S>
	implements TreeValueModel<E>
{

	// ********** constructors **********

	/**
	 * Construct a tree value model adapter for an aspect of the
	 * specified subject.
	 */
	protected AspectTreeValueModelAdapter(PropertyValueModel<? extends S> subjectHolder) {
		super(subjectHolder);
	}


	// ********** TreeValueModel implementation **********

	/**
	 * Return the nodes of the subject's tree aspect.
	 */
	public Iterator<E> nodes() {
		return (this.subject == null) ? EmptyIterator.<E>instance() : this.nodes_();
	}

	/**
	 * Return the nodes of the subject's tree aspect.
	 * At this point we can be sure the subject is not null.
	 * @see #nodes()
	 */
	protected Iterator<E> nodes_() {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object getValue() {
		return this.buildValueCollection();
	}

	@Override
	protected Class<? extends EventListener> getListenerClass() {
		return TreeChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return NODES;
	}

    @Override
	protected boolean hasListeners() {
		return this.hasAnyTreeChangeListeners(NODES);
	}

    @Override
	protected void fireAspectChanged(Object oldValue, Object newValue) {
		@SuppressWarnings("unchecked") Collection<E> newNodes = (Collection<E>) newValue;
		this.fireTreeChanged(NODES, newNodes);
	}

	protected void treeChanged() {
		this.fireTreeChanged(NODES, this.buildValueCollection());
	}

	protected Collection<E> buildValueCollection() {
		return CollectionTools.collection(this.nodes());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.buildValueCollection());
	}

}
