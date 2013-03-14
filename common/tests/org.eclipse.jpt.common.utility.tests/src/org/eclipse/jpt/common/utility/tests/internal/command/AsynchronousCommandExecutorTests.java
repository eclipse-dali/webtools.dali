/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.command.AsynchronousExtendedCommandContext;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;

public class AsynchronousCommandExecutorTests
	extends MultiThreadedTestCase
{
	public AsynchronousCommandExecutorTests(String name) {
		super(name);
	}

	public void testExecution() throws Exception {
		TestCommand command = new TestCommand();
		AsynchronousExtendedCommandContext.SimpleConfig config = new AsynchronousExtendedCommandContext.SimpleConfig();
		config.setThreadFactory(this.buildThreadFactory());
		config.setExceptionHandler(RuntimeExceptionHandler.instance());
		StatefulCommandContext commandContext = new AsynchronousExtendedCommandContext(config);
		commandContext.start();
		commandContext.execute(command);
		commandContext.execute(command);
		commandContext.execute(command);
		Thread.sleep(TWO_TICKS);  // wait for the command to execute
		commandContext.stop();
		assertEquals(3, command.count);
	}

	static class TestCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
		}
	}
}
