/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.exception;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.CompositeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.PrintWriterExceptionHandler;

@SuppressWarnings("nls")
public class PrintWriterExceptionHandlerTests
	extends TestCase
{
	public PrintWriterExceptionHandlerTests(String name) {
		super(name);
	}

	public void testHandleException() {
		OutputStream out = new ByteArrayOutputStream();
		PrintWriter stream = new PrintWriter(out);
		ExceptionHandler exceptionHandler = new PrintWriterExceptionHandler(stream);
		Exception npe = new NullPointerException();
		exceptionHandler.handleException(npe);
		stream.flush();

		assertTrue(out.toString().contains("NullPointerException"));
	}

	public void testToString() {
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler();
		assertNotNull(exceptionHandler.toString());
	}
}
