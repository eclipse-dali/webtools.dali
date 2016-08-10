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
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class TransformerToolsTests
	extends TestCase
{
	public TransformerToolsTests(String name) {
		super(name);
	}

	public void testCollectionFirstElementTransformer() {
		Collection<String> list = new ArrayList<>();
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionFirstElementTransformer();
		assertNull(transformer.transform(list));
		list.add("foo");
		assertEquals("foo", transformer.transform(list));
		list.add("bar");
		assertEquals("foo", transformer.transform(list));
		list.remove("foo");
		assertEquals("bar", transformer.transform(list));
		list.remove("bar");
		assertNull(transformer.transform(list));
	}

	public void testCollectionFirstElementTransformer_NPE() {
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionFirstElementTransformer();
		boolean exCaught = false;
		try {
			String s = transformer.transform(null);
			fail("bogus: " + s);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCollectionFirstElementTransformer_() {
		Collection<String> list = new ArrayList<>();
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionLastElementTransformer_();
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
		assertEquals("bar", transformer.transform(list));
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

	public void testCollectionFirstElementTransformer__NPE() {
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionFirstElementTransformer_();
		boolean exCaught = false;
		try {
			String s = transformer.transform(null);
			fail("bogus: " + s);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCollectionLastElementTransformer() {
		Collection<String> list = new ArrayList<>();
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionLastElementTransformer();
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

	public void testCollectionLastElementTransformer_NPE() {
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionLastElementTransformer();
		boolean exCaught = false;
		try {
			String s = transformer.transform(null);
			fail("bogus: " + s);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCollectionLastElementTransformer_() {
		Collection<String> list = new ArrayList<>();
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionLastElementTransformer_();
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
		assertEquals("bar", transformer.transform(list));
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

	public void testCollectionLastElementTransformer__NPE() {
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionLastElementTransformer_();
		boolean exCaught = false;
		try {
			String s = transformer.transform(null);
			fail("bogus: " + s);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCollectionSingleElementTransformer() {
		Collection<String> list = new ArrayList<>();
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionSingleElementTransformer();
		assertNull(transformer.transform(list));
		list.add("foo");
		assertEquals("foo", transformer.transform(list));
		list.add("bar");
		assertNull(transformer.transform(list));
		list.remove("foo");
		assertEquals("bar", transformer.transform(list));
		list.remove("bar");
		assertNull(transformer.transform(list));
	}

	public void testCollectionSingleElementTransformer_NPE() {
		Transformer<Collection<? extends String>, String> transformer = TransformerTools.collectionSingleElementTransformer();
		boolean exCaught = false;
		try {
			String s = transformer.transform(null);
			fail("bogus: " + s);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
