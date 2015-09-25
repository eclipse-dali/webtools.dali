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

import org.eclipse.jpt.common.utility.internal.reference.AbstractBooleanReference;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class AbstractBooleanReferenceTests
	extends TestCase
{
	public AbstractBooleanReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		LocalBooleanReference br = new LocalBooleanReference();
		assertTrue(br.getValue());
	}

	public void testIs() {
		LocalBooleanReference br = new LocalBooleanReference();
		assertTrue(br.is(true));
		assertFalse(br.is(false));
	}

	public void testIsNot() {
		LocalBooleanReference br = new LocalBooleanReference();
		assertFalse(br.isNot(true));
		assertTrue(br.isNot(false));
	}

	public void testIsTrue() {
		LocalBooleanReference br = new LocalBooleanReference();
		assertTrue(br.isTrue());
	}

	public void testIsFalse() {
		LocalBooleanReference br = new LocalBooleanReference();
		assertFalse(br.isFalse());
	}

	public void testEquals() {
		LocalBooleanReference br1 = new LocalBooleanReference();
		LocalBooleanReference br2 = new LocalBooleanReference();
		assertTrue(br1.equals(br1));
		assertFalse(br1.equals(br2));
	}

	public void testHashCode() {
		LocalBooleanReference br = new LocalBooleanReference();
		assertEquals(br.hashCode(), br.hashCode());
	}

	public void testToString() {
		LocalBooleanReference br = new LocalBooleanReference();
		assertEquals("[true]", br.toString());
	}

	private class LocalBooleanReference
		extends AbstractBooleanReference
	{
		LocalBooleanReference() {
			super();
		}
		public boolean getValue() {
			return true;
		}
	}
}
