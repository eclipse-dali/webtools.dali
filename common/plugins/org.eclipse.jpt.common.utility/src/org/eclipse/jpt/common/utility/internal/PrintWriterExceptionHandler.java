/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.PrintWriter;
import org.eclipse.jpt.common.utility.ExceptionHandler;

/**
 * An exception handler that prints the exceptions to the configured
 * {@link PrintWriter}.
 */
public class PrintWriterExceptionHandler
	implements ExceptionHandler
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

	public void handleException(Throwable t) {
		t.printStackTrace(this.printWriter);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
