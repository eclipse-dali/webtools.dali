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
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlTransformationAnnotation;


public class XmlTransformationAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlTransformationAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestXmlTransformation() 
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_TRANSFORMATION);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlTransformation");
			}
		});
	}
	
	private ICompilationUnit createTestXmlTransformationWithOptional()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_TRANSFORMATION);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlTransformation(optional = true)");
			}
		});
	}
	
	private XmlTransformationAnnotation getXmlTransformationAnnotation(JavaResourceAttribute resourceAttribute) {
		return (XmlTransformationAnnotation) resourceAttribute.getAnnotation(ELJaxb.XML_TRANSFORMATION);
	}
	
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = createTestXmlTransformation();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlTransformationAnnotation annotation = getXmlTransformationAnnotation(resourceAttribute);
		
		assertTrue(annotation != null);
		assertNull(annotation.getOptional());
	}
	
	public void testGetOptional() throws Exception {
		ICompilationUnit cu = createTestXmlTransformationWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlTransformationAnnotation annotation = getXmlTransformationAnnotation(resourceAttribute);
		
		assertEquals(Boolean.TRUE, annotation.getOptional());
	}
	
	public void testSetOptional() throws Exception {
		ICompilationUnit cu = createTestXmlTransformation();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlTransformationAnnotation annotation = getXmlTransformationAnnotation(resourceAttribute);
		
		assertNull(annotation.getOptional());
		
		annotation.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, annotation.getOptional());
		assertSourceContains("@XmlTransformation(optional = false)", cu);
		
		annotation.setOptional(null);
		
		assertNull(annotation.getOptional());
		assertSourceContains("@XmlTransformation", cu);
		assertSourceDoesNotContain("optional", cu);
	}
}
