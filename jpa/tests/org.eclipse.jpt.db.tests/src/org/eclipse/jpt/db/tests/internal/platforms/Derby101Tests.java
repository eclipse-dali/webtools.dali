/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
 *  Derby 10.1 Embedded Driver Test
 */
public class Derby101Tests extends DTPPlatformTests {
	
    public static void main( String[] args) {
        TestRunner.main( new String[] { "-c", Derby101Tests.class.getName()});
    }

    public static Test suite() {
        return new TestSuite( Derby101Tests.class);
    }
    
    public Derby101Tests( String name) {
        super( name);
    }
    
    protected String databaseVendor() {
        return "Derby";
    }

    protected String databaseVersion() {
        return "10.1";
    }
	
    protected String providerId() {
        return "org.eclipse.datatools.connectivity.db.derby.embedded.connectionProfile";
    }
    
    protected String driverName() {
        return "Derby Embedded JDBC Driver";
    }
    
    protected String driverDefinitionType() {
        return "org.eclipse.datatools.connectivity.db.derby101.genericDriverTemplate";
    }
    
    protected String driverDefinitionId() {
        return "DriverDefn.Derby Embedded JDBC Driver";
    }
    
    protected String driverClass() {
        return "org.apache.derby.jdbc.EmbeddedDriver";
    }
	
    protected String getConfigName() {
    	return "derby101.properties";
    }
}
