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
	
	private static final String XML_ENUM_VALUE_VALUE = "myEnumValue";

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

	private ICompilationUnit createTestXmlEnumValueWithValue() throws Exception {
		return this.createTestXmlEnumValueWithStringElement("value", XML_ENUM_VALUE_VALUE);
	}

	private ICompilationUnit createTestXmlEnumValueWithStringElement(final String element, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ENUM_VALUE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlEnumValue(" + element + " = \"" + value + "\")");
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


	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlEnumValueWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlEnumValueAnnotation xmlEnumValueAnnotation = (XmlEnumValueAnnotation) attributeResource.getAnnotation(JAXB.XML_ENUM_VALUE);
		assertTrue(xmlEnumValueAnnotation != null);
		assertEquals(XML_ENUM_VALUE_VALUE, xmlEnumValueAnnotation.getValue());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlEnumValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlEnumValueAnnotation xmlEnumValueAnnotation = (XmlEnumValueAnnotation) attributeResource.getAnnotation(JAXB.XML_ENUM_VALUE);
		assertTrue(xmlEnumValueAnnotation != null);
		assertNull(xmlEnumValueAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlEnumValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlEnumValueAnnotation xmlEnumValueAnnotation = (XmlEnumValueAnnotation) attributeResource.getAnnotation(JAXB.XML_ENUM_VALUE);
		assertNull(xmlEnumValueAnnotation.getValue());
		xmlEnumValueAnnotation.setValue(XML_ENUM_VALUE_VALUE);
		assertEquals(XML_ENUM_VALUE_VALUE, xmlEnumValueAnnotation.getValue());

		assertSourceContains("@XmlEnumValue(\"" + XML_ENUM_VALUE_VALUE + "\")", cu);

		xmlEnumValueAnnotation.setValue(null);
		assertNull(xmlEnumValueAnnotation.getValue());

		assertSourceDoesNotContain("@XmlEnumValue", cu);
	}
}
