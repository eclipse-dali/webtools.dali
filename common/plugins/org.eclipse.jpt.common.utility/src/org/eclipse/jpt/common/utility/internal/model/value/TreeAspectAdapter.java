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
import org.eclipse.jpt.common.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.common.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.common.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This extension of {@link AspectTreeValueModelAdapter} provides
 * basic tree change support.
 * This converts a set of one or more trees into
 * a single {@link #NODES} tree.
 * <p>
 * The typical subclass will override the following methods (see the descriptions
 * in {@link AspectTreeValueModelAdapter}):<ul>
 * <li>{@link #nodes_()}
 * <li>{@link #nodes()}
 * </ul>
 */
public abstract class TreeAspectAdapter<S extends Model, E>
	extends AspectTreeValueModelAdapter<S, E>
{
	/**
	 * The name of the subject's trees that we use for the value.
	 */
	protected final String[] treeNames;
		protected static final String[] EMPTY_TREE_NAMES = new String[0];

	/** A listener that listens to the subject's tree aspects. */
	protected final TreeChangeListener treeChangeListener;


	// ********** constructors **********

	/**
	 * Construct a tree aspect adapter for the specified subject
	 * and tree.
	 */
	protected TreeAspectAdapter(String treeName, S subject) {
		this(new String[] {treeName}, subject);
	}

	/**
	 * Construct a tree aspect adapter for the specified subject
	 * and trees.
	 */
	protected TreeAspectAdapter(String[] treeNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), treeNames);
	}

	/**
	 * Construct a tree aspect adapter for the specified subject holder
	 * and trees.
	 */
	protected TreeAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... treeNames) {
		super(subjectHolder);
		this.treeNames = treeNames;
		this.treeChangeListener = this.buildTreeChangeListener();
	}

	/**
	 * Construct a tree aspect adapter for the specified subject holder
	 * and trees.
	 */
	protected TreeAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> treeNames) {
		this(subjectHolder, treeNames.toArray(new String[treeNames.size()]));
	}

	/**
	 * Construct a tree aspect adapter for an "unchanging" tree in
	 * the specified subject. This is useful for a tree aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new tree.
	 */
	protected TreeAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_TREE_NAMES);
	}


	// ********** initialization **********

	protected TreeChangeListener buildTreeChangeListener() {
		// transform the subject's tree change events into VALUE tree change events
		return new TreeChangeListener() {
			public void nodeAdded(TreeAddEvent event) {
				TreeAspectAdapter.this.nodeAdded(event);
			}
			public void nodeRemoved(TreeRemoveEvent event) {
				TreeAspectAdapter.this.nodeRemoved(event);
			}
			public void treeCleared(TreeClearEvent event) {
				TreeAspectAdapter.this.treeCleared(event);
			}
			public void treeChanged(TreeChangeEvent event) {
				TreeAspectAdapter.this.treeChanged(event);
			}
			@Override
			public String toString() {
				return "tree change listener: " + Arrays.asList(TreeAspectAdapter.this.treeNames); //$NON-NLS-1$
			}
		};
	}


	// ********** AspectAdapter implementation **********

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


	// ********** behavior **********

	protected void nodeAdded(TreeAddEvent event) {
		this.fireNodeAdded(event.clone(this, NODES));
	}

	protected void nodeRemoved(TreeRemoveEvent event) {
		this.fireNodeRemoved(event.clone(this, NODES));
	}

	protected void treeCleared(TreeClearEvent event) {
		this.fireTreeCleared(event.clone(this, NODES));
	}

	protected void treeChanged(TreeChangeEvent event) {
		this.fireTreeChanged(event.clone(this, NODES));
	}

}
