/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClassAnnotation2_0;

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
				return IteratorTools.iterator(JPA2_0.MAP_KEY_CLASS);
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
				return IteratorTools.iterator(JPA2_0.MAP_KEY_CLASS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyClass(" + MAP_KEY_CLASS_VALUE + ".class)");
			}
		});
	}

	private ICompilationUnit createTestMapKeyClassWithPrimitiveValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.MAP_KEY_CLASS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyClass(value=int.class)");
			}
		});
	}

	public void testMapKeyClass() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapKeyClassAnnotation2_0 mapKeyClass = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertNotNull(mapKeyClass);
		assertNull(mapKeyClass.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapKeyClassAnnotation2_0 mapKeyClass = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertEquals(MAP_KEY_CLASS_VALUE, mapKeyClass.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapKeyClassAnnotation2_0 mapKeyClass = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertEquals(MAP_KEY_CLASS_VALUE, mapKeyClass.getValue());
		
		mapKeyClass.setValue("foo");
		assertEquals("foo", mapKeyClass.getValue());
		
		assertSourceContains("@MapKeyClass(foo.class)", cu);
		
		mapKeyClass.setValue(null);
		
		assertSourceDoesNotContain("@MapKeyClass(", cu);
	}
	
	public void testGetFullyQualifiedClass() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapKeyClassAnnotation2_0 mapKeyClass = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertNotNull(mapKeyClass.getValue());
		assertEquals("MyClass", mapKeyClass.getFullyQualifiedClassName()); //bug 196200 changed this


		mapKeyClass.setValue(TYPE_NAME);
				
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, mapKeyClass.getFullyQualifiedClassName());				
		assertSourceContains("@MapKeyClass(" + TYPE_NAME + ".class)", cu);
	}

	public void testGetPrimitiveValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithPrimitiveValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapKeyClassAnnotation2_0 mapKeyClass = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertEquals("int", mapKeyClass.getValue());
		assertEquals("int", mapKeyClass.getFullyQualifiedClassName());
	}

	public void testSetPrimitiveValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyClassWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapKeyClassAnnotation2_0 mapKeyClass = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_CLASS);
		assertEquals(MAP_KEY_CLASS_VALUE, mapKeyClass.getValue());

		mapKeyClass.setValue("int");
		assertEquals("int", mapKeyClass.getValue());

		assertSourceContains("@MapKeyClass(int.class)", cu);

		mapKeyClass.setValue(null);

		assertSourceDoesNotContain("@MapKeyClass(", cu);
	}
}
