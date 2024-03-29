/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;

@SuppressWarnings("nls")
public class EntityTests extends JpaJavaResourceModelTestCase {

	private static final String ENTITY_NAME = "Foo";
	
	public EntityTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity(name = \"" + ENTITY_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassAndEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
				sb.append("@Entity");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EntityAnnotation entity = (EntityAnnotation) resourceType.getAnnotation(JPA.ENTITY);
		assertTrue(entity != null);
		assertEquals(ENTITY_NAME, entity.getName());
	}

	public void testGetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EntityAnnotation entity = (EntityAnnotation) resourceType.getAnnotation(JPA.ENTITY);
		assertTrue(entity != null);
		assertNull(entity.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EntityAnnotation entity = (EntityAnnotation) resourceType.getAnnotation(JPA.ENTITY);
		assertNull(entity.getName());
		entity.setName("Foo");
		assertEquals("Foo", entity.getName());
		
		assertSourceContains("@Entity(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EntityAnnotation entity = (EntityAnnotation) resourceType.getAnnotation(JPA.ENTITY);
		assertEquals(ENTITY_NAME, entity.getName());
		
		entity.setName(null);
		assertNull(entity.getName());
		
		assertSourceContains("@Entity", cu);
		assertSourceDoesNotContain("@Entity(name = \"Foo\")", cu);
	}
	
	public void testMappedSuperclassAndEntity() throws Exception {
		ICompilationUnit cu = this.createTestMappedSuperclassAndEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EntityAnnotation mappingAnnotation = (EntityAnnotation) resourceType.getAnnotation(JPA.ENTITY);
		assertNotNull(mappingAnnotation);
		
		MappedSuperclassAnnotation mappedSuperclass = (MappedSuperclassAnnotation) resourceType.getAnnotation(JPA.MAPPED_SUPERCLASS);
		assertNotNull(mappedSuperclass);
	}

}
