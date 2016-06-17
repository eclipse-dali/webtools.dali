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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ListLastElementTransformerTests
	extends TestCase
{
	public ListLastElementTransformerTests(String name) {
		super(name);
	}

	public void testEvaluate() {
		List<String> list = new ArrayList<>();
		Transformer<List<? extends String>, String> transformer = TransformerTools.listLastElementTransformer();
		assertNull(transformer.transform(list));
		list.add("foo");
		assertEquals("foo", transformer.transform(list));
		list.add("bar");
		assertEquals("bar", transformer.transform(list));
		list.remove("foo");
		assertEquals("bar", transformer.transform(list));
		list.remove("bar");
		assertNull(transformer.transform(list));
	}

	public void testToString() {
		Transformer<List<? extends String>, String> transformer = TransformerTools.listLastElementTransformer();
		assertEquals("ListLastElementTransformer", transformer.toString());
	}

	public void testSerialization() throws Exception {
		Transformer<List<? extends String>, String> transformer = TransformerTools.listLastElementTransformer();
		assertSame(transformer, TestTools.serialize(transformer));
	}
}
