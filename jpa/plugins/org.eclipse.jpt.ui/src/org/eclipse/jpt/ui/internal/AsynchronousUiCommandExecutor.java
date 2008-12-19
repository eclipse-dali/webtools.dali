/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.CommandRunnable;
import org.eclipse.swt.widgets.Display;

/**
 * This implementation of CommandExecutor can be used by a non-UI
 * thread to asynchronously modify a JPA project with any objects associated
 * with documents that are currently displayed in the UI.
 */
public final class AsynchronousUiCommandExecutor
	implements CommandExecutor
{
	public static final CommandExecutor INSTANCE = new AsynchronousUiCommandExecutor();

	public static CommandExecutor instance() {
		return INSTANCE;
	}

	// ensure single instance
	private AsynchronousUiCommandExecutor() {
		super();
	}

	public void execute(Command command) {
		this.display().asyncExec(this.buildRunnable(command));
	}

	private Runnable buildRunnable(Command command) {
		return new CommandRunnable(command);
	}

	private Display display() {
		Display display = Display.getCurrent();
		return (display != null) ? display : Display.getDefault();
	}

}
