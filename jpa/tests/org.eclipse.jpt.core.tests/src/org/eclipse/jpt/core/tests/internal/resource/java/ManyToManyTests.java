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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.resource.java.FetchType;
import org.eclipse.jpt.core.internal.resource.java.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.ManyToMany;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ManyToManyTests extends AnnotationTestCase {
	
	public ManyToManyTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private IType createTestManyToMany() throws Exception {
		this.createAnnotationAndMembers("ManyToMany", "FetchType fetch() default FetchType.LAZY;");
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
		this.createEnum("FetchType", "EAGER, LAZY");
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
		this.createAnnotationAndMembers("ManyToMany", "Class targetEntity() default void.class;");
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

	protected JavaResource buildParentResource(final IJpaPlatform jpaPlatform) {
		return new JavaResource() {
			public void updateFromJava(CompilationUnit astRoot) {
			}
			public IJpaPlatform jpaPlatform() {
				return jpaPlatform;
			}
		};
	}
	
	protected IJpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}

	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = new JavaPersistentTypeResourceImpl(buildParentResource(buildJpaPlatform()), new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER));
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
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
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, manyToMany.getFullyQualfiedTargetEntity());
		
		manyToMany.setTargetEntity("Foo");
		
		assertSourceContains("@ManyToMany(targetEntity=Foo.class)");
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		assertEquals("Foo", manyToMany.getTargetEntity());
		
		assertNull(manyToMany.getFullyQualfiedTargetEntity());
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

}
