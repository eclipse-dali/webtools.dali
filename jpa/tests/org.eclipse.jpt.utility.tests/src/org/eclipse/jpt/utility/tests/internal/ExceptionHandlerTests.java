/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.ExceptionHandler;

public class ExceptionHandlerTests extends TestCase {

	public ExceptionHandlerTests(String name) {
		super(name);
	}

	public void testNullExceptionHandler() {
		ExceptionHandler exceptionHandler = ExceptionHandler.Null.instance();
		exceptionHandler.handleException(new NullPointerException());  // just make sure it doesn't blow up?
	}

	public void testNullExceptionHandlerToString() {
		ExceptionHandler exceptionHandler = ExceptionHandler.Null.instance();
		assertNotNull(exceptionHandler.toString());
	}

	public void testRuntimeExceptionHandler() {
		Exception npe = new NullPointerException();
		ExceptionHandler exceptionHandler = ExceptionHandler.Runtime.instance();
		boolean exCaught = false;
		try {
			exceptionHandler.handleException(npe);
			fail();
		} catch (RuntimeException ex) {
			assertSame(npe, ex.getCause());
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRuntimeExceptionHandlerToString() {
		ExceptionHandler exceptionHandler = ExceptionHandler.Runtime.instance();
		assertNotNull(exceptionHandler.toString());
	}

}
