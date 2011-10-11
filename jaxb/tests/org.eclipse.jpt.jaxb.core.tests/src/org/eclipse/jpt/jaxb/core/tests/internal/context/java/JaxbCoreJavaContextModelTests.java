/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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

public class JaxbCoreJavaContextModelTests
		extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreJavaContextModelTests.class.getName());
		suite.addTestSuite(GenericJavaElementFactoryMethodTests.class);
		suite.addTestSuite(GenericJavaEnumConstantTests.class);
		suite.addTestSuite(GenericJavaPackageInfoTests.class);
		suite.addTestSuite(GenericJavaPersistentAttributeTests.class);
		suite.addTestSuite(GenericJavaClassMappingTests.class);
		suite.addTestSuite(GenericJavaEnumMappingTests.class);
		suite.addTestSuite(GenericJavaRegistryTests.class);
		suite.addTestSuite(GenericJavaXmlAdapterTests.class);
		suite.addTestSuite(GenericJavaXmlAnyAttributeMappingTests.class);
		suite.addTestSuite(GenericJavaXmlAnyElementMappingTests.class);
		suite.addTestSuite(GenericJavaXmlAttributeMappingTests.class);
		suite.addTestSuite(GenericJavaXmlElementMappingTests.class);
		suite.addTestSuite(GenericJavaXmlElementRefMappingTests.class);
		suite.addTestSuite(GenericJavaXmlElementRefsMappingTests.class);
		suite.addTestSuite(GenericJavaXmlElementsMappingTests.class);
		suite.addTestSuite(GenericJavaPackageXmlJavaTypeAdapterTests.class);
		suite.addTestSuite(GenericJavaTypeXmlJavaTypeAdapterTests.class);
		suite.addTestSuite(GenericJavaAttributeXmlJavaTypeAdapterTests.class);
		suite.addTestSuite(GenericJavaXmlRootElementTests.class);
		suite.addTestSuite(GenericJavaXmlSchemaTests.class);
		suite.addTestSuite(GenericJavaXmlSchemaTypeTests.class);
		suite.addTestSuite(GenericJavaXmlSeeAlsoTests.class);
		suite.addTestSuite(GenericJavaXmlValueMappingTests.class);
		return suite;
	}
	
	
	private JaxbCoreJavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
