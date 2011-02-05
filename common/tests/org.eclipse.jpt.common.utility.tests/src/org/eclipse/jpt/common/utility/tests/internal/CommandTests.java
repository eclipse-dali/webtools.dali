/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import org.eclipse.jpt.common.utility.Command;
import org.eclipse.jpt.common.utility.CommandExecutor;
import org.eclipse.jpt.common.utility.internal.CommandRunnable;
import org.eclipse.jpt.common.utility.internal.RunnableCommand;
import org.eclipse.jpt.common.utility.internal.ThreadLocalCommand;

public class CommandTests
	extends MultiThreadedTestCase
{
	public CommandTests(String name) {
		super(name);
	}

	public void testNullCommand() {
		Command command = Command.Null.instance();
		command.execute();  // just make sure it doesn't blow up?
	}

	public void testNullCommand_toString() {
		Command command = Command.Null.instance();
		assertNotNull(command.toString());
	}

	public void testNullCommand_serialization() throws Exception {
		Command command1 = Command.Null.instance();
		Command command2 = TestTools.serialize(command1);
		assertSame(command1, command2);
	}

	public void testDisabledCommand() {
		Command command = Command.Disabled.instance();
		boolean exCaught = false;
		try {
			command.execute();
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledCommand_toString() {
		Command command = Command.Disabled.instance();
		assertNotNull(command.toString());
	}

	public void testDisabledCommand_serialization() throws Exception {
		Command command1 = Command.Disabled.instance();
		Command command2 = TestTools.serialize(command1);
		assertSame(command1, command2);
	}

	public void testRunnableCommand() {
		SimpleTestRunnable testRunnable = new SimpleTestRunnable();
		assertFalse(testRunnable.ran);
		Command command = new RunnableCommand(testRunnable);
		command.execute();
		assertTrue(testRunnable.ran);
	}

	static class SimpleTestRunnable implements Runnable {
		boolean ran = false;
		public void run() {
			this.ran = true;
		}
	}

	public void testCommandRunnable() {
		TestCommand testCommand = new TestCommand();
		assertEquals(0, testCommand.count);
		Runnable runnable = new CommandRunnable(testCommand);
		runnable.run();
		assertEquals(1, testCommand.count);
	}

	static class TestCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
		}
	}

	public void testThreadLocalCommand() throws Exception {
		ThreadLocalCommand threadLocalCommand = new ThreadLocalCommand();
		TestRunnable testRunnable1 = new TestRunnable(threadLocalCommand, 1);
		Thread thread1 = this.buildThread(testRunnable1);
		thread1.run();

		TestRunnable testRunnable2 = new TestRunnable(threadLocalCommand, 2);
		Thread thread2 = this.buildThread(testRunnable2);
		thread2.run();

		thread1.join();
		thread2.join();

		assertEquals(1, testRunnable1.testCommand.count);

		assertEquals(2, testRunnable2.testCommand.count);
	}

	static class TestCommandExecutor implements CommandExecutor {
		int count = 0;
		public void execute(Command command) {
			this.count++;
			command.execute();
		}
	}

	static class TestRunnable implements Runnable {
		final ThreadLocalCommand threadLocalCommand;
		final int executionCount;
		final TestCommand testCommand = new TestCommand();
		TestRunnable(ThreadLocalCommand threadLocalCommand, int executionCount) {
			super();
			this.threadLocalCommand = threadLocalCommand;
			this.executionCount = executionCount;
		}
		public void run() {
			this.threadLocalCommand.set(this.testCommand);
			for (int i = 0; i < this.executionCount; i++) {
				this.threadLocalCommand.execute();
			}
		}
	}

}
