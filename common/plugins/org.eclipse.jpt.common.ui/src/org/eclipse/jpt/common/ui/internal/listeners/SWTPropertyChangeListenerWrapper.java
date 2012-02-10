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
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;

/**
 * Wrap another property change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited. (Typically, the adapter(s) between a <em>property</em> and
 * its corresponding UI widget are read-write; as opposed to the adapter(s)
 * between a <em>collection</em> (or <em>list</em>) and its UI widget, which
 * is read-only.)
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
		this.execute(new PropertyChangedRunnable(event));
	}

	/* CU private */ class PropertyChangedRunnable
		extends RunnableAdapter
	{
		private final PropertyChangeEvent event;
		PropertyChangedRunnable(PropertyChangeEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTPropertyChangeListenerWrapper.this.propertyChanged_(this.event);
		}
	}

	void propertyChanged_(PropertyChangeEvent event) {
		this.listener.propertyChanged(event);
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
