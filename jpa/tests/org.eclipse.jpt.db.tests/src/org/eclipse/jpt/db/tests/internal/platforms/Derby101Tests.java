/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal.platforms;


/**
 *  Derby 10.1 Embedded Driver Test
 */
public class Derby101Tests extends DTPPlatformTests {
	
    public Derby101Tests( String name) {
        super( name);
    }
    
    @Override
	protected String databaseVendor() {
        return "Derby";
    }

    @Override
    protected String databaseVersion() {
        return "10.1";
    }
	
    @Override
    protected String providerId() {
        return "org.eclipse.datatools.connectivity.db.derby.embedded.connectionProfile";
    }
    
    @Override
    protected String driverName() {
        return "Derby Embedded JDBC Driver";
    }
    
    @Override
    protected String driverDefinitionType() {
        return "org.eclipse.datatools.connectivity.db.derby101.genericDriverTemplate";
    }
    
    @Override
    protected String driverDefinitionId() {
        return "DriverDefn.Derby Embedded JDBC Driver";
    }
    
    @Override
    protected String driverClass() {
        return "org.apache.derby.jdbc.EmbeddedDriver";
    }
	
    @Override
    protected String getConfigName() {
    	return "derby101.properties";
    }

}
