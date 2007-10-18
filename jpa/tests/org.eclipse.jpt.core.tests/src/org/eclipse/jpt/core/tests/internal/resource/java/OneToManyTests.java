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
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OneToManyTests extends JavaResourceModelTestCase {
	
	public OneToManyTests(String name) {
		super(name);
	}

	private IType createTestOneToMany() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "FetchType fetch() default FetchType.LAZY; CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToMany");
			}
		});
	}
	
	private IType createTestOneToManyWithFetch() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "FetchType fetch() default FetchType.LAZY;");
		this.createEnumAndMembers("FetchType", "EAGER, LAZY");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToMany(fetch=FetchType.EAGER)");
			}
		});
	}
	
	private IType createTestOneToManyWithTargetEntity() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "Class targetEntity() default void.class;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToMany(targetEntity=AnnotationTestType.class)");
			}
		});
	}
	
	private IType createTestOneToManyWithMappedBy() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "String mappedBy() default\"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToMany(mappedBy=\"foo\")");
			}
		});
	}
	
	private IType createTestOneToManyWithCascade() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToMany(cascade=CascadeType.ALL)");
			}
		});
	}
	
	private IType createTestOneToManyWithMultipleCascade() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToMany(cascade={CascadeType.MERGE, CascadeType.REMOVE})");
			}
		});
	}
	
	private IType createTestOneToManyWithDuplicateCascade() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "CascadeType[] cascade() default = {};");
		this.createEnumAndMembers("CascadeType", "ALL, PERSIST, MERGE, REMOVE, REFRESH");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_MANY, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToMany(cascade={CascadeType.MERGE, CascadeType.MERGE})");
			}
		});
	}

	public void testOneToMany() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertNotNull(oneToMany);
	}
	
	public void testGetFetch() throws Exception {
		IType testType = this.createTestOneToManyWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FetchType.EAGER, oneToMany.getFetch());
	}

	public void testSetFetch() throws Exception {
		IType testType = this.createTestOneToManyWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FetchType.EAGER, oneToMany.getFetch());
		
		oneToMany.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToMany.getFetch());
		
		assertSourceContains("@OneToMany(fetch=LAZY)");
	}
	
	public void testSetFetchNull() throws Exception {
		IType testType = this.createTestOneToManyWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FetchType.EAGER, oneToMany.getFetch());
		
		oneToMany.setFetch(null);
		assertNull(oneToMany.getFetch());
		
		assertSourceContains("@OneToMany");
		assertSourceDoesNotContain("fetch");
	}
	
	
	public void testGetTargetEntity() throws Exception {
		IType testType = this.createTestOneToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(TYPE_NAME, oneToMany.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		IType testType = this.createTestOneToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(TYPE_NAME, oneToMany.getTargetEntity());
		
		oneToMany.setTargetEntity("Foo");
		
		assertSourceContains("@OneToMany(targetEntity=Foo.class)");
	}
	
	public void testSetTargetEntityNull() throws Exception {
		IType testType = this.createTestOneToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(TYPE_NAME, oneToMany.getTargetEntity());
		
		oneToMany.setTargetEntity(null);
		
		assertSourceContains("@OneToMany");
		assertSourceDoesNotContain("targetEntity");
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		IType testType = this.createTestOneToManyWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, oneToMany.getFullyQualifiedTargetEntity());
		
		oneToMany.setTargetEntity("Foo");
		
		assertSourceContains("@OneToMany(targetEntity=Foo.class)");
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		assertEquals("Foo", oneToMany.getTargetEntity());
		
		assertNull(oneToMany.getFullyQualifiedTargetEntity());
	}
	
	public void testGetMappedBy() throws Exception {
		IType testType = this.createTestOneToManyWithMappedBy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals("foo", oneToMany.getMappedBy());
	}


	public void testGetMappedByNull() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(null, oneToMany.getMappedBy());
	}

	public void testSetMappedBy() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertNull(oneToMany.getMappedBy());
		oneToMany.setMappedBy("bar");
		assertEquals("bar", oneToMany.getMappedBy());
		
		assertSourceContains("@OneToMany(mappedBy=\"bar\")");
	}
	
	public void testSetMappedByNull() throws Exception {
		IType testType = this.createTestOneToManyWithMappedBy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals("foo", oneToMany.getMappedBy());
		
		oneToMany.setMappedBy(null);
		assertNull(oneToMany.getMappedBy());
		
		assertSourceContains("@OneToMany");
		assertSourceDoesNotContain("mappedBy");
	}
	
	public void testSetCascadeAll() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeAll());
	
		oneToMany.setCascadeAll(true);
		assertSourceContains("@OneToMany(cascade=ALL)");
		
		oneToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(oneToMany.isCascadeAll());
	}
	
	public void testSetCascadeMerge() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeMerge());
	
		oneToMany.setCascadeMerge(true);
		assertSourceContains("@OneToMany(cascade=MERGE)");
		
		oneToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(oneToMany.isCascadeMerge());
	}
	
	public void testSetCascadePersist() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadePersist());
	
		oneToMany.setCascadePersist(true);
		assertSourceContains("@OneToMany(cascade=PERSIST)");
		
		oneToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(oneToMany.isCascadePersist());
	}
	
	public void testSetCascadeRemove() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeRemove());
	
		oneToMany.setCascadeRemove(true);
		assertSourceContains("@OneToMany(cascade=REMOVE)");
		
		oneToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(oneToMany.isCascadeRemove());
	}

	public void testSetCascadeRefresh() throws Exception {
		IType testType = this.createTestOneToMany();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertFalse(oneToMany.isCascadeRefresh());
	
		oneToMany.setCascadeRefresh(true);
		assertSourceContains("@OneToMany(cascade=REFRESH)");
		
		oneToMany.updateFromJava(JDTTools.buildASTRoot(testType));
		assertTrue(oneToMany.isCascadeRefresh());
	}

	public void testCascadeMoreThanOnce() throws Exception {
		IType testType = this.createTestOneToManyWithCascade();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertTrue(oneToMany.isCascadeAll());
		
		oneToMany.setCascadeAll(true);
		assertTrue(oneToMany.isCascadeAll());
		//a second CascadeType.All should not have been added
		assertSourceContains("@OneToMany(cascade=CascadeType.ALL)");
		
		oneToMany.setCascadeAll(false);
		assertFalse(oneToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade");
		
		//test setting cascadeAll to false again, should just do nothing
		oneToMany.setCascadeAll(false);
		assertFalse(oneToMany.isCascadeAll());
		
		assertSourceDoesNotContain("cascade");
	}
	
	public void testDuplicateCascade() throws Exception {
		IType testType = this.createTestOneToManyWithDuplicateCascade();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertTrue(oneToMany.isCascadeMerge());
		
		oneToMany.setCascadeMerge(false);//TODO should the resource model handle this and remove both MERGE 
										//settings instead of having to set it false twice?
		assertTrue(oneToMany.isCascadeMerge());
		
		oneToMany.setCascadeMerge(false);
		assertFalse(oneToMany.isCascadeMerge());
		
		assertSourceDoesNotContain("cascade");
	}
	
	public void testMultipleCascade() throws Exception {
		IType testType = this.createTestOneToManyWithMultipleCascade();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation(JPA.ONE_TO_MANY);
		assertTrue(oneToMany.isCascadeMerge());
		assertTrue(oneToMany.isCascadeRemove());
		
		oneToMany.setCascadeMerge(false);
		assertSourceContains("@OneToMany(cascade=REMOVE)");
		
		oneToMany.setCascadeRemove(false);		
		assertSourceDoesNotContain("cascade");
	}

}
