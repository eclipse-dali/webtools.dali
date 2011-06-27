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
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;

@SuppressWarnings("nls")
public class XmlEnumAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ENUM_JAVA_TYPE = "String";

	public XmlEnumAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlEnum() throws Exception {
		return this.createTestEnum(new DefaultEnumAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ENUM);
			}
			@Override
			public void appendEnumAnnotationTo(StringBuilder sb) {
				sb.append("@XmlEnum");
			}
		});
	}

	private ICompilationUnit createTestXmlEnumWithValue() throws Exception {
		return this.createTestEnum(new DefaultEnumAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ENUM);
			}
			@Override
			public void appendEnumAnnotationTo(StringBuilder sb) {
				sb.append("@XmlEnum(value = " + XML_ENUM_JAVA_TYPE  + ".class)");
			}
		});
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlEnum();
		JavaResourceEnum resourceEnum = this.buildJavaResourceEnum(cu); 

		XmlEnumAnnotation xmlEnumAnnotation = (XmlEnumAnnotation) resourceEnum.getAnnotation(JAXB.XML_ENUM);
		assertTrue(xmlEnumAnnotation != null);
		assertNull(xmlEnumAnnotation.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlEnumWithValue();
		JavaResourceEnum resourceEnum = this.buildJavaResourceEnum(cu); 

		XmlEnumAnnotation xmlEnumAnnotation = (XmlEnumAnnotation) resourceEnum.getAnnotation(JAXB.XML_ENUM);
		assertTrue(xmlEnumAnnotation != null);
		assertEquals(XML_ENUM_JAVA_TYPE, xmlEnumAnnotation.getValue());
		assertEquals("java.lang." + XML_ENUM_JAVA_TYPE, xmlEnumAnnotation.getFullyQualifiedValueClassName());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlEnum();
		JavaResourceEnum resourceEnum = this.buildJavaResourceEnum(cu); 

		XmlEnumAnnotation xmlEnumAnnotation = (XmlEnumAnnotation) resourceEnum.getAnnotation(JAXB.XML_ENUM);
		assertNull(xmlEnumAnnotation.getValue());
		xmlEnumAnnotation.setValue(XML_ENUM_JAVA_TYPE);
		assertEquals(XML_ENUM_JAVA_TYPE, xmlEnumAnnotation.getValue());

		assertSourceContains("@XmlEnum(" + XML_ENUM_JAVA_TYPE  + ".class)", cu);

		xmlEnumAnnotation.setValue(null);
		assertNull(xmlEnumAnnotation.getValue());

		assertSourceContains("@XmlEnum", cu);
	}
}
