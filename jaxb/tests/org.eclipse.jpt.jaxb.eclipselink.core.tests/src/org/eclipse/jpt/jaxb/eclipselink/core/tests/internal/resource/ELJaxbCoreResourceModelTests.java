/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.ELJaxbCoreTests;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java.ELJaxbJavaResourceModelTests;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.oxm.OxmResourceTests;

public class ELJaxbCoreResourceModelTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(ELJaxbCoreResourceModelTests.class.getName());

		if (ELJaxbCoreTests.requiredJarsExists()) {
			suite.addTest(ELJaxbJavaResourceModelTests.suite());
			suite.addTestSuite(OxmResourceTests.class);
		}
		else {
			suite.addTest(TestSuite.warning(ELJaxbCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}
	
	
	private ELJaxbCoreResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
