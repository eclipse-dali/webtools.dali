/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal;

import org.eclipse.jpt.db.tests.internal.platforms.AllPlatformTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *  JPT DB Tests
 */
public class JptDbTests {

    public static Test suite() {
		TestSuite suite = new TestSuite(JptDbTests.class.getPackage().getName());
    
        suite.addTest( AllPlatformTests.suite());
    
        return suite;
    }
    
    private JptDbTests() {
        super();
        throw new UnsupportedOperationException();
    }

}
