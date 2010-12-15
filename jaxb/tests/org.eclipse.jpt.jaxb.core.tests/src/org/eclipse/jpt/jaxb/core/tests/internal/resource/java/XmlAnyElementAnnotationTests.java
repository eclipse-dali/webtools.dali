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
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyElementAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlAnyElementAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ANY_ELEMENT_VALUE = "String";

	public XmlAnyElementAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlAnyElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ANY_ELEMENT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAnyElement");
			}
		});
	}

	private ICompilationUnit createTestXmlAnyElementWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ANY_ELEMENT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAnyElement(" + booleanElement + " = true)");
			}
		});
	}

	private ICompilationUnit createTestXmlAnyElementWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ANY_ELEMENT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAnyElement(value = " + XML_ANY_ELEMENT_VALUE  + ".class)");
			}
		});
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAnyElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAnyElementAnnotation xmlAnyElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);
		assertTrue(xmlAnyElementAnnotation != null);
		assertNull(xmlAnyElementAnnotation.getLax());
		assertNull(xmlAnyElementAnnotation.getValue());
	}

	public void testGetLax() throws Exception {
		ICompilationUnit cu = this.createTestXmlAnyElementWithBooleanElement("lax");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlAnyElementAnnotation xmlAnyElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);

		assertEquals(Boolean.TRUE, xmlAnyElementAnnotation.getLax());
	}

	public void testSetLax() throws Exception {
		ICompilationUnit cu = this.createTestXmlAnyElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlAnyElementAnnotation xmlAnyElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);

		assertNotNull(xmlAnyElementAnnotation);
		assertNull(xmlAnyElementAnnotation.getLax());

		xmlAnyElementAnnotation.setLax(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlAnyElementAnnotation.getLax());

		assertSourceContains("@XmlAnyElement(lax = false)", cu);

		xmlAnyElementAnnotation.setLax(null);
		assertSourceContains("@XmlAnyElement", cu);
		assertSourceDoesNotContain("lax", cu);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAnyElementWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAnyElementAnnotation xmlAnyElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);
		assertTrue(xmlAnyElementAnnotation != null);
		assertEquals(XML_ANY_ELEMENT_VALUE, xmlAnyElementAnnotation.getValue());
		assertEquals("java.lang." + XML_ANY_ELEMENT_VALUE, xmlAnyElementAnnotation.getFullyQualifiedValueClassName());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAnyElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAnyElementAnnotation xmlAnyElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);
		assertNull(xmlAnyElementAnnotation.getValue());
		xmlAnyElementAnnotation.setValue(XML_ANY_ELEMENT_VALUE);
		assertEquals(XML_ANY_ELEMENT_VALUE, xmlAnyElementAnnotation.getValue());

		assertSourceContains("@XmlAnyElement(" + XML_ANY_ELEMENT_VALUE  + ".class)", cu);

		xmlAnyElementAnnotation.setValue(null);
		assertNull(xmlAnyElementAnnotation.getValue());

		assertSourceContains("@XmlAnyElement", cu);
		assertSourceDoesNotContain("@XmlAnyElement(value = " + XML_ANY_ELEMENT_VALUE  + ".class)", cu);
	}
}
