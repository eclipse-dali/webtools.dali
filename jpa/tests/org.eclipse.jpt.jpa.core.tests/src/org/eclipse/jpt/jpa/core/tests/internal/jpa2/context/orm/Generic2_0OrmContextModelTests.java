/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.orm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Generic2_0OrmContextModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(Generic2_0OrmContextModelTests.class.getPackage().getName());
		suite.addTestSuite(GenericOrmAssociationOverride2_0Tests.class);
		suite.addTestSuite(GenericOrmCascade2_0Tests.class);
		suite.addTestSuite(GenericOrmCollectionTable2_0Tests.class);
		suite.addTestSuite(GenericOrmElementCollectionMapping2_0Tests.class);
		suite.addTestSuite(GenericOrmEntity2_0Tests.class);
		suite.addTestSuite(GenericOrmEmbeddedMapping2_0Tests.class);
		suite.addTestSuite(GenericOrmManyToManyMapping2_0Tests.class);
		suite.addTestSuite(GenericOrmManyToOneMapping2_0Tests.class);
		suite.addTestSuite(GenericOrmOneToManyMapping2_0Tests.class);
		suite.addTestSuite(GenericOrmOneToOneMapping2_0Tests.class);
		suite.addTestSuite(GenericOrmPersistentAttribute2_0Tests.class);
		suite.addTestSuite(GenericOrmPersistentType2_0Tests.class);
		suite.addTestSuite(GenericOrmSequenceGenerator2_0Tests.class);
		return suite;
	}

	private Generic2_0OrmContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
