/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;

@SuppressWarnings("nls")
public class MapsId2_0AnnotationTests
	extends JavaResourceModel2_0TestCase
{
	public MapsId2_0AnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestMapsId() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAPS_ID);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapsId");
			}
		});
	}
	
	private ICompilationUnit createTestMapsIdWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAPS_ID);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapsId(\"foo\")");
			}
		});
	}
	
	public void testMapsId() throws Exception {
		ICompilationUnit cu = this.createTestMapsId();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapsId2_0Annotation annotation = (MapsId2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAPS_ID);
		assertNotNull(annotation);
	}
	
	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapsIdWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapsId2_0Annotation annotation = (MapsId2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAPS_ID);
		assertEquals("foo", annotation.getValue());
	}
	
	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapsId();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		MapsId2_0Annotation annotation = (MapsId2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAPS_ID);
		annotation.setValue("foo");
		assertSourceContains("@MapsId(\"foo\")", cu);
		
		annotation.setValue(null);
		assertSourceContains("@MapsId", cu);
	}
}
