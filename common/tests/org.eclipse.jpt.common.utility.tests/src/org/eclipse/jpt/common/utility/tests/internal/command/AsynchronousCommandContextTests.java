/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.command.AsynchronousExtendedCommandContext;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;

public class AsynchronousCommandContextTests
	extends MultiThreadedTestCase
{
	public AsynchronousCommandContextTests(String name) {
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
