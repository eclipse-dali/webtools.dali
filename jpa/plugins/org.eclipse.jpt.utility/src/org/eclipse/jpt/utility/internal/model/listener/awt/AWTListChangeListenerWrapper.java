/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
 * event queue.
 * Forward *every* event asynchronously via the UI thread so the listener
 * receives in the same order they were generated.
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

	public void itemsAdded(ListAddEvent event) {
		this.executeOnEventQueue(this.buildItemsAddedRunnable(event));
	}

	public void itemsRemoved(ListRemoveEvent event) {
		this.executeOnEventQueue(this.buildItemsRemovedRunnable(event));
	}

	public void itemsMoved(ListMoveEvent event) {
		this.executeOnEventQueue(this.buildItemsMovedRunnable(event));
	}

	public void itemsReplaced(ListReplaceEvent event) {
		this.executeOnEventQueue(this.buildItemsReplacedRunnable(event));
	}

	public void listCleared(ListClearEvent event) {
		this.executeOnEventQueue(this.buildListClearedRunnable(event));
	}

	public void listChanged(ListChangeEvent event) {
		this.executeOnEventQueue(this.buildListChangedRunnable(event));
	}

	private Runnable buildItemsAddedRunnable(final ListAddEvent event) {
		return new Runnable() {
			public void run() {
				AWTListChangeListenerWrapper.this.itemsAdded_(event);
			}
			@Override
			public String toString() {
				return "items added"; //$NON-NLS-1$
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
				return "items removed"; //$NON-NLS-1$
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
				return "items moved"; //$NON-NLS-1$
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
				return "items replaced"; //$NON-NLS-1$
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
				return "list cleared"; //$NON-NLS-1$
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
				return "list changed"; //$NON-NLS-1$
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
