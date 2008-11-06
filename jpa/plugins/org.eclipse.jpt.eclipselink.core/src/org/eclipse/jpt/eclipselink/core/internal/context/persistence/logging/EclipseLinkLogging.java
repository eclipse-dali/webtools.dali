/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging;

import java.util.Map;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 *  EclipseLinkLogging
 */
public class EclipseLinkLogging extends EclipseLinkPersistenceUnitProperties
	implements Logging
{
	// ********** EclipseLink properties **********
	private LoggingLevel level;
	private Boolean timestamp;
	private Boolean thread;
	private Boolean session;
	private Boolean exceptions;
	private String logFileLocation;
	private String logger; // storing EclipseLinkStringValue since value can be Logger or custom class

	// ********** constructors **********
	public EclipseLinkLogging(PersistenceUnit parent, ListValueModel<Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		// TOREVIEW - handle incorrect String in persistence.xml
		this.level = 
			this.getEnumValue(ECLIPSELINK_LEVEL, LoggingLevel.values());
		this.timestamp = 
			this.getBooleanValue(ECLIPSELINK_TIMESTAMP);
		this.thread = 
			this.getBooleanValue(ECLIPSELINK_THREAD);
		this.session = 
			this.getBooleanValue(ECLIPSELINK_SESSION);
		this.exceptions = 
			this.getBooleanValue(ECLIPSELINK_EXCEPTIONS);
		this.logFileLocation = 
			this.getStringValue(ECLIPSELINK_LOG_FILE_LOCATION);
		
		Logger standardLogger = this.getEnumValue(ECLIPSELINK_LOGGER, Logger.values());
		if( ! this.getPersistenceUnit().containsProperty(ECLIPSELINK_LOGGER)) {
			this.logger = null;
		}
		else if(standardLogger == null) {
			this.logger = this.getStringValue(ECLIPSELINK_LOGGER); // custom logger
		}
		else {
			this.logger = getEclipseLinkStringValueOf(standardLogger);
		}
	}

	// ********** behavior **********
	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_LEVEL,
			LEVEL_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_TIMESTAMP,
			TIMESTAMP_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_THREAD,
			THREAD_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SESSION,
			SESSION_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_EXCEPTIONS,
			EXCEPTIONS_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_LOG_FILE_LOCATION,
			LOG_FILE_LOCATION_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_LOGGER,
			LOGGER_PROPERTY);
	}

	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		if (aspectName.equals(LEVEL_PROPERTY)) {
			this.levelChanged(event);
		}
		else if (aspectName.equals(TIMESTAMP_PROPERTY)) {
			this.timestampChanged(event);
		}
		else if (aspectName.equals(THREAD_PROPERTY)) {
			this.threadChanged(event);
		}
		else if (aspectName.equals(SESSION_PROPERTY)) {
			this.sessionChanged(event);
		}
		else if (aspectName.equals(EXCEPTIONS_PROPERTY)) {
			this.exceptionsChanged(event);
		}
		else if (aspectName.equals(LOG_FILE_LOCATION_PROPERTY)) {
			this.logFileLocationChanged(event);
		}
		else if (aspectName.equals(LOGGER_PROPERTY)) {
			this.loggerChanged(event);
		}
		else {
			throw new IllegalArgumentException("Illegal event received - property not applicable: " + aspectName);
		}
		return;
	}

	// ********** LoggingLevel **********
	
	public LoggingLevel getLevel() {
		return this.level;
	}
	
	public void setLevel(LoggingLevel newLevel) {
		LoggingLevel old = this.level;
		this.level = newLevel;
		this.putProperty(LEVEL_PROPERTY, newLevel);
		this.firePropertyChanged(LEVEL_PROPERTY, old, newLevel);
	}

	private void levelChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		LoggingLevel newValue = getEnumValueOf(stringValue, LoggingLevel.values());
		LoggingLevel old = this.level;
		this.level = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}
	
	public LoggingLevel getDefaultLevel() {
		return DEFAULT_LEVEL;
	}

	// ********** Timestamp **********
	public Boolean getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Boolean newTimestamp) {
		Boolean old = this.timestamp;
		this.timestamp = newTimestamp;
		this.putProperty(TIMESTAMP_PROPERTY, newTimestamp);
		this.firePropertyChanged(TIMESTAMP_PROPERTY, old, newTimestamp);
	}

	private void timestampChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.timestamp;
		this.timestamp = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultTimestamp() {
		return DEFAULT_TIMESTAMP;
	}

	// ********** Thread **********
	public Boolean getThread() {
		return this.thread;
	}

	public void setThread(Boolean newThread) {
		Boolean old = this.thread;
		this.thread = newThread;
		this.putProperty(THREAD_PROPERTY, newThread);
		this.firePropertyChanged(THREAD_PROPERTY, old, newThread);
	}

	private void threadChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.thread;
		this.thread = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultThread() {
		return DEFAULT_THREAD;
	}

	// ********** Session **********
	public Boolean getSession() {
		return this.session;
	}

	public void setSession(Boolean newSession) {
		Boolean old = this.session;
		this.session = newSession;
		this.putProperty(SESSION_PROPERTY, newSession);
		this.firePropertyChanged(SESSION_PROPERTY, old, newSession);
	}

	private void sessionChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.session;
		this.session = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultSession() {
		return DEFAULT_SESSION;
	}

	// ********** Exceptions **********
	public Boolean getExceptions() {
		return this.exceptions;
	}

	public void setExceptions(Boolean newExceptions) {
		Boolean old = this.exceptions;
		this.exceptions = newExceptions;
		this.putProperty(EXCEPTIONS_PROPERTY, newExceptions);
		this.firePropertyChanged(EXCEPTIONS_PROPERTY, old, newExceptions);
	}

	private void exceptionsChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.exceptions;
		this.exceptions = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultExceptions() {
		return DEFAULT_EXCEPTIONS;
	}

	// ********** logFileLocation **********
	public String getLogFileLocation() {
		return this.logFileLocation;
	}

	public void setLogFileLocation(String newLogFileLocation) {
		String old = this.logFileLocation;
		this.logFileLocation = newLogFileLocation;
		this.putProperty(LOG_FILE_LOCATION_PROPERTY, newLogFileLocation);
		this.firePropertyChanged(LOG_FILE_LOCATION_PROPERTY, old, newLogFileLocation);
	}

	private void logFileLocationChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.logFileLocation;
		this.logFileLocation = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultLogFileLocation() {
		return DEFAULT_LOG_FILE_LOCATION;
	}

	// ********** Logger **********
	/**
	 * Returns Logger or custom logger class.
	 * 
	 * @return EclipseLink string value for Logger enum or custom logger class
	 */
	public String getLogger() {
		return this.logger;
	}

	/**
	 * Sets EclipseLink logger.
	 * 
	 * @param newLogger - Logger
	 */
	public void setLogger(Logger newLogger) {
		if( newLogger == null) {
			this.setLogger_((String) null);
			return;
		}
		this.setLogger_(getEclipseLinkStringValueOf(newLogger));
	}

	/**
	 * Sets EclipseLink logger or custom logger.
	 * 
	 * @param newLogger -
	 *            Can be a EclipseLink logger literal or
	 *            a fully qualified class name of a custom logger.
	 */
	public void setLogger(String newLogger) {
		if( newLogger == null) {
			this.setLogger_((String) null);
			return;
		}
		Logger logger = Logger.getLoggerFor(newLogger);
		if(logger == null) {	// custom Logger class
			this.setLogger_(newLogger);
		}
		else {
			this.setLogger(logger);
		}
	}
	
	private void setLogger_(String newLogger) {
		String old = this.logger;
		this.logger = newLogger;
		this.putProperty(LOGGER_PROPERTY, newLogger);
		this.firePropertyChanged(LOGGER_PROPERTY, old, newLogger);
	}

	private void loggerChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.logger;
		this.logger = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultLogger() {
		return DEFAULT_LOGGER;
	}

}