/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.ThreadLocalCommandExecutor;

public class CommandExecutorTests extends TestCase {

	public CommandExecutorTests(String name) {
		super(name);
	}

	public void testDefaultCommandExecutor_toString() throws Exception {
		CommandExecutor commandExecutor = CommandExecutor.Default.instance();
		assertNotNull(commandExecutor.toString());
	}

	public void testDefaultCommandExecutor_serialization() throws Exception {
		CommandExecutor commandExecutor1 = CommandExecutor.Default.instance();
		CommandExecutor commandExecutor2 = TestTools.serialize(commandExecutor1);
		assertSame(commandExecutor1, commandExecutor2);
	}

	public void testDefaultCommandExecutor() {
		TestCommand testCommand = new TestCommand();
		assertEquals(0, testCommand.count);
		CommandExecutor commandExecutor = CommandExecutor.Default.instance();
		commandExecutor.execute(testCommand);
		assertEquals(1, testCommand.count);
	}

	static class TestCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
		}
	}

	public void testThreadLocalCommandExecutor_toString() throws Exception {
		CommandExecutor commandExecutor = new ThreadLocalCommandExecutor();
		assertNotNull(commandExecutor.toString());
	}

	public void testThreadLocalCommandExecutor() throws Exception {
		ThreadLocalCommandExecutor threadLocalCommandExecutor = new ThreadLocalCommandExecutor();
		TestRunnable testRunnable1 = new TestRunnable(threadLocalCommandExecutor, 1);
		Thread thread1 = new Thread(testRunnable1);
		thread1.run();

		TestRunnable testRunnable2 = new TestRunnable(threadLocalCommandExecutor, 2);
		Thread thread2 = new Thread(testRunnable2);
		thread2.run();

		TestRunnable testRunnable3 = new TestRunnable(threadLocalCommandExecutor, 3, null);
		Thread thread3 = new Thread(testRunnable3);
		thread3.run();

		thread1.join();
		thread2.join();
		thread3.join();

		assertEquals(1, testRunnable1.testCommand.count);
		assertEquals(1, testRunnable1.testCommandExecutor.count);

		assertEquals(2, testRunnable2.testCommand.count);
		assertEquals(2, testRunnable2.testCommandExecutor.count);

		assertEquals(3, testRunnable3.testCommand.count);
		assertNull(testRunnable3.testCommandExecutor);
	}

	static class TestCommandExecutor implements CommandExecutor {
		int count = 0;
		public void execute(Command command) {
			this.count++;
			command.execute();
		}
	}

	static class TestRunnable implements Runnable {
		final ThreadLocalCommandExecutor threadLocalCommandExecutor;
		final int executionCount;
		final TestCommand testCommand = new TestCommand();
		final TestCommandExecutor testCommandExecutor;
		TestRunnable(ThreadLocalCommandExecutor threadLocalCommandExecutor, int executionCount) {
			this(threadLocalCommandExecutor, executionCount, new TestCommandExecutor());
		}
		TestRunnable(ThreadLocalCommandExecutor threadLocalCommandExecutor, int executionCount, TestCommandExecutor testCommandExecutor) {
			super();
			this.threadLocalCommandExecutor = threadLocalCommandExecutor;
			this.executionCount = executionCount;
			this.testCommandExecutor = testCommandExecutor;
		}
		public void run() {
			this.threadLocalCommandExecutor.set(this.testCommandExecutor);
			for (int i = 0; i < this.executionCount; i++) {
				this.threadLocalCommandExecutor.execute(this.testCommand);
			}
		}
	}

}
