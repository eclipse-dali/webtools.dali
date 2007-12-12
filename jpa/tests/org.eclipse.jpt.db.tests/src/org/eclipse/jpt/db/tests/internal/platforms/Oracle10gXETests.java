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
public class Oracle10gXETests extends DTPPlatformTests {

    public Oracle10gXETests( String name) {
        super( name);
    }

	protected String databaseVendor() {
		return "Oracle";
	}

	protected String databaseVersion() {
		return "10";
	}

	protected String driverClass() {
		return "oracle.jdbc.OracleDriver";
	}

	protected String driverDefinitionId() {
		return "DriverDefn.Oracle Thin Driver";
	}

	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.oracle.10.driverTemplate";
	}

	protected String driverName() {
		return "Oracle 10g Thin Driver";
	}
	
    protected String getConfigName() {
    	return "oracle10gXE.properties";
    }
}
