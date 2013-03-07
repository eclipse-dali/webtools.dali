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

import java.io.Serializable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Combine the job command context and command context interfaces.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CombinedCommandContext
	extends JobCommandContext, CommandContext
{
	/**
	 * Singleton implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default
		implements CombinedCommandContext, Serializable
	{
		public static final CombinedCommandContext INSTANCE = new Default();
		public static CombinedCommandContext instance() {
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
		implements CombinedCommandContext, Serializable
	{
		public static final CombinedCommandContext INSTANCE = new Inactive();
		public static CombinedCommandContext instance() {
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
