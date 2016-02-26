/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;

@SuppressWarnings("nls")
public class LinkedStackTests
	extends StackTests
{
	public LinkedStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return StackTools.linkedStack();
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

	public void testNodeToString() {
		Stack<String> queue = StackTools.linkedStack();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.push(first);
		queue.push(second);
		queue.push(third);

		Object head = ObjectTools.get(queue, "head");
		assertTrue(head.toString().startsWith("LinkedStack.Node"));
		assertTrue(head.toString().endsWith("(third)"));
	}

	public void testToString_empty() throws Exception {
		Stack<String> stack = this.buildStack();
		assertEquals("[]", stack.toString());
	}
}
