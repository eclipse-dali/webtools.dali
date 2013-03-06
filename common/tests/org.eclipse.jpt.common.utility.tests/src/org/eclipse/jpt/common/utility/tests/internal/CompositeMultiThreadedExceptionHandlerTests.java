/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.MultiThreadExceptionHandler;
import org.eclipse.jpt.common.utility.internal.CompositeMultiThreadedExceptionHandler;
import org.eclipse.jpt.common.utility.tests.internal.CompositeExceptionHandlerTests.TestExceptionHandler;

public class CompositeMultiThreadedExceptionHandlerTests
	extends TestCase
{
	public CompositeMultiThreadedExceptionHandlerTests(String name) {
		super(name);
	}

	public void testHandleException() {
		TestMultiThreadedExceptionHandler handler1 = new TestMultiThreadedExceptionHandler();
		TestMultiThreadedExceptionHandler handler2 = new TestMultiThreadedExceptionHandler();
		CompositeMultiThreadedExceptionHandler exceptionHandler = new CompositeMultiThreadedExceptionHandler(handler1, handler2);
		Exception npe = new NullPointerException();
		Thread thread = Thread.currentThread();
		exceptionHandler.handleException(thread, npe);

		assertEquals(npe, handler1.throwable);
		assertEquals(thread, handler1.thread);
		assertEquals(npe, handler2.throwable);
		assertEquals(thread, handler2.thread);
	}

	public static class TestMultiThreadedExceptionHandler
		extends TestExceptionHandler
		implements MultiThreadExceptionHandler
	{
		public volatile Thread thread = null;
		public void handleException(Thread t, Throwable ex) {
			this.thread = t;
			this.throwable = ex;
		}
	}
}
