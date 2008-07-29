/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import org.eclipse.jpt.utility.internal.BooleanHolder;

import junit.framework.TestCase;

public class BooleanHolderTests extends TestCase {

	public BooleanHolderTests(String name) {
		super(name);
	}

	public void testGetValue() {
		BooleanHolder bh = new BooleanHolder(true);
		assertTrue(bh.getValue());
	}

	public void testIsTrue() {
		BooleanHolder bh = new BooleanHolder(true);
		assertTrue(bh.isTrue());
	}

	public void testIsFalse() {
		BooleanHolder bh = new BooleanHolder(true);
		assertFalse(bh.isFalse());
	}

	public void testIs() {
		BooleanHolder bh = new BooleanHolder(true);
		assertTrue(bh.is(true));
		assertFalse(bh.is(false));
	}

	public void testSetValue() {
		BooleanHolder bh = new BooleanHolder(true);
		assertTrue(bh.getValue());
		bh.setValue(false);
		assertFalse(bh.getValue());
	}

	public void testSetTrue() {
		BooleanHolder bh = new BooleanHolder(false);
		assertFalse(bh.getValue());
		bh.setTrue();
		assertTrue(bh.getValue());
	}


	public void testSetFalse() {
		BooleanHolder bh = new BooleanHolder(true);
		assertTrue(bh.getValue());
		bh.setFalse();
		assertFalse(bh.getValue());
	}

	public void testClone() {
		BooleanHolder bh = new BooleanHolder(true);
		BooleanHolder clone = (BooleanHolder) bh.clone();
		assertTrue(clone.getValue());
		assertEquals(bh, clone);
	}

	public void testEquals() {
		BooleanHolder bh1 = new BooleanHolder(true);
		BooleanHolder bh2 = new BooleanHolder(true);
		assertEquals(bh1, bh2);

		BooleanHolder bh3 = new BooleanHolder(false);
		assertFalse(bh1.equals(bh3));
	}

}
