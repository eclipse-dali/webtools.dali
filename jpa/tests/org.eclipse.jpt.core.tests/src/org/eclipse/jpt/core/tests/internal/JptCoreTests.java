/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.core.tests.internal.context.JptCoreContextModelTests;
import org.eclipse.jpt.core.tests.internal.jdtutility.JptCoreJdtUtilityTests;
import org.eclipse.jpt.core.tests.internal.model.JptCoreModelTests;
import org.eclipse.jpt.core.tests.internal.resource.JptCoreResourceModelTests;

/**
 * Runs MOST JPT Core Tests. Currently we do not have a jpa.jar checked into cvs. 
 * As a result we cannot run any tests that depend on that jar during the nightly build.
 * In our development environments we should run JptAllCoreTests (NOT this suite)
 * until jpa.jar is checked into CVS.
 */
public class JptCoreTests {

	public static Test suite() {
		return suite(false);
	}
	
	public static Test suite(boolean all) {
		String quantity = all ? "All" : "Most";
		TestSuite suite = new TestSuite(quantity + " JPT Core Tests");
		suite.addTest(JptCoreJdtUtilityTests.suite(all));
		suite.addTest(JptCoreModelTests.suite(all));
		suite.addTest(JptCoreResourceModelTests.suite(all));
		suite.addTest(JptCoreContextModelTests.suite(all));
		return suite;
	}
	
	private JptCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
