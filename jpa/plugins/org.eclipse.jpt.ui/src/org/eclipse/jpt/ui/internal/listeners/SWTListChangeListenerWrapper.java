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

import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another list change listener and forward events to it on the SWT
 * UI thread.
 */
public class SWTListChangeListenerWrapper
	implements ListChangeListener
{
	private final ListChangeListener listener;

	public SWTListChangeListenerWrapper(ListChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void itemsAdded(ListChangeEvent event) {
		if (this.isExecutingUIThread()) {
			this.itemsAdded_(event);
		} else {
			this.executeOnUIThread(this.buildItemsAddedRunnable(event));
		}
	}

	public void itemsRemoved(ListChangeEvent event) {
		if (this.isExecutingUIThread()) {
			this.itemsRemoved_(event);
		} else {
			this.executeOnUIThread(this.buildItemsRemovedRunnable(event));
		}
	}

	public void itemsMoved(ListChangeEvent event) {
		if (this.isExecutingUIThread()) {
			this.itemsMoved_(event);
		} else {
			this.executeOnUIThread(this.buildItemsMovedRunnable(event));
		}
	}

	public void itemsReplaced(ListChangeEvent event) {
		if (this.isExecutingUIThread()) {
			this.itemsReplaced_(event);
		} else {
			this.executeOnUIThread(this.buildItemsReplacedRunnable(event));
		}
	}

	public void listCleared(ListChangeEvent event) {
		if (this.isExecutingUIThread()) {
			this.listCleared_(event);
		} else {
			this.executeOnUIThread(this.buildCollectionClearedRunnable(event));
		}
	}

	public void listChanged(ListChangeEvent event) {
		if (this.isExecutingUIThread()) {
			this.listChanged_(event);
		} else {
			this.executeOnUIThread(this.buildCollectionChangedRunnable(event));
		}
	}

	private Runnable buildItemsAddedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTListChangeListenerWrapper.this.itemsAdded_(event);
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
				SWTListChangeListenerWrapper.this.itemsRemoved_(event);
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
				SWTListChangeListenerWrapper.this.itemsMoved_(event);
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
				SWTListChangeListenerWrapper.this.itemsReplaced_(event);
			}
			@Override
			public String toString() {
				return "items replaced";
			}
		};
	}

	private Runnable buildCollectionClearedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTListChangeListenerWrapper.this.listCleared_(event);
			}
			@Override
			public String toString() {
				return "list cleared";
			}
		};
	}

	private Runnable buildCollectionChangedRunnable(final ListChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTListChangeListenerWrapper.this.listChanged_(event);
			}
			@Override
			public String toString() {
				return "list changed";
			}
		};
	}

	private boolean isExecutingUIThread() {
		return Display.getCurrent() != null;
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
		return "SWT(" + this.listener.toString() + ")";
	}

}
