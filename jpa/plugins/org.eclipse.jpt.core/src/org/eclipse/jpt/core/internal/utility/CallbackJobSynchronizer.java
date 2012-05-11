/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.synchronizers.CallbackSynchronizer;

/**
 * Extend the job synchronizer to notify listeners
 * when a synchronization "cycle" is complete; i.e. the synchronization has,
 * for the moment, handled every "synchronize" request and quiesced.
 * This notification is <em>not</em> guaranteed to occur with <em>every</em>
 * synchronization "cycle";
 * since other, unrelated, synchronizations can be triggered concurrently.
 */
public class CallbackJobSynchronizer
	extends JobSynchronizer
	implements CallbackSynchronizer
{
	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);


	// ********** construction **********

	/**
	 * Construct a callback job synchronizer that uses the specified job command to
	 * perform the synchronization. Assign the generated Eclipse job the
	 * specified name.
	 */
	public CallbackJobSynchronizer(String jobName, JobCommand command) {
		super(jobName, command);
	}

	/**
	 * Construct a callback job synchronizer that uses the specified job command to
	 * perform the synchronization. Assign the generated Eclipse job the
	 * specified name and scheduling rule.
	 */
	public CallbackJobSynchronizer(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
		super(jobName, command, schedulingRule);
	}

	/**
	 * Build a job that will let us know when the synchronization has
	 * quiesced.
	 */
	@Override
	SynchronizationJob buildJob(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
		return new CallbackSynchronizationJob(jobName, command, schedulingRule);
	}


	// ********** CallbackSynchronizer implementation **********

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}

	/**
	 * Notify our listeners.
	 */
	void synchronizationQuiesced() {
		for (Listener listener : this.listenerList.getListeners()) {
			listener.synchronizationQuiesced(this);
		}
	}


	// ********** synchronization job **********

	/**
	 * Extend {@link JobSynchronizer.SynchronizationJob}
	 * to notify the synchronizer when the synchronization has quiesced
	 * (i.e. the command has finished executing and there are no further
	 * requests for synchronization).
	 * Because synchronization occurs during the job's execution,
	 * no other thread will be able to
	 * initiate another synchronization until the synchronizer's listeners have been
	 * notified. Note also, the synchronizer's listeners can, themselves,
	 * trigger another synchronization (by directly or indirectly calling
	 * {@link org.eclipse.jpt.utility.internal.synchronizers.Synchronizer#synchronize());
	 * but this synchronization will not occur until <em>after</em> all the
	 * listeners have been notified.
	 */
	class CallbackSynchronizationJob
		extends SynchronizationJob
	{
		/**
		 * When this flag is set to true, the job has been scheduled to run.
		 * We need this because {@link org.eclipse.core.runtime.jobs.Job Job}
		 * has no public API for discovering whether a job is "scheduled".
		 */
		// use 'volatile' because synchronization isn't really required
		private volatile boolean scheduled;


		CallbackSynchronizationJob(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
			super(jobName, command, schedulingRule);
			this.scheduled = false;
		}

		/**
		 * If we are allowing the job to be scheduled (i.e. {@link #start()}
		 * was called), set the "scheduled" flag.
		 */
		@Override
		void synchronize() {
			if (this.shouldSchedule) {
				this.scheduled = true;
			}
			super.synchronize();
		}

		/**
		 * Clear the "scheduled" flag; perform the synchronization; and,
		 * if the "scheduled" flag is still clear (i.e. there have been no
		 * further calls to {@link #synchronize()}), notify our listeners.
		 */
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			this.scheduled = false;
			IStatus status = super.run(monitor);
			// hmmm - we will notify listeners even when we our job is "stopped";
			// that seems ok...  ~bjv
			if ( ! this.scheduled) {
				CallbackJobSynchronizer.this.synchronizationQuiesced();
			}
			return status;
		}

		@Override
		void stop() {
			this.scheduled = false;
			super.stop();
		}

	}

}
