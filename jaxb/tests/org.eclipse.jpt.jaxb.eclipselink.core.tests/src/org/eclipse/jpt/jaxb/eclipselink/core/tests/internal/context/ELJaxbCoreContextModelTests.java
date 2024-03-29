/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.ELJaxbCoreTests;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java.ELJaxbCoreJavaContextModelTests;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.oxm.ELJaxbCoreOxmContextModelTests;


public class ELJaxbCoreContextModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(ELJaxbCoreContextModelTests.class.getName());
		
		if (ELJaxbCoreTests.requiredJarsExists()) {
			suite.addTestSuite(ELJaxbContextRootTests.class);			
			suite.addTest(ELJaxbCoreJavaContextModelTests.suite());
			suite.addTest(ELJaxbCoreOxmContextModelTests.suite());
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
