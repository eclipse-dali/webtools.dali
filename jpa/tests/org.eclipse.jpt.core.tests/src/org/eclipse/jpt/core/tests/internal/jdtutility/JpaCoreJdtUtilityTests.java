/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jdtutility;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JpaCoreJdtUtilityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JpaCoreJdtUtilityTests.class.getName());
		suite.addTestSuite(MemberAnnotationElementAdapterTests.class);
		suite.addTestSuite(CombinationIndexedDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(JDTToolsTests.class);
		suite.addTestSuite(NestedDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(NestedIndexedDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(SimpleDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(TypeTests.class);
		return suite;
	}

	private JpaCoreJdtUtilityTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
