/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.Map;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogger;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel;
import org.eclipse.text.edits.ReplaceEdit;

/**
 *  EclipseLink logging
 */
public class EclipseLinkLoggingImpl
	extends EclipseLinkPersistenceUnitProperties
	implements EclipseLinkLogging
{
	// ********** EclipseLink properties **********
	private EclipseLinkLoggingLevel level;
	private Boolean timestamp;
	private Boolean thread;
	private Boolean session;
	private Boolean exceptions;
	private String logFileLocation;
	private String logger; // storing EclipseLinkStringValue since value can be Logger or custom class

	// ********** constructors **********
	public EclipseLinkLoggingImpl(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		// TOREVIEW - handle incorrect String in persistence.xml
		this.level = 
			this.getEnumValue(ECLIPSELINK_LEVEL, EclipseLinkLoggingLevel.values());
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
		
		this.logger = this.getLoggerProtertyValue();
	}

	/**
	 * Gets the Logger property from the persistence unit.
	 */
	private String getLoggerProtertyValue() {

		String value = this.getStringValue(ECLIPSELINK_LOGGER);
		if (value == null) {
			return null;	// no property found
		}
		EclipseLinkLogger standardLogger = this.getEnumValue(ECLIPSELINK_LOGGER, EclipseLinkLogger.values());
		return (standardLogger == null) ? value : getPropertyStringValueOf(standardLogger);
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(ECLIPSELINK_LEVEL)) {
			this.levelChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_TIMESTAMP)) {
			this.timestampChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_THREAD)) {
			this.threadChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION)) {
			this.sessionChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_EXCEPTIONS)) {
			this.exceptionsChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_LOG_FILE_LOCATION)) {
			this.logFileLocationChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_LOGGER)) {
			this.loggerChanged(newValue);
		}
	}
		
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(ECLIPSELINK_LEVEL)) {
			this.levelChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_TIMESTAMP)) {
			this.timestampChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_THREAD)) {
			this.threadChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION)) {
			this.sessionChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_EXCEPTIONS)) {
			this.exceptionsChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_LOG_FILE_LOCATION)) {
			this.logFileLocationChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_LOGGER)) {
			this.loggerChanged(null);
		}
	}

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

	// ********** LoggingLevel **********
	
	public EclipseLinkLoggingLevel getLevel() {
		return this.level;
	}
	
	public void setLevel(EclipseLinkLoggingLevel newLevel) {
		EclipseLinkLoggingLevel old = this.level;
		this.level = newLevel;
		this.putProperty(LEVEL_PROPERTY, newLevel);
		this.firePropertyChanged(LEVEL_PROPERTY, old, newLevel);
	}

	private void levelChanged(String stringValue) {
		EclipseLinkLoggingLevel newValue = getEnumValueOf(stringValue, EclipseLinkLoggingLevel.values());
		EclipseLinkLoggingLevel old = this.level;
		this.level = newValue;
		this.firePropertyChanged(LEVEL_PROPERTY, old, newValue);
	}
	
	public EclipseLinkLoggingLevel getDefaultLevel() {
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

	private void timestampChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.timestamp;
		this.timestamp = newValue;
		this.firePropertyChanged(TIMESTAMP_PROPERTY, old, newValue);
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

	private void threadChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.thread;
		this.thread = newValue;
		this.firePropertyChanged(THREAD_PROPERTY, old, newValue);
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

	private void sessionChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.session;
		this.session = newValue;
		this.firePropertyChanged(SESSION_PROPERTY, old, newValue);
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

	private void exceptionsChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.exceptions;
		this.exceptions = newValue;
		this.firePropertyChanged(EXCEPTIONS_PROPERTY, old, newValue);
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
		this.logFileLocation = (StringTools.isNotBlank(newLogFileLocation)) ? 
										newLogFileLocation : 
										this.getDefaultLogFileLocation();
		this.putProperty(LOG_FILE_LOCATION_PROPERTY, this.logFileLocation);
		this.firePropertyChanged(LOG_FILE_LOCATION_PROPERTY, old, this.logFileLocation);
	}

	private void logFileLocationChanged(String newValue) {
		String old = this.logFileLocation;
		this.logFileLocation = newValue;
		this.firePropertyChanged(LOG_FILE_LOCATION_PROPERTY, old, newValue);
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
	public void setLogger(EclipseLinkLogger newLogger) {
		if( newLogger == null) {
			this.setLogger_((String) null);
			return;
		}
		this.setLogger_(getPropertyStringValueOf(newLogger));
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
			this.setLogger_(null);
			return;
		}
		EclipseLinkLogger l = EclipseLinkLogger.getLoggerFor(newLogger);
		if(l == null) {	// custom Logger class
			this.setLogger_(newLogger);
		}
		else {
			this.setLogger(l);
		}
	}
	
	private void setLogger_(String newLogger) {
		String old = this.logger;
		this.logger = newLogger;
		this.putProperty(LOGGER_PROPERTY, newLogger);
		this.firePropertyChanged(LOGGER_PROPERTY, old, newLogger);
	}

	private void loggerChanged(String newValue) {
		String old = this.logger;
		this.logger = newValue;
		this.firePropertyChanged(LOGGER_PROPERTY, old, newValue);
	}

	public String getDefaultLogger() {
		return DEFAULT_LOGGER;
	}


	// ********** refactoring ************

	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.createLoggerRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createLoggerRenameTypeEdits(IType originalType, String newName) {
		//TODO seems like we should have the Property stored in a SessionCustomizer object instead of having to go 
		//find all of the Properties from the persistence unit.
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_LOGGER);
		return (property != null) ?
				property.createRenameTypeEdits(originalType, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.createLoggerMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createLoggerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_LOGGER);
		return (property != null) ?
				property.createMoveTypeEdits(originalType, newPackage) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.createLoggerRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createLoggerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		//find all of the Properties from the persistence unit.
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_LOGGER);
		return (property != null) ?
				property.createRenamePackageEdits(originalPackage, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}
}
