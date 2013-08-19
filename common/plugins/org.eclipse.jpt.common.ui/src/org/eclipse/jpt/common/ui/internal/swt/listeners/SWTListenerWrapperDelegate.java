/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.listeners;

import java.util.EventObject;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.internal.collection.SynchronizedQueue;
import org.eclipse.swt.widgets.Display;

/**
 * Delegate used by a {@link Wrapper} to forward events to its
 * wrapped listener on the SWT UI thread, asynchronously if necessary.
 * If the event arrived on the UI
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
final class SWTListenerWrapperDelegate<E extends EventObject> {
	private final Wrapper<E> wrapper;
	private final Display display;
	private final Runnable forwardEventsRunnable = new ForwardEventsRunnable();
	private final ExceptionHandler exceptionHandler;
	private final SynchronizedQueue<E> events = new SynchronizedQueue<E>();


	SWTListenerWrapperDelegate(Wrapper<E> wrapper, Display display, ExceptionHandler exceptionHandler) {
		super();
		if (wrapper == null) {
			throw new NullPointerException();
		}
		this.wrapper = wrapper;
		if (display == null) {
			throw new NullPointerException();
		}
		this.display = display;
		if (exceptionHandler == null) {
			throw new NullPointerException();
		}
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * Called by the wrapper.
	 */
	public void handle(E event) {
		this.events.enqueue(event);
		this.execute(this.forwardEventsRunnable);
	}

	/**
	 * {@link DisplayTools#execute(Runnable)} seems to work OK;
	 * but using {@link Display#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void execute(Runnable runnable) {
		DisplayTools.execute(this.display, runnable);
//		this.display.syncExec(runnable);
	}

	/* CU private */ class ForwardEventsRunnable
		extends RunnableAdapter
	{
		@Override
		public void run() {
			SWTListenerWrapperDelegate.this.forwardEvents();
		}
	}

	/**
	 * Dispatch the events back to the wrapper once we are on the UI thread.
	 */
	/* CU private */ void forwardEvents() {
		Iterable<E> temp = this.events.drain();  // debug aid
		for (E event : temp) {
			try {
				this.wrapper.forward(event);
			} catch (RuntimeException ex) {
				this.exceptionHandler.handleException(ex);
			}
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.wrapper);
	}


	// ********** wrapper interface **********

	/**
	 * The interface used by {@link SWTListenerWrapperDelegate} to forward
	 * events to the wrapped listener from the UI thread.
	 */
	public interface Wrapper<E extends EventObject> {
		/**
		 * Forward the specified event to the wrapped listener.
		 */
		void forward(E event);
	}
}
