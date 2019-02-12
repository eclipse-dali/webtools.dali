/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.utility.jdt;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonCoreUtilityJdtTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonCoreUtilityJdtTests.class.getPackage().getName());
		suite.addTestSuite(CombinationIndexedDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(DefaultAnnotationEditFormatterTests.class);
		suite.addTestSuite(ASTToolsTests.class);
		suite.addTestSuite(MemberAnnotationElementAdapterTests.class);
		suite.addTestSuite(NestedDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(NestedIndexedDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(SimpleDeclarationAnnotationAdapterTests.class);
		suite.addTestSuite(TypeTests.class);
		suite.addTestSuite(TypeToolsTests.class);
		return suite;
	}

	private JptCommonCoreUtilityJdtTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
