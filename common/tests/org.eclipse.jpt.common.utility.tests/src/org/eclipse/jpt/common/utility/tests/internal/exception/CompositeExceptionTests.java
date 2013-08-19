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
import org.eclipse.jpt.common.utility.internal.exception.CompositeException;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

@SuppressWarnings("nls")
public class CompositeExceptionTests
	extends TestCase
{
	public CompositeExceptionTests(String name) {
		super(name);
	}

	public void testGetException() {
		Exception npe = new NullPointerException();
		Exception iae = new IllegalArgumentException();
		CompositeException ex = new CompositeException(npe, iae);

		Iterable<Throwable> exceptions = ex.getExceptions();
		assertEquals(2, IterableTools.size(exceptions));
		assertTrue(IterableTools.contains(exceptions, npe));
		assertTrue(IterableTools.contains(exceptions, iae));
	}

	public void testGetMessage() {
		Exception npe = new NullPointerException();
		Exception iae = new IllegalArgumentException();
		CompositeException ex = new CompositeException(npe, iae);

		assertTrue(ex.getMessage().contains("NullPointerException"));
		assertTrue(ex.getMessage().contains("IllegalArgumentException"));
	}

	public void testToString() {
		CompositeException ex = new CompositeException();
		assertNotNull(ex.toString());
	}
}
