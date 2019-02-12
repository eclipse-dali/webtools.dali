/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Vector;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.command.CommandAdapter;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ClassToolsTests
	extends TestCase
{
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

	public void testForNameString() throws Exception {
		String className = "java.lang.String";
		Class<?> clazz = ClassTools.forName(className);
		assertNotNull(clazz);
		assertEquals(className, clazz.getName());
		String s = (String) clazz.newInstance();
		assertNotNull(s);
	}

	public void testForNameString_exception() throws Exception {
		String className = "java.lang.XXXX";
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forName(className);
			fail("bogus: " + clazz);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForNameCharArray() throws Exception {
		String className = "java.lang.String";
		Class<?> clazz = ClassTools.forName(className.toCharArray());
		assertNotNull(clazz);
		assertEquals(className, clazz.getName());
		String s = (String) clazz.newInstance();
		assertNotNull(s);
	}

	public void testForNameCharArray_exception() throws Exception {
		String className = "java.lang.XXXX";
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forName(className.toCharArray());
			fail("bogus: " + clazz);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForName_CharArray() throws Exception {
		String className = "java.lang.String";
		Class<?> clazz = ClassTools.forName_(className.toCharArray());
		assertNotNull(clazz);
		assertEquals(className, clazz.getName());
		String s = (String) clazz.newInstance();
		assertNotNull(s);
	}

	public void testForName_CharArray_exception() throws Exception {
		String className = "java.lang.XXXX";
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forName_(className.toCharArray());
			fail("bogus: " + clazz);
		} catch (ClassNotFoundException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testForNameStringBooleanClassLoader() throws Exception {
		String className = "java.lang.String";
		Class<?> clazz = ClassTools.forName(className, true, this.getClass().getClassLoader());
		assertNotNull(clazz);
		assertEquals(className, clazz.getName());
		String s = (String) clazz.newInstance();
		assertNotNull(s);
	}

	public void testForNameStringBooleanClassLoader_exception() throws Exception {
		String className = "java.lang.XXXX";
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forName(className, true, this.getClass().getClassLoader());
			fail("bogus: " + clazz);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForNameCharArrayBooleanClassLoader() throws Exception {
		String className = "java.lang.String";
		Class<?> clazz = ClassTools.forName(className.toCharArray(), true, this.getClass().getClassLoader());
		assertNotNull(clazz);
		assertEquals(className, clazz.getName());
		String s = (String) clazz.newInstance();
		assertNotNull(s);
	}

	public void testForNameCharArrayBooleanClassLoader_exception() throws Exception {
		String className = "java.lang.XXXX";
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forName(className.toCharArray(), true, this.getClass().getClassLoader());
			fail("bogus: " + clazz);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForName_CharArrayBooleanClassLoader() throws Exception {
		String className = "java.lang.String";
		Class<?> clazz = ClassTools.forName_(className.toCharArray(), true, this.getClass().getClassLoader());
		assertNotNull(clazz);
		assertEquals(className, clazz.getName());
		String s = (String) clazz.newInstance();
		assertNotNull(s);
	}

	public void testForName_CharArrayBooleanClassLoader_exception() throws Exception {
		String className = "java.lang.XXXX";
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forName_(className.toCharArray(), true, this.getClass().getClassLoader());
			fail("bogus: " + clazz);
		} catch (ClassNotFoundException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testToStringName() {
		assertEquals(this.getClass().getSimpleName(), ClassTools.toStringName(this.getClass()));
	}

	public void testToStringName_anonymous() {
		Command command = new CommandAdapter() {
				@Override
				public void execute() {
					// NOP
				}
			};
		assertEquals("CommandAdapter", ClassTools.toStringName(command.getClass()));
	}

	public void testToStringName_member() {
		Command command = new LocalCommand();
		assertEquals(this.getClass().getSimpleName() + ".LocalCommand", ClassTools.toStringName(command.getClass()));
	}

	public static class LocalCommand
		extends CommandAdapter
	{
		@Override
		public void execute() {
			// NOP
		}
	}

	public void testAllSuperclasses() {
		Iterator<Class<?>> superclasses = ClassTools.allSuperclasses(java.util.Vector.class).iterator();
		assertEquals(superclasses.next(), java.util.AbstractList.class);
		assertEquals(superclasses.next(), java.util.AbstractCollection.class);
		assertEquals(superclasses.next(), java.lang.Object.class);
	}

	public void testAllSuperclasses_Object() {
		Iterator<Class<?>> superclasses = ClassTools.allSuperclasses(java.lang.Object.class).iterator();
		assertFalse(superclasses.hasNext());
	}

	public void testAllInterfaces() {
		int count = 0;
		count += java.util.Vector.class.getInterfaces().length;
		count += java.util.AbstractList.class.getInterfaces().length;
		count += java.util.AbstractCollection.class.getInterfaces().length;
		count += java.lang.Object.class.getInterfaces().length;
		Iterable<Class<?>> interfaces = ClassTools.allInterfaces(java.util.Vector.class);
		assertEquals(count, IterableTools.size(interfaces));
		assertTrue(IterableTools.contains(interfaces, java.util.List.class));
		assertTrue(IterableTools.contains(interfaces, java.util.RandomAccess.class));
		assertTrue(IterableTools.contains(interfaces, java.util.Collection.class));
		assertTrue(IterableTools.contains(interfaces, java.lang.Cloneable.class));
		assertTrue(IterableTools.contains(interfaces, java.io.Serializable.class));
	}

	public void testAllInterfaces_Object() {
		Iterator<Class<?>> interfaces = ClassTools.allInterfaces(java.lang.Object.class).iterator();
		assertFalse(interfaces.hasNext());
	}

	public void testGet() {
		assertEquals(TEST_STATIC_FINAL_FIELD, ClassTools.get(this.getClass(), "TEST_STATIC_FINAL_FIELD"));
	}
	public static final String TEST_STATIC_FINAL_FIELD = "XXXX value";

	public void testGet_exception() {
		boolean exCaught = false;
		try {
			Object value = ClassTools.get(this.getClass(), "BOGUS");
			fail("bogus: " + value);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testSet() {
		ClassTools.set(this.getClass(), "testStaticField", "new value");
		assertEquals(testStaticField, "new value");
	}
	private static String testStaticField;

	public void testSet_exception() {
		boolean exCaught = false;
		try {
			ClassTools.set(this.getClass(), "bogusStaticField", "new value");
			fail();
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testField() {
		assertNotNull(ClassTools.field(this.getClass(), "testStaticField"));
	}

	public void testField_superclass() {
		assertNotNull(ClassTools.field(this.getClass(), "fName"));
	}

	public void testField_exception() {
		boolean exCaught = false;
		try {
			Field field = ClassTools.field(this.getClass(), "BOGUS");
			fail("bogus: " + field);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

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

	public void testExecute() {
		assertEquals(UNINITIALIZED, executeStaticMethod_field);
		assertNull(ClassTools.execute(this.getClass(), "executeStaticMethod"));
		assertEquals(TOUCHED, executeStaticMethod_field);
	}

	public static void executeStaticMethod() {
		executeStaticMethod_field = TOUCHED;
	}
	private static final String UNINITIALIZED = "uninitialized";
	private static final String TOUCHED = "touched";
	private static String executeStaticMethod_field = UNINITIALIZED;

	public void testExecute_() throws Exception {
		assertEquals(UNINITIALIZED_, executeStaticMethod_field_);
		assertNull(ClassTools.execute_(this.getClass(), "executeStaticMethod_"));
		assertEquals(TOUCHED_, executeStaticMethod_field_);
	}
	public static void executeStaticMethod_() {
		executeStaticMethod_field_ = TOUCHED_;
	}
	private static final String UNINITIALIZED_ = "uninitialized_";
	private static final String TOUCHED_ = "touched_";
	private static String executeStaticMethod_field_ = UNINITIALIZED_;

	public void testExecute_returnValue() {
		assertEquals(STATIC_RETURN_VALUE, ClassTools.execute(this.getClass(), "executeStaticMethod_returnValue"));
	}

	public static String executeStaticMethod_returnValue() {
		return STATIC_RETURN_VALUE;
	}
	private static final String STATIC_RETURN_VALUE = "foo";

	public void testExecute_noSuchMethod() {
		boolean exCaught = false;
		try {
			Object value = ClassTools.execute(this.getClass(), "executeStaticMethod_BOGUS");
			fail("bogus: " + value);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testExecute_exception() {
		boolean exCaught = false;
		try {
			Object value = ClassTools.execute(this.getClass(), "executeStaticMethod_exception");
			fail("bogus: " + value);
		} catch (RuntimeException ex1) {
			Throwable cause1 = ex1.getCause();
			if (cause1 instanceof InvocationTargetException) {
				Throwable cause2 = ((InvocationTargetException) cause1).getCause();
				if (cause2 instanceof NullPointerException) {
					if (cause2.getMessage().equals("class tools test")) {
						exCaught = true;
					}
				}
			}
		}
		assertTrue(exCaught);
	}

	public static String executeStaticMethod_exception() {
		throw new NullPointerException("class tools test");
	}

	public void testExecuteWithParm() {
		assertEquals(UNINITIALIZED, executeStaticMethodWithParm_field);
		ClassTools.execute(this.getClass(), "executeStaticMethodWithParm", String.class, TOUCHED);
		assertEquals(TOUCHED, executeStaticMethodWithParm_field);
	}

	public static void executeStaticMethodWithParm(String value) {
		executeStaticMethodWithParm_field = value;
	}
	private static String executeStaticMethodWithParm_field = UNINITIALIZED;

	public void testExecuteWithParm_() throws Exception {
		assertEquals(UNINITIALIZED_, executeStaticMethodWithParm_field_);
		ClassTools.execute_(this.getClass(), "executeStaticMethodWithParm_", String.class, TOUCHED_);
		assertEquals(TOUCHED_, executeStaticMethodWithParm_field_);
	}

	public static void executeStaticMethodWithParm_(String value) {
		executeStaticMethodWithParm_field_ = value;
	}
	private static String executeStaticMethodWithParm_field_ = UNINITIALIZED_;

	public void testMethod() {
		assertNotNull(ClassTools.method(this.getClass(), "testMethod"));
	}

	public void testMethod_superclass() {
		assertNotNull(ClassTools.method(this.getClass(), "run"));
	}

	public void testMethod_() throws Exception {
		assertNotNull(ClassTools.method_(this.getClass(), "testMethod_"));
	}

	public void testMethodWithParm() {
		assertNotNull(ClassTools.method(this.getClass(), "methodWithParm", String.class));
	}

	public void testMethodWithParm_() throws Exception {
		assertNotNull(ClassTools.method_(this.getClass(), "methodWithParm", String.class));
	}

	public void methodWithParm(String parm) {
		assertNotNull(parm);
	}

	public void testMethod_noSuchMethod() {
		boolean exCaught = false;
		try {
			Method method = ClassTools.method(this.getClass(), "executeStaticMethod_BOGUS");
			fail("bogus: " + method);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testStaticMethod() {
		assertNotNull(ClassTools.staticMethod(this.getClass(), "staticTestMethod"));
	}

	public void testStaticMethod_() throws Exception {
		assertNotNull(ClassTools.staticMethod_(this.getClass(), "staticTestMethod"));
	}

	public static void staticTestMethod() {
		// NOP
	}

	public void testStaticMethodWithParm() {
		assertNotNull(ClassTools.staticMethod(this.getClass(), "staticTestMethodWithParm", String.class));
	}

	public void testStaticMethodWithParm_() throws Exception {
		assertNotNull(ClassTools.staticMethod_(this.getClass(), "staticTestMethodWithParm", String.class));
	}

	public static void staticTestMethodWithParm(String string) {
		assertNotNull(string);
	}

	public void testStaticMethod_noSuchMethod() {
		boolean exCaught = false;
		try {
			Method method = ClassTools.staticMethod(this.getClass(), "executeStaticMethod_BOGUS");
			fail("bogus: " + method);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
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

	public void testNewInstanceClass_() throws Exception {
		Vector<?> v = ClassTools.newInstance_(java.util.Vector.class);
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

	public void testNewInstanceClassClassObject_() throws Exception {
		int initialCapacity = 200;
		Vector<?> v = ClassTools.newInstance_(java.util.Vector.class, int.class, new Integer(initialCapacity));
		assertNotNull(v);
		assertEquals(0, v.size());
		Object[] elementData = (Object[]) ObjectTools.get(v, "elementData");
		assertEquals(initialCapacity, elementData.length);
	}

	public void testNewInstanceClassClassArrayObjectArray() {
		int initialCapacity = 200;
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = int.class;
		Object[] args = new Object[1];
		args[0] = new Integer(initialCapacity);
		Vector<?> v = ClassTools.newInstance(java.util.Vector.class, parmTypes, args);
		assertNotNull(v);
		assertEquals(0, v.size());
		Object[] elementData = (Object[]) ObjectTools.get(v, "elementData");
		assertEquals(initialCapacity, elementData.length);
	}

	public void testNewInstanceClassClassArrayObjectArray_exception() {
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = int.class;
		Object[] args = new Object[1];
		args[0] = new Integer(-1);
		boolean exCaught = false;
		try {
			Vector<?> v = ClassTools.newInstance(java.util.Vector.class, parmTypes, args);
			fail("bogus: " + v);
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue("RuntimeException not thrown", exCaught);

		parmTypes[0] = java.lang.String.class;
		args[0] = "foo";
		exCaught = false;
		try {
			Vector<?> v = ClassTools.newInstance(java.util.Vector.class, parmTypes, args);
			fail("bogus: " + v);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown", exCaught);
	}

	public void testDefaultConstructor() {
		assertNotNull(ClassTools.defaultConstructor(java.util.Vector.class));
	}

	public void testDefaultConstructor_() throws Exception {
		assertNotNull(ClassTools.defaultConstructor_(java.util.Vector.class));
	}

	public void testConstructor() {
		assertNotNull(ClassTools.constructor(java.util.Vector.class));
	}

	public void testConstructor_() throws Exception {
		assertNotNull(ClassTools.constructor_(java.util.Vector.class));
	}

	public void testConstructorClass() {
		assertNotNull(ClassTools.constructor(java.util.Vector.class, int.class));
	}

	public void testConstructorClass_() throws Exception {
		assertNotNull(ClassTools.constructor_(java.util.Vector.class, int.class));
	}

	public void testConstructor_noSuchMethod() {
		boolean exCaught = false;
		try {
			Constructor<?> ctor = ClassTools.constructor(this.getClass());
			fail("bogus: " + ctor);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
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
		Object[] args = new Object[1];
		args[0] = Boolean.TRUE;
		String s = (String) ClassTools.execute(java.lang.String.class, "valueOf", parmTypes, args);
		assertNotNull(s);
		assertEquals("true", s);

		boolean exCaught = false;
		Object bogusStaticMethodReturnValue = null;
		try {
			bogusStaticMethodReturnValue = ClassTools.execute(java.lang.String.class, "bogusStaticMethod", parmTypes, args);
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

		assertFalse(ClassTools.isPrimitiveWrapper((new int[0]).getClass()));
		assertFalse(ClassTools.isPrimitiveWrapper(this.getClass()));
		assertFalse(ClassTools.isPrimitiveWrapper((new java.lang.String[0]).getClass()));
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

	public void testIsVariablePrimitive() {
		assertTrue(ClassTools.isVariablePrimitive(boolean.class));
		assertTrue(ClassTools.isVariablePrimitive(int.class));
		assertTrue(ClassTools.isVariablePrimitive(float.class));

		assertFalse(ClassTools.isVariablePrimitive(void.class));
		assertFalse(ClassTools.isVariablePrimitive(java.lang.Void.class));
		assertFalse(ClassTools.isVariablePrimitive(java.lang.String.class));
		assertFalse(ClassTools.isVariablePrimitive(java.lang.Boolean.class));
		assertFalse(ClassTools.isVariablePrimitive(java.lang.Integer.class));
	}

	public void testPrimitiveWrapper() {
		assertEquals(java.lang.Void.class, ClassTools.primitiveWrapper(void.class));
		assertEquals(java.lang.Integer.class, ClassTools.primitiveWrapper(int.class));
		assertEquals(java.lang.Float.class, ClassTools.primitiveWrapper(float.class));
		assertEquals(java.lang.Boolean.class, ClassTools.primitiveWrapper(boolean.class));

		assertNull(ClassTools.primitiveWrapper(java.lang.String.class));
	}

	public void testPrimitiveForCode_char() {
		assertEquals(byte.class, ClassTools.primitiveForCode('B'));
		assertEquals(int.class, ClassTools.primitiveForCode('I'));
		assertEquals(void.class, ClassTools.primitiveForCode('V'));
		assertNull(ClassTools.primitiveForCode('X'));
	}

	public void testPrimitiveForCode_int() {
		assertEquals(byte.class, ClassTools.primitiveForCode((int) 'B'));
		assertEquals(int.class, ClassTools.primitiveForCode((int) 'I'));
		assertEquals(void.class, ClassTools.primitiveForCode((int) 'V'));
		assertNull(ClassTools.primitiveForCode((int) 'X'));
	}

	public void testPrimitiveCode() {
		assertEquals('I', ClassTools.primitiveCode(int.class));
		assertEquals('B', ClassTools.primitiveCode(byte.class));
		assertEquals(0,   ClassTools.primitiveCode((new byte[0]).getClass()));
		assertEquals(0,   ClassTools.primitiveCode(this.getClass()));
	}

	public void testForTypeDeclarationString() {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration("java.lang.String"));
	}

	public void testForTypeDeclarationString_exception() {
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forTypeDeclaration("java.lang.BOGUS");
			fail("bogus: " + clazz);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForTypeDeclarationCharArray() {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration("java.lang.String".toCharArray()));
	}

	public void testForTypeDeclarationCharArray_exception() {
		boolean exCaught = false;
		try {
			Class<?> clazz = ClassTools.forTypeDeclaration("java.lang.BOGUS".toCharArray());
			fail("bogus: " + clazz);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForTypeDeclarationString_() throws Exception {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration_("java.lang.String"));
	}

	public void testForTypeDeclarationCharArray_() throws Exception {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration_("java.lang.String".toCharArray()));
	}

	public void testForTypeDeclarationStringClassLoader() {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration("java.lang.String", this.getClass().getClassLoader()));
	}

	public void testForTypeDeclarationCharArrayClassLoader() {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration("java.lang.String".toCharArray(), this.getClass().getClassLoader()));
	}

	public void testForTypeDeclarationStringClassLoader_() throws Exception {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration_("java.lang.String", this.getClass().getClassLoader()));
	}

	public void testForTypeDeclarationCharArrayClassLoader_() throws Exception {
		assertEquals(java.lang.String.class, ClassTools.forTypeDeclaration_("java.lang.String".toCharArray(), this.getClass().getClassLoader()));
	}

	public void testForTypeDeclarationStringInt() throws Exception {
		assertEquals(int.class, ClassTools.forTypeDeclaration("int", 0));
		assertEquals(int[].class, ClassTools.forTypeDeclaration("int", 1));
		assertEquals(int[][][].class, ClassTools.forTypeDeclaration("int", 3));

		assertEquals(Object.class, ClassTools.forTypeDeclaration("java.lang.Object", 0));
		assertEquals(Object[][][].class, ClassTools.forTypeDeclaration("java.lang.Object", 3));
	}

	public void testForTypeDeclarationStringInt_exception() throws Exception {
		boolean exCaught = false;
		try {
			ClassTools.forTypeDeclaration(void.class.getName(), 1);
			fail("should not get here...");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForTypeDeclarationCharArrayInt() throws Exception {
		assertEquals(int.class, ClassTools.forTypeDeclaration("int".toCharArray(), 0));
		assertEquals(int[].class, ClassTools.forTypeDeclaration("int".toCharArray(), 1));
		assertEquals(int[][][].class, ClassTools.forTypeDeclaration("int".toCharArray(), 3));

		assertEquals(Object.class, ClassTools.forTypeDeclaration("java.lang.Object".toCharArray(), 0));
		assertEquals(Object[][][].class, ClassTools.forTypeDeclaration("java.lang.Object".toCharArray(), 3));
	}

	public void testForTypeDeclarationCharArrayInt_exception() throws Exception {
		boolean exCaught = false;
		try {
			ClassTools.forTypeDeclaration(void.class.getName().toCharArray(), 1);
			fail("should not get here...");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof ClassNotFoundException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testForTypeDeclarationStringInt_() throws Exception {
		assertEquals((new java.lang.String[0][0]).getClass(), ClassTools.forTypeDeclaration_("java.lang.String", 2));
	}

	public void testForTypeDeclarationCharArrayInt_() throws Exception {
		assertEquals((new java.lang.String[0][0]).getClass(), ClassTools.forTypeDeclaration_("java.lang.String".toCharArray(), 2));
	}

	public void testBuildMethodSignature() {
		assertEquals("foo(java.lang.String)", ClassTools.buildMethodSignature("foo", new Class<?>[] {java.lang.String.class}));
	}

	private Iterable<String> fieldNames(Iterable<Field> fields) {
		return new TransformationIterable<>(fields, FIELD_NAME_TRANSFORMER);
	}

	private Iterable<String> methodNames(Iterable<Method> methods) {
		return new TransformationIterable<>(methods, METHOD_NAME_TRANSFORMER);
	}

	private static final Transformer<Field, String> FIELD_NAME_TRANSFORMER = new FieldNameTransformer();
	static class FieldNameTransformer
		extends TransformerAdapter<Field, String>
	{
		@Override
		public String transform(Field field) {
			return field.getName();
		}
	}

	private static final Transformer<Method, String> METHOD_NAME_TRANSFORMER = new MethodNameTransformer();
	static class MethodNameTransformer
		extends TransformerAdapter<Method, String>
	{
		@Override
		public String transform(Method method) {
			return method.getName();
		}
	}

	public void testClassToolsConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(ClassTools.class);
			fail("bogus: " + at);
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
