/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Generic2_0JavaContextModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(Generic2_0JavaContextModelTests.class.getPackage().getName());
		suite.addTestSuite(GenericJavaAssociationOverride2_0Tests.class);
		suite.addTestSuite(GenericJavaBasicMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaCascade2_0Tests.class);
		suite.addTestSuite(GenericJavaCollectionTable2_0Tests.class);
		suite.addTestSuite(GenericJavaElementCollectionMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaEntity2_0Tests.class);
		suite.addTestSuite(GenericJavaEmbeddedMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaEmbeddedIdMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaIdMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaManyToManyMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaManyToOneMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaOneToManyMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaOneToOneMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaPersistentAttribute2_0Tests.class);
		suite.addTestSuite(GenericJavaPersistentType2_0Tests.class);
		suite.addTestSuite(GenericJavaSequenceGenerator2_0Tests.class);
		suite.addTestSuite(GenericJavaVersionMapping2_0Tests.class);
		return suite;
	}

	private Generic2_0JavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
