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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;

@SuppressWarnings("nls")
public class XmlElementRefAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
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
				return IteratorTools.iterator(JAXB.XML_ELEMENT_REF);
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
				return IteratorTools.iterator(JAXB.XML_ELEMENT_REF);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef(" + element + " = \"" + value + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestXmlElementRefWithBooleanElement(final String booleanElement) throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_ELEMENT_REF);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef(" + booleanElement + " = true)");
			}
		});
	}
	
	private ICompilationUnit createTestXmlElementRefWithType() throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_ELEMENT_REF);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class)");
			}
		});
	}
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertNull(xmlElementRefAnnotation.getName());
		assertNull(xmlElementRefAnnotation.getNamespace());
		assertNull(xmlElementRefAnnotation.getType());
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());
	}
	
	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNull(xmlElementRefAnnotation.getName());
		
		xmlElementRefAnnotation.setName(XML_ELEMENT_REF_NAME);
		
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());
		assertSourceContains("@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME + "\")", cu);
		
		xmlElementRefAnnotation.setName(null);
		
		assertNull(xmlElementRefAnnotation.getName());
		assertSourceContains("@XmlElementRef", cu);
		assertSourceDoesNotContain("@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME + "\")", cu);
	}
	
	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithNamespace();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_NAMESPACE, xmlElementRefAnnotation.getNamespace());
	}
	
	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNull(xmlElementRefAnnotation.getNamespace());
		
		xmlElementRefAnnotation.setNamespace(XML_ELEMENT_REF_NAMESPACE);
		
		assertEquals(XML_ELEMENT_REF_NAMESPACE, xmlElementRefAnnotation.getNamespace());
		assertSourceContains("@XmlElementRef(namespace = \"" + XML_ELEMENT_REF_NAMESPACE + "\")", cu);
		
		xmlElementRefAnnotation.setNamespace(null);
		
		assertNull(xmlElementRefAnnotation.getNamespace());
		assertSourceContains("@XmlElementRef", cu);
		assertSourceDoesNotContain("@XmlElementRef(namespace = \"" + XML_ELEMENT_REF_NAMESPACE + "\")", cu);
	}
	
	public void testGetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithBooleanElement("required");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertEquals(Boolean.TRUE, xmlElementRefAnnotation.getRequired());
	}
	
	public void testSetRequired() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNotNull(xmlElementRefAnnotation);
		assertNull(xmlElementRefAnnotation.getRequired());
		
		xmlElementRefAnnotation.setRequired(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, xmlElementRefAnnotation.getRequired());
		assertSourceContains("@XmlElementRef(required = false)", cu);
		
		xmlElementRefAnnotation.setRequired(null);
		
		assertSourceContains("@XmlElementRef", cu);
		assertSourceDoesNotContain("required", cu);
	}
	
	public void testGetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getType());
		assertEquals("java.lang." + XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getFullyQualifiedTypeName());
	}
	
	public void testSetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNull(xmlElementRefAnnotation.getType());
		
		xmlElementRefAnnotation.setType(XML_ELEMENT_REF_TYPE);
		
		assertEquals(XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getType());
		assertSourceContains("@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class", cu);
		
		xmlElementRefAnnotation.setType(null);
		
		assertNull(xmlElementRefAnnotation.getType());
		assertSourceContains("@XmlElementRef", cu);
		assertSourceDoesNotContain("@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class", cu);
	}
}
