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
import org.eclipse.jpt.utility.internal.ClassTools;

/**
 *  AllPlatformTests
 */
public class AllPlatformTests {

    public static void main( String[] args) {
        TestRunner.main( new String[] { "-c",  AllPlatformTests.class.getName()});
    }

    public static Test suite() {
        TestSuite suite = new TestSuite( ClassTools.packageNameFor( AllPlatformTests.class));

        // TODO - Uncomment the platform to test.
//        suite.addTest( Derby101Tests.suite());
//        suite.addTest( Oracle9iTests.suite());
//        suite.addTest( Oracle10gTests.suite());
//        suite.addTest( SQLServer2005Tests.suite());

        return suite;
    }

    private AllPlatformTests() {
        super();
    }

}