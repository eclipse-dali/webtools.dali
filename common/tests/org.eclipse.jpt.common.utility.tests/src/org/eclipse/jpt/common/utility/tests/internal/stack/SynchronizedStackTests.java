/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.stack.LinkedStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.internal.stack.SynchronizedStack;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.deque.DequeToolsTests;

@SuppressWarnings("nls")
public class SynchronizedStackTests
	extends StackTests
{
	private volatile SynchronizedStack<String> ss;
	volatile boolean timeoutOccurred;
	volatile long startTime;
	volatile long endTime;
	volatile Object poppedObject;

	boolean commandExecuted;

	static final String ITEM_1 = new String();
	static final String ITEM_2 = new String();

	public SynchronizedStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return StackTools.synchronizedStack();
	}

	@Override
	public void testClone() {
		// synchronized stack is not cloneable
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.ss = StackTools.synchronizedStack();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.poppedObject = null;
	}

	// ********** constructor **********

	public void testConstructorStack() throws Exception {
		Stack<String> innerStack = StackTools.arrayStack();
		SynchronizedStack<String> stack = StackTools.synchronizedStack(innerStack);
		assertNotNull(stack);
		assertSame(stack, stack.getMutex());
	}

	public void testConstructorStack_NPE() throws Exception {
		boolean exCaught = false;
		try {
			Stack<String> stack = StackTools.synchronizedStack(null);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorStackObject() throws Exception {
		String mutex = "mutex";
		Stack<String> innerStack = StackTools.arrayStack();
		SynchronizedStack<String> stack = StackTools.synchronizedStack(innerStack, mutex);
		assertNotNull(stack);
		assertSame(mutex, stack.getMutex());
	}

	public void testConstructorStackObject_NPE1() throws Exception {
		String mutex = "mutex";
		boolean exCaught = false;
		try {
			Stack<String> stack = StackTools.synchronizedStack(null, mutex);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorStackObject_NPE2() throws Exception {
		Stack<String> innerStack = StackTools.arrayStack();
		boolean exCaught = false;
		try {
			Stack<String> stack = StackTools.synchronizedStack(innerStack, null);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	// ********** concurrent access **********

	/**
	 * test first with an unsynchronized stack,
	 * then with a synchronized stack
	 */
	public void testConcurrentPop() throws Exception {
		this.verifyConcurrentPop(new SlowLinkedStack<String>(), "second");
		this.verifyConcurrentPop(new SlowSynchronizedStack<String>(), "first");
	}

	private void verifyConcurrentPop(SlowStack<String> slowStack, String expected) throws Exception {
		slowStack.push("first");
		slowStack.push("second");

		Thread thread = this.buildThread(this.buildRunnablePop(slowStack));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(expected, slowStack.pop());
		thread.join();
		assertTrue(slowStack.isEmpty());
	}

	private Runnable buildRunnablePop(final SlowStack<String> slowStack) {
		return new Runnable() {
			public void run() {
				slowStack.slowPop();
			}
		};
	}

	/**
	 * test first with an unsynchronized stack,
	 * then with a synchronized stack
	 */
	public void testConcurrentPush() throws Exception {
		this.verifyConcurrentPush(new SlowLinkedStack<String>(), "first", "second");
		this.verifyConcurrentPush(new SlowSynchronizedStack<String>(), "second", "first");
	}

	private void verifyConcurrentPush(SlowStack<String> slowStack, String first, String second) throws Exception {
		Thread thread = this.buildThread(this.buildRunnablePush(slowStack, "first"));
		thread.start();
		Thread.sleep(TWO_TICKS);

		slowStack.push("second");
		thread.join();
		assertEquals(first, slowStack.pop());
		assertEquals(second, slowStack.pop());
		assertTrue(slowStack.isEmpty());
	}

	private Runnable buildRunnablePush(final SlowStack<String> slowStack, final String element) {
		return new Runnable() {
			public void run() {
				slowStack.slowPush(element);
			}
		};
	}

	/**
	 * test first with an unsynchronized stack,
	 * then with a synchronized stack
	 */
	public void testConcurrentIsEmpty() throws Exception {
		this.verifyConcurrentIsEmpty(new SlowLinkedStack<String>(), true);
		this.verifyConcurrentIsEmpty(new SlowSynchronizedStack<String>(), false);
	}

	private void verifyConcurrentIsEmpty(SlowStack<String> slowStack, boolean empty) throws Exception {
		Thread thread = this.buildThread(this.buildRunnablePush(slowStack, "first"));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(empty, slowStack.isEmpty());
		thread.join();
		assertEquals("first", slowStack.pop());
		assertTrue(slowStack.isEmpty());
	}


	private interface SlowStack<E> extends Stack<E> {
		Object slowPop();
		void slowPush(E element);
	}

	private class SlowLinkedStack<E> extends LinkedStack<E> implements SlowStack<E> {
		private static final long serialVersionUID = 1L;
		SlowLinkedStack() {
			super();
		}
		public Object slowPop() {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.pop();
		}
		public void slowPush(E element) {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			this.push(element);
		}
	}

	private class SlowSynchronizedStack<E> extends SynchronizedStack<E> implements SlowStack<E> {
		private static final long serialVersionUID = 1L;
		SlowSynchronizedStack() {
			super(StackTools.<E>linkedStack());
		}
		public Object slowPop() {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				return this.pop();
			}
		}
		public void slowPush(E element) {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				this.push(element);
			}
		}
	}

	// ********** waits **********

	public void testWaitUntilEmpty() throws Exception {
		this.verifyWaitUntilEmpty(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...and the stack should be empty
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilEmpty2() throws Exception {
		this.verifyWaitUntilEmpty(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...and the stack should be empty
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilEmptyTimeout() throws Exception {
		this.verifyWaitUntilEmpty(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the stack was popped...
		assertSame(ITEM_1, this.poppedObject);
		// ...and the stack should be empty
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitUntilEmpty(long timeout) throws Exception {
		this.ss.push(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildPopCommand(), this.ss, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitUntilEmptyCommand(timeout), this.ss, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitUntilEmptyCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				SynchronizedStackTests.this.startTime = System.currentTimeMillis();
				SynchronizedStackTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedStack);
				SynchronizedStackTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				if (timeout < 0) {
					synchronizedStack.waitUntilEmpty();
					return false;
				}
				return ! synchronizedStack.waitUntilEmpty(timeout);
			}
		};
	}

	public void testWaitUntilNotEmpty() throws Exception {
		this.verifyWaitUntilNotEmpty(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been pushed by t1...
		assertFalse(this.ss.isEmpty());
		assertSame(ITEM_1, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilNotEmpty2() throws Exception {
		this.verifyWaitUntilNotEmpty(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been pushed by t1...
		assertFalse(this.ss.isEmpty());
		assertSame(ITEM_1, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilNotEmptyTimeout() throws Exception {
		this.verifyWaitUntilNotEmpty(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and an item should have been pushed by t1...
		assertFalse(this.ss.isEmpty());
		assertSame(ITEM_1, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitUntilNotEmpty(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildPushCommand(), this.ss, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitUntilNotEmptyCommand(timeout), this.ss, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitUntilNotEmptyCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				SynchronizedStackTests.this.startTime = System.currentTimeMillis();
				SynchronizedStackTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedStack);
				SynchronizedStackTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				if (timeout < 0) {
					synchronizedStack.waitUntilNotEmpty();
					return false;
				}
				return ! synchronizedStack.waitUntilNotEmpty(timeout);
			}
		};
	}

	public void testWaitToPop() throws Exception {
		this.verifyWaitToPop(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been popped by t2...
		assertSame(ITEM_1, this.poppedObject);
		// ...and the stack should be empty
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToPop2() throws Exception {
		this.verifyWaitToPop(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been popped by t2...
		assertSame(ITEM_1, this.poppedObject);
		// ...and the stack should be empty
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToPopTimeout() throws Exception {
		this.verifyWaitToPop(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the stack was never popped...
		assertNull(this.poppedObject);
		// ...and it still holds the item
		assertSame(ITEM_1, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToPop(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildPushCommand(), this.ss, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToPopCommand(timeout), this.ss, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToPopCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				SynchronizedStackTests.this.startTime = System.currentTimeMillis();
				this.waitToPop(synchronizedStack);
				SynchronizedStackTests.this.endTime = System.currentTimeMillis();
			}
			private void waitToPop(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				if (timeout < 0) {
					SynchronizedStackTests.this.poppedObject = synchronizedStack.waitToPop();
					return;
				}
				try {
					SynchronizedStackTests.this.poppedObject = synchronizedStack.waitToPop(timeout);
				} catch (EmptyStackException ex) {
					SynchronizedStackTests.this.timeoutOccurred = true;
				}
			}
		};
	}

	public void testWaitToPush() throws Exception {
		this.verifyWaitToPush(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the stack gets popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...and an item is pushed on to the stack by t2
		assertFalse(this.ss.isEmpty());
		assertSame(ITEM_2, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToPush2() throws Exception {
		this.verifyWaitToPush(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the stack gets popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...and an item is pushed on to the stack by t2
		assertFalse(this.ss.isEmpty());
		assertSame(ITEM_2, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToPushTimeout() throws Exception {
		this.verifyWaitToPush(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the stack is eventually popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...but nothing is pushed on to the stack by t2
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToPush(long timeout) throws Exception {
		this.ss.push(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildPopCommand(), this.ss, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToPushCommand(timeout), this.ss, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToPushCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				SynchronizedStackTests.this.startTime = System.currentTimeMillis();
				SynchronizedStackTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedStack);
				SynchronizedStackTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				if (timeout < 0) {
					synchronizedStack.waitToPush(ITEM_2);
					return false;
				}
				return ! synchronizedStack.waitToPush(ITEM_2, timeout);
			}
		};
	}

	private Command buildPushCommand() {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) {
				synchronizedStack.push(ITEM_1);
			}
		};
	}

	private Command buildPopCommand() {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) {
				SynchronizedStackTests.this.poppedObject = synchronizedStack.pop();
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedStack<String> synchronizedStack, final long sleep) {
		return new TestRunnable() {
			@Override
			protected void run_() throws Throwable {
				if (sleep != 0) {
					Thread.sleep(sleep);
				}
				command.execute(synchronizedStack);
			}
		};
	}

	long calculateElapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException;
	}


	// ********** execute **********

	public void testExecute() throws Exception {
		org.eclipse.jpt.common.utility.command.Command command = new org.eclipse.jpt.common.utility.command.Command() {
			public void execute() {
				SynchronizedStackTests.this.commandExecuted = true;
			}
		};
		this.commandExecuted = false;
		this.ss.execute(command);
		assertTrue(this.commandExecuted);
	}


	// ********** additional protocol **********

	public void testPushAllIterable() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		this.ss.pushAll(list);
		assertEquals("three", this.ss.pop());
		assertEquals("two", this.ss.pop());
		assertEquals("one", this.ss.pop());
		assertTrue(this.ss.isEmpty());
	}

	public void testPushAllIterable_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		this.ss.pushAll(list);
		assertTrue(this.ss.isEmpty());
	}

	public void testPushAllObjectArray() throws Exception {
		this.ss.pushAll(new String[] { "one", "two", "three" });
		assertEquals("three", this.ss.pop());
		assertEquals("two", this.ss.pop());
		assertEquals("one", this.ss.pop());
		assertTrue(this.ss.isEmpty());
	}

	public void testPushAllObjectArray_empty() throws Exception {
		this.ss.pushAll(new String[0]);
		assertTrue(this.ss.isEmpty());
	}

	public void testPushAllStack() throws Exception {
		Stack<String> s2 = StackTools.arrayStack();
		s2.push("one");
		s2.push("two");
		s2.push("three");
		this.ss.pushAll(s2);
		assertEquals("one", this.ss.pop());
		assertEquals("two", this.ss.pop());
		assertEquals("three", this.ss.pop());
		assertTrue(this.ss.isEmpty());
	}

	public void testPushAllStack_empty() throws Exception {
		Stack<String> s2 = StackTools.arrayStack();
		this.ss.pushAll(s2);
		assertTrue(this.ss.isEmpty());
	}

	public void testPushAllQueue() throws Exception {
		Queue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("one");
		queue.enqueue("two");
		queue.enqueue("three");
		this.ss.pushAll(queue);
		assertEquals("three", this.ss.pop());
		assertEquals("two", this.ss.pop());
		assertEquals("one", this.ss.pop());
		assertTrue(this.ss.isEmpty());
	}

	public void testPushAllQueue_empty() throws Exception {
		Queue<String> queue = QueueTools.arrayQueue();
		this.ss.pushAll(queue);
		assertTrue(this.ss.isEmpty());
	}

	public void testPopAll() throws Exception {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("three");
		ArrayList<String> list = this.ss.popAll();
		assertTrue(this.ss.isEmpty());
		assertEquals("three", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("one", list.get(2));
	}

	public void testPopAll_empty() throws Exception {
		ArrayList<String> list = this.ss.popAll();
		assertTrue(this.ss.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testPopAllToCollection() throws Exception {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("three");
		ArrayList<String> list = new ArrayList<String>();
		assertTrue(this.ss.popAllTo(list));
		assertTrue(this.ss.isEmpty());
		assertEquals("three", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("one", list.get(2));
	}

	public void testPopAllToCollection_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		assertFalse(this.ss.popAllTo(list));
		assertTrue(this.ss.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testPopAllToListInt() throws Exception {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.ss.popAllTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("three", list.get(2));
		assertEquals("two", list.get(3));
		assertEquals("one", list.get(4));
		assertEquals("ccc", list.get(5));
	}

	public void testPopAllToListInt_end() throws Exception {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.ss.popAllTo(list, 3));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
		assertEquals("three", list.get(3));
		assertEquals("two", list.get(4));
		assertEquals("one", list.get(5));
	}

	public void testPopAllToListInt_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertFalse(this.ss.popAllTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
	}

	public void testPopAllToStack() throws Exception {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("three");
		Stack<String> s2 = StackTools.arrayStack();
		assertTrue(this.ss.popAllTo(s2));
		assertTrue(this.ss.isEmpty());
		assertEquals("one", s2.pop());
		assertEquals("two", s2.pop());
		assertEquals("three", s2.pop());
		assertTrue(s2.isEmpty());
	}

	public void testPopAllToStack_empty() throws Exception {
		Stack<String> s2 = StackTools.arrayStack();
		assertFalse(this.ss.popAllTo(s2));
		assertTrue(this.ss.isEmpty());
		assertTrue(s2.isEmpty());
	}

	public void testPopAllToQueue() throws Exception {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("three");
		Queue<String> queue = QueueTools.arrayQueue();
		assertTrue(this.ss.popAllTo(queue));
		assertTrue(this.ss.isEmpty());
		assertEquals("three", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("one", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testPopAllToQueue_empty() throws Exception {
		Queue<String> queue = QueueTools.arrayQueue();
		assertFalse(this.ss.popAllTo(queue));
		assertTrue(this.ss.isEmpty());
		assertTrue(queue.isEmpty());
	}

	public void testPopAllToMapTransformer() {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.ss.popAllTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testPopAllToMapTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.ss.popAllTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testPopAllToMapTransformerTransformer() {
		this.ss.push("one");
		this.ss.push("two");
		this.ss.push("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.ss.popAllTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testPopAllToMapTransformerTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.ss.popAllTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertTrue(map.isEmpty());
	}
}
