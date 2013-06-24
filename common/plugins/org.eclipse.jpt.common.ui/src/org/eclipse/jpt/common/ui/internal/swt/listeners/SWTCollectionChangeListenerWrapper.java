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

import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another collection change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 */
class SWTCollectionChangeListenerWrapper
	extends AbstractSWTListenerWrapper<CollectionEvent, CollectionChangeListener>
	implements CollectionChangeListener
{
	SWTCollectionChangeListenerWrapper(CollectionChangeListener listener, Display display, ExceptionHandler exceptionHandler) {
		super(listener, display, exceptionHandler);
	}

	public void itemsAdded(CollectionAddEvent event) {
		this.delegate.handle(event);
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		this.delegate.handle(event);
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.delegate.handle(event);
	}

	public void collectionChanged(CollectionChangeEvent event) {
		this.delegate.handle(event);
	}

	public void forward(CollectionEvent event) {
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
}
