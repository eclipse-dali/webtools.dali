/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.tests.internal.platforms.AllPlatformTests;

/**
 *  JPT DB Tests
 */
public class JptJpaDbTests {

    public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaDbTests.class.getPackage().getName());
    
        suite.addTest(AllPlatformTests.suite());
		suite.addTest(new BundleActivatorTest(Database.class));
    
        return suite;
    }
    
    private JptJpaDbTests() {
        super();
        throw new UnsupportedOperationException();
    }
}
