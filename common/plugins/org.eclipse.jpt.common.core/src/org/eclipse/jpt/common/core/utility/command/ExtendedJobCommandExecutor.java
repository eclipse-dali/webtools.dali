/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.command;

import java.io.Serializable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * This interface extends the normal command executor to allow the client
 * to control when a command is required to be executed synchronously.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ExtendedJobCommandExecutor
	extends JobCommandExecutor
{
	/**
	 * Suspend the current thread until the specified command is executed.
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command executor. The command may or may
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
	 * previously passed to the command executor. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand)
	 */
	boolean waitToExecute(JobCommand command, long timeout) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is executed.
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command executor. The command may or may
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
	 * previously passed to the command executor. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand, String)
	 */
	boolean waitToExecute(JobCommand command, String jobName, long timeout) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is executed.
	 * The command itself must be executed <em>after</em> any other commands
	 * previously passed to the command executor. The command may or may
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
	 * previously passed to the command executor. The command may or may
	 * not be assigned to a {@link org.eclipse.core.runtime.jobs.Job}
	 * for execution.
	 * @see #execute(JobCommand, String, ISchedulingRule)
	 */
	boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule, long timeout) throws InterruptedException;


	/**
	 * Singleton implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default
		implements ExtendedJobCommandExecutor, Serializable
	{
		public static final ExtendedJobCommandExecutor INSTANCE = new Default();
		public static ExtendedJobCommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public void execute(JobCommand command) {
			command.execute(new NullProgressMonitor());
		}
		public void execute(JobCommand command, String jobName) {
			this.execute(command);
		}
		public void execute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
			this.execute(command);
		}
		public void waitToExecute(JobCommand command) {
			this.execute(command);
		}
		public boolean waitToExecute(JobCommand command, long timeout) {
			this.execute(command);
			return true;
		}
		public void waitToExecute(JobCommand command, String jobName) {
			this.execute(command);
		}
		public boolean waitToExecute(JobCommand command, String jobName, long timeout) {
			this.execute(command);
			return true;
		}
		public void waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
			this.execute(command);
		}
		public boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule, long timeout) {
			this.execute(command);
			return true;
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}


	/**
	 * Singleton implementation of the command executor interface
	 * that ignores any commands.
	 */
	final class Inactive
		implements ExtendedJobCommandExecutor, Serializable
	{
		public static final ExtendedJobCommandExecutor INSTANCE = new Inactive();
		public static ExtendedJobCommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Inactive() {
			super();
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
		public void waitToExecute(JobCommand command) {
			// do nothing
		}
		public boolean waitToExecute(JobCommand command, long timeout) throws InterruptedException {
			// do nothing
			return true;
		}
		public void waitToExecute(JobCommand command, String jobName) {
			// do nothing
		}
		public boolean waitToExecute(JobCommand command, String jobName, long timeout) throws InterruptedException {
			// do nothing
			return true;
		}
		public void waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
			// do nothing
		}
		public boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule, long timeout) throws InterruptedException {
			// do nothing
			return true;
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
