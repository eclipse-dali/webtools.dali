/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.listener;

import org.eclipse.jpt.common.utility.Command;

/**
 * Convenience implementation of {@link ChangeListener}.
 * All change notifications are funneled through a single command.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class CommandChangeListener
	extends SimpleChangeListener
{
	protected final Command command;

	/**
	 * Construct a change listener that executes the specified command whenever
	 * it receives any change notification from the model to which it is added
	 * as a listener.
	 */
	public CommandChangeListener(Command command) {
		super();
		this.command = command;
	}

	@Override
	protected void modelChanged() {
		this.command.execute();
	}

}
