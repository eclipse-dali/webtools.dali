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
import java.util.Collection;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CollectionFirstElementTransformer_Tests
	extends TestCase
{
	public CollectionFirstElementTransformer_Tests(String name) {
		super(name);
	}

	public void testEvaluate() {
		Collection<String> list = new ArrayList<>();
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionFirstElementTransformer_();
		boolean exCaught = false;
		try {
			String bogus = transformer.transform(list);
			fail("bogus output: " + bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		list.add("foo");
		assertEquals("foo", transformer.transform(list));
		list.add("bar");
		assertEquals("foo", transformer.transform(list));
		list.remove("foo");
		assertEquals("bar", transformer.transform(list));
		list.remove("bar");
		exCaught = false;
		try {
			String bogus = transformer.transform(list);
			fail("bogus output: " + bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
	}

	public void testToString() {
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionFirstElementTransformer_();
		assertEquals("CollectionFirstElementTransformer_", transformer.toString());
	}

	public void testSerialization() throws Exception {
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionFirstElementTransformer_();
		assertSame(transformer, TestTools.serialize(transformer));
	}
}
