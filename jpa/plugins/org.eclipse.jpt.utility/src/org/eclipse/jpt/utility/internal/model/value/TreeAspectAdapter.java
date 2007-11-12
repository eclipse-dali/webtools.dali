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

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.TreeChangeListener;

/**
 * This extension of PropertyAdapter provides TreeChange support.
 * 
 * The typical subclass will override the following methods:
 * #getValueFromSubject()
 *     at the very minimum, override this method to return an iterator
 *     on the subject's tree aspect; it does not need to be overridden if
 *     #value() is overridden and its behavior changed
 * #add(Object[], Object) and #remove(Object[])
 *     override these methods if the client code needs to *change* the contents of
 *     the subject's tree aspect; oftentimes, though, the client code
 *     (e.g. UI) will need only to *get* the nodes
 * #nodes()
 *     override this method only if returning an empty iterator when the
 *     subject is null is unacceptable
 */
public abstract class TreeAspectAdapter
	extends AspectAdapter
	implements TreeValueModel
{
	/**
	 * The name of the subject's tree that we use for the value.
	 */
	protected final String treeName;

	/** A listener that listens to the subject's tree aspect. */
	protected final TreeChangeListener treeChangeListener;


	// ********** constructors **********

	/**
	 * Construct a TreeAspectAdapter for the specified subject
	 * and tree.
	 */
	protected TreeAspectAdapter(String treeName, Model subject) {
		this(new ReadOnlyPropertyValueModel(subject), treeName);
	}

	/**
	 * Construct a TreeAspectAdapter for the specified subject holder
	 * and tree.
	 */
	protected TreeAspectAdapter(ValueModel subjectHolder, String treeName) {
		super(subjectHolder);
		this.treeName = treeName;
		this.treeChangeListener = this.buildTreeChangeListener();
	}


	// ********** initialization **********

	/**
	 * The subject's tree aspect has changed, notify the listeners.
	 */
	protected TreeChangeListener buildTreeChangeListener() {
		// transform the subject's tree change events into VALUE tree change events
		return new TreeChangeListener() {
			public void nodeAdded(TreeChangeEvent e) {
				TreeAspectAdapter.this.nodeAdded(e);
			}
			public void nodeRemoved(TreeChangeEvent e) {
				TreeAspectAdapter.this.nodeRemoved(e);
			}
			public void treeCleared(TreeChangeEvent e) {
				TreeAspectAdapter.this.treeCleared(e);
			}
			public void treeChanged(TreeChangeEvent e) {
				TreeAspectAdapter.this.treeChanged(e);
			}
			@Override
			public String toString() {
				return "tree change listener: " + TreeAspectAdapter.this.treeName;
			}
		};
	}


	// ********** TreeValueModel implementation **********

	/**
	 * Return the value of the subject's tree aspect.
	 * This should be an *iterator* on the tree.
	 */
	public Iterator nodes() {
		if (this.subject == null) {
			return EmptyIterator.instance();
		}
		return this.getValueFromSubject();
	}

	/**
	 * Return the value of the subject's tree aspect.
	 * This should be an *iterator* on the tree.
	 * At this point we can be sure that the subject is not null.
	 * @see #value()
	 */
	protected Iterator getValueFromSubject() {	// private-protected
		throw new UnsupportedOperationException();
	}

	/**
	 * Insert the specified node in the subject's tree aspect.
	 */
	public void add(Object[] parentPath, Object node) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Remove the specified node from the subject's tree aspect.
	 */
	public void remove(Object[] path) {
		throw new UnsupportedOperationException();
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object value() {
		return this.nodes();
	}

	@Override
	protected Class<? extends ChangeListener> listenerClass() {
		return TreeChangeListener.class;
	}

	@Override
	protected String listenerAspectName() {
		return NODES;
	}

    @Override
	protected boolean hasListeners() {
		return this.hasAnyTreeChangeListeners(NODES);
	}

    @Override
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.fireTreeChanged(NODES);
	}

    @Override
	protected void engageNonNullSubject() {
		((Model) this.subject).addTreeChangeListener(this.treeName, this.treeChangeListener);
	}

    @Override
	protected void disengageNonNullSubject() {
		((Model) this.subject).removeTreeChangeListener(this.treeName, this.treeChangeListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.treeName);
	}


	// ********** behavior **********

	protected void nodeAdded(TreeChangeEvent e) {
		this.fireNodeAdded(NODES, e.path());
	}

	protected void nodeRemoved(TreeChangeEvent e) {
		this.fireNodeRemoved(NODES, e.path());
	}

	protected void treeCleared(TreeChangeEvent e) {
		this.fireTreeCleared(NODES);
	}

	protected void treeChanged(TreeChangeEvent e) {
		this.fireTreeChanged(NODES, e.path());
	}

}
