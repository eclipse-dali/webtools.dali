/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.reference.BooleanReference;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public abstract class BooleanReferenceTests
	extends TestCase
{
	public BooleanReferenceTests(String name) {
		super(name);
	}

	protected BooleanReference buildBooleanReference() {
		return this.buildBooleanReference(true);
	}

	protected abstract BooleanReference buildBooleanReference(boolean value);

	public void testGetValue() {
		BooleanReference br = this.buildBooleanReference();
		assertTrue(br.getValue());
	}

	public void testIs() {
		BooleanReference br = this.buildBooleanReference();
		assertTrue(br.is(true));
		assertFalse(br.is(false));
	}

	public void testIsNot() {
		BooleanReference br = this.buildBooleanReference();
		assertFalse(br.isNot(true));
		assertTrue(br.isNot(false));
	}

	public void testIsTrue() {
		BooleanReference br = this.buildBooleanReference();
		assertTrue(br.isTrue());
	}

	public void testIsFalse() {
		BooleanReference br = this.buildBooleanReference();
		assertFalse(br.isFalse());
		br = this.buildBooleanReference(false);
		assertTrue(br.isFalse());
	}

	public void testEquals() {
		BooleanReference br1 = this.buildBooleanReference();
		BooleanReference br2 = this.buildBooleanReference();
		assertTrue(br1.equals(br1));
		assertFalse(br1.equals(br2));
	}

	public void testHashCode() {
		BooleanReference br = this.buildBooleanReference();
		assertEquals(br.hashCode(), br.hashCode());
	}

	public void testToString() {
		BooleanReference br = this.buildBooleanReference(true);
		assertEquals("[true]", br.toString());
		br = this.buildBooleanReference(false);
		assertEquals("[false]", br.toString());
	}
}
