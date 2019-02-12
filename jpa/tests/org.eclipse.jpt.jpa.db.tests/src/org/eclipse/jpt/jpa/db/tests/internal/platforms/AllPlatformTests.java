/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.tests.internal.platforms;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllPlatformTests {

    public static Test suite() {
		TestSuite suite = new TestSuite(AllPlatformTests.class.getPackage().getName());

		suite.addTestSuite(DerbyTests.class);
		suite.addTestSuite(MySQLTests.class);
		suite.addTestSuite(Oracle10gTests.class);
//		suite.addTestSuite(Oracle10gXETests.class);
//		suite.addTestSuite(Oracle9iTests.class);
		suite.addTestSuite(PostgreSQLTests.class);
//		suite.addTestSuite(SQLServerTests.class);
		suite.addTestSuite(SybaseTests.class);

        return suite;
    }

    private AllPlatformTests() {
        super();
        throw new UnsupportedOperationException();
    }

}
