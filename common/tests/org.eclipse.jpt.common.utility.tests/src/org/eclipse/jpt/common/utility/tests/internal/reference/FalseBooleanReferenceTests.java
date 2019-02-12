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

import org.eclipse.jpt.common.utility.internal.reference.FalseBooleanReference;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class FalseBooleanReferenceTests
	extends TestCase
{
	public FalseBooleanReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		BooleanReference ref = FalseBooleanReference.instance();
		assertFalse(ref.getValue());
	}

	public void testIs() {
		BooleanReference ref = FalseBooleanReference.instance();
		assertFalse(ref.is(true));
		assertTrue(ref.is(false));
	}

	public void testIsNot() {
		BooleanReference ref = FalseBooleanReference.instance();
		assertTrue(ref.isNot(true));
		assertFalse(ref.isNot(false));
	}

	public void testIsTrue() {
		BooleanReference ref = FalseBooleanReference.instance();
		assertFalse(ref.isTrue());
	}

	public void testIsFalse() {
		BooleanReference ref = FalseBooleanReference.instance();
		assertTrue(ref.isFalse());
	}

	public void testToString() {
		BooleanReference ref = FalseBooleanReference.instance();
		assertEquals("[false]", ref.toString());
	}

	public void testSerialization() throws Exception {
		BooleanReference ref = FalseBooleanReference.instance();
		assertSame(ref, TestTools.serialize(ref));
	}
}
