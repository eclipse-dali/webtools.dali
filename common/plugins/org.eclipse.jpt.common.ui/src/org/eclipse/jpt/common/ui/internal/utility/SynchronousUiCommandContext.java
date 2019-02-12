/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.utility;

import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;

/**
 * This command context synchronously executes a {@link Command command}
 * on the UI thread.
 * <p>
 * This command context allows a non-UI process to modify any objects
 * associated with documents that are currently displayed in the UI.
 */
public final class SynchronousUiCommandContext
	extends AbstractUiCommandContext
{
	public static final ExtendedCommandContext INSTANCE = new SynchronousUiCommandContext();

	public static ExtendedCommandContext instance() {
		return INSTANCE;
	}

	// ensure single instance
	private SynchronousUiCommandContext() {
		super();
	}

	public void execute(Command command) {
		DisplayTools.syncExec(this.buildRunnable(command));
	}

	public void waitToExecute(Command command) {
		this.execute(command);
	}
}
