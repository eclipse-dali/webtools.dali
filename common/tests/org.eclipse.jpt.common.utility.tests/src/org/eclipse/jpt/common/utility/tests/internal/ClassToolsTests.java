/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;

@SuppressWarnings("nls")
public class ClassToolsTests
	extends TestCase
{
	private static String testStaticField;

	public ClassToolsTests(String name) {
		super(name);
	}

// this is no longer true - it appears the JLS now defines the generated names...
//	/**
//	 * Return the compiler-generated class name. The Eclipse compiler generates
//	 * "local" classes with names in the form "com.foo.Outer$1$Local"; while the
//	 * JDK compiler generates "com.foo.Outer$1Local". There might be other
//	 * differences.... ~bjv
//	 */
//	public static String compilerDependentClassNameFor(String className) {
//		int index = className.indexOf("$1$");
//		if (index == -1) {
//			return className;
//		}
//		try {
//			Class.forName(className);
//		} catch (ClassNotFoundException ex) {
//			return className.substring(0, index + 2) + className.substring(index + 3);
//		}
//		return className;
//	}
//
//	private static String munge(String className) {
//		return compilerDependentClassNameFor(className);
//	}

	public void testAllFields() {
		int fieldCount = 0;
		fieldCount += java.util.Vector.class.getDeclaredFields().length;
		fieldCount += java.util.AbstractList.class.getDeclaredFields().length;
		fieldCount += java.util.AbstractCollection.class.getDeclaredFields().length;
		fieldCount += java.lang.Object.class.getDeclaredFields().length;
		Iterable<Field> fields = ClassTools.allFields(java.util.Vector.class);
		assertEquals(fieldCount, IterableTools.size(fields));
		assertTrue(IterableTools.contains(this.fieldNames(fields), "modCount"));
		assertTrue(IterableTools.contains(this.fieldNames(fields), "serialVersionUID"));
		assertTrue(IterableTools.contains(this.fieldNames(fields), "capacityIncrement"));
		assertTrue(IterableTools.contains(this.fieldNames(fields), "elementCount"));
		assertTrue(IterableTools.contains(this.fieldNames(fields), "elementData"));
		assertTrue(fields.iterator().next().isAccessible());
	}

	public void testAllMethods() {
		int methodCount = 0;
		methodCount += java.util.Vector.class.getDeclaredMethods().length;
		methodCount += java.util.AbstractList.class.getDeclaredMethods().length;
		methodCount += java.util.AbstractCollection.class.getDeclaredMethods().length;
		methodCount += java.lang.Object.class.getDeclaredMethods().length;
		Iterable<Method> methods = ClassTools.allMethods(java.util.Vector.class);
		assertEquals(methodCount, IterableTools.size(methods));
		assertTrue(IterableTools.contains(this.methodNames(methods), "wait"));
		assertTrue(IterableTools.contains(this.methodNames(methods), "addElement"));
		assertTrue(methods.iterator().next().isAccessible());
	}

	public void testNewInstanceClass() {
		Vector<?> v = ClassTools.newInstance(java.util.Vector.class);
		assertNotNull(v);
		assertEquals(0, v.size());
	}

	public void testNewInstanceClassClassObject() {
		int initialCapacity = 200;
		Vector<?> v = ClassTools.newInstance(java.util.Vector.class, int.class, new Integer(initialCapacity));
		assertNotNull(v);
		assertEquals(0, v.size());
		Object[] elementData = (Object[]) ObjectTools.get(v, "elementData");
		assertEquals(initialCapacity, elementData.length);
	}

	public void testNewInstanceClassClassArrayObjectArray() {
		int initialCapacity = 200;
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = int.class;
		Object[] parms = new Object[1];
		parms[0] = new Integer(initialCapacity);
		Vector<?> v = ClassTools.newInstance(java.util.Vector.class, parmTypes, parms);
		assertNotNull(v);
		assertEquals(0, v.size());
		Object[] elementData = (Object[]) ObjectTools.get(v, "elementData");
		assertEquals(initialCapacity, elementData.length);

		parms[0] = new Integer(-1);
		boolean exCaught = false;
		try {
			v = ClassTools.newInstance(java.util.Vector.class, parmTypes, parms);
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue("RuntimeException not thrown", exCaught);

		parmTypes[0] = java.lang.String.class;
		parms[0] = "foo";
		exCaught = false;
		try {
			v = ClassTools.newInstance(java.util.Vector.class, parmTypes, parms);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown", exCaught);
	}

	public void testExecuteClassString() {
		Double randomObject = (Double) ClassTools.execute(java.lang.Math.class, "random");
		assertNotNull(randomObject);
		double random = randomObject.doubleValue();
		assertTrue(random >= 0);
		assertTrue(random < 1);
	}

	public void testExecuteClassStringClassObject() {
		String s = (String) ClassTools.execute(java.lang.String.class, "valueOf", boolean.class, Boolean.TRUE);
		assertNotNull(s);
		assertEquals("true", s);
	}

	public void testExecuteClassStringClassArrayObjectArray() {
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = boolean.class;
		Object[] parms = new Object[1];
		parms[0] = Boolean.TRUE;
		String s = (String) ClassTools.execute(java.lang.String.class, "valueOf", parmTypes, parms);
		assertNotNull(s);
		assertEquals("true", s);

		boolean exCaught = false;
		Object bogusStaticMethodReturnValue = null;
		try {
			bogusStaticMethodReturnValue = ClassTools.execute(java.lang.String.class, "bogusStaticMethod", parmTypes, parms);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown: " + bogusStaticMethodReturnValue, exCaught);

		// test non-static method
		exCaught = false;
		try {
			bogusStaticMethodReturnValue = ClassTools.execute(java.lang.String.class, "toString");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown: " + bogusStaticMethodReturnValue, exCaught);
	}

	public void testSet() {
		ClassTools.set(this.getClass(), "testStaticField", "new value");
		assertEquals(testStaticField, "new value");

		boolean exCaught = false;
		try {
			ClassTools.set(this.getClass(), "bogusStaticField", "new value");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchFieldException not thrown", exCaught);
	}

	public void testSimpleName() {
		assertEquals("Vector", java.util.Vector.class.getSimpleName());
		assertEquals("Entry", java.util.Map.Entry.class.getSimpleName());
		assertEquals("int", int.class.getSimpleName());
		assertEquals("int[]", int[].class.getSimpleName());
		assertEquals("int[][]", int[][].class.getSimpleName());
		assertEquals("void", void.class.getSimpleName());
	}

	public void testPackageName() {
		assertEquals("java.util", java.util.Vector.class.getPackage().getName());
		assertEquals("java.util", java.util.Map.Entry.class.getPackage().getName());
	}

	public void testArrayDepth() {
		assertEquals(0, ClassTools.arrayDepth(java.util.Vector.class));
		assertEquals(0, ClassTools.arrayDepth(int.class));
		assertEquals(0, ClassTools.arrayDepth(void.class));
		assertEquals(1, ClassTools.arrayDepth(java.util.Vector[].class));
		assertEquals(1, ClassTools.arrayDepth(int[].class));
		assertEquals(3, ClassTools.arrayDepth(java.util.Vector[][][].class));
		assertEquals(3, ClassTools.arrayDepth(int[][][].class));
	}

	public void testElementType() {
		assertEquals(java.util.Vector.class, ClassTools.elementType(java.util.Vector.class));
		assertEquals(int.class, ClassTools.elementType(int.class));
		assertEquals(void.class, ClassTools.elementType(void.class));
		assertEquals(java.util.Vector.class, ClassTools.elementType(java.util.Vector[].class));
		assertEquals(int.class, ClassTools.elementType(int[].class));
		assertEquals(java.util.Vector.class, ClassTools.elementType(java.util.Vector[][][].class));
		assertEquals(int.class, ClassTools.elementType(int[][][].class));
	}

	public void testIsPrimitiveWrapper() {
		assertTrue(ClassTools.isPrimitiveWrapper(java.lang.Void.class));
		assertTrue(ClassTools.isPrimitiveWrapper(java.lang.Boolean.class));
		assertTrue(ClassTools.isPrimitiveWrapper(java.lang.Integer.class));
		assertTrue(ClassTools.isPrimitiveWrapper(java.lang.Float.class));

		assertFalse(ClassTools.isPrimitiveWrapper(java.lang.String.class));
		assertFalse(ClassTools.isPrimitiveWrapper(void.class));
		assertFalse(ClassTools.isPrimitiveWrapper(int.class));
	}

	public void testIsVariablePrimitiveWrapper() {
		assertFalse(ClassTools.isVariablePrimitiveWrapper(java.lang.Void.class));

		assertTrue(ClassTools.isVariablePrimitiveWrapper(java.lang.Boolean.class));
		assertTrue(ClassTools.isVariablePrimitiveWrapper(java.lang.Integer.class));
		assertTrue(ClassTools.isVariablePrimitiveWrapper(java.lang.Float.class));

		assertFalse(ClassTools.isVariablePrimitiveWrapper(java.lang.String.class));
		assertFalse(ClassTools.isVariablePrimitiveWrapper(void.class));
		assertFalse(ClassTools.isVariablePrimitiveWrapper(int.class));
	}

	public void testPrimitiveWrapper() {
		assertEquals(java.lang.Void.class, ClassTools.primitiveWrapper(void.class));
		assertEquals(java.lang.Integer.class, ClassTools.primitiveWrapper(int.class));
		assertEquals(java.lang.Float.class, ClassTools.primitiveWrapper(float.class));
		assertEquals(java.lang.Boolean.class, ClassTools.primitiveWrapper(boolean.class));

		assertNull(ClassTools.primitiveWrapper(java.lang.String.class));
	}

	public void testForTypeDeclarationStringInt() throws Exception {
		assertEquals(int.class, ClassTools.forTypeDeclaration("int", 0));
		assertEquals(int[].class, ClassTools.forTypeDeclaration("int", 1));
		assertEquals(int[][][].class, ClassTools.forTypeDeclaration("int", 3));

		assertEquals(Object.class, ClassTools.forTypeDeclaration("java.lang.Object", 0));
		assertEquals(Object[][][].class, ClassTools.forTypeDeclaration("java.lang.Object", 3));

		assertEquals(void.class, ClassTools.forTypeDeclaration("void", 0));
		try {
			ClassTools.forTypeDeclaration(void.class.getName(), 1);
			fail("should not get here...");
		} catch (RuntimeException ex) {
			// expected
		}
	}

	public void testPrimitiveCode() {
		assertEquals('I', ClassTools.primitiveCode(int.class));
		assertEquals('B', ClassTools.primitiveCode(byte.class));
	}

	private Iterable<String> fieldNames(Iterable<Field> fields) {
		return new TransformationIterable<Field, String>(fields) {
			@Override
			protected String transform(Field field) {
				return field.getName();
			}
		};
	}

	private Iterable<String> methodNames(Iterable<Method> methods) {
		return new TransformationIterable<Method, String>(methods) {
			@Override
			protected String transform(Method method) {
				return method.getName();
			}
		};
	}
}
