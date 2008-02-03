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
public class MySQL41Tests extends DTPPlatformTests {
	
    public MySQL41Tests( String name) {
        super( name);
    }
    
    @Override
    protected String databaseVendor() {
        return "MySql";
    }

    @Override
    protected String databaseVersion() {
        return "4.1";
    }

    @Override
	protected String driverClass() {
		return "com.mysql.jdbc.Driver";
	}

    @Override
	protected String driverDefinitionId() {
		return "DriverDefn.MySQL JDBC Driver";
	}

    @Override
	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.mysql.4_1.driverTemplate";
	}

    @Override
	protected String driverName() {
		return "MySQL JDBC Driver";
	}
	
    @Override
    protected String getConfigName() {
    	return "mysql41.properties";
    }

}