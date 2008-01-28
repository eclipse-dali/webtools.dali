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
 *  Oracle 9i Thin Driver Test
 */
public class Oracle9iTests extends DTPPlatformTests {

    public Oracle9iTests( String name) {
        super( name);
    }

    @Override
	protected String databaseVendor() {
		return "Oracle";
	}

    @Override
	protected String databaseVersion() {
		return "9";
	}

    @Override
	protected String driverClass() {
		return "oracle.jdbc.OracleDriver";
	}

    @Override
	protected String driverDefinitionId() {
		return "DriverDefn.Oracle Thin Driver";
	}

    @Override
	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.oracle.9.driverTemplate";
	}

    @Override
	protected String driverName() {
		return "Oracle 9i Thin Driver";
	}
	
    @Override
    protected String getConfigName() {
    	return "oracle9i.properties";
    }

}
