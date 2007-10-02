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
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.java.MappingAnnotation;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class MappedSuperclassTests extends AnnotationTestCase {

	public MappedSuperclassTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private IType createTestMappedSuperclass() throws Exception {
		this.createAnnotationAndMembers("MappedSuperclass", "");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@MappedSuperclass");
			}
		});
	}
	
	private IType createTestMappedSuperclassAndEntity() throws Exception {
		this.createAnnotationAndMembers("MappedSuperclass", "");
		this.createAnnotationAndMembers("Entity", "");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@MappedSuperclass");
				sb.append("@Entity");
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
		return new JavaPersistentTypeResourceImpl(buildParentResource(buildJpaPlatform()), new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER));
	}

	public void testMappedSuperclass() throws Exception {
		IType testType = this.createTestMappedSuperclass();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		
		MappingAnnotation mappingAnnotation = typeResource.mappingAnnotation();
		assertTrue(mappingAnnotation instanceof MappedSuperclass);
	}
	
	public void testMappedSuperclassAndEntity() throws Exception {
		IType testType = this.createTestMappedSuperclassAndEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		
		MappingAnnotation mappingAnnotation = typeResource.mappingAnnotation();
		assertTrue(mappingAnnotation instanceof Entity);
		
		MappedSuperclass mappedSuperclass = (MappedSuperclass) typeResource.mappingAnnotation(JPA.MAPPED_SUPERCLASS);
		assertNotNull(mappedSuperclass);
	}

}
