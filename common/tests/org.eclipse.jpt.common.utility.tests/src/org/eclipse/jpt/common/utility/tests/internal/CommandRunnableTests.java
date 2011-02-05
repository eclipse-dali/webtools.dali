/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.Command;
import org.eclipse.jpt.common.utility.internal.CommandRunnable;

@SuppressWarnings("nls")
public class CommandRunnableTests extends TestCase {
	boolean commandExecuted = false;

	public CommandRunnableTests(String name) {
		super(name);
	}

	public void testNullCommand() {
		boolean exCaught = false;
		try {
			Runnable runnable = new CommandRunnable(null);
			fail("bogus: " + runnable);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRun() {
		Runnable runnable = new CommandRunnable(this.buildCommand());
		runnable.run();
		assertTrue(this.commandExecuted);
	}

	public void testToString() {
		Runnable runnable = new CommandRunnable(this.buildCommand());
		assertNotNull(runnable.toString());
	}

	private Command buildCommand() {
		return new Command() {
			public void execute() {
				CommandRunnableTests.this.commandExecuted = true;
			}
		};
	}

}
