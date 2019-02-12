/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.PeekableIterable;
import org.eclipse.jpt.common.utility.internal.iterator.PeekableIterator;

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
		return IterableTools.iterable(this.buildArray());
	}

	private String[] buildArray() {
		return new String[] {"one", "two", "three"};
	}

}
