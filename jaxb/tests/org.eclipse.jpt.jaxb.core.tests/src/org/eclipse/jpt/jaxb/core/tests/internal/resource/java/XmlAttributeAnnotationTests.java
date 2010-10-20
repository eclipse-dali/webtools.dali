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
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlAttributeAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ATTRIBUTE_NAME = "elementName";
	private static final String XML_ATTRIBUTE_NAMESPACE = "XmlAttributeNamespace";

	public XmlAttributeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlAttribute() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ATTRIBUTE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAttribute");
			}
		});
	}

	private ICompilationUnit createTestXmlAttributeWithName() throws Exception {
		return this.createTestXmlAttributeWithStringElement("name", XML_ATTRIBUTE_NAME);
	}

	private ICompilationUnit createTestXmlAttributeWithNamespace() throws Exception {
		return this.createTestXmlAttributeWithStringElement("namespace", XML_ATTRIBUTE_NAMESPACE);
	}

	private ICompilationUnit createTestXmlAttributeWithStringElement(final String element, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ATTRIBUTE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAttribute(" + element + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlAttributeWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ATTRIBUTE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAttribute(" + booleanElement + " = true)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttributeWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) attributeResource.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertTrue(xmlAttributeAnnotation != null);
		assertEquals(XML_ATTRIBUTE_NAME, xmlAttributeAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) attributeResource.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertTrue(xmlAttributeAnnotation != null);
		assertNull(xmlAttributeAnnotation.getName());
		assertNull(xmlAttributeAnnotation.getNamespace());
		assertNull(xmlAttributeAnnotation.getRequired());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) attributeResource.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertNull(xmlAttributeAnnotation.getName());
		xmlAttributeAnnotation.setName(XML_ATTRIBUTE_NAME);
		assertEquals(XML_ATTRIBUTE_NAME, xmlAttributeAnnotation.getName());

		assertSourceContains("@XmlAttribute(name = \"" + XML_ATTRIBUTE_NAME + "\")", cu);

		xmlAttributeAnnotation.setName(null);
		assertNull(xmlAttributeAnnotation.getName());

		assertSourceContains("@XmlAttribute", cu);
		assertSourceDoesNotContain("@XmlAttribute(name = \"" + XML_ATTRIBUTE_NAME + "\")", cu);
	}

	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttributeWithNamespace();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) attributeResource.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertTrue(xmlAttributeAnnotation != null);
		assertEquals(XML_ATTRIBUTE_NAMESPACE, xmlAttributeAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) attributeResource.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertNull(xmlAttributeAnnotation.getNamespace());
		xmlAttributeAnnotation.setNamespace(XML_ATTRIBUTE_NAMESPACE);
		assertEquals(XML_ATTRIBUTE_NAMESPACE, xmlAttributeAnnotation.getNamespace());

		assertSourceContains("@XmlAttribute(namespace = \"" + XML_ATTRIBUTE_NAMESPACE + "\")", cu);

		xmlAttributeAnnotation.setNamespace(null);
		assertNull(xmlAttributeAnnotation.getNamespace());

		assertSourceContains("@XmlAttribute", cu);
		assertSourceDoesNotContain("@XmlAttribute(namespace = \"" + XML_ATTRIBUTE_NAMESPACE + "\")", cu);
	}

	public void testGetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttributeWithBooleanElement("required");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) attributeResource.getAnnotation(JAXB.XML_ATTRIBUTE);

		assertEquals(Boolean.TRUE, xmlAttributeAnnotation.getRequired());
	}

	public void testSetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) attributeResource.getAnnotation(JAXB.XML_ATTRIBUTE);

		assertNotNull(xmlAttributeAnnotation);
		assertNull(xmlAttributeAnnotation.getRequired());

		xmlAttributeAnnotation.setRequired(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlAttributeAnnotation.getRequired());

		assertSourceContains("@XmlAttribute(required = false)", cu);

		xmlAttributeAnnotation.setRequired(null);
		assertSourceContains("@XmlAttribute", cu);
		assertSourceDoesNotContain("required", cu);
	}
}
