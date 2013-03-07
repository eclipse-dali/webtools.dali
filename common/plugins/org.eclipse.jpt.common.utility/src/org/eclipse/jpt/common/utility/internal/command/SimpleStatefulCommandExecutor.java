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

import org.eclipse.jpt.common.utility.command.CommandContext;

/**
 * Straightforward implementation of {@link org.eclipse.jpt.common.utility.command.StatefulCommandContext}
 * that executes commands immediately by default. This executor can
 * also be used to adapt simple {@link CommandContext}s to the
 * {@link org.eclipse.jpt.common.utility.command.StatefulCommandContext} interface, providing support for
 * lifecycle state.
 */
public class SimpleStatefulCommandExecutor
	extends AbstractStatefulCommandExecutor<CommandContext>
{
	public SimpleStatefulCommandExecutor() {
		this(CommandContext.Default.instance());
	}

	public SimpleStatefulCommandExecutor(CommandContext commandExecutor) {
		super(commandExecutor);
	}
}
