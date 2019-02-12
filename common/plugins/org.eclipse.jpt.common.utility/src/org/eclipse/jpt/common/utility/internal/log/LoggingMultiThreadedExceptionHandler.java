/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.eclipse.jpt.common.utility.internal.exception.MultiThreadedExceptionHandlerAdapter;

/**
 * This exception handler logs any exceptions to a JDK logger.
 */
public class LoggingMultiThreadedExceptionHandler
	extends MultiThreadedExceptionHandlerAdapter
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
		if ((logger == null) || (level == null) || (message == null)) {
			throw new NullPointerException();
		}
		this.logger = logger;
		this.level = level;
		this.message = message;
	}


	@Override
	public void handleException(Throwable exception) {
		this.handleException(null, exception);
	}

	/**
	 * We need to do all this because {@link Logger#log(LogRecord)}
	 * does not pass through {@link Logger#doLog(LogRecord)}
	 * like all the other <code>Logger#log(...)</code> methods.
	 */
	@Override
	public void handleException(Thread thread, Throwable exception) {
		LogRecord logRecord = new LogRecord(this.level, this.message);
		logRecord.setParameters(new Object[] { (thread == null) ? "null" : thread.getName() }); //$NON-NLS-1$
		logRecord.setThrown(exception);
		logRecord.setLoggerName(this.logger.getName());
		logRecord.setResourceBundle(this.logger.getResourceBundle());
		this.logger.log(logRecord);
	}
}
