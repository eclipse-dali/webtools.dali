/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * {@link Class} utility methods.
 * <p>
 * There are a number of convenience reflection methods.
 * These methods provide shortcuts for manipulating classes via
 * reflection; particularly when dealing with fields and/or methods that
 * are not publicly accessible or are inherited.
 * <p>
 * In most cases, all exceptions are handled and wrapped in
 * {@link java.lang.RuntimeException}s; so these methods should
 * be used when there should be no problems using reflection (i.e.
 * the referenced members are presumably present etc.).
 * <p>
 * There are also a number of methods whose names
 * end with an underscore. These methods declare the expected checked
 * exceptions (e.g. {@link NoSuchMethodException}, {@link NoSuchFieldException}).
 * These methods can be used to probe
 * for methods, fields, etc. that should be present but might not be.
 */
public final class ClassTools {

	public static final Class<?>[] EMPTY_ARRAY = new Class[0];

	public static final Class<?> OBJECT = java.lang.Object.class;

	public static final Class<?> VOID = void.class;
	public static final Class<java.lang.Void> VOID_WRAPPER = java.lang.Void.class;


	// ********** load class **********

	/**
	 * Return the specified class (without the checked exception).
	 * @see Class#forName(String)
	 */
	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(className, ex);
		}
	}

	/**
	 * @see #forName(String)
	 */
	public static Class<?> forName(char[] className) {
		return forName(String.copyValueOf(className));
	}

	/**
	 * @see Class#forName(String)
	 */
	public static Class<?> forName_(char[] className) throws ClassNotFoundException {
		return Class.forName(String.copyValueOf(className));
	}

	/**
	 * Return the specified class (without the checked exception).
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Class<?> forName(String className, boolean initialize, ClassLoader classLoader) {
		try {
			return Class.forName(className, initialize, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(className, ex);
		}
	}

	/**
	 * @see #forName(String, boolean, ClassLoader)
	 */
	public static Class<?> forName(char[] className, boolean initialize, ClassLoader classLoader) {
		return forName(String.copyValueOf(className), initialize, classLoader);
	}

	/**
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Class<?> forName_(char[] className, boolean initialize, ClassLoader classLoader) throws ClassNotFoundException {
		return Class.forName(String.copyValueOf(className), initialize, classLoader);
	}


	// ********** string representation **********

	/**
	 * Return a class name suitable for a "Dali standard"
	 * {@link Object#toString() toString()} implementation.
	 * {@link Class#getSimpleName()} isn't quite useful enough:<ul>
	 * <li>An <em>anonymous</em> class's simple name is an empty string.
	 *     Return the "Dali standard" name of the anonymous class's super class
	 *     instead, which is a bit more helpful.
	 * <li>A <em>member</em> or <em>local</em> class's simple name does not
	 *     include its context. Prefix the class's simple name with its
	 *     enclosing class's "Dali standard" name.
	 * </ul>
	 * @see Object#toString()
	 */
	public static String toStringName(Class<?> javaClass) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendToStringName(sb, javaClass);
		return sb.toString();
	}

	/**
	 * Append a class name suitable for a "Dali standard"
	 * {@link Object#toString() toString()} implementation to the specified
	 * string builder.
	 * 
	 * @see #toStringName(Class)
	 * @see Object#toString()
	 */
	static void appendToStringNameTo(Class<?> javaClass, StringBuilder sb) {
		if (javaClass.isAnonymousClass()) {
			appendToStringNameTo(javaClass.getSuperclass(), sb);  // recurse
		} else {
			Class<?> enclosingClass = javaClass.getEnclosingClass();
			if (enclosingClass == null) {
				appendTopLevelToStringNameTo(javaClass, sb);  // top-level class
			} else {
				appendToStringNameTo(enclosingClass, sb);  // recurse
				sb.append('.');
				sb.append(javaClass.getSimpleName());
			}
		}
	}

	/**
	 * Pre-condition: the specified class is a top-level class
	 */
	private static void appendTopLevelToStringNameTo(Class<?> javaClass, StringBuilder sb) {
		String fullName = javaClass.getName();
		int dot = fullName.lastIndexOf('.');
		if (dot == -1) {
			sb.append(fullName);  // "default" package
		} else {
			sb.append(fullName, dot + 1, fullName.length());  // NB: end index is exclusive
		}
	}


	// ********** superclasses **********

	/**
	 * Return all the superclasses for the
	 * specified class, in order from the class's immediate superclass to
	 * {@link Object}.
	 * @see Class#getSuperclass()
	 */
	public static Iterable<Class<?>> allSuperclasses(Class<?> javaClass) {
		javaClass = javaClass.getSuperclass();
		if (javaClass == null) {
			return IterableTools.emptyIterable();
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		do {
			classes.add(javaClass);
			javaClass = javaClass.getSuperclass();
		} while (javaClass != null);
		return classes;
	}


	// ********** interfaces **********

	/**
	 * Return all the interfaces for the
	 * specified class, including inherited interfaces.
	 * @see Class#getInterfaces()
	 */
	public static Iterable<Class<?>> allInterfaces(Class<?> javaClass) {
		ArrayList<Class<?>> interfaces = new ArrayList<Class<?>>();
		for (Class<?> tempClass = javaClass; tempClass != null; tempClass = tempClass.getSuperclass()) {
			CollectionTools.<Class<?>>addAll(interfaces, tempClass.getInterfaces());
		}
		return interfaces;
	}


	// ********** fields **********

	/**
	 * Return the value of the specified class's static field with the specified
	 * name. Useful for accessing private, package, or protected fields.
	 * @see Field#get(Object)
	 */
	public static Object get(Class<?> javaClass, String fieldName) {
		try {
			return get_(javaClass, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, javaClass, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, javaClass, fieldName), ex);
		}
	}

	/**
	 * @see #get(Class, String)
	 */
	public static Object get_(Class<?> javaClass, String fieldName)
		throws NoSuchFieldException, IllegalAccessException
	{
		return field_(javaClass, fieldName).get(null);
	}

	/**
	 * Set the value of the specified class's static field with the specified
	 * name to the specified value. Useful for accessing private, package, or
	 * protected fields.
	 * @see Field#set(Object, Object)
	 */
	public static void set(Class<?> javaClass, String fieldName, Object value) {
		try {
			set_(javaClass, fieldName, value);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, javaClass, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, javaClass, fieldName), ex);
		}
	}

	/**
	 * @see #set(Class, String, Object)
	 */
	public static void set_(Class<?> javaClass, String fieldName, Object value)
		throws NoSuchFieldException, IllegalAccessException
	{
		field_(javaClass, fieldName).set(null, value);
	}

	/**
	 * Return the specified class's field with the specified name.
	 * If the class does not directly
	 * define the field, look for it in the class's superclasses.
	 * Make any private/package/protected field accessible.
	 * @see Class#getDeclaredField(String)
	 */
	public static Field field(Class<?> javaClass, String fieldName) {
		try {
			return field_(javaClass, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, javaClass, fieldName), ex);
		}
	}

	/**
	 * @see #field(Class, String)
	 */
	public static Field field_(Class<?> javaClass, String fieldName)
		throws NoSuchFieldException
	{
		Field field = null;
		try {
			field = javaClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException ex) {
			Class<?> superclass = javaClass.getSuperclass();
			if (superclass == null) {
				throw ex;
			}
			return field_(superclass, fieldName);  // recurse
		}
		field.setAccessible(true);
		return field;
	}

	/**
	 * Return all the fields for the
	 * specified class, including inherited fields.
	 * Make any private/package/protected fields accessible.
	 * @see Class#getDeclaredFields()
	 */
	public static Iterable<Field> allFields(Class<?> javaClass) {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (Class<?> tempClass = javaClass; tempClass != null; tempClass = tempClass.getSuperclass()) {
			CollectionTools.addAll(fields, declaredFields(tempClass));
		}
		return makeAccessible(fields);
	}

	/**
	 * Return the declared fields for the specified class.
	 * Make any private/package/protected fields accessible.
	 * @see Class#getDeclaredFields()
	 */
	public static Iterable<Field> declaredFields(Class<?> javaClass) {
		return makeAccessible(IterableTools.iterable(javaClass.getDeclaredFields()));
	}


	// ********** methods **********

	/**
	 * Execute the specified zero-argument static method.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 */
	public static Object execute(Class<?> javaClass, String methodName) {
		return execute(javaClass, methodName, EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * @see #execute(Class, String)
	 */
	public static Object execute_(Class<?> javaClass, String methodName)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return execute_(javaClass, methodName, EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Execute the specified one-argument method.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 */
	public static Object execute(Class<?> javaClass, String methodName, Class<?> parameterType, Object argument) {
		return execute(javaClass, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * @see #execute(Class, String, Class, Object)
	 */
	public static Object execute_(Class<?> javaClass, String methodName, Class<?> parameterType, Object argument)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return execute_(javaClass, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Execute the specified method.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 */
	public static Object execute(Class<?> javaClass, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		try {
			return execute_(javaClass, methodName, parameterTypes, arguments);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(buildMethodExceptionMessage(ex, javaClass, methodName, parameterTypes), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(buildMethodExceptionMessage(ex, javaClass, methodName, parameterTypes), ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(buildMethodExceptionMessage(ex, javaClass, methodName, parameterTypes), ex);
		}
	}

	/**
	 * @see #execute(Class, String, Class[], Object[])
	 */
	public static Object execute_(Class<?> javaClass, String methodName, Class<?>[] parameterTypes, Object[] arguments)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return staticMethod_(javaClass, methodName, parameterTypes).invoke(null, arguments);
	}

	/**
	 * Return the specified class's zero-argument method with the specified
	 * name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method method(Class<?> javaClass, String methodName) {
		return method(javaClass, methodName, EMPTY_ARRAY);
	}

	/**
	 * @see #method(Class, String)
	 */
	public static Method method_(Class<?> javaClass, String methodName)
		throws NoSuchMethodException
	{
		return method_(javaClass, methodName, EMPTY_ARRAY);
	}

	/**
	 * Return the specified class's one-argument method with the specified
	 * name and parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method method(Class<?> javaClass, String methodName, Class<?> parameterType) {
		return method(javaClass, methodName, new Class[] {parameterType});
	}

	/**
	 * @see #method(Class, String, Class)
	 */
	public static Method method_(Class<?> javaClass, String methodName, Class<?> parameterType)
		throws NoSuchMethodException
	{
		return method_(javaClass, methodName, new Class[] {parameterType});
	}

	/**
	 * Return the specified class's method with the specified
	 * name and parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method method(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		try {
			return method_(javaClass, methodName, parameterTypes);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(buildMethodExceptionMessage(ex, javaClass, methodName, parameterTypes), ex);
		}
	}

	/**
	 * @see #method(Class, String, Class[])
	 */
	public static Method method_(Class<?> javaClass, String methodName, Class<?>[] parameterTypes)
		throws NoSuchMethodException
	{
		Method method = null;
		try {
			method = javaClass.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException ex) {
			Class<?> superclass = javaClass.getSuperclass();
			if (superclass == null) {
				throw ex;
			}
			// recurse
			return method_(superclass, methodName, parameterTypes);
		}
		method.setAccessible(true);
		return method;
	}

	/**
	 * Return the zero-argument static method for the specified class
	 * and method name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method staticMethod(Class<?> javaClass, String methodName) {
		return staticMethod(javaClass, methodName, EMPTY_ARRAY);
	}

	/**
	 * @see #staticMethod(Class, String)
	 */
	public static Method staticMethod_(Class<?> javaClass, String methodName)
		throws NoSuchMethodException
	{
		return staticMethod_(javaClass, methodName, EMPTY_ARRAY);
	}

	/**
	 * Return the one-argument static method for the specified class, method name,
	 * and formal parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method staticMethod(Class<?> javaClass, String methodName, Class<?> parameterType) {
		return staticMethod(javaClass, methodName, new Class[] {parameterType});
	}

	/**
	 * @see #staticMethod(Class, String, Class)
	 */
	public static Method staticMethod_(Class<?> javaClass, String methodName, Class<?> parameterType)
		throws NoSuchMethodException
	{
		return staticMethod_(javaClass, methodName, new Class[] {parameterType});
	}

	/**
	 * Return the static method for the specified class, method name,
	 * and formal parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method staticMethod(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		try {
			return staticMethod_(javaClass, methodName, parameterTypes);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(buildMethodExceptionMessage(ex, javaClass, methodName, parameterTypes), ex);
		}
	}

	/**
	 * @see #staticMethod(Class, String, Class[])
	 */
	public static Method staticMethod_(Class<?> javaClass, String methodName, Class<?>[] parameterTypes)
		throws NoSuchMethodException
	{
		Method method = method_(javaClass, methodName, parameterTypes);
		if (Modifier.isStatic(method.getModifiers())) {
			return method;
		}
		throw new NoSuchMethodException(buildFullyQualifiedMethodSignature(javaClass, methodName, parameterTypes));
	}

	/**
	 * Return all the methods for the
	 * specified class, including inherited methods.
	 * Make any private/package/protected methods accessible.
	 */
	public static Iterable<Method> allMethods(Class<?> javaClass) {
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Class<?> tempClass = javaClass; tempClass != null; tempClass = tempClass.getSuperclass()) {
			CollectionTools.addAll(methods, declaredMethods(tempClass));
		}
		return makeAccessible(methods);
	}

	/**
	 * Return the declared methods for the specified class.
	 * Make any private/package/protected methods accessible.
	 */
	public static Iterable<Method> declaredMethods(Class<?> javaClass) {
		return makeAccessible(IterableTools.iterable(javaClass.getDeclaredMethods()));
	}


	// ********** constructors **********

	/**
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 */
	public static <T> T newInstance(Class<T> javaClass) {
		return newInstance(javaClass, EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * @see #newInstance(Class)
	 */
	public static <T> T newInstance_(Class<T> javaClass)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		return newInstance_(javaClass, EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and argument.
	 */
	public static <T> T newInstance(Class<T> javaClass, Class<?> parameterType, Object argument) {
		return newInstance(javaClass, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * @see #newInstance(Class, Class, Object)
	 */
	public static <T> T newInstance_(Class<T> javaClass, Class<?> parameterType, Object argument)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		return newInstance_(javaClass, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and arguments.
	 */
	public static <T> T newInstance(Class<T> javaClass, Class<?>[] parameterTypes, Object... arguments) {
		try {
			return newInstance_(javaClass, parameterTypes, arguments);
		} catch (InstantiationException ex) {
			throw new RuntimeException(buildConstructorExceptionMessage(ex, javaClass, parameterTypes), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(buildConstructorExceptionMessage(ex, javaClass, parameterTypes), ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(buildConstructorExceptionMessage(ex, javaClass, parameterTypes), ex);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(buildConstructorExceptionMessage(ex, javaClass, parameterTypes), ex);
		}
	}

	/**
	 * @see #newInstance(Class, Class[], Object[])
	 */
	public static <T> T newInstance_(Class<T> javaClass, Class<?>[] parameterTypes, Object... arguments)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		return constructor_(javaClass, parameterTypes).newInstance(arguments);
	}

	/**
	 * Return the default (zero-argument) constructor
	 * for the specified class.
	 * Make any private/package/protected constructor accessible.
	 */
	public static <T> Constructor<T> defaultConstructor(Class<T> javaClass) {
		return constructor(javaClass);
	}

	/**
	 * @see #defaultConstructor(Class)
	 */
	public static <T> Constructor<T> defaultConstructor_(Class<T> javaClass)
		throws NoSuchMethodException
	{
		return constructor_(javaClass);
	}

	/**
	 * @see #defaultConstructor(Class)
	 */
	public static <T> Constructor<T> constructor(Class<T> javaClass) {
		return constructor(javaClass, EMPTY_ARRAY);
	}

	/**
	 * @see #constructor(Class)
	 */
	public static <T> Constructor<T> constructor_(Class<T> javaClass)
		throws NoSuchMethodException
	{
		return constructor_(javaClass, EMPTY_ARRAY);
	}

	/**
	 * Return the constructor for the specified class
	 * and formal parameter type.
	 * Make any private/package/protected constructor accessible.
	 */
	public static <T> Constructor<T> constructor(Class<T> javaClass, Class<?> parameterType) {
		return constructor(javaClass, new Class[] {parameterType});
	}

	/**
	 * @see #constructor(Class, Class)
	 */
	public static <T> Constructor<T> constructor_(Class<T> javaClass, Class<?> parameterType)
		throws NoSuchMethodException
	{
		return constructor_(javaClass, new Class[] {parameterType});
	}

	/**
	 * Return the constructor for the specified class
	 * and formal parameter types.
	 * Make any private/package/protected constructor accessible.
	 */
	public static <T> Constructor<T> constructor(Class<T> javaClass, Class<?>[] parameterTypes) {
		try {
			return constructor_(javaClass, parameterTypes);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(buildConstructorExceptionMessage(ex, javaClass, parameterTypes), ex);
		}
	}

	/**
	 * @see #constructor(Class, Class[])
	 */
	public static <T> Constructor<T> constructor_(Class<T> javaClass, Class<?>[] parameterTypes)
		throws NoSuchMethodException
	{
		Constructor<T> constructor = javaClass.getDeclaredConstructor(parameterTypes);
		constructor.setAccessible(true);
		return constructor;
	}

	/**
	 * Return the declared constructors for the specified class.
	 * Make any private/package/protected constructors accessible.
	 */
	public static <T> Iterable<Constructor<T>> declaredConstructors(Class<T> javaClass) {
		@SuppressWarnings("unchecked")
		Constructor<T>[] constructors = (Constructor<T>[]) javaClass.getDeclaredConstructors();
		return makeAccessible(IterableTools.iterable(constructors));
	}


	// ********** arrays **********

	/**
	 * Return the "array depth" of the specified class.
	 * The depth is the number of dimensions for an array type.
	 * Non-array types have a depth of zero.
	 */
	public static int arrayDepth(Class<?> javaClass) {
		int depth = 0;
		while (javaClass.isArray()) {
			depth++;
			javaClass = javaClass.getComponentType();
		}
		return depth;
	}

	/**
	 * Return the "element type" of the specified class.
	 * The element type is the base type held by an array type.
	 * A non-array type simply returns itself.
	 */
	public static Class<?> elementType(Class<?> javaClass) {
		while (javaClass.isArray()) {
			javaClass = javaClass.getComponentType();
		}
		return javaClass;
	}


	// ********** primitives **********

	/**
	 * Return the wrapper class corresponding to the specified
	 * primitive class. Return <code>null</code> if the specified class
	 * is not a primitive class.
	 */
	public static Class<?> primitiveWrapper(Class<?> primitiveClass) {
		for (Primitive primitive : PRIMITIVES) {
			if (primitive.javaClass == primitiveClass) {
				return primitive.wrapperClass;
			}
		}
		return null;
	}

	/**
	 * Return whether the specified class is a primitive wrapper
	 * class (i.e. <code>java.lang.Void</code> or one of the primitive
	 * variable wrapper classes, <code>java.lang.Boolean</code>,
	 * <code>java.lang.Integer</code>, <code>java.lang.Float</code>, etc.).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isPrimitiveWrapper(Class<?> javaClass) {
		if (javaClass.isArray() || (javaClass.getName().length() > MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH)) {
			return false;  // performance tweak
		}
		for (Primitive primitive : PRIMITIVES) {
			if (javaClass == primitive.wrapperClass) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified class is a "variable" primitive wrapper
	 * class (i.e. <code>java.lang.Boolean</code>,
	 * <code>java.lang.Integer</code>, <code>java.lang.Float</code>, etc.,
	 * but not <code>java.lang.Void</code>).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isVariablePrimitiveWrapper(Class<?> javaClass) {
		return isPrimitiveWrapper(javaClass)
			&& (javaClass != VOID_WRAPPER);
	}

	/**
	 * Return whether the specified class is a "variable" primitive
	 * class (i.e. <code>boolean</code>, <code>int</code>,
	 * <code>float</code>, etc., but not <code>void</code>).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isVariablePrimitive(Class<?> javaClass) {
		return javaClass.isPrimitive() && (javaClass != VOID);
	}

	/**
	 * @see #primitiveForCode(char)
	 */
	public static Class<?> primitiveForCode(int classCode) {
		return primitiveForCode((char) classCode);
	}

	/**
	 * Return the primitive class for the specified primitive class code.
	 * Return <code>null</code> if the specified code
	 * is not a primitive class code.
	 * @see java.lang.Class#getName()
	 */
	public static Class<?> primitiveForCode(char classCode) {
		for (Primitive primitive : PRIMITIVES) {
			if (primitive.code == classCode) {
				return primitive.javaClass;
			}
		}
		return null;
	}

	/**
	 * Return the class code for the specified primitive class.
	 * Return zero if the specified class is not a primitive class.
	 * @see java.lang.Class#getName()
	 */
	public static char primitiveCode(Class<?> primitiveClass) {
		if (( ! primitiveClass.isArray()) && (primitiveClass.getName().length() <= MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			for (Primitive primitive : PRIMITIVES) {
				if (primitive.javaClass == primitiveClass) {
					return primitive.code;
				}
			}
		}
		return 0;
	}


	// ********** primitive constants **********

	static final Iterable<Primitive> PRIMITIVES = buildPrimitives();

	public static final char BYTE_CODE = 'B';
	public static final char CHAR_CODE = 'C';
	public static final char DOUBLE_CODE = 'D';
	public static final char FLOAT_CODE = 'F';
	public static final char INT_CODE = 'I';
	public static final char LONG_CODE = 'J';
	public static final char SHORT_CODE = 'S';
	public static final char BOOLEAN_CODE = 'Z';
	public static final char VOID_CODE = 'V';

	static final int MAX_PRIMITIVE_CLASS_NAME_LENGTH = calculateMaxPrimitiveClassNameLength();
	static final int MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH = calculateMaxPrimitiveWrapperClassNameLength();

	private static int calculateMaxPrimitiveClassNameLength() {
		int max = -1;
		for (Primitive primitive : PRIMITIVES) {
			int len = primitive.javaClassName.length;
			if (len > max) {
				max = len;
			}
		}
		return max;
	}

	private static int calculateMaxPrimitiveWrapperClassNameLength() {
		int max = -1;
		for (Primitive primitive : PRIMITIVES) {
			int len = primitive.wrapperClassName.length;
			if (len > max) {
				max = len;
			}
		}
		return max;
	}

	/**
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	private static Iterable<Primitive> buildPrimitives() {
		Primitive[] array = new Primitive[9];
		array[0] = new Primitive(BYTE_CODE, java.lang.Byte.class);
		array[1] = new Primitive(CHAR_CODE, java.lang.Character.class);
		array[2] = new Primitive(DOUBLE_CODE, java.lang.Double.class);
		array[3] = new Primitive(FLOAT_CODE, java.lang.Float.class);
		array[4] = new Primitive(INT_CODE, java.lang.Integer.class);
		array[5] = new Primitive(LONG_CODE, java.lang.Long.class);
		array[6] = new Primitive(SHORT_CODE, java.lang.Short.class);
		array[7] = new Primitive(BOOLEAN_CODE, java.lang.Boolean.class);
		array[8] = new Primitive(VOID_CODE, java.lang.Void.class);
		return IterableTools.iterable(array);
	}

	static class Primitive {
		final char code;
		final Class<?> javaClass;
		final char[] javaClassName;
		final Class<?> wrapperClass;
		final char[] wrapperClassName;
		private static final String WRAPPER_CLASS_TYPE_FIELD_NAME = "TYPE"; //$NON-NLS-1$
		// e.g. java.lang.Boolean.TYPE => boolean.class
		Primitive(char code, Class<?> wrapperClass) {
			this.code = code;
			this.javaClass = (Class<?>) get(wrapperClass, WRAPPER_CLASS_TYPE_FIELD_NAME);
			this.javaClassName = this.javaClass.getName().toCharArray();
			this.wrapperClass = wrapperClass;
			this.wrapperClassName = wrapperClass.getName().toCharArray();
		}
	}


	// ********** type declarations **********

	/**
	 * Return the class for the specified {@link TypeDeclarationTools type declaration}.
	 */
	public static Class<?> forTypeDeclaration(String typeDeclaration) {
		return forTypeDeclaration(typeDeclaration, null);
	}

	/**
	 * @see #forTypeDeclaration(String)
	 */
	public static Class<?> forTypeDeclaration(char[] typeDeclaration) {
		return forTypeDeclaration(typeDeclaration, null);
	}

	/**
	 * @see #forTypeDeclaration(String)
	 */
	public static Class<?> forTypeDeclaration_(String typeDeclaration)
		throws ClassNotFoundException
	{
		return forTypeDeclaration_(typeDeclaration, null);
	}

	/**
	 * @see #forTypeDeclaration_(String)
	 */
	public static Class<?> forTypeDeclaration_(char[] typeDeclaration)
		throws ClassNotFoundException
	{
		return forTypeDeclaration_(typeDeclaration, null);
	}

	/**
	 * Return the class for the specified {@link TypeDeclarationTools type declaration},
	 * using the specified class loader.
	 */
	public static Class<?> forTypeDeclaration(String typeDeclaration, ClassLoader classLoader) {
		try {
			return forTypeDeclaration_(typeDeclaration, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * @see #forTypeDeclaration(String, ClassLoader)
	 */
	public static Class<?> forTypeDeclaration(char[] typeDeclaration, ClassLoader classLoader) {
		try {
			return forTypeDeclaration_(typeDeclaration, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * @see #forTypeDeclaration(String, ClassLoader)
	 */
	public static Class<?> forTypeDeclaration_(String typeDeclaration, ClassLoader classLoader)
		throws ClassNotFoundException
	{
		typeDeclaration = StringTools.removeAllWhitespace(typeDeclaration);
		int arrayDepth = TypeDeclarationTools.arrayDepth_(typeDeclaration);
		String elementTypeName = TypeDeclarationTools.elementTypeName_(typeDeclaration, arrayDepth);
		return forTypeDeclaration_(elementTypeName, arrayDepth, classLoader);
	}

	/**
	 * @see #forTypeDeclaration_(String, ClassLoader)
	 */
	public static Class<?> forTypeDeclaration_(char[] typeDeclaration, ClassLoader classLoader)
		throws ClassNotFoundException
	{
		typeDeclaration = CharArrayTools.removeAllWhitespace(typeDeclaration);
		int arrayDepth = TypeDeclarationTools.arrayDepth_(typeDeclaration);
		char[] elementTypeName = TypeDeclarationTools.elementTypeName_(typeDeclaration, arrayDepth);
		return forTypeDeclaration_(elementTypeName, arrayDepth, classLoader);
	}

	/**
	 * Return the class for the specified "type declaration".
	 */
	public static Class<?> forTypeDeclaration(String elementTypeName, int arrayDepth) {
		return forTypeDeclaration(elementTypeName, arrayDepth, null);
	}

	/**
	 * @see #forTypeDeclaration(String, int)
	 */
	public static Class<?> forTypeDeclaration(char[] elementTypeName, int arrayDepth) {
		return forTypeDeclaration(elementTypeName, arrayDepth, null);
	}

	/**
	 * @see #forTypeDeclaration(String, int)
	 */
	public static Class<?> forTypeDeclaration_(String elementTypeName, int arrayDepth)
		throws ClassNotFoundException
	{
		return forTypeDeclaration_(elementTypeName, arrayDepth, null);
	}

	/**
	 * @see #forTypeDeclaration_(String, int)
	 */
	public static Class<?> forTypeDeclaration_(char[] elementTypeName, int arrayDepth)
		throws ClassNotFoundException
	{
		return forTypeDeclaration_(elementTypeName, arrayDepth, null);
	}

	/**
	 * Return the class for the specified "type declaration",
	 * using the specified class loader.
	 */
	public static Class<?> forTypeDeclaration(String elementTypeName, int arrayDepth, ClassLoader classLoader) {
		try {
			return forTypeDeclaration_(elementTypeName, arrayDepth, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * @see #forTypeDeclaration(String, int, ClassLoader)
	 */
	public static Class<?> forTypeDeclaration(char[] elementTypeName, int arrayDepth, ClassLoader classLoader) {
		try {
			return forTypeDeclaration_(elementTypeName, arrayDepth, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * @see #forTypeDeclaration(String, int, ClassLoader)
	 */
	// see the "Evaluation" of JDK bug 6446627 for a discussion of loading classes
	public static Class<?> forTypeDeclaration_(String elementTypeName, int arrayDepth, ClassLoader classLoader)
		throws ClassNotFoundException
	{
		// primitives cannot be loaded via Class.forName(),
		// so check for a primitive class name first
		Primitive primitive = null;
		if (elementTypeName.length() <= MAX_PRIMITIVE_CLASS_NAME_LENGTH) {  // performance tweak
			for (Primitive p : PRIMITIVES) {
				if (p.javaClass.getName().equals(elementTypeName)) {
					primitive = p;
					break;
				}
			}
		}

		// non-array
		if (arrayDepth == 0) {
			return (primitive != null) ? primitive.javaClass : Class.forName(elementTypeName, false, classLoader);
		}

		// array
		StringBuilder sb = new StringBuilder(100);
		for (int i = arrayDepth; i-- > 0; ) {
			sb.append('[');
		}
		if (primitive != null) {
			sb.append(primitive.code);
		} else {
			ClassNameTools.appendReferenceNameTo(elementTypeName, sb);
		}
		return Class.forName(sb.toString(), false, classLoader);
	}

	/**
	 * @see #forTypeDeclaration_(String, int, ClassLoader)
	 */
	public static Class<?> forTypeDeclaration_(char[] elementTypeName, int arrayDepth, ClassLoader classLoader)
		throws ClassNotFoundException
	{
		// primitives cannot be loaded via Class.forName(),
		// so check for a primitive class name first
		Primitive primitive = null;
		if (elementTypeName.length <= MAX_PRIMITIVE_CLASS_NAME_LENGTH) {  // performance tweak
			for (Primitive p : PRIMITIVES) {
				if (Arrays.equals(p.javaClassName, elementTypeName)) {
					primitive = p;
					break;
				}
			}
		}

		// non-array
		if (arrayDepth == 0) {
			return (primitive != null) ? primitive.javaClass : Class.forName(String.copyValueOf(elementTypeName), false, classLoader);
		}

		// array
		StringBuilder sb = new StringBuilder(100);
		for (int i = arrayDepth; i-- > 0; ) {
			sb.append('[');
		}
		if (primitive != null) {
			sb.append(primitive.code);
		} else {
			ClassNameTools.appendReferenceNameTo(elementTypeName, sb);
		}
		return Class.forName(sb.toString(), false, classLoader);
	}


	// ********** misc **********

	private static <A extends AccessibleObject> Iterable<A> makeAccessible(Iterable<A> objects) {
		for (AccessibleObject object : objects) {
			object.setAccessible(true);
		}
		return objects;
	}

	/**
	 * Build an exception message for the specified field.
	 * @see ObjectTools#buildFieldExceptionMessage(Exception, Object, String)
	 */
	static String buildFieldExceptionMessage(Exception ex, Class<?> javaClass, String fieldName) {
		StringBuilder sb = new StringBuilder(200);
		sb.append(ex);
		sb.append(StringTools.CR);
		sb.append(javaClass.getName());
		sb.append('.');
		sb.append(fieldName);
		return sb.toString();
	}

	/**
	 * Build an exception message for the specified method.
	 */
	private static String buildMethodExceptionMessage(Exception ex, Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		StringBuilder sb = new StringBuilder(200);
		sb.append(ex);
		sb.append(StringTools.CR);
		appendFullyQualifiedMethodSignature(sb, javaClass, methodName, parameterTypes);
		return sb.toString();
	}

	/**
	 * Build an invocation target exception message for the specified method.
	 */
	private static String buildMethodExceptionMessage(InvocationTargetException ex, Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		StringBuilder sb = new StringBuilder(200);
		appendFullyQualifiedMethodSignature(sb, javaClass, methodName, parameterTypes);
		sb.append(StringTools.CR);
		sb.append(ex.getTargetException());
		return sb.toString();
	}

	/**
	 * Return a string representation of the specified method.
	 */
	private static String buildFullyQualifiedMethodSignature(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		StringBuilder sb = new StringBuilder(200);
		appendFullyQualifiedMethodSignature(sb, javaClass, methodName, parameterTypes);
		return sb.toString();
	}

	private static void appendFullyQualifiedMethodSignature(StringBuilder sb, Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		sb.append(javaClass.getName());
		appendMethodSignature(sb, methodName, parameterTypes);
	}

	/**
	 * Return a string representation of the specified method.
	 */
	public static String buildMethodSignature(String methodName, Class<?>[] parameterTypes) {
		StringBuilder sb = new StringBuilder(200);
		appendMethodSignature(sb, methodName, parameterTypes);
		return sb.toString();
	}

	private static void appendMethodSignature(StringBuilder sb, String methodName, Class<?>[] parameterTypes) {
		// method name is null for constructors
		if (methodName != null) {
			sb.append('.');
			sb.append(methodName);
		}
		sb.append('(');
		if (parameterTypes.length > 0) {
			for (Class<?> parameterType : parameterTypes) {
				sb.append(parameterType.getName());
				sb.append(", "); //$NON-NLS-1$
			}
			sb.setLength(sb.length() - 2);  // strip off extra comma
		}
		sb.append(')');
	}

	/**
	 * Build an exception message for the specified constructor.
	 */
	private static String buildConstructorExceptionMessage(Exception ex, Class<?> javaClass, Class<?>[] parameterTypes) {
		return buildMethodExceptionMessage(ex, javaClass, null, parameterTypes);
	}

	/**
	 * Build an invocation target exception message for the specified constructor.
	 */
	private static String buildConstructorExceptionMessage(InvocationTargetException ex, Class<?> javaClass, Class<?>[] parameterTypes) {
		return buildMethodExceptionMessage(ex, javaClass, null, parameterTypes);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ClassTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
