/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.BidiFilter;

@SuppressWarnings("nls")
public class BidiFilterTests extends TestCase {

	public BidiFilterTests(String name) {
		super(name);
	}

	public void testNullBidiFilter_accept() throws Exception {
		assertTrue(BidiFilter.Null.instance().accept("foo"));
	}

	public void testNullBidiFilter_reverseAccept() throws Exception {
		assertTrue(BidiFilter.Null.instance().reverseAccept("foo"));
	}

	public void testNullBidiFilter_toString() throws Exception {
		assertNotNull(BidiFilter.Null.instance().toString());
	}

	public void testNullBidiFilter_serialization() throws Exception {
		BidiFilter<?> xxx = TestTools.serialize(BidiFilter.Null.instance());
		assertSame(BidiFilter.Null.instance(), xxx);
	}

	public void testOpaqueBidiFilter_accept() throws Exception {
		assertFalse(BidiFilter.Opaque.instance().accept("foo"));
	}

	public void testOpaqueBidiFilter_reverseAccept() throws Exception {
		assertFalse(BidiFilter.Opaque.instance().reverseAccept("foo"));
	}

	public void testOpaqueBidiFilter_toString() throws Exception {
		assertNotNull(BidiFilter.Opaque.instance().toString());
	}

	public void testOpaqueBidiFilter_serialization() throws Exception {
		BidiFilter<?> xxx = TestTools.serialize(BidiFilter.Opaque.instance());
		assertSame(BidiFilter.Opaque.instance(), xxx);
	}

	public void testDisabledBidiFilter_accept() throws Exception {
		boolean exCaught = false;
		try {
			BidiFilter.Disabled.instance().accept("foo");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBidiFilter_reverseAccept() throws Exception {
		boolean exCaught = false;
		try {
			BidiFilter.Disabled.instance().reverseAccept("foo");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBidiFilter_toString() throws Exception {
		assertNotNull(BidiFilter.Disabled.instance().toString());
	}

	public void testDisabledBidiFilter_serialization() throws Exception {
		BidiFilter<?> xxx = TestTools.serialize(BidiFilter.Disabled.instance());
		assertSame(BidiFilter.Disabled.instance(), xxx);
	}

}
