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
 *  Oracle 10g Thin Driver Test
 */
@SuppressWarnings("nls")
public class SybaseTests extends DTPPlatformTests {

	public SybaseTests( String name) {
		super( name);
	}

	@Override
	protected String getPlatformPropertiesFileName() {
		return "sybase.properties";
	}

	@Override
	protected String getDriverName() {
		return "Sybase JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionID() {
		return "DriverDefn.Sybase JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionType() {
		return "org.eclipse.datatools.enablement.sybase.ase.12_x.driverTemplate";
	}

	@Override
	protected String getDatabaseVendor() {
		return "Sybase";
	}

	@Override
	protected String getDatabaseVersion() {
		return "12.x";
	}

	@Override
	protected String getDriverClass() {
		return "com.sybase.jdbc3.jdbc.SybDriver";
	}

	@Override
	protected String getProfileName() {
		return "Sybase_12";
	}

	@Override
	protected String getProfileDescription() {
		return "Sybase 12 jConnect JDBC Profile [Test]";
	}

}
