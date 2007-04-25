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
import org.eclipse.jpt.core.tests.internal.content.java.mappings.JpaCoreContentJavaMappingsTests;
import org.eclipse.jpt.core.tests.internal.jdtutility.JpaCoreJdtUtilityTests;
import org.eclipse.jpt.core.tests.internal.model.JpaCoreModelTests;

/**
 * Runs all JPA Core Tests
 */
public class JpaCoreTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JpaCoreTests.class.getName());
		suite.addTest(JpaCoreContentJavaMappingsTests.suite());
		suite.addTest(JpaCoreModelTests.suite());
		suite.addTest(JpaCoreJdtUtilityTests.suite());
		return suite;
	}
	
	private JpaCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
