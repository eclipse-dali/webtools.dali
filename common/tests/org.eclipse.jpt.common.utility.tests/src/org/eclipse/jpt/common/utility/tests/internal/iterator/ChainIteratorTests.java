/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterator;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterator.ChainIterator;
import org.eclipse.jpt.common.utility.internal.transformer.DisabledTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class ChainIteratorTests
	extends TestCase
{
	private final static Class<?>[] VECTOR_HIERARCHY = { Vector.class, AbstractList.class, AbstractCollection.class, Object.class };

	public ChainIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<Class<?>> stream = this.buildIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(VECTOR_HIERARCHY.length, i);
	}

	public void testNext() {
		int i = 0;
		for (Iterator<Class<?>> stream = this.buildIterator(); stream.hasNext(); i++) {
			assertEquals("bogus link", VECTOR_HIERARCHY[i], stream.next());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<Class<?>> stream = this.buildIterator();
		Class<?> javaClass = null;
		while (stream.hasNext()) {
			javaClass = stream.next();
		}
		try {
			javaClass = stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + javaClass, exCaught);
	}

	public void testUnsupportedOperationException() {
		boolean exCaught = false;
		for (Iterator<Class<?>> stream = this.buildIterator(); stream.hasNext();) {
			if (stream.next() == AbstractCollection.class) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	private Iterator<Class<?>> buildIterator() {
		return this.buildChainIterator(Vector.class, SUPERCLASS_TRANSFORMER);
	}

	private Iterator<Class<?>> buildChainIterator(Class<?> startLink, Transformer<Class<?>, Class<?>> transformer) {
		return new ChainIterator<Class<?>>(startLink, transformer);
	}

	public void testInvalidChainIterator() {
		// missing method override
		Iterator<Class<?>> iterator = new ChainIterator<Class<?>>(Vector.class, DisabledTransformer.<Class<?>, Class<?>>instance());
		boolean exCaught = false;
		try {
			Class<?> c = iterator.next();
			fail("invalid class: " + c.getName());
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
	}

	private static final TransformerAdapter<Class<?>, Class<?>> SUPERCLASS_TRANSFORMER = new SuperclassTransformer();
	static class SuperclassTransformer
		extends TransformerAdapter<Class<?>, Class<?>>
	{
		@Override
		public Class<?> transform(Class<?> clazz) {
			return clazz.getSuperclass();
		}
	}
}
