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

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.reference.SimpleBooleanReference;

@SuppressWarnings("nls")
public class SimpleBooleanReferenceTests
	extends TestCase
{
	public SimpleBooleanReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.getValue());
	}

	public void testGetValueDefault() {
		SimpleBooleanReference br = new SimpleBooleanReference();
		assertFalse(br.getValue());
	}

	public void testIs() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.is(true));
		assertFalse(br.is(false));
	}

	public void testIsNot() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertFalse(br.isNot(true));
		assertTrue(br.isNot(false));
	}

	public void testIsTrue() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.isTrue());
	}

	public void testIsFalse() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertFalse(br.isFalse());
		br.setFalse();
		assertTrue(br.isFalse());
	}

	public void testSetValue() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.getValue());
		br.setValue(false);
		assertFalse(br.getValue());
	}

	public void testFlip() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.getValue());
		assertFalse(br.flip());
		assertFalse(br.getValue());
		assertTrue(br.flip());
		assertTrue(br.getValue());
	}

	public void testAnd() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.and(true));
		assertTrue(br.getValue());

		assertFalse(br.and(false));
		assertFalse(br.getValue());

		assertFalse(br.and(true));
		assertFalse(br.getValue());

		assertFalse(br.and(false));
		assertFalse(br.getValue());
	}

	public void testOr() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.or(true));
		assertTrue(br.getValue());

		assertTrue(br.or(false));
		assertTrue(br.getValue());

		assertTrue(br.setValue(false));
		assertFalse(br.or(false));
		assertFalse(br.getValue());

		assertTrue(br.or(true));
		assertTrue(br.getValue());
	}

	public void testXor() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertFalse(br.xor(true));
		assertFalse(br.getValue());

		assertFalse(br.setValue(true));
		assertTrue(br.xor(false));
		assertTrue(br.getValue());

		assertTrue(br.setValue(false));
		assertFalse(br.xor(false));
		assertFalse(br.getValue());

		assertFalse(br.setValue(false));
		assertTrue(br.xor(true));
		assertTrue(br.getValue());
	}

	public void testSetNotBoolean() {
		SimpleBooleanReference br = new SimpleBooleanReference(false);
		assertFalse(br.getValue());
		br.setNot(true);
		assertFalse(br.getValue());
		br.setNot(true);
		assertFalse(br.getValue());
		br.setNot(false);
		assertTrue(br.getValue());
	}

	public void testSetTrue() {
		SimpleBooleanReference br = new SimpleBooleanReference(false);
		assertFalse(br.getValue());
		br.setTrue();
		assertTrue(br.getValue());
	}

	public void testSetFalse() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.getValue());
		br.setFalse();
		assertFalse(br.getValue());
	}

	public void testCommit() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.commit(false, true));
		assertFalse(br.getValue());

		assertFalse(br.commit(false, true));
		assertFalse(br.getValue());

		assertTrue(br.commit(true, false));
		assertTrue(br.getValue());

		assertFalse(br.commit(true, false));
		assertTrue(br.getValue());

		assertFalse(br.commit(false, false));
		assertTrue(br.getValue());

		assertTrue(br.setValue(false));
		assertFalse(br.commit(true, true));
		assertFalse(br.getValue());
	}

	public void testSwap_sameObject() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		assertTrue(br.swap(br));
		assertTrue(br.getValue());
	}

	public void testSwap_sameValues() {
		SimpleBooleanReference br1 = new SimpleBooleanReference(true);
		SimpleBooleanReference br2 = new SimpleBooleanReference(true);
		assertTrue(br1.swap(br2));
		assertTrue(br1.getValue());
		assertTrue(br2.getValue());
	}

	public void testSwap_differentValues1() {
		SimpleBooleanReference br1 = new SimpleBooleanReference(true);
		SimpleBooleanReference br2 = new SimpleBooleanReference(false);
		assertFalse(br1.swap(br2));
		assertFalse(br1.getValue());
		assertTrue(br2.getValue());
	}

	public void testSwap_differentValues2() {
		SimpleBooleanReference br1 = new SimpleBooleanReference(false);
		SimpleBooleanReference br2 = new SimpleBooleanReference(true);
		assertTrue(br1.swap(br2));
		assertTrue(br1.getValue());
		assertFalse(br2.getValue());
	}

	public void testEquals() {
		SimpleBooleanReference br1 = new SimpleBooleanReference(false);
		SimpleBooleanReference br2 = new SimpleBooleanReference(true);
		assertTrue(br1.equals(br1));
		assertFalse(br1.equals(br2));
	}

	public void testHashCode() {
		SimpleBooleanReference br1 = new SimpleBooleanReference(false);
		assertEquals(br1.hashCode(), br1.hashCode());
	}

	public void testClone() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		SimpleBooleanReference clone = br.clone();
		assertTrue(clone.getValue());
	}

	public void testToString() {
		SimpleBooleanReference br1 = new SimpleBooleanReference(true);
		assertEquals("[true]", br1.toString());
		br1.setFalse();
		assertEquals("[false]", br1.toString());
	}
}
