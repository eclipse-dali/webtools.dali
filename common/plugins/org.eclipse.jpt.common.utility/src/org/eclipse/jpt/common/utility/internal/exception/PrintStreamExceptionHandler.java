/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.exception;

import java.io.PrintStream;

/**
 * An exception handler that prints the exceptions to the configured
 * {@link PrintStream}.
 */
public class PrintStreamExceptionHandler
	extends ExceptionHandlerAdapter
{
	private final PrintStream printStream;

	/**
	 * Construct an exception handler that prints any exceptions
	 * to the specified print stream.
	 */
	public PrintStreamExceptionHandler(PrintStream printStream) {
		super();
		if (printStream == null) {
			throw new NullPointerException();
		}
		this.printStream = printStream;
	}

	@Override
	public void handleException(Throwable t) {
		t.printStackTrace(this.printStream);
	}
}
