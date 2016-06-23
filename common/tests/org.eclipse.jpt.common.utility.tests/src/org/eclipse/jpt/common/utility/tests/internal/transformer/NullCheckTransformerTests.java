/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.transformer;

import org.eclipse.jpt.common.utility.internal.transformer.NullCheckTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class NullCheckTransformerTests
	extends TestCase
{
	public NullCheckTransformerTests(String name) {
		super(name);
	}

	public void testTransform() {
		Transformer<String, String> transformer = TransformerTools.nullCheck("");

		assertEquals("foo", transformer.transform("foo"));
		assertEquals("bar", transformer.transform("bar"));
		assertEquals("barbar", transformer.transform("barbar"));
		assertEquals("b", transformer.transform("b"));
		assertEquals("", transformer.transform(""));
		assertEquals("", transformer.transform(null));
	}

	public void testToString() {
		Transformer<String, String> transformer = TransformerTools.nullCheck("");
		assertTrue(transformer.toString().indexOf("NullCheck") != -1);
	}

	public void testGetters() {
		NullCheckTransformer<String> transformer = (NullCheckTransformer<String>) TransformerTools.nullCheck("");
		assertEquals("", transformer.getNullOutput());
	}
}
