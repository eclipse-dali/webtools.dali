/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.AccessType;

@SuppressWarnings("nls")
public class Access2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	public Access2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestAccessOnType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Access(AccessType.PROPERTY)");
			}
		});
	}

	private ICompilationUnit createTestAccessOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
		
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Access(AccessType.FIELD)");
			}
		});
	}

	private ICompilationUnit createTestAccessOnProperty() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Access(AccessType.PROPERTY)");
			}
		});
	}

	public void testGetAccessOnType() throws Exception {
		ICompilationUnit cu = this.createTestAccessOnType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		Access2_0Annotation access = (Access2_0Annotation) resourceType.getAnnotation(JPA2_0.ACCESS);
		assertNotNull(access);
		assertEquals(AccessType.PROPERTY, access.getValue());
	}

	public void testSetAccessOnType() throws Exception {
		ICompilationUnit cu = this.createTestType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		Access2_0Annotation access = (Access2_0Annotation) resourceType.getAnnotation(JPA2_0.ACCESS);
		assertNull(access);
		
		resourceType.addAnnotation(JPA2_0.ACCESS);
		assertSourceContains("@Access", cu);

		access = (Access2_0Annotation) resourceType.getAnnotation(JPA2_0.ACCESS);
		assertNotNull(access);
		
		access.setValue(AccessType.FIELD);
		assertEquals(AccessType.FIELD, access.getValue());		
		assertSourceContains("@Access(FIELD)", cu);
		
		access.setValue(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, access.getValue());
		assertSourceContains("@Access(PROPERTY)", cu);
		
		access.setValue(null);
		assertSourceDoesNotContain("@Access(", cu);
	}
	
	public void testGetAccessOnField() throws Exception {
		ICompilationUnit cu = this.createTestAccessOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		Access2_0Annotation access = (Access2_0Annotation) resourceField.getAnnotation(JPA2_0.ACCESS);
		assertNotNull(access);
		assertEquals(AccessType.FIELD, access.getValue());
	}

	public void testSetAccessOnField() throws Exception {
		ICompilationUnit cu = this.createTestType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		Access2_0Annotation access = (Access2_0Annotation) resourceField.getAnnotation(JPA2_0.ACCESS);
		assertNull(access);
		
		resourceField.addAnnotation(JPA2_0.ACCESS);
		assertSourceContains("@Access", cu);

		access = (Access2_0Annotation) resourceField.getAnnotation(JPA2_0.ACCESS);
		assertNotNull(access);
		
		access.setValue(AccessType.FIELD);
		assertEquals(AccessType.FIELD, access.getValue());		
		assertSourceContains("@Access(FIELD)", cu);
		
		access.setValue(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, access.getValue());
		assertSourceContains("@Access(PROPERTY)", cu);
		
		access.setValue(null);
		assertSourceDoesNotContain("@Access(", cu);
	}
	
	public void testGetAccessOnProperty() throws Exception {
		ICompilationUnit cu = this.createTestAccessOnProperty();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = CollectionTools.get(resourceType.getMethods(), 0);
		
		Access2_0Annotation access = (Access2_0Annotation) resourceMethod.getAnnotation(JPA2_0.ACCESS);
		assertNotNull(access);
		assertEquals(AccessType.PROPERTY, access.getValue());
	}

	public void testSetAccessOnProperty() throws Exception {
		ICompilationUnit cu = this.createTestType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = CollectionTools.get(resourceType.getMethods(), 0);
		
		Access2_0Annotation access = (Access2_0Annotation) resourceMethod.getAnnotation(JPA2_0.ACCESS);
		assertNull(access);
		
		resourceMethod.addAnnotation(JPA2_0.ACCESS);
		assertSourceContains("@Access", cu);

		access = (Access2_0Annotation) resourceMethod.getAnnotation(JPA2_0.ACCESS);
		assertNotNull(access);
		
		access.setValue(AccessType.FIELD);
		assertEquals(AccessType.FIELD, access.getValue());		
		assertSourceContains("@Access(FIELD)", cu);
		
		access.setValue(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, access.getValue());
		assertSourceContains("@Access(PROPERTY)", cu);
		
		access.setValue(null);
		assertSourceDoesNotContain("@Access(", cu);
	}
}
