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

import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another state change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
 */
public class SWTStateChangeListenerWrapper
	implements StateChangeListener
{
	private final StateChangeListener listener;

	public SWTStateChangeListenerWrapper(StateChangeListener listener) {
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
			this.executeOnUIThread(this.buildStateChangedRunnable(event));
		}
	}

	private Runnable buildStateChangedRunnable(final StateChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTStateChangeListenerWrapper.this.stateChanged_(event);
			}
			@Override
			public String toString() {
				return "state changed runnable"; //$NON-NLS-1$
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

	void stateChanged_(StateChangeEvent event) {
		this.listener.stateChanged(event);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
