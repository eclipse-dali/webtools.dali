/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.exception;

import org.eclipse.jpt.common.utility.exception.MultiThreadExceptionHandler;

/**
 * This thread group overrides {@link #uncaughtException(Thread, Throwable)}
 * and notifies the configured exception handler.
 */
public class ExceptionHandlerThreadGroup
	extends ThreadGroup
{
	/**
	 * Broadcasting delegate.
	 */
	private final MultiThreadExceptionHandler exceptionHandler;


	// ********** constructors/initialization **********

	/**
	 * @see ThreadGroup#ThreadGroup(String)
	 */
	public ExceptionHandlerThreadGroup(String name, MultiThreadExceptionHandler exceptionHandler) {
		super(name);
		if (exceptionHandler == null) {
			throw new NullPointerException();
		}
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * @see ThreadGroup#ThreadGroup(ThreadGroup, String)
	 */
	public ExceptionHandlerThreadGroup(ThreadGroup parent, String name, MultiThreadExceptionHandler exceptionHandler) {
		super(parent, name);
		if (exceptionHandler == null) {
			throw new NullPointerException();
		}
		this.exceptionHandler = exceptionHandler;
	}


	/**
	 * Any uncaught exceptions are forwarded to the exception handler.
	 * @see ThreadGroup#uncaughtException(Thread, Throwable)
	 */
	@Override
	public synchronized void uncaughtException(Thread t, Throwable e) {
		this.exceptionHandler.handleException(t, e);
	}

	/**
	 * Return the exception handler the thread group uses to forward any
	 * uncaught exceptions.
	 */
	public MultiThreadExceptionHandler getExceptionHandler() {
		return this.exceptionHandler;
	}
}
