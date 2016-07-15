/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import org.eclipse.jpt.common.utility.tests.internal.model.value.prefs.JptCommonUtilityModelValuePrefsTests;
import org.eclipse.jpt.common.utility.tests.internal.model.value.swing.JptCommonUtilityModelValueSwingTests;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityModelValueTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityModelValueTests.class.getPackage().getName());

		suite.addTest(JptCommonUtilityModelValuePrefsTests.suite());
		suite.addTest(JptCommonUtilityModelValueSwingTests.suite());

		suite.addTestSuite(BufferedPropertyValueModelTests.class);
		suite.addTestSuite(CachingTransformationPropertyValueModelTests.class);
		suite.addTestSuite(CollectionAspectAdapterTests.class);
		suite.addTestSuite(CollectionListValueModelAdapterTests.class);
		suite.addTestSuite(CollectionPropertyValueModelAdapterTests.class);
		suite.addTestSuite(CollectionValueModelToolsTests.class);
		suite.addTestSuite(CompositeAndBooleanPropertyValueModelTests.class);
		suite.addTestSuite(CompositeCollectionValueModelTests.class);
		suite.addTestSuite(CompositeListValueModelTests.class);
		suite.addTestSuite(CompositeOrBooleanPropertyValueModelTests.class);
		suite.addTestSuite(CompositePropertyValueModelTests.class);
		suite.addTestSuite(CompoundModifiablePropertyValueModelTests.class);
		suite.addTestSuite(CompoundPropertyValueModelTests.class);
		suite.addTestSuite(ExtendedListValueModelWrapperTests.class);
		suite.addTestSuite(FilteringCollectionValueModelTests.class);
		suite.addTestSuite(FilteringPropertyValueModelTests.class);
		suite.addTestSuite(ItemCollectionListValueModelAdapterTests.class);
		suite.addTestSuite(ItemListListValueModelAdapterTests.class);
		suite.addTestSuite(ItemPropertyListValueModelAdapterTests.class);
		suite.addTestSuite(ItemStateListValueModelAdapterTests.class);
		suite.addTestSuite(ListAspectAdapterTests.class);
		suite.addTestSuite(ListCollectionValueModelAdapterTests.class);
		suite.addTestSuite(ListCompositePropertyValueModelTests.class);
		suite.addTestSuite(ListCuratorTests.class);
		suite.addTestSuite(ListPropertyValueModelAdapterTests.class);
		suite.addTestSuite(ListValueModelToolsTests.class);
		suite.addTestSuite(NullCollectionValueModelTests.class);
		suite.addTestSuite(NullListValueModelTests.class);
		suite.addTestSuite(NullPropertyValueModelTests.class);
		suite.addTestSuite(PropertyAspectAdapterTests.class);
		suite.addTestSuite(PropertyCollectionValueModelAdapterTests.class);
		suite.addTestSuite(PropertyListValueModelAdapterTests.class);
		suite.addTestSuite(PropertyValueModelToolsTests.class);
		suite.addTestSuite(SetCollectionValueModelTests.class);
		suite.addTestSuite(SimpleCollectionValueModelTests.class);
		suite.addTestSuite(SimpleListValueModelTests.class);
		suite.addTestSuite(SimplePropertyValueModelTests.class);
		suite.addTestSuite(SortedListValueModelAdapterTests.class);
		suite.addTestSuite(SortedListValueModelWrapperTests.class);
		suite.addTestSuite(StaticCollectionValueModelTests.class);
		suite.addTestSuite(StaticListValueModelTests.class);
		suite.addTestSuite(StaticValueModelTests.class);
		suite.addTestSuite(TransformationListValueModelTests.class);
		suite.addTestSuite(TransformationListValueModelTests.TransformerTests.class);
		suite.addTestSuite(TransformationModifiablePropertyValueModelTests.class);
		suite.addTestSuite(TransformationPropertyValueModelTests.class);

		return suite;
	}

	private JptCommonUtilityModelValueTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
