/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.listener;

import org.eclipse.jpt.common.utility.model.event.ChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.common.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.common.utility.model.event.TreeRemoveEvent;

/**
 * Convenience abstract implementation of {@link ChangeListener}.
 * All change notifications are funneled through a single method.
 * This class can be used by
 * subclassing it and overriding either {@link #modelChanged(ChangeEvent)}
 * (if access to the event is required) or {@link #modelChanged()} (if access
 * to the event is not required).
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class SimpleChangeListener
	implements ChangeListener
{
	public SimpleChangeListener() {
		super();
	}

	public void stateChanged(StateChangeEvent event) {
		this.modelChanged(event);
	}

	public void propertyChanged(PropertyChangeEvent event) {
		this.modelChanged(event);
	}

	public void collectionChanged(CollectionChangeEvent event) {
		this.modelChanged(event);
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.modelChanged(event);
	}

	public void itemsAdded(CollectionAddEvent event) {
		this.modelChanged(event);
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		this.modelChanged(event);
	}

	public void itemsAdded(ListAddEvent event) {
		this.modelChanged(event);
	}

	public void itemsMoved(ListMoveEvent event) {
		this.modelChanged(event);
	}

	public void itemsRemoved(ListRemoveEvent event) {
		this.modelChanged(event);
	}

	public void itemsReplaced(ListReplaceEvent event) {
		this.modelChanged(event);
	}

	public void listChanged(ListChangeEvent event) {
		this.modelChanged(event);
	}

	public void listCleared(ListClearEvent event) {
		this.modelChanged(event);
	}

	public void nodeAdded(TreeAddEvent event) {
		this.modelChanged(event);
	}

	public void nodeRemoved(TreeRemoveEvent event) {
		this.modelChanged(event);
	}

	public void treeChanged(TreeChangeEvent event) {
		this.modelChanged(event);
	}

	public void treeCleared(TreeClearEvent event) {
		this.modelChanged(event);
	}

	/**
	 * The model has notified the listener of the change described by the
	 * specified change event. By default the listener executes {@link #modelChanged()}.
	 */
	protected void modelChanged(@SuppressWarnings("unused") ChangeEvent event) {
		this.modelChanged();
	}

	/**
	 * The model has notified the listener of a change.
	 * By default the listener throws an exception.
	 */
	protected void modelChanged() {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

}
