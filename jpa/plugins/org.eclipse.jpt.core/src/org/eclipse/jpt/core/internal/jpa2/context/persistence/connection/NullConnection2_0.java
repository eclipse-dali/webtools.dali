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
public class NullConnection2_0 extends AbstractPersistenceUnitProperties
	implements JpaConnection2_0
{
	

	// ********** constructors **********
	public NullConnection2_0(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		//do nothing
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		//do nothing
	}

	public void propertyRemoved(String propertyName) {
		//do nothing
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = PU property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		//do nothing
	}

	// ********** Driver **********
	public String getDriver() {
		return null;
	}

	public void setDriver(String newDriver) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultDriver() {
		return DEFAULT_JDBC_DRIVER;
	}

	// ********** URL **********
	public String getUrl() {
		return null;
	}

	public void setUrl(String newUrl) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultUrl() {
		return DEFAULT_JDBC_URL;
	}

	// ********** User **********
	public String getUser() {
		return null;
	}

	public void setUser(String newUser) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultUser() {
		return DEFAULT_JDBC_USER;
	}

	// ********** Password **********
	public String getPassword() {
		return null;
	}

	public void setPassword(String newPassword) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultPassword() {
		return DEFAULT_JDBC_PASSWORD;
	}
}
