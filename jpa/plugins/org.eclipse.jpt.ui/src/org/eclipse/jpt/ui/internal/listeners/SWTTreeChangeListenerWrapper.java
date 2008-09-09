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

import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another tree change listener and forward events to it on the SWT
 * UI thread.
 * Forward *every* event asynchronously via the UI thread so the listener
 * receives in the same order they were generated.
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

	public void nodeAdded(TreeChangeEvent event) {
		this.executeOnUIThread(this.buildNodeAddedRunnable(event));
	}

	public void nodeRemoved(TreeChangeEvent event) {
		this.executeOnUIThread(this.buildNodeRemovedRunnable(event));
	}

	public void treeCleared(TreeChangeEvent event) {
		this.executeOnUIThread(this.buildTreeClearedRunnable(event));
	}

	public void treeChanged(TreeChangeEvent event) {
		this.executeOnUIThread(this.buildTreeChangedRunnable(event));
	}

	private Runnable buildNodeAddedRunnable(final TreeChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTTreeChangeListenerWrapper.this.nodeAdded_(event);
			}
			@Override
			public String toString() {
				return "node added"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildNodeRemovedRunnable(final TreeChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTTreeChangeListenerWrapper.this.nodeRemoved_(event);
			}
			@Override
			public String toString() {
				return "node removed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildTreeClearedRunnable(final TreeChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTTreeChangeListenerWrapper.this.treeCleared_(event);
			}
			@Override
			public String toString() {
				return "tree cleared"; //$NON-NLS-1$
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
				return "tree changed"; //$NON-NLS-1$
			}
		};
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

	void nodeAdded_(TreeChangeEvent event) {
		this.listener.nodeAdded(event);
	}

	void nodeRemoved_(TreeChangeEvent event) {
		this.listener.nodeRemoved(event);
	}

	void treeCleared_(TreeChangeEvent event) {
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
