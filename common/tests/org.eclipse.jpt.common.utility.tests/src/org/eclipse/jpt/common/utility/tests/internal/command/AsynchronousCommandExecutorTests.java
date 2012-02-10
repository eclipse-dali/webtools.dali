/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.command;

import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandExecutor;
import org.eclipse.jpt.common.utility.internal.command.AsynchronousExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;

public class AsynchronousCommandExecutorTests
	extends MultiThreadedTestCase
{
	public AsynchronousCommandExecutorTests(String name) {
		super(name);
	}

	public void testExecution() throws Exception {
		TestCommand command = new TestCommand();
		AsynchronousExtendedCommandExecutor.SimpleConfig config = new AsynchronousExtendedCommandExecutor.SimpleConfig();
		config.setThreadFactory(this.buildThreadFactory());
		config.setExceptionHandler(ExceptionHandler.Runtime.instance());
		StatefulCommandExecutor commandExecutor = new AsynchronousExtendedCommandExecutor(config);
		commandExecutor.start();
		commandExecutor.execute(command);
		commandExecutor.execute(command);
		commandExecutor.execute(command);
		Thread.sleep(TWO_TICKS);  // wait for the command to execute
		commandExecutor.stop();
		assertEquals(3, command.count);
	}

	static class TestCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
		}
	}
}
