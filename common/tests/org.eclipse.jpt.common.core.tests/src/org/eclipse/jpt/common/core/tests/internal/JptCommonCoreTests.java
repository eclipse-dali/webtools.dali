/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.common.core.tests.internal.resource.java.JptCommonCoreResourceJavaTests;
import org.eclipse.jpt.common.core.tests.internal.utility.JptCommonCoreUtilityTests;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.JptCommonCoreUtilityJdtTests;

public class JptCommonCoreTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonCoreTests.class.getPackage().getName());
		suite.addTest(JptCommonCoreResourceJavaTests.suite());
		suite.addTest(JptCommonCoreUtilityTests.suite());
		suite.addTest(JptCommonCoreUtilityJdtTests.suite());
		suite.addTest(new BundleActivatorTest(AnnotationProvider.class));
		return suite;
	}
	
	private JptCommonCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
