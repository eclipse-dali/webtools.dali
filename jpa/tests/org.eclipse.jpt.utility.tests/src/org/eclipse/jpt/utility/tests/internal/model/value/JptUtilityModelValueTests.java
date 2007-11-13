/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import org.eclipse.jpt.utility.tests.internal.model.value.prefs.JptUtilityModelValuePrefsTests;
import org.eclipse.jpt.utility.tests.internal.model.value.swing.JptUtilityModelValueSwingTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptUtilityModelValueTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityModelValueTests.class.getPackage().getName());

		suite.addTest(JptUtilityModelValuePrefsTests.suite());
		suite.addTest(JptUtilityModelValueSwingTests.suite());
		
		suite.addTestSuite(BufferedPropertyValueModelTests.class);
		suite.addTestSuite(CollectionAspectAdapterTests.class);
		suite.addTestSuite(CollectionListValueModelAdapterTests.class);
		suite.addTestSuite(CollectionPropertyValueModelAdapterTests.class);
		suite.addTestSuite(CompositeCollectionValueModelTests.class);
		suite.addTestSuite(ExtendedListValueModelWrapperTests.class);
		suite.addTestSuite(FilteringCollectionValueModelTests.class);
		suite.addTestSuite(FilteringPropertyValueModelTests.class);
		suite.addTestSuite(ItemCollectionListValueModelAdapterTests.class);
		suite.addTestSuite(ItemListListValueModelAdapterTests.class);
		suite.addTestSuite(ItemPropertyListValueModelAdapterTests.class);
		suite.addTestSuite(ItemStateListValueModelAdapterTests.class);
		suite.addTestSuite(ListAspectAdapterTests.class);
		suite.addTestSuite(ListCollectionValueModelAdapterTests.class);
		suite.addTestSuite(ListCuratorTests.class);
		suite.addTestSuite(NullCollectionValueModelTests.class);
		suite.addTestSuite(NullListValueModelTests.class);
		suite.addTestSuite(NullPropertyValueModelTests.class);
		suite.addTestSuite(PropertyAspectAdapterTests.class);
		suite.addTestSuite(PropertyCollectionValueModelAdapterTests.class);
		suite.addTestSuite(ReadOnlyCollectionValueModelTests.class);
		suite.addTestSuite(ReadOnlyListValueModelTests.class);
		suite.addTestSuite(SimpleCollectionValueModelTests.class);
		suite.addTestSuite(SimpleListValueModelTests.class);
		suite.addTestSuite(SimplePropertyValueModelTests.class);
		suite.addTestSuite(SortedListValueModelAdapterTests.class);
		suite.addTestSuite(StaticValueModelTests.class);
		suite.addTestSuite(TransformationListValueModelAdapterTests.class);
		suite.addTestSuite(TransformationListValueModelAdapterTests.TransformerTests.class);
		suite.addTestSuite(TransformationPropertyValueModelTests.class);
		suite.addTestSuite(TreeAspectAdapterTests.class);
		suite.addTestSuite(ValueCollectionPropertyValueModelAdapterTests.class);
		suite.addTestSuite(ValueListPropertyValueModelAdapterTests.class);
		suite.addTestSuite(ValuePropertyPropertyValueModelAdapterTests.class);
		suite.addTestSuite(ValueStatePropertyValueModelAdapterTests.class);
	
		return suite;
	}
	
	private JptUtilityModelValueTests() {
		super();
		throw new UnsupportedOperationException();
	}
	
}
