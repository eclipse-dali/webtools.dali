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

import java.awt.EventQueue;
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
 * AWT-aware implementation of ChangeEventDispatcher interface:
 * If we are executing on the AWT event-dispatch thread,
 * simply forward the change notification directly to the listener.
 * If we are executing on some other thread, queue up the
 * notification on the AWT event queue so it can be executed
 * on the event-dispatch thread (after the pending events have
 * been dispatched).
 */
public class AWTChangeEventDispatcher
	implements ChangeEventDispatcher, Serializable
{
	// singleton
	private static ChangeEventDispatcher INSTANCE;

	private static final long serialVersionUID = 1L;


	/**
	 * Return the singleton.
	 */
	public synchronized static ChangeEventDispatcher instance() {
		if (INSTANCE == null) {
			INSTANCE = new AWTChangeEventDispatcher();
		}
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private AWTChangeEventDispatcher() {
		super();
	}

	public void stateChanged(final StateChangeListener listener, final StateChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			listener.stateChanged(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.propertyChanged(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.itemsAdded(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.itemsRemoved(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.collectionCleared(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.collectionChanged(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.itemsAdded(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.itemsRemoved(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.itemsReplaced(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.itemsMoved(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.listCleared(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.listChanged(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.nodeAdded(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.nodeRemoved(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.treeCleared(event);
		} else {
			this.invoke(
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
		if (EventQueue.isDispatchThread()) {
			listener.treeChanged(event);
		} else {
			this.invoke(
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

	/**
	 * EventQueue.invokeLater(Runnable) seems to work OK;
	 * but using #invokeAndWait() can somtimes make things
	 * more predictable when debugging.
	 */
	private void invoke(Runnable r) {
		EventQueue.invokeLater(r);
//		try {
//			EventQueue.invokeAndWait(r);
//		} catch (InterruptedException ex) {
//			throw new RuntimeException(ex);
//		} catch (java.lang.reflect.InvocationTargetException ex) {
//			throw new RuntimeException(ex);
//		}
	}

	/**
	 * Serializable singleton support
	 */
	private Object readResolve() {
		return instance();
	}

}
