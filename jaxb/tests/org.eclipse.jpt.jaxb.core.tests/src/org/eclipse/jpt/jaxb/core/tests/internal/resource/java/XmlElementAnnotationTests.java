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
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;

@SuppressWarnings("nls")
public class XmlElementAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
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
	
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertTrue(xmlElementAnnotation != null);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElementAnnotation.getNamespace());
		assertNull(xmlElementAnnotation.getDefaultValue());
		assertNull(xmlElementAnnotation.getNillable());
		assertNull(xmlElementAnnotation.getRequired());
		assertNull(xmlElementAnnotation.getType());
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_NAME, xmlElementAnnotation.getName());
	}
	
	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertNull(xmlElementAnnotation.getName());
		
		xmlElementAnnotation.setName(XML_ELEMENT_NAME);
		
		assertEquals(XML_ELEMENT_NAME, xmlElementAnnotation.getName());
		assertSourceContains("@XmlElement(name = \"" + XML_ELEMENT_NAME + "\")", cu);
		
		xmlElementAnnotation.setName(null);
		
		assertNull(xmlElementAnnotation.getName());
		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(name = \"" + XML_ELEMENT_NAME + "\")", cu);
	}
	
	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithNamespace();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_NAMESPACE, xmlElementAnnotation.getNamespace());
	}
	
	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertNull(xmlElementAnnotation.getNamespace());
		
		xmlElementAnnotation.setNamespace(XML_ELEMENT_NAMESPACE);
		
		assertEquals(XML_ELEMENT_NAMESPACE, xmlElementAnnotation.getNamespace());
		assertSourceContains("@XmlElement(namespace = \"" + XML_ELEMENT_NAMESPACE + "\")", cu);
		
		xmlElementAnnotation.setNamespace(null);
		
		assertNull(xmlElementAnnotation.getNamespace());
		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(namespace = \"" + XML_ELEMENT_NAMESPACE + "\")", cu);
	}
	
	public void testGetDefaultValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithDefaultValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_DEFAULT_VALUE, xmlElementAnnotation.getDefaultValue());
	}
	
	public void testSetDefaultValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertNull(xmlElementAnnotation.getDefaultValue());
		
		xmlElementAnnotation.setDefaultValue(XML_ELEMENT_DEFAULT_VALUE);
		
		assertEquals(XML_ELEMENT_DEFAULT_VALUE, xmlElementAnnotation.getDefaultValue());
		assertSourceContains("@XmlElement(defaultValue = \"" + XML_ELEMENT_DEFAULT_VALUE + "\")", cu);
		
		xmlElementAnnotation.setDefaultValue(null);
		
		assertNull(xmlElementAnnotation.getDefaultValue());
		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(defaultValue = \"" + XML_ELEMENT_DEFAULT_VALUE + "\")", cu);
	}
	
	public void testGetNillable() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementWithBooleanElement("nillable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertEquals(Boolean.TRUE, xmlElementAnnotation.getNillable());
	}
	
	public void testSetNillable() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertEquals(Boolean.TRUE, xmlElementAnnotation.getRequired());
	}
	
	public void testSetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertTrue(xmlElementAnnotation != null);
		assertEquals(XML_ELEMENT_TYPE, xmlElementAnnotation.getType());
		assertEquals("java.lang." + XML_ELEMENT_TYPE, xmlElementAnnotation.getFullyQualifiedTypeName());
	}
	
	public void testSetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElement();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		
		assertNull(xmlElementAnnotation.getType());
		
		xmlElementAnnotation.setType(XML_ELEMENT_TYPE);
		
		assertEquals(XML_ELEMENT_TYPE, xmlElementAnnotation.getType());
		assertSourceContains("@XmlElement(type = " + XML_ELEMENT_TYPE  + ".class", cu);
		
		xmlElementAnnotation.setType(null);
		
		assertNull(xmlElementAnnotation.getType());
		assertSourceContains("@XmlElement", cu);
		assertSourceDoesNotContain("@XmlElement(type = " + XML_ELEMENT_TYPE  + ".class", cu);
	}
}
