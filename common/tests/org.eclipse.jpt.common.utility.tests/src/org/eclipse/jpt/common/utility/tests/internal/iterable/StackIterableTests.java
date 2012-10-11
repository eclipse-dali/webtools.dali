/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.internal.collection.LinkedStack;
import org.eclipse.jpt.common.utility.internal.iterable.StackIterable;

@SuppressWarnings("nls")
public class StackIterableTests extends TestCase {

	public StackIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		Iterator<String> iterator = this.buildIterable().iterator();
		assertEquals("three", iterator.next());
		assertEquals("two", iterator.next());
		assertEquals("one", iterator.next());
	}

	public void testToString() {
		assertNotNull(this.buildIterable().toString());
	}

	private Iterable<String> buildIterable() {
		return new StackIterable<String>(this.buildStack());
	}

	private Stack<String> buildStack() {
		Stack<String> stack = new LinkedStack<String>();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		return stack;
	}

}
