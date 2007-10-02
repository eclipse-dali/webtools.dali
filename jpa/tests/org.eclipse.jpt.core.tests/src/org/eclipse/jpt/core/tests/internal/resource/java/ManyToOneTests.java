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
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ManyToOneTests extends AnnotationTestCase {
	
	public ManyToOneTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private IType createTestManyToOne() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "FetchType fetch() default FetchType.LAZY;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToOne");
			}
		});
	}
	
	private IType createTestManyToOneWithFetch() throws Exception {
		this.createAnnotationAndMembers("ManyToOne", "FetchType fetch() default FetchType.LAZY;");
		this.createEnum("FetchType", "EAGER, LAZY");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MANY_TO_ONE, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@ManyToOne(fetch=FetchType.EAGER)");
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

	public void testManyToOne() throws Exception {
		IType testType = this.createTestManyToOne();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation(JPA.MANY_TO_ONE);
		assertNotNull(manyToOne);
	}
	
	public void testGetFetch() throws Exception {
		IType testType = this.createTestManyToOneWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(FetchType.EAGER, manyToOne.getFetch());
	}

	public void testSetFetch() throws Exception {
		IType testType = this.createTestManyToOneWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(FetchType.EAGER, manyToOne.getFetch());
		
		manyToOne.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, manyToOne.getFetch());
		
		assertSourceContains("@ManyToOne(fetch=LAZY)");
	}
	
	public void testSetFetchNull() throws Exception {
		IType testType = this.createTestManyToOneWithFetch();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation(JPA.MANY_TO_ONE);
		assertEquals(FetchType.EAGER, manyToOne.getFetch());
		
		manyToOne.setFetch(null);
		assertNull(manyToOne.getFetch());
		
		assertSourceContains("@ManyToOne");
		assertSourceDoesNotContain("fetch");
	}
}
