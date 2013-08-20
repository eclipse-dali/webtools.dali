/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.command;

/**
 * Simple interface for implementing the GOF Command design pattern
 * and allows for the command to throw an {@link InterruptedException}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see Command
 * @see org.eclipse.jpt.common.utility.factory.InterruptibleFactory
 * @see org.eclipse.jpt.common.utility.factory.Factory
 * @see org.eclipse.jpt.common.utility.command.InterruptibleParameterizedCommand
 * @see org.eclipse.jpt.common.utility.command.ParameterizedCommand
 * @see org.eclipse.jpt.common.utility.transformer.Transformer
 */
public interface InterruptibleCommand {

	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 */
	void execute() throws InterruptedException;
}
