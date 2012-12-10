/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;

@SuppressWarnings("nls")
public class XmlPathAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlPathAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestXmlPath()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_PATH);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlPath");
			}
		});
	}
	
	private ICompilationUnit createTestXmlPathWithValue()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_PATH);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlPath(\"foo\")");
			}
		});
	}
	
	private ICompilationUnit createTestXmlPaths()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_PATHS, ELJaxb.XML_PATH);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlPaths({@XmlPath,@XmlPath})");
			}
		});
	}
	
	private XmlPathAnnotation getXmlPathAnnotation(JavaResourceAttribute resourceAttribute) {
		return getXmlPathAnnotation(resourceAttribute, 0);
	}
	
	private XmlPathAnnotation getXmlPathAnnotation(JavaResourceAttribute resourceAttribute, int index) {
		return (XmlPathAnnotation) resourceAttribute.getAnnotation(index, ELJaxb.XML_PATH);
	}
	
	
	public void testValue() throws Exception {
		ICompilationUnit cu = createTestXmlPathWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlPathAnnotation annotation = getXmlPathAnnotation(resourceAttribute);
		
		assertEquals("foo", annotation.getValue());
		assertSourceContains("@XmlPath(\"foo\")", cu);
		
		annotation.setValue("bar");
		
		assertEquals("bar", annotation.getValue());
		assertSourceContains("@XmlPath(\"bar\")", cu);
		
		annotation.setValue("");
		
		assertEquals("", annotation.getValue());
		assertSourceContains("@XmlPath(\"\")", cu);
		
		annotation.setValue(null);
		
		assertNull(annotation.getValue());
		assertSourceContains("@XmlPath", cu);
		assertSourceDoesNotContain("@XmlPath(", cu);
	}
	
	public void testContainedValue() throws Exception {
		// test contained annotation value setting/updating
		
		ICompilationUnit cu = createTestXmlPaths();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlPathAnnotation annotation = getXmlPathAnnotation(resourceAttribute);
		
		assertNull(annotation.getValue());
		assertSourceContains(
				"@XmlPaths({@XmlPath,@XmlPath})", cu);
		
		annotation.setValue("foo");
		assertEquals("foo", annotation.getValue());
		assertSourceContains(
				"@XmlPaths({@XmlPath(\"foo\"),@XmlPath})", cu);
		
		annotation.setValue(null);
		assertNull(annotation.getValue());
		assertSourceContains(
				"@XmlPaths({@XmlPath,@XmlPath})", cu);
	}
	
	public void testContained()
			throws Exception {
		// test adding/removing/moving
		
		ICompilationUnit cu = createTestXmlPath();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		
		assertEquals(1, resourceAttribute.getAnnotationsSize(ELJaxb.XML_PATH));
		
		resourceAttribute.addAnnotation(1, ELJaxb.XML_PATH);
		
		assertEquals(2, resourceAttribute.getAnnotationsSize(ELJaxb.XML_PATH));
		assertSourceContains("@XmlPaths({ @XmlPath, @XmlPath })", cu);
		
		XmlPathAnnotation annotation1 = getXmlPathAnnotation(resourceAttribute, 0);
		annotation1.setValue("foo");
		XmlPathAnnotation annotation2 = getXmlPathAnnotation(resourceAttribute, 1);
		annotation2.setValue("bar");
		assertSourceContains(
				"@XmlPaths({ @XmlPath(\"foo\"), @XmlPath(\"bar\") })", cu);
		
		resourceAttribute.moveAnnotation(0, 1, ELJaxb.XML_PATH);
		assertSourceContains(
				"@XmlPaths({ @XmlPath(\"bar\"), @XmlPath(\"foo\") })", cu);
		
		resourceAttribute.removeAnnotation(0, ELJaxb.XML_PATH);
		assertEquals(1, resourceAttribute.getAnnotationsSize(ELJaxb.XML_PATH));
		assertSourceContains(
				"@XmlPath(\"foo\")", cu);
		assertSourceDoesNotContain("@XmlPaths", cu);
	}
}
