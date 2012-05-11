/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence.connection;

import java.util.Map;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnitProperties;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;

/**
 *  GenericConnection2_0
 */
public class GenericConnection2_0 extends AbstractPersistenceUnitProperties
	implements JpaConnection2_0
{
	// ********** GenericConnection properties **********
	private String driver;
	private String url;
	private String user;
	private String password;
	

	// ********** constructors **********
	public GenericConnection2_0(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		this.driver = 
			this.getStringValue(PERSISTENCE_JDBC_DRIVER);
		this.url = 
			this.getStringValue(PERSISTENCE_JDBC_URL);
		this.user = 
			this.getStringValue(PERSISTENCE_JDBC_USER);
		this.password = 
			this.getStringValue(PERSISTENCE_JDBC_PASSWORD);
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(PERSISTENCE_JDBC_DRIVER)) {
			this.driverChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_JDBC_URL)) {
			this.urlChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_JDBC_USER)) {
			this.userChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_JDBC_PASSWORD)) {
			this.passwordChanged(newValue);
		}
	}

	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(PERSISTENCE_JDBC_DRIVER)) {
			this.driverChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_JDBC_URL)) {
			this.urlChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_JDBC_USER)) {
			this.userChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_JDBC_PASSWORD)) {
			this.passwordChanged(null);
		}
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = PU property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			PERSISTENCE_JDBC_DRIVER,
			DRIVER_PROPERTY);
		propertyNames.put(
			PERSISTENCE_JDBC_URL,
			URL_PROPERTY);
		propertyNames.put(
			PERSISTENCE_JDBC_USER,
			USER_PROPERTY);
		propertyNames.put(
			PERSISTENCE_JDBC_PASSWORD,
			PASSWORD_PROPERTY);
	}

	// ********** Driver **********
	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String newDriver) {
		String old = this.driver;
		this.driver = newDriver;
		this.putProperty(DRIVER_PROPERTY, newDriver);
		this.firePropertyChanged(DRIVER_PROPERTY, old, newDriver);
	}

	private void driverChanged(String newValue) {
		String old = this.driver;
		this.driver = newValue;
		this.firePropertyChanged(DRIVER_PROPERTY, old, newValue);
	}

	public String getDefaultDriver() {
		return DEFAULT_JDBC_DRIVER;
	}

	// ********** URL **********
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String newUrl) {
		String old = this.url;
		this.url = newUrl;
		this.putProperty(URL_PROPERTY, newUrl);
		this.firePropertyChanged(URL_PROPERTY, old, newUrl);
	}

	private void urlChanged(String newValue) {
		String old = this.url;
		this.url = newValue;
		this.firePropertyChanged(URL_PROPERTY, old, newValue);
	}

	public String getDefaultUrl() {
		return DEFAULT_JDBC_URL;
	}

	// ********** User **********
	public String getUser() {
		return this.user;
	}

	public void setUser(String newUser) {
		String old = this.user;
		this.user = newUser;
		this.putProperty(USER_PROPERTY, newUser);
		this.firePropertyChanged(USER_PROPERTY, old, newUser);
	}

	private void userChanged(String newValue) {
		String old = this.user;
		this.user = newValue;
		this.firePropertyChanged(USER_PROPERTY, old, newValue);
	}

	public String getDefaultUser() {
		return DEFAULT_JDBC_USER;
	}

	// ********** Password **********
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String newPassword) {
		String old = this.password;
		this.password = newPassword;
		this.putProperty(PASSWORD_PROPERTY, newPassword);
		this.firePropertyChanged(PASSWORD_PROPERTY, old, newPassword);
	}

	private void passwordChanged(String newValue) {
		String old = this.password;
		this.password = newValue;
		this.firePropertyChanged(PASSWORD_PROPERTY, old, newValue);
	}

	public String getDefaultPassword() {
		return DEFAULT_JDBC_PASSWORD;
	}

	
}
