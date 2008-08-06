/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener.awt;

import java.awt.EventQueue;

import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 * Wrap another property change listener and forward events to it on the AWT
 * event queue.
 */
public class AWTPropertyChangeListenerWrapper
	implements PropertyChangeListener
{
	private final PropertyChangeListener listener;


	public AWTPropertyChangeListenerWrapper(PropertyChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void propertyChanged(PropertyChangeEvent event) {
		if (EventQueue.isDispatchThread()) {
			this.propertyChanged_(event);
		} else {
			this.executeOnEventQueue(this.buildRunnable(event));
		}
	}

	private Runnable buildRunnable(final PropertyChangeEvent event) {
		return new Runnable() {
			public void run() {
				AWTPropertyChangeListenerWrapper.this.propertyChanged_(event);
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

	void propertyChanged_(PropertyChangeEvent event) {
		this.listener.propertyChanged(event);
	}

	@Override
	public String toString() {
		return "AWT(" + this.listener.toString() + ")";
	}

}
