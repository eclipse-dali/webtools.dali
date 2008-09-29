/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal.platforms;


/**
 *  SQL Server 2005 Driver Test
 */
@SuppressWarnings("nls")
public class SQLServerTests extends DTPPlatformTests {

	public SQLServerTests( String name) {
		super( name);
	}

	@Override
	protected String getPlatformPropertiesFileName() {
		return "sqlserver.properties";
	}

	@Override
	protected String getDriverName() {
		return "Microsoft SQL Server 2005 JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionID() {
		return "DriverDefn.Microsoft SQL Server 2005 JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionType() {
		return "org.eclipse.datatools.enablement.msft.sqlserver.2005.driverTemplate";
	}

	@Override
	protected String getDatabaseVendor() {
		return "SQLServer";
	}

	@Override
	protected String getDatabaseVersion() {
		return "2005";
	}

	@Override
	protected String getDriverClass() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	protected String getProfileName() {
		return "SQLServer_2005";
	}

	@Override
	protected String getProfileDescription() {
		return "Microsoft SQL Server 2005 JDBC Profile [Test]";
	}

	@Override
	protected String getProviderID() {
		return "org.eclipse.datatools.connectivity.db.generic.connectionProfile";
	}

	@Override
	protected boolean supportsCatalogs() {
		return true;
	}

	@Override
	protected boolean executeOfflineTests() {
		return true;  // haven't actually tried this yet...
	}

}
