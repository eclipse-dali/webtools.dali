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
import org.eclipse.jpt.utility.internal.CommandRunnable;
import org.eclipse.jpt.utility.internal.RunnableCommand;

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
		TestRunnable testRunnable = new TestRunnable();
		assertFalse(testRunnable.ran);
		Command command = new RunnableCommand(testRunnable);
		command.execute();
		assertTrue(testRunnable.ran);
	}

	static class TestRunnable implements Runnable {
		boolean ran = false;
		public void run() {
			this.ran = true;
		}
	}

	public void testCommandRunnable() {
		TestCommand testCommand = new TestCommand();
		assertFalse(testCommand.executed);
		Runnable runnable = new CommandRunnable(testCommand);
		runnable.run();
		assertTrue(testCommand.executed);
	}

	static class TestCommand implements Command {
		boolean executed = false;
		public void execute() {
			this.executed = true;
		}
	}

}
