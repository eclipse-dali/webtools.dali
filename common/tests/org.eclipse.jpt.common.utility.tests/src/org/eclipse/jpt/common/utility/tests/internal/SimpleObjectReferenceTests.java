/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.SimpleObjectReference;

@SuppressWarnings("nls")
public class SimpleObjectReferenceTests extends TestCase {

	public SimpleObjectReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>();
		assertNull(or.getValue());
		or.setValue("foo");
		assertEquals("foo", or.getValue());
	}

	public void testValueEqualsObject() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>();
		assertTrue(or.valueEquals(null));
		assertFalse(or.valueEquals("foo"));

		or.setValue("foo");
		assertFalse(or.valueEquals(null));
		assertTrue(or.valueEquals("foo"));
	}

	public void testValueNotEqualObject() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>();
		assertFalse(or.valueNotEqual(null));
		assertTrue(or.valueNotEqual("foo"));

		or.setValue("foo");
		assertTrue(or.valueNotEqual(null));
		assertFalse(or.valueNotEqual("foo"));
	}

	public void testIsNull() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>();
		assertTrue(or.isNull());
		or.setValue("foo");
		assertFalse(or.isNull());
	}

	public void testIsNotNull() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>();
		assertFalse(or.isNotNull());
		or.setValue("foo");
		assertTrue(or.isNotNull());
	}

	public void testSetNull() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>();
		assertNull(or.getValue());
		or.setValue("foo");
		assertEquals("foo", or.getValue());
		or.setNull();
		assertNull(or.getValue());
	}

	public void testClone() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>("foo");
		@SuppressWarnings("cast")
		SimpleObjectReference<String> clone = (SimpleObjectReference<String>) or.clone();
		assertEquals("foo", clone.getValue());
		assertNotSame(or, clone);
	}

	public void testToString() {
		SimpleObjectReference<String> or = new SimpleObjectReference<String>();
		assertEquals("[null]", or.toString());
		or.setValue("foo");
		assertEquals("[foo]", or.toString());
	}

}
