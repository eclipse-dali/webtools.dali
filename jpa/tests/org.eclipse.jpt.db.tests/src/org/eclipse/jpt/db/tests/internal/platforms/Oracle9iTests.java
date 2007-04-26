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
 *  Oracle 9i Thin Driver Test
 */
public class Oracle9iTests extends DTPPlatformTests {

    public static void main( String[] args) {
        TestRunner.main( new String[] { "-c", Oracle9iTests.class.getName()});
    }

    public static Test suite() {
        return new TestSuite( Oracle9iTests.class);
    }
    
    public Oracle9iTests( String name) {
        super( name);
    }

	protected String databaseVendor() {
		return "Oracle";
	}

	protected String databaseVersion() {
		return "9";
	}

	protected String driverClass() {
		return "oracle.jdbc.OracleDriver";
	}

	protected String driverDefinitionId() {
		return "DriverDefn.Oracle Thin Driver";
	}

	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.oracle.9.driverTemplate";
	}

	protected String driverName() {
		return "Oracle 9i Thin Driver";
	}
	
    protected String getConfigName() {
    	return "oracle9i.properties";
    }
}
