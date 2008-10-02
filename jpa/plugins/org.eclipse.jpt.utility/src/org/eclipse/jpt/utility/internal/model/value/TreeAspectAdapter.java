/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.TreeValueModel;

/**
 * This extension of PropertyAdapter provides TreeChange support.
 * This allows us to convert a set of one or more trees into
 * a single tree, NODES.
 * 
 * The typical subclass will override the following methods:
 * #nodes_()
 *     at the very minimum, override this method to return an iterator
 *     on the subject's tree aspect; it does not need to be overridden if
 *     #nodes() is overridden and its behavior changed
 * #nodes()
 *     override this method only if returning an empty iterator when the
 *     subject is null is unacceptable
 */
public abstract class TreeAspectAdapter<S extends Model, E>
	extends AspectAdapter<S>
	implements TreeValueModel<E>
{
	/**
	 * The name of the subject's trees that we use for the value.
	 */
	protected final String[] treeNames;
		protected static final String[] EMPTY_TREE_NAMES = new String[0];

	/** A listener that listens to the subject's tree aspect. */
	protected final TreeChangeListener treeChangeListener;


	// ********** constructors **********

	/**
	 * Construct a TreeAspectAdapter for the specified subject
	 * and tree.
	 */
	protected TreeAspectAdapter(String treeName, S subject) {
		this(new String[] {treeName}, subject);
	}

	/**
	 * Construct a TreeAspectAdapter for the specified subject
	 * and trees.
	 */
	protected TreeAspectAdapter(String[] treeNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), treeNames);
	}

	/**
	 * Construct a TreeAspectAdapter for the specified subject holder
	 * and trees.
	 */
	protected TreeAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... treeNames) {
		super(subjectHolder);
		this.treeNames = treeNames;
		this.treeChangeListener = this.buildTreeChangeListener();
	}

	/**
	 * Construct a TreeAspectAdapter for the specified subject holder
	 * and trees.
	 */
	protected TreeAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> treeNames) {
		this(subjectHolder, treeNames.toArray(new String[treeNames.size()]));
	}

	/**
	 * Construct a TreeAspectAdapter for an "unchanging" tree in
	 * the specified subject. This is useful for a tree aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new tree.
	 */
	protected TreeAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_TREE_NAMES);
	}


	// ********** initialization **********

	/**
	 * The subject's tree aspect has changed, notify the listeners.
	 */
	protected TreeChangeListener buildTreeChangeListener() {
		// transform the subject's tree change events into VALUE tree change events
		return new TreeChangeListener() {
			public void nodeAdded(TreeChangeEvent event) {
				TreeAspectAdapter.this.nodeAdded(event);
			}
			public void nodeRemoved(TreeChangeEvent event) {
				TreeAspectAdapter.this.nodeRemoved(event);
			}
			public void treeCleared(TreeChangeEvent event) {
				TreeAspectAdapter.this.treeCleared(event);
			}
			public void treeChanged(TreeChangeEvent event) {
				TreeAspectAdapter.this.treeChanged(event);
			}
			@Override
			public String toString() {
				return "tree change listener: " + Arrays.asList(TreeAspectAdapter.this.treeNames);
			}
		};
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
	 * At this point we can be sure that the subject is not null.
	 * @see #nodes()
	 */
	protected Iterator<E> nodes_() {
		throw new UnsupportedOperationException();
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object getValue() {
		return this.nodes();
	}

	@Override
	protected Class<? extends ChangeListener> getListenerClass() {
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
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.fireTreeChanged(NODES);
	}

    @Override
	protected void engageSubject_() {
    	for (String treeName : this.treeNames) {
			((Model) this.subject).addTreeChangeListener(treeName, this.treeChangeListener);
		}
	}

    @Override
	protected void disengageSubject_() {
    	for (String treeName : this.treeNames) {
			((Model) this.subject).removeTreeChangeListener(treeName, this.treeChangeListener);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		for (int i = 0; i < this.treeNames.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(this.treeNames[i]);
		}
	}


	// ********** behavior **********

	protected void nodeAdded(TreeChangeEvent event) {
		this.fireNodeAdded(NODES, event.getPath());
	}

	protected void nodeRemoved(TreeChangeEvent event) {
		this.fireNodeRemoved(NODES, event.getPath());
	}

	protected void treeCleared(TreeChangeEvent event) {
		this.fireTreeCleared(NODES);
	}

	protected void treeChanged(TreeChangeEvent event) {
		this.fireTreeChanged(NODES, event.getPath());
	}

}
