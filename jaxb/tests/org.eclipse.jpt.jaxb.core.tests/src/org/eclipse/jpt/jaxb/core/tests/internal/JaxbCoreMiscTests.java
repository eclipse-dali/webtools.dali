/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.common.core.tests.ValidationMessageClassTest;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;

public class JaxbCoreMiscTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreMiscTests.class.getName());

		suite.addTestSuite(SchemaLibraryTests.class);
		suite.addTest(new BundleActivatorTest(JaxbProject.class));
		suite.addTest(new ValidationMessageClassTest(JptJaxbCoreValidationMessages.class));

		return suite;
	}

	private JaxbCoreMiscTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
