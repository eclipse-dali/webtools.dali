/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OneToOne2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;

@SuppressWarnings("nls")
public class OneToOne2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	public OneToOne2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestOneToOne() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne");
			}
		});
	}
	
	private ICompilationUnit createTestOneToOneWithFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(fetch = FetchType.EAGER)");
			}
		});
	}

	private ICompilationUnit createTestOneToOneWithTargetEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(targetEntity = AnnotationTestType.class)");
			}
		});
	}

	private ICompilationUnit createTestOneToOneWithOptional() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(optional = true)");
			}
		});
	}
	
	private ICompilationUnit createTestOneToOneWithMappedBy() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(mappedBy = \"foo\")");
			}
		});
	}
	
	private ICompilationUnit createTestOneToOneWithCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(cascade = CascadeType.ALL)");
			}
		});
	}
	
	private ICompilationUnit createTestOneToOneWithMultipleCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})");
			}
		});
	}
	
	private ICompilationUnit createTestOneToOneWithDuplicateCascade() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(cascade = {CascadeType.MERGE, CascadeType.MERGE})");
			}
		});
	}

	public void testOneToOne() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertNotNull(oneToOne);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToOne.getFetch());
		
		assertSourceContains("@OneToOne(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(null);
		assertNull(oneToOne.getFetch());
		
		assertSourceContains("@OneToOne", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
	
	
	public void testGetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
		
		oneToOne.setTargetEntity("Foo");
		
		assertSourceContains("@OneToOne(targetEntity = Foo.class)", cu);
	}
	
	public void testSetTargetEntityNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
		
		oneToOne.setTargetEntity(null);
		
		assertSourceContains("@OneToOne", cu);
		assertSourceDoesNotContain("targetEntity", cu);
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, oneToOne.getFullyQualifiedTargetEntityClassName());
		
		oneToOne.setTargetEntity("Foo");
		
		assertSourceContains("@OneToOne(targetEntity = Foo.class)", cu);
		
		assertEquals("Foo", oneToOne.getTargetEntity());
		
		assertEquals("Foo", oneToOne.getFullyQualifiedTargetEntityClassName()); //bug 196200 changed this
	}
	
	public void testGetOptional() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(Boolean.TRUE, oneToOne.getOptional());
	}

	public void testSetOptional() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(Boolean.TRUE, oneToOne.getOptional());
		
		oneToOne.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToOne.getOptional());
		
		assertSourceContains("@OneToOne(optional = false)", cu);
	}
	
	public void testSetOptionalNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(Boolean.TRUE, oneToOne.getOptional());
		
		oneToOne.setOptional(null);
		assertNull(oneToOne.getOptional());
		
		assertSourceContains("@OneToOne", cu);
		assertSourceDoesNotContain("optional", cu);
	}
	
	public void testGetMappedBy() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithMappedBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals("foo", oneToOne.getMappedBy());
	}

	public void testGetMappedByNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals(null, oneToOne.getMappedBy());
	}

	public void testSetMappedBy() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertNull(oneToOne.getMappedBy());
		oneToOne.setMappedBy("bar");
		assertEquals("bar", oneToOne.getMappedBy());
		
		assertSourceContains("@OneToOne(mappedBy = \"bar\")", cu);
	}
	
	public void testSetMappedByNull() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithMappedBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertEquals("foo", oneToOne.getMappedBy());
		
		oneToOne.setMappedBy(null);
		assertNull(oneToOne.getMappedBy());
		
		assertSourceContains("@OneToOne", cu);
		assertSourceDoesNotContain("mappedBy", cu);
	}

	public void testSetCascadeAll() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeAll());
	
		oneToOne.setCascadeAll(true);
		assertSourceContains("@OneToOne(cascade = ALL)", cu);
		
		assertTrue(oneToOne.isCascadeAll());
	}
	
	public void testSetCascadeMerge() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeMerge());
	
		oneToOne.setCascadeMerge(true);
		assertSourceContains("@OneToOne(cascade = MERGE)", cu);
		
		assertTrue(oneToOne.isCascadeMerge());
	}
	
	public void testSetCascadePersist() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadePersist());
	
		oneToOne.setCascadePersist(true);
		assertSourceContains("@OneToOne(cascade = PERSIST)", cu);
		
		assertTrue(oneToOne.isCascadePersist());
	}
	
	public void testSetCascadeRemove() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeRemove());
	
		oneToOne.setCascadeRemove(true);
		assertSourceContains("@OneToOne(cascade = REMOVE)", cu);
		
		assertTrue(oneToOne.isCascadeRemove());
	}

	public void testSetCascadeRefresh() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeRefresh());
	
		oneToOne.setCascadeRefresh(true);
		assertSourceContains("@OneToOne(cascade = REFRESH)", cu);
		
		assertTrue(oneToOne.isCascadeRefresh());
	}

	public void testSetCascadeDetach() throws Exception {
		ICompilationUnit cu = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne2_0Annotation oneToOne = (OneToOne2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeDetach());
	
		oneToOne.setCascadeDetach(true);
		assertSourceContains("@OneToOne(cascade = DETACH)", cu);
		
		assertTrue(oneToOne.isCascadeDetach());
	}

	public void testCascadeMoreThanOnce() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.isCascadeAll());
		
		oneToOne.setCascadeAll(true);
		assertTrue(oneToOne.isCascadeAll());
		//a second CascadeType.All should not have been added
		assertSourceContains("@OneToOne(cascade = CascadeType.ALL)", cu);
		
		oneToOne.setCascadeAll(false);
		assertFalse(oneToOne.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
		
		//test setting cascadeAll to false again, should just do nothing
		oneToOne.setCascadeAll(false);
		assertFalse(oneToOne.isCascadeAll());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testDuplicateCascade() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithDuplicateCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.isCascadeMerge());
		
		oneToOne.setCascadeMerge(false);//TODO should the resource model handle this and remove both MERGE 
										//settings instead of having to set it false twice?
		assertTrue(oneToOne.isCascadeMerge());
		
		oneToOne.setCascadeMerge(false);
		assertFalse(oneToOne.isCascadeMerge());
		
		assertSourceDoesNotContain("cascade", cu);
	}
	
	public void testMultipleCascade() throws Exception {
		ICompilationUnit cu = this.createTestOneToOneWithMultipleCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.isCascadeMerge());
		assertTrue(oneToOne.isCascadeRemove());
		
		oneToOne.setCascadeMerge(false);
		assertSourceContains("@OneToOne(cascade = REMOVE)", cu);
		
		oneToOne.setCascadeRemove(false);		
		assertSourceDoesNotContain("cascade", cu);
	}
	
}
