/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.enumeration;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.enumeration.EmptyEnumeration;

@SuppressWarnings("nls")
public class EmptyEnumerationTests extends TestCase {

	public EmptyEnumerationTests(String name) {
		super(name);
	}

	public void testHasMoreElements() {
		int i = 0;
		for (Enumeration<Object> stream = EmptyEnumeration.instance(); stream.hasMoreElements();) {
			stream.nextElement();
			i++;
		}
		assertEquals(0, i);
	}

	public void testNextElement() {
		for (Enumeration<Object> stream = EmptyEnumeration.instance(); stream.hasMoreElements();) {
			fail("bogus element: " + stream.nextElement());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Enumeration<Object> stream = EmptyEnumeration.instance();
		Object element = null;
		while (stream.hasMoreElements()) {
			element = stream.nextElement();
		}
		try {
			element = stream.nextElement();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + element, exCaught);
	}

}
