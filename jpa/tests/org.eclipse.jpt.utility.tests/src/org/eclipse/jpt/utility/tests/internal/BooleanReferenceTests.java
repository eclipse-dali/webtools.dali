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
import org.eclipse.jpt.utility.internal.BooleanReference;

@SuppressWarnings("nls")
public class BooleanReferenceTests extends TestCase {

	public BooleanReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		BooleanReference br = new BooleanReference(true);
		assertTrue(br.getValue());
	}

	public void testGetValueDefault() {
		BooleanReference br = new BooleanReference();
		assertFalse(br.getValue());
	}

	public void testIs() {
		BooleanReference br = new BooleanReference(true);
		assertTrue(br.is(true));
		assertFalse(br.is(false));
	}

	public void testIsNot() {
		BooleanReference br = new BooleanReference(true);
		assertFalse(br.isNot(true));
		assertTrue(br.isNot(false));
	}

	public void testIsTrue() {
		BooleanReference br = new BooleanReference(true);
		assertTrue(br.isTrue());
	}

	public void testIsFalse() {
		BooleanReference br = new BooleanReference(true);
		assertFalse(br.isFalse());
		br.setFalse();
		assertTrue(br.isFalse());
	}

	public void testSetValue() {
		BooleanReference br = new BooleanReference(true);
		assertTrue(br.getValue());
		br.setValue(false);
		assertFalse(br.getValue());
	}

	public void testFlip() {
		BooleanReference br = new BooleanReference(true);
		assertTrue(br.getValue());
		assertFalse(br.flip());
		assertFalse(br.getValue());
		assertTrue(br.flip());
		assertTrue(br.getValue());
	}

	public void testSetNotBoolean() {
		BooleanReference br = new BooleanReference(false);
		assertFalse(br.getValue());
		br.setNot(true);
		assertFalse(br.getValue());
		br.setNot(true);
		assertFalse(br.getValue());
		br.setNot(false);
		assertTrue(br.getValue());
	}

	public void testSetTrue() {
		BooleanReference br = new BooleanReference(false);
		assertFalse(br.getValue());
		br.setTrue();
		assertTrue(br.getValue());
	}

	public void testSetFalse() {
		BooleanReference br = new BooleanReference(true);
		assertTrue(br.getValue());
		br.setFalse();
		assertFalse(br.getValue());
	}

	public void testClone() {
		BooleanReference br = new BooleanReference(true);
		BooleanReference clone = (BooleanReference) br.clone();
		assertTrue(clone.getValue());
		assertEquals(br, clone);
	}

	public void testEquals() {
		BooleanReference br1 = new BooleanReference(true);
		BooleanReference br2 = new BooleanReference(true);
		assertEquals(br1, br2);

		BooleanReference br3 = new BooleanReference(false);
		assertFalse(br1.equals(br3));
	}

	public void testHashCode() {
		BooleanReference br1 = new BooleanReference(true);
		assertEquals(1, br1.hashCode());
		br1.setFalse();
		assertEquals(0, br1.hashCode());
	}

	public void testToString() {
		BooleanReference br1 = new BooleanReference(true);
		assertEquals("[true]", br1.toString());
		br1.setFalse();
		assertEquals("[false]", br1.toString());
	}

}
