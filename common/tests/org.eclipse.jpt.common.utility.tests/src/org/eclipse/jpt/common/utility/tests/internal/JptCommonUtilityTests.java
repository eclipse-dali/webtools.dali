/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.utility.tests.internal.collection.JptCommonUtilityCollectionTests;
import org.eclipse.jpt.common.utility.tests.internal.command.JptCommonUtilityCommandTests;
import org.eclipse.jpt.common.utility.tests.internal.comparator.JptCommonUtilityComparatorTests;
import org.eclipse.jpt.common.utility.tests.internal.enumeration.JptCommonUtilityEnumerationTests;
import org.eclipse.jpt.common.utility.tests.internal.exception.JptCommonUtilityExceptionTests;
import org.eclipse.jpt.common.utility.tests.internal.factory.JptCommonUtilityFactoryTests;
import org.eclipse.jpt.common.utility.tests.internal.io.JptCommonUtilityIOTests;
import org.eclipse.jpt.common.utility.tests.internal.iterable.JptCommonUtilityIterableTests;
import org.eclipse.jpt.common.utility.tests.internal.iterator.JptCommonUtilityIteratorTests;
import org.eclipse.jpt.common.utility.tests.internal.jdbc.JptCommonUtilityJDBCTests;
import org.eclipse.jpt.common.utility.tests.internal.model.JptCommonUtilityModelTests;
import org.eclipse.jpt.common.utility.tests.internal.node.JptCommonUtilityNodeTests;
import org.eclipse.jpt.common.utility.tests.internal.predicate.JptCommonUtilityPredicateTests;
import org.eclipse.jpt.common.utility.tests.internal.reference.JptCommonUtilityReferenceTests;
import org.eclipse.jpt.common.utility.tests.internal.transformer.JptCommonUtilityTransformerTests;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityTests.class.getPackage().getName());

		suite.addTest(JptCommonUtilityCollectionTests.suite());
		suite.addTest(JptCommonUtilityCommandTests.suite());
		suite.addTest(JptCommonUtilityComparatorTests.suite());
		suite.addTest(JptCommonUtilityEnumerationTests.suite());
		suite.addTest(JptCommonUtilityExceptionTests.suite());
		suite.addTest(JptCommonUtilityFactoryTests.suite());
		suite.addTest(JptCommonUtilityIOTests.suite());
		suite.addTest(JptCommonUtilityIterableTests.suite());
		suite.addTest(JptCommonUtilityIteratorTests.suite());
		suite.addTest(JptCommonUtilityJDBCTests.suite());
		suite.addTest(JptCommonUtilityModelTests.suite());
		suite.addTest(JptCommonUtilityNodeTests.suite());
		suite.addTest(JptCommonUtilityPredicateTests.suite());
		suite.addTest(JptCommonUtilityReferenceTests.suite());
		suite.addTest(JptCommonUtilityTransformerTests.suite());

		suite.addTestSuite(ArrayToolsTests.class);
		suite.addTestSuite(BitToolsTests.class);
		suite.addTestSuite(BooleanToolsTests.class);
		suite.addTestSuite(ByteArrayToolsTests.class);
		suite.addTestSuite(CharacterToolsTests.class);
		suite.addTestSuite(CharArrayToolsTests.class);
		suite.addTestSuite(ClassToolsTests.class);
		suite.addTestSuite(ClassNameToolsTests.class);
		suite.addTestSuite(ClasspathTests.class);
		suite.addTestSuite(ListenerListTests.class);
		suite.addTestSuite(MapKeyAssociationTests.class);
		suite.addTestSuite(NameToolsTests.class);
		suite.addTestSuite(ObjectToolsTests.class);
		suite.addTestSuite(RangeTests.class);
		suite.addTestSuite(SimpleAssociationTests.class);
		suite.addTestSuite(SimpleJavaTypeTests.class);
		suite.addTestSuite(SimpleMethodSignatureTests.class);
		suite.addTestSuite(StackTraceTests.class);
		suite.addTestSuite(StringBufferToolsTests.class);
		suite.addTestSuite(StringBuilderToolsTests.class);
		suite.addTestSuite(StringToolsTests.class);
		suite.addTestSuite(SystemToolsTests.class);
		suite.addTestSuite(TypeDeclarationToolsTests.class);
		suite.addTestSuite(XMLToolsReadTests.class);
		suite.addTestSuite(XMLToolsWriteTests.class);

		return suite;
	}

	private JptCommonUtilityTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
