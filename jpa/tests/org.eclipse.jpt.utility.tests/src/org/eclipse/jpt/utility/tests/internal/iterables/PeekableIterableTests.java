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

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.PeekableIterable;
import org.eclipse.jpt.utility.internal.iterators.PeekableIterator;

@SuppressWarnings("nls")
public class PeekableIterableTests extends TestCase {

	public PeekableIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		PeekableIterable<String> iterable = this.buildIterable();
		PeekableIterator<String> iterator = iterable.iterator();
		assertEquals("one", iterator.peek());
	}

	public void testToString() {
		assertNotNull(this.buildIterable().toString());
	}

	private PeekableIterable<String> buildIterable() {
		return new PeekableIterable<String>(this.buildNestedIterable());
	}

	private Iterable<String> buildNestedIterable() {
		return new ArrayIterable<String>(this.buildArray());
	}

	private String[] buildArray() {
		return new String[] {"one", "two", "three"};
	}

}
