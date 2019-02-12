/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.Iterator;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterable.StackIterable;
import org.eclipse.jpt.common.utility.internal.stack.LinkedStack;
import org.eclipse.jpt.common.utility.stack.Stack;

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
