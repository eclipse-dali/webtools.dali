/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.command;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * This interface allows clients to control a job command's context.
 * This is useful when the server provides the command but the client provides
 * the context (e.g. the client would like to execute the command synchronously
 * rather than dispatching it to a job queue).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JobCommandContext {

	/**
	 * Execute the specified command, synchronously or asynchronously.
	 * Commands with the same scheduling rule must be executed in the order in
	 * which they are passed to the command context.
	 * The command may or may not be assigned to a
	 * {@link org.eclipse.core.runtime.jobs.Job} for execution.
	 */
	void execute(JobCommand command);

	/**
	 * Execute the specified command, synchronously or asynchronously.
	 * Commands with the same scheduling rule must be executed in the order in
	 * which they are passed to the command context.
	 * The command may or may not be assigned to a
	 * {@link org.eclipse.core.runtime.jobs.Job} for execution.
	 */
	void execute(JobCommand command, String jobName);

	/**
	 * Execute the specified command, synchronously or asynchronously.
	 * Commands with the same scheduling rule must be executed in the order in
	 * which they are passed to the command context.
	 * The command may or may not be assigned to a
	 * {@link org.eclipse.core.runtime.jobs.Job} for execution.
	 */
	void execute(JobCommand command, String jobName, ISchedulingRule schedulingRule);
}
