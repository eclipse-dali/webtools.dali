/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import java.io.Serializable;

import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.TreeChangeListener;

/**
 * UI-aware implementation of ChangeEventDispatcher interface:
 * If we are executing on the UI event-dispatch thread,
 * simply forward the change notification directly to the listener.
 * If we are executing on some other thread, queue up the
 * notification on the UI event queue so it can be executed
 * on the event-dispatch thread (after the pending events have
 * been dispatched).
 */
public class UIChangeEventDispatcher
	implements ChangeEventDispatcher, Serializable
{
	/**
	 * This adapter will provide the platform-specific behavior
	 * (e.g. AWT, SWT).
	 */
	private final PlatformAdapter platformAdapter;

	private static final long serialVersionUID = 1L;


	public UIChangeEventDispatcher(PlatformAdapter platformAdapter) {
		super();
		if (platformAdapter == null) {
			throw new NullPointerException();
		}
		this.platformAdapter = platformAdapter;
	}


	// ********** ChangeEventDispatcher implementation **********

	public void stateChanged(final StateChangeListener listener, final StateChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.stateChanged(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.stateChanged(event);
					}
					@Override
					public String toString() {
						return "stateChanged";
					}
				}
			);
		}
	}

	public void propertyChanged(final PropertyChangeListener listener, final PropertyChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.propertyChanged(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.propertyChanged(event);
					}
					@Override
					public String toString() {
						return "propertyChanged";
					}
				}
			);
		}
	}

	public void itemsAdded(final CollectionChangeListener listener, final CollectionChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.itemsAdded(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.itemsAdded(event);
					}
					@Override
					public String toString() {
						return "itemsAdded (Collection)";
					}
				}
			);
		}
	}

	public void itemsRemoved(final CollectionChangeListener listener, final CollectionChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.itemsRemoved(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.itemsRemoved(event);
					}
					@Override
					public String toString() {
						return "itemsRemoved (Collection)";
					}
				}
			);
		}
	}

	public void collectionCleared(final CollectionChangeListener listener, final CollectionChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.collectionCleared(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.collectionCleared(event);
					}
					@Override
					public String toString() {
						return "collectionCleared";
					}
				}
			);
		}
	}

	public void collectionChanged(final CollectionChangeListener listener, final CollectionChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.collectionChanged(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.collectionChanged(event);
					}
					@Override
					public String toString() {
						return "collectionChanged";
					}
				}
			);
		}
	}

	public void itemsAdded(final ListChangeListener listener, final ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.itemsAdded(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.itemsAdded(event);
					}
					@Override
					public String toString() {
						return "itemsAdded (List)";
					}
				}
			);
		}
	}

	public void itemsRemoved(final ListChangeListener listener, final ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.itemsRemoved(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.itemsRemoved(event);
					}
					@Override
					public String toString() {
						return "itemsRemoved (List)";
					}
				}
			);
		}
	}

	public void itemsReplaced(final ListChangeListener listener, final ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.itemsReplaced(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.itemsReplaced(event);
					}
					@Override
					public String toString() {
						return "itemsReplaced (List)";
					}
				}
			);
		}
	}

	public void itemsMoved(final ListChangeListener listener, final ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.itemsMoved(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.itemsMoved(event);
					}
					@Override
					public String toString() {
						return "itemsMoved (List)";
					}
				}
			);
		}
	}

	public void listCleared(final ListChangeListener listener, final ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.listCleared(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.listCleared(event);
					}
					@Override
					public String toString() {
						return "listCleared";
					}
				}
			);
		}
	}

	public void listChanged(final ListChangeListener listener, final ListChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.listChanged(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.listChanged(event);
					}
					@Override
					public String toString() {
						return "listChanged";
					}
				}
			);
		}
	}

	public void nodeAdded(final TreeChangeListener listener, final TreeChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.nodeAdded(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.nodeAdded(event);
					}
					@Override
					public String toString() {
						return "nodeAdded";
					}
				}
			);
		}
	}

	public void nodeRemoved(final TreeChangeListener listener, final TreeChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.nodeRemoved(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.nodeRemoved(event);
					}
					@Override
					public String toString() {
						return "nodeRemoved";
					}
				}
			);
		}
	}

	public void treeCleared(final TreeChangeListener listener, final TreeChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.treeCleared(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.treeCleared(event);
					}
					@Override
					public String toString() {
						return "treeCleared";
					}
				}
			);
		}
	}

	public void treeChanged(final TreeChangeListener listener, final TreeChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			listener.treeChanged(event);
		} else {
			this.executeOnUIThread(
				new Runnable() {
					public void run() {
						listener.treeChanged(event);
					}
					@Override
					public String toString() {
						return "treeChanged";
					}
				}
			);
		}
	}


	// ********** internal methods **********

	private boolean isExecutingOnUIThread() {
		return this.platformAdapter.currentThreadIsUIThread();
	}

	private void executeOnUIThread(Runnable r) {
		this.platformAdapter.executeOnUIThread(r);
	}


	// ********** platform adapter **********

	/**
	 * Define the UI platform-specific methods required by the UI change event
	 * dispatcher.
	 */
	public interface PlatformAdapter {

		/**
		 * Return whether the current thread is the UI event dispatch thread.
		 */
		boolean currentThreadIsUIThread();

		/**
		 * Execute the specified runnable on the UI event dispatch thread.
		 */
		void executeOnUIThread(Runnable r);

	}

}
