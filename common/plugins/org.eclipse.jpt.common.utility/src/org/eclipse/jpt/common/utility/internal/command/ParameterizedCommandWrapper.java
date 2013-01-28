/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.ParameterizedCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Command wrapper that can have its wrapped command changed,
 * allowing a client to change a previously-supplied command's
 * behavior mid-stream.
 * 
 * @parm <T> the type of the object passed to the command
 * @see #setCommand(ParameterizedCommand)
 */
public class ParameterizedCommandWrapper<T>
	implements ParameterizedCommand<T>
{
	protected volatile ParameterizedCommand<? super T> command;

	public ParameterizedCommandWrapper(ParameterizedCommand<? super T> command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public void execute(T argument) {
		this.command.execute(argument);
	}

	public void setCommand(ParameterizedCommand<? super T> command) {
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
