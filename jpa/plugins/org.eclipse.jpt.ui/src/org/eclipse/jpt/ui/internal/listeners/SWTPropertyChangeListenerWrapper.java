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

import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another property change listener and forward events to it on the SWT
 * UI thread.
 */
public class SWTPropertyChangeListenerWrapper
	implements PropertyChangeListener
{
	private final PropertyChangeListener listener;

	public SWTPropertyChangeListenerWrapper(PropertyChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void propertyChanged(PropertyChangeEvent event) {
		if (this.isExecutingUIThread()) {
			this.propertyChanged_(event);
		} else {
			this.executeOnUIThread(this.buildRunnable(event));
		}
	}

	private Runnable buildRunnable(final PropertyChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTPropertyChangeListenerWrapper.this.propertyChanged_(event);
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

	void propertyChanged_(PropertyChangeEvent event) {
		this.listener.propertyChanged(event);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener.toString() + ")";
	}

}
