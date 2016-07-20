/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.closure;

/**
 * Simple interface for implementing a command that takes two arguments
 * and allows for the closure to throw an {@link InterruptedException}.
 * The expectation is the closure will have side effects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <A1> the type of the first object passed to the command
 * @param <A2> the type of the second object passed to the command
 * 
 * @see BiClosure
 * @see InterruptibleClosure
 * @see org.eclipse.jpt.common.utility.command.InterruptibleCommand
 * @see org.eclipse.jpt.common.utility.factory.InterruptibleFactory
 * @see org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer
 */
public interface InterruptibleBiClosure<A1, A2> {

	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 */
	void execute(A1 argument1, A2 argument2) throws InterruptedException;
}
