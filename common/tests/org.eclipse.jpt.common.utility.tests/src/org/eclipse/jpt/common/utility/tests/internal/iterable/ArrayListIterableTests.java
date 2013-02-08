/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.ListIterator;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterable.ArrayListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

@SuppressWarnings("nls")
public class ArrayListIterableTests extends TestCase {

	public ArrayListIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		int i = 1;
		ListIterable<String> iterable = this.buildIterable();
		for (String string : iterable) {
			assertEquals(i++, Integer.parseInt(string));
		}
		ListIterator<String> stream = iterable.iterator();
		while (stream.hasNext()) {
			stream.next();
		}
		while (stream.hasPrevious()) {
			assertEquals(--i, Integer.parseInt(stream.previous()));
		}
	}

	public void testSubIterator() {
		int i = 3;
		for (String string : this.buildIterable(2)) {
			assertEquals(i++, Integer.parseInt(string));
		}
	}

	public void testIllegalArgumentException() {
		this.triggerIllegalArgumentException(-1, 1);
		this.triggerIllegalArgumentException(8, 1);
		this.triggerIllegalArgumentException(0, -1);
		this.triggerIllegalArgumentException(0, 9);
	}

	private void triggerIllegalArgumentException(int start, int end) {
		boolean exCaught = false;
		try {
			Iterable<String> iterable = this.buildIterable(start, end);
			fail("bogus iterable: " + iterable);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private ListIterable<String> buildIterable() {
		return this.buildIterable(0);
	}

	private ListIterable<String> buildIterable(int start) {
		return this.buildIterable(this.buildArray(), start);
	}

	private ListIterable<String> buildIterable(String[] array, int start) {
		return (start == 0) ?
				IterableTools.listIterable(array) :
				IterableTools.listIterable(array, start);
	}

	private ListIterable<String> buildIterable(int start, int end) {
		return this.buildIterable(this.buildArray(), start, end);
	}

	private ListIterable<String> buildIterable(String[] array, int start, int end) {
		return new ArrayListIterable<String>(array, start, end);
	}

	private String[] buildArray() {
		return new String[] { "1", "2", "3", "4", "5", "6", "7", "8" };
	}
}
