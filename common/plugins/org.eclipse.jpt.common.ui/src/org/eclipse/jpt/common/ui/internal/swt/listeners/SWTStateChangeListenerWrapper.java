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

import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another state change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 */
final class SWTStateChangeListenerWrapper
	extends AbstractSWTListenerWrapper<StateChangeEvent, StateChangeListener>
	implements StateChangeListener
{
	SWTStateChangeListenerWrapper(StateChangeListener listener, Display display, ExceptionHandler exceptionHandler) {
		super(listener, display, exceptionHandler);
	}

	public void stateChanged(StateChangeEvent event) {
		this.delegate.handle(event);
	}

	public void forward(StateChangeEvent event) {
		this.listener.stateChanged(event);
	}
}
