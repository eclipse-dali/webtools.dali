/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.listener;

import org.eclipse.jpt.utility.internal.ClassTools;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptUtilityModelListenerTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(ClassTools.packageNameFor(JptUtilityModelListenerTests.class));

		suite.addTestSuite(ReflectiveCollectionChangeListenerTests.class);
		suite.addTestSuite(ReflectiveListChangeListenerTests.class);
		suite.addTestSuite(ReflectivePropertyChangeListenerTests.class);
		suite.addTestSuite(ReflectiveStateChangeListenerTests.class);
		suite.addTestSuite(ReflectiveTreeChangeListenerTests.class);
	
		return suite;
	}
	
	private JptUtilityModelListenerTests() {
		super();
		throw new UnsupportedOperationException();
	}
	
}
