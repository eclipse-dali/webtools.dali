/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlInverseReferenceAnnotation;


public class XmlInverseReferenceAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlInverseReferenceAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestXmlInverseReference() 
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_INVERSE_REFERENCE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlInverseReference");
			}
		});
	}
	
	private ICompilationUnit createTestXmlInverseReferenceWithMappedBy()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_INVERSE_REFERENCE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlInverseReference(mappedBy = \"foo\")");
			}
		});
	}
	
	private XmlInverseReferenceAnnotation getXmlInverseReferenceAnnotation(JavaResourceAttribute resourceAttribute) {
		return (XmlInverseReferenceAnnotation) resourceAttribute.getAnnotation(ELJaxb.XML_INVERSE_REFERENCE);
	}
	
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = createTestXmlInverseReference();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlInverseReferenceAnnotation annotation = getXmlInverseReferenceAnnotation(resourceAttribute);
		
		assertTrue(annotation != null);
		assertNull(annotation.getMappedBy());
	}
	
	public void testGetMappedBy() throws Exception {
		ICompilationUnit cu = createTestXmlInverseReferenceWithMappedBy();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlInverseReferenceAnnotation annotation = getXmlInverseReferenceAnnotation(resourceAttribute);
		
		assertEquals("foo", annotation.getMappedBy());
	}
	
	public void testSetMappedBy() throws Exception {
		ICompilationUnit cu = createTestXmlInverseReference();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlInverseReferenceAnnotation annotation = getXmlInverseReferenceAnnotation(resourceAttribute);
		
		assertNull(annotation.getMappedBy());
		
		annotation.setMappedBy("bar");
		
		assertEquals("bar", annotation.getMappedBy());
		assertSourceContains("@XmlInverseReference(mappedBy = \"bar\")", cu);
		
		annotation.setMappedBy("");
		
		assertEquals("", annotation.getMappedBy());
		assertSourceContains("@XmlInverseReference(mappedBy = \"\")", cu);
		
		annotation.setMappedBy(null);
		
		assertNull(annotation.getMappedBy());
		assertSourceContains("@XmlInverseReference", cu);
		assertSourceDoesNotContain("mappedBy", cu);
	}
}
