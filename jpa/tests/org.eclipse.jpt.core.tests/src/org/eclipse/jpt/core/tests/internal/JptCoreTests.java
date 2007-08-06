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

/**
 * Runs most JPT Core Tests.  Currently we do not have a jpa.jar checked in to cvs. 
 * As a result we cannot run any tests that depend on that jar during the build.  In
 * our dev environments we should run JptAllCoreTests until we have jpa.jar checked in.
 */
public class JptCoreTests {

	public static Test suite() {
		return JptAllCoreTests.suite(false);
	}
	
	private JptCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
