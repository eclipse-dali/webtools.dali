/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.common.core.tests.ValidationMessageClassTest;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class JptJpaCoreMiscTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaCoreMiscTests.class.getPackage().getName());
		suite.addTestSuite(JpaPreferencesTests.class);
		suite.addTest(new BundleActivatorTest(JpaProject.class));
		suite.addTest(new ValidationMessageClassTest(JptJpaCoreValidationMessages.class));
		return suite;
	}

	private JptJpaCoreMiscTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
