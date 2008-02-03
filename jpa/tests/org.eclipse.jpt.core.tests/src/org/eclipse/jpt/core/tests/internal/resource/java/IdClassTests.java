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
import org.eclipse.jpt.core.internal.resource.java.IdClass;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class IdClassTests extends JavaResourceModelTestCase {

	private static final String ID_CLASS_VALUE = "MyClass";
	
	public IdClassTests(String name) {
		super(name);
	}

	private void createIdClassAnnotation() throws Exception {
		this.createAnnotationAndMembers("IdClass", "Class value();");
	}
	
	private IType createTestIdClass() throws Exception {
		createIdClassAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ID_CLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@IdClass");
			}
		});
	}
	
	private IType createTestIdClassWithValue() throws Exception {
		createIdClassAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ID_CLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@IdClass(" + ID_CLASS_VALUE + ".class)");
			}
		});
	}

	public void testIdClass() throws Exception {
		IType testType = this.createTestIdClass();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		IdClass idClass = (IdClass) typeResource.annotation(JPA.ID_CLASS);
		assertNotNull(idClass);
		assertNull(idClass.getValue());
	}

	public void testGetValue() throws Exception {
		IType testType = this.createTestIdClassWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		
		IdClass idClass = (IdClass) typeResource.annotation(JPA.ID_CLASS);
		assertEquals(ID_CLASS_VALUE, idClass.getValue());
	}

	public void testSetValue() throws Exception {
		IType testType = this.createTestIdClassWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		
		IdClass idClass = (IdClass) typeResource.annotation(JPA.ID_CLASS);
		assertEquals(ID_CLASS_VALUE, idClass.getValue());
		
		idClass.setValue("foo");
		assertEquals("foo", idClass.getValue());
		
		assertSourceContains("@IdClass(foo.class)");
		
		idClass.setValue(null);
		
		assertSourceDoesNotContain("@IdClass");
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		
		idClass = (IdClass) typeResource.annotation(JPA.ID_CLASS);
		assertNull(idClass);
	}
	
	public void testGetFullyQualifiedClass() throws Exception {
		IType testType = this.createTestIdClassWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);

		IdClass idClass = (IdClass) typeResource.annotation(JPA.ID_CLASS);
		assertNotNull(idClass.getValue());
		assertNull(idClass.getFullyQualifiedClass());


		idClass.setValue(TYPE_NAME);
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, idClass.getFullyQualifiedClass());
				
		assertSourceContains("@IdClass(" + TYPE_NAME + ".class)");

	}

}
