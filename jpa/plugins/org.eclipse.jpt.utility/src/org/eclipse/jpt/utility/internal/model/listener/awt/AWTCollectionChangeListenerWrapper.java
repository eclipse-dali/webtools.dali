/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener.awt;

import java.awt.EventQueue;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;

/**
 * Wrap another collection change listener and forward events to it on the AWT
 * event queue.
 */
public class AWTCollectionChangeListenerWrapper
	implements CollectionChangeListener
{
	private final CollectionChangeListener listener;

	public AWTCollectionChangeListenerWrapper(CollectionChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void itemsAdded(CollectionChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.itemsAdded_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsAddedRunnable(event));
		}
	}

	public void itemsRemoved(CollectionChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.itemsRemoved_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsRemovedRunnable(event));
		}
	}

	public void collectionCleared(CollectionChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.collectionCleared_(event);
		} else {
			this.executeOnEventQueue(this.buildCollectionClearedRunnable(event));
		}
	}

	public void collectionChanged(CollectionChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.collectionChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildCollectionChangedRunnable(event));
		}
	}

	private Runnable buildItemsAddedRunnable(final CollectionChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTCollectionChangeListenerWrapper.this.itemsAdded_(event);
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
				AWTCollectionChangeListenerWrapper.this.itemsRemoved_(event);
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
				AWTCollectionChangeListenerWrapper.this.collectionCleared_(event);
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
				AWTCollectionChangeListenerWrapper.this.collectionChanged_(event);
			}
			@Override
			public String toString() {
				return "collection changed";
			}
		};
	}

	/**
	 * EventQueue#invokeLater(Runnable) seems to work OK;
	 * but using #invokeAndWait(Runnable) can sometimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void executeOnEventQueue(Runnable r) {
		EventQueue.invokeLater(r);
//		try {
//			EventQueue.invokeAndWait(r);
//		} catch (InterruptedException ex) {
//			throw new RuntimeException(ex);
//		} catch (java.lang.reflect.InvocationTargetException ex) {
//			throw new RuntimeException(ex);
//		}
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
		return "AWT(" + this.listener.toString() + ")";
	}

}
