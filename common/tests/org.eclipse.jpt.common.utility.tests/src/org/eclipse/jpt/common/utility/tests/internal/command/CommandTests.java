/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.command.CommandRunnable;
import org.eclipse.jpt.common.utility.internal.command.CommandTools;
import org.eclipse.jpt.common.utility.internal.command.DisabledCommand;
import org.eclipse.jpt.common.utility.internal.command.NullCommand;
import org.eclipse.jpt.common.utility.internal.command.RunnableCommand;
import org.eclipse.jpt.common.utility.internal.command.ThreadLocalCommand;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class CommandTests
	extends MultiThreadedTestCase
{
	public CommandTests(String name) {
		super(name);
	}

	public void testNullCommand() {
		Command command = NullCommand.instance();
		command.execute();  // just make sure it doesn't blow up?
	}

	public void testNullCommand_toString() {
		Command command = NullCommand.instance();
		assertNotNull(command.toString());
	}

	public void testNullCommand_serialization() throws Exception {
		Command command1 = NullCommand.instance();
		Command command2 = TestTools.serialize(command1);
		assertSame(command1, command2);
	}

	public void testDisabledCommand() {
		Command command = DisabledCommand.instance();
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
		Command command = DisabledCommand.instance();
		assertNotNull(command.toString());
	}

	public void testDisabledCommand_serialization() throws Exception {
		Command command1 = DisabledCommand.instance();
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
		ThreadLocalCommand threadLocalCommand = CommandTools.threadLocalCommand();
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

	static class TestCommandContext implements CommandContext {
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
