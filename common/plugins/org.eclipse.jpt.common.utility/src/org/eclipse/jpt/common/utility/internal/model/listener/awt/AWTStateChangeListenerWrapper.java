/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.listener.awt;

import java.awt.EventQueue;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.queue.SynchronizedQueue;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;

/**
 * Wrap another state change listener and forward events to it on the AWT
 * event queue, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
 * <p>
 * Any events received earlier (on a non-UI thread) will be
 * forwarded, in the order received, before the current event is forwarded.
 * @see AWTPropertyChangeListenerWrapper
 */
public final class AWTStateChangeListenerWrapper
	implements StateChangeListener
{
	private final StateChangeListener listener;
	private final SynchronizedQueue<StateChangeEvent> events = QueueTools.synchronizedQueue();


	public AWTStateChangeListenerWrapper(StateChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void stateChanged(StateChangeEvent event) {
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
			AWTStateChangeListenerWrapper.this.forwardEvents();
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
		for (StateChangeEvent event : this.events.drain()) {
			this.listener.stateChanged(event);
		}
	}

	@Override
	public String toString() {
		return "AWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}
}
