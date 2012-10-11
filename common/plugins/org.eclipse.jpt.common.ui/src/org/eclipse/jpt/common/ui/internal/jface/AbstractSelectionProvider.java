/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Provide support for {@link ISelectionChangedListener selection change
 * listeners} and {@link #fireSelectionChanged(ISelection) the safe firing of
 * events}.
 */
public abstract class AbstractSelectionProvider
	extends EventManager
	implements ISelectionProvider
{
	protected AbstractSelectionProvider() {
		super();
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		this.addListenerObject(listener);
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		this.removeListenerObject(listener);
	}

	protected void fireSelectionChanged(ISelection selection) {
		SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
		for (Object listener : this.getListeners()) {
			SafeRunner.run(new ListenerNotifier(listener, event));
        }
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}


	// ********** listener notifier **********

	/**
	 * A runnable that forwards an event to a listener and handles any
	 * exceptions thrown by the listener by notifying the user via a dialog.
	 */
	/* CU private */ class ListenerNotifier
		extends SafeRunnable
	{
		private final ISelectionChangedListener listener;
		private final SelectionChangedEvent event;

		ListenerNotifier(Object listener, SelectionChangedEvent event) {
			super();
			this.listener = (ISelectionChangedListener) listener;
			this.event = event;
		}

		public void run() {
			this.listener.selectionChanged(this.event);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.listener);
		}
	}
}
