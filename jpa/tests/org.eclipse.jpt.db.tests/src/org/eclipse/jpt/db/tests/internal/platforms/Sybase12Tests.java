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
public class Sybase12Tests extends DTPPlatformTests {

    public Sybase12Tests( String name) {
        super( name);
    }

    @Override
	protected String databaseVendor() {
		return "Sybase";
	}

    @Override
	protected String databaseVersion() {
		return "12.x";
	}

    @Override
	protected String driverClass() {
		return "com.sybase.jdbc3.jdbc.SybDriver";
	}

    @Override
	protected String driverDefinitionId() {
		return "DriverDefn.Sybase JDBC Driver";
	}

    @Override
	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.sybase.ase.12_x.driverTemplate";
	}

    @Override
	protected String driverName() {
		return "Sybase JDBC Driver";
	}
	
    @Override
    protected String getConfigName() {
    	return "sybase12.properties";
    }

}
