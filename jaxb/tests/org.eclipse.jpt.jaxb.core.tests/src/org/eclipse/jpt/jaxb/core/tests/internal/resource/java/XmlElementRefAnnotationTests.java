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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlElementRefAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ELEMENT_REF_NAME = "elementName";
	private static final String XML_ELEMENT_REF_NAMESPACE = "XmlElementRefNamespace";
	private static final String XML_ELEMENT_REF_TYPE = "String";

	public XmlElementRefAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlElementRef() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_REF);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef");
			}
		});
	}

	private ICompilationUnit createTestXmlElementRefWithName() throws Exception {
		return this.createTestXmlElementRefWithStringElement("name", XML_ELEMENT_REF_NAME);
	}

	private ICompilationUnit createTestXmlElementRefWithNamespace() throws Exception {
		return this.createTestXmlElementRefWithStringElement("namespace", XML_ELEMENT_REF_NAMESPACE);
	}

	private ICompilationUnit createTestXmlElementRefWithStringElement(final String element, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_REF);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef(" + element + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlElementRefWithType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_REF);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertTrue(xmlElementRefAnnotation != null);
		assertNull(xmlElementRefAnnotation.getName());
		assertNull(xmlElementRefAnnotation.getNamespace());
		assertNull(xmlElementRefAnnotation.getType());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertNull(xmlElementRefAnnotation.getName());
		xmlElementRefAnnotation.setName(XML_ELEMENT_REF_NAME);
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());

		assertSourceContains("@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME + "\")", cu);
	}

	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());

		xmlElementRefAnnotation.setName(null);
		assertNull(xmlElementRefAnnotation.getName());

		assertSourceContains("@XmlElementRef", cu);
		assertSourceDoesNotContain("@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME + "\")", cu);
	}

	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithNamespace();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_NAMESPACE, xmlElementRefAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertNull(xmlElementRefAnnotation.getNamespace());
		xmlElementRefAnnotation.setNamespace(XML_ELEMENT_REF_NAMESPACE);
		assertEquals(XML_ELEMENT_REF_NAMESPACE, xmlElementRefAnnotation.getNamespace());

		assertSourceContains("@XmlElementRef(namespace = \"" + XML_ELEMENT_REF_NAMESPACE + "\")", cu);
	}

	public void testSetNamespaceNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithNamespace();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertEquals(XML_ELEMENT_REF_NAMESPACE, xmlElementRefAnnotation.getNamespace());

		xmlElementRefAnnotation.setNamespace(null);
		assertNull(xmlElementRefAnnotation.getNamespace());

		assertSourceContains("@XmlElementRef", cu);
		assertSourceDoesNotContain("@XmlElementRef(namespace = \"" + XML_ELEMENT_REF_NAMESPACE + "\")", cu);
	}

	public void testGetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getType());
		assertEquals("java.lang." + XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getFullyQualifiedTypeName());
	}

	public void testSetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertNull(xmlElementRefAnnotation.getType());
		xmlElementRefAnnotation.setType(XML_ELEMENT_REF_TYPE);
		assertEquals(XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getType());

		assertSourceContains("@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class", cu);
	}

	public void testSetTypeNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertEquals(XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getType());

		xmlElementRefAnnotation.setType(null);
		assertNull(xmlElementRefAnnotation.getType());

		assertSourceContains("@XmlElementRef", cu);
		assertSourceDoesNotContain("@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class", cu);
	}

	public void testAddXmlElementRefAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());

		XmlElementRefAnnotation xmlElementRefAnnotation2 = (XmlElementRefAnnotation) attributeResource.addAnnotation(1, JAXB.XML_ELEMENT_REF, JAXB.XML_ELEMENT_REFS);
		xmlElementRefAnnotation2.setName("Foo");
		assertSourceContains("@XmlElementRefs({@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME + "\"),@XmlElementRef(name = \"Foo\")})", cu);
	}
}
