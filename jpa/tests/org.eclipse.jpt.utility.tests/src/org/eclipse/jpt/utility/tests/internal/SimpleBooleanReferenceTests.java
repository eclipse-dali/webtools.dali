/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.SimpleBooleanReference;

@SuppressWarnings("nls")
public class SimpleBooleanReferenceTests extends TestCase {

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
