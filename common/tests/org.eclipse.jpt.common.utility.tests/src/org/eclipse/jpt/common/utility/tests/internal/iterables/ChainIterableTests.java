/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterables;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterables.ChainIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ChainIterator;

@SuppressWarnings("nls")
public class ChainIterableTests extends TestCase {
	private final static Class<?>[] VECTOR_HIERARCHY = { Vector.class, AbstractList.class, AbstractCollection.class, Object.class };

	public ChainIterableTests(String name) {
		super(name);
	}


	public void testNextLink() {
		int i = 0;
		for (Class<?> clazz : this.buildIterable()) {
			assertEquals(VECTOR_HIERARCHY[i++], clazz);
		}
	}

	public void testLinker() {
		int i = 0;
		for (Class<?> clazz : new ChainIterable<Class<?>>(Vector.class, this.buildLinker())) {
			assertEquals(VECTOR_HIERARCHY[i++], clazz);
		}
	}

	public void testException() {
		Iterable<Class<?>> iterable = new ChainIterable<Class<?>>(Vector.class);
		Iterator<Class<?>> iterator = iterable.iterator();
		boolean exCaught = false;
		try {
			Class<?> clazz = iterator.next();
			fail("bogus class: " + clazz);
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testToString() {
		assertNotNull(this.buildIterable().toString());
	}

	private Iterable<Class<?>> buildIterable() {
		return this.buildChainIterable(Vector.class);
	}

	private Iterable<Class<?>> buildChainIterable(Class<?> startLink) {
		// chain up the class's hierarchy
		return new ChainIterable<Class<?>>(startLink) {
			@Override
			protected Class<?> nextLink(Class<?> currentLink) {
				return currentLink.getSuperclass();
			}
		};
	}

	private ChainIterator.Linker<Class<?>> buildLinker() {
		return new ChainIterator.Linker<Class<?>>() {
			public Class<?> nextLink(Class<?> currentLink) {
				return currentLink.getSuperclass();
			}
		};
	}

}
