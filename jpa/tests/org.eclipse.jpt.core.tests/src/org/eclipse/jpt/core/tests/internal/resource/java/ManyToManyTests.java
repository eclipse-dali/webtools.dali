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
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.resource.java.FetchType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.ManyToMany;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ManyToManyTests extends JavaResourceModelTestCase {
	
	public ManyToManyTests(String name) {
		super(name);
	}

	private IType createTestManyToMany() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "FetchType fetch() default FetchType.LAZY; CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToMany");
			}
		});
	}
	
	private IType createTestManyToManyWithFetch() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "FetchType fetch() default FetchType.LAZY;");
		this.createEnumAndMembers("FetchType", "EAGER, LAZY");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_MANY, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToMany(fetch=FetchType.EAGER)");
			}
		});
	}
	
	private IType createTestManyToManyWithTargetEntity() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "Class targetEntity() default void.class; ");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToMany(targetEntity=AnnotationTestType.class)");
			}
		});
	}
	
	private IType createTestManyToManyWithMappedBy() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "String mappedBy() default\"\";");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToMany(mappedBy=\"foo\")");
			}
		});
	}
	
	private IType createTestManyToManyWithCascade() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToMany(cascade=CascadeType.ALL)");
			}
		});
	}
	
	private IType createTestManyToManyWithMultipleCascade() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToMany(cascade={CascadeType.MERGE, CascadeType.REMOVE})");
			}
		});
	}
	
	private IType createTestManyToManyWithDuplicateCascade() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToMany(cascade={CascadeType.MERGE, CascadeType.MERGE})");
			}
		});
	}

	public void testManyToMany() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertNotNull(manyToMany);
	}
	
	public void testGetFetch() throws Exception {
		IType testType = this.createTestManyToManyWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FetchType.EAGER, manyToMany.getFetch());
	}

	public void testSetFetch() throws Exception {
		IType testType = this.createTestManyToManyWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FetchType.EAGER, manyToMany.getFetch());
		
		manyToMany.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, manyToMany.getFetch());
		
		assertSourceContains("@ManyToMany(fetch=LAZY)");
	}
	
	public void testSetFetchNull() throws Exception {
		IType testType = this.createTestManyToManyWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FetchType.EAGER, manyToMany.getFetch());
		
		manyToMany.setFetch(null);
		assertNull(manyToMany.getFetch());
		
		assertSourceContains("@ManyToMany");
		assertSourceDoesNotContain("fetch");
	}
	
	public void testGetTargetEntity() throws Exception {
		IType testType = this.createTestManyToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(TYPE_NAME, manyToMany.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		IType testType = this.createTestManyToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(TYPE_NAME, manyToMany.getTargetEntity());
		
		manyToMany.setTargetEntity("Foo");
		
		assertSourceContains("@ManyToMany(targetEntity=Foo.class)");
	}
	
	public void testSetTargetEntityNull() throws Exception {
		IType testType = this.createTestManyToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(TYPE_NAME, manyToMany.getTargetEntity());
		
		manyToMany.setTargetEntity(null);
		
		assertSourceContains("@ManyToMany");
		assertSourceDoesNotContain("targetEntity");
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		IType testType = this.createTestManyToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, manyToMany.getFullyQualifiedTargetEntity());
		
		manyToMany.setTargetEntity("Foo");
		
		assertSourceContains("@ManyToMany(targetEntity=Foo.class)");
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		assertEquals("Foo", manyToMany.getTargetEntity());
		
		assertNull(manyToMany.getFullyQualifiedTargetEntity());
	}
	
	public void testGetMappedBy() throws Exception {
		IType testType = this.createTestManyToManyWithMappedBy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals("foo", manyToMany.getMappedBy());
	}

	public void testGetMappedByNull() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals(null, manyToMany.getMappedBy());
	}

	public void testSetMappedBy() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertNull(manyToMany.getMappedBy());
		manyToMany.setMappedBy("bar");
		assertEquals("bar", manyToMany.getMappedBy());
		
		assertSourceContains("@ManyToMany(mappedBy=\"bar\")");
	}
	
	public void testSetMappedByNull() throws Exception {
		IType testType = this.createTestManyToManyWithMappedBy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertEquals("foo", manyToMany.getMappedBy());
		
		manyToMany.setMappedBy(null);
		assertNull(manyToMany.getMappedBy());
		
		assertSourceContains("@ManyToMany");
		assertSourceDoesNotContain("mappedBy");
	}
	
	public void testSetCascadeAll() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeAll());
	
		manyToMany.setCascadeAll(true);
		assertSourceContains("@ManyToMany(cascade=ALL)");
		
		manyToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(manyToMany.isCascadeAll());
	}
	
	public void testSetCascadeMerge() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeMerge());
	
		manyToMany.setCascadeMerge(true);
		assertSourceContains("@ManyToMany(cascade=MERGE)");
		
		manyToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(manyToMany.isCascadeMerge());
	}
	
	public void testSetCascadePersist() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadePersist());
	
		manyToMany.setCascadePersist(true);
		assertSourceContains("@ManyToMany(cascade=PERSIST)");
		
		manyToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(manyToMany.isCascadePersist());
	}
	
	public void testSetCascadeRemove() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeRemove());
	
		manyToMany.setCascadeRemove(true);
		assertSourceContains("@ManyToMany(cascade=REMOVE)");
		
		manyToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(manyToMany.isCascadeRemove());
	}

	public void testSetCascadeRefresh() throws Exception {
		IType testType = this.createTestManyToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertFalse(manyToMany.isCascadeRefresh());
	
		manyToMany.setCascadeRefresh(true);
		assertSourceContains("@ManyToMany(cascade=REFRESH)");
		
		manyToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(manyToMany.isCascadeRefresh());
	}

	public void testCascadeMoreThanOnce() throws Exception {
		IType testType = this.createTestManyToManyWithCascade();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertTrue(manyToMany.isCascadeAll());
		
		manyToMany.setCascadeAll(true);
		assertTrue(manyToMany.isCascadeAll());
		//a second CascadeType.All should not have been added
		assertSourceContains("@ManyToMany(cascade=CascadeType.ALL)");
		
		manyToMany.setCascadeAll(false);
		assertFalse(manyToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade");
		
		//test setting cascadeAll to false again, should just do nothing
		manyToMany.setCascadeAll(false);
		assertFalse(manyToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade");
	}
	
	public void testDuplicateCascade() throws Exception {
		IType testType = this.createTestManyToManyWithDuplicateCascade();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertTrue(manyToMany.isCascadeMerge());
		
		manyToMany.setCascadeMerge(false); //TODO should the resource model handle this and remove both MERGE 
										  //settings instead of having to set it false twice?
		assertTrue(manyToMany.isCascadeMerge());
		
		manyToMany.setCascadeMerge(false);
		assertFalse(manyToMany.isCascadeMerge());
		
		assertSourceDoesNotContain("cascade");
	}
	
	public void testMultipleCascade() throws Exception {
		IType testType = this.createTestManyToManyWithMultipleCascade();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation(JPA.MANY_TO_MANY);
		assertTrue(manyToMany.isCascadeMerge());
		assertTrue(manyToMany.isCascadeRemove());
		
		manyToMany.setCascadeMerge(false);
		assertSourceContains("@ManyToMany(cascade=REMOVE)");
		
		manyToMany.setCascadeRemove(false);		
		assertSourceDoesNotContain("cascade");
	}
}
