/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.exception;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.exception.MultiThreadExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton multi-thread exception handler
 * that, like what happens with an unhandled exception
 * (see {@link ThreadGroup#uncaughtException(Thread, Throwable)}),
 * prints the exception's stack trace to {@link System#err the
 * "standard" error output stream}.
 */
public final class DefaultMultiThreadExceptionHandler
	implements MultiThreadExceptionHandler, Serializable
{
	public static final ExceptionHandler INSTANCE = new DefaultMultiThreadExceptionHandler();
	public static ExceptionHandler instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DefaultMultiThreadExceptionHandler() {
		super();
	}

	public void handleException(Thread thread, Throwable t) {
		this.handleException(t);
	}

	public void handleException(Throwable t) {
		t.printStackTrace();
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
