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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;

@SuppressWarnings("nls")
public class XmlElementWrapperAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ELEMENT_WRAPPER_NAME = "elementName";
	private static final String XML_ELEMENT_WRAPPER_NAMESPACE = "XmlElementWrapperNamespace";

	public XmlElementWrapperAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlElementWrapper() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_WRAPPER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementWrapper");
			}
		});
	}

	private ICompilationUnit createTestXmlElementWrapperWithName() throws Exception {
		return this.createTestXmlElementWrapperWithStringElement("name", XML_ELEMENT_WRAPPER_NAME);
	}

	private ICompilationUnit createTestXmlElementWrapperWithNamespace() throws Exception {
		return this.createTestXmlElementWrapperWithStringElement("namespace", XML_ELEMENT_WRAPPER_NAMESPACE);
	}

	private ICompilationUnit createTestXmlElementWrapperWithStringElement(final String element, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_WRAPPER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementWrapper(" + element + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlElementWrapperWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_WRAPPER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementWrapper(" + booleanElement + " = true)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapperWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertTrue(xmlElementWrapperAnnotation != null);
		assertEquals(XML_ELEMENT_WRAPPER_NAME, xmlElementWrapperAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapper();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertTrue(xmlElementWrapperAnnotation != null);
		assertNull(xmlElementWrapperAnnotation.getName());
		assertNull(xmlElementWrapperAnnotation.getNamespace());
		assertNull(xmlElementWrapperAnnotation.getNillable());
		assertNull(xmlElementWrapperAnnotation.getRequired());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapper();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementWrapperAnnotation.getName());
		xmlElementWrapperAnnotation.setName(XML_ELEMENT_WRAPPER_NAME);
		assertEquals(XML_ELEMENT_WRAPPER_NAME, xmlElementWrapperAnnotation.getName());

		assertSourceContains("@XmlElementWrapper(name = \"" + XML_ELEMENT_WRAPPER_NAME + "\")", cu);

		xmlElementWrapperAnnotation.setName(null);
		assertNull(xmlElementWrapperAnnotation.getName());

		assertSourceContains("@XmlElementWrapper", cu);
		assertSourceDoesNotContain("@XmlElementWrapper(name = \"" + XML_ELEMENT_WRAPPER_NAME + "\")", cu);
	}

	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapperWithNamespace();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertTrue(xmlElementWrapperAnnotation != null);
		assertEquals(XML_ELEMENT_WRAPPER_NAMESPACE, xmlElementWrapperAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapper();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementWrapperAnnotation.getNamespace());
		xmlElementWrapperAnnotation.setNamespace(XML_ELEMENT_WRAPPER_NAMESPACE);
		assertEquals(XML_ELEMENT_WRAPPER_NAMESPACE, xmlElementWrapperAnnotation.getNamespace());

		assertSourceContains("@XmlElementWrapper(namespace = \"" + XML_ELEMENT_WRAPPER_NAMESPACE + "\")", cu);

		xmlElementWrapperAnnotation.setNamespace(null);
		assertNull(xmlElementWrapperAnnotation.getNamespace());

		assertSourceContains("@XmlElementWrapper", cu);
		assertSourceDoesNotContain("@XmlElementWrapper(namespace = \"" + XML_ELEMENT_WRAPPER_NAMESPACE + "\")", cu);
	}

	public void testGetNillable() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapperWithBooleanElement("nillable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);

		assertEquals(Boolean.TRUE, xmlElementWrapperAnnotation.getNillable());
	}

	public void testSetNillable() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapper();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);

		assertNotNull(xmlElementWrapperAnnotation);
		assertNull(xmlElementWrapperAnnotation.getNillable());

		xmlElementWrapperAnnotation.setNillable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlElementWrapperAnnotation.getNillable());

		assertSourceContains("@XmlElementWrapper(nillable = false)", cu);

		xmlElementWrapperAnnotation.setNillable(null);
		assertSourceContains("@XmlElementWrapper", cu);
		assertSourceDoesNotContain("nillable", cu);
	}

	public void testGetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapperWithBooleanElement("required");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);

		assertEquals(Boolean.TRUE, xmlElementWrapperAnnotation.getRequired());
	}

	public void testSetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWrapper();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);

		assertNotNull(xmlElementWrapperAnnotation);
		assertNull(xmlElementWrapperAnnotation.getRequired());

		xmlElementWrapperAnnotation.setRequired(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlElementWrapperAnnotation.getRequired());

		assertSourceContains("@XmlElementWrapper(required = false)", cu);

		xmlElementWrapperAnnotation.setRequired(null);
		assertSourceContains("@XmlElementWrapper", cu);
		assertSourceDoesNotContain("required", cu);
	}
}
