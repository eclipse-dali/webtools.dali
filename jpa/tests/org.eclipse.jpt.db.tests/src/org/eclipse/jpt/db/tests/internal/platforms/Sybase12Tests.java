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
 *  Oracle 10g Thin Driver Test
 */
public class Sybase12Tests extends DTPPlatformTests {

    public static void main( String[] args) {
        TestRunner.main( new String[] { "-c", Sybase12Tests.class.getName()});
    }

    public static Test suite() {
        return new TestSuite( Sybase12Tests.class);
    }
    
    public Sybase12Tests( String name) {
        super( name);
    }

	protected String databaseVendor() {
		return "Sybase";
	}

	protected String databaseVersion() {
		return "12.x";
	}

	protected String driverClass() {
		return "com.sybase.jdbc3.jdbc.SybDriver";
	}

	protected String driverDefinitionId() {
		return "DriverDefn.Sybase JDBC Driver";
	}

	protected String driverDefinitionType() {
		return "org.eclipse.datatools.enablement.sybase.ase.12_x.driverTemplate";
	}

	protected String driverName() {
		return "Sybase JDBC Driver";
	}
	
    protected String getConfigName() {
    	return "sybase12.properties";
    }
}
