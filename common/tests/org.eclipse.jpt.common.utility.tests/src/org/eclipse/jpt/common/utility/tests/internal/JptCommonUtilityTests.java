/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.tests.internal.enumerations.JptUtilityEnumerationsTests;
import org.eclipse.jpt.common.utility.tests.internal.iterables.JptUtilityIterablesTests;
import org.eclipse.jpt.common.utility.tests.internal.iterators.JptUtilityIteratorsTests;
import org.eclipse.jpt.common.utility.tests.internal.model.JptUtilityModelTests;
import org.eclipse.jpt.common.utility.tests.internal.node.JptUtilityNodeTests;
import org.eclipse.jpt.common.utility.tests.internal.synchronizers.JptUtilitySynchronizersTests;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityTests.class.getPackage().getName());

		suite.addTest(JptUtilityEnumerationsTests.suite());
		suite.addTest(JptUtilityIterablesTests.suite());
		suite.addTest(JptUtilityIteratorsTests.suite());
		suite.addTest(JptUtilityModelTests.suite());
		suite.addTest(JptUtilityNodeTests.suite());
		suite.addTest(JptUtilitySynchronizersTests.suite());

		suite.addTestSuite(ArrayToolsTests.class);
		suite.addTestSuite(AsynchronousCommandExecutorTests.class);
		suite.addTestSuite(BagTests.class);
		suite.addTestSuite(BidiFilterTests.class);
		suite.addTestSuite(BidiStringConverterTests.class);
		suite.addTestSuite(BidiTransformerTests.class);
		suite.addTestSuite(BitToolsTests.class);
		suite.addTestSuite(SimpleBooleanReferenceTests.class);
		suite.addTestSuite(BooleanToolsTests.class);
		suite.addTestSuite(ClasspathTests.class);
		suite.addTestSuite(ClassNameTests.class);
		suite.addTestSuite(CollectionToolsTests.class);
		suite.addTestSuite(CommandExecutorTests.class);
		suite.addTestSuite(CommandRunnableTests.class);
		suite.addTestSuite(CommandTests.class);
		suite.addTestSuite(CompositeCommandTests.class);
		suite.addTestSuite(ExceptionHandlerTests.class);
		suite.addTestSuite(FileToolsTests.class);
		suite.addTestSuite(FilterTests.class);
		suite.addTestSuite(HashBagTests.class);
		suite.addTestSuite(IdentityHashBagTests.class);
		suite.addTestSuite(IndentingPrintWriterTests.class);
		suite.addTestSuite(SimpleIntReferenceTests.class);
		suite.addTestSuite(JavaTypeTests.class);
		suite.addTestSuite(JDBCTypeTests.class);
		suite.addTestSuite(KeyedSetTests.class);
		suite.addTestSuite(ListenerListTests.class);
		suite.addTestSuite(MethodSignatureTests.class);
		suite.addTestSuite(NameToolsTests.class);
		suite.addTestSuite(NotNullFilterTests.class);
		suite.addTestSuite(RangeTests.class);
		suite.addTestSuite(ReflectionToolsTests.class);
		suite.addTestSuite(ReverseComparatorTests.class);
		suite.addTestSuite(SimpleAssociationTests.class);
		suite.addTestSuite(SimpleObjectReferenceTests.class);
		suite.addTestSuite(SimpleQueueTests.class);
		suite.addTestSuite(SimpleStackTests.class);
		suite.addTestSuite(StringToolsTests.class);
		suite.addTestSuite(SynchronizedBooleanTests.class);
		suite.addTestSuite(SynchronizedIntTests.class);
		suite.addTestSuite(SynchronizedObjectTests.class);
		suite.addTestSuite(SynchronizedQueueTests.class);
		suite.addTestSuite(SynchronizedStackTests.class);
		suite.addTestSuite(ToolsTests.class);
		suite.addTestSuite(XMLStringEncoderTests.class);

		return suite;
	}

	private JptCommonUtilityTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
