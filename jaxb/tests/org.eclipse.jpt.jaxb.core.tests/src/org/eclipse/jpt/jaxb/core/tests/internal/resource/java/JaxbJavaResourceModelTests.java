/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JaxbJavaResourceModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbJavaResourceModelTests.class.getName());
		suite.addTestSuite(XmlAccessorOrderPackageAnnotationTests.class);
		suite.addTestSuite(XmlAccessorOrderTypeAnnotationTests.class);
		suite.addTestSuite(XmlAccessorTypePackageAnnotationTests.class);
		suite.addTestSuite(XmlAccessorTypeTypeAnnotationTests.class);
		suite.addTestSuite(XmlAnyAttributeAnnotationTests.class);
		suite.addTestSuite(XmlAnyElementAnnotationTests.class);
		suite.addTestSuite(XmlAttachmentRefAnnotationTests.class);
		suite.addTestSuite(XmlAttributeAnnotationTests.class);
		suite.addTestSuite(XmlElementAnnotationTests.class);
		suite.addTestSuite(XmlElementDeclAnnotationTests.class);
		suite.addTestSuite(XmlElementRefAnnotationTests.class);
		suite.addTestSuite(XmlElementWrapperAnnotationTests.class);
		suite.addTestSuite(XmlEnumAnnotationTests.class);
		suite.addTestSuite(XmlEnumValueAnnotationTests.class);
		suite.addTestSuite(XmlIDAnnotationTests.class);
		suite.addTestSuite(XmlIDREFAnnotationTests.class);
		suite.addTestSuite(XmlInlineBinaryDataAttributeAnnotationTests.class);
		suite.addTestSuite(XmlInlineBinaryDataTypeAnnotationTests.class);
		suite.addTestSuite(XmlJavaTypeAdapterPackageAnnotationTests.class);
		suite.addTestSuite(XmlJavaTypeAdapterTypeAnnotationTests.class);
		suite.addTestSuite(XmlListAnnotationTests.class);
		suite.addTestSuite(XmlMimeTypeAnnotationTests.class);
		suite.addTestSuite(XmlMixedAnnotationTests.class);
		suite.addTestSuite(XmlRegistryAnnotationTests.class);
		suite.addTestSuite(XmlRootElementAnnotationTests.class);
		suite.addTestSuite(XmlSchemaAnnotationTests.class);
		suite.addTestSuite(XmlSchemaTypeAttributeAnnotationTests.class);
		suite.addTestSuite(XmlSchemaTypePackageAnnotationTests.class);
		suite.addTestSuite(XmlSeeAlsoAnnotationTests.class);
		suite.addTestSuite(XmlTransientAttributeAnnotationTests.class);
		suite.addTestSuite(XmlTransientTypeAnnotationTests.class);
		suite.addTestSuite(XmlTypeAnnotationTests.class);
		suite.addTestSuite(XmlValueAnnotationTests.class);

		return suite;
	}

	private JaxbJavaResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
