/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import org.eclipse.jpt.common.utility.CommandExecutor;

/**
 * This interface allows clients to control how a command is executed.
 * This is useful when the server provides the command but the client provides
 * the context (e.g. the client would like to dispatch the command to the UI
 * thread).
 */
public interface StatefulCommandExecutor
	extends CommandExecutor
{
	/**
	 * Start the command executor.
	 */
	void start();

	/**
	 * Stop the command executor.
	 */
	void stop();

}
