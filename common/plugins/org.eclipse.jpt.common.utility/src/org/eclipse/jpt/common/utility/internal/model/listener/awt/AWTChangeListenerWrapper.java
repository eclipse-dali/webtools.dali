/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.listener.awt;

import java.awt.EventQueue;
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
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;

/**
 * Wrap another change listener and forward events to it on the AWT
 * event queue, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
 */
public final class AWTChangeListenerWrapper
	implements ChangeListener
{
	private final ChangeListener listener;


	public AWTChangeListenerWrapper(ChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void stateChanged(StateChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.stateChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildStateChangedRunnable(event));
		}
	}

	public void propertyChanged(PropertyChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.propertyChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildPropertyChangedRunnable(event));
		}
	}

	public void itemsAdded(CollectionAddEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsAdded_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsAddedRunnable(event));
		}
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.itemsRemoved_(event);
		} else {
			this.executeOnEventQueue(this.buildItemsRemovedRunnable(event));
		}
	}

	public void collectionCleared(CollectionClearEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.collectionCleared_(event);
		} else {
			this.executeOnEventQueue(this.buildCollectionClearedRunnable(event));
		}
	}

	public void collectionChanged(CollectionChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.collectionChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildCollectionChangedRunnable(event));
		}
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

	private Runnable buildStateChangedRunnable(final StateChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTChangeListenerWrapper.this.stateChanged_(event);
			}
		};
	}

	private Runnable buildPropertyChangedRunnable(final PropertyChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTChangeListenerWrapper.this.propertyChanged_(event);
			}
		};
	}

	private Runnable buildItemsAddedRunnable(final CollectionAddEvent event) {
		return new Runnable() {
			public void run() {
				AWTChangeListenerWrapper.this.itemsAdded_(event);
			}
			@Override
			public String toString() {
				return "items added runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildItemsRemovedRunnable(final CollectionRemoveEvent event) {
		return new Runnable() {
			public void run() {
				AWTChangeListenerWrapper.this.itemsRemoved_(event);
			}
			@Override
			public String toString() {
				return "items removed runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildCollectionClearedRunnable(final CollectionClearEvent event) {
		return new Runnable() {
			public void run() {
				AWTChangeListenerWrapper.this.collectionCleared_(event);
			}
			@Override
			public String toString() {
				return "collection cleared runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildCollectionChangedRunnable(final CollectionChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTChangeListenerWrapper.this.collectionChanged_(event);
			}
			@Override
			public String toString() {
				return "collection changed runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildItemsAddedRunnable(final ListAddEvent event) {
		return new Runnable() {
			public void run() {
				AWTChangeListenerWrapper.this.itemsAdded_(event);
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
				AWTChangeListenerWrapper.this.itemsRemoved_(event);
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
				AWTChangeListenerWrapper.this.itemsMoved_(event);
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
				AWTChangeListenerWrapper.this.itemsReplaced_(event);
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
				AWTChangeListenerWrapper.this.listCleared_(event);
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
				AWTChangeListenerWrapper.this.listChanged_(event);
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

	void stateChanged_(StateChangeEvent event) {
		this.listener.stateChanged(event);
	}

	void propertyChanged_(PropertyChangeEvent event) {
		this.listener.propertyChanged(event);
	}

	void itemsAdded_(CollectionAddEvent event) {
		this.listener.itemsAdded(event);
	}

	void itemsRemoved_(CollectionRemoveEvent event) {
		this.listener.itemsRemoved(event);
	}

	void collectionCleared_(CollectionClearEvent event) {
		this.listener.collectionCleared(event);
	}

	void collectionChanged_(CollectionChangeEvent event) {
		this.listener.collectionChanged(event);
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
