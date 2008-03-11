/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal.platforms;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *  AllPlatformTests
 */
public class AllPlatformTests {

    public static Test suite() {
		TestSuite suite = new TestSuite(AllPlatformTests.class.getPackage().getName());

// TODO - Uncomment the platform to test.
//			suite.addTestSuite(Derby101Tests.class);
//			suite.addTestSuite(Oracle9iTests.class);
//			suite.addTestSuite(Oracle10gTests.class);
//			suite.addTestSuite(Oracle10gXETests.class);
//			suite.addTestSuite(SQLServer2005Tests.class);
//			suite.addTestSuite(MySQL41Tests.class);
//			suite.addTestSuite(PostgreSQL824Tests.class);
//			suite.addTestSuite(Sybase12Tests.class);

        return suite;
    }

    private AllPlatformTests() {
        super();
        throw new UnsupportedOperationException();
    }

}
