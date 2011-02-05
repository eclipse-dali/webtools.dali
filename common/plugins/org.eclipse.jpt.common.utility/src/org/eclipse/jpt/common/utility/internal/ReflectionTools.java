/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;

/**
 * Convenience methods related to the <code>java.lang.reflect</code> package.
 * These methods provide shortcuts for manipulating objects via
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
public final class ReflectionTools {

	public static final Class<?>[] ZERO_PARAMETER_TYPES = new Class[0];
	public static final Object[] ZERO_ARGUMENTS = new Object[0];
	private static final String CR = StringTools.CR;

	public static final Class<?> VOID_CLASS = void.class;
	public static final Class<java.lang.Void> VOID_WRAPPER_CLASS = java.lang.Void.class;


	// ********** fields **********

	/**
	 * Get a field value, given the containing object and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Object.getFieldValue(String fieldName)</code>
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		try {
			return getFieldValue_(object, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(object, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(object, fieldName), ex);
		}
	}

	/**
	 * Get a field value, given the containing object and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Object.getFieldValue(String fieldName)</code>
	 */
	public static Object getFieldValue_(Object object, String fieldName)
		throws NoSuchFieldException, IllegalAccessException
	{
		return getField_(object, fieldName).get(object);
	}

	/**
	 * Get a static field value, given the containing class and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Class.getStaticFieldValue(String fieldName)</code>
	 */
	public static Object getStaticFieldValue(Class<?> javaClass, String fieldName) {
		try {
			return getStaticFieldValue_(javaClass, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(javaClass, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(javaClass, fieldName), ex);
		}
	}

	/**
	 * Get a static field value, given the containing class and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Class.getStaticFieldValue(String fieldName)</code>
	 */
	public static Object getStaticFieldValue_(Class<?> javaClass, String fieldName)
		throws NoSuchFieldException, IllegalAccessException
	{
		return getField_(javaClass, fieldName).get(null);
	}

	/**
	 * Set a field value, given the containing object, field name, and new value.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Object.setFieldValue(String fieldName, Object value)</code>
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) {
		try {
			setFieldValue_(object, fieldName, value);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(object, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(object, fieldName), ex);
		}
	}

	/**
	 * Set a field value, given the containing object, field name, and new value.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Object.setFieldValue(String fieldName, Object value)</code>
	 */
	public static void setFieldValue_(Object object, String fieldName, Object value)
		throws NoSuchFieldException, IllegalAccessException
	{
		getField_(object, fieldName).set(object, value);
	}

	/**
	 * Set a static field value, given the containing class, field name, and new value.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Class.setStaticFieldValue(String fieldName, Object value)</code>
	 */
	public static void setStaticFieldValue(Class<?> javaClass, String fieldName, Object value) {
		try {
			setStaticFieldValue_(javaClass, fieldName, value);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(javaClass, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(javaClass, fieldName), ex);
		}
	}

	/**
	 * Set a static field value, given the containing class, field name, and new value.
	 * Useful for accessing private, package, or protected fields.
	 * <p>
	 * <code>Class.setStaticFieldValue(String fieldName, Object value)</code>
	 */
	public static void setStaticFieldValue_(Class<?> javaClass, String fieldName, Object value)
		throws NoSuchFieldException, IllegalAccessException
	{
		getField_(javaClass, fieldName).set(null, value);
	}

	/**
	 * Convenience method.
	 * Return a field for the specified object and field name.
	 * If the object's class does not directly
	 * define the field, look for it in the class's superclasses.
	 * Make any private/package/protected field accessible.
	 * <p>
	 * <code>Object.getField(String fieldName)</code>
	 */
	public static Field getField(Object object, String fieldName) {
		try {
			return getField_(object, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(object, fieldName), ex);
		}
	}

	/**
	 * Convenience method.
	 * Return a field for the specified object and field name.
	 * If the object's class does not directly
	 * define the field, look for it in the class's superclasses.
	 * Make any private/package/protected field accessible.
	 * <p>
	 * <code>Object.getField(String fieldName)</code>
	 */
	public static Field getField_(Object object, String fieldName)
		throws NoSuchFieldException
	{
		return getField_(object.getClass(), fieldName);
	}

	/**
	 * Return a field for the specified class and field name.
	 * If the class does not directly
	 * define the field, look for it in the class's superclasses.
	 * Make any private/package/protected field accessible.
	 */
	public static Field getField(Class<?> javaClass, String fieldName) {
		try {
			return getField_(javaClass, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedFieldName(javaClass, fieldName), ex);
		}
	}

	/**
	 * Return a field for the specified class and field name.
	 * If the class does not directly
	 * define the field, look for it in the class's superclasses.
	 * Make any private/package/protected field accessible.
	 */
	public static Field getField_(Class<?> javaClass, String fieldName)
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
			return getField_(superclass, fieldName);  // recurse
		}
		field.setAccessible(true);
		return field;
	}

	/**
	 * Return all the fields for the
	 * specified class, including inherited fields.
	 * Make any private/package/protected fields accessible.
	 * <p>
	 * <code>Class.getAllFields()</code>
	 */
	public static Iterable<Field> getAllFields(Class<?> javaClass) {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (Class<?> tempClass = javaClass; tempClass != null; tempClass = tempClass.getSuperclass()) {
			addDeclaredFieldsTo(tempClass, fields);
		}
		return fields;
	}

	/*
	 * Add the declared fields for the specified class
	 * to the specified list.
	 */
	private static void addDeclaredFieldsTo(Class<?> javaClass, ArrayList<Field> fields) {
		for (Field field : getDeclaredFields(javaClass)) {
			fields.add(field);
		}
	}

	/**
	 * Return the declared fields for the specified class.
	 * Make any private/package/protected fields accessible.
	 * <p>
	 * <code>Class.getAccessibleDeclaredFields()</code>
	 */
	public static Iterable<Field> getDeclaredFields(Class<?> javaClass) {
		Field[] fields = javaClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
		return new ArrayIterable<Field>(fields);
	}


	// ********** methods **********

	/**
	 * Convenience method.
	 * Execute a zero-argument method, given the receiver and method name.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Object.execute(String methodName)</code>
	 */
	public static Object executeMethod(Object receiver, String methodName) {
		return executeMethod(receiver, methodName, ZERO_PARAMETER_TYPES, ZERO_ARGUMENTS);
	}

	/**
	 * Convenience method.
	 * Execute a one-argument method, given the receiver,
	 * method name, parameter type, and argument.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Object.execute(String methodName, Class<?> parameterType, Object argument)</code>
	 */
	public static Object executeMethod(Object receiver, String methodName, Class<?> parameterType, Object argument) {
		return executeMethod(receiver, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Execute a method, given the receiver,
	 * method name, parameter types, and arguments.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Object.execute(String methodName, Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static Object executeMethod(Object receiver, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return executeMethod(getMethod(receiver, methodName, parameterTypes), receiver, arguments);
	}

	/**
	 * Execute the specified method, given the receiver and arguments.
	 * Return its result.
	 * Useful for invoking cached methods.
	 */
	public static Object executeMethod(Method method, Object receiver, Object[] arguments) {
		try {
			return method.invoke(receiver, arguments);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + CR + method, ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(method + CR + ex.getTargetException(), ex);
		}
	}

	/**
	 * Convenience method.
	 * Execute a zero-argument method,
	 * given the receiver and method name.
	 * Return its result.
	 * Throw an exception if the method is not defined.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Object.execute(String methodName)</code>
	 */
	public static Object executeMethod_(Object receiver, String methodName)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return executeMethod_(receiver, methodName, ZERO_PARAMETER_TYPES, ZERO_ARGUMENTS);
	}

	/**
	 * Convenience method.
	 * Execute a method, given the receiver,
	 * method name, parameter type, and argument.
	 * Return its result.
	 * Throw an exception if the method is not defined.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Object.execute(String methodName, Class<?> parameterType, Object argument)</code>
	 */
	public static Object executeMethod_(Object receiver, String methodName, Class<?> parameterType, Object argument)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return executeMethod_(receiver, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Execute a method, given the receiver,
	 * method name, parameter types, and arguments.
	 * Return its result.
	 * Throw an exception if the method is not defined.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Object.execute(String methodName, Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static Object executeMethod_(Object receiver, String methodName, Class<?>[] parameterTypes, Object[] arguments)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return getMethod_(receiver, methodName, parameterTypes).invoke(receiver, arguments);
	}

	/**
	 * Convenience method.
	 * Execute a zero-argument static method,
	 * given the class and method name.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Class.executeStaticMethod(String methodName)</code>
	 */
	public static Object executeStaticMethod(Class<?> javaClass, String methodName) {
		return executeStaticMethod(javaClass, methodName, ZERO_PARAMETER_TYPES, ZERO_ARGUMENTS);
	}

	/**
	 * Convenience method.
	 * Execute a static method, given the class,
	 * method name, parameter type, and argument.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Class.executeStaticMethod(String methodName, Class<?> parameterType, Object argument)</code>
	 */
	public static Object executeStaticMethod(Class<?> javaClass, String methodName, Class<?> parameterType, Object argument) {
		return executeStaticMethod(javaClass, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Execute a static method, given the class,
	 * method name, parameter types, and arguments.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Class.executeStaticMethod(String methodName, Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static Object executeStaticMethod(Class<?> javaClass, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		try {
			return executeStaticMethod_(javaClass, methodName, parameterTypes, arguments);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedMethodSignature(javaClass, methodName, parameterTypes), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedMethodSignature(javaClass, methodName, parameterTypes), ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(buildFullyQualifiedMethodSignature(javaClass, methodName, parameterTypes) + CR + ex.getTargetException(), ex);
		}
	}

	/**
	 * Convenience method.
	 * Execute a zero-argument static method,
	 * given the class and method name.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Class.executeStaticMethod(String methodName)</code>
	 */
	public static Object executeStaticMethod_(Class<?> javaClass, String methodName)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return executeStaticMethod_(javaClass, methodName, ZERO_PARAMETER_TYPES, ZERO_ARGUMENTS);
	}

	/**
	 * Convenience method.
	 * Execute a static method, given the class,
	 * method name, parameter type, and argument.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Class.executeStaticMethod(String methodName, Class<?> parameterType, Object argument)</code>
	 */
	public static Object executeStaticMethod_(Class<?> javaClass, String methodName, Class<?> parameterType, Object argument)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return executeStaticMethod_(javaClass, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Execute a static method, given the class,
	 * method name, parameter types, and arguments.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * <p>
	 * <code>Class.executeStaticMethod(String methodName, Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static Object executeStaticMethod_(Class<?> javaClass, String methodName, Class<?>[] parameterTypes, Object[] arguments)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return getStaticMethod_(javaClass, methodName, parameterTypes).invoke(null, arguments);
	}

	/**
	 * Convenience method.
	 * Return a zero-argument method for the specified class
	 * and method name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod(Class<?> javaClass, String methodName) {
		return getMethod(javaClass, methodName, ZERO_PARAMETER_TYPES);
	}

	/**
	 * Convenience method.
	 * Return a zero-argument method for the specified class
	 * and method name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod_(Class<?> javaClass, String methodName)
		throws NoSuchMethodException
	{
		return getMethod_(javaClass, methodName, ZERO_PARAMETER_TYPES);
	}

	/**
	 * Convenience method.
	 * Return a method for the specified class, method name,
	 * and formal parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod(Class<?> javaClass, String methodName, Class<?> parameterType) {
		return getMethod(javaClass, methodName, new Class[] {parameterType});
	}

	/**
	 * Convenience method.
	 * Return a method for the specified class, method name,
	 * and formal parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod_(Class<?> javaClass, String methodName, Class<?> parameterType)
		throws NoSuchMethodException
	{
		return getMethod_(javaClass, methodName, new Class[] {parameterType});
	}

	/**
	 * Return a method for the specified class, method name,
	 * and formal parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		try {
			return getMethod_(javaClass, methodName, parameterTypes);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedMethodSignature(javaClass, methodName, parameterTypes), ex);
		}
	}

	/**
	 * Return a method for the specified class, method name,
	 * and formal parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod_(Class<?> javaClass, String methodName, Class<?>[] parameterTypes)
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
			return getMethod_(superclass, methodName, parameterTypes);
		}
		method.setAccessible(true);
		return method;
	}

	/**
	 * Convenience method.
	 * Return a zero-argument method for the specified object
	 * and method name. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod(Object object, String methodName) {
		return getMethod(object.getClass(), methodName);
	}

	/**
	 * Convenience method.
	 * Return a zero-argument method for the specified object
	 * and method name. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod_(Object object, String methodName)
		throws NoSuchMethodException
	{
		return getMethod_(object.getClass(), methodName);
	}

	/**
	 * Convenience method.
	 * Return a method for the specified object, method name,
	 * and formal parameter types. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		return getMethod(object.getClass(), methodName, parameterTypes);
	}

	/**
	 * Convenience method.
	 * Return a method for the specified object, method name,
	 * and formal parameter types. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod_(Object object, String methodName, Class<?>[] parameterTypes)
		throws NoSuchMethodException
	{
		return getMethod_(object.getClass(), methodName, parameterTypes);
	}

	/**
	 * Convenience method.
	 * Return a method for the specified object, method name,
	 * and formal parameter type. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod(Object object, String methodName, Class<?> parameterType) {
		return getMethod(object.getClass(), methodName, parameterType);
	}

	/**
	 * Convenience method.
	 * Return a method for the specified object, method name,
	 * and formal parameter type. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getMethod_(Object object, String methodName, Class<?> parameterType)
		throws NoSuchMethodException
	{
		return getMethod_(object.getClass(), methodName, parameterType);
	}

	/**
	 * Convenience method.
	 * Return a zero-argument static method for the specified class
	 * and method name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getStaticMethod(Class<?> javaClass, String methodName) {
		return getStaticMethod(javaClass, methodName, ZERO_PARAMETER_TYPES);
	}

	/**
	 * Convenience method.
	 * Return a static method for the specified class, method name,
	 * and formal parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getStaticMethod(Class<?> javaClass, String methodName, Class<?> parameterTypes) {
		return getStaticMethod(javaClass, methodName, new Class[] {parameterTypes});
	}

	/**
	 * Return a static method for the specified class, method name,
	 * and formal parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getStaticMethod(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		try {
			return getStaticMethod_(javaClass, methodName, parameterTypes);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedMethodSignature(javaClass, methodName, parameterTypes), ex);
		}
	}

	/**
	 * Convenience method.
	 * Return a zero-argument static method for the specified class
	 * and method name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getStaticMethod_(Class<?> javaClass, String methodName)
		throws NoSuchMethodException
	{
		return getStaticMethod_(javaClass, methodName, ZERO_PARAMETER_TYPES);
	}

	/**
	 * Convenience method.
	 * Return a static method for the specified class, method name,
	 * and formal parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getStaticMethod_(Class<?> javaClass, String methodName, Class<?> parameterTypes)
		throws NoSuchMethodException
	{
		return getStaticMethod_(javaClass, methodName, new Class[] {parameterTypes});
	}

	/**
	 * Return a static method for the specified class, method name,
	 * and formal parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method getStaticMethod_(Class<?> javaClass, String methodName, Class<?>[] parameterTypes)
		throws NoSuchMethodException
	{
		Method method = getMethod_(javaClass, methodName, parameterTypes);
		if (Modifier.isStatic(method.getModifiers())) {
			return method;
		}
		throw new NoSuchMethodException(buildFullyQualifiedMethodSignature(javaClass, methodName, parameterTypes));
	}

	/**
	 * Return all the methods for the
	 * specified class, including inherited methods.
	 * Make any private/package/protected methods accessible.
	 * <p>
	 * <code>Class.getAllMethods()</code>
	 */
	public static Iterable<Method> getAllMethods(Class<?> javaClass) {
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Class<?> tempClass = javaClass; tempClass != null; tempClass = tempClass.getSuperclass()) {
			addDeclaredMethodsTo(tempClass, methods);
		}
		return methods;
	}

	/*
	 * Add the declared methods for the specified class
	 * to the specified list.
	 */
	private static void addDeclaredMethodsTo(Class<?> javaClass, ArrayList<Method> methods) {
		for (Method method : getDeclaredMethods(javaClass)) {
			methods.add(method);
		}
	}

	/**
	 * Return the declared methods for the specified class.
	 * Make any private/package/protected methods accessible.
	 * <p>
	 * <code>Class.getAccessibleDeclaredMethods()</code>
	 */
	public static Iterable<Method> getDeclaredMethods(Class<?> javaClass) {
		Method[] methods = javaClass.getDeclaredMethods();
		for (Method method : methods) {
			method.setAccessible(true);
		}
		return new ArrayIterable<Method>(methods);
	}


	// ********** constructors **********

	/**
	 * Return the default (zero-argument) constructor
	 * for the specified class.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getDefaultConstructor()</code>
	 */
	public static <T> Constructor<T> getDefaultConstructor(Class<T> javaClass) {
		return getConstructor(javaClass);
	}

	/**
	 * Convenience method.
	 * Return the default (zero-argument) constructor
	 * for the specified class.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getConstructor()</code>
	 */
	public static <T> Constructor<T> getConstructor(Class<T> javaClass) {
		return getConstructor(javaClass, ZERO_PARAMETER_TYPES);
	}

	/**
	 * Convenience method.
	 * Return the constructor for the specified class
	 * and formal parameter type.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getConstructor(Class<?> parameterType)</code>
	 */
	public static <T> Constructor<T> getConstructor(Class<T> javaClass, Class<?> parameterType) {
		return getConstructor(javaClass, new Class[] {parameterType});
	}

	/**
	 * Return the constructor for the specified class
	 * and formal parameter types.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getConstructor(Class<?>[] parameterTypes)</code>
	 */
	public static <T> Constructor<T> getConstructor(Class<T> javaClass, Class<?>[] parameterTypes) {
		try {
			return getConstructor_(javaClass, parameterTypes);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedConstructorSignature(javaClass, parameterTypes), ex);
		}
	}

	/**
	 * Return the default (zero-argument) constructor
	 * for the specified class.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getDefaultConstructor()</code>
	 */
	public static <T> Constructor<T> getDefaultConstructor_(Class<T> javaClass)
		throws NoSuchMethodException
	{
		return getConstructor_(javaClass);
	}

	/**
	 * Convenience method.
	 * Return the default (zero-argument) constructor
	 * for the specified class.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getConstructor()</code>
	 */
	public static <T> Constructor<T> getConstructor_(Class<T> javaClass)
		throws NoSuchMethodException
	{
		return getConstructor_(javaClass, ZERO_PARAMETER_TYPES);
	}

	/**
	 * Convenience method.
	 * Return the constructor for the specified class
	 * and formal parameter type.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getConstructor(Class<?> parameterType)</code>
	 */
	public static <T> Constructor<T> getConstructor_(Class<T> javaClass, Class<?> parameterType)
		throws NoSuchMethodException
	{
		return getConstructor_(javaClass, new Class[] {parameterType});
	}

	/**
	 * Return the constructor for the specified class
	 * and formal parameter types.
	 * Make any private/package/protected constructor accessible.
	 * <p>
	 * <code>Class.getConstructor(Class<?>[] parameterTypes)</code>
	 */
	public static <T> Constructor<T> getConstructor_(Class<T> javaClass, Class<?>[] parameterTypes)
		throws NoSuchMethodException
	{
		Constructor<T> constructor = javaClass.getDeclaredConstructor(parameterTypes);
		constructor.setAccessible(true);
		return constructor;
	}

	/**
	 * Return the declared constructors for the specified class.
	 * Make any private/package/protected constructors accessible.
	 * <p>
	 * <code>Class.getAccessibleDeclaredConstructors()</code>
	 */
	public static <T> Iterable<Constructor<T>> getDeclaredConstructors(Class<T> javaClass) {
		@SuppressWarnings("unchecked")
		Constructor<T>[] constructors = (Constructor<T>[]) javaClass.getDeclaredConstructors();
		for (Constructor<T> constructor : constructors) {
			constructor.setAccessible(true);
		}
		return new ArrayIterable<Constructor<T>>(constructors);
	}


	// ********** classes **********

	/**
	 * Convenience method.
	 * Return the specified class (without the checked exception).
	 */
	public static Class<?> classForName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(className, ex);
		}
	}

	/**
	 * Convenience method.
	 * Return the specified class (without the checked exception).
	 */
	public static Class<?> classForName(String className, boolean initialize, ClassLoader classLoader) {
		try {
			return Class.forName(className, initialize, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(className, ex);
		}
	}

	/**
	 * Return the "array depth" of the specified class.
	 * The depth is the number of dimensions for an array type.
	 * Non-array types have a depth of zero.
	 * <p>
	 * <code>Class.getArrayDepth()</code>
	 */
	public static int getArrayDepth(Class<?> javaClass) {
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
	 * <p>
	 * <code>Class.getElementType()</code>
	 */
	public static Class<?> getElementType(Class<?> javaClass) {
		while (javaClass.isArray()) {
			javaClass = javaClass.getComponentType();
		}
		return javaClass;
	}

	/**
	 * Return the wrapper class corresponding to the specified
	 * primitive class. Return <code>null</code> if the specified class
	 * is not a primitive class.
	 * <p>
	 * <code>Class.getWrapperClass()</code>
	 */
	public static Class<?> getWrapperClass(Class<?> primitiveClass) {
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
	 * <p>
	 * <code>Class.isPrimitiveWrapper()</code>
	 */
	public static boolean classIsPrimitiveWrapper(Class<?> javaClass) {
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
	 * <p>
	 * <code>Class.isVariablePrimitiveWrapper()</code>
	 */
	public static boolean classIsVariablePrimitiveWrapper(Class<?> javaClass) {
		return classIsPrimitiveWrapper(javaClass)
			&& (javaClass != VOID_WRAPPER_CLASS);
	}

	/**
	 * Return whether the specified class is a "variable" primitive
	 * class (i.e. <code>boolean</code>, <code>int</code>,
	 * <code>float</code>, etc., but not <code>void</code>).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 * <p>
	 * <code>Class.isVariablePrimitive()</code>
	 */
	public static boolean classIsVariablePrimitive(Class<?> javaClass) {
		return javaClass.isPrimitive() && (javaClass != VOID_CLASS);
	}

	/**
	 * Return the primitive class for the specified primitive class code.
	 * Return <code>null</code> if the specified code
	 * is not a primitive class code.
	 * @see java.lang.Class#getName()
	 */
	public static Class<?> getClassForCode(int classCode) {
		return getClassForCode((char) classCode);
	}

	/**
	 * Return the primitive class for the specified primitive class code.
	 * Return <code>null</code> if the specified code
	 * is not a primitive class code.
	 * @see java.lang.Class#getName()
	 */
	public static Class<?> getClassForCode(char classCode) {
		for (Primitive primitive : PRIMITIVES) {
			if (primitive.code == classCode) {
				return primitive.javaClass;
			}
		}
		return null;
	}

	/**
	 * Return the class code for the specified primitive class.
	 * Return <code>0</code> if the specified class
	 * is not a primitive class.
	 * @see java.lang.Class#getName()
	 */
	public static char getCodeForClass(Class<?> primitiveClass) {
		if (( ! primitiveClass.isArray()) && (primitiveClass.getName().length() <= MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			for (Primitive primitive : PRIMITIVES) {
				if (primitive.javaClass == primitiveClass) {
					return primitive.code;
				}
			}
		}
		return 0;
	}


	// ********** instantiation **********

	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * <p>
	 * <code>Class.newInstance()</code>
	 */
	public static Object newInstance(String className) {
		return newInstance(className, null);
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and argument.
	 * <p>
	 * <code>Class.newInstance(Class<?> parameterType, Object argument)</code>
	 */
	public static Object newInstance(String className, Class<?> parameterType, Object argument) {
		return newInstance(className, parameterType, argument, null);
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and arguments.
	 * <p>
	 * <code>Class.newInstance(Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static Object newInstance(String className, Class<?>[] parameterTypes, Object[] arguments) {
		return newInstance(className, parameterTypes, arguments, null);
	}

	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * Use the specified class loader to load the class.
	 * <p>
	 * <code>Class.newInstance()</code>
	 */
	public static Object newInstance(String className, ClassLoader classLoader) {
		return newInstance(classForName(className, false, classLoader));
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and argument.
	 * <p>
	 * <code>Class.newInstance(Class<?> parameterType, Object argument)</code>
	 */
	public static Object newInstance(String className, Class<?> parameterType, Object argument, ClassLoader classLoader) {
		return newInstance(classForName(className, false, classLoader), parameterType, argument);
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and arguments.
	 * <p>
	 * <code>Class.newInstance(Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static Object newInstance(String className, Class<?>[] parameterTypes, Object[] arguments, ClassLoader classLoader) {
		return newInstance(classForName(className, false, classLoader), parameterTypes, arguments);
	}

	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * <p>
	 * <code>Class.newInstance()</code>
	 */
	public static <T> T newInstance(Class<T> javaClass) {
		return newInstance(javaClass, ZERO_PARAMETER_TYPES, ZERO_ARGUMENTS);
	}

	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and argument.
	 * <p>
	 * <code>Class.newInstance(Class<?> parameterType, Object argument)</code>
	 */
	public static <T> T newInstance(Class<T> javaClass, Class<?> parameterType, Object argument) {
		return newInstance(javaClass, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and arguments.
	 * <p>
	 * <code>Class.newInstance(Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static <T> T newInstance(Class<T> javaClass, Class<?>[] parameterTypes, Object[] arguments) {
		try {
			return newInstance_(javaClass, parameterTypes, arguments);
		} catch (InstantiationException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedConstructorSignature(javaClass, parameterTypes), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedConstructorSignature(javaClass, parameterTypes), ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(buildFullyQualifiedConstructorSignature(javaClass, parameterTypes) + CR + ex.getTargetException(), ex);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex + CR + buildFullyQualifiedConstructorSignature(javaClass, parameterTypes), ex);
		}
	}

	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * <p>
	 * <code>Class.newInstance()</code>
	 */
	public static <T> T newInstance_(Class<T> javaClass)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		return newInstance_(javaClass, ZERO_PARAMETER_TYPES, ZERO_ARGUMENTS);
	}

	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and argument.
	 * <p>
	 * <code>Class.newInstance(Class<?> parameterType, Object argument)</code>
	 */
	public static <T> T newInstance_(Class<T> javaClass, Class<?> parameterType, Object argument)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		return newInstance_(javaClass, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and arguments.
	 * <p>
	 * <code>Class.newInstance(Class<?>[] parameterTypes, Object[] arguments)</code>
	 */
	public static <T> T newInstance_(Class<T> javaClass, Class<?>[] parameterTypes, Object[] arguments)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		return getConstructor_(javaClass, parameterTypes).newInstance(arguments);
	}


	// ********** type declarations **********

	/**
	 * Return the class for the specified "type declaration".
	 */
	public static Class<?> getClassForTypeDeclaration(String typeDeclaration) {
		return getClassForTypeDeclaration(typeDeclaration, null);
	}

	/**
	 * Return the class for the specified "type declaration".
	 */
	public static Class<?> getClassForTypeDeclaration_(String typeDeclaration)
		throws ClassNotFoundException
	{
		return getClassForTypeDeclaration_(typeDeclaration, null);
	}

	/**
	 * Return the class for the specified "type declaration",
	 * using the specified class loader.
	 */
	public static Class<?> getClassForTypeDeclaration(String typeDeclaration, ClassLoader classLoader) {
		TypeDeclaration td = buildTypeDeclaration(typeDeclaration);
		return getClassForTypeDeclaration(td.elementTypeName, td.arrayDepth, classLoader);
	}

	/**
	 * Return the class for the specified "type declaration",
	 * using the specified class loader.
	 */
	public static Class<?> getClassForTypeDeclaration_(String typeDeclaration, ClassLoader classLoader)
		throws ClassNotFoundException
	{
		TypeDeclaration td = buildTypeDeclaration(typeDeclaration);
		return getClassForTypeDeclaration_(td.elementTypeName, td.arrayDepth, classLoader);
	}

	private static TypeDeclaration buildTypeDeclaration(String typeDeclaration) {
		typeDeclaration = StringTools.removeAllWhitespace(typeDeclaration);
		int arrayDepth = getArrayDepthForTypeDeclaration_(typeDeclaration);
		String elementTypeName = getElementTypeNameForTypeDeclaration_(typeDeclaration, arrayDepth);
		return new TypeDeclaration(elementTypeName, arrayDepth);
	}

	/**
	 * Return the array depth for the specified "type declaration"; e.g.<ul>
	 * <li><code>"int[]"</code> returns <code>1</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>3</code>
	 * </ul>
	 */
	public static int getArrayDepthForTypeDeclaration(String typeDeclaration) {
		return getArrayDepthForTypeDeclaration_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * pre-condition: no whitespace in the type declaration.
	 */
	private static int getArrayDepthForTypeDeclaration_(String typeDeclaration) {
		int last = typeDeclaration.length() - 1;
		int depth = 0;
		int close = last;
		while (typeDeclaration.charAt(close) == ']') {
			if (typeDeclaration.charAt(close - 1) == '[') {
				depth++;
			} else {
				throw new IllegalArgumentException("invalid type declaration: " + typeDeclaration); //$NON-NLS-1$
			}
			close = last - (depth * 2);
		}
		return depth;
	}

	/**
	 * Return the element type name for the specified "type declaration"; e.g.<ul>
	 * <li><code>"int[]"</code> returns <code>"int"</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>"java.lang.String"</code>
	 * </ul>
	 */
	public static String getElementTypeNameForTypeDeclaration(String typeDeclaration) {
		typeDeclaration = StringTools.removeAllWhitespace(typeDeclaration);
		return getElementTypeNameForTypeDeclaration_(typeDeclaration, getArrayDepthForTypeDeclaration_(typeDeclaration));
	}

	/**
	 * Return the element type name for the specified "type declaration"; e.g.<ul>
	 * <li><code>"int[]"</code> returns <code>"int"</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>"java.lang.String"</code>
	 * </ul>
	 * Useful for clients that have already queried the type declaration's array depth.
	 */
	public static String getElementTypeNameForTypeDeclaration(String typeDeclaration, int arrayDepth) {
		return getElementTypeNameForTypeDeclaration_(StringTools.removeAllWhitespace(typeDeclaration), arrayDepth);
	}

	/**
	 * pre-condition: no whitespace in the type declaration.
	 */
	private static String getElementTypeNameForTypeDeclaration_(String typeDeclaration, int arrayDepth) {
		return typeDeclaration.substring(0, typeDeclaration.length() - (arrayDepth * 2));
	}

	/**
	 * Return the class for the specified "type declaration".
	 */
	public static Class<?> getClassForTypeDeclaration(String elementTypeName, int arrayDepth) {
		return getClassForTypeDeclaration(elementTypeName, arrayDepth, null);
	}

	/**
	 * Return the class for the specified "type declaration".
	 */
	public static Class<?> getClassForTypeDeclaration_(String elementTypeName, int arrayDepth)
		throws ClassNotFoundException
	{
		return getClassForTypeDeclaration_(elementTypeName, arrayDepth, null);
	}

	/**
	 * Return the class for the specified "type declaration",
	 * using the specified class loader.
	 */
	public static Class<?> getClassForTypeDeclaration(String elementTypeName, int arrayDepth, ClassLoader classLoader) {
		try {
			return getClassForTypeDeclaration_(elementTypeName, arrayDepth, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Return the class for the specified "type declaration",
	 * using the specified class loader.
	 */
	// see the "Evaluation" of JDK bug 6446627 for a discussion of loading classes
	public static Class<?> getClassForTypeDeclaration_(String elementTypeName, int arrayDepth, ClassLoader classLoader)
		throws ClassNotFoundException
	{
		// primitives cannot be loaded via Class.forName(),
		// so check for a primitive class name first
		Primitive pcc = null;
		if (elementTypeName.length() <= MAX_PRIMITIVE_CLASS_NAME_LENGTH) {  // performance tweak
			for (Primitive primitive : PRIMITIVES) {
				if (primitive.javaClass.getName().equals(elementTypeName)) {
					pcc = primitive;
					break;
				}
			}
		}

		// non-array
		if (arrayDepth == 0) {
			return (pcc == null) ? Class.forName(elementTypeName, false, classLoader) : pcc.javaClass;
		}

		// array
		StringBuilder sb = new StringBuilder(100);
		for (int i = arrayDepth; i-- > 0; ) {
			sb.append('[');
		}
		if (pcc == null) {
			ClassName.append(elementTypeName, sb);
		} else {
			sb.append(pcc.code);
		}
		return Class.forName(sb.toString(), false, classLoader);
	}

	/**
	 * Return the class name for the specified "type declaration"; e.g.<ul>
	 * <li><code>"int"</code> returns <code>"int"</code>
	 * <li><code>"int[]"</code> returns <code>"[I"</code>
	 * <li><code>"java.lang.String"</code> returns <code>"java.lang.String"</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>"[[[Ljava.lang.String;"</code>
	 * </ul>
	 * @see java.lang.Class#getName()
	 */
	public static String getClassNameForTypeDeclaration(String typeDeclaration) {
		TypeDeclaration td = buildTypeDeclaration(typeDeclaration);
		return getClassNameForTypeDeclaration(td.elementTypeName, td.arrayDepth);
	}

	/**
	 * Return the class name for the specified "type declaration".
	 * @see java.lang.Class#getName()
	 */
	public static String getClassNameForTypeDeclaration(String elementTypeName, int arrayDepth) {
		// non-array
		if (arrayDepth == 0) {
			return elementTypeName;
		}

		if (elementTypeName.equals(ClassName.VOID_CLASS_NAME)) {
			throw new IllegalArgumentException('\'' + ClassName.VOID_CLASS_NAME + "' must have an array depth of zero: " + arrayDepth + '.'); //$NON-NLS-1$
		}
		// array
		StringBuilder sb = new StringBuilder(100);
		for (int i = arrayDepth; i-- > 0; ) {
			sb.append('[');
		}

		// look for a primitive first
		Primitive pcc = null;
		if (elementTypeName.length() <= MAX_PRIMITIVE_CLASS_NAME_LENGTH) {  // performance tweak
			for (Primitive primitive : PRIMITIVES) {
				if (primitive.javaClass.getName().equals(elementTypeName)) {
					pcc = primitive;
					break;
				}
			}
		}

		if (pcc == null) {
			ClassName.append(elementTypeName, sb);
		} else {
			sb.append(pcc.code);
		}

		return sb.toString();
	}


	// ********** exception messages **********

	/**
	 * Return a string representation of the specified constructor.
	 */
	private static String buildFullyQualifiedConstructorSignature(Class<?> javaClass, Class<?>[] parameterTypes) {
		return buildFullyQualifiedMethodSignature(javaClass, null, parameterTypes);
	}

	/**
	 * Return a string representation of the specified field.
	 */
	private static String buildFullyQualifiedFieldName(Class<?> javaClass, String fieldName) {
		StringBuilder sb = new StringBuilder(200);
		sb.append(javaClass.getName());
		sb.append('.');
		sb.append(fieldName);
		return sb.toString();
	}

	/**
	 * Return a string representation of the specified field.
	 */
	private static String buildFullyQualifiedFieldName(Object object, String fieldName) {
		return buildFullyQualifiedFieldName(object.getClass(), fieldName);
	}

	/**
	 * Return a string representation of the specified method.
	 */
	private static String buildFullyQualifiedMethodSignature(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
		StringBuilder sb = new StringBuilder(200);
		sb.append(javaClass.getName());
		// this check allows us to use this code for constructors, where the methodName is null
		if (methodName != null) {
			sb.append('.');
			sb.append(methodName);
		}
		sb.append('(');
		int max = parameterTypes.length - 1;
		if (max != -1) {
			// stop one short of the end of the array
			for (int i = 0; i < max; i++) {
				sb.append(parameterTypes[i].getName());
				sb.append(", "); //$NON-NLS-1$
			}
			sb.append(parameterTypes[max].getName());
		}
		sb.append(')');
		return sb.toString();
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
			int len = primitive.javaClass.getName().length();
			if (len > max) {
				max = len;
			}
		}
		return max;
	}

	private static int calculateMaxPrimitiveWrapperClassNameLength() {
		int max = -1;
		for (Primitive primitive : PRIMITIVES) {
			int len = primitive.wrapperClass.getName().length();
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
		return new ArrayIterable<Primitive>(array);
	}


	// ********** member classes **********

	static class Primitive {
		final char code;
		final Class<?> javaClass;
		final Class<?> wrapperClass;
		private static final String WRAPPER_CLASS_TYPE_FIELD_NAME = "TYPE"; //$NON-NLS-1$
		// e.g. java.lang.Boolean.TYPE => boolean.class
		Primitive(char code, Class<?> wrapperClass) {
			this.code = code;
			this.wrapperClass = wrapperClass;
			this.javaClass = (Class<?>) getStaticFieldValue(wrapperClass, WRAPPER_CLASS_TYPE_FIELD_NAME);
		}
	}

	private static class TypeDeclaration {
		final String elementTypeName;
		final int arrayDepth;
		TypeDeclaration(String elementTypeName, int arrayDepth) {
			this.elementTypeName = elementTypeName;
			this.arrayDepth = arrayDepth;
		}
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ReflectionTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
