/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.AsynchronousCommandExecutor;
import org.eclipse.jpt.utility.internal.StatefulCommandExecutor;

public class AsynchronousCommandExecutorTests extends TestCase {

	public AsynchronousCommandExecutorTests(String name) {
		super(name);
	}

	public void testExecution() throws Exception {
		TestCommand command = new TestCommand();
		StatefulCommandExecutor commandExecutor = new AsynchronousCommandExecutor();
		commandExecutor.start();
		commandExecutor.execute(command);
		commandExecutor.execute(command);
		commandExecutor.execute(command);
		Thread.sleep(200);  // wait for the command to execute
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
