/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.listeners;

import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another collection change listener and forward events to it on the SWT
 * UI thread.
 * Forward *every* event asynchronously via the UI thread so the listener
 * receives in the same order they were generated.
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

	public void itemsAdded(CollectionChangeEvent event) {
		this.executeOnUIThread(this.buildItemsAddedRunnable(event));
	}

	public void itemsRemoved(CollectionChangeEvent event) {
		this.executeOnUIThread(this.buildItemsRemovedRunnable(event));
	}

	public void collectionCleared(CollectionChangeEvent event) {
		this.executeOnUIThread(this.buildCollectionClearedRunnable(event));
	}

	public void collectionChanged(CollectionChangeEvent event) {
		this.executeOnUIThread(this.buildCollectionChangedRunnable(event));
	}

	private Runnable buildItemsAddedRunnable(final CollectionChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTCollectionChangeListenerWrapper.this.itemsAdded_(event);
			}
			@Override
			public String toString() {
				return "items added";
			}
		};
	}

	private Runnable buildItemsRemovedRunnable(final CollectionChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTCollectionChangeListenerWrapper.this.itemsRemoved_(event);
			}
			@Override
			public String toString() {
				return "items removed";
			}
		};
	}

	private Runnable buildCollectionClearedRunnable(final CollectionChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTCollectionChangeListenerWrapper.this.collectionCleared_(event);
			}
			@Override
			public String toString() {
				return "collection cleared";
			}
		};
	}

	private Runnable buildCollectionChangedRunnable(final CollectionChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTCollectionChangeListenerWrapper.this.collectionChanged_(event);
			}
			@Override
			public String toString() {
				return "collection changed";
			}
		};
	}

	/**
	 * Display#asyncExec(Runnable) seems to work OK;
	 * but using #syncExec(Runnable) can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void executeOnUIThread(Runnable r) {
		Display.getDefault().asyncExec(r);
//		Display.getDefault().syncExec(r);
	}

	void itemsAdded_(CollectionChangeEvent event) {
		this.listener.itemsAdded(event);
	}

	void itemsRemoved_(CollectionChangeEvent event) {
		this.listener.itemsRemoved(event);
	}

	void collectionCleared_(CollectionChangeEvent event) {
		this.listener.collectionCleared(event);
	}

	void collectionChanged_(CollectionChangeEvent event) {
		this.listener.collectionChanged(event);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener.toString() + ")";
	}

}
