/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.listeners;

import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;

/**
 * Wrap another collection change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 * 
 * @see SWTPropertyChangeListenerWrapper
 */
public class SWTCollectionChangeListenerWrapper
	implements CollectionChangeListener
{
	private final CollectionChangeListener listener;

	public SWTCollectionChangeListenerWrapper(CollectionChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void itemsAdded(CollectionAddEvent event) {
		this.execute(new ItemsAddedRunnable(event));
	}

	/* CU private */ class ItemsAddedRunnable
		extends RunnableAdapter
	{
		private final CollectionAddEvent event;
		ItemsAddedRunnable(CollectionAddEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTCollectionChangeListenerWrapper.this.itemsAdded_(this.event);
		}
	}

	void itemsAdded_(CollectionAddEvent event) {
		this.listener.itemsAdded(event);
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		this.execute(new ItemsRemovedRunnable(event));
	}

	/* CU private */ class ItemsRemovedRunnable
		extends RunnableAdapter
	{
		private final CollectionRemoveEvent event;
		ItemsRemovedRunnable(CollectionRemoveEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTCollectionChangeListenerWrapper.this.itemsRemoved_(this.event);
		}
	}

	void itemsRemoved_(CollectionRemoveEvent event) {
		this.listener.itemsRemoved(event);
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.execute(new CollectionClearedRunnable(event));
	}

	/* CU private */ class CollectionClearedRunnable
		extends RunnableAdapter
	{
		private final CollectionClearEvent event;
		CollectionClearedRunnable(CollectionClearEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTCollectionChangeListenerWrapper.this.collectionCleared_(this.event);
		}
	}

	void collectionCleared_(CollectionClearEvent event) {
		this.listener.collectionCleared(event);
	}

	public void collectionChanged(CollectionChangeEvent event) {
		this.execute(new CollectionChangedRunnable(event));
	}

	/* CU private */ class CollectionChangedRunnable
		extends RunnableAdapter
	{
		private final CollectionChangeEvent event;
		CollectionChangedRunnable(CollectionChangeEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTCollectionChangeListenerWrapper.this.collectionChanged_(this.event);
		}
	}

	void collectionChanged_(CollectionChangeEvent event) {
		this.listener.collectionChanged(event);
	}

	/**
	 * {@link DisplayTools#execute(Runnable)} seems to work OK;
	 * but using {@link DisplayTools#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void execute(Runnable r) {
		DisplayTools.execute(r);
//		SWTUtil.syncExec(r);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener + ')'; //$NON-NLS-1$
	}
}
