/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.util.EventListener;

import org.eclipse.jpt.utility.Command;

/**
 * This command executor notifies clients commands are executed.
 */
public interface CallbackStatefulCommandExecutor
	extends StatefulCommandExecutor
{
	/**
	 * Add the specified listener to be notified whenever a command has been
	 * executed.
	 * @see #removeListener(Listener)
	 */
	void addListener(Listener listener);

	/**
	 * Remove the specified listener.
	 * @see #addListener(Listener)
	 */
	void removeListener(Listener listener);


	// ********** listener **********

	/**
	 * Interface implemented by listeners to be notified whenever a
	 * command is executed.
	 */
	public interface Listener
		extends EventListener
	{
		/**
		 * The specified command was executed.
		 */
		void commandExecuted(Command command);
	}

}
