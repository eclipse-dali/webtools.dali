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

import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;

/**
 * Wrap another state change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 * 
 * @see SWTPropertyChangeListenerWrapper
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
		this.execute(new StateChangedRunnable(event));
	}

	/* CU private */ class StateChangedRunnable
		extends RunnableAdapter
	{
		private final StateChangeEvent event;
		StateChangedRunnable(StateChangeEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTStateChangeListenerWrapper.this.stateChanged_(this.event);
		}
	}

	void stateChanged_(StateChangeEvent event) {
		this.listener.stateChanged(event);
	}

	/**
	 * {@link SWTUtil#execute(Runnable)} seems to work OK;
	 * but using {@link SWTUtil#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void execute(Runnable r) {
		SWTUtil.execute(r);
//		SWTUtil.syncExec(r);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener + ')'; //$NON-NLS-1$
	}
}
