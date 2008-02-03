/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.JavaType;

public class JavaTypeTests extends TestCase {

	public JavaTypeTests(String name) {
		super(name);
	}

	public void testInvalidElementTypeNull() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new JavaType(null, 0);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidElementTypeEmpty() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new JavaType("", 0);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidElementTypeArray() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new JavaType(java.lang.Object[].class.getName(), 0);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidArrayDepthNegative() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new JavaType(java.lang.Object.class.getName(), -2);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidVoidArray() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new JavaType(void.class.getName(), 2);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testElementTypeName() throws Exception {
		JavaType javaType;
		javaType = new JavaType(java.lang.Object.class);
		assertEquals("java.lang.Object", javaType.elementTypeName());

		javaType = new JavaType(java.lang.Object[].class);
		assertEquals("java.lang.Object", javaType.elementTypeName());

		javaType = new JavaType(int.class);
		assertEquals("int", javaType.elementTypeName());

		javaType = new JavaType(int[].class);
		assertEquals("int", javaType.elementTypeName());

		javaType = new JavaType(void.class);
		assertEquals("void", javaType.elementTypeName());

		javaType = new JavaType(java.util.Map.Entry.class);
		assertEquals("java.util.Map$Entry", javaType.elementTypeName());

		javaType = new JavaType(java.util.Map.Entry[][].class);
		assertEquals("java.util.Map$Entry", javaType.elementTypeName());
	}

	public void testArrayDepth() throws Exception {
		JavaType javaType;
		javaType = new JavaType(java.lang.Object.class);
		assertEquals(0, javaType.arrayDepth());

		javaType = new JavaType(java.lang.Object[].class);
		assertEquals(1, javaType.arrayDepth());

		javaType = new JavaType(int.class);
		assertEquals(0, javaType.arrayDepth());

		javaType = new JavaType(int[].class);
		assertEquals(1, javaType.arrayDepth());

		javaType = new JavaType(void.class);
		assertEquals(0, javaType.arrayDepth());

		javaType = new JavaType(java.util.Map.Entry.class);
		assertEquals(0, javaType.arrayDepth());

		javaType = new JavaType(java.util.Map.Entry[][].class);
		assertEquals(2, javaType.arrayDepth());
	}

	public void testIsArray() throws Exception {
		JavaType javaType;
		javaType = new JavaType(java.lang.Object.class);
		assertFalse(javaType.isArray());

		javaType = new JavaType(java.lang.Object[].class);
		assertTrue(javaType.isArray());

		javaType = new JavaType(int.class);
		assertFalse(javaType.isArray());

		javaType = new JavaType(int[].class);
		assertTrue(javaType.isArray());

		javaType = new JavaType(void.class);
		assertFalse(javaType.isArray());

		javaType = new JavaType(java.util.Map.Entry.class);
		assertFalse(javaType.isArray());

		javaType = new JavaType(java.util.Map.Entry[][].class);
		assertTrue(javaType.isArray());
	}

	public void testJavaClass() throws Exception {
		this.verifyJavaClass(java.lang.Object.class);
		this.verifyJavaClass(java.lang.Object[].class);
		this.verifyJavaClass(int.class);
		this.verifyJavaClass(int[].class);
		this.verifyJavaClass(void.class);
		this.verifyJavaClass(java.util.Map.Entry.class);
		this.verifyJavaClass(java.util.Map.Entry[][].class);
	}

	private void verifyJavaClass(Class<?> javaClass) throws Exception {
		JavaType javaType = new JavaType(javaClass);
		assertEquals(javaClass, javaType.javaClass());
	}

	public void testJavaClassName() throws Exception {
		JavaType javaType;
		javaType = new JavaType(java.lang.Object.class);
		assertEquals("java.lang.Object", javaType.javaClassName());

		javaType = new JavaType(java.lang.Object[].class);
		assertEquals("[Ljava.lang.Object;", javaType.javaClassName());

		javaType = new JavaType(int.class);
		assertEquals("int", javaType.javaClassName());

		javaType = new JavaType(int[].class);
		assertEquals("[I", javaType.javaClassName());

		javaType = new JavaType(void.class);
		assertEquals("void", javaType.javaClassName());

		javaType = new JavaType(java.util.Map.Entry.class);
		assertEquals("java.util.Map$Entry", javaType.javaClassName());

		javaType = new JavaType(java.util.Map.Entry[][].class);
		assertEquals("[[Ljava.util.Map$Entry;", javaType.javaClassName());
	}

	public void testDescribes() throws Exception {
		this.verifyDescribes(java.lang.Object.class);
		this.verifyDescribes(java.lang.Object[].class);
		this.verifyDescribes(int.class);
		this.verifyDescribes(int[].class);
		this.verifyDescribes(void.class);
		this.verifyDescribes(java.util.Map.Entry.class);
		this.verifyDescribes(java.util.Map.Entry[][].class);
	}

	private void verifyDescribes(Class<?> javaClass) throws Exception {
		JavaType javaType = new JavaType(javaClass);
		assertTrue(javaType.describes(javaClass));
	}

	public void testDeclaration() throws Exception {
		JavaType javaType;
		javaType = new JavaType(java.lang.Object.class);
		assertEquals("java.lang.Object", javaType.declaration());

		javaType = new JavaType(java.lang.Object[].class);
		assertEquals("java.lang.Object[]", javaType.declaration());

		javaType = new JavaType(int.class);
		assertEquals("int", javaType.declaration());

		javaType = new JavaType(int[].class);
		assertEquals("int[]", javaType.declaration());

		javaType = new JavaType(void.class);
		assertEquals("void", javaType.declaration());

		javaType = new JavaType(java.util.Map.Entry.class);
		assertEquals("java.util.Map.Entry", javaType.declaration());

		javaType = new JavaType(java.util.Map.Entry[][].class);
		assertEquals("java.util.Map.Entry[][]", javaType.declaration());
	}

	public void testIsPrimitive() throws Exception {
		JavaType javaType;
		javaType = new JavaType(java.lang.Object.class);
		assertFalse(javaType.isPrimitive());

		javaType = new JavaType(java.lang.Object[].class);
		assertFalse(javaType.isPrimitive());

		javaType = new JavaType(int.class);
		assertTrue(javaType.isPrimitive());

		javaType = new JavaType(int[].class);
		assertFalse(javaType.isPrimitive());

		javaType = new JavaType(void.class);
		assertTrue(javaType.isPrimitive());

		javaType = new JavaType(java.util.Map.Entry.class);
		assertFalse(javaType.isPrimitive());

		javaType = new JavaType(java.util.Map.Entry[][].class);
		assertFalse(javaType.isPrimitive());
	}

}
