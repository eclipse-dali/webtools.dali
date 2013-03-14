/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.command;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * This interface extends the normal command context to allow the client
 * to control when a command is required to be executed synchronously.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ExtendedJobCommandContext
	extends JobCommandContext
{
	/**
	 * Suspend the current thread until the specified command is executed.
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command context. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand)
	 */
	void waitToExecute(JobCommand command) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is executed
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed in the allotted time;
	 * return <code>false</code> if a time-out occurred and the command was
	 * <em>not</em> executed.
	 * If the time-out is zero, wait indefinitely.
	 * <p>
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command context. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand)
	 */
	boolean waitToExecute(JobCommand command, long timeout) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is executed.
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command context. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand, String)
	 */
	void waitToExecute(JobCommand command, String jobName) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is executed
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed in the allotted time;
	 * return <code>false</code> if a time-out occurred and the command was
	 * <em>not</em> executed.
	 * If the time-out is zero, wait indefinitely.
	 * <p>
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command context. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand, String)
	 */
	boolean waitToExecute(JobCommand command, String jobName, long timeout) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is executed.
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command context. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand, String, ISchedulingRule)
	 */
	void waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule) throws InterruptedException;


	/**
	 * Suspend the current thread until the specified command is executed
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed in the allotted time;
	 * return <code>false</code> if a time-out occurred and the command was
	 * <em>not</em> executed.
	 * If the time-out is zero, wait indefinitely.
	 * <p>
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command context. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand, String, ISchedulingRule)
	 */
	boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule, long timeout) throws InterruptedException;
}
