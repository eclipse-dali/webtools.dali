/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.ExceptionHandler;

/**
 * Singleton exception handler that does
 * nothing with the exception.
 */
public final class NullExceptionHandler
	implements ExceptionHandler, Serializable
{
	public static final ExceptionHandler INSTANCE = new NullExceptionHandler();
	public static ExceptionHandler instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullExceptionHandler() {
		super();
	}

	public void handleException(Throwable t) {
		// do nothing
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
