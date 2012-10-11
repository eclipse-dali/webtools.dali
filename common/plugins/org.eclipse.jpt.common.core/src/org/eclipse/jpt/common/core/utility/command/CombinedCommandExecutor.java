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
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandExecutor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Combine the job command executor and command executor interfaces.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CombinedCommandExecutor
	extends JobCommandExecutor, CommandExecutor
{
	/**
	 * Singleton implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default
		implements CombinedCommandExecutor, Serializable
	{
		public static final CombinedCommandExecutor INSTANCE = new Default();
		public static CombinedCommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public void execute(Command command) {
			command.execute();
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


	/**
	 * Singleton implementation of the command executor interface
	 * that ignores any commands.
	 */
	final class Inactive
		implements CombinedCommandExecutor, Serializable
	{
		public static final CombinedCommandExecutor INSTANCE = new Inactive();
		public static CombinedCommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Inactive() {
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
}
