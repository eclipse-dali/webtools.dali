/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.listener.awt;

import java.awt.EventQueue;

import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;

/**
 * Wrap another state change listener and forward events to it on the AWT
 * event queue, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
 */
public final class AWTStateChangeListenerWrapper
	implements StateChangeListener
{
	private final StateChangeListener listener;

	public AWTStateChangeListenerWrapper(StateChangeListener listener) {
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

	private Runnable buildStateChangedRunnable(final StateChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTStateChangeListenerWrapper.this.stateChanged_(event);
			}
			@Override
			public String toString() {
				return "state changed runnable"; //$NON-NLS-1$
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

	@Override
	public String toString() {
		return "AWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
