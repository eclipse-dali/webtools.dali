/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.command.SingleUseQueueingCommandExecutor;

/**
 * This command executor wraps and extends a {@link SingleUseQueueingCommandExecutor},
 * adding support for executing {@link JobCommand}s.
 */
public class SingleUseQueueingJobCommandExecutor
	extends AbstractSingleUseQueueingJobCommandContext<SingleUseQueueingCommandExecutor, StatefulCommandContext>
{
	public SingleUseQueueingJobCommandExecutor() {
		this(new SingleUseQueueingCommandExecutor());
	}

	public SingleUseQueueingJobCommandExecutor(CommandContext commandExecutor) {
		this(new SingleUseQueueingCommandExecutor(commandExecutor));
	}

	public SingleUseQueueingJobCommandExecutor(StatefulCommandContext commandExecutor) {
		this(new SingleUseQueueingCommandExecutor(commandExecutor));
	}

	public SingleUseQueueingJobCommandExecutor(SingleUseQueueingCommandExecutor commandExecutor) {
		super(commandExecutor);
	}
}
