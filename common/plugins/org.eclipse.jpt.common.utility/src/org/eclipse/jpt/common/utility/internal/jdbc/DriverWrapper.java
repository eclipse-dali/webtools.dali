/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This implementation of the {@link Driver JDBC interface} delegates all calls
 * to a "real" JDBC driver. This wrapper simplifies the dynamic loading of a
 * JDBC driver that is not on the "Java" classpath (the classpath set at
 * the time the JVM starts up). This is because, if you use a URL class loader
 * to load a driver directly, the {@link java.sql.DriverManager} will not allow your code
 * access to the driver. For security reasons, the driver must be loaded
 * by the same class loader chain that loaded the code that calls
 * {@link java.sql.DriverManager#getConnection(String)}.
 */
public class DriverWrapper
	implements Driver
{
	/** the "real" JDBC driver */
	private final Driver driver;


	/**
	 * Wrap the driver for the specified driver class and class loader
	 * that can load the driver.
	 */
	@SuppressWarnings("unchecked")
	public DriverWrapper(String driverClassName, ClassLoader classLoader)
		throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		this((Class<Driver>) Class.forName(driverClassName, true, classLoader));
	}

	/**
	 * Wrap the driver for the specified driver class.
	 */
	public DriverWrapper(Class<Driver> driverClass)
		throws InstantiationException, IllegalAccessException
	{
		this(driverClass.newInstance());
	}

	/**
	 * Wrap the specified driver.
	 */
	public DriverWrapper(Driver driver) {
		super();
		if (driver == null) {
			throw new NullPointerException();
		}
		this.driver = driver;
	}


	// ********** Driver implementation **********

	public Connection connect(String url, Properties info) throws SQLException {
		return this.driver.connect(url, info);
	}

	public boolean acceptsURL(String url) throws SQLException {
		return this.driver.acceptsURL(url);
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return this.driver.getPropertyInfo(url, info);
	}

	public int getMajorVersion() {
		return this.driver.getMajorVersion();
	}

	public int getMinorVersion() {
		return this.driver.getMinorVersion();
	}

	public boolean jdbcCompliant() {
		return this.driver.jdbcCompliant();
	}

	// JDBC 4.1
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return this.driver.getParentLogger();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.driver);
	}
}
