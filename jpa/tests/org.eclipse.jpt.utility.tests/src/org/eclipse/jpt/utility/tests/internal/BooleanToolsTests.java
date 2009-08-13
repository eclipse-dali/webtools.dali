/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import org.eclipse.jpt.utility.internal.BooleanTools;

import junit.framework.TestCase;

public class BooleanToolsTests extends TestCase {
	private static final Boolean TRUE = Boolean.TRUE;
	private static final Boolean FALSE = Boolean.FALSE;

	public BooleanToolsTests(String name) {
		super(name);
	}

	public void testNOT() {
		assertEquals(FALSE, BooleanTools.not(TRUE));
		assertEquals(TRUE, BooleanTools.not(FALSE));
	}

	public void testAND() {
		assertEquals(TRUE, BooleanTools.and(TRUE, TRUE));
		assertEquals(FALSE, BooleanTools.and(TRUE, FALSE));
		assertEquals(FALSE, BooleanTools.and(FALSE, TRUE));
		assertEquals(FALSE, BooleanTools.and(FALSE, FALSE));
	}

	public void testOR() {
		assertEquals(TRUE, BooleanTools.or(TRUE, TRUE));
		assertEquals(TRUE, BooleanTools.or(TRUE, FALSE));
		assertEquals(TRUE, BooleanTools.or(FALSE, TRUE));
		assertEquals(FALSE, BooleanTools.or(FALSE, FALSE));
	}

	public void testXOR() {
		assertEquals(FALSE, BooleanTools.xor(TRUE, TRUE));
		assertEquals(TRUE, BooleanTools.xor(TRUE, FALSE));
		assertEquals(TRUE, BooleanTools.xor(FALSE, TRUE));
		assertEquals(FALSE, BooleanTools.xor(FALSE, FALSE));
	}

	public void testNAND() {
		assertEquals(FALSE, BooleanTools.nand(TRUE, TRUE));
		assertEquals(TRUE, BooleanTools.nand(TRUE, FALSE));
		assertEquals(TRUE, BooleanTools.nand(FALSE, TRUE));
		assertEquals(TRUE, BooleanTools.nand(FALSE, FALSE));
	}

	public void testNOR() {
		assertEquals(FALSE, BooleanTools.nor(TRUE, TRUE));
		assertEquals(FALSE, BooleanTools.nor(TRUE, FALSE));
		assertEquals(FALSE, BooleanTools.nor(FALSE, TRUE));
		assertEquals(TRUE, BooleanTools.nor(FALSE, FALSE));
	}

	public void testXNOR() {
		assertEquals(TRUE, BooleanTools.xnor(TRUE, TRUE));
		assertEquals(FALSE, BooleanTools.xnor(TRUE, FALSE));
		assertEquals(FALSE, BooleanTools.xnor(FALSE, TRUE));
		assertEquals(TRUE, BooleanTools.xnor(FALSE, FALSE));
	}

}
