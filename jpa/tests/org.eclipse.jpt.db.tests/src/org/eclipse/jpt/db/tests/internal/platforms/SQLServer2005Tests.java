/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal.platforms;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.swingui.TestRunner;

/**
 *  SQL Server 2005 Driver Test
 */
public class SQLServer2005Tests extends DTPPlatformTests {
	
    public static void main( String[] args) {
        TestRunner.main( new String[] { "-c", SQLServer2005Tests.class.getName()});
    }

    public static Test suite() {
        return new TestSuite( SQLServer2005Tests.class);
    }
    
    public SQLServer2005Tests( String name) {
        super( name);
    }
    
    protected String databaseVendor() {
        return "SQLServer";
    }

    protected String databaseVersion() {
        return "2005";
    }
	
    protected String providerId() {
        return "org.eclipse.datatools.connectivity.db.generic.connectionProfile";
    }
    
    protected String driverName() {
        return "Microsoft SQL Server 2005 JDBC Driver";
    }
    
    protected String driverDefinitionType() {
        return "org.eclipse.datatools.enablement.msft.sqlserver.2005.driverTemplate";
    }
    
    protected String driverDefinitionId() {
        return "DriverDefn.Microsoft SQL Server 2005 JDBC Driver";
    }
    
    protected String driverClass() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }
	
    protected String getConfigName() {
    	return "sqlserver2005.properties";
    }
}
