/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.listeners;

import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.internal.collection.SynchronizedQueue;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;

/**
 * Wrap another state change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 * <p>
 * Any events received earlier (on a non-UI thread) will be
 * forwarded, in the order received, before the current event is forwarded.
 * @see SWTPropertyChangeListenerWrapper
 */
public class SWTStateChangeListenerWrapper
	implements StateChangeListener
{
	private final StateChangeListener listener;
	private final SynchronizedQueue<StateChangeEvent> events = new SynchronizedQueue<StateChangeEvent>();


	public SWTStateChangeListenerWrapper(StateChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void stateChanged(StateChangeEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	/* CU private */ class ForwardEventsRunnable
		extends RunnableAdapter
	{
		@Override
		public void run() {
			SWTStateChangeListenerWrapper.this.forwardEvents();
		}
	}

	void forwardEvents() {
		for (StateChangeEvent event : this.events.drain()) {
			this.listener.stateChanged(event);
		}
	}

	/**
	 * {@link DisplayTools#execute(Runnable)} seems to work OK;
	 * but using {@link DisplayTools#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void execute(Runnable r) {
		DisplayTools.execute(r);
//		SWTUtil.syncExec(r);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener + ')'; //$NON-NLS-1$
	}
}
