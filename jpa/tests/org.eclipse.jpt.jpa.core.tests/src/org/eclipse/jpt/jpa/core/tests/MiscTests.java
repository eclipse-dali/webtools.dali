/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;

@SuppressWarnings("nls")
public class MiscTests extends TestCase {

	public MiscTests(String name) {
		super(name);
	}

	/*
	 * 
	 */
	public void testJobsAndLocks() throws Exception {
		ILock lock = Job.getJobManager().newLock();
		Job testJob = new TestJob(lock);
		testJob.schedule();
	}

	class TestJob extends Job {
		private final ILock lock;
		TestJob(ILock lock) {
			super("test job");
			this.lock = lock;
		}
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			this.run();
			return Status.OK_STATUS;
		}
		private void run() {
			try {
				this.lock.acquire();
				MiscTests.sleep(100);
			} finally {
				this.lock.release();
			}
		}
	}

	static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

}
