/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.ELJaxbCoreTests;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java.ELJaxbCoreJavaContextModelTests;


public class ELJaxbCoreContextModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(ELJaxbCoreContextModelTests.class.getName());
		
		if (ELJaxbCoreTests.requiredJarsExists()) {
			suite.addTestSuite(ELJaxbContextRootTests.class);			
			suite.addTest(ELJaxbCoreJavaContextModelTests.suite());
		}
		else {
			suite.addTest(TestSuite.warning(ELJaxbCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}

	private ELJaxbCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
