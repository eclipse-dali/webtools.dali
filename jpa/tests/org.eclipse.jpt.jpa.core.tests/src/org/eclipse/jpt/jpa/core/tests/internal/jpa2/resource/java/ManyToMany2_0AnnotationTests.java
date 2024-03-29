/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ManyToManyAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;

@SuppressWarnings("nls")
public class ManyToMany2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	public ManyToMany2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestManyToMany() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MANY_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany");
			}
		});
	}
	
	private ICompilationUnit createTestManyToManyWithFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MANY_TO_MANY, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany(fetch = FetchType.EAGER)");
			}
		});
	}
	
	private ICompilationUnit createTestManyToManyWithTargetEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MANY_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany(targetEntity = AnnotationTestType.class)");
			}
		});
	}
	
	private ICompilationUnit createTestManyToManyWithMappedBy() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MANY_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany(mappedBy = \"foo\")");
			}
		});
	}
	
	private ICompilationUnit createTestManyToManyWithCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MANY_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany(cascade = CascadeType.ALL)");
			}
		});
	}
	
	private ICompilationUnit createTestManyToManyWithMultipleCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MANY_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})");
			}
		});
	}
	
	private ICompilationUnit createTestManyToManyWithDuplicateCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MANY_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.MERGE})");
			}
		});
	}

	public void testManyToMany() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertNotNull(manyToMany);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FetchType.EAGER, manyToMany.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FetchType.EAGER, manyToMany.getFetch());
		
		manyToMany.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, manyToMany.getFetch());
		
		assertSourceContains("@ManyToMany(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FetchType.EAGER, manyToMany.getFetch());
		
		manyToMany.setFetch(null);
		assertNull(manyToMany.getFetch());
		
		assertSourceContains("@ManyToMany", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
	
	public void testGetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithTargetEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(TYPE_NAME, manyToMany.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithTargetEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(TYPE_NAME, manyToMany.getTargetEntity());
		
		manyToMany.setTargetEntity("Foo");
		
		assertSourceContains("@ManyToMany(targetEntity = Foo.class)", cu);
	}
	
	public void testSetTargetEntityNull() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithTargetEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(TYPE_NAME, manyToMany.getTargetEntity());
		
		manyToMany.setTargetEntity(null);
		
		assertSourceContains("@ManyToMany", cu);
		assertSourceDoesNotContain("targetEntity", cu);
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithTargetEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, manyToMany.getFullyQualifiedTargetEntityClassName());
		
		manyToMany.setTargetEntity("Foo");
		
		assertSourceContains("@ManyToMany(targetEntity = Foo.class)", cu);
		
		assertEquals("Foo", manyToMany.getTargetEntity());
		
		assertEquals("Foo", manyToMany.getFullyQualifiedTargetEntityClassName()); //bug 196200 changed this
	}
	
	public void testGetMappedBy() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithMappedBy();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals("foo", manyToMany.getMappedBy());
	}

	public void testGetMappedByNull() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals(null, manyToMany.getMappedBy());
	}

	public void testSetMappedBy() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertNull(manyToMany.getMappedBy());
		manyToMany.setMappedBy("bar");
		assertEquals("bar", manyToMany.getMappedBy());
		
		assertSourceContains("@ManyToMany(mappedBy = \"bar\")", cu);
	}
	
	public void testSetMappedByNull() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithMappedBy();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertEquals("foo", manyToMany.getMappedBy());
		
		manyToMany.setMappedBy(null);
		assertNull(manyToMany.getMappedBy());
		
		assertSourceContains("@ManyToMany", cu);
		assertSourceDoesNotContain("mappedBy", cu);
	}
	
	public void testSetCascadeAll() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeAll());
	
		manyToMany.setCascadeAll(true);
		assertSourceContains("@ManyToMany(cascade = ALL)", cu);
		
		assertTrue(manyToMany.isCascadeAll());
	}
	
	public void testSetCascadeMerge() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeMerge());
	
		manyToMany.setCascadeMerge(true);
		assertSourceContains("@ManyToMany(cascade = MERGE)", cu);
		
		assertTrue(manyToMany.isCascadeMerge());
	}
	
	public void testSetCascadePersist() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadePersist());
	
		manyToMany.setCascadePersist(true);
		assertSourceContains("@ManyToMany(cascade = PERSIST)", cu);
		
		assertTrue(manyToMany.isCascadePersist());
	}
	
	public void testSetCascadeRemove() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeRemove());
	
		manyToMany.setCascadeRemove(true);
		assertSourceContains("@ManyToMany(cascade = REMOVE)", cu);
		
		assertTrue(manyToMany.isCascadeRemove());
	}

	public void testSetCascadeRefresh() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeRefresh());
	
		manyToMany.setCascadeRefresh(true);
		assertSourceContains("@ManyToMany(cascade = REFRESH)", cu);
		
		assertTrue(manyToMany.isCascadeRefresh());
	}

	public void testSetCascadeDetach() throws Exception {
		ICompilationUnit cu = this.createTestManyToMany();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation2_0 manyToMany = (ManyToManyAnnotation2_0) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeDetach());
	
		manyToMany.setCascadeDetach(true);
		assertSourceContains("@ManyToMany(cascade = DETACH)", cu);
		
		assertTrue(manyToMany.isCascadeDetach());
	}

	public void testCascadeMoreThanOnce() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithCascade();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertTrue(manyToMany.isCascadeAll());
		
		manyToMany.setCascadeAll(true);
		assertTrue(manyToMany.isCascadeAll());
		//a second CascadeType.All should not have been added
		assertSourceContains("@ManyToMany(cascade = CascadeType.ALL)", cu);
		
		manyToMany.setCascadeAll(false);
		assertFalse(manyToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
		
		//test setting cascadeAll to false again, should just do nothing
		manyToMany.setCascadeAll(false);
		assertFalse(manyToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testDuplicateCascade() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithDuplicateCascade();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertTrue(manyToMany.isCascadeMerge());
		
		manyToMany.setCascadeMerge(false); //TODO should the resource model handle this and remove both MERGE 
										  //settings instead of having to set it false twice?
		assertTrue(manyToMany.isCascadeMerge());
		
		manyToMany.setCascadeMerge(false);
		assertFalse(manyToMany.isCascadeMerge());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testMultipleCascade() throws Exception {
		ICompilationUnit cu = this.createTestManyToManyWithMultipleCascade();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) resourceField.getAnnotation(JPA.MANY_TO_MANY);
		assertTrue(manyToMany.isCascadeMerge());
		assertTrue(manyToMany.isCascadeRemove());
		
		manyToMany.setCascadeMerge(false);
		assertSourceContains("@ManyToMany(cascade = REMOVE)", cu);
		
		manyToMany.setCascadeRemove(false);		
		assertSourceDoesNotContain("cascade", cu);
	}
}
