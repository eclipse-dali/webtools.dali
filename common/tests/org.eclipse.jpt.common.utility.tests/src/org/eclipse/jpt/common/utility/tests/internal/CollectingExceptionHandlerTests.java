/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.CollectingExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

public class CollectingExceptionHandlerTests
	extends TestCase
{
	public CollectingExceptionHandlerTests(String name) {
		super(name);
	}

	public void testHandleException() {
		CollectingExceptionHandler exceptionHandler = new CollectingExceptionHandler();
		Exception npe1 = new NullPointerException();
		exceptionHandler.handleException(npe1);
		Exception npe2 = new NullPointerException();
		exceptionHandler.handleException(npe2);

		Iterable<Throwable> exceptions = exceptionHandler.getExceptions();
		assertEquals(2, IterableTools.size(exceptions));
	}

	public void testGetExceptions() {
		CollectingExceptionHandler exceptionHandler = new CollectingExceptionHandler();
		Exception npe1 = new NullPointerException();
		exceptionHandler.handleException(npe1);
		Exception npe2 = new NullPointerException();
		exceptionHandler.handleException(npe2);

		Iterable<Throwable> exceptions = exceptionHandler.getExceptions();
		assertEquals(2, IterableTools.size(exceptions));
		assertTrue(IterableTools.contains(exceptions, npe1));
		assertTrue(IterableTools.contains(exceptions, npe2));
	}

	public void testClearExceptions() {
		CollectingExceptionHandler exceptionHandler = new CollectingExceptionHandler();
		Exception npe1 = new NullPointerException();
		exceptionHandler.handleException(npe1);
		Exception npe2 = new NullPointerException();
		exceptionHandler.handleException(npe2);

		Iterable<Throwable> exceptions = exceptionHandler.clearExceptions();
		assertEquals(2, IterableTools.size(exceptions));
		assertTrue(IterableTools.contains(exceptions, npe1));
		assertTrue(IterableTools.contains(exceptions, npe2));

		exceptions = exceptionHandler.clearExceptions();
		assertTrue(IterableTools.isEmpty(exceptions));

		exceptions = exceptionHandler.getExceptions();
		assertTrue(IterableTools.isEmpty(exceptions));
	}

	public void testToString() {
		CollectingExceptionHandler exceptionHandler = new CollectingExceptionHandler();
		assertNotNull(exceptionHandler.toString());
	}
}
