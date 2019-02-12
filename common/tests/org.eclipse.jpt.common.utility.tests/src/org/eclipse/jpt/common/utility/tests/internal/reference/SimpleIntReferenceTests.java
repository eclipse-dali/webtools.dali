/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class SimpleIntReferenceTests
	extends ModifiableIntReferenceTests
{
	public SimpleIntReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableIntReference buildIntReference(int value) {
		return new SimpleIntReference(value);
	}

	public void testCtors() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertEquals(0, ir.getValue());
		ir = new SimpleIntReference(7);
		assertEquals(7, ir.getValue());
		ir = new SimpleIntReference(-7);
		assertEquals(-7, ir.getValue());
	}

	public void testClone() {
		SimpleIntReference ir1 = new SimpleIntReference(44);
		SimpleIntReference ir2 = ir1.clone();
		assertEquals(44, ir2.getValue());
		assertNotSame(ir1, ir2);
	}

	public void testSerialization() throws Exception {
		SimpleIntReference ir1 = new SimpleIntReference(44);
		SimpleIntReference ir2 = TestTools.serialize(ir1);
		assertEquals(44, ir2.getValue());
		assertNotSame(ir1, ir2);
	}
}
