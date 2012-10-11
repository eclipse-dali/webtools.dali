/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterable.ArrayIterable;

@SuppressWarnings("nls")
public class ArrayIterableTests extends TestCase {

	public ArrayIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		int i = 1;
		for (String string : this.buildIterable()) {
			assertEquals(i++, Integer.parseInt(string));
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

	private Iterable<String> buildIterable() {
		return this.buildIterable(0);
	}

	private Iterable<String> buildIterable(int start) {
		return this.buildIterable(this.buildArray(), start);
	}

	private Iterable<String> buildIterable(String[] array, int start) {
		return new ArrayIterable<String>(array, start);
	}

	private Iterable<String> buildIterable(int start, int end) {
		return this.buildIterable(this.buildArray(), start, end);
	}

	private Iterable<String> buildIterable(String[] array, int start, int end) {
		return new ArrayIterable<String>(array, start, end);
	}

	private String[] buildArray() {
		return new String[] { "1", "2", "3", "4", "5", "6", "7", "8" };
	}
}
