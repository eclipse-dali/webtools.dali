/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.listener.awt;

import java.awt.EventQueue;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.internal.collection.SynchronizedQueue;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;

/**
 * Wrap another property change listener and forward events to it on the AWT
 * event queue, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited. (Typically, the adapter(s) between a <em>property</em> and
 * its corresponding UI widget are read-write; as opposed to the adapter(s)
 * between a <em>collection</em> (or <em>list</em>) and its UI widget, which
 * is read-only.)
 * <p>
 * Any events received earlier (on a non-UI thread) will be
 * forwarded, in the order received, before the current event is forwarded.
 */
public final class AWTPropertyChangeListenerWrapper
	implements PropertyChangeListener
{
	private final PropertyChangeListener listener;
	private final SynchronizedQueue<PropertyChangeEvent> events = new SynchronizedQueue<PropertyChangeEvent>();


	public AWTPropertyChangeListenerWrapper(PropertyChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void propertyChanged(PropertyChangeEvent event) {
		this.events.enqueue(event);
		if (this.isExecutingOnUIThread()) {
			this.forwardEvents();
		} else {
			this.executeOnEventQueue(new ForwardEventsRunnable());
		}
	}

	private boolean isExecutingOnUIThread() {
		return EventQueue.isDispatchThread();
	}

	/* CU private */ class ForwardEventsRunnable
		extends RunnableAdapter
	{
		@Override
		public void run() {
			AWTPropertyChangeListenerWrapper.this.forwardEvents();
		}
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

	void forwardEvents() {
		for (PropertyChangeEvent event : this.events.drain()) {
			this.listener.propertyChanged(event);
		}
	}

	@Override
	public String toString() {
		return "AWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}
}
