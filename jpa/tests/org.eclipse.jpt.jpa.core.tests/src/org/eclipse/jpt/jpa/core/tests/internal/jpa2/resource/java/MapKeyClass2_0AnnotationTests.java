/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;

@SuppressWarnings("nls")
public class MapKeyClass2_0AnnotationTests extends JavaResourceModel2_0TestCase {

	private static final String MAP_KEY_CLASS_VALUE = "MyClass";
	
	public MapKeyClass2_0AnnotationTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestMapKeyClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_CLASS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyClass");
			}
		});
	}
	
	private ICompilationUnit createTestMapKeyClassWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_CLASS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyClass(" + MAP_KEY_CLASS_VALUE + ".class)");
			}
		});
	}

	public void testMapKeyClass() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClass();
		JavaResourcePersistentType resourceType = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute resourceAttribute = resourceType.fields().next();

		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertNotNull(mapKeyClass);
		assertNull(mapKeyClass.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithValue();
		JavaResourcePersistentType resourceType = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute resourceAttribute = resourceType.fields().next();

		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertEquals(MAP_KEY_CLASS_VALUE, mapKeyClass.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithValue();
		JavaResourcePersistentType resourceType = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute resourceAttribute = resourceType.fields().next();

		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertEquals(MAP_KEY_CLASS_VALUE, mapKeyClass.getValue());
		
		mapKeyClass.setValue("foo");
		assertEquals("foo", mapKeyClass.getValue());
		
		assertSourceContains("@MapKeyClass(foo.class)", cu);
		
		mapKeyClass.setValue(null);
		
		assertSourceDoesNotContain("@MapKeyClass(", cu);
	}
	
	public void testGetFullyQualifiedClass() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithValue();
		JavaResourcePersistentType resourceType = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute resourceAttribute = resourceType.fields().next();

		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertNotNull(mapKeyClass.getValue());
		assertEquals("MyClass", mapKeyClass.getFullyQualifiedClassName()); //bug 196200 changed this


		mapKeyClass.setValue(TYPE_NAME);
				
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, mapKeyClass.getFullyQualifiedClassName());				
		assertSourceContains("@MapKeyClass(" + TYPE_NAME + ".class)", cu);
	}

}
