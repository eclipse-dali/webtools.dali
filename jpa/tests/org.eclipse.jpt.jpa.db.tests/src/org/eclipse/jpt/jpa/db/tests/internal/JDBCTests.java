/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.tests.internal;

import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.TestCase;

/**
 * These aren't tests. They are just an easy way to dump JDBC metadata to the
 * console.
 */
@SuppressWarnings("nls")
public class JDBCTests extends TestCase {

	public JDBCTests(String name) {
		super(name);
	}

	public void testDerby() throws Exception {
		this.dumpMetaData(DERBY);
	}

	public void testMySQL() throws Exception {
		this.dumpMetaData(MYSQL);
	}

	public void testOracle() throws Exception {
		this.dumpMetaData(ORACLE);
	}

	public void testPostgreSQL() throws Exception {
		this.dumpMetaData(POSTGRESQL);
	}

	public void testSybase() throws Exception {
		this.dumpMetaData(SYBASE);
	}

	protected void dumpMetaData(ConnectionConfig config) throws Exception {
		System.out.println("***** PLATFORM: " + config.platformName + " *****");
		System.out.println();
		Class.forName(config.driverClassName);
		Connection connection = DriverManager.getConnection(this.buildURL(config), config.user, config.password);
		System.out.println("CATALOGS:");
		JDBCTools.dump(connection.getMetaData().getCatalogs());
		System.out.println();
		System.out.println("SCHEMATA:");
		JDBCTools.dump(connection.getMetaData().getSchemas());
		connection.close();
		System.out.println();
	}

	protected String buildURL(ConnectionConfig config) {
		return "jdbc:" + config.databaseURL;
	}

	protected static final ConnectionConfig DERBY =
		new ConnectionConfig(
			"Derby",
			"org.apache.derby.jdbc.EmbeddedDriver",
			"derby:C:/derby/data/test",
			null,
			null
		);

	protected static final ConnectionConfig MYSQL =
		new ConnectionConfig(
			"MySQL",
			"com.mysql.jdbc.Driver",
			"mysql://localhost:3306",
			"root",
			"oracle"
		);

	protected static final ConnectionConfig ORACLE =
		new ConnectionConfig(
			"Oracle",
			"oracle.jdbc.OracleDriver",
			"oracle:thin:@localhost:1521:orcl",
			"scott",
			"tiger"
		);

	protected static final ConnectionConfig POSTGRESQL =
		new ConnectionConfig(
			"PostgreSQL",
			"org.postgresql.Driver",
			"postgresql:postgres",
			"postgres",
			"oracle"
		);

	// the Sybase server must be configured explicitly to "localhost"
	// in the config file [SYBASE]/ini/sql.ini
	protected static final ConnectionConfig SYBASE =
		new ConnectionConfig(
			"Sybase",
			"com.sybase.jdbc3.jdbc.SybDriver",
			"sybase:Tds:localhost:5000",
			"sa",
			"oracle"
		);

	protected static class ConnectionConfig {
		protected final String platformName;
		protected final String driverClassName;
		protected final String databaseURL;
		protected final String user;
		protected final String password;
		protected ConnectionConfig(
				String platformName,
				String driverClassName,
				String databaseURL,
				String user,
				String password
		) {
			super();
			this.platformName = platformName;
			this.driverClassName = driverClassName;
			this.databaseURL = databaseURL;
			this.user = user;
			this.password = password;
		}
	}

}
