/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.listeners;

import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another list change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
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

	public void itemsAdded(ListAddEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsAdded_(event);
		} else {
			this.executeOnUIThread(this.buildItemsAddedRunnable(event));
		}
	}

	public void itemsRemoved(ListRemoveEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsRemoved_(event);
		} else {
			this.executeOnUIThread(this.buildItemsRemovedRunnable(event));
		}
	}

	public void itemsMoved(ListMoveEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsMoved_(event);
		} else {
			this.executeOnUIThread(this.buildItemsMovedRunnable(event));
		}
	}

	public void itemsReplaced(ListReplaceEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsReplaced_(event);
		} else {
			this.executeOnUIThread(this.buildItemsReplacedRunnable(event));
		}
	}

	public void listCleared(ListClearEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.listCleared_(event);
		} else {
			this.executeOnUIThread(this.buildListClearedRunnable(event));
		}
	}

	public void listChanged(ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.listChanged_(event);
		} else {
			this.executeOnUIThread(this.buildListChangedRunnable(event));
		}
	}

	private Runnable buildItemsAddedRunnable(final ListAddEvent event) {
		return new Runnable() {
			public void run() {
				SWTListChangeListenerWrapper.this.itemsAdded_(event);
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
				SWTListChangeListenerWrapper.this.itemsRemoved_(event);
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
				SWTListChangeListenerWrapper.this.itemsMoved_(event);
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
				SWTListChangeListenerWrapper.this.itemsReplaced_(event);
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
				SWTListChangeListenerWrapper.this.listCleared_(event);
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
				SWTListChangeListenerWrapper.this.listChanged_(event);
			}
			@Override
			public String toString() {
				return "list changed"; //$NON-NLS-1$
			}
		};
	}

	private boolean isExecutingOnUIThread() {
		return Display.getCurrent() != null;
	}

	/**
	 * {@link Display#asyncExec(Runnable)} seems to work OK;
	 * but using {@link Display#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void executeOnUIThread(Runnable r) {
		Display.getDefault().asyncExec(r);
//		Display.getDefault().syncExec(r);
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
		return "SWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
