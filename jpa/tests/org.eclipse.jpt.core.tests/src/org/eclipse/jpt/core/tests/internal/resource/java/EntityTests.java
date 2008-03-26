/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EntityTests extends JavaResourceModelTestCase {

	private static final String ENTITY_NAME = "Foo";
	
	public EntityTests(String name) {
		super(name);
	}

	private IType createTestEntity() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private IType createTestEntityWithName() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity(name=\"" + ENTITY_NAME + "\")");
			}
		});
	}
	
	private IType createTestMappedSuperclassAndEntity() throws Exception {
		this.createAnnotationAndMembers("MappedSuperclass", "");
		this.createAnnotationAndMembers("Entity", "String name();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
				sb.append("@Entity");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestEntityWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		EntityAnnotation entity = (EntityAnnotation) typeResource.getMappingAnnotation(JPA.ENTITY);
		assertTrue(entity != null);
		assertEquals(ENTITY_NAME, entity.getName());
	}

	public void testGetNameNull() throws Exception {
		IType testType = this.createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		EntityAnnotation entity = (EntityAnnotation) typeResource.getMappingAnnotation(JPA.ENTITY);
		assertTrue(entity != null);
		assertNull(entity.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		EntityAnnotation entity = (EntityAnnotation) typeResource.getMappingAnnotation(JPA.ENTITY);
		assertNull(entity.getName());
		entity.setName("Foo");
		assertEquals("Foo", entity.getName());
		
		assertSourceContains("@Entity(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestEntityWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		EntityAnnotation entity = (EntityAnnotation) typeResource.getMappingAnnotation(JPA.ENTITY);
		assertEquals(ENTITY_NAME, entity.getName());
		
		entity.setName(null);
		assertNull(entity.getName());
		
		assertSourceContains("@Entity");
		assertSourceDoesNotContain("@Entity(name=\"Foo\")");
	}
	
	public void testMappedSuperclassAndEntity() throws Exception {
		IType testType = this.createTestMappedSuperclassAndEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		JavaResourceNode mappingAnnotation = typeResource.getMappingAnnotation();
		assertTrue(mappingAnnotation instanceof EntityAnnotation);
		
		MappedSuperclassAnnotation mappedSuperclass = (MappedSuperclassAnnotation) typeResource.getMappingAnnotation(JPA.MAPPED_SUPERCLASS);
		assertNotNull(mappedSuperclass);
	}

}
