/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.LinkedStack;

@SuppressWarnings("nls")
public class LinkedStackTests
	extends StackTests
{
	public LinkedStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return new LinkedStack<String>();
	}

	public void testSize() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";
		String third = "third";

		assertEquals(0, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.push(first);
		stack.push(second);
		assertEquals(2, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.push(third);
		assertEquals(3, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.pop();
		assertEquals(2, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.pop();
		stack.pop();
		assertEquals(0, ((Integer) ObjectTools.execute(stack, "size")).intValue());
	}

	public void testBuildElements() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";
		String third = "third";
		stack.push(first);
		stack.push(second);
		stack.push(third);

		Object[] elements = new Object[] { third, second, first };
		assertTrue(Arrays.equals(elements, ((Object[]) ObjectTools.execute(stack, "buildElements"))));
	}

	public void testNodeCache() {
		Stack<String> stack = new LinkedStack<String>(2);
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		Object factory = ObjectTools.get(stack, "nodeFactory");

		this.verifyNodeCache(0, factory);
		stack.push(first);
		this.verifyNodeCache(0, factory);
		stack.push(second);
		stack.push(third);
		stack.push(fourth);
		stack.push(fifth);
		this.verifyNodeCache(0, factory);
		assertNull(ObjectTools.get(factory, "cacheHead"));

		stack.pop();
		this.verifyNodeCache(1, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.push(first);
		this.verifyNodeCache(1, factory);
		stack.push(second);
		this.verifyNodeCache(0, factory);
		stack.push(third);
		this.verifyNodeCache(0, factory);
	}

	public void verifyNodeCache(int size, Object factory) {
		assertEquals(size, ((Integer) ObjectTools.get(factory, "cacheSize")).intValue());
		int nodeCount = 0;
		for (Object node = ObjectTools.get(factory, "cacheHead"); node != null; node = ObjectTools.get(node, "next")) {
			nodeCount++;
		}
		assertEquals(size, nodeCount);
	}
}
