/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.exception;

import java.io.PrintWriter;

/**
 * An exception handler that prints the exceptions to the configured
 * {@link PrintWriter}.
 */
public class PrintWriterExceptionHandler
	extends ExceptionHandlerAdapter
{
	private final PrintWriter printWriter;

	/**
	 * Construct an exception handler that prints any exceptions
	 * to the specified print writer.
	 */
	public PrintWriterExceptionHandler(PrintWriter printWriter) {
		super();
		if (printWriter == null) {
			throw new NullPointerException();
		}
		this.printWriter = printWriter;
	}

	@Override
	public void handleException(Throwable t) {
		t.printStackTrace(this.printWriter);
	}
}
