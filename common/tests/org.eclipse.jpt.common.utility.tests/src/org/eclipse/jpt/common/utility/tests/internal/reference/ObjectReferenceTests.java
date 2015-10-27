/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.reference.ObjectReference;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public abstract class ObjectReferenceTests
	extends TestCase
{
	public ObjectReferenceTests(String name) {
		super(name);
	}

	protected ObjectReference<String> buildObjectReference() {
		return this.buildObjectReference(null);
	}

	protected abstract ObjectReference<String> buildObjectReference(String value);

	public void testGetValue() {
		ObjectReference<String> or = this.buildObjectReference();
		assertNull(or.getValue());
		or = this.buildObjectReference("foo");
		assertEquals("foo", or.getValue());
	}

	public void testValueEqualsObject() {
		ObjectReference<String> or = this.buildObjectReference();
		assertTrue(or.valueEquals(null));
		assertFalse(or.valueEquals("foo"));

		or = this.buildObjectReference("foo");
		assertFalse(or.valueEquals(null));
		assertTrue(or.valueEquals("foo"));
		assertTrue(or.valueEquals(new String("foo")));
	}

	public void testValueNotEqualObject() {
		ObjectReference<String> or = this.buildObjectReference();
		assertFalse(or.valueNotEqual(null));
		assertTrue(or.valueNotEqual("foo"));

		or = this.buildObjectReference("foo");
		assertTrue(or.valueNotEqual(null));
		assertFalse(or.valueNotEqual("foo"));
		assertFalse(or.valueNotEqual(new String("foo")));
	}

	public void testIsObject() {
		ObjectReference<String> or = this.buildObjectReference();
		assertTrue(or.is(null));
		assertFalse(or.is("foo"));

		or = this.buildObjectReference("foo");
		assertFalse(or.is(null));
		assertTrue(or.is("foo"));
		assertFalse(or.is(new String("foo")));
	}

	public void testIsNotObject() {
		ObjectReference<String> or = this.buildObjectReference();
		assertFalse(or.isNot(null));
		assertTrue(or.isNot("foo"));

		or = this.buildObjectReference("foo");
		assertTrue(or.isNot(null));
		assertFalse(or.isNot("foo"));
		assertTrue(or.isNot(new String("foo")));
	}

	public void testIsNull() {
		ObjectReference<String> or = this.buildObjectReference();
		assertTrue(or.isNull());
		or = this.buildObjectReference("foo");
		assertFalse(or.isNull());
	}

	public void testIsNotNull() {
		ObjectReference<String> or = this.buildObjectReference();
		assertFalse(or.isNotNull());
		or = this.buildObjectReference("foo");
		assertTrue(or.isNotNull());
	}

	public void testIsMemberOfPredicate() {
		ObjectReference<String> or = this.buildObjectReference();
		assertTrue(or.isMemberOf(PredicateTools.isNull()));
		or = this.buildObjectReference("foo");
		assertFalse(or.isMemberOf(PredicateTools.isNull()));
	}

	public void testIsNotMemberOfPredicate() {
		ObjectReference<String> or = this.buildObjectReference();
		assertTrue(or.isNotMemberOf(PredicateTools.isNotNull()));
		or = this.buildObjectReference("foo");
		assertFalse(or.isNotMemberOf(PredicateTools.isNotNull()));
	}

	public void testEqualsObject() {
		ObjectReference<String> or1 = this.buildObjectReference();
		assertTrue(or1.equals(or1));
		ObjectReference<String> or2 = this.buildObjectReference();
		assertFalse(or1.equals(or2));
	}

	public void testHashCode() {
		ObjectReference<String> or1 = this.buildObjectReference();
		assertEquals(or1.hashCode(), or1.hashCode());
	}

	public void testToString() {
		ObjectReference<String> or = this.buildObjectReference();
		assertEquals("[null]", or.toString());
		or = this.buildObjectReference("foo");
		assertEquals("[foo]", or.toString());
	}
}
