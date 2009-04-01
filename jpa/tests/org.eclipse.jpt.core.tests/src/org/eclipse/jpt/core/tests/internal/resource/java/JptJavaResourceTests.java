/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptJavaResourceTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJavaResourceTests.class.getName());
		suite.addTestSuite(JavaResourcePersistentTypeTests.class);
		suite.addTestSuite(JavaResourcePersistentAttributeTests.class);
		suite.addTestSuite(JPTToolsTests.class);
		
		suite.addTestSuite(AssociationOverrideTests.class);
		suite.addTestSuite(AssociationOverridesTests.class);
		suite.addTestSuite(AttributeOverrideTests.class);
		suite.addTestSuite(AttributeOverridesTests.class);
		suite.addTestSuite(BasicTests.class);
		suite.addTestSuite(ColumnTests.class);
		suite.addTestSuite(DiscriminatorValueTests.class);
		suite.addTestSuite(DiscriminatorColumnTests.class);
		suite.addTestSuite(EmbeddableTests.class);
		suite.addTestSuite(EmbeddedTests.class);
		suite.addTestSuite(EmbeddedIdTests.class);
		suite.addTestSuite(EntityTests.class);
		suite.addTestSuite(EnumeratedTests.class);
		suite.addTestSuite(GeneratedValueTests.class);
		suite.addTestSuite(IdClassTests.class);
		suite.addTestSuite(IdTests.class);
		suite.addTestSuite(InheritanceTests.class);
		suite.addTestSuite(JoinColumnTests.class);
		suite.addTestSuite(JoinColumnsTests.class);
		suite.addTestSuite(JoinTableTests.class);
		suite.addTestSuite(LobTests.class);
		suite.addTestSuite(ManyToManyTests.class);
		suite.addTestSuite(ManyToOneTests.class);
		suite.addTestSuite(MapKeyTests.class);
		suite.addTestSuite(MappedSuperclassTests.class);
		suite.addTestSuite(NamedNativeQueryTests.class);
		suite.addTestSuite(NamedNativeQueriesTests.class);
		suite.addTestSuite(NamedQueryTests.class);
		suite.addTestSuite(NamedQueriesTests.class);
		suite.addTestSuite(OneToManyTests.class);
		suite.addTestSuite(OneToOneTests.class);
		suite.addTestSuite(OrderByTests.class);
		suite.addTestSuite(PrimaryKeyJoinColumnTests.class);
		suite.addTestSuite(PrimaryKeyJoinColumnsTests.class);
		suite.addTestSuite(QueryHintTests.class);
		suite.addTestSuite(SecondaryTableTests.class);
		suite.addTestSuite(SecondaryTablesTests.class);
		suite.addTestSuite(SequenceGeneratorTests.class);
		suite.addTestSuite(TableGeneratorTests.class);
		suite.addTestSuite(TableTests.class);
		suite.addTestSuite(TemporalTests.class);
		suite.addTestSuite(TransientTests.class);
		suite.addTestSuite(VersionTests.class);
			
		return suite;
	}

	private JptJavaResourceTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
