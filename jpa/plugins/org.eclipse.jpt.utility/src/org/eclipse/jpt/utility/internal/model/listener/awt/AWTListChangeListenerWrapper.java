/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener.awt;

import java.awt.EventQueue;

import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;

/**
 * Wrap another list change listener and forward events to it on the AWT
 * event queue, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
 */
public final class AWTListChangeListenerWrapper
	implements ListChangeListener
{
	private final ListChangeListener listener;

	public AWTListChangeListenerWrapper(ListChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void itemsAdded(ListAddEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsAdded_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsAddedRunnable(event));
		}
	}

	public void itemsRemoved(ListRemoveEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsRemoved_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsRemovedRunnable(event));
		}
	}

	public void itemsMoved(ListMoveEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsMoved_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsMovedRunnable(event));
		}
	}

	public void itemsReplaced(ListReplaceEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsReplaced_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsReplacedRunnable(event));
		}
	}

	public void listCleared(ListClearEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.listCleared_(event);
		} else {
			this.executeOnEventQueue(this.buildListClearedRunnable(event));
		}
	}

	public void listChanged(ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.listChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildListChangedRunnable(event));
		}
	}

	private Runnable buildItemsAddedRunnable(final ListAddEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsAdded_(event);
			}
			@Override
			public String toString() {
				return "items added runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildItemsRemovedRunnable(final ListRemoveEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsRemoved_(event);
			}
			@Override
			public String toString() {
				return "items removed runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildItemsMovedRunnable(final ListMoveEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsMoved_(event);
			}
			@Override
			public String toString() {
				return "items moved runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildItemsReplacedRunnable(final ListReplaceEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsReplaced_(event);
			}
			@Override
			public String toString() {
				return "items replaced runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildListClearedRunnable(final ListClearEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.listCleared_(event);
			}
			@Override
			public String toString() {
				return "list cleared runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildListChangedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.listChanged_(event);
			}
			@Override
			public String toString() {
				return "list changed runnable"; //$NON-NLS-1$
			}
		};
	}

	private boolean isExecutingOnUIThread() {
		return EventQueue.isDispatchThread();
	}

	/**
	 * {@link EventQueue#invokeLater(Runnable)} seems to work OK;
	 * but using {@link EventQueue#invokeAndWait(Runnable)} can sometimes make
	 * things more predictable when debugging, at the risk of deadlocks.
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

	void itemsAdded_(ListAddEvent event) {
		this.listener.itemsAdded(event);
	}

	void itemsRemoved_(ListRemoveEvent event) {
		this.listener.itemsRemoved(event);
	}

	void itemsMoved_(ListMoveEvent event) {
		this.listener.itemsMoved(event);
	}

	void itemsReplaced_(ListReplaceEvent event) {
		this.listener.itemsReplaced(event);
	}

	void listCleared_(ListClearEvent event) {
		this.listener.listCleared(event);
	}

	void listChanged_(ListChangeEvent event) {
		this.listener.listChanged(event);
	}

	@Override
	public String toString() {
		return "AWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
