/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import java.io.Serializable;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.common.core.utility.command.CombinedCommandContext;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the command context interface
 * that ignores any commands.
 */
public final class InactiveCombinedCommandContext
	implements CombinedCommandContext, Serializable
{
	public static final CombinedCommandContext INSTANCE = new InactiveCombinedCommandContext();

	public static CombinedCommandContext instance() {
		return INSTANCE;
	}

	// ensure single instance
	private InactiveCombinedCommandContext() {
		super();
	}

	public void execute(Command command) {
		// do nothing
	}

	public void execute(JobCommand command) {
		// do nothing
	}

	public void execute(JobCommand command, String jobName) {
		// do nothing
	}

	public void execute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
		// do nothing
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
