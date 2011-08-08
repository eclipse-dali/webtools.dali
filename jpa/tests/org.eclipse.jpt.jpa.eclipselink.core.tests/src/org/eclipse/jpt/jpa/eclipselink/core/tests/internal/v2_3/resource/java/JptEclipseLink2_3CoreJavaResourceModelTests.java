/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptEclipseLink2_3CoreJavaResourceModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink2_3CoreJavaResourceModelTests.class.getName());
		suite.addTestSuite(EclipselinkMultitenantAnnotationTests.class);
		suite.addTestSuite(EclipseLinkTenantDiscriminatorColumnTests.class);
		
		return suite;
	}

	private JptEclipseLink2_3CoreJavaResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
