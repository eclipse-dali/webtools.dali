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

import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;

/**
 * Wrap another tree change listener and forward events to it on the AWT
 * event queue, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
 */
public final class AWTTreeChangeListenerWrapper
	implements TreeChangeListener
{
	private final TreeChangeListener listener;

	public AWTTreeChangeListenerWrapper(TreeChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void nodeAdded(TreeAddEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.nodeAdded_(event);
		} else {
			this.executeOnEventQueue(this.buildNodeAddedRunnable(event));
		}
	}

	public void nodeRemoved(TreeRemoveEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.nodeRemoved_(event);
		} else {
			this.executeOnEventQueue(this.buildNodeRemovedRunnable(event));
		}
	}

	public void treeCleared(TreeClearEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.treeCleared_(event);
		} else {
			this.executeOnEventQueue(this.buildTreeClearedRunnable(event));
		}
	}

	public void treeChanged(TreeChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.treeChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildTreeChangedRunnable(event));
		}
	}

	private Runnable buildNodeAddedRunnable(final TreeAddEvent event) {
		return new Runnable() {
			public void run() {
				AWTTreeChangeListenerWrapper.this.nodeAdded_(event);
			}
			@Override
			public String toString() {
				return "node added runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildNodeRemovedRunnable(final TreeRemoveEvent event) {
		return new Runnable() {
			public void run() {
				AWTTreeChangeListenerWrapper.this.nodeRemoved_(event);
			}
			@Override
			public String toString() {
				return "node removed runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildTreeClearedRunnable(final TreeClearEvent event) {
		return new Runnable() {
			public void run() {
				AWTTreeChangeListenerWrapper.this.treeCleared_(event);
			}
			@Override
			public String toString() {
				return "tree cleared runnable"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildTreeChangedRunnable(final TreeChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTTreeChangeListenerWrapper.this.treeChanged_(event);
			}
			@Override
			public String toString() {
				return "tree changed runnable"; //$NON-NLS-1$
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

	void nodeAdded_(TreeAddEvent event) {
		this.listener.nodeAdded(event);
	}

	void nodeRemoved_(TreeRemoveEvent event) {
		this.listener.nodeRemoved(event);
	}

	void treeCleared_(TreeClearEvent event) {
		this.listener.treeCleared(event);
	}

	void treeChanged_(TreeChangeEvent event) {
		this.listener.treeChanged(event);
	}

	@Override
	public String toString() {
		return "AWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
