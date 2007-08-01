/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Yet another level of indirection to allow clients to control
 * how a command is executed by the server
 * (e.g. dispatching the command to the UI thread).
 */
public interface CommandExecutorProvider {

	/**
	 * Return the appropriate command executor.
	 */
	CommandExecutor commandExecutor();

}
