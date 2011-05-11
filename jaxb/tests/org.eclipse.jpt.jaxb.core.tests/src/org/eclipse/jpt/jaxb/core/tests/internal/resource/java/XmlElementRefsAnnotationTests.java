/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;


public class XmlElementRefsAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	private static final String XML_ELEMENT_REF_NAME = "elementName";
	private static final String XML_ELEMENT_REF_NAMESPACE = "XmlElementRefNamespace";
	private static final String XML_ELEMENT_REF_TYPE = "String";
	
	
	public XmlElementRefsAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestXmlElementRef() throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_REF, JAXB.XML_ELEMENT_REFS);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRefs(@XmlElementRef)");
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
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_REF, JAXB.XML_ELEMENT_REFS);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRefs(@XmlElementRef(" + element + " = \"" + value + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestXmlElementRefWithType() throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_REF, JAXB.XML_ELEMENT_REFS);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRefs(@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class))");
			}
		});
	}
	
	private ICompilationUnit createTestXmlElementRefWithAll() throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_REF, JAXB.XML_ELEMENT_REFS);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRefs(@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME + "\", namespace = \"" + XML_ELEMENT_REF_NAMESPACE + "\", type = " + XML_ELEMENT_REF_TYPE  + ".class))");
			}
		});
	}
	
	
	private XmlElementRefAnnotation getXmlElementRefAnnotation(JavaResourceAttribute resourceAttribute) {
		XmlElementRefsAnnotation refsAnnotation 
				= (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		return refsAnnotation.xmlElementRefAt(0);
	}
	
	private XmlElementRefAnnotation addXmlElementRefAnnotation(int index, JavaResourceAttribute resourceAttribute) {
		XmlElementRefsAnnotation refsAnnotation 
				= (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		return refsAnnotation.addXmlElementRef(index);
	}
	
	private void removeXmlElementRefAnnotation(int index, JavaResourceAttribute resourceAttribute) {
		XmlElementRefsAnnotation refsAnnotation 
				= (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		refsAnnotation.removeXmlElementRef(index);
	}
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation(resourceAttribute);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertNull(xmlElementRefAnnotation.getName());
		assertNull(xmlElementRefAnnotation.getNamespace());
		assertNull(xmlElementRefAnnotation.getType());
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation(resourceAttribute);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation(resourceAttribute);
		
		assertNull(xmlElementRefAnnotation.getName());
		
		xmlElementRefAnnotation.setName(XML_ELEMENT_REF_NAME);
		assertEquals(XML_ELEMENT_REF_NAME, xmlElementRefAnnotation.getName());

		assertSourceContains("@XmlElementRefs(@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME + "\"))", cu);

		xmlElementRefAnnotation.setName(null);
		assertNull(xmlElementRefAnnotation.getName());

		assertSourceContains("@XmlElementRefs(@XmlElementRef)", cu);
	}
	
	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithNamespace();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation(resourceAttribute);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_NAMESPACE, xmlElementRefAnnotation.getNamespace());
	}
	
	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation(resourceAttribute);
		
		assertNull(xmlElementRefAnnotation.getNamespace());
		
		xmlElementRefAnnotation.setNamespace(XML_ELEMENT_REF_NAMESPACE);
		
		assertEquals(XML_ELEMENT_REF_NAMESPACE, xmlElementRefAnnotation.getNamespace());
		assertSourceContains("@XmlElementRefs(@XmlElementRef(namespace = \"" + XML_ELEMENT_REF_NAMESPACE + "\"))", cu);
		
		xmlElementRefAnnotation.setNamespace(null);
		
		assertNull(xmlElementRefAnnotation.getNamespace());
		assertSourceContains("@XmlElementRefs(@XmlElementRef)", cu);
	}
	
	public void testGetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation(resourceAttribute);
		
		assertTrue(xmlElementRefAnnotation != null);
		assertEquals(XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getType());
		assertEquals("java.lang." + XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getFullyQualifiedTypeName());
	}
	
	public void testSetType() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlElementRefAnnotation xmlElementRefAnnotation = getXmlElementRefAnnotation(resourceAttribute);
		
		assertNull(xmlElementRefAnnotation.getType());
		
		xmlElementRefAnnotation.setType(XML_ELEMENT_REF_TYPE);
		
		assertEquals(XML_ELEMENT_REF_TYPE, xmlElementRefAnnotation.getType());
		assertSourceContains("@XmlElementRefs(@XmlElementRef(type = " + XML_ELEMENT_REF_TYPE  + ".class))", cu);
		
		xmlElementRefAnnotation.setType(null);
		
		assertNull(xmlElementRefAnnotation.getType());
		assertSourceContains("@XmlElementRefs(@XmlElementRef)", cu);
	}
	
	public void testAddXmlElementRef() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithAll();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		addXmlElementRefAnnotation(1, resourceAttribute);
		XmlElementRefsAnnotation refsAnnotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		
		assertSourceContains(
				"@XmlElementRefs({@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME 
						+ "\", namespace = \"" + XML_ELEMENT_REF_NAMESPACE
						+ "\", type = " + XML_ELEMENT_REF_TYPE 
						+ ".class),@XmlElementRef})", cu);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF));
		assertNotNull(refsAnnotation);
		assertEquals(2, refsAnnotation.getXmlElementRefsSize());
	}
	
	public void testAddXmlElementRefToBeginningOfList() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithAll();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		addXmlElementRefAnnotation(1, resourceAttribute);
		XmlElementRefsAnnotation refsAnnotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		
		assertSourceContains(
				"@XmlElementRefs({@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME 
						+ "\", namespace = \"" + XML_ELEMENT_REF_NAMESPACE
						+ "\", type = " + XML_ELEMENT_REF_TYPE 
						+ ".class),@XmlElementRef})", cu);
				
		addXmlElementRefAnnotation(0, resourceAttribute);
		
		assertSourceContains(
				"@XmlElementRefs({@XmlElementRef,@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME 
						+ "\", namespace = \"" + XML_ELEMENT_REF_NAMESPACE
						+ "\", type = " + XML_ELEMENT_REF_TYPE 
						+ ".class), @XmlElementRef})", cu);
		
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF));
		assertNotNull(refsAnnotation);
		assertEquals(3, refsAnnotation.getXmlElementRefsSize());
	}
	
	public void testRemoveXmlElementRef() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementRefWithAll();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		addXmlElementRefAnnotation(1, resourceAttribute);
		
		assertSourceContains(
				"@XmlElementRefs({@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME 
						+ "\", namespace = \"" + XML_ELEMENT_REF_NAMESPACE
						+ "\", type = " + XML_ELEMENT_REF_TYPE 
						+ ".class),@XmlElementRef})", cu);
		
		removeXmlElementRefAnnotation(1, resourceAttribute);
		
		assertSourceContains(
				"@XmlElementRef(name = \"" + XML_ELEMENT_REF_NAME 
						+ "\", namespace = \"" + XML_ELEMENT_REF_NAMESPACE
						+ "\", type = " + XML_ELEMENT_REF_TYPE 
						+ ".class)", cu);
	}
}