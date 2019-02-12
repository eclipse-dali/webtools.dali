/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;


public class JptCommonCoreResourceJavaTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonCoreResourceJavaTests.class.getPackage().getName());
		suite.addTestSuite(BinaryTypeTests.class);
		suite.addTestSuite(SourceAttributeTests.class);
		return suite;
	}
	
	private JptCommonCoreResourceJavaTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
