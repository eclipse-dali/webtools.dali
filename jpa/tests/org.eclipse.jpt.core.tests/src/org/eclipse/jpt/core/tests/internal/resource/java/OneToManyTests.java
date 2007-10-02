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
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OneToManyTests extends AnnotationTestCase {
	
	public OneToManyTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private IType createTestOneToMany() throws Exception {
		this.createAnnotationAndMembers("OneToMany", "FetchType fetch() default FetchType.LAZY;");
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
		this.createEnum("FetchType", "EAGER, LAZY");
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
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, oneToMany.getFullyQualfiedTargetEntity());
		
		oneToMany.setTargetEntity("Foo");
		
		assertSourceContains("@OneToMany(targetEntity=Foo.class)");
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		assertEquals("Foo", oneToMany.getTargetEntity());
		
		assertNull(oneToMany.getFullyQualfiedTargetEntity());
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

}
