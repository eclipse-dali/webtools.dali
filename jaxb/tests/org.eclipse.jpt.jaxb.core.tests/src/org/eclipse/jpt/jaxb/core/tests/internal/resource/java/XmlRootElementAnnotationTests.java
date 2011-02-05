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
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;

@SuppressWarnings("nls")
public class XmlRootElementAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ROOT_ELEMENT_NAME = "XmlRootElementName";
	private static final String XML_ROOT_ELEMENT_NAMESPACE = "XmlRootElementNamespace";

	public XmlRootElementAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlRootElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ROOT_ELEMENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRootElement");
			}
		});
	}

	private ICompilationUnit createTestXmlRootElementWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ROOT_ELEMENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRootElement(name = \"" + XML_ROOT_ELEMENT_NAME + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlRootElementWithNamespace() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ROOT_ELEMENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRootElement(namespace = \"" + XML_ROOT_ELEMENT_NAMESPACE + "\")");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlRootElementWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertTrue(xmlRootElementAnnotation != null);
		assertEquals(XML_ROOT_ELEMENT_NAME, xmlRootElementAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlRootElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertTrue(xmlRootElementAnnotation != null);
		assertNull(xmlRootElementAnnotation.getName());
		assertNull(xmlRootElementAnnotation.getNamespace());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlRootElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertNull(xmlRootElementAnnotation.getName());
		xmlRootElementAnnotation.setName(XML_ROOT_ELEMENT_NAME);
		assertEquals(XML_ROOT_ELEMENT_NAME, xmlRootElementAnnotation.getName());

		assertSourceContains("@XmlRootElement(name = \"" + XML_ROOT_ELEMENT_NAME + "\")", cu);

		xmlRootElementAnnotation.setName(null);
		assertNull(xmlRootElementAnnotation.getName());

		assertSourceContains("@XmlRootElement", cu);
		assertSourceDoesNotContain("@XmlRootElement(name = \"" + XML_ROOT_ELEMENT_NAME + "\")", cu);
	}

	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlRootElementWithNamespace();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertTrue(xmlRootElementAnnotation != null);
		assertEquals(XML_ROOT_ELEMENT_NAMESPACE, xmlRootElementAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlRootElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertNull(xmlRootElementAnnotation.getNamespace());
		xmlRootElementAnnotation.setNamespace(XML_ROOT_ELEMENT_NAMESPACE);
		assertEquals(XML_ROOT_ELEMENT_NAMESPACE, xmlRootElementAnnotation.getNamespace());

		assertSourceContains("@XmlRootElement(namespace = \"" + XML_ROOT_ELEMENT_NAMESPACE + "\")", cu);

		xmlRootElementAnnotation.setNamespace(null);
		assertNull(xmlRootElementAnnotation.getNamespace());

		assertSourceContains("@XmlRootElement", cu);
		assertSourceDoesNotContain("@XmlRootElement(namespace = \"" + XML_ROOT_ELEMENT_NAMESPACE + "\")", cu);
	}
}
