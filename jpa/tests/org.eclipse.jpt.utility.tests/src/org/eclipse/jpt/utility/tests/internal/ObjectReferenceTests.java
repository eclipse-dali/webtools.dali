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

import org.eclipse.jpt.utility.internal.ObjectReference;

@SuppressWarnings("nls")
public class ObjectReferenceTests extends TestCase {

	public ObjectReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertNull(or.getValue());
		or.setValue("foo");
		assertEquals("foo", or.getValue());
	}

	public void testValueEqualsObject() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertTrue(or.valueEquals(null));
		assertFalse(or.valueEquals("foo"));

		or.setValue("foo");
		assertFalse(or.valueEquals(null));
		assertTrue(or.valueEquals("foo"));
	}

	public void testValueNotEqualObject() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertFalse(or.valueNotEqual(null));
		assertTrue(or.valueNotEqual("foo"));

		or.setValue("foo");
		assertTrue(or.valueNotEqual(null));
		assertFalse(or.valueNotEqual("foo"));
	}

	public void testIsNull() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertTrue(or.isNull());
		or.setValue("foo");
		assertFalse(or.isNull());
	}

	public void testIsNotNull() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertFalse(or.isNotNull());
		or.setValue("foo");
		assertTrue(or.isNotNull());
	}

	public void testSetNull() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertNull(or.getValue());
		or.setValue("foo");
		assertEquals("foo", or.getValue());
		or.setNull();
		assertNull(or.getValue());
	}

	public void testClone() {
		ObjectReference<String> or = new ObjectReference<String>("foo");
		@SuppressWarnings("cast")
		ObjectReference<String> clone = (ObjectReference<String>) or.clone();
		assertEquals("foo", clone.getValue());
		assertEquals(or, clone);
	}

	public void testEquals() {
		ObjectReference<String> or1 = new ObjectReference<String>("foo");
		ObjectReference<String> or2 = new ObjectReference<String>("foo");
		assertTrue(or1.equals(or2));
		ObjectReference<String> or3 = new ObjectReference<String>("bar");
		assertFalse(or1.equals(or3));
	}

	public void testHashCode() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertEquals(0, or.hashCode());
		or.setValue("foo");
		assertEquals("foo".hashCode(), or.hashCode());
	}

	public void testToString() {
		ObjectReference<String> or = new ObjectReference<String>();
		assertEquals("[null]", or.toString());
		or.setValue("foo");
		assertEquals("[foo]", or.toString());
	}

}
