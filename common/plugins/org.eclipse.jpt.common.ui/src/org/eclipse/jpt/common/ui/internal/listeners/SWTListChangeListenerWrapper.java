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
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
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

	public SWTListChangeListenerWrapper(ListChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void itemsAdded(ListAddEvent event) {
		this.execute(new ItemsAddedRunnable(event));
	}

	/* CU private */ class ItemsAddedRunnable
		extends RunnableAdapter
	{
		private final ListAddEvent event;
		ItemsAddedRunnable(ListAddEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTListChangeListenerWrapper.this.itemsAdded_(this.event);
		}
	}

	void itemsAdded_(ListAddEvent event) {
		this.listener.itemsAdded(event);
	}

	public void itemsRemoved(ListRemoveEvent event) {
		this.execute(new ItemsRemovedRunnable(event));
	}

	/* CU private */ class ItemsRemovedRunnable
		extends RunnableAdapter
	{
		private final ListRemoveEvent event;
		ItemsRemovedRunnable(ListRemoveEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTListChangeListenerWrapper.this.itemsRemoved_(this.event);
		}
	}

	void itemsRemoved_(ListRemoveEvent event) {
		this.listener.itemsRemoved(event);
	}

	public void itemsMoved(ListMoveEvent event) {
		this.execute(new ItemsMovedRunnable(event));
	}

	/* CU private */ class ItemsMovedRunnable
		extends RunnableAdapter
	{
		private final ListMoveEvent event;
		ItemsMovedRunnable(ListMoveEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTListChangeListenerWrapper.this.itemsMoved_(this.event);
		}
	}

	void itemsMoved_(ListMoveEvent event) {
		this.listener.itemsMoved(event);
	}

	public void itemsReplaced(ListReplaceEvent event) {
		this.execute(new ItemsReplacedRunnable(event));
	}

	/* CU private */ class ItemsReplacedRunnable
		extends RunnableAdapter
	{
		private final ListReplaceEvent event;
		ItemsReplacedRunnable(ListReplaceEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTListChangeListenerWrapper.this.itemsReplaced_(this.event);
		}
	}

	public void listCleared(ListClearEvent event) {
		this.execute(new ListClearedRunnable(event));
	}

	void itemsReplaced_(ListReplaceEvent event) {
		this.listener.itemsReplaced(event);
	}

	/* CU private */ class ListClearedRunnable
		extends RunnableAdapter
	{
		private final ListClearEvent event;
		ListClearedRunnable(ListClearEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTListChangeListenerWrapper.this.listCleared_(this.event);
		}
	}

	void listCleared_(ListClearEvent event) {
		this.listener.listCleared(event);
	}

	public void listChanged(ListChangeEvent event) {
		this.execute(new ListChangedRunnable(event));
	}

	/* CU private */ class ListChangedRunnable
		extends RunnableAdapter
	{
		private final ListChangeEvent event;
		ListChangedRunnable(ListChangeEvent event) {
			super();
			this.event = event;
		}
		@Override
		public void run() {
			SWTListChangeListenerWrapper.this.listChanged_(this.event);
		}
	}

	void listChanged_(ListChangeEvent event) {
		this.listener.listChanged(event);
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
