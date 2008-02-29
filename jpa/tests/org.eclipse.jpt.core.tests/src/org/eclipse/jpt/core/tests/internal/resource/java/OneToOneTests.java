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
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.OneToOne;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OneToOneTests extends JavaResourceModelTestCase {
	
	public OneToOneTests(String name) {
		super(name);
	}

	private IType createTestOneToOne() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "FetchType fetch() default FetchType.LAZY; CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
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
	
	private IType createTestOneToOneWithFetch() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "FetchType fetch() default FetchType.LAZY;");
		this.createEnumAndMembers("FetchType", "EAGER, LAZY");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(fetch=FetchType.EAGER)");
			}
		});
	}

	private IType createTestOneToOneWithTargetEntity() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "Class targetEntity() default void.class;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(targetEntity=AnnotationTestType.class)");
			}
		});
	}

	private IType createTestOneToOneWithOptional() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "boolean optional() default true;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(optional=true)");
			}
		});
	}
	
	private IType createTestOneToOneWithMappedBy() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "String mappedBy() default\"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(mappedBy=\"foo\")");
			}
		});
	}
	
	private IType createTestOneToOneWithCascade() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(cascade=CascadeType.ALL)");
			}
		});
	}
	
	private IType createTestOneToOneWithMultipleCascade() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(cascade={CascadeType.MERGE, CascadeType.REMOVE})");
			}
		});
	}
	
	private IType createTestOneToOneWithDuplicateCascade() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne(cascade={CascadeType.MERGE, CascadeType.MERGE})");
			}
		});
	}

	public void testOneToOne() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertNotNull(oneToOne);
	}
	
	public void testGetFetch() throws Exception {
		IType testType = this.createTestOneToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
	}

	public void testSetFetch() throws Exception {
		IType testType = this.createTestOneToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToOne.getFetch());
		
		assertSourceContains("@OneToOne(fetch=LAZY)");
	}
	
	public void testSetFetchNull() throws Exception {
		IType testType = this.createTestOneToOneWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(null);
		assertNull(oneToOne.getFetch());
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("fetch");
	}
	
	
	public void testGetTargetEntity() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
		
		oneToOne.setTargetEntity("Foo");
		
		assertSourceContains("@OneToOne(targetEntity=Foo.class)");
	}
	
	public void testSetTargetEntityNull() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
		
		oneToOne.setTargetEntity(null);
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("targetEntity");
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, oneToOne.getFullyQualifiedTargetEntity());
		
		oneToOne.setTargetEntity("Foo");
		
		assertSourceContains("@OneToOne(targetEntity=Foo.class)");
		
		assertEquals("Foo", oneToOne.getTargetEntity());
		
		assertNull(oneToOne.getFullyQualifiedTargetEntity());
	}
	
	public void testGetOptional() throws Exception {
		IType testType = this.createTestOneToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.getOptional());
	}

	public void testSetOptional() throws Exception {
		IType testType = this.createTestOneToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.getOptional());
		
		oneToOne.setOptional(false);
		assertFalse(oneToOne.getOptional());
		
		assertSourceContains("@OneToOne(optional=false)");
	}
	
	public void testSetOptionalNull() throws Exception {
		IType testType = this.createTestOneToOneWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.getOptional());
		
		oneToOne.setOptional(null);
		assertNull(oneToOne.getOptional());
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("optional");
	}
	
	public void testGetMappedBy() throws Exception {
		IType testType = this.createTestOneToOneWithMappedBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals("foo", oneToOne.getMappedBy());
	}

	public void testGetMappedByNull() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(null, oneToOne.getMappedBy());
	}

	public void testSetMappedBy() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertNull(oneToOne.getMappedBy());
		oneToOne.setMappedBy("bar");
		assertEquals("bar", oneToOne.getMappedBy());
		
		assertSourceContains("@OneToOne(mappedBy=\"bar\")");
	}
	
	public void testSetMappedByNull() throws Exception {
		IType testType = this.createTestOneToOneWithMappedBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals("foo", oneToOne.getMappedBy());
		
		oneToOne.setMappedBy(null);
		assertNull(oneToOne.getMappedBy());
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("mappedBy");
	}

	public void testSetCascadeAll() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeAll());
	
		oneToOne.setCascadeAll(true);
		assertSourceContains("@OneToOne(cascade=ALL)");
		
		assertTrue(oneToOne.isCascadeAll());
	}
	
	public void testSetCascadeMerge() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeMerge());
	
		oneToOne.setCascadeMerge(true);
		assertSourceContains("@OneToOne(cascade=MERGE)");
		
		assertTrue(oneToOne.isCascadeMerge());
	}
	
	public void testSetCascadePersist() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadePersist());
	
		oneToOne.setCascadePersist(true);
		assertSourceContains("@OneToOne(cascade=PERSIST)");
		
		assertTrue(oneToOne.isCascadePersist());
	}
	
	public void testSetCascadeRemove() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeRemove());
	
		oneToOne.setCascadeRemove(true);
		assertSourceContains("@OneToOne(cascade=REMOVE)");
		
		assertTrue(oneToOne.isCascadeRemove());
	}

	public void testSetCascadeRefresh() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertFalse(oneToOne.isCascadeRefresh());
	
		oneToOne.setCascadeRefresh(true);
		assertSourceContains("@OneToOne(cascade=REFRESH)");
		
		assertTrue(oneToOne.isCascadeRefresh());
	}

	public void testCascadeMoreThanOnce() throws Exception {
		IType testType = this.createTestOneToOneWithCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.isCascadeAll());
		
		oneToOne.setCascadeAll(true);
		assertTrue(oneToOne.isCascadeAll());
		//a second CascadeType.All should not have been added
		assertSourceContains("@OneToOne(cascade=CascadeType.ALL)");
		
		oneToOne.setCascadeAll(false);
		assertFalse(oneToOne.isCascadeAll());
		
		assertSourceDoesNotContain("cascade");
		
		//test setting cascadeAll to false again, should just do nothing
		oneToOne.setCascadeAll(false);
		assertFalse(oneToOne.isCascadeAll());
		
		assertSourceDoesNotContain("cascade");
	}
	
	public void testDuplicateCascade() throws Exception {
		IType testType = this.createTestOneToOneWithDuplicateCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.isCascadeMerge());
		
		oneToOne.setCascadeMerge(false);//TODO should the resource model handle this and remove both MERGE 
										//settings instead of having to set it false twice?
		assertTrue(oneToOne.isCascadeMerge());
		
		oneToOne.setCascadeMerge(false);
		assertFalse(oneToOne.isCascadeMerge());
		
		assertSourceDoesNotContain("cascade");
	}
	
	public void testMultipleCascade() throws Exception {
		IType testType = this.createTestOneToOneWithMultipleCascade();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.isCascadeMerge());
		assertTrue(oneToOne.isCascadeRemove());
		
		oneToOne.setCascadeMerge(false);
		assertSourceContains("@OneToOne(cascade=REMOVE)");
		
		oneToOne.setCascadeRemove(false);		
		assertSourceDoesNotContain("cascade");
	}
	
}
