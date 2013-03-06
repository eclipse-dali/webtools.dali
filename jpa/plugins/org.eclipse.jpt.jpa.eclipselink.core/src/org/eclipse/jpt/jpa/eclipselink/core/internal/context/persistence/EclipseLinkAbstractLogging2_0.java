/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;

public abstract class EclipseLinkAbstractLogging2_0
	extends EclipseLinkLoggingImpl
	implements EclipseLinkLogging2_0
{
	// ********** EclipseLink properties **********

	/** 
	 * Store Category levels in a map
	 * key/value pairs, where: 
	 * 		key = Category property id
	 * 		value = value
	 */
	private Map<String, LoggingLevel> categoryValues;

	private Boolean connection;
	
	private LoggingLevel categoriesDefaultValue;
	
	// ********** constructors **********
	public EclipseLinkAbstractLogging2_0(PersistenceUnit2_0 parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		super.initializeProperties();

		this.connection = 
			this.getBooleanValue(ECLIPSELINK_CONNECTION);
		
		this.categoriesDefaultValue = 
			this.getEnumValue(ECLIPSELINK_LEVEL, LoggingLevel.values());
		this.initializeCategory(SQL_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(TRANSACTION_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(EVENT_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(CONNECTION_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(QUERY_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(CACHE_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(PROPAGATION_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(SEQUENCING_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(EJB_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(DMS_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(JPA_METAMODEL_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(WEAVER_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(PROPERTIES_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(SERVER_CATEGORY_LOGGING_PROPERTY);
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		this.categoryValues = new HashMap<String, LoggingLevel>();
	}

	/**
	 * Convenience method to set a logging level category value from the value in the persistence unit.
	 * 
	 * key = Category property id; value = value (LoggingLevel)
	 * @param categoryId - Category property id
	 */
	protected void initializeCategory(String categoryId) {
		String puKey = this.persistenceUnitKeyOf(categoryId);
		LoggingLevel puValue = this.getEnumValue(puKey, LoggingLevel.values());

		this.categoryValues.put(categoryId, puValue);
	}

	// ********** behavior **********
	
	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);

		if (propertyName.equals(ECLIPSELINK_CONNECTION)) {
			this.connectionChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SQL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_SQL_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_TRANSACTION_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_TRANSACTION_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_EVENT_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_EVENT_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_CONNECTION_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_CONNECTION_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_QUERY_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_QUERY_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_CACHE_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_PROPAGATION_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_PROPAGATION_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SEQUENCING_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_SEQUENCING_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_EJB_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_EJB_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_DMS_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_DMS_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_EJB_OR_METADATA_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_EJB_OR_METADATA_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_JPA_METAMODEL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_JPA_METAMODEL_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVER_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_WEAVER_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_PROPERTIES_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_PROPERTIES_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SERVER_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_SERVER_CATEGORY_LOGGING_LEVEL, newValue);
		}
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);

		if (propertyName.equals(ECLIPSELINK_CONNECTION)) {
			this.connectionChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_SQL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_SQL_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_TRANSACTION_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_TRANSACTION_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_EVENT_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_EVENT_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_CONNECTION_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_CONNECTION_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_QUERY_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_QUERY_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_CACHE_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_PROPAGATION_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_PROPAGATION_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_SEQUENCING_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_SEQUENCING_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_EJB_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_EJB_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_DMS_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_DMS_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_EJB_OR_METADATA_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_EJB_OR_METADATA_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_JPA_METAMODEL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_JPA_METAMODEL_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVER_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_WEAVER_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_PROPERTIES_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_PROPERTIES_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_SERVER_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_SERVER_CATEGORY_LOGGING_LEVEL, null);
		}
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		super.addPropertyNames(propertyNames);

		propertyNames.put(
			ECLIPSELINK_CONNECTION,
			CONNECTION_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SQL_CATEGORY_LOGGING_LEVEL,
			SQL_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_TRANSACTION_CATEGORY_LOGGING_LEVEL,
			TRANSACTION_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_EVENT_CATEGORY_LOGGING_LEVEL,
			EVENT_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_CONNECTION_CATEGORY_LOGGING_LEVEL,
			CONNECTION_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_QUERY_CATEGORY_LOGGING_LEVEL,
			QUERY_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_CACHE_CATEGORY_LOGGING_LEVEL,
			CACHE_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_PROPAGATION_CATEGORY_LOGGING_LEVEL,
			PROPAGATION_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SEQUENCING_CATEGORY_LOGGING_LEVEL,
			SEQUENCING_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_EJB_CATEGORY_LOGGING_LEVEL,
			EJB_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_DMS_CATEGORY_LOGGING_LEVEL,
			DMS_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_EJB_OR_METADATA_CATEGORY_LOGGING_LEVEL,
			EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_JPA_METAMODEL_CATEGORY_LOGGING_LEVEL,
			JPA_METAMODEL_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WEAVER_CATEGORY_LOGGING_LEVEL,
			WEAVER_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_PROPERTIES_CATEGORY_LOGGING_LEVEL,
			PROPERTIES_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SERVER_CATEGORY_LOGGING_LEVEL,
			SERVER_CATEGORY_LOGGING_PROPERTY);
	}

	// ********** Connection **********
	public Boolean getConnection() {
		return this.connection;
	}

	public void setConnection(Boolean newConnection) {
		Boolean old = this.connection;
		this.connection = newConnection;
		this.putProperty(CONNECTION_PROPERTY, newConnection);
		this.firePropertyChanged(CONNECTION_PROPERTY, old, newConnection);
	}

	private void connectionChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.connection;
		this.connection = newValue;
		this.firePropertyChanged(CONNECTION_PROPERTY, old, newValue);
	}

	public Boolean getDefaultConnection() {
		return DEFAULT_CONNECTION;
	}
	
	// ********** Category Levels **********
	
	public LoggingLevel getLevel(String category) {

		return this.categoryValues.get(category);
	}

	public void setLevel(String category, LoggingLevel newLevel) {
		LoggingLevel old = this.getLevel(category);
		this.categoryValues.put(category, newLevel);
		this.putProperty(category, newLevel);
		this.firePropertyChanged(category, old, newLevel);
	}

	protected void categoryLoggingChanged_(String eclipselinkKey, String stringValue) {
		String category = this.propertyIdOf(eclipselinkKey);
		this.categoryLoggingChanged(category, stringValue);
	}

	private void categoryLoggingChanged(String category, String stringValue) {
		LoggingLevel enumValue = getEnumValueOf(stringValue, LoggingLevel.values());
		LoggingLevel old = this.getLevel(category);
		this.categoryValues.put(category, enumValue);
		this.firePropertyChanged(category, old, enumValue);
	}

	public LoggingLevel getCategoriesDefaultLevel() {
		return (this.categoriesDefaultValue == null) ? super.getDefaultLevel() : this.categoriesDefaultValue;
	}

	@Override
	public void setLevel(LoggingLevel level) {
		super.setLevel(level);
		this.setDefaultLevel(level);
	}

	public void setDefaultLevel(LoggingLevel level) {
		LoggingLevel old = this.categoriesDefaultValue;
		this.categoriesDefaultValue = level;
		this.firePropertyChanged(CATEGORIES_DEFAULT_LOGGING_PROPERTY, old, level);
	}
}
