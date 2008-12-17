/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.SynchronizedBoolean;

/**
 * This updater will "update" the JPA project immediately and not return until
 * the "update" and all resulting "updates" are complete. This implementation
 * should be used sparingly and for as short a time as possible, as it increases
 * the probability of deadlocks. A deadlock can occur when a JPA project is
 * updated from multiple threads and various resources are locked in varying
 * orders.
 */
public class SynchronousJpaProjectUpdater implements JpaProject.Updater {
	protected final JpaProject jpaProject;
	protected final SynchronizedFlags flags;

	protected static final IProgressMonitor NULL_PROGRESS_MONITOR = new NullProgressMonitor();

	public SynchronousJpaProjectUpdater(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.flags = new SynchronizedFlags();
	}

	/**
	 * Initialize the flags and execute an "update".
	 */
	public void start() {
		this.flags.start();
		this.update();
	}

	// recursion: we come back here when IJpaProject#update() is called during the "update"
	public void update() {
		if (this.flags.updateCanStart()) {
			do {
				this.jpaProject.update(NULL_PROGRESS_MONITOR);
			} while (this.flags.updateMustExecuteAgain());
		}
	}

	public void stop() {
		this.flags.stop();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.jpaProject);
	}


	/**
	 * synchronize access to the pair of 'updating' and 'again' flags
	 */
	protected static class SynchronizedFlags {
		protected SynchronizedBoolean updating = new SynchronizedBoolean(false, this);
		protected boolean again = false;
		protected boolean stop = true;

		/**
		 * Simply clear the 'stop' flag.
		 */
		protected synchronized void start() {
			if ( ! this.stop) {
				throw new IllegalStateException("The Updater was not stopped."); //$NON-NLS-1$
			}
			this.stop = false;
		}

		/**
		 * A client has requested an "update";
		 * return whether the updater can start an "update".
		 * Side-effects:
		 *   - If we are supposed to stop, both the 'updating' and 'again' flags are cleared in #stop().
		 *   - If we are currently "updating", set the 'again' flag.
		 *   - If we are not currently "updating", set the 'updating' flag and clear the 'again' flag.
		 */
		protected synchronized boolean updateCanStart() {
			if (this.stop) {
				return false;
			}
			if (this.updating.isTrue()) {
				this.again = true;
				return false;
			}
			this.updating.setTrue();
			this.again = false;
			return true;
		}

		/**
		 * The "update" has finished;
		 * return whether the updater must execute another "update".
		 * Side-effects:
		 *   - If we are supposed to stop,
		 *       the 'again' flag was cleared in #stop();
		 *       clear the 'updating' flag so #stop() can complete.
		 *   - If we have to "update" again, clear the 'again' flag and leave the 'updating' flag set.
		 *   - If we are finished (i.e. no recursive "update" requests occurred), clear the 'updating' flag.
		 */
		protected synchronized boolean updateMustExecuteAgain() {
			if (this.stop) {
				this.updating.setFalse();
				return false;
			}
			if (this.again) {
				this.again = false;
				return true;
			}
			this.updating.setFalse();
			return false;
		}

		/**
		 * Restore our start-up state and wait for any current update to complete.
		 */
		protected synchronized void stop() {
			this.stop = true;
			this.again = false;
			try {
				this.updating.waitUntilFalse();
			} catch (InterruptedException ex) {
				// the "update" thread was interrupted while waiting - ignore
			}
		}

	}

}
