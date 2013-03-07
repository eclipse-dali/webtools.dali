/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;
import org.eclipse.jpt.common.utility.internal.command.ThreadLocalExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class CommandExecutorTests
	extends MultiThreadedTestCase
{

	public CommandExecutorTests(String name) {
		super(name);
	}

	public void testDefaultCommandExecutor_toString() throws Exception {
		CommandContext commandContext = CommandContext.Default.instance();
		assertNotNull(commandContext.toString());
	}

	public void testDefaultCommandExecutor_serialization() throws Exception {
		CommandContext commandContext1 = CommandContext.Default.instance();
		CommandContext commandContext2 = TestTools.serialize(commandContext1);
		assertSame(commandContext1, commandContext2);
	}

	public void testDefaultCommandContext() {
		TestCommand testCommand = new TestCommand();
		assertEquals(0, testCommand.count);
		CommandContext commandContext = CommandContext.Default.instance();
		commandContext.execute(testCommand);
		assertEquals(1, testCommand.count);
	}

	static class TestCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
		}
	}

	public void testThreadLocalCommandContext_toString() throws Exception {
		CommandContext commandContext = new ThreadLocalExtendedCommandExecutor();
		assertNotNull(commandContext.toString());
	}

	public void testThreadLocalCommandContext() throws Exception {
		ThreadLocalExtendedCommandExecutor threadLocalCommandContext = new ThreadLocalExtendedCommandExecutor();
		TestRunnable testRunnable1 = new TestRunnable(threadLocalCommandContext, 1);
		Thread thread1 = this.buildThread(testRunnable1);
		thread1.run();

		TestRunnable testRunnable2 = new TestRunnable(threadLocalCommandContext, 2);
		Thread thread2 = this.buildThread(testRunnable2);
		thread2.run();

		TestRunnable testRunnable3 = new TestRunnable(threadLocalCommandContext, 3, null);
		Thread thread3 = this.buildThread(testRunnable3);
		thread3.run();

		thread1.join();
		thread2.join();
		thread3.join();

		assertEquals(1, testRunnable1.testCommand.count);
		assertEquals(1, testRunnable1.testCommandContext.count);

		assertEquals(2, testRunnable2.testCommand.count);
		assertEquals(2, testRunnable2.testCommandContext.count);

		assertEquals(3, testRunnable3.testCommand.count);
		assertNull(testRunnable3.testCommandContext);
	}

	static class TestCommandContext implements ExtendedCommandContext {
		int count = 0;
		public void execute(Command command) {
			this.count++;
			command.execute();
		}
		public void waitToExecute(Command command) {
			this.execute(command);
		}
		public boolean waitToExecute(Command command, long timeout) {
			this.execute(command);
			return true;
		}
	}

	static class TestRunnable implements Runnable {
		final ThreadLocalExtendedCommandExecutor threadLocalCommandContext;
		final int executionCount;
		final TestCommand testCommand = new TestCommand();
		final TestCommandContext testCommandContext;
		TestRunnable(ThreadLocalExtendedCommandExecutor threadLocalCommandContext, int executionCount) {
			this(threadLocalCommandContext, executionCount, new TestCommandContext());
		}
		TestRunnable(ThreadLocalExtendedCommandExecutor threadLocalCommandContext, int executionCount, TestCommandContext testCommandContext) {
			super();
			this.threadLocalCommandContext = threadLocalCommandContext;
			this.executionCount = executionCount;
			this.testCommandContext = testCommandContext;
		}
		public void run() {
			this.threadLocalCommandContext.set(this.testCommandContext);
			for (int i = 0; i < this.executionCount; i++) {
				this.threadLocalCommandContext.execute(this.testCommand);
			}
		}
	}
}
