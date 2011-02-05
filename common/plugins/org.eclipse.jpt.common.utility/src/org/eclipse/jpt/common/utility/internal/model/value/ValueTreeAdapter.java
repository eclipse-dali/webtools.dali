/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.common.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.common.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * Extend {@link ValueAspectAdapter} to listen to one or more
 * tree aspects of the value in the wrapped value model.
 */
public class ValueTreeAdapter<V extends Model>
	extends ValueAspectAdapter<V>
{
	/** The names of the value's trees that we listen to. */
	protected final String[] treeNames;

	/** Listener that listens to the value. */
	protected final TreeChangeListener valueTreeListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value trees.
	 */
	public ValueTreeAdapter(WritablePropertyValueModel<V> valueHolder, String... treeNames) {
		super(valueHolder);
		this.treeNames = treeNames;
		this.valueTreeListener = this.buildValueTreeListener();
	}


	// ********** initialization **********

	protected TreeChangeListener buildValueTreeListener() {
		return new TreeChangeListener() {
			public void nodeAdded(TreeAddEvent event) {
				ValueTreeAdapter.this.nodeAdded(event);
			}
			public void nodeRemoved(TreeRemoveEvent event) {
				ValueTreeAdapter.this.nodeRemoved(event);
			}
			public void treeCleared(TreeClearEvent event) {
				ValueTreeAdapter.this.treeCleared(event);
			}
			public void treeChanged(TreeChangeEvent event) {
				ValueTreeAdapter.this.treeChanged(event);
			}
			@Override
			public String toString() {
				return "value tree listener: " + Arrays.asList(ValueTreeAdapter.this.treeNames); //$NON-NLS-1$
			}
		};
	}


	// ********** ValueAspectAdapter implementation **********

	@Override
	protected void engageValue_() {
		for (String treeName : this.treeNames) {
			this.value.addTreeChangeListener(treeName, this.valueTreeListener);
		}
	}

	@Override
	protected void disengageValue_() {
		for (String treeName : this.treeNames) {
			this.value.removeTreeChangeListener(treeName, this.valueTreeListener);
		}
	}


	// ********** change events **********

	protected void nodeAdded(@SuppressWarnings("unused") TreeAddEvent event) {
		this.valueAspectChanged();
	}

	protected void nodeRemoved(@SuppressWarnings("unused") TreeRemoveEvent event) {
		this.valueAspectChanged();
	}

	protected void treeCleared(@SuppressWarnings("unused") TreeClearEvent event) {
		this.valueAspectChanged();
	}

	protected void treeChanged(@SuppressWarnings("unused") TreeChangeEvent event) {
		this.valueAspectChanged();
	}

}
