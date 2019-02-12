/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.gen.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.jpa.gen.internal.util.EntityGenTools;

public class JptJpaGenTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("JPA Entity Generation Tests"); //$NON-NLS-1$
		suite.addTestSuite(EntityGenToolsTests.class);
		suite.addTest(new BundleActivatorTest(EntityGenTools.class));
		return suite;
	}

	private JptJpaGenTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
