/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.reference.FlaggedObjectReference;
import org.eclipse.jpt.common.utility.reference.ObjectReference;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class FlaggedObjectReferenceTests
	extends TestCase
{
	public FlaggedObjectReferenceTests(String name) {
		super(name);
	}

	public void testConstructors() {
		ObjectReference<String> ref = new FlaggedObjectReference<>();
		assertNull(ref.getValue());
		ref = new FlaggedObjectReference<>("foo");
		assertEquals("foo", ref.getValue());
	}

	public void testIsSet() {
		FlaggedObjectReference<String> ref = new FlaggedObjectReference<>();
		assertNull(ref.getValue());
		assertFalse(ref.isSet());
		ref.setValue(null);
		assertNull(ref.getValue());
		assertTrue(ref.isSet());

		ref = new FlaggedObjectReference<>("foo");
		assertEquals("foo", ref.getValue());
		assertFalse(ref.isSet());
		ref.setValue(null);
		assertNull(ref.getValue());
		assertTrue(ref.isSet());

		ref = new FlaggedObjectReference<>("foo");
		assertEquals("foo", ref.getValue());
		assertFalse(ref.isSet());
		ref.setValue("foo");
		assertEquals("foo", ref.getValue());
		assertTrue(ref.isSet());
	}

	public void testToString() {
		FlaggedObjectReference<String> ref = new FlaggedObjectReference<>();
		assertNull(ref.getValue());
		assertEquals("[null]", ref.toString());
		ref.setValue(null);
		assertEquals("*[null]", ref.toString());

		ref = new FlaggedObjectReference<>("foo");
		assertEquals("foo", ref.getValue());
		assertEquals("[foo]", ref.toString());
		ref.setValue(null);
		assertEquals("*[null]", ref.toString());

		ref = new FlaggedObjectReference<>("foo");
		assertEquals("foo", ref.getValue());
		assertEquals("[foo]", ref.toString());
		ref.setValue("foo");
		assertEquals("*[foo]", ref.toString());
	}
}
