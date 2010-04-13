/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.ClassName;
import org.eclipse.jpt.utility.internal.ReflectionTools;

@SuppressWarnings("nls")
public class ClassNameTests extends TestCase {

	public ClassNameTests(String name) {
		super(name);
	}

	public void testIsArray() {
		assertFalse(ClassName.isArray(int.class.getName()));
		assertTrue(ClassName.isArray(int[].class.getName()));
		assertTrue(ClassName.isArray(int[][].class.getName()));

		assertFalse(ClassName.isArray(java.lang.String.class.getName()));
		assertTrue(ClassName.isArray(java.lang.String[].class.getName()));
		assertTrue(ClassName.isArray(java.lang.String[][].class.getName()));
	}

	public void testGetElementTypeName() {
		assertEquals(java.util.Vector.class.getName(), ClassName.getElementTypeName(java.util.Vector.class.getName()));
		assertEquals(int.class.getName(), ClassName.getElementTypeName(int.class.getName()));
		assertEquals(void.class.getName(), ClassName.getElementTypeName(void.class.getName()));
		assertEquals(java.util.Vector.class.getName(), ClassName.getElementTypeName(java.util.Vector[].class.getName()));
		assertEquals(int.class.getName(), ClassName.getElementTypeName(int[].class.getName()));
		assertEquals(java.util.Vector.class.getName(), ClassName.getElementTypeName(java.util.Vector[][][].class.getName()));
		assertEquals(int.class.getName(), ClassName.getElementTypeName(int[][][].class.getName()));
	}

	public void testGetArrayDepth() {
		assertEquals(0, ClassName.getArrayDepth(java.util.Vector.class.getName()));
		assertEquals(0, ClassName.getArrayDepth(int.class.getName()));
		assertEquals(0, ClassName.getArrayDepth(void.class.getName()));
		assertEquals(1, ClassName.getArrayDepth(java.util.Vector[].class.getName()));
		assertEquals(1, ClassName.getArrayDepth(int[].class.getName()));
		assertEquals(3, ClassName.getArrayDepth(java.util.Vector[][][].class.getName()));
		assertEquals(3, ClassName.getArrayDepth(int[][][].class.getName()));
	}

	public void testGetComponentTypeName() {
		assertEquals(null, ClassName.getComponentTypeName(java.lang.Object.class.getName()));
		assertEquals(java.lang.Object.class.getName(), ClassName.getComponentTypeName(java.lang.Object[].class.getName()));
		assertEquals(java.lang.Object[].class.getName(), ClassName.getComponentTypeName(java.lang.Object[][].class.getName()));

		assertEquals(null, ClassName.getComponentTypeName(int.class.getName()));
		assertEquals(int.class.getName(), ClassName.getComponentTypeName(int[].class.getName()));
		assertEquals(int[].class.getName(), ClassName.getComponentTypeName(int[][].class.getName()));
	}

	public void testGetSimpleName() throws Exception {
		assertEquals("Object", ClassName.getSimpleName(java.lang.Object.class.getName()));
		assertEquals("Object[]", ClassName.getSimpleName(java.lang.Object[].class.getName()));
		assertEquals("Object[][]", ClassName.getSimpleName(java.lang.Object[][].class.getName()));

		assertEquals(java.util.Map.class.getSimpleName(), ClassName.getSimpleName(java.util.Map.class.getName()));
		assertEquals(java.util.Map.Entry.class.getSimpleName(), ClassName.getSimpleName(java.util.Map.Entry.class.getName()));

		assertEquals("int", ClassName.getSimpleName(int.class.getName()));
		assertEquals("int[]", ClassName.getSimpleName(int[].class.getName()));
		assertEquals("int[][]", ClassName.getSimpleName(int[][].class.getName()));

		Object anonObject = new Object() {
			// anonymous class
		};
		assertEquals("", ClassName.getSimpleName(anonObject.getClass().getName()));

		class Local {
			// anonymous class
		}
		Local localObject = new Local();
		assertEquals("Local", ClassName.getSimpleName(localObject.getClass().getName()));
	}

	public void testGetPackageName() throws Exception {
		assertEquals(java.lang.Object.class.getPackage().getName(), ClassName.getPackageName(java.lang.Object.class.getName()));
		assertEquals("", ClassName.getPackageName(java.lang.Object[].class.getName()));
		assertEquals("", ClassName.getPackageName(java.lang.Object[][].class.getName()));

		assertEquals(java.util.Map.class.getPackage().getName(), ClassName.getPackageName(java.util.Map.class.getName()));
		assertEquals(java.util.Map.Entry.class.getPackage().getName(), ClassName.getPackageName(java.util.Map.Entry.class.getName()));

		assertEquals("", ClassName.getPackageName(int.class.getName()));
		assertEquals("", ClassName.getPackageName(int[].class.getName()));
		assertEquals("", ClassName.getPackageName(int[][].class.getName()));

		assertEquals("", ClassName.getPackageName(void.class.getName()));

		Object anonObject = new Object() {
			// anonymous class
		};
		assertEquals(anonObject.getClass().getPackage().getName(), ClassName.getPackageName(anonObject.getClass().getName()));
	}

	public void testIsTopLevel() throws Exception {
		assertTrue(ClassName.isTopLevel(java.util.Map.class.getName())); // top-level
		assertFalse(ClassName.isTopLevel(java.util.Map.Entry.class.getName())); // member
		assertFalse(ClassName.isTopLevel(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertFalse(ClassName.isTopLevel(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassName.isTopLevel(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassName.isTopLevel(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassName.isTopLevel(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassName.isTopLevel(array.getClass().getName()));
	}

	public void testIsMember() throws Exception {
		assertFalse(ClassName.isMember(java.util.Map.class.getName())); // top-level
		assertTrue(ClassName.isMember(java.util.Map.Entry.class.getName())); // member
		assertFalse(ClassName.isMember(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertFalse(ClassName.isMember(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassName.isMember(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassName.isMember(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassName.isMember(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassName.isMember(array.getClass().getName()));

		// test a few edge cases
		assertTrue(ClassName.isMember("java.util.Map$a1"));
		assertTrue(ClassName.isMember("java.util.Map$1aa$aaa"));  // member inside local
		assertTrue(ClassName.isMember("java.util.Map$1$aaa"));  // member inside anonymous
		assertTrue(ClassName.isMember("java.util.Map$a1$aaa$bbb"));
		assertTrue(ClassName.isMember("java.util.Map$1a1$aaa"));  // member inside local
		assertFalse(ClassName.isMember("java.util.Map$1a"));
		assertTrue(ClassName.isMember("java.util.Map$a12345$b12345"));
		assertFalse(ClassName.isMember("java.util.Map$12345a"));
		assertFalse(ClassName.isMember("java.util.Map$333"));
		assertFalse(ClassName.isMember("java.util.Map3$333"));
	}

	public void testIsLocal() throws Exception {
		class LocalClass {
			void foo() {
				System.getProperty("foo");
			}
		}
		new LocalClass().foo();
		assertFalse(ClassName.isLocal(java.util.Map.class.getName())); // top-level
		assertFalse(ClassName.isLocal(java.util.Map.Entry.class.getName())); // member
		assertTrue(ClassName.isLocal(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertFalse(ClassName.isLocal(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassName.isLocal(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassName.isLocal(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassName.isLocal(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassName.isLocal(array.getClass().getName()));

		// test a few edge cases
		assertFalse(ClassName.isLocal("java.util.Map$a1"));
		assertFalse(ClassName.isLocal("java.util.Map$a1$aaa$bbb"));
		assertFalse(ClassName.isLocal("java.util.Map$11$aaa"));
		assertTrue(ClassName.isLocal("java.util.Map$1a"));
		assertTrue(ClassName.isLocal("java.util.Map$2abc"));
		assertTrue(ClassName.isLocal("java.util.Map$2abc1"));
		assertFalse(ClassName.isLocal("java.util.Map$a12345$b12345"));
		assertTrue(ClassName.isLocal("java.util.Map$12345$1234a"));
		assertFalse(ClassName.isLocal("java.util.Map$333"));
		assertFalse(ClassName.isLocal("java.util.Map3$333"));
	}

	public void testIsAnonymous() throws Exception {
		assertFalse(ClassName.isAnonymous(java.util.Map.class.getName())); // top-level
		assertFalse(ClassName.isAnonymous(java.util.Map.Entry.class.getName())); // member
		assertFalse(ClassName.isAnonymous(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertTrue(ClassName.isAnonymous(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassName.isAnonymous(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassName.isAnonymous(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassName.isAnonymous(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassName.isAnonymous(array.getClass().getName()));

		// test a few edge cases
		assertFalse(ClassName.isAnonymous("java.util.Map$a1"));
		assertFalse(ClassName.isAnonymous("java.util.Map$a1$aaa$bbb"));
		assertFalse(ClassName.isAnonymous("java.util.Map$1a1$aaa"));
		assertFalse(ClassName.isAnonymous("java.util.Map$1$a"));
		assertFalse(ClassName.isAnonymous("java.util.Map$1a"));
		assertFalse(ClassName.isAnonymous("java.util.Map$a12345$b12345"));
		assertFalse(ClassName.isAnonymous("java.util.Map$12345$a1234"));
		assertTrue(ClassName.isAnonymous("java.util.Map$333"));
		assertTrue(ClassName.isAnonymous("java.util.Map3$333"));
	}

	public void testIsReference() throws Exception {
		assertFalse(ClassName.isReference(int.class.getName())); // top-level

		assertTrue(ClassName.isReference(java.util.Map.class.getName())); // top-level
		assertTrue(ClassName.isReference(java.util.Map.Entry.class.getName())); // member
		assertTrue(ClassName.isReference(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertTrue(ClassName.isReference(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertTrue(ClassName.isReference(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertTrue(ClassName.isReference(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertTrue(ClassName.isReference(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertTrue(ClassName.isReference(array.getClass().getName()));
	}

	public void testIsPrimitive() {
		assertTrue(void.class.isPrimitive());

		assertTrue(ClassName.isPrimitive(void.class.getName()));
		assertTrue(ClassName.isPrimitive(int.class.getName()));
		assertTrue(ClassName.isPrimitive(float.class.getName()));
		assertTrue(ClassName.isPrimitive(boolean.class.getName()));

		assertFalse(ClassName.isPrimitive(java.lang.Number.class.getName()));
		assertFalse(ClassName.isPrimitive(java.lang.String.class.getName()));
		assertFalse(ClassName.isPrimitive(java.lang.Boolean.class.getName()));
		assertFalse(ClassName.isPrimitive(java.lang.Integer.class.getName()));
	}

	public void testIsPrimitiveWrapper() {
		assertFalse(ClassName.isPrimitiveWrapper(void.class.getName()));
		assertFalse(ClassName.isPrimitiveWrapper(int.class.getName()));
		assertFalse(ClassName.isPrimitiveWrapper(float.class.getName()));
		assertFalse(ClassName.isPrimitiveWrapper(boolean.class.getName()));

		assertFalse(ClassName.isPrimitiveWrapper(java.lang.reflect.Field.class.getName()));
		assertFalse(ClassName.isPrimitiveWrapper(java.lang.String.class.getName()));
		assertTrue(ClassName.isPrimitiveWrapper(java.lang.Boolean.class.getName()));
		assertTrue(ClassName.isPrimitiveWrapper(java.lang.Integer.class.getName()));
	}

	public void testIsVariablePrimitive() {
		assertFalse(ClassName.isVariablePrimitive(void.class.getName()));

		assertTrue(ClassName.isVariablePrimitive(int.class.getName()));
		assertTrue(ClassName.isVariablePrimitive(float.class.getName()));
		assertTrue(ClassName.isVariablePrimitive(boolean.class.getName()));

		assertFalse(ClassName.isVariablePrimitive(java.lang.Number.class.getName()));
		assertFalse(ClassName.isVariablePrimitive(java.lang.String.class.getName()));
		assertFalse(ClassName.isVariablePrimitive(java.lang.Boolean.class.getName()));
	}

	public void testIsVariablePrimitiveWrapper() {
		assertFalse(ClassName.isVariablePrimitiveWrapper(java.lang.Void.class.getName()));

		assertTrue(ClassName.isVariablePrimitiveWrapper(java.lang.Integer.class.getName()));
		assertTrue(ClassName.isVariablePrimitiveWrapper(java.lang.Float.class.getName()));
		assertTrue(ClassName.isVariablePrimitiveWrapper(java.lang.Boolean.class.getName()));

		assertFalse(ClassName.isVariablePrimitiveWrapper(java.lang.Number.class.getName()));
		assertFalse(ClassName.isVariablePrimitiveWrapper(java.lang.String.class.getName()));
		assertFalse(ClassName.isVariablePrimitiveWrapper(java.lang.Object.class.getName()));
	}

	public void testGetWrapperClassName() {
		assertEquals(java.lang.Void.class.getName(), ClassName.getWrapperClassName(void.class.getName()));
		assertEquals(java.lang.Integer.class.getName(), ClassName.getWrapperClassName(int.class.getName()));
		assertEquals(java.lang.Float.class.getName(), ClassName.getWrapperClassName(float.class.getName()));
		assertEquals(java.lang.Boolean.class.getName(), ClassName.getWrapperClassName(boolean.class.getName()));

		assertNull(ClassName.getWrapperClassName(java.lang.String.class.getName()));
	}

	public void testGetPrimitiveClassName() {
		assertEquals(void.class.getName(), ClassName.getPrimitiveClassName(java.lang.Void.class.getName()));
		assertEquals(int.class.getName(), ClassName.getPrimitiveClassName(java.lang.Integer.class.getName()));
		assertEquals(float.class.getName(), ClassName.getPrimitiveClassName(java.lang.Float.class.getName()));
		assertEquals(boolean.class.getName(), ClassName.getPrimitiveClassName(java.lang.Boolean.class.getName()));

		assertNull(ClassName.getPrimitiveClassName(java.lang.String.class.getName()));
	}

	public void testForCode() {
		assertEquals("byte", ClassName.forCode('B'));
		assertEquals("char", ClassName.forCode('C'));
		assertEquals("double", ClassName.forCode('D'));
		assertEquals("float", ClassName.forCode('F'));
		assertEquals("int", ClassName.forCode('I'));
		assertEquals("long", ClassName.forCode('J'));
		assertEquals("short", ClassName.forCode('S'));
		assertEquals("boolean", ClassName.forCode('Z'));
		assertEquals("void", ClassName.forCode('V'));

		assertNull(ClassName.forCode('X'));

		assertEquals("byte", ClassName.forCode((int) 'B'));
		assertEquals("char", ClassName.forCode((int) 'C'));
		assertEquals("double", ClassName.forCode((int) 'D'));
		assertEquals("float", ClassName.forCode((int) 'F'));
		assertEquals("int", ClassName.forCode((int) 'I'));
		assertEquals("long", ClassName.forCode((int) 'J'));
		assertEquals("short", ClassName.forCode((int) 'S'));
		assertEquals("boolean", ClassName.forCode((int) 'Z'));
		assertEquals("void", ClassName.forCode((int) 'V'));

		assertNull(ClassName.forCode((int) 'X'));
	}

	public void testGetCodeForClassName() {
		assertEquals('I', ClassName.getCodeForClassName(int.class.getName()));
		assertEquals('I', ClassName.getCodeForClassName("int"));
		assertEquals('B', ClassName.getCodeForClassName(byte.class.getName()));
		assertEquals('B', ClassName.getCodeForClassName("byte"));

		assertEquals((char) 0, ClassName.getCodeForClassName(java.lang.Object.class.getName()));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ReflectionTools.newInstance(ClassName.class);
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

}
