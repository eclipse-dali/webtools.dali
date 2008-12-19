/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.CommandRunnable;
import org.eclipse.jpt.utility.internal.RunnableCommand;
import org.eclipse.jpt.utility.internal.ThreadLocalCommand;

public class CommandTests extends TestCase {

	public CommandTests(String name) {
		super(name);
	}

	public void testNullCommand() {
		Command command = Command.Null.instance();
		command.execute();  // just make sure it doesn't blow up?
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
		Thread thread1 = new Thread(testRunnable1);
		thread1.run();

		TestRunnable testRunnable2 = new TestRunnable(threadLocalCommand, 2);
		Thread thread2 = new Thread(testRunnable2);
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
