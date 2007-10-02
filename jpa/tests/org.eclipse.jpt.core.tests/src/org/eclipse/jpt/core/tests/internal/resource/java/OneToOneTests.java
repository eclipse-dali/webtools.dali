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
import org.eclipse.jpt.core.internal.resource.java.OneToOne;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase.DefaultAnnotationWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OneToOneTests extends AnnotationTestCase {
	
	public OneToOneTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private IType createTestOneToOne() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "FetchType fetch() default FetchType.LAZY;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToOne");
			}
		});
	}
	
	private IType createTestOneToOneWithFetch() throws Exception {
		this.createAnnotationAndMembers("OneToOne", "FetchType fetch() default FetchType.LAZY;");
		this.createEnum("FetchType", "EAGER, LAZY");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ONE_TO_ONE, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@OneToOne(mappedBy=\"foo\")");
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

	public void testOneToOne() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertNotNull(oneToOne);
	}
	
	public void testGetFetch() throws Exception {
		IType testType = this.createTestOneToOneWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
	}

	public void testSetFetch() throws Exception {
		IType testType = this.createTestOneToOneWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToOne.getFetch());
		
		assertSourceContains("@OneToOne(fetch=LAZY)");
	}
	
	public void testSetFetchNull() throws Exception {
		IType testType = this.createTestOneToOneWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(null);
		assertNull(oneToOne.getFetch());
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("fetch");
	}
	
	
	public void testGetTargetEntity() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
	}
	
	public void testSetTargetEntity() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
		
		oneToOne.setTargetEntity("Foo");
		
		assertSourceContains("@OneToOne(targetEntity=Foo.class)");
	}
	
	public void testSetTargetEntityNull() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(TYPE_NAME, oneToOne.getTargetEntity());
		
		oneToOne.setTargetEntity(null);
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("targetEntity");
	}
	
	
	public void testGetFullyQualifiedTargetEntity() throws Exception {
		IType testType = this.createTestOneToOneWithTargetEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, oneToOne.getFullyQualfiedTargetEntity());
		
		oneToOne.setTargetEntity("Foo");
		
		assertSourceContains("@OneToOne(targetEntity=Foo.class)");
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		assertEquals("Foo", oneToOne.getTargetEntity());
		
		assertNull(oneToOne.getFullyQualfiedTargetEntity());
	}
	
	public void testGetOptional() throws Exception {
		IType testType = this.createTestOneToOneWithOptional();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.getOptional());
	}

	public void testSetOptional() throws Exception {
		IType testType = this.createTestOneToOneWithOptional();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.getOptional());
		
		oneToOne.setOptional(false);
		assertFalse(oneToOne.getOptional());
		
		assertSourceContains("@OneToOne(optional=false)");
	}
	
	public void testSetOptionalNull() throws Exception {
		IType testType = this.createTestOneToOneWithOptional();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertTrue(oneToOne.getOptional());
		
		oneToOne.setOptional(null);
		assertNull(oneToOne.getOptional());
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("optional");
	}
	
	public void testGetMappedBy() throws Exception {
		IType testType = this.createTestOneToOneWithMappedBy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals("foo", oneToOne.getMappedBy());
	}

	public void testGetMappedByNull() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals(null, oneToOne.getMappedBy());
	}

	public void testSetMappedBy() throws Exception {
		IType testType = this.createTestOneToOne();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertNull(oneToOne.getMappedBy());
		oneToOne.setMappedBy("bar");
		assertEquals("bar", oneToOne.getMappedBy());
		
		assertSourceContains("@OneToOne(mappedBy=\"bar\")");
	}
	
	public void testSetMappedByNull() throws Exception {
		IType testType = this.createTestOneToOneWithMappedBy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation(JPA.ONE_TO_ONE);
		assertEquals("foo", oneToOne.getMappedBy());
		
		oneToOne.setMappedBy(null);
		assertNull(oneToOne.getMappedBy());
		
		assertSourceContains("@OneToOne");
		assertSourceDoesNotContain("mappedBy");
	}

}
