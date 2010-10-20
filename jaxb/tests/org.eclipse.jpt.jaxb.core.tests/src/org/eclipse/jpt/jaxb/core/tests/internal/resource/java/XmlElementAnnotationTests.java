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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlElementAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ELEMENT_NAME = "elementName";
	private static final String XML_ELEMENT_NAMESPACE = "XmlElementNamespace";
	private static final String XML_ELEMENT_DEFAULT_VALUE = "myDefaultValue";
	private static final String XML_ELEMENT_TYPE = "String";

	public XmlElementAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElement");
			}
		});
	}

	private ICompilationUnit createTestXmlElementWithName() throws Exception {
		return this.createTestXmlElementWithStringElement("name", XML_ELEMENT_NAME);
	}

	private ICompilationUnit createTestXmlElementWithNamespace() throws Exception {
		return this.createTestXmlElementWithStringElement("namespace", XML_ELEMENT_NAMESPACE);
	}

	private ICompilationUnit createTestXmlElementWithDefaultValue() throws Exception {
		return this.createTestXmlElementWithStringElement("defaultValue", XML_ELEMENT_DEFAULT_VALUE);
	}

	private ICompilationUnit createTestXmlElementWithStringElement(final String element, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElement(" + element + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlElementWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElement(" + booleanElement + " = true)");
			}
		});
	}

	private ICompilationUnit createTestXmlElementWithType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElement(type = " + XML_ELEMENT_TYPE  + ".class)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_NAME, xmlElementAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertTrue(xmlElementAnnotation != null);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElementAnnotation.getNamespace());
		assertNull(xmlElementAnnotation.getDefaultValue());
		assertNull(xmlElementAnnotation.getNillable());
		assertNull(xmlElementAnnotation.getRequired());
		assertNull(xmlElementAnnotation.getType());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getName());
		xmlElementAnnotation.setName(XML_ELEMENT_NAME);
		assertEquals(XML_ELEMENT_NAME, xmlElementAnnotation.getName());

		assertSourceContains("@XmlElement(name = \"" + XML_ELEMENT_NAME + "\")", cu);
	}

	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals(XML_ELEMENT_NAME, xmlElementAnnotation.getName());

		xmlElementAnnotation.setName(null);
		assertNull(xmlElementAnnotation.getName());

		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(name = \"" + XML_ELEMENT_NAME + "\")", cu);
	}

	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithNamespace();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_NAMESPACE, xmlElementAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getNamespace());
		xmlElementAnnotation.setNamespace(XML_ELEMENT_NAMESPACE);
		assertEquals(XML_ELEMENT_NAMESPACE, xmlElementAnnotation.getNamespace());

		assertSourceContains("@XmlElement(namespace = \"" + XML_ELEMENT_NAMESPACE + "\")", cu);
	}

	public void testSetNamespaceNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithNamespace();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals(XML_ELEMENT_NAMESPACE, xmlElementAnnotation.getNamespace());

		xmlElementAnnotation.setNamespace(null);
		assertNull(xmlElementAnnotation.getNamespace());

		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(namespace = \"" + XML_ELEMENT_NAMESPACE + "\")", cu);
	}

	public void testGetDefaultValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithDefaultValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_DEFAULT_VALUE, xmlElementAnnotation.getDefaultValue());
	}

	public void testSetDefaultValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getDefaultValue());
		xmlElementAnnotation.setDefaultValue(XML_ELEMENT_DEFAULT_VALUE);
		assertEquals(XML_ELEMENT_DEFAULT_VALUE, xmlElementAnnotation.getDefaultValue());

		assertSourceContains("@XmlElement(defaultValue = \"" + XML_ELEMENT_DEFAULT_VALUE + "\")", cu);
	}

	public void testSetDefaultValueNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithDefaultValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals(XML_ELEMENT_DEFAULT_VALUE, xmlElementAnnotation.getDefaultValue());

		xmlElementAnnotation.setDefaultValue(null);
		assertNull(xmlElementAnnotation.getDefaultValue());

		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(defaultValue = \"" + XML_ELEMENT_DEFAULT_VALUE + "\")", cu);
	}

	public void testGetNillable() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithBooleanElement("nillable");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);

		assertEquals(Boolean.TRUE, xmlElementAnnotation.getNillable());
	}

	public void testSetNillable() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);

		assertNotNull(xmlElementAnnotation);
		assertNull(xmlElementAnnotation.getNillable());

		xmlElementAnnotation.setNillable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlElementAnnotation.getNillable());

		assertSourceContains("@XmlElement(nillable = false)", cu);

		xmlElementAnnotation.setNillable(null);
		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("nillable", cu);
	}

	public void testGetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithBooleanElement("required");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);

		assertEquals(Boolean.TRUE, xmlElementAnnotation.getRequired());
	}

	public void testSetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);

		assertNotNull(xmlElementAnnotation);
		assertNull(xmlElementAnnotation.getRequired());

		xmlElementAnnotation.setRequired(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlElementAnnotation.getRequired());

		assertSourceContains("@XmlElement(required = false)", cu);

		xmlElementAnnotation.setRequired(null);
		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("required", cu);
	}

	public void testGetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_TYPE, xmlElementAnnotation.getType());
		assertEquals("java.lang." + XML_ELEMENT_TYPE, xmlElementAnnotation.getFullyQualifiedTypeName());
	}

	public void testSetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getType());
		xmlElementAnnotation.setType(XML_ELEMENT_TYPE);
		assertEquals(XML_ELEMENT_TYPE, xmlElementAnnotation.getType());

		assertSourceContains("@XmlElement(type = " + XML_ELEMENT_TYPE  + ".class", cu);
	}

	public void testSetTypeNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals(XML_ELEMENT_TYPE, xmlElementAnnotation.getType());

		xmlElementAnnotation.setType(null);
		assertNull(xmlElementAnnotation.getType());

		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(type = " + XML_ELEMENT_TYPE  + ".class", cu);
	}

	public void testAddXmlElementAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) attributeResource.getAnnotation(JAXB.XML_ELEMENT);
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_NAME, xmlElementAnnotation.getName());

		XmlElementAnnotation xmlElementAnnotation2 = (XmlElementAnnotation) attributeResource.addAnnotation(1, JAXB.XML_ELEMENT, JAXB.XML_ELEMENTS);
		xmlElementAnnotation2.setName("Foo");
		assertSourceContains("@XmlElements({@XmlElement(name = \"" + XML_ELEMENT_NAME + "\"),@XmlElement(name = \"Foo\")})", cu);
	}
}
