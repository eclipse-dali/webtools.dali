/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;

@SuppressWarnings("nls")
public class OneToManyTests extends JpaJavaResourceModelTestCase {
	
	public OneToManyTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestOneToMany() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany");
			}
		});
	}
	
	private ICompilationUnit createTestOneToManyWithFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany(fetch = FetchType.EAGER)");
			}
		});
	}
	
	private ICompilationUnit createTestOneToManyWithTargetEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany(targetEntity = AnnotationTestType.class)");
			}
		});
	}
	
	private ICompilationUnit createTestOneToManyWithMappedBy() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany(mappedBy = \"foo\")");
			}
		});
	}
	
	private ICompilationUnit createTestOneToManyWithCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany(cascade = CascadeType.ALL)");
			}
		});
	}
	
	private ICompilationUnit createTestOneToManyWithMultipleCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})");
			}
		});
	}
	
	private ICompilationUnit createTestOneToManyWithDuplicateCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany(cascade = {CascadeType.MERGE, CascadeType.MERGE})");
			}
		});
	}

	public void testOneToMany() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertNotNull(oneToMany);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FetchType.EAGER, oneToMany.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FetchType.EAGER, oneToMany.getFetch());
		
		oneToMany.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToMany.getFetch());
		
		assertSourceContains("@OneToMany(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FetchType.EAGER, oneToMany.getFetch());
		
		oneToMany.setFetch(null);
		assertNull(oneToMany.getFetch());
		
		assertSourceContains("@OneToMany", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
	
	
	public void testGetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(TYPE_NAME, oneToMany.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(TYPE_NAME, oneToMany.getTargetEntity());
		
		oneToMany.setTargetEntity("Foo");
		
		assertSourceContains("@OneToMany(targetEntity = Foo.class)", cu);
	}
	
	public void testSetTargetEntityNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(TYPE_NAME, oneToMany.getTargetEntity());
		
		oneToMany.setTargetEntity(null);
		
		assertSourceContains("@OneToMany", cu);
		assertSourceDoesNotContain("targetEntity", cu);
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, oneToMany.getFullyQualifiedTargetEntityClassName());
		
		oneToMany.setTargetEntity("Foo");
		
		assertSourceContains("@OneToMany(targetEntity = Foo.class)", cu);
		
		assertEquals("Foo", oneToMany.getTargetEntity());
		
		assertEquals("Foo", oneToMany.getFullyQualifiedTargetEntityClassName()); //bug 196200 changed this
	}
	
	public void testGetMappedBy() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithMappedBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals("foo", oneToMany.getMappedBy());
	}


	public void testGetMappedByNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals(null, oneToMany.getMappedBy());
	}

	public void testSetMappedBy() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertNull(oneToMany.getMappedBy());
		oneToMany.setMappedBy("bar");
		assertEquals("bar", oneToMany.getMappedBy());
		
		assertSourceContains("@OneToMany(mappedBy = \"bar\")", cu);
	}
	
	public void testSetMappedByNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithMappedBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertEquals("foo", oneToMany.getMappedBy());
		
		oneToMany.setMappedBy(null);
		assertNull(oneToMany.getMappedBy());
		
		assertSourceContains("@OneToMany", cu);
		assertSourceDoesNotContain("mappedBy", cu);
	}
	
	public void testSetCascadeAll() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeAll());
	
		oneToMany.setCascadeAll(true);
		assertSourceContains("@OneToMany(cascade = ALL)", cu);
		
		assertTrue(oneToMany.isCascadeAll());
	}
	
	public void testSetCascadeMerge() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeMerge());
	
		oneToMany.setCascadeMerge(true);
		assertSourceContains("@OneToMany(cascade = MERGE)", cu);
		
		assertTrue(oneToMany.isCascadeMerge());
	}
	
	public void testSetCascadePersist() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadePersist());
	
		oneToMany.setCascadePersist(true);
		assertSourceContains("@OneToMany(cascade = PERSIST)", cu);
		
		assertTrue(oneToMany.isCascadePersist());
	}
	
	public void testSetCascadeRemove() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeRemove());
	
		oneToMany.setCascadeRemove(true);
		assertSourceContains("@OneToMany(cascade = REMOVE)", cu);
		
		assertTrue(oneToMany.isCascadeRemove());
	}

	public void testSetCascadeRefresh() throws Exception {
		ICompilationUnit cu = this.createTestOneToMany();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeRefresh());
	
		oneToMany.setCascadeRefresh(true);
		assertSourceContains("@OneToMany(cascade = REFRESH)", cu);
		
		assertTrue(oneToMany.isCascadeRefresh());
	}

	public void testCascadeMoreThanOnce() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertTrue(oneToMany.isCascadeAll());
		
		oneToMany.setCascadeAll(true);
		assertTrue(oneToMany.isCascadeAll());
		//a second CascadeType.All should not have been added
		assertSourceContains("@OneToMany(cascade = CascadeType.ALL)", cu);
		
		oneToMany.setCascadeAll(false);
		assertFalse(oneToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
		
		//test setting cascadeAll to false again, should just do nothing
		oneToMany.setCascadeAll(false);
		assertFalse(oneToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testDuplicateCascade() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithDuplicateCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertTrue(oneToMany.isCascadeMerge());
		
		oneToMany.setCascadeMerge(false);//TODO should the resource model handle this and remove both MERGE 
										//settings instead of having to set it false twice?
		assertTrue(oneToMany.isCascadeMerge());
		
		oneToMany.setCascadeMerge(false);
		assertFalse(oneToMany.isCascadeMerge());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testMultipleCascade() throws Exception {
		ICompilationUnit cu = this.createTestOneToManyWithMultipleCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToManyAnnotation oneToMany = (OneToManyAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		assertTrue(oneToMany.isCascadeMerge());
		assertTrue(oneToMany.isCascadeRemove());
		
		oneToMany.setCascadeMerge(false);
		assertSourceContains("@OneToMany(cascade = REMOVE)", cu);
		
		oneToMany.setCascadeRemove(false);		
		assertSourceDoesNotContain("cascade", cu);
	}

}
