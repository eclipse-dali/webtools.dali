/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ManyToOneTests extends JavaResourceModelTestCase {
	
	public ManyToOneTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestManyToOne() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "FetchType fetch() default FetchType.LAZY; CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne");
			}
		});
	}
	
	private ICompilationUnit createTestManyToOneWithFetch() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "FetchType fetch() default FetchType.LAZY;");
		this.createEnumAndMembers("FetchType", "EAGER, LAZY");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne(fetch=FetchType.EAGER)");
			}
		});
	}

	private ICompilationUnit createTestManyToOneWithTargetEntity() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "Class targetEntity() default void.class;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne(targetEntity=AnnotationTestType.class)");
			}
		});
	}

	private ICompilationUnit createTestManyToOneWithOptional() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "boolean optional() default true;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne(optional=true)");
			}
		});
	}
	
	private ICompilationUnit createTestManyToOneWithCascade() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne(cascade=CascadeType.ALL)");
			}
		});
	}
	
	private ICompilationUnit createTestManyToOneWithMultipleCascade() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REMOVE})");
			}
		});
	}
	
	private ICompilationUnit createTestManyToOneWithDuplicateCascade() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne(cascade={CascadeType.MERGE, CascadeType.MERGE})");
			}
		});
	}

	public void testManyToOne() throws Exception {
		ICompilationUnit cu = this.createTestManyToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertNotNull(manyToOne);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(FetchType.EAGER, manyToOne.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(FetchType.EAGER, manyToOne.getFetch());
		
		manyToOne.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, manyToOne.getFetch());
		
		assertSourceContains("@ManyToOne(fetch=LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(FetchType.EAGER, manyToOne.getFetch());
		
		manyToOne.setFetch(null);
		assertNull(manyToOne.getFetch());
		
		assertSourceContains("@ManyToOne", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
	
	
	public void testGetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(TYPE_NAME, manyToOne.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(TYPE_NAME, manyToOne.getTargetEntity());
		
		manyToOne.setTargetEntity("Foo");
		
		assertSourceContains("@ManyToOne(targetEntity=Foo.class)", cu);
	}
	
	public void testSetTargetEntityNull() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(TYPE_NAME, manyToOne.getTargetEntity());
		
		manyToOne.setTargetEntity(null);
		
		assertSourceContains("@ManyToOne", cu);
		assertSourceDoesNotContain("targetEntity", cu);
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, manyToOne.getFullyQualifiedTargetEntity());
		
		manyToOne.setTargetEntity("Foo");
		
		assertSourceContains("@ManyToOne(targetEntity=Foo.class)", cu);
		
		assertEquals("Foo", manyToOne.getTargetEntity());
		
		assertEquals("Foo", manyToOne.getFullyQualifiedTargetEntity());//bug 196200 changed this
	}
	
	public void testGetOptional() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(Boolean.TRUE, manyToOne.getOptional());
	}

	public void testSetOptional() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(Boolean.TRUE, manyToOne.getOptional());
		
		manyToOne.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, manyToOne.getOptional());
		
		assertSourceContains("@ManyToOne(optional=false)", cu);
	}
	
	public void testSetOptionalNull() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(Boolean.TRUE, manyToOne.getOptional());
		
		manyToOne.setOptional(null);
		assertNull(manyToOne.getOptional());
		
		assertSourceContains("@ManyToOne", cu);
		assertSourceDoesNotContain("optional", cu);
	}

	public void testSetCascadeAll() throws Exception {
		ICompilationUnit cu = this.createTestManyToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertFalse(manyToOne.isCascadeAll());
	
		manyToOne.setCascadeAll(true);
		assertSourceContains("@ManyToOne(cascade=ALL)", cu);
		
		assertTrue(manyToOne.isCascadeAll());
	}
	
	public void testSetCascadeMerge() throws Exception {
		ICompilationUnit cu = this.createTestManyToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertFalse(manyToOne.isCascadeMerge());
	
		manyToOne.setCascadeMerge(true);
		assertSourceContains("@ManyToOne(cascade=MERGE)", cu);
		
		assertTrue(manyToOne.isCascadeMerge());
	}
	
	public void testSetCascadePersist() throws Exception {
		ICompilationUnit cu = this.createTestManyToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertFalse(manyToOne.isCascadePersist());
	
		manyToOne.setCascadePersist(true);
		assertSourceContains("@ManyToOne(cascade=PERSIST)", cu);
		
		assertTrue(manyToOne.isCascadePersist());
	}
	
	public void testSetCascadeRemove() throws Exception {
		ICompilationUnit cu = this.createTestManyToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertFalse(manyToOne.isCascadeRemove());
	
		manyToOne.setCascadeRemove(true);
		assertSourceContains("@ManyToOne(cascade=REMOVE)", cu);
		
		assertTrue(manyToOne.isCascadeRemove());
	}

	public void testSetCascadeRefresh() throws Exception {
		ICompilationUnit cu = this.createTestManyToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertFalse(manyToOne.isCascadeRefresh());
	
		manyToOne.setCascadeRefresh(true);
		assertSourceContains("@ManyToOne(cascade=REFRESH)", cu);
		
		assertTrue(manyToOne.isCascadeRefresh());
	}

	public void testCascadeMoreThanOnce() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertTrue(manyToOne.isCascadeAll());
		
		manyToOne.setCascadeAll(true);
		assertTrue(manyToOne.isCascadeAll());
		//a second CascadeType.All should not have been added
		assertSourceContains("@ManyToOne(cascade=CascadeType.ALL)", cu);
		
		manyToOne.setCascadeAll(false);
		assertFalse(manyToOne.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
		
		//test setting cascadeAll to false again, should just do nothing
		manyToOne.setCascadeAll(false);
		assertFalse(manyToOne.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testDuplicateCascade() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithDuplicateCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertTrue(manyToOne.isCascadeMerge());
		
		manyToOne.setCascadeMerge(false);//TODO should the resource model handle this and remove both MERGE 
										//settings instead of having to set it false twice?
		assertTrue(manyToOne.isCascadeMerge());
		
		manyToOne.setCascadeMerge(false);
		assertFalse(manyToOne.isCascadeMerge());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testMultipleCascade() throws Exception {
		ICompilationUnit cu = this.createTestManyToOneWithMultipleCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) attributeResource.getMappingAnnotation(JPA.MANY_TO_ONE);
		assertTrue(manyToOne.isCascadeMerge());
		assertTrue(manyToOne.isCascadeRemove());
		
		manyToOne.setCascadeMerge(false);
		assertSourceContains("@ManyToOne(cascade=REMOVE)", cu);
		
		manyToOne.setCascadeRemove(false);		
		assertSourceDoesNotContain("cascade", cu);
	}
}
