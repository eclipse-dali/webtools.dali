/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import org.eclipse.jpt.common.utility.MultiThreadExceptionHandler;

/**
 * An exception handler that forwards exceptions to a collection of other
 * exception handlers.
 */
public class CompositeMultiThreadedExceptionHandler
	extends AbstractCompositeExceptionHandler<MultiThreadExceptionHandler>
	implements MultiThreadExceptionHandler
{
	public CompositeMultiThreadedExceptionHandler() {
		super();
	}

	public CompositeMultiThreadedExceptionHandler(MultiThreadExceptionHandler... exceptionHandlers) {
		super(exceptionHandlers);
	}

	public CompositeMultiThreadedExceptionHandler(Iterable<? extends MultiThreadExceptionHandler> exceptionHandlers) {
		super(exceptionHandlers);
	}

	public void handleException(Thread thread, Throwable t) {
		for (MultiThreadExceptionHandler handler : this.getExceptionHandlers()) {
			handler.handleException(thread, t);
		}
	}
}
