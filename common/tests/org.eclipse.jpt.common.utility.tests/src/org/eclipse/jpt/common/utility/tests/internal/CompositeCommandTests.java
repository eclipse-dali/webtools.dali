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
import org.eclipse.jpt.common.utility.internal.CompositeCommand;

public class CompositeCommandTests extends TestCase {
	boolean command1Executed = false;
	boolean command2Executed = false;

	public CompositeCommandTests(String name) {
		super(name);
	}

	public void testRun() {
		Runnable runnable = new CommandRunnable(this.buildCompositeCommand());
		runnable.run();
		assertTrue(this.command1Executed);
		assertTrue(this.command2Executed);
	}

	public void testToString() {
		Runnable runnable = new CommandRunnable(this.buildCompositeCommand());
		assertNotNull(runnable.toString());
	}

	private Command buildCompositeCommand() {
		return new CompositeCommand(
					this.buildCommand1(),
					this.buildCommand2()
				);
	}

	private Command buildCommand1() {
		return new Command() {
			public void execute() {
				CompositeCommandTests.this.command1Executed = true;
			}
		};
	}

	private Command buildCommand2() {
		return new Command() {
			public void execute() {
				CompositeCommandTests.this.command2Executed = true;
			}
		};
	}

}
