/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.exception;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.CompositeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.ExceptionHandlerAdapter;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

@SuppressWarnings("nls")
public class CompositeExceptionHandlerTests
	extends TestCase
{
	public CompositeExceptionHandlerTests(String name) {
		super(name);
	}

	public void testHandleException() {
		TestExceptionHandler handler1 = new TestExceptionHandler();
		TestExceptionHandler handler2 = new TestExceptionHandler();
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler(handler1, handler2);
		Exception npe = new NullPointerException();
		exceptionHandler.handleException(npe);

		assertEquals(npe, handler1.throwable);
		assertEquals(npe, handler2.throwable);
	}

	public void testGetExceptionHandlers() {
		TestExceptionHandler handler1 = new TestExceptionHandler();
		TestExceptionHandler handler2 = new TestExceptionHandler();
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler(handler1, handler2);

		Iterable<ExceptionHandler> handlers = exceptionHandler.getExceptionHandlers();
		assertTrue(IterableTools.contains(handlers, handler1));
		assertTrue(IterableTools.contains(handlers, handler2));
	}

	public void testAddExceptionHandler() {
		TestExceptionHandler handler1 = new TestExceptionHandler();
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler(handler1);
		TestExceptionHandler handler2 = new TestExceptionHandler();
		exceptionHandler.addExceptionHandler(handler2);
		Exception npe = new NullPointerException();
		exceptionHandler.handleException(npe);

		Iterable<ExceptionHandler> handlers = exceptionHandler.getExceptionHandlers();
		assertTrue(IterableTools.contains(handlers, handler1));
		assertTrue(IterableTools.contains(handlers, handler2));

		assertEquals(npe, handler1.throwable);
		assertEquals(npe, handler2.throwable);
	}

	public void testAddExceptionHandler_exception() {
		TestExceptionHandler handler1 = new TestExceptionHandler();
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler(handler1);
		try {
			exceptionHandler.addExceptionHandler(handler1);
			fail();
		} catch (IllegalArgumentException ex) {
			assertTrue(ex.getMessage().contains("duplicate handler"));
		}
	}

	public void testRemoveExceptionHandler() {
		TestExceptionHandler handler1 = new TestExceptionHandler();
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler(handler1);
		TestExceptionHandler handler2 = new TestExceptionHandler();
		exceptionHandler.addExceptionHandler(handler2);
		Exception npe = new NullPointerException();
		exceptionHandler.handleException(npe);

		Iterable<ExceptionHandler> handlers = exceptionHandler.getExceptionHandlers();
		assertTrue(IterableTools.contains(handlers, handler1));
		assertTrue(IterableTools.contains(handlers, handler2));

		assertEquals(npe, handler1.throwable);
		assertEquals(npe, handler2.throwable);

		handler1.throwable = null;
		handler2.throwable = null;
		exceptionHandler.removeExceptionHandler(handler2);
		exceptionHandler.handleException(npe);

		handlers = exceptionHandler.getExceptionHandlers();
		assertTrue(IterableTools.contains(handlers, handler1));
		assertFalse(IterableTools.contains(handlers, handler2));

		assertEquals(npe, handler1.throwable);
		assertNull(handler2.throwable);
	}

	public void testRemoveExceptionHandler_exception() {
		TestExceptionHandler handler1 = new TestExceptionHandler();
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler(handler1);
		TestExceptionHandler handler2 = new TestExceptionHandler();
		try {
			exceptionHandler.removeExceptionHandler(handler2);
			fail();
		} catch (IllegalArgumentException ex) {
			assertTrue(ex.getMessage().contains("handler not registered"));
		}
	}

	public void testToString() {
		CompositeExceptionHandler exceptionHandler = new CompositeExceptionHandler();
		assertNotNull(exceptionHandler.toString());
	}


	public static class TestExceptionHandler
		extends ExceptionHandlerAdapter
	{
		public volatile Throwable throwable = null;
		@Override
		public void handleException(Throwable t) {
			this.throwable = t;
		}
	}
}
