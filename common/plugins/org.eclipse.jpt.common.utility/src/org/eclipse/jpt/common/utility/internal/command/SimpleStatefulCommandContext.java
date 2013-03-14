/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
 * that executes commands immediately by default. This context can
 * also be used to adapt simple {@link CommandContext}s to the
 * {@link org.eclipse.jpt.common.utility.command.StatefulCommandContext} interface, providing support for
 * lifecycle state.
 */
public class SimpleStatefulCommandContext
	extends AbstractStatefulCommandContext<CommandContext>
{
	public SimpleStatefulCommandContext() {
		this(DefaultCommandContext.instance());
	}

	public SimpleStatefulCommandContext(CommandContext commandContext) {
		super(commandContext);
	}
}
