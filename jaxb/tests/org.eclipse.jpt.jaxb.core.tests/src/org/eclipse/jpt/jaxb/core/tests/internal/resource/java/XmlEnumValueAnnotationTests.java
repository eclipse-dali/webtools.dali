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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumValueAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlEnumValueAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlEnumValueAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlEnumValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ENUM_VALUE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlEnumValue");
			}
		});
	}

	public void testGetXmlEnumValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlEnumValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlEnumValueAnnotation xmlEnumValueAnnotation = (XmlEnumValueAnnotation) attributeResource.getAnnotation(JAXB.XML_ENUM_VALUE);
		assertTrue(xmlEnumValueAnnotation != null);

		attributeResource.removeAnnotation(JAXB.XML_ENUM_VALUE);
		assertSourceDoesNotContain("@XmlEnumValue", cu);
	}
}
