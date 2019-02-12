/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.listeners;

import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another list change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 */
final class SWTListChangeListenerWrapper
	extends AbstractSWTListenerWrapper<ListEvent, ListChangeListener>
	implements ListChangeListener
{
	SWTListChangeListenerWrapper(ListChangeListener listener, Display display, ExceptionHandler exceptionHandler) {
		super(listener, display, exceptionHandler);
	}

	public void itemsAdded(ListAddEvent event) {
		this.delegate.handle(event);
	}

	public void itemsRemoved(ListRemoveEvent event) {
		this.delegate.handle(event);
	}

	public void itemsMoved(ListMoveEvent event) {
		this.delegate.handle(event);
	}

	public void itemsReplaced(ListReplaceEvent event) {
		this.delegate.handle(event);
	}

	public void listCleared(ListClearEvent event) {
		this.delegate.handle(event);
	}

	public void listChanged(ListChangeEvent event) {
		this.delegate.handle(event);
	}

	public void forward(ListEvent event) {
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
}
