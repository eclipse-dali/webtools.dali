/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.factory;

/**
 * Simple interface for implementing the Factory design pattern.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <T> the type of the object created by the factory
 * 
 * @see org.eclipse.jpt.common.utility.command.Command
 * @see org.eclipse.jpt.common.utility.command.ParameterizedCommand
 * @see org.eclipse.jpt.common.utility.transformer.Transformer
 */
public interface Factory<T>
	extends InterruptibleFactory<T>
{
	/**
	 * Create a new object. The semantics of the factory
	 * is determined by the contract between the client and server.
	 */
	T create();
}
