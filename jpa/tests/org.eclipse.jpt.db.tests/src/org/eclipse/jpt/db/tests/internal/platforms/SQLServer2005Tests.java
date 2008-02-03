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
public class SQLServer2005Tests extends DTPPlatformTests {
	
    public SQLServer2005Tests( String name) {
        super( name);
    }
    
    @Override
    protected String databaseVendor() {
        return "SQLServer";
    }

    @Override
    protected String databaseVersion() {
        return "2005";
    }
	
    @Override
    protected String providerId() {
        return "org.eclipse.datatools.connectivity.db.generic.connectionProfile";
    }
    
    @Override
    protected String driverName() {
        return "Microsoft SQL Server 2005 JDBC Driver";
    }
    
    @Override
    protected String driverDefinitionType() {
        return "org.eclipse.datatools.enablement.msft.sqlserver.2005.driverTemplate";
    }
    
    @Override
    protected String driverDefinitionId() {
        return "DriverDefn.Microsoft SQL Server 2005 JDBC Driver";
    }
    
    @Override
    protected String driverClass() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }
	
    @Override
    protected String getConfigName() {
    	return "sqlserver2005.properties";
    }

}
