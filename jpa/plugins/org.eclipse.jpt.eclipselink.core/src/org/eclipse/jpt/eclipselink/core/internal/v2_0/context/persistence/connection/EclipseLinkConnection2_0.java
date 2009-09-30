/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.connection;

import java.util.Map;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.EclipseLinkConnection;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.connection.Connection2_0;


/**
 *  EclipseLinkConnection2_0
 */
public class EclipseLinkConnection2_0 extends EclipseLinkConnection
	implements Connection2_0
{

	// ********** constructors **********
	public EclipseLinkConnection2_0(PersistenceUnit parent) {
		super(parent);
	}
	
	// ********** initialization **********
	@Override
	protected void initializeDatabaseConnectionProperties() {
		this.driver = 
			this.getStringValue(ECLIPSELINK2_0_DRIVER);
		this.url = 
			this.getStringValue(ECLIPSELINK2_0_URL);
		this.user = 
			this.getStringValue(ECLIPSELINK2_0_USER);
		this.password = 
			this.getStringValue(ECLIPSELINK2_0_PASSWORD);
	}
	
	@Override
	protected void postInitializeProperties() {
		super.postInitializeProperties();

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

		if (propertyName.equals(ECLIPSELINK2_0_DRIVER)) {
			this.driverChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK2_0_URL)) {
			this.urlChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK2_0_USER)) {
			this.userChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK2_0_PASSWORD)) {
			this.passwordChanged(newValue);
		}
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		
		if (propertyName.equals(ECLIPSELINK2_0_DRIVER)) {
			this.driverChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK2_0_URL)) {
			this.urlChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK2_0_USER)) {
			this.userChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK2_0_PASSWORD)) {
			this.passwordChanged(null);
		}
	}

	@Override
	protected void addDatabaseConnectionPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK2_0_DRIVER,
			Connection.DRIVER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK2_0_URL,
			Connection.URL_PROPERTY);
		propertyNames.put(
			ECLIPSELINK2_0_USER,
			Connection.USER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK2_0_PASSWORD,
			Connection.PASSWORD_PROPERTY);
	}
	
	/**
	 * Migrate properties names to EclipseLink 2.0 names.
	 */
	private void migrateProperties() {
		this.migrateStringProperty(ECLIPSELINK_DRIVER, ECLIPSELINK2_0_DRIVER, this.driver);
		this.migrateStringProperty(ECLIPSELINK_URL, ECLIPSELINK2_0_URL, this.url);
		this.migrateStringProperty(ECLIPSELINK_USER, ECLIPSELINK2_0_USER, this.user);
		this.migrateStringProperty(ECLIPSELINK_PASSWORD, ECLIPSELINK2_0_PASSWORD, this.password);
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