/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;

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
				return IteratorTools.iterator(JAXB.XML_ATTRIBUTE);
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
				return IteratorTools.iterator(JAXB.XML_ATTRIBUTE);
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
				return IteratorTools.iterator(JAXB.XML_ATTRIBUTE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAttribute(" + booleanElement + " = true)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttributeWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertTrue(xmlAttributeAnnotation != null);
		assertEquals(XML_ATTRIBUTE_NAME, xmlAttributeAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertTrue(xmlAttributeAnnotation != null);
		assertNull(xmlAttributeAnnotation.getName());
		assertNull(xmlAttributeAnnotation.getNamespace());
		assertNull(xmlAttributeAnnotation.getRequired());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertTrue(xmlAttributeAnnotation != null);
		assertEquals(XML_ATTRIBUTE_NAMESPACE, xmlAttributeAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);

		assertEquals(Boolean.TRUE, xmlAttributeAnnotation.getRequired());
	}

	public void testSetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttribute();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);

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
