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
import org.eclipse.jpt.common.utility.command.ExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Combine the synchronouse job command executor and command executor
 * interfaces.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CombinedExtendedCommandExecutor
	extends CombinedCommandExecutor, ExtendedJobCommandExecutor, ExtendedCommandExecutor
{
	/**
	 * Singleton implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default
		implements CombinedExtendedCommandExecutor, Serializable
	{
		public static final CombinedExtendedCommandExecutor INSTANCE = new Default();
		public static CombinedExtendedCommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public void execute(Command command) {
			command.execute();
		}
		public void waitToExecute(Command command) {
			command.execute();
		}
		public boolean waitToExecute(Command command, long timeout) {
			command.execute();
			return true;
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
		implements CombinedExtendedCommandExecutor, Serializable
	{
		public static final CombinedExtendedCommandExecutor INSTANCE = new Inactive();
		public static CombinedExtendedCommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Inactive() {
			super();
		}
		public void execute(Command command) {
			// do nothing
		}
		public void waitToExecute(Command command) {
			// do nothing
		}
		public boolean waitToExecute(Command command, long timeout) {
			// do nothing
			return true;
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
