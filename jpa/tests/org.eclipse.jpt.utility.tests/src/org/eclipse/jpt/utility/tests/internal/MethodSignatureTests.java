/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.JavaType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.SimpleJavaType;
import org.eclipse.jpt.utility.internal.SimpleMethodSignature;

@SuppressWarnings("nls")
public class MethodSignatureTests extends TestCase {

	public MethodSignatureTests(String name) {
		super(name);
	}

	public void testInvalidNameNull() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature((String) null);
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidNameEmpty() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("");
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidParameterTypesNull() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("foo", (JavaType[]) null);
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidParameterTypesNullItem() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("foo", new JavaType[1]);
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidParameterTypesVoidItem() throws Exception {
		JavaType jt = new SimpleJavaType(void.class.getName());
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("foo", new JavaType[] {jt});
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidParameterTypeNamesNull() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("foo", (String[]) null);
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidParameterTypeNamesNullItem() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("foo", new String[1]);
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidParameterJavaClassesNull() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("foo", (Class<?>[]) null);
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidParameterJavaClassesNullItem() throws Exception {
		boolean exCaught = false;
		try {
			MethodSignature methodSignature = new SimpleMethodSignature("foo", new Class[1]);
			fail("invalid MethodSignature: " + methodSignature);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testGetSignature0() throws Exception {
		MethodSignature ms = new SimpleMethodSignature(this.getMethod("method0"));
		assertEquals("method0()", ms.getSignature());
	}

	public void testGetSignature1() throws Exception {
		MethodSignature ms = new SimpleMethodSignature(this.getMethod("method1"));
		assertEquals("method1(int)", ms.getSignature());
	}

	public void testGetSignature2() throws Exception {
		MethodSignature ms = new SimpleMethodSignature(this.getMethod("method2"));
		assertEquals("method2(int, java.lang.String)", ms.getSignature());
	}

	public void testGetSignature3() throws Exception {
		MethodSignature ms = new SimpleMethodSignature(this.getMethod("method3"));
		assertEquals("method3(int, java.lang.String, java.lang.Object[][])", ms.getSignature());
	}

	public void testGetName() throws Exception {
		MethodSignature ms = new SimpleMethodSignature(this.getMethod("method2"));
		assertEquals("method2", ms.getName());
	}

	public void testGetParameterTypes() throws Exception {
		MethodSignature ms = new SimpleMethodSignature(this.getMethod("method3"));
		JavaType[] expected = new JavaType[3];
		expected[0] = new SimpleJavaType("int");
		expected[1] = new SimpleJavaType("java.lang.String");
		expected[2] = new SimpleJavaType("java.lang.Object", 2);
		assertTrue(Arrays.equals(expected, ms.getParameterTypes()));
	}

	public void testEquals() throws Exception {
		Object ms1 = new SimpleMethodSignature(this.getMethod("method3"));
		Object ms2 = new SimpleMethodSignature(this.getMethod("method3"));
		assertNotSame(ms1, ms2);
		assertEquals(ms1, ms1);
		assertEquals(ms1, ms2);
		assertEquals(ms1.hashCode(), ms2.hashCode());

		Object ms3 = new SimpleMethodSignature(this.getMethod("method2"));
		assertNotSame(ms1, ms3);
		assertFalse(ms1.equals(ms3));
	}

	public void testClone() throws Exception {
		SimpleMethodSignature ms1 = new SimpleMethodSignature(this.getMethod("method3"));
		SimpleMethodSignature ms2 = (SimpleMethodSignature) ms1.clone();
		assertNotSame(ms1, ms2);
		assertEquals(ms1, ms2);
	}

	public void testSerialization() throws Exception {
		SimpleMethodSignature ms1 = new SimpleMethodSignature(this.getMethod("method3"));
		SimpleMethodSignature ms2 = TestTools.serialize(ms1);
		assertNotSame(ms1, ms2);
		assertEquals(ms1, ms2);
	}

	private Method getMethod(String methodName) {
		for (Method method : this.getClass().getMethods()) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		throw new IllegalArgumentException("method not found: " + methodName);
	}

	public void method0() { /* used by tests */ }
	@SuppressWarnings("unused") public void method1(int foo) { /* used by tests */ }
	@SuppressWarnings("unused") public void method2(int foo, String bar) { /* used by tests */ }
	@SuppressWarnings("unused") public void method3(int foo, String bar, Object[][] baz) { /* used by tests */ }

	@SuppressWarnings("unused") public void methodA(int foo, String bar) { /* used by tests */ }
	@SuppressWarnings("unused") public void methodA(int foo, String bar, String baz) { /* used by tests */ }

	@SuppressWarnings("unused") public void methodB(int foo, Object bar) { /* used by tests */ }
	@SuppressWarnings("unused") public void methodB(int foo, String bar) { /* used by tests */ }

}
