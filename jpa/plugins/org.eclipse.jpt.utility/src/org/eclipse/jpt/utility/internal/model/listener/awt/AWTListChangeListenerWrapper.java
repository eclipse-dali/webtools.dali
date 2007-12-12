/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener.awt;

import java.awt.EventQueue;

import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * Wrap another list change listener and forward events to it on the AWT
 * event queue.
 */
public class AWTListChangeListenerWrapper
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

	public void itemsAdded(ListChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.itemsAdded_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsAddedRunnable(event));
		}
	}

	public void itemsRemoved(ListChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.itemsRemoved_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsRemovedRunnable(event));
		}
	}

	public void itemsMoved(ListChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.itemsMoved_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsMovedRunnable(event));
		}
	}

	public void itemsReplaced(ListChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.itemsReplaced_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsReplacedRunnable(event));
		}
	}

	public void listCleared(ListChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.listCleared_(event);
		} else {
			this.executeOnEventQueue(this.buildListClearedRunnable(event));
		}
	}

	public void listChanged(ListChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.listChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildListChangedRunnable(event));
		}
	}

	private Runnable buildItemsAddedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsAdded_(event);
			}
			@Override
			public String toString() {
				return "items added";
			}
		};
	}

	private Runnable buildItemsRemovedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsRemoved_(event);
			}
			@Override
			public String toString() {
				return "items removed";
			}
		};
	}

	private Runnable buildItemsMovedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsMoved_(event);
			}
			@Override
			public String toString() {
				return "items moved";
			}
		};
	}

	private Runnable buildItemsReplacedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsReplaced_(event);
			}
			@Override
			public String toString() {
				return "items replaced";
			}
		};
	}

	private Runnable buildListClearedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.listCleared_(event);
			}
			@Override
			public String toString() {
				return "list cleared";
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
				return "list changed";
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

	void itemsAdded_(ListChangeEvent event) {
		this.listener.itemsAdded(event);
	}

	void itemsRemoved_(ListChangeEvent event) {
		this.listener.itemsRemoved(event);
	}

	void itemsMoved_(ListChangeEvent event) {
		this.listener.itemsMoved(event);
	}

	void itemsReplaced_(ListChangeEvent event) {
		this.listener.itemsReplaced(event);
	}

	void listCleared_(ListChangeEvent event) {
		this.listener.listCleared(event);
	}

	void listChanged_(ListChangeEvent event) {
		this.listener.listChanged(event);
	}

	@Override
	public String toString() {
		return "AWT(" + this.listener.toString() + ")";
	}

}
