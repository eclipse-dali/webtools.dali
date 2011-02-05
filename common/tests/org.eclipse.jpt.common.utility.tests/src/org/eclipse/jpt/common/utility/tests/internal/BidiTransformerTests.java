/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.BidiTransformer;

@SuppressWarnings("nls")
public class BidiTransformerTests extends TestCase {

	public BidiTransformerTests(String name) {
		super(name);
	}

	public void testNullBidiTransformer_transform() throws Exception {
		assertEquals("foo", BidiTransformer.Null.instance().transform("foo"));
		assertNull(BidiTransformer.Null.instance().transform(null));
	}

	public void testNullBidiTransformer_reverseTransform() throws Exception {
		assertEquals("foo", BidiTransformer.Null.instance().reverseTransform("foo"));
		assertNull(BidiTransformer.Null.instance().transform(null));
	}

	public void testNullBidiTransformer_toString() throws Exception {
		assertNotNull(BidiTransformer.Null.instance().toString());
	}

	public void testNullBidiTransformer_serialization() throws Exception {
		BidiTransformer<?, ?> xxx = TestTools.serialize(BidiTransformer.Null.instance());
		assertSame(BidiTransformer.Null.instance(), xxx);
	}

	public void testDisabledBidiTransformer_transform() throws Exception {
		boolean exCaught = false;
		try {
			BidiTransformer.Disabled.instance().transform("foo");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBidiTransformer_reverseTransform() throws Exception {
		boolean exCaught = false;
		try {
			BidiTransformer.Disabled.instance().reverseTransform("foo");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBidiTransformer_toString() throws Exception {
		assertNotNull(BidiTransformer.Disabled.instance().toString());
	}

	public void testDisabledBidiTransformer_serialization() throws Exception {
		BidiTransformer<?, ?> xxx = TestTools.serialize(BidiTransformer.Disabled.instance());
		assertSame(BidiTransformer.Disabled.instance(), xxx);
	}

}
