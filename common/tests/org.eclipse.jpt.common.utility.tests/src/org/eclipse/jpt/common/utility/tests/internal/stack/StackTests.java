/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

//subclass MultiThreadedTestCase for subclasses of this class
@SuppressWarnings("nls")
public abstract class StackTests
	extends MultiThreadedTestCase
{
	public StackTests(String name) {
		super(name);
	}

	abstract Stack<String> buildStack();

	public void testIsEmpty() {
		Stack<String> stack = this.buildStack();
		assertTrue(stack.isEmpty());
		stack.push("first");
		assertFalse(stack.isEmpty());
		stack.push("second");
		assertFalse(stack.isEmpty());
		stack.pop();
		assertFalse(stack.isEmpty());
		stack.pop();
		assertTrue(stack.isEmpty());
	}

	public void testPushAndPop() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.pop());
		assertEquals(first, stack.pop());
	}

	public void testPushAndPeek() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());
	}

	public void testEmptyStackExceptionPeek() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());

		boolean exCaught = false;
		try {
			stack.peek();
			fail();
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyStackExceptionPop() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());

		boolean exCaught = false;
		try {
			stack.pop();
			fail();
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		Stack<String> stack = this.buildStack();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		@SuppressWarnings("unchecked")
		Stack<String> clone = (Stack<String>) ObjectTools.execute(stack, "clone");
		this.verifyClone(stack, clone);
	}

	public void testSerialization() throws Exception {
		Stack<String> stack = this.buildStack();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		this.verifyClone(stack, TestTools.serialize(stack));
	}

	protected void verifyClone(Stack<String> original, Stack<String> clone) {
		assertNotSame(original, clone);
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.pop(), clone.pop());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.pop(), clone.pop());
		assertEquals(original.isEmpty(), clone.isEmpty());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.pop(), clone.pop());
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.push("fourth");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testSerialization_empty() throws Exception {
		Stack<String> stack = this.buildStack();
		Stack<String> clone = TestTools.serialize(stack);
		assertNotSame(stack, clone);
		assertTrue(clone.isEmpty());
		stack.push("fourth");
		assertFalse(stack.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testToString() throws Exception {
		Stack<String> stack = this.buildStack();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		assertEquals("[third, second, first]", stack.toString());
	}

	// ********** concurrency **********

	Stack<Integer> buildConcurrentStack() {
		return null;
	}

	public void testConcurrentAccess() throws InterruptedException {
		Stack<Integer> stack = this.buildConcurrentStack();
		if (stack == null) {
			return;
		}

		int threadCount = 10;
		int elementsPerThread = 100000;
		SynchronizedBoolean startFlag = new SynchronizedBoolean(false);

		PushRunnable[] pushRunnables = new PushRunnable[threadCount];
		Thread[] pushThreads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			PushRunnable pushRunnable = new PushRunnable(stack, (i * elementsPerThread), elementsPerThread, startFlag);
			pushRunnables[i] = pushRunnable;
			Thread pushThread = new Thread(pushRunnable, "Push-" + i);
			pushThreads[i] = pushThread;
			pushThread.start();
		}

		PopRunnable[] popRunnables = new PopRunnable[threadCount];
		Thread[] popThreads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			PopRunnable popRunnable = new PopRunnable(stack, elementsPerThread, startFlag);
			popRunnables[i] = popRunnable;
			Thread popThread = new Thread(popRunnable, "Pop-" + i);
			popThreads[i] = popThread;
			popThread.start();
		}

		startFlag.setTrue();
		for (int i = 0; i < threadCount; i++) {
			pushThreads[i].join();
			assertTrue(pushRunnables[i].exceptions.isEmpty());
		}
		for (int i = 0; i < threadCount; i++) {
			popThreads[i].join();
			assertTrue(popRunnables[i].exceptions.isEmpty());
		}

		// if we get here, we have, at the least, popped as many elements as we pushed...
		// ...now verify that all the popped elements are unique
		// (i.e. none were lost or duplicated)
		int totalCount = threadCount * elementsPerThread;
		int uberMax = totalCount + 1;
		int uberMaxThreadIndex = threadCount + 1;
		int[] indexes = ArrayTools.fill(new int[threadCount], 0);
		for (int i = 0; i < totalCount; i++) {
			int min = uberMax;
			int minThreadIndex = uberMaxThreadIndex;
			for (int j = 0; j < threadCount; j++) {
				int currentIndex = indexes[j];
				if (currentIndex < elementsPerThread) {
					int current = popRunnables[j].elements[currentIndex].intValue();
					if (current < min) {
						min = current;
						minThreadIndex = j;
					}
				}
			}
			assertEquals(i, min);
			indexes[minThreadIndex]++;
		}
	}

	public static class PushRunnable
		implements Runnable
	{
		private final Stack<Integer> stack;
		private final int start;
		private final int stop;
		private final SynchronizedBoolean startFlag;
		final List<InterruptedException> exceptions = new Vector<>();

		public PushRunnable(Stack<Integer> stack, int start, int count, SynchronizedBoolean startFlag) {
			super();
			this.stack = stack;
			this.start = start;
			this.stop = start + count;
			this.startFlag = startFlag;
		}

		public void run() {
			try {
				this.run_();
			} catch (InterruptedException ex) {
				this.exceptions.add(ex);
			}
		}

		private void run_() throws InterruptedException {
			this.startFlag.waitUntilTrue();
			for (int i = this.start; i < this.stop; i++) {
				this.stack.push(Integer.valueOf(i));
				if ((i % 20) == 0) {
					Thread.sleep(0);
				}
			}
		}
	}

	public static class PopRunnable
		implements Runnable
	{
		private final Stack<Integer> stack;
		private final int count;
		final Integer[] elements;
		private int elementsCount;
		private final SynchronizedBoolean startFlag;
		final List<InterruptedException> exceptions = new Vector<>();
	
		public PopRunnable(Stack<Integer> stack, int count, SynchronizedBoolean startFlag) {
			super();
			this.stack = stack;
			this.count = count;
			this.elements = new Integer[count];
			this.elementsCount = 0;
			this.startFlag = startFlag;
		}
	
		public void run() {
			try {
				this.run_();
			} catch (InterruptedException ex) {
				this.exceptions.add(ex);
			}
		}
	
		private void run_() throws InterruptedException {
			this.startFlag.waitUntilTrue();
			int i = 0;
			while (true) {
				Integer element = null;
				try {
					element = this.stack.peek(); // fiddle with peek also
					element = this.stack.pop();
				} catch (EmptyStackException ex) {
					element = null;
				}
				if (element != null) {
					this.elements[this.elementsCount++] = element;
					if (this.elementsCount == this.count) {
						break;
					}
				}
				if ((i % 20) == 0) {
					Thread.sleep(0);
				}
				if (i == Integer.MAX_VALUE) {
					i = 0;
				} else {
					i++;
				}
			}
			Arrays.sort(this.elements);
		}
	}
}
