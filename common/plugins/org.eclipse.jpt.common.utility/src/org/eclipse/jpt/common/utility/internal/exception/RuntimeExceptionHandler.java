/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton exception handler that
 * wraps the exception in a runtime exception and throws the runtime
 * exception.
 */
public final class RuntimeExceptionHandler
	implements ExceptionHandler, Serializable
{
	public static final ExceptionHandler INSTANCE = new RuntimeExceptionHandler();
	public static ExceptionHandler instance() {
		return INSTANCE;
	}

	// ensure single instance
	private RuntimeExceptionHandler() {
		super();
	}

	public void handleException(Throwable t) {
		// re-throw the exception unchecked
		throw (t instanceof RuntimeException) ? (RuntimeException) t : new RuntimeException(t);
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
