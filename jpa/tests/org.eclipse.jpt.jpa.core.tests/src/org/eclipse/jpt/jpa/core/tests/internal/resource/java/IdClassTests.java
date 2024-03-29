/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class IdClassTests extends JpaJavaResourceModelTestCase {

	private static final String ID_CLASS_VALUE = "MyClass";
	
	public IdClassTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestIdClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ID_CLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@IdClass");
			}
		});
	}
	
	private ICompilationUnit createTestIdClassWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ID_CLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@IdClass(" + ID_CLASS_VALUE + ".class)");
			}
		});
	}

	public void testIdClass() throws Exception {
		ICompilationUnit cu = this.createTestIdClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		IdClassAnnotation idClass = (IdClassAnnotation) resourceType.getAnnotation(JPA.ID_CLASS);
		assertNotNull(idClass);
		assertNull(idClass.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestIdClassWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		IdClassAnnotation idClass = (IdClassAnnotation) resourceType.getAnnotation(JPA.ID_CLASS);
		assertEquals(ID_CLASS_VALUE, idClass.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestIdClassWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		IdClassAnnotation idClass = (IdClassAnnotation) resourceType.getAnnotation(JPA.ID_CLASS);
		assertEquals(ID_CLASS_VALUE, idClass.getValue());
		
		idClass.setValue("foo");
		assertEquals("foo", idClass.getValue());
		
		assertSourceContains("@IdClass(foo.class)", cu);
		
		idClass.setValue(null);
		
		assertSourceDoesNotContain("@IdClass(", cu);
	}
	
	public void testGetFullyQualifiedClass() throws Exception {
		ICompilationUnit cu = this.createTestIdClassWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		IdClassAnnotation idClass = (IdClassAnnotation) resourceType.getAnnotation(JPA.ID_CLASS);
		assertNotNull(idClass.getValue());
		assertEquals("MyClass", idClass.getFullyQualifiedClassName()); //bug 196200 changed this


		idClass.setValue(TYPE_NAME);
				
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, idClass.getFullyQualifiedClassName());				
		assertSourceContains("@IdClass(" + TYPE_NAME + ".class)", cu);
	}

}
