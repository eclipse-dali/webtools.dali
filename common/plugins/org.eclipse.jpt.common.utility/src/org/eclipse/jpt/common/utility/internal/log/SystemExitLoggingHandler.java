/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.RunnableSystemExit;

/**
 * This logging handler will shutdown the JVM if it receives
 * a sufficiently severe log record.
 */
public class SystemExitLoggingHandler
	extends Handler
{
	Runnable runnableSystemExit;


	/**
	 * Construct a logging handler that will shut down the JVM if it
	 * receives a sufficiently severe log record. It will wait the
	 * specified amount of time and exit with the specified status.
	 */
	public SystemExitLoggingHandler(int wait, int exitStatus) {
		super();
		this.runnableSystemExit = new RunnableSystemExit(wait, exitStatus);
	}

	@Override
	public void close() throws SecurityException {
		// do nothing
	}

	@Override
	public void flush() {
		// do nothing
	}

	@Override
	public void publish(LogRecord record) {
		if (this.isLoggable(record)) {
			new Thread(this.runnableSystemExit, "System exit").start(); //$NON-NLS-1$
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
