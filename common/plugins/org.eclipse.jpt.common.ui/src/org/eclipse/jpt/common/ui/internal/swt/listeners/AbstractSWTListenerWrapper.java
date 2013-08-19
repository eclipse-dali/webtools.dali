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

import java.util.EventListener;
import java.util.EventObject;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 * @see SWTListenerWrapperDelegate
 */
abstract class AbstractSWTListenerWrapper<E extends EventObject, L extends EventListener>
	implements SWTListenerWrapperDelegate.Wrapper<E>
{
	/* private-*/ protected final L listener;
	/* private-*/ protected final SWTListenerWrapperDelegate<E> delegate;


	AbstractSWTListenerWrapper(L listener, Display display, ExceptionHandler exceptionHandler) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
		this.delegate = new SWTListenerWrapperDelegate<E>(this, display, exceptionHandler);
	}

	@Override
	public String toString() {
		return "SWT[" + this.listener + ']'; //$NON-NLS-1$
	}
}
