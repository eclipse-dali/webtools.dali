/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener.awt;

import java.awt.EventQueue;

import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;

/**
 * Wrap another state change listener and forward events to it on the AWT
 * event queue.
 * Forward <em>every</em> event asynchronously via the UI thread so the listener
 * receives in the same order they were generated.
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
		this.executeOnEventQueue(this.buildRunnable(event));
	}

	private Runnable buildRunnable(final StateChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTStateChangeListenerWrapper.this.stateChanged_(event);
			}
		};
	}

	/**
	 * EventQueue#invokeLater(Runnable) seems to work OK;
	 * but using #invokeAndWait(Runnable) can sometimes make things
	 * more predictable when debugging, at the risk of deadlocks.
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
