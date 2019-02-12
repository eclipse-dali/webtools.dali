/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;

/**
 * @see AbstractSingleUseQueueingCommandContext
 */
public class SingleUseQueueingCommandContext
	extends AbstractSingleUseQueueingCommandContext<StatefulCommandContext>
{
	public SingleUseQueueingCommandContext() {
		this(DefaultExtendedCommandContext.instance());
	}

	public SingleUseQueueingCommandContext(CommandContext commandContext) {
		this(new SimpleStatefulCommandContext(commandContext));
	}

	public SingleUseQueueingCommandContext(StatefulCommandContext commandContext) {
		super(commandContext);
	}
}
