/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.listeners;

import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.internal.collection.SynchronizedQueue;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;

/**
 * Wrap another list change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 * 
 * @see SWTPropertyChangeListenerWrapper
 */
public class SWTListChangeListenerWrapper
	implements ListChangeListener
{
	private final ListChangeListener listener;
	private final SynchronizedQueue<ListEvent> events = new SynchronizedQueue<ListEvent>();


	public SWTListChangeListenerWrapper(ListChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void itemsAdded(ListAddEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void itemsRemoved(ListRemoveEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void itemsMoved(ListMoveEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void itemsReplaced(ListReplaceEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void listCleared(ListClearEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void listChanged(ListChangeEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	/* CU private */ class ForwardEventsRunnable
		extends RunnableAdapter
	{
		@Override
		public void run() {
			SWTListChangeListenerWrapper.this.forwardEvents();
		}
	}

	void forwardEvents() {
		for (ListEvent event : this.events.drain()) {
			try {
				this.forwardEvent(event);
			} catch (RuntimeException ex) {
				JptCommonUiPlugin.instance().logError(ex);
			}
		}
	}

	private void forwardEvent(ListEvent event) {
		if (event instanceof ListAddEvent) {
			this.listener.itemsAdded((ListAddEvent) event);
		}
		else if (event instanceof ListRemoveEvent) {
			this.listener.itemsRemoved((ListRemoveEvent) event);
		}
		else if (event instanceof ListMoveEvent) {
			this.listener.itemsMoved((ListMoveEvent) event);
		}
		else if (event instanceof ListReplaceEvent) {
			this.listener.itemsReplaced((ListReplaceEvent) event);
		}
		else if (event instanceof ListClearEvent) {
			this.listener.listCleared((ListClearEvent) event);
		}
		else if (event instanceof ListChangeEvent) {
			this.listener.listChanged((ListChangeEvent) event);
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
