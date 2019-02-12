/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.Map;

import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection2_0;


/**
 *  EclipseLink 2.0 connection
 */
public class EclipseLinkConnectionImpl2_0
	extends EclipseLinkConnectionImpl
	implements EclipseLinkConnection2_0
{

	// ********** constructors **********
	public EclipseLinkConnectionImpl2_0(PersistenceUnit2_0 parent) {
		super(parent);
	}
	
	// ********** initialization **********
	@Override
	protected void initializeDatabaseConnectionProperties() {
		this.driver = 
			this.getStringValue(Connection2_0.PERSISTENCE_JDBC_DRIVER);
		this.url = 
			this.getStringValue(Connection2_0.PERSISTENCE_JDBC_URL);
		this.user = 
			this.getStringValue(Connection2_0.PERSISTENCE_JDBC_USER);
		this.password = 
			this.getStringValue(Connection2_0.PERSISTENCE_JDBC_PASSWORD);
	}
	
	@Override
	protected void postInitialize() {
		super.postInitialize();

		// Initialize Properties from legacy properties names if not initialized.
		if(this.persistenceUnitKeyExists(ECLIPSELINK_DRIVER)) {
			if(this.driver == null) {
				this.driver = this.getStringValue(ECLIPSELINK_DRIVER);
			}
		}
		if(this.persistenceUnitKeyExists(ECLIPSELINK_URL)) {
			if(this.url == null) {
				this.url = this.getStringValue(ECLIPSELINK_URL);
			}
		}
		if(this.persistenceUnitKeyExists(ECLIPSELINK_USER)) {
			if(this.user == null) {
				this.user = this.getStringValue(ECLIPSELINK_USER);
			}
		}
		if(this.persistenceUnitKeyExists(ECLIPSELINK_PASSWORD)) {
			if(this.password == null) {
				this.password = this.getStringValue(ECLIPSELINK_PASSWORD);
			}
		}
	}

	// ********** behavior **********

	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);

		if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_DRIVER)) {
			this.driverChanged(newValue);
		}
		else if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_URL)) {
			this.urlChanged(newValue);
		}
		else if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_USER)) {
			this.userChanged(newValue);
		}
		else if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_PASSWORD)) {
			this.passwordChanged(newValue);
		}
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		
		if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_DRIVER)) {
			this.driverChanged(null);
		}
		else if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_URL)) {
			this.urlChanged(null);
		}
		else if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_USER)) {
			this.userChanged(null);
		}
		else if (propertyName.equals(Connection2_0.PERSISTENCE_JDBC_PASSWORD)) {
			this.passwordChanged(null);
		}
	}

	@Override
	protected void addDatabaseConnectionPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			Connection2_0.PERSISTENCE_JDBC_DRIVER,
			EclipseLinkConnection.DRIVER_PROPERTY);
		propertyNames.put(
			Connection2_0.PERSISTENCE_JDBC_URL,
			EclipseLinkConnection.URL_PROPERTY);
		propertyNames.put(
			Connection2_0.PERSISTENCE_JDBC_USER,
			EclipseLinkConnection.USER_PROPERTY);
		propertyNames.put(
			Connection2_0.PERSISTENCE_JDBC_PASSWORD,
			EclipseLinkConnection.PASSWORD_PROPERTY);
	}
	
	/**
	 * Migrate properties names to EclipseLink 2.0 names.
	 */
	private void migrateProperties() {
		this.migrateStringProperty(ECLIPSELINK_DRIVER, Connection2_0.PERSISTENCE_JDBC_DRIVER, this.driver);
		this.migrateStringProperty(ECLIPSELINK_URL, Connection2_0.PERSISTENCE_JDBC_URL, this.url);
		this.migrateStringProperty(ECLIPSELINK_USER, Connection2_0.PERSISTENCE_JDBC_USER, this.user);
		this.migrateStringProperty(ECLIPSELINK_PASSWORD, Connection2_0.PERSISTENCE_JDBC_PASSWORD, this.password);
	}
	
	private void migrateStringProperty(String oldKey, String newKey, String value) {
		if(this.persistenceUnitKeyExists(oldKey)) {
			this.getPersistenceUnit().removeProperty(oldKey);
			this.getPersistenceUnit().setProperty(newKey, value);
		}
	}

	/**
     * Migrate all properties names before the property is set
	 */
	@Override
	protected void preSetProperty() {
		
		this.migrateProperties();
	}

}
