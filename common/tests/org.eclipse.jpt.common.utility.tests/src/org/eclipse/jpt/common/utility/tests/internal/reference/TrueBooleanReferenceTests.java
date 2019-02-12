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

import org.eclipse.jpt.common.utility.internal.reference.TrueBooleanReference;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class TrueBooleanReferenceTests
	extends TestCase
{
	public TrueBooleanReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		BooleanReference ref = TrueBooleanReference.instance();
		assertTrue(ref.getValue());
	}

	public void testIs() {
		BooleanReference ref = TrueBooleanReference.instance();
		assertTrue(ref.is(true));
		assertFalse(ref.is(false));
	}

	public void testIsNot() {
		BooleanReference ref = TrueBooleanReference.instance();
		assertFalse(ref.isNot(true));
		assertTrue(ref.isNot(false));
	}

	public void testIsTrue() {
		BooleanReference ref = TrueBooleanReference.instance();
		assertTrue(ref.isTrue());
	}

	public void testIsFalse() {
		BooleanReference ref = TrueBooleanReference.instance();
		assertFalse(ref.isFalse());
	}

	public void testToString() {
		BooleanReference ref = TrueBooleanReference.instance();
		assertEquals("[true]", ref.toString());
	}

	public void testSerialization() throws Exception {
		BooleanReference ref = TrueBooleanReference.instance();
		assertSame(ref, TestTools.serialize(ref));
	}
}
