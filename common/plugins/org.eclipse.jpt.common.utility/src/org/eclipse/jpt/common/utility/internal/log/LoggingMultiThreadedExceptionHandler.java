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

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.eclipse.jpt.common.utility.MultiThreadedExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This exception handler logs any exceptions to a JDK logger.
 */
public class LoggingMultiThreadedExceptionHandler
	implements MultiThreadedExceptionHandler
{
	private final Logger logger;
	private final Level level;
	private final String message;


	// ********** constructors **********

	/**
	 * Construct a listener that logs exceptions to the root logger
	 * at the SEVERE level with the generic message "Unexpected Exception".
	 */
	public LoggingMultiThreadedExceptionHandler() {
		this(Logger.getLogger(null));
	}

	/**
	 * Construct a handler that logs exceptions to the specified logger
	 * at the SEVERE level with the generic message "Unexpected Exception".
	 */
	public LoggingMultiThreadedExceptionHandler(Logger logger) {
		this(logger, Level.SEVERE);
	}

	/**
	 * Construct a handler that logs exceptions to the specified logger
	 * at the specified level with the generic message "Unexpected Exception".
	 */
	public LoggingMultiThreadedExceptionHandler(Logger logger, Level level) {
		this(logger, level, "Unexpected Exception"); //$NON-NLS-1$
	}

	/**
	 * Construct a handler that logs exceptions to the specified logger
	 * at the specified level with the specified message.
	 */
	public LoggingMultiThreadedExceptionHandler(Logger logger, Level level, String message) {
		super();
		this.logger = logger;
		this.level = level;
		this.message = message;
	}


	public void handleException(Throwable exception) {
		this.handleException(null, exception);
	}

	/**
	 * We need to do all this because {@link Logger#log(LogRecord)}
	 * does not pass through {@link Logger#doLog(LogRecord)}
	 * like all the other <code>Logger#log(...)</code> methods.
	 */
	public void handleException(Thread thread, Throwable exception) {
		LogRecord logRecord = new LogRecord(this.level, this.message);
		logRecord.setParameters(new Object[] { (thread == null) ? "null" : thread.getName() }); //$NON-NLS-1$
		logRecord.setThrown(exception);
		logRecord.setLoggerName(this.logger.getName());
		logRecord.setResourceBundle(this.logger.getResourceBundle());
		this.logger.log(logRecord);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
