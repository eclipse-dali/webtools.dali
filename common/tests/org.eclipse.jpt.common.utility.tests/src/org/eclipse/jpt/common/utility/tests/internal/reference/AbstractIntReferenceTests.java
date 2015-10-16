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

import org.eclipse.jpt.common.utility.internal.reference.AbstractIntReference;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class AbstractIntReferenceTests
	extends TestCase
{
	public AbstractIntReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		LocalIntReference br = new LocalIntReference();
		assertEquals(42, br.getValue());
	}

	public void testEqualsInt() {
		LocalIntReference br = new LocalIntReference();
		assertTrue(br.equals(42));
		assertFalse(br.equals(0));
	}

	public void testNotEqual() {
		LocalIntReference br = new LocalIntReference();
		assertFalse(br.notEqual(42));
		assertTrue(br.notEqual(0));
	}

	public void testIsZero() {
		LocalIntReference br = new LocalIntReference();
		assertFalse(br.isZero());
	}

	public void testIsNotZero() {
		LocalIntReference br = new LocalIntReference();
		assertTrue(br.isNotZero());
	}

	public void testEquals() {
		LocalIntReference br1 = new LocalIntReference();
		LocalIntReference br2 = new LocalIntReference();
		assertTrue(br1.equals(br1));
		assertFalse(br1.equals(br2));
	}

	public void testHashCode() {
		LocalIntReference br = new LocalIntReference();
		assertEquals(br.hashCode(), br.hashCode());
	}

	public void testToString() {
		LocalIntReference br = new LocalIntReference();
		assertEquals("[42]", br.toString());
	}

	private class LocalIntReference
		extends AbstractIntReference
	{
		LocalIntReference() {
			super();
		}
		public int getValue() {
			return 42;
		}
	}
}
