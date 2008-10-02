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
import org.eclipse.jpt.utility.JavaType;
import org.eclipse.jpt.utility.internal.SimpleJavaType;

public class JavaTypeTests extends TestCase {

	public JavaTypeTests(String name) {
		super(name);
	}

	public void testInvalidElementTypeNull() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new SimpleJavaType(null, 0);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidElementTypeEmpty() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new SimpleJavaType("", 0);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidElementTypeArray() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new SimpleJavaType(java.lang.Object[].class.getName(), 0);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidArrayDepthNegative() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new SimpleJavaType(java.lang.Object.class.getName(), -2);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidVoidArray() throws Exception {
		boolean exCaught = false;
		try {
			JavaType javaType = new SimpleJavaType(void.class.getName(), 2);
			fail("invalid JavaType: " + javaType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testElementTypeName() throws Exception {
		JavaType javaType;
		javaType = new SimpleJavaType(java.lang.Object.class);
		assertEquals("java.lang.Object", javaType.getElementTypeName());

		javaType = new SimpleJavaType(java.lang.Object[].class);
		assertEquals("java.lang.Object", javaType.getElementTypeName());

		javaType = new SimpleJavaType(int.class);
		assertEquals("int", javaType.getElementTypeName());

		javaType = new SimpleJavaType(int[].class);
		assertEquals("int", javaType.getElementTypeName());

		javaType = new SimpleJavaType(void.class);
		assertEquals("void", javaType.getElementTypeName());

		javaType = new SimpleJavaType(java.util.Map.Entry.class);
		assertEquals("java.util.Map$Entry", javaType.getElementTypeName());

		javaType = new SimpleJavaType(java.util.Map.Entry[][].class);
		assertEquals("java.util.Map$Entry", javaType.getElementTypeName());
	}

	public void testArrayDepth() throws Exception {
		JavaType javaType;
		javaType = new SimpleJavaType(java.lang.Object.class);
		assertEquals(0, javaType.getArrayDepth());

		javaType = new SimpleJavaType(java.lang.Object[].class);
		assertEquals(1, javaType.getArrayDepth());

		javaType = new SimpleJavaType(int.class);
		assertEquals(0, javaType.getArrayDepth());

		javaType = new SimpleJavaType(int[].class);
		assertEquals(1, javaType.getArrayDepth());

		javaType = new SimpleJavaType(void.class);
		assertEquals(0, javaType.getArrayDepth());

		javaType = new SimpleJavaType(java.util.Map.Entry.class);
		assertEquals(0, javaType.getArrayDepth());

		javaType = new SimpleJavaType(java.util.Map.Entry[][].class);
		assertEquals(2, javaType.getArrayDepth());
	}

	public void testIsArray() throws Exception {
		JavaType javaType;
		javaType = new SimpleJavaType(java.lang.Object.class);
		assertFalse(javaType.isArray());

		javaType = new SimpleJavaType(java.lang.Object[].class);
		assertTrue(javaType.isArray());

		javaType = new SimpleJavaType(int.class);
		assertFalse(javaType.isArray());

		javaType = new SimpleJavaType(int[].class);
		assertTrue(javaType.isArray());

		javaType = new SimpleJavaType(void.class);
		assertFalse(javaType.isArray());

		javaType = new SimpleJavaType(java.util.Map.Entry.class);
		assertFalse(javaType.isArray());

		javaType = new SimpleJavaType(java.util.Map.Entry[][].class);
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
		JavaType javaType = new SimpleJavaType(javaClass);
		assertEquals(javaClass, javaType.getJavaClass());
	}

	public void testJavaClassName() throws Exception {
		JavaType javaType;
		javaType = new SimpleJavaType(java.lang.Object.class);
		assertEquals("java.lang.Object", javaType.getJavaClassName());

		javaType = new SimpleJavaType(java.lang.Object[].class);
		assertEquals("[Ljava.lang.Object;", javaType.getJavaClassName());

		javaType = new SimpleJavaType(int.class);
		assertEquals("int", javaType.getJavaClassName());

		javaType = new SimpleJavaType(int[].class);
		assertEquals("[I", javaType.getJavaClassName());

		javaType = new SimpleJavaType(void.class);
		assertEquals("void", javaType.getJavaClassName());

		javaType = new SimpleJavaType(java.util.Map.Entry.class);
		assertEquals("java.util.Map$Entry", javaType.getJavaClassName());

		javaType = new SimpleJavaType(java.util.Map.Entry[][].class);
		assertEquals("[[Ljava.util.Map$Entry;", javaType.getJavaClassName());
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
		JavaType javaType = new SimpleJavaType(javaClass);
		assertTrue(javaType.describes(javaClass));
	}

	public void testDeclaration() throws Exception {
		JavaType javaType;
		javaType = new SimpleJavaType(java.lang.Object.class);
		assertEquals("java.lang.Object", javaType.declaration());

		javaType = new SimpleJavaType(java.lang.Object[].class);
		assertEquals("java.lang.Object[]", javaType.declaration());

		javaType = new SimpleJavaType(int.class);
		assertEquals("int", javaType.declaration());

		javaType = new SimpleJavaType(int[].class);
		assertEquals("int[]", javaType.declaration());

		javaType = new SimpleJavaType(void.class);
		assertEquals("void", javaType.declaration());

		javaType = new SimpleJavaType(java.util.Map.Entry.class);
		assertEquals("java.util.Map.Entry", javaType.declaration());

		javaType = new SimpleJavaType(java.util.Map.Entry[][].class);
		assertEquals("java.util.Map.Entry[][]", javaType.declaration());
	}

	public void testIsPrimitive() throws Exception {
		JavaType javaType;
		javaType = new SimpleJavaType(java.lang.Object.class);
		assertFalse(javaType.isPrimitive());

		javaType = new SimpleJavaType(java.lang.Object[].class);
		assertFalse(javaType.isPrimitive());

		javaType = new SimpleJavaType(int.class);
		assertTrue(javaType.isPrimitive());

		javaType = new SimpleJavaType(int[].class);
		assertFalse(javaType.isPrimitive());

		javaType = new SimpleJavaType(void.class);
		assertTrue(javaType.isPrimitive());

		javaType = new SimpleJavaType(java.util.Map.Entry.class);
		assertFalse(javaType.isPrimitive());

		javaType = new SimpleJavaType(java.util.Map.Entry[][].class);
		assertFalse(javaType.isPrimitive());
	}

}
