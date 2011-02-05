/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.listeners;

import org.eclipse.jpt.common.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.common.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.common.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.TreeChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another tree change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 */
public class SWTTreeChangeListenerWrapper
	implements TreeChangeListener
{
	private final TreeChangeListener listener;

	public SWTTreeChangeListenerWrapper(TreeChangeListener listener) {
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
			this.executeOnUIThread(this.buildNodeAddedRunnable(event));
		}
	}

	public void nodeRemoved(TreeRemoveEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.nodeRemoved_(event);
		} else {
			this.executeOnUIThread(this.buildNodeRemovedRunnable(event));
		}
	}

	public void treeCleared(TreeClearEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.treeCleared_(event);
		} else {
			this.executeOnUIThread(this.buildTreeClearedRunnable(event));
		}
	}

	public void treeChanged(TreeChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.treeChanged_(event);
		} else {
			this.executeOnUIThread(this.buildTreeChangedRunnable(event));
		}
	}

	private Runnable buildNodeAddedRunnable(final TreeAddEvent event) {
		return new Runnable() {
			public void run() {
				SWTTreeChangeListenerWrapper.this.nodeAdded_(event);
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
				SWTTreeChangeListenerWrapper.this.nodeRemoved_(event);
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
				SWTTreeChangeListenerWrapper.this.treeCleared_(event);
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
				SWTTreeChangeListenerWrapper.this.treeChanged_(event);
			}
			@Override
			public String toString() {
				return "tree changed runnable"; //$NON-NLS-1$
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
		return "SWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
