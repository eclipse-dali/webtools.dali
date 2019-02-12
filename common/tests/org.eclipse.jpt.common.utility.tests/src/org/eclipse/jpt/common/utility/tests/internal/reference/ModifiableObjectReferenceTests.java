/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.reference.SimpleObjectReference;
import org.eclipse.jpt.common.utility.reference.ModifiableObjectReference;

@SuppressWarnings("nls")
public abstract class ModifiableObjectReferenceTests
	extends ObjectReferenceTests
{
	public ModifiableObjectReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableObjectReference<String> buildObjectReference() {
		return (ModifiableObjectReference<String>) super.buildObjectReference();
	}

	@Override
	protected abstract ModifiableObjectReference<String> buildObjectReference(String value);

	public void testSetNull() {
		ModifiableObjectReference<String> or = this.buildObjectReference();
		assertNull(or.getValue());
		or.setValue("foo");
		assertEquals("foo", or.getValue());
		or.setNull();
		assertNull(or.getValue());
	}

	public void testCommit() throws Exception {
		String v1 = "foo";
		ModifiableObjectReference<String> or = this.buildObjectReference();
		assertTrue(or.commit(v1, null));
		assertEquals(v1, or.getValue());
		assertFalse(or.commit(v1, null));
		assertEquals(v1, or.getValue());

		String v2 = "bar";
		assertTrue(or.commit(v2, v1));
		assertEquals(v2, or.getValue());
		assertFalse(or.commit(v2, v1));
		assertEquals(v2, or.getValue());
	}

	public void testSwapRef() throws Exception {
		ModifiableObjectReference<String> or = this.buildObjectReference();
		ModifiableObjectReference<String> temp = or;
		assertEquals(null, or.swap(temp));

		ModifiableObjectReference<String> ref = new SimpleObjectReference<>("foo");
		assertEquals("foo", or.swap(ref));
		assertEquals("foo", or.getValue());
		assertEquals(null, ref.getValue());

		or.setValue("foo");
		ref.setValue("foo");
		assertEquals("foo", or.swap(ref));
		assertEquals("foo", or.getValue());
		assertEquals("foo", ref.getValue());
	}
}
