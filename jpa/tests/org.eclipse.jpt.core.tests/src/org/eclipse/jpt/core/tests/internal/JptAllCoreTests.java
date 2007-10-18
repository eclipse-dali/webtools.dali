/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal;

import junit.framework.Test;

/**
 * This test suite is temporary, until jpa.jar can be checked into the CVS
 * repository and used during the nightly builds.
 * 
 * Runs ALL JPT Core Tests. Currently we do not have a jpa.jar checked in to CVS.
 * As a result we cannot run any tests that depend on that jar during the build.
 * In our local development environments we should run this suite (JptAllCoreTests)
 * until jpa.jar is checked into CVS.
 * In the nightly build we should continue to run JptCoreTests.
 */
public class JptAllCoreTests {

	public static Test suite() {
		return JptCoreTests.suite(true);
	}

	private JptAllCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
