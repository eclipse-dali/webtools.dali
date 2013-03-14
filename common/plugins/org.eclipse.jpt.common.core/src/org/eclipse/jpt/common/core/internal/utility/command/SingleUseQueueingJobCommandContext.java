/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.command.SingleUseQueueingCommandContext;

/**
 * This command context wraps and extends a {@link SingleUseQueueingCommandContext},
 * adding support for executing {@link JobCommand}s.
 */
public class SingleUseQueueingJobCommandContext
	extends AbstractSingleUseQueueingJobCommandContext<SingleUseQueueingCommandContext, StatefulCommandContext>
{
	public SingleUseQueueingJobCommandContext() {
		this(new SingleUseQueueingCommandContext());
	}

	public SingleUseQueueingJobCommandContext(CommandContext commandContext) {
		this(new SingleUseQueueingCommandContext(commandContext));
	}

	public SingleUseQueueingJobCommandContext(StatefulCommandContext commandContext) {
		this(new SingleUseQueueingCommandContext(commandContext));
	}

	public SingleUseQueueingJobCommandContext(SingleUseQueueingCommandContext commandContext) {
		super(commandContext);
	}
}
