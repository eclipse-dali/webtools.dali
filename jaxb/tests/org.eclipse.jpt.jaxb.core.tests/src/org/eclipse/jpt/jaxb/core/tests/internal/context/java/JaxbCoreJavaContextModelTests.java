/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JaxbCoreJavaContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreJavaContextModelTests.class.getName());
		suite.addTestSuite(GenericJavaElementFactoryMethodTests.class);
		suite.addTestSuite(GenericJavaEnumConstantTests.class);
		suite.addTestSuite(GenericJavaPackageInfoTests.class);
		suite.addTestSuite(GenericJavaPersistentClassTests.class);
		suite.addTestSuite(GenericJavaPersistentEnumTests.class);
		suite.addTestSuite(GenericJavaRegistryTests.class);
		suite.addTestSuite(GenericJavaXmlJavaTypeAdapterTests.class);
		suite.addTestSuite(GenericJavaXmlRootElementTests.class);
		suite.addTestSuite(GenericJavaXmlSchemaTests.class);
		suite.addTestSuite(GenericJavaXmlSchemaTypeTests.class);
		return suite;
	}

	private JaxbCoreJavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
