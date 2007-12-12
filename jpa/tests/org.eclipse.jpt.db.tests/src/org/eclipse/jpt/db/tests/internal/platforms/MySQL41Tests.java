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
 *  SQL Server 2005 Driver Test
 */
public class MySQL41Tests extends DTPPlatformTests {
	
    public MySQL41Tests( String name) {
        super( name);
    }
    
    protected String databaseVendor() {
        return "MySql";
    }

    protected String databaseVersion() {
        return "4.1";
    }

	protected String driverClass() {
		return "com.mysql.jdbc.Driver";
	}

	protected String driverDefinitionId() {
		return "DriverDefn.MySQL JDBC Driver";
	}

	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.mysql.4_1.driverTemplate";
	}

	protected String driverName() {
		return "MySQL JDBC Driver";
	}
	
    protected String getConfigName() {
    	return "mysql41.properties";
    }
}