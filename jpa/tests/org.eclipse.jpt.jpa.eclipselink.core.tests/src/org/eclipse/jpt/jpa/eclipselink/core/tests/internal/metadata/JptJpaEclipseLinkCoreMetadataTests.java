/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.metadata;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 *  JptJpaEclipseLinkCoreMetadataTests
 */
public class JptJpaEclipseLinkCoreMetadataTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaEclipseLinkCoreMetadataTests.class.getPackage().getName());
		suite.addTestSuite(EclipseLinkStaticWeavingBuilderMetadataTests.class);
		return suite;
	}

	private JptJpaEclipseLinkCoreMetadataTests() {
		super();
		throw new UnsupportedOperationException();
	}

}