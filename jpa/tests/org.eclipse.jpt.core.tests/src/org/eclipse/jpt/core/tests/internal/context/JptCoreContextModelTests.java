/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.core.tests.internal.JptCoreTests;
import org.eclipse.jpt.core.tests.internal.context.java.JptCoreContextJavaModelTests;
import org.eclipse.jpt.core.tests.internal.context.orm.JptCoreOrmContextModelTests;
import org.eclipse.jpt.core.tests.internal.context.persistence.JptCorePersistenceContextModelTests;
import org.eclipse.jpt.core.tests.internal.jpa2.context.java.Generic2_0JavaContextModelTests;
import org.eclipse.jpt.core.tests.internal.jpa2.context.orm.Generic2_0OrmContextModelTests;
import org.eclipse.jpt.core.tests.internal.jpa2.context.persistence.Generic2_0PersistenceContextModelTests;

/**
 * Required Java system property:
 *   -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 */
public class JptCoreContextModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptCoreContextModelTests.class.getPackage().getName());

		if (JptCoreTests.requiredJarsExists()) {
			suite.addTestSuite(JpaProjectTests.class);
			suite.addTestSuite(JpaFileTests.class);
			suite.addTest(JptCorePersistenceContextModelTests.suite());
			suite.addTest(JptCoreOrmContextModelTests.suite());
			suite.addTest(JptCoreContextJavaModelTests.suite());
			suite.addTest(Generic2_0JavaContextModelTests.suite());
			suite.addTest(Generic2_0OrmContextModelTests.suite());
			suite.addTest(Generic2_0PersistenceContextModelTests.suite());
		} else {
			suite.addTest(TestSuite.warning(JptCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}

	private JptCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
