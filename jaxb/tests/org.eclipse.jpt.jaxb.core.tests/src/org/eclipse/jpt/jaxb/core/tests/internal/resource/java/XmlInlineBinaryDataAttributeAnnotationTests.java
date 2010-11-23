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

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlInlineBinaryDataAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlInlineBinaryDataAttributeAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlInlineBinaryDataAttributeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlInlineBinaryData() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_INLINE_BINARY_DATA);
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@XmlInlineBinaryData");
			}
		});
	}

	public void testGetXmlInlineBinaryData() throws Exception {
		ICompilationUnit cu = this.createTestXmlInlineBinaryData();
		JavaResourceType resourceType = this.buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = this.getMethod(resourceType, 0);

		XmlInlineBinaryDataAnnotation xmlInlineBinaryDataAnnotation = (XmlInlineBinaryDataAnnotation) resourceAttribute.getAnnotation(JAXB.XML_INLINE_BINARY_DATA);
		assertTrue(xmlInlineBinaryDataAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_INLINE_BINARY_DATA);
		assertSourceDoesNotContain("@XmlInlineBinaryData", cu);
	}
}
