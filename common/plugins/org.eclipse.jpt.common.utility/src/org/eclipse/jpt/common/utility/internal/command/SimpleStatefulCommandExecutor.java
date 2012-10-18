/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.CommandExecutor;

/**
 * Straightforward implementation of {@link org.eclipse.jpt.common.utility.command.StatefulCommandExecutor}
 * that executes commands immediately by default. This executor can
 * also be used to adapt simple {@link CommandExecutor}s to the
 * {@link org.eclipse.jpt.common.utility.command.StatefulCommandExecutor} interface, providing support for
 * lifecycle state.
 */
public class SimpleStatefulCommandExecutor
	extends AbstractStatefulCommandExecutor<CommandExecutor>
{
	public SimpleStatefulCommandExecutor() {
		this(CommandExecutor.Default.instance());
	}

	public SimpleStatefulCommandExecutor(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}
}
