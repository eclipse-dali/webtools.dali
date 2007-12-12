/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
public class PostgreSQL824Tests extends DTPPlatformTests {

    public PostgreSQL824Tests( String name) {
        super( name);
    }

	protected String databaseVendor() {
		return "postgres";
	}

	protected String databaseVersion() {
		return "8.x";
	}

	protected String driverClass() {
		return "org.postgresql.Driver";
	}

	protected String driverDefinitionId() {
		return "DriverDefn.PostgreSQL JDBC Driver";
	}

	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.postgresql.postgresqlDriverTemplate";
	}

	protected String driverName() {
		return "PostgreSQL JDBC Driver";
	}
	
    protected String getConfigName() {
    	return "postgresql824.properties";
    }
}
