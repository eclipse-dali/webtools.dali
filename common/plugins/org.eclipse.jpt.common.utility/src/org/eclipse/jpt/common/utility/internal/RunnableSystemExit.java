/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * This runnable will sleep for a specified amount of time
 * and then kill the JVM with a call to {@link System#exit(int)} with
 * a client-specified exit status. If the thread is interrupted,
 * we will stop the thread without killing the JVM.
 */
public final class RunnableSystemExit
	implements Runnable
{
	/** The time to wait before killing the JVM, in milliseconds. */
	private final int wait;
	/** The exit status code that will passed to the O/S on exit. */
	private final int exitStatus;

	/**
	 * Construct a killer that will wait for the specified time and
	 * exit with the specified exit status.
	 */
	public RunnableSystemExit(int wait, int exitStatus) {
		super();
		this.wait = wait;
		this.exitStatus = exitStatus;
	}

	public void run() {
		long stop = System.currentTimeMillis() + this.wait;
		long remaining = this.wait;
		while (remaining > 0L) {
			try {
				Thread.sleep(remaining);
			} catch (InterruptedException ex) {
				return;		// commuted death sentence
			}
			remaining = stop - System.currentTimeMillis();
		}
		System.exit(this.exitStatus);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, "wait=" + this.wait + "; exit status=" + this.exitStatus); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
