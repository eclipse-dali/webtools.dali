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
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.Inheritance;
import org.eclipse.jpt.core.internal.resource.java.InheritanceType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class InheritanceTests extends AnnotationTestCase {

	public InheritanceTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private IType createTestInheritance() throws Exception {
		this.createAnnotationAndMembers("Inheritance", "InheritanceType strategy() default SINGLE_TABLE;");
		this.createEnum("InheritanceType", "SINGLE_TABLE, JOINED, TABLE_PER_CLASS");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.INHERITANCE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Inheritance");
			}
		});
	}
	
	private IType createTestInheritanceWithStrategy() throws Exception {
		this.createAnnotationAndMembers("Inheritance", "InheritanceType strategy() default SINGLE_TABLE;");
		this.createEnum("InheritanceType", "SINGLE_TABLE, JOINED, TABLE_PER_CLASS");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.INHERITANCE, JPA.INHERITANCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Inheritance(strategy=InheritanceType.JOINED)");
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

	public void testInheritance() throws Exception {
		IType testType = this.createTestInheritance();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		Inheritance inheritance = (Inheritance) typeResource.annotation(JPA.INHERITANCE);
		assertNotNull(inheritance);
	}
	
	public void testGetStrategy() throws Exception {
		IType testType = this.createTestInheritanceWithStrategy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		Inheritance inheritance = (Inheritance) typeResource.annotation(JPA.INHERITANCE);
		assertEquals(InheritanceType.JOINED, inheritance.getStrategy());
	}
	
	public void testSetStrategy() throws Exception {
		IType testType = this.createTestInheritance();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 

		Inheritance inheritance = (Inheritance) typeResource.annotation(JPA.INHERITANCE);
		inheritance.setStrategy(InheritanceType.TABLE_PER_CLASS);
		
		assertSourceContains("@Inheritance(strategy=TABLE_PER_CLASS)");
		
		inheritance.setStrategy(null);
		
		assertSourceDoesNotContain("@Inheritance");
	}

}
