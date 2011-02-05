/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
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

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

@SuppressWarnings("nls")
public class ReflectionToolsTests extends TestCase {

	private static String testStaticField;

	public ReflectionToolsTests(String name) {
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
		Iterable<Field> fields = ReflectionTools.getAllFields(java.util.Vector.class);
		assertEquals(fieldCount, CollectionTools.size(fields));
		assertTrue(CollectionTools.contains(this.fieldNames(fields), "modCount"));
		assertTrue(CollectionTools.contains(this.fieldNames(fields), "serialVersionUID"));
		assertTrue(CollectionTools.contains(this.fieldNames(fields), "capacityIncrement"));
		assertTrue(CollectionTools.contains(this.fieldNames(fields), "elementCount"));
		assertTrue(CollectionTools.contains(this.fieldNames(fields), "elementData"));
		assertTrue(fields.iterator().next().isAccessible());
	}

	public void testAllMethods() {
		int methodCount = 0;
		methodCount += java.util.Vector.class.getDeclaredMethods().length;
		methodCount += java.util.AbstractList.class.getDeclaredMethods().length;
		methodCount += java.util.AbstractCollection.class.getDeclaredMethods().length;
		methodCount += java.lang.Object.class.getDeclaredMethods().length;
		Iterable<Method> methods = ReflectionTools.getAllMethods(java.util.Vector.class);
		assertEquals(methodCount, CollectionTools.size(methods));
		assertTrue(CollectionTools.contains(this.methodNames(methods), "wait"));
		assertTrue(CollectionTools.contains(this.methodNames(methods), "addElement"));
		assertTrue(methods.iterator().next().isAccessible());
	}

	public void testNewInstanceClass() {
		Vector<?> v = ReflectionTools.newInstance(java.util.Vector.class);
		assertNotNull(v);
		assertEquals(0, v.size());
	}

	public void testNewInstanceClassClassObject() {
		int initialCapacity = 200;
		Vector<?> v = ReflectionTools.newInstance(java.util.Vector.class, int.class, new Integer(initialCapacity));
		assertNotNull(v);
		assertEquals(0, v.size());
		Object[] elementData = (Object[]) ReflectionTools.getFieldValue(v, "elementData");
		assertEquals(initialCapacity, elementData.length);
	}

	public void testNewInstanceClassClassArrayObjectArray() {
		int initialCapacity = 200;
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = int.class;
		Object[] parms = new Object[1];
		parms[0] = new Integer(initialCapacity);
		Vector<?> v = ReflectionTools.newInstance(java.util.Vector.class, parmTypes, parms);
		assertNotNull(v);
		assertEquals(0, v.size());
		Object[] elementData = (Object[]) ReflectionTools.getFieldValue(v, "elementData");
		assertEquals(initialCapacity, elementData.length);

		parms[0] = new Integer(-1);
		boolean exCaught = false;
		try {
			v = ReflectionTools.newInstance(java.util.Vector.class, parmTypes, parms);
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue("RuntimeException not thrown", exCaught);

		parmTypes[0] = java.lang.String.class;
		parms[0] = "foo";
		exCaught = false;
		try {
			v = ReflectionTools.newInstance(java.util.Vector.class, parmTypes, parms);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown", exCaught);
	}

	public void testFieldValue() {
		int initialCapacity = 200;
		Vector<?> v = new Vector<Object>(initialCapacity);
		Object[] elementData = (Object[]) ReflectionTools.getFieldValue(v, "elementData");
		assertEquals(initialCapacity, elementData.length);

		// test inherited field
		Integer modCountInteger = (Integer) ReflectionTools.getFieldValue(v, "modCount");
		int modCount = modCountInteger.intValue();
		assertEquals(0, modCount);

		boolean exCaught = false;
		Object bogusFieldValue = null;
		try {
			bogusFieldValue = ReflectionTools.getFieldValue(v, "bogusField");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchFieldException not thrown: " + bogusFieldValue, exCaught);
	}

	public void testExecuteMethodObjectString() {
		Vector<String> v = new Vector<String>();
		int size = ((Integer) ReflectionTools.executeMethod(v, "size")).intValue();
		assertEquals(0, size);

		v.addElement("foo");
		size = ((Integer) ReflectionTools.executeMethod(v, "size")).intValue();
		assertEquals(1, size);
	}

	public void testExecuteMethodObjectStringClassObject() {
		Vector<String> v = new Vector<String>();
		boolean booleanResult = ((Boolean) ReflectionTools.executeMethod(v, "add", Object.class, "foo")).booleanValue();
		assertTrue(booleanResult);
		assertTrue(v.contains("foo"));
		Object voidResult = ReflectionTools.executeMethod(v, "addElement", Object.class, "bar");
		assertNull(voidResult);
	}

	public void testExecuteMethodObjectStringClassArrayObjectArray() {
		Vector<String> v = new Vector<String>();
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = java.lang.Object.class;
		Object[] parms = new Object[1];
		parms[0] = "foo";
		boolean booleanResult = ((Boolean) ReflectionTools.executeMethod(v, "add", parmTypes, parms)).booleanValue();
		assertTrue(booleanResult);
		assertTrue(v.contains("foo"));

		boolean exCaught = false;
		Object bogusMethodReturnValue = null;
		try {
			bogusMethodReturnValue = ReflectionTools.executeMethod(v, "bogusMethod", parmTypes, parms);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown: " + bogusMethodReturnValue, exCaught);
	}

	public void testExecuteStaticMethodClassString() {
		Double randomObject = (Double) ReflectionTools.executeStaticMethod(java.lang.Math.class, "random");
		assertNotNull(randomObject);
		double random = randomObject.doubleValue();
		assertTrue(random >= 0);
		assertTrue(random < 1);
	}

	public void testExecuteStaticMethodClassStringClassObject() {
		String s = (String) ReflectionTools.executeStaticMethod(java.lang.String.class, "valueOf", boolean.class, Boolean.TRUE);
		assertNotNull(s);
		assertEquals("true", s);
	}

	public void testExecuteStaticMethodClassStringClassArrayObjectArray() {
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = boolean.class;
		Object[] parms = new Object[1];
		parms[0] = Boolean.TRUE;
		String s = (String) ReflectionTools.executeStaticMethod(java.lang.String.class, "valueOf", parmTypes, parms);
		assertNotNull(s);
		assertEquals("true", s);

		boolean exCaught = false;
		Object bogusStaticMethodReturnValue = null;
		try {
			bogusStaticMethodReturnValue = ReflectionTools.executeStaticMethod(java.lang.String.class, "bogusStaticMethod", parmTypes, parms);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown: " + bogusStaticMethodReturnValue, exCaught);

		// test non-static method
		exCaught = false;
		try {
			bogusStaticMethodReturnValue = ReflectionTools.executeStaticMethod(java.lang.String.class, "toString");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown: " + bogusStaticMethodReturnValue, exCaught);
	}

	public void testSetFieldValue() {
		Vector<String> v = new Vector<String>();
		Object[] newElementData = new Object[5];
		newElementData[0] = "foo";
		ReflectionTools.setFieldValue(v, "elementData", newElementData);
		ReflectionTools.setFieldValue(v, "elementCount", new Integer(1));
		// test inherited field
		ReflectionTools.setFieldValue(v, "modCount", new Integer(1));
		assertTrue(v.contains("foo"));

		boolean exCaught = false;
		try {
			ReflectionTools.setFieldValue(v, "bogusField", "foo");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchFieldException not thrown", exCaught);
	}

	public void testSetStaticFieldValue() {
		ReflectionTools.setStaticFieldValue(this.getClass(), "testStaticField", "new value");
		assertEquals(testStaticField, "new value");

		boolean exCaught = false;
		try {
			ReflectionTools.setStaticFieldValue(this.getClass(), "bogusStaticField", "new value");
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

	public void testArrayDepthFor() {
		assertEquals(0, ReflectionTools.getArrayDepth(java.util.Vector.class));
		assertEquals(0, ReflectionTools.getArrayDepth(int.class));
		assertEquals(0, ReflectionTools.getArrayDepth(void.class));
		assertEquals(1, ReflectionTools.getArrayDepth(java.util.Vector[].class));
		assertEquals(1, ReflectionTools.getArrayDepth(int[].class));
		assertEquals(3, ReflectionTools.getArrayDepth(java.util.Vector[][][].class));
		assertEquals(3, ReflectionTools.getArrayDepth(int[][][].class));
	}

	public void testElementTypeFor() {
		assertEquals(java.util.Vector.class, ReflectionTools.getElementType(java.util.Vector.class));
		assertEquals(int.class, ReflectionTools.getElementType(int.class));
		assertEquals(void.class, ReflectionTools.getElementType(void.class));
		assertEquals(java.util.Vector.class, ReflectionTools.getElementType(java.util.Vector[].class));
		assertEquals(int.class, ReflectionTools.getElementType(int[].class));
		assertEquals(java.util.Vector.class, ReflectionTools.getElementType(java.util.Vector[][][].class));
		assertEquals(int.class, ReflectionTools.getElementType(int[][][].class));
	}

	public void testClassIsPrimitiveWrapperClass() {
		assertTrue(ReflectionTools.classIsPrimitiveWrapper(java.lang.Void.class));
		assertTrue(ReflectionTools.classIsPrimitiveWrapper(java.lang.Boolean.class));
		assertTrue(ReflectionTools.classIsPrimitiveWrapper(java.lang.Integer.class));
		assertTrue(ReflectionTools.classIsPrimitiveWrapper(java.lang.Float.class));

		assertFalse(ReflectionTools.classIsPrimitiveWrapper(java.lang.String.class));
		assertFalse(ReflectionTools.classIsPrimitiveWrapper(void.class));
		assertFalse(ReflectionTools.classIsPrimitiveWrapper(int.class));
	}

	public void testClassIsVariablePrimitiveWrapperClass() {
		assertFalse(ReflectionTools.classIsVariablePrimitiveWrapper(java.lang.Void.class));

		assertTrue(ReflectionTools.classIsVariablePrimitiveWrapper(java.lang.Boolean.class));
		assertTrue(ReflectionTools.classIsVariablePrimitiveWrapper(java.lang.Integer.class));
		assertTrue(ReflectionTools.classIsVariablePrimitiveWrapper(java.lang.Float.class));

		assertFalse(ReflectionTools.classIsVariablePrimitiveWrapper(java.lang.String.class));
		assertFalse(ReflectionTools.classIsVariablePrimitiveWrapper(void.class));
		assertFalse(ReflectionTools.classIsVariablePrimitiveWrapper(int.class));
	}

	public void testWrapperClass() {
		assertEquals(java.lang.Void.class, ReflectionTools.getWrapperClass(void.class));
		assertEquals(java.lang.Integer.class, ReflectionTools.getWrapperClass(int.class));
		assertEquals(java.lang.Float.class, ReflectionTools.getWrapperClass(float.class));
		assertEquals(java.lang.Boolean.class, ReflectionTools.getWrapperClass(boolean.class));

		assertNull(ReflectionTools.getWrapperClass(java.lang.String.class));
	}

	public void testClassForTypeDeclarationStringInt() throws Exception {
		assertEquals(int.class, ReflectionTools.getClassForTypeDeclaration("int", 0));
		assertEquals(int[].class, ReflectionTools.getClassForTypeDeclaration("int", 1));
		assertEquals(int[][][].class, ReflectionTools.getClassForTypeDeclaration("int", 3));

		assertEquals(Object.class, ReflectionTools.getClassForTypeDeclaration("java.lang.Object", 0));
		assertEquals(Object[][][].class, ReflectionTools.getClassForTypeDeclaration("java.lang.Object", 3));

		assertEquals(void.class, ReflectionTools.getClassForTypeDeclaration("void", 0));
		try {
			ReflectionTools.getClassForTypeDeclaration(void.class.getName(), 1);
			fail("should not get here...");
		} catch (RuntimeException ex) {
			// expected
		}
	}

	public void testCodeForClass() {
		assertEquals('I', ReflectionTools.getCodeForClass(int.class));
		assertEquals('B', ReflectionTools.getCodeForClass(byte.class));
	}

	public void testClassNameForTypeDeclarationString() throws Exception {
		assertEquals("int", ReflectionTools.getClassNameForTypeDeclaration("int"));
		assertEquals("[I", ReflectionTools.getClassNameForTypeDeclaration("int[]"));
		assertEquals("[[I", ReflectionTools.getClassNameForTypeDeclaration("int [ ] [ ]"));

		assertEquals("java.lang.Object", ReflectionTools.getClassNameForTypeDeclaration("java.lang.Object"));
		assertEquals("[Ljava.lang.Object;", ReflectionTools.getClassNameForTypeDeclaration("java.lang.Object\t[]"));
		assertEquals("[[Ljava.lang.Object;", ReflectionTools.getClassNameForTypeDeclaration("java.lang.Object\t[]\t[]"));
	}

	public void testArrayDepthForTypeDeclarationString() throws Exception {
		assertEquals(0, ReflectionTools.getArrayDepthForTypeDeclaration("java.lang.Object"));
		assertEquals(1, ReflectionTools.getArrayDepthForTypeDeclaration("java.lang.Object[]"));
		assertEquals(3, ReflectionTools.getArrayDepthForTypeDeclaration("java.lang.Object[][][]"));

		assertEquals(0, ReflectionTools.getArrayDepthForTypeDeclaration("int"));
		assertEquals(1, ReflectionTools.getArrayDepthForTypeDeclaration("int[]"));
		assertEquals(3, ReflectionTools.getArrayDepthForTypeDeclaration("int[][][]"));

		assertEquals(0, ReflectionTools.getArrayDepthForTypeDeclaration("float"));
		assertEquals(1, ReflectionTools.getArrayDepthForTypeDeclaration("float [ ]"));
		assertEquals(3, ReflectionTools.getArrayDepthForTypeDeclaration("float[] [] []"));
	}

	public void testElementTypeNameForTypeDeclarationString() throws Exception {
		assertEquals("java.lang.Object", ReflectionTools.getElementTypeNameForTypeDeclaration("java.lang.Object"));
		assertEquals("java.lang.Object", ReflectionTools.getElementTypeNameForTypeDeclaration("java.lang.Object[]"));
		assertEquals("java.lang.Object", ReflectionTools.getElementTypeNameForTypeDeclaration("java.lang.Object[][][]"));

		assertEquals("int", ReflectionTools.getElementTypeNameForTypeDeclaration("int"));
		assertEquals("int", ReflectionTools.getElementTypeNameForTypeDeclaration("int[]"));
		assertEquals("int", ReflectionTools.getElementTypeNameForTypeDeclaration("int[][][]"));

		assertEquals("float", ReflectionTools.getElementTypeNameForTypeDeclaration("float"));
		assertEquals("float", ReflectionTools.getElementTypeNameForTypeDeclaration("float [ ]"));
		assertEquals("float", ReflectionTools.getElementTypeNameForTypeDeclaration("float[] [] []"));
	}

	public void testClassNameForTypeDeclarationStringInt() throws Exception {
		assertEquals(int.class.getName(), ReflectionTools.getClassNameForTypeDeclaration("int", 0));
		assertEquals(int[].class.getName(), ReflectionTools.getClassNameForTypeDeclaration("int", 1));
		assertEquals(int[][][].class.getName(), ReflectionTools.getClassNameForTypeDeclaration("int", 3));

		assertEquals(Object.class.getName(), ReflectionTools.getClassNameForTypeDeclaration("java.lang.Object", 0));
		assertEquals(Object[][][].class.getName(), ReflectionTools.getClassNameForTypeDeclaration("java.lang.Object", 3));

		assertEquals(void.class.getName(), ReflectionTools.getClassNameForTypeDeclaration("void", 0));
		try {
			ReflectionTools.getClassNameForTypeDeclaration(void.class.getName(), 1);
			fail("should not get here...");
		} catch (IllegalArgumentException ex) {
			// expected
		}
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
