/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterables;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.ChainIterable;

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

}
