/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptUtilityModelValueSwingTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityModelValueSwingTests.class.getPackage().getName());

		suite.addTestSuite(CheckBoxModelAdapterTests.class);
		suite.addTestSuite(ComboBoxModelAdapterTests.class);
		suite.addTestSuite(DateSpinnerModelAdapterTests.class);
		suite.addTestSuite(DocumentAdapterTests.class);
		suite.addTestSuite(ListModelAdapterTests.class);
		suite.addTestSuite(ListSpinnerModelAdapterTests.class);
		suite.addTestSuite(NumberSpinnerModelAdapterTests.class);
		suite.addTestSuite(ObjectListSelectionModelTests.class);
		suite.addTestSuite(PrimitiveListTreeModelTests.class);
		suite.addTestSuite(RadioButtonModelAdapterTests.class);
		suite.addTestSuite(SpinnerModelAdapterTests.class);
		suite.addTestSuite(TableModelAdapterTests.class);
		suite.addTestSuite(TreeModelAdapterTests.class);
	
		return suite;
	}
	
	private JptUtilityModelValueSwingTests() {
		super();
		throw new UnsupportedOperationException();
	}
	
}
