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
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;

/**
 * Wrap another collection change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 * 
 * @see SWTPropertyChangeListenerWrapper
 */
public class SWTCollectionChangeListenerWrapper
	implements CollectionChangeListener
{
	private final CollectionChangeListener listener;
	private final SynchronizedQueue<CollectionEvent> events = new SynchronizedQueue<CollectionEvent>();


	public SWTCollectionChangeListenerWrapper(CollectionChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void itemsAdded(CollectionAddEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	public void collectionChanged(CollectionChangeEvent event) {
		this.events.enqueue(event);
		this.execute(new ForwardEventsRunnable());
	}

	/* CU private */ class ForwardEventsRunnable
		extends RunnableAdapter
	{
		@Override
		public void run() {
			SWTCollectionChangeListenerWrapper.this.forwardEvents();
		}
	}

	void forwardEvents() {
		Iterable<CollectionEvent> temp = this.events.drain();  // debug
		for (CollectionEvent event : temp) {
			try {
				this.forwardEvent(event);
			} catch (RuntimeException ex) {
				JptCommonUiPlugin.instance().logError(ex);
			}
		}
	}

	private void forwardEvent(CollectionEvent event) {
		if (event instanceof CollectionAddEvent) {
			this.listener.itemsAdded((CollectionAddEvent) event);
		}
		else if (event instanceof CollectionRemoveEvent) {
			this.listener.itemsRemoved((CollectionRemoveEvent) event);
		}
		else if (event instanceof CollectionClearEvent) {
			this.listener.collectionCleared((CollectionClearEvent) event);
		}
		else if (event instanceof CollectionChangeEvent) {
			this.listener.collectionChanged((CollectionChangeEvent) event);
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
