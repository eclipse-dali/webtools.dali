/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Stack;

/**
 * Convenience methods related to the java.lang.reflect package.
 * These methods provide shortcuts for manipulating objects via
 * reflection; particularly when dealing with fields and/or methods that
 * are not publicly accessible or are inherited.
 * 
 * In most cases, all the exceptions are handled and
 * wrapped in java.lang.RuntimeExceptions; so these methods should
 * be used when you are confident that you will not having any problems
 * using reflection.
 * 
 * There are also a number of methods whose names
 * begin with "attempt". These methods will throw a NoSuchMethodException
 * or NoSuchFieldException when appropriate, allowing you to probe
 * for methods that should be present but might not.
 */
public final class ClassTools {

	public static final Class<?>[] ZERO_PARAMETER_TYPES = new Class[0];
	public static final Object[] ZERO_PARAMETERS = new Object[0];
	private static final String CR = StringTools.CR;

	public static final char NESTED_CLASS_NAME_SEPARATOR = '$';

	public static final char ARRAY_INDICATOR = '[';
	public static final char TYPE_DECLARATION_ARRAY_OPEN = '[';
	public static final char TYPE_DECLARATION_ARRAY_CLOSE = ']';

	public static final char REFERENCE_CLASS_CODE = 'L';
	public static final char REFERENCE_CLASS_NAME_DELIMITER = ';';
	
	private static Primitive[] PRIMITIVES;  // pseudo-'final' - lazy-initialized
	public static final char BYTE_CODE = 'B';
	public static final char CHAR_CODE = 'C';
	public static final char DOUBLE_CODE = 'D';
	public static final char FLOAT_CODE = 'F';
	public static final char INT_CODE = 'I';
	public static final char LONG_CODE = 'J';
	public static final char SHORT_CODE = 'S';
	public static final char BOOLEAN_CODE = 'Z';
	public static final char VOID_CODE = 'V';
	private static int MAX_PRIMITIVE_CLASS_NAME_LENGTH = -1;  // pseudo-'final' - lazy-initialized
	private static int MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH = -1;  // pseudo-'final' - lazy-initialized

	public static final String VOID_CLASS_NAME = void.class.getName();
	public static final String VOID_WRAPPER_CLASS_NAME = java.lang.Void.class.getName();


	/**
	 * Return all the fields for the
	 * specified class, including inherited fields.
	 * Class#allFields()
	 */
	@Deprecated
	public static Field[] allFields(Class<?> javaClass) {
		Stack<Field> stack = new Stack<Field>();
		for (Class<?> tempClass = javaClass; tempClass != null; tempClass = tempClass.getSuperclass()) {
			pushDeclaredFields(tempClass, stack);
		}
		Collections.reverse(stack);
		return stack.toArray(new Field[stack.size()]);
	}
	
	/**
	 * Return all the methods for the
	 * specified class, including inherited methods.
	 * Class#allMethods()
	 */
	public static Method[] allMethods(Class<?> javaClass) {
		Stack<Method> stack = new Stack<Method>();
		for (Class<?> tempClass = javaClass; tempClass != null; tempClass = tempClass.getSuperclass()) {
			pushDeclaredMethods(tempClass, stack);
		}
		Collections.reverse(stack);
		return stack.toArray(new Method[stack.size()]);
	}
	
	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * Throw an exception if the default constructor is not defined.
	 * Class#newInstance() throws NoSuchMethodException
	 */
	@Deprecated
	public static <T> T attemptNewInstance(Class<T> javaClass) throws NoSuchMethodException {
		return attemptNewInstance(javaClass, ZERO_PARAMETER_TYPES, ZERO_PARAMETERS);
	}
	
	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and parameters.
	 * Throw an exception if the constructor is not defined.
	 * Class#newInstance(Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException
	 */
	public static <T> T attemptNewInstance(Class<T> javaClass, Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException {
		try {
			return constructor(javaClass, parameterTypes).newInstance(parameters);
		} catch (InstantiationException ie) {
			throw new RuntimeException(ie + CR + fullyQualifiedConstructorSignature(javaClass, parameterTypes), ie);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae + CR + fullyQualifiedConstructorSignature(javaClass, parameterTypes), iae);
		} catch (InvocationTargetException ite) {
			throw new RuntimeException(fullyQualifiedConstructorSignature(javaClass, parameterTypes) + CR + ite.getTargetException(), ite);
		}
	}
	
	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and parameter.
	 * Throw an exception if the constructor is not defined.
	 * Class#newInstance(Class<?> parameterType, Object parameter) throws NoSuchMethodException
	 */
	public static <T> T attemptNewInstance(Class<T> javaClass, Class<?> parameterType, Object parameter) throws NoSuchMethodException {
		return attemptNewInstance(javaClass, new Class[] {parameterType}, new Object[] {parameter});
	}
	
	/**
	 * Attempt to get a field value, given the containing object and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * Throw an exception if the field is not defined.
	 * Object#getFieldValue(String fieldName) throws NoSuchFieldException
	 */
	public static Object attemptToGetFieldValue(Object object, String fieldName) throws NoSuchFieldException {
		try {
			return field(object, fieldName).get(object);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae + CR + fullyQualifiedFieldName(object, fieldName), iae);
		}
	}
	
	/**
	 * Attempt to get a static field value, given the containing object and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * Throw an exception if the field is not defined.
	 * Class#getStaticFieldValue(String fieldName) throws NoSuchFieldException
	 */
	public static Object attemptToGetStaticFieldValue(Class<?> javaClass, String fieldName) throws NoSuchFieldException {
		try {
			return field(javaClass, fieldName).get(null);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae + CR + fullyQualifiedFieldName(javaClass, fieldName), iae);
		}
	}
	
	/**
	 * Convenience method.
	 * Attempt to execute a zero-argument method,
	 * given the receiver and method name.
	 * Return its result.
	 * Throw an exception if the method is not found.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName) throws NoSuchMethodException
	 */
	public static Object attemptToExecuteMethod(Object receiver, String methodName) throws NoSuchMethodException {
		return attemptToExecuteMethod(receiver, methodName, ZERO_PARAMETER_TYPES, ZERO_PARAMETERS);
	}
	
	/**
	 * Convenience method.
	 * Attempt to execute a method, given the receiver,
	 * method name, parameter type, and parameter.
	 * Return its result.
	 * Throw an exception if the method is not found.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName, Class<?> parameterType, Object parameter) throws NoSuchMethodException
	 */
	public static Object attemptToExecuteMethod(Object receiver, String methodName, Class<?> parameterType, Object parameter) throws NoSuchMethodException {
		return attemptToExecuteMethod(receiver, methodName, new Class[] {parameterType}, new Object[] {parameter});
	}
	
	/**
	 * Attempt to execute a method, given the receiver,
	 * method name, parameter types, and parameters.
	 * Return its result.
	 * Throw an exception if the method is not found.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName, Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException
	 */
	public static Object attemptToExecuteMethod(Object receiver, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException {
		return executeMethod(method(receiver, methodName, parameterTypes), receiver, parameters);
	}
	
	/**
	 * Attempt to execute a method, given the receiver,
	 * method name, parameter types, and parameters.
	 * Return its result.
	 * Throw an exception if the method is not found.
	 * If the executed method throws an exception, rethrow that exception.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName, Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException
	 */
	public static Object attemptToExecuteMethodWithException(Object receiver, String methodName, Class<?>[] parameterTypes, Object[] parameters) 
		throws Throwable, NoSuchMethodException 
	{
		return executeMethodWithException(method(receiver, methodName, parameterTypes), receiver, parameters);
	}
	
	/**
	 * Convenience method.
	 * Attempt to execute a zero-argument static method,
	 * given the class and method name.
	 * Return its result.
	 * Throw an exception if the method is not found.
	 * Useful for invoking private, package, or protected methods.
	 * Class#executeStaticMethod(String methodName) throws NoSuchMethodException
	 */
	public static Object attemptToExecuteStaticMethod(Class<?> javaClass, String methodName) throws NoSuchMethodException {
		return attemptToExecuteStaticMethod(javaClass, methodName, ZERO_PARAMETER_TYPES, ZERO_PARAMETERS);
	}
	
	/**
	 * Attempt to execute a static method, given the class,
	 * method name, parameter types, and parameters.
	 * Return its result.
	 * Throw an exception if the method is not found.
	 * Useful for invoking private, package, or protected methods.
	 * Class#executeStaticMethod(String methodName, Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException
	 */
	public static Object attemptToExecuteStaticMethod(Class<?> javaClass, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException {
		return executeStaticMethod(staticMethod(javaClass, methodName, parameterTypes), parameters);
	}
	
	/**
	 * Convenience method.
	 * Attempt to execute a static method, given the class,
	 * method name, parameter type, and parameter.
	 * Return its result.
	 * Throw an exception if the method is not found.
	 * Useful for invoking private, package, or protected methods.
	 * Class#executeStaticMethod(String methodName, Class<?> parameterType, Object parameter) throws NoSuchMethodException
	 */
	public static Object attemptToExecuteStaticMethod(Class<?> javaClass, String methodName, Class<?> parameterType, Object parameter) throws NoSuchMethodException {
		return attemptToExecuteStaticMethod(javaClass, methodName, new Class[] {parameterType}, new Object[] {parameter});
	}
	
	/**
	 * Attempt to set a field value, given the
	 * containing object, field name, and new field value.
	 * Useful for accessing private, package, or protected fields.
	 * Throw an exception if the field is not defined.
	 * Object#setFieldValue(String fieldName, Object fieldValue) throws NoSuchFieldException
	 */
	public static void attemptToSetFieldValue(Object object, String fieldName, Object fieldValue) throws NoSuchFieldException {
		try {
			field(object, fieldName).set(object, fieldValue);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae + CR + fullyQualifiedFieldName(object, fieldName), iae);
		}
	}
	
	/**
	 * Attempt to set a static field value, given the
	 * containing class, field name, and new field value.
	 * Useful for accessing private, package, or protected fields.
	 * Throw an exception if the field is not defined.
	 * Class#setStaticFieldValue(String fieldName, Object fieldValue) throws NoSuchFieldException
	 */
	public static void attemptToSetStaticFieldValue(Class<?> javaClass, String fieldName, Object fieldValue) throws NoSuchFieldException {
		try {
			field(javaClass, fieldName).set(null, fieldValue);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae + CR + fullyQualifiedFieldName(javaClass, fieldName), iae);
		}
	}
	
	/**
	 * Convenience method.
	 * Return the default (zero-argument) constructor
	 * for the specified class.
	 * Set accessible to true, so we can access
	 * private/package/protected constructors.
	 * Class#constructor() throws NoSuchMethodException
	 */
	public static <T> Constructor<T> constructor(Class<T> javaClass) throws NoSuchMethodException {
		return constructor(javaClass, ZERO_PARAMETER_TYPES);
	}
	
	/**
	 * Return the constructor for the specified class
	 * and formal parameter types.
	 * Set accessible to true, so we can access
	 * private/package/protected constructors.
	 * Class#constructor(Class<?>[] parameterTypes) throws NoSuchMethodException
	 */
	public static <T> Constructor<T> constructor(Class<T> javaClass, Class<?>[] parameterTypes) throws NoSuchMethodException {
		Constructor<T> constructor = javaClass.getDeclaredConstructor(parameterTypes);
		constructor.setAccessible(true);
		return constructor;
	}
	
	/**
	 * Convenience method.
	 * Return the constructor for the specified class
	 * and formal parameter type.
	 * Set accessible to true, so we can access
	 * private/package/protected constructors.
	 * Class#constructor(Class<?> parameterType) throws NoSuchMethodException
	 */
	public static <T> Constructor<T> constructor(Class<T> javaClass, Class<?> parameterType) throws NoSuchMethodException {
		return constructor(javaClass, new Class[] {parameterType});
	}
	
	/**
	 * Return the declared fields for the specified class.
	 * Set accessible to true, so we can access
	 * private/package/protected fields.
	 * Class#accessibleDeclaredFields()
	 */
	public static Field[] declaredFields(Class<?> javaClass) {
		Field[] fields = javaClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
		return fields;
	}
	
	/**
	 * Return the declared methods for the
	 * specified class.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 * Class#accessibleDeclaredMethods()
	 */
	public static Method[] declaredMethods(Class<?> javaClass) {
		Method[] methods = javaClass.getDeclaredMethods();
		for (Method method : methods) {
			method.setAccessible(true);
		}
		return methods;
	}
	
	/**
	 * Return the default (zero-argument) constructor
	 * for the specified class.
	 * Set accessible to true, so we can access
	 * private/package/protected constructors.
	 * Class#defaultConstructor()
	 */
	public static <T> Constructor<T> defaultConstructor(Class<T> javaClass) throws NoSuchMethodException {
		return constructor(javaClass);
	}
	
	/**
	 * Return a field for the specified class and field name.
	 * If the class does not directly
	 * define the field, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected fields.
	 */
	public static Field field(Class<?> javaClass, String fieldName) throws NoSuchFieldException {
		Field field = null;
		try {
			field = javaClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException ex) {
			Class<?> superclass = javaClass.getSuperclass();
			if (superclass == null) {
				throw ex;
			}
			// recurse
			return field(superclass, fieldName);
		}
		field.setAccessible(true);
		return field;
	}
	
	/**
	 * Convenience method.
	 * Return a field for the specified object and field name.
	 * If the object's class does not directly
	 * define the field, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected fields.
	 */
	public static Field field(Object object, String fieldName) throws NoSuchFieldException {
		return field(object.getClass(), fieldName);
	}
	
	/*
	 * Return a string representation of the specified constructor.
	 */
	private static String fullyQualifiedConstructorSignature(Class<?> javaClass, Class<?>[] parameterTypes) {
		return fullyQualifiedMethodSignature(javaClass, null, parameterTypes);
	}
	
	/*
	 * Return a string representation of the specified field.
	 */
	private static String fullyQualifiedFieldName(Class<?> javaClass, String fieldName) {
		StringBuilder sb = new StringBuilder(200);
		sb.append(javaClass.getName());
		sb.append('.');
		sb.append(fieldName);
		return sb.toString();
	}
	
	/*
	 * Return a string representation of the specified field.
	 */
	private static String fullyQualifiedFieldName(Object object, String fieldName) {
		return fullyQualifiedFieldName(object.getClass(), fieldName);
	}
	
	/*
	 * Return a string representation of the specified method.
	 */
	private static String fullyQualifiedMethodSignature(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) {
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
				sb.append(", ");
			}
			sb.append(parameterTypes[max].getName());
		}
		sb.append(')');
		return sb.toString();
	}
	
	/*
	 * Return a string representation of the specified method.
	 */
	private static String fullyQualifiedMethodSignature(Object receiver, String methodName, Class<?>[] parameterTypes) {
		return fullyQualifiedMethodSignature(receiver.getClass(), methodName, parameterTypes);
	}
	
	/**
	 * Get a field value, given the containing object and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * Object#getFieldValue(String fieldName)
	 */
	public static Object fieldValue(Object object, String fieldName) {
		try {
			return attemptToGetFieldValue(object, fieldName);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe + CR + fullyQualifiedFieldName(object, fieldName), nsfe);
		}
	}
	
	/**
	 * Get a static field value, given the containing class and field name.
	 * Return its result.
	 * Useful for accessing private, package, or protected fields.
	 * Class#getStaticFieldValue(String fieldName)
	 */
	public static Object staticFieldValue(Class<?> javaClass, String fieldName) {
		try {
			return attemptToGetStaticFieldValue(javaClass, fieldName);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe + CR + fullyQualifiedFieldName(javaClass, fieldName), nsfe);
		}
	}
	
	/**
	 * Convenience method.
	 * Execute a zero-argument method, given the receiver and method name.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName)
	 */
	public static Object executeMethod(Object receiver, String methodName) {
		return executeMethod(receiver, methodName, ZERO_PARAMETER_TYPES, ZERO_PARAMETERS);
	}
	
	/**
	 * Execute a method, given the receiver,
	 * method name, parameter types, and parameters.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName, Class<?>[] parameterTypes, Object[] parameters)
	 */
	public static Object executeMethod(Object receiver, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
		try {
			return attemptToExecuteMethod(receiver, methodName, parameterTypes, parameters);
		} catch (NoSuchMethodException nsme) {
			throw new RuntimeException(nsme + CR + fullyQualifiedMethodSignature(receiver, methodName, parameterTypes), nsme);
		}
	}
	
	/**
	 * Convenience method.
	 * Execute a one-argument method, given the receiver,
	 * method name, parameter type, and parameter.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName, Class<?> parameterType, Object parameter)
	 */
	public static Object executeMethod(Object receiver, String methodName, Class<?> parameterType, Object parameter) {
		return executeMethod(receiver, methodName, new Class[] {parameterType}, new Object[] {parameter});
	}
	
	/**
	 * Convenience method.
	 * Execute a zero-argument method, given the receiver and method name.
	 * Return its result.
	 * If the method throws an exception, rethrow that exception.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName)
	 */
	public static Object executeMethodWithException(Object receiver, String methodName) 
		throws Throwable
	{
		return executeMethodWithException(receiver, methodName, ZERO_PARAMETER_TYPES, ZERO_PARAMETERS);
	}
	
	/**
	 * Convenience method.
	 * Execute a one-argument method, given the receiver,
	 * method name, parameter type, and parameter.
	 * Return its result.
	 * If the method throws an exception, rethrow that exception.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName, Class<?> parameterType, Object parameter)
	 */
	public static Object executeMethodWithException(Object receiver, String methodName, Class<?> parameterType, Object parameter) 
		throws Throwable
	{
		return executeMethodWithException(receiver, methodName, new Class[] {parameterType}, new Object[] {parameter});
	}
	
	/**
	 * Execute a method, given the receiver,
	 * method name, parameter types, and parameters.
	 * Return its result.
	 * If the method throws an exception, rethrow that exception.
	 * Useful for invoking private, package, or protected methods.
	 * Object#execute(String methodName, Class<?>[] parameterTypes, Object[] parameters)
	 */
	public static Object executeMethodWithException(Object receiver, String methodName, Class<?>[] parameterTypes, Object[] parameters) 
		throws Throwable
	{
		try {
			return attemptToExecuteMethodWithException(receiver, methodName, parameterTypes, parameters);
		} catch (NoSuchMethodException nsme) {
			throw new RuntimeException(nsme + CR + fullyQualifiedMethodSignature(receiver, methodName, parameterTypes), nsme);
		}
	}
	
	/**
	 * Execute the specified method with the specified parameters.
	 * Return its result.
	 * Convert exceptions to RuntimeExceptions.
	 */
	public static Object executeMethod(Method method, Object receiver, Object[] parameters) {
		try {
			return method.invoke(receiver, parameters);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae + CR + method, iae);
		} catch (InvocationTargetException ite) {
			throw new RuntimeException(method + CR + ite.getTargetException(), ite);
		}
	}
	
	/**
	 * Execute the specified method with the specified parameters.
	 * Return its result.
	 * If the method throws an exception, rethrow that exception.
	 * Convert all other exceptions to RuntimeExceptions.
	 */
	public static Object executeMethodWithException(Method method, Object receiver, Object[] parameters)
		throws Throwable
	{
		try {
			return method.invoke(receiver, parameters);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae + CR + method, iae);
		} catch (InvocationTargetException ite) {
			Throwable cause = ite.getCause();
			if (cause == null) {
				throw new RuntimeException(method.toString(), ite);
			}
			throw cause;
		}
	}
	
	/**
	 * Convenience method.
	 * Execute a zero-argument static method,
	 * given the class and method name.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * Class#executeStaticMethod(String methodName)
	 */
	public static Object executeStaticMethod(Class<?> javaClass, String methodName) {
		return executeStaticMethod(javaClass, methodName, ZERO_PARAMETER_TYPES, ZERO_PARAMETERS);
	}
	
	/**
	 * Execute a static method, given the class,
	 * method name, parameter types, and parameters.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * Class#executeStaticMethod(String methodName, Class<?>[] parameterTypes, Object[] parameters)
	 */
	public static Object executeStaticMethod(Class<?> javaClass, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
		try {
			return attemptToExecuteStaticMethod(javaClass, methodName, parameterTypes, parameters);
		} catch (NoSuchMethodException nsme) {
			throw new RuntimeException(nsme + CR + fullyQualifiedMethodSignature(javaClass, methodName, parameterTypes), nsme);
		}
	}
	
	/**
	 * Convenience method.
	 * Execute a static method, given the class,
	 * method name, parameter type, and parameter.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 * Class#executeStaticMethod(String methodName, Class<?> parameterType, Object parameter)
	 */
	public static Object executeStaticMethod(Class<?> javaClass, String methodName, Class<?> parameterType, Object parameter) {
		return executeStaticMethod(javaClass, methodName, new Class[] {parameterType}, new Object[] {parameter});
	}
	
	/**
	 * Execute the specified static method with the specified parameters.
	 * Return its result.
	 * Convert exceptions to RuntimeExceptions.
	 */
	public static Object executeStaticMethod(Method method, Object[] parameters) {
		return executeMethod(method, null, parameters);
	}
	
	/**
	 * Convenience method.
	 * Return a zero-argument method for the specified class
	 * and method name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method method(Class<?> javaClass, String methodName) throws NoSuchMethodException {
		return method(javaClass, methodName, ZERO_PARAMETER_TYPES);
	}
	
	/**
	 * Return a method for the specified class, method name,
	 * and formal parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method method(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
		Method method = null;
		try {
			method = javaClass.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException ex) {
			Class<?> superclass = javaClass.getSuperclass();
			if (superclass == null) {
				throw ex;
			}
			// recurse
			return method(superclass, methodName, parameterTypes);
		}
		method.setAccessible(true);
		return method;
	}
	
	/**
	 * Convenience method.
	 * Return a method for the specified class, method name,
	 * and formal parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method method(Class<?> javaClass, String methodName, Class<?> parameterType) throws NoSuchMethodException {
		return method(javaClass, methodName, new Class[] {parameterType});
	}
	
	/**
	 * Convenience method.
	 * Return a zero-argument method for the specified object
	 * and method name. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method method(Object object, String methodName) throws NoSuchMethodException {
		return method(object.getClass(), methodName);
	}
	
	/**
	 * Convenience method.
	 * Return a method for the specified object, method name,
	 * and formal parameter types. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method method(Object object, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
		return method(object.getClass(), methodName, parameterTypes);
	}
	
	/**
	 * Convenience method.
	 * Return a method for the specified object, method name,
	 * and formal parameter type. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method method(Object object, String methodName, Class<?> parameterType) throws NoSuchMethodException {
		return method(object.getClass(), methodName, parameterType);
	}
	
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
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * Class#newInstance()
	 */
	public static <T> T newInstance(Class<T> javaClass) {
		return newInstance(javaClass, ZERO_PARAMETER_TYPES, ZERO_PARAMETERS);
	}
	
	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * Class#newInstance()
	 */
	public static Object newInstance(String className) throws ClassNotFoundException {
		return newInstance(className, null);
	}
	
	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * Class#newInstance()
	 */
	public static Object newInstance(String className, ClassLoader classLoader) throws ClassNotFoundException {
		return newInstance(Class.forName(className, true, classLoader));
	}
	
	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and parameters.
	 * Class#newInstance(Class<?>[] parameterTypes, Object[] parameters)
	 */
	public static <T> T newInstance(Class<T> javaClass, Class<?>[] parameterTypes, Object[] parameters) {
		try {
			return attemptNewInstance(javaClass, parameterTypes, parameters);
		} catch (NoSuchMethodException nsme) {
			throw new RuntimeException(nsme + CR + fullyQualifiedConstructorSignature(javaClass, parameterTypes), nsme);
		}
	}
	
	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and parameters.
	 * Class#newInstance(Class<?>[] parameterTypes, Object[] parameters)
	 */
	public static Object newInstance(String className, Class<?>[] parameterTypes, Object[] parameters) throws ClassNotFoundException {
		return newInstance(className, parameterTypes, parameters, null);
	}
	
	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and parameters.
	 * Class#newInstance(Class<?>[] parameterTypes, Object[] parameters)
	 */
	public static Object newInstance(String className, Class<?>[] parameterTypes, Object[] parameters, ClassLoader classLoader) throws ClassNotFoundException {
		return newInstance(Class.forName(className, true, classLoader), parameterTypes, parameters);
	}
	
	/**
	 * Convenience method.
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and parameter.
	 * Class#newInstance(Class<?> parameterType, Object parameter)
	 */
	public static <T> T newInstance(Class<T> javaClass, Class<?> parameterType, Object parameter) {
		return newInstance(javaClass, new Class[] {parameterType}, new Object[] {parameter});
	}
	
	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and parameter.
	 * Class#newInstance(Class<?> parameterType, Object parameter)
	 */
	public static Object newInstance(String className, Class<?> parameterType, Object parameter) throws ClassNotFoundException {
		return newInstance(className, parameterType, parameter, null);
	}
	
	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and parameter.
	 * Class#newInstance(Class<?> parameterType, Object parameter)
	 */
	public static Object newInstance(String className, Class<?> parameterType, Object parameter, ClassLoader classLoader) throws ClassNotFoundException {
		return newInstance(Class.forName(className, false, classLoader), parameterType, parameter);
	}
	
	/*
	 * Push the declared fields for the specified class
	 * onto the top of the stack.
	 */
	private static void pushDeclaredFields(Class<?> javaClass, Stack<Field> stack) {
		Field[] fields = declaredFields(javaClass);
		for (int i = fields.length; i-- > 0; ) {
			stack.push(fields[i]);
		}
	}
	
	/*
	 * Push the declared methods for the specified class
	 * onto the top of the stack.
	 */
	private static void pushDeclaredMethods(Class<?> javaClass, Stack<Method> stack) {
		Method[] methods = declaredMethods(javaClass);
		for (int i = methods.length; i-- > 0; ) {
			stack.push(methods[i]);
		}
	}
	
	/**
	 * Set a field value, given the containing object, field name, and new field value.
	 * Useful for accessing private, package, or protected fields.
	 * Object#setFieldValue(String fieldName, Object fieldValue)
	 */
	public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
		try {
			attemptToSetFieldValue(object, fieldName, fieldValue);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe + CR + fullyQualifiedFieldName(object, fieldName), nsfe);
		}
	}
	
	/**
	 * Set a static field value, given the containing class, field name, and new field value.
	 * Useful for accessing private, package, or protected fields.
	 * Class#setStaticFieldValue(String fieldName, Object fieldValue)
	 */
	public static void setStaticFieldValue(Class<?> javaClass, String fieldName, Object fieldValue) {
		try {
			attemptToSetStaticFieldValue(javaClass, fieldName, fieldValue);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe + CR + fullyQualifiedFieldName(javaClass, fieldName), nsfe);
		}
	}
	
	/**
	 * Return the short name of the object's class.
	 * Class#getShortName()
	 */
	public static String shortClassNameForObject(Object object) {
		return shortNameFor(object.getClass());
	}
	
	/**
	 * Return the short name of the class (e.g. "Object").
	 * Class#getShortName()
	 */
	public static String shortNameForClassNamed(String className) {
		return className.substring(className.lastIndexOf('.') + 1);
	}
	
	/**
	 * Return the short name of the class (e.g. "Object").
	 * Class#getShortName()
	 */
	public static String shortNameFor(Class<?> javaClass) {
		return shortNameForClassNamed(javaClass.getName());
	}
	
	/**
	 * Return the nested name of the object's class.
	 * Class#getNestedName()
	 */
	public static String nestedClassNameForObject(Object object) {
		return nestedNameFor(object.getClass());
	}
	
	/**
	 * Return the nested name of the class (e.g. "Entry").
	 * Class#getNestedName()
	 */
	public static String nestedNameForClassNamed(String className) {
		return className.substring(className.lastIndexOf(NESTED_CLASS_NAME_SEPARATOR) + 1);
	}
	
	/**
	 * Return the nested name of the class (e.g. "Entry").
	 * Class#getNestedName()
	 */
	public static String nestedNameFor(Class<?> javaClass) {
		return nestedNameForClassNamed(javaClass.getName());
	}
	
	/**
	 * Return the "toString()" name of the object's class.
	 */
	public static String toStringClassNameForObject(Object object) {
		return toStringNameFor(object.getClass());
	}
	
	/**
	 * Return the "toString()" name of the class.
	 * "Member" classes will return only the final name:
	 *     "com.foo.bar.TopLevelClass$MemberClass$NestedMemberClass"
	 *         => "NestedMemberClass"
	 * "Local" and "anonymous" classes will still return the embedded '$'s:
	 *     "com.foo.bar.TopLevelClass$1LocalClass"
	 *         => "TopLevelClass$1LocalClass"
	 *     "com.foo.bar.TopLevelClass$1"
	 *         => "TopLevelClass$1"
	 */
	public static String toStringNameForClassNamed(String className) {
		return classNamedIsMember(className) ?
			className.substring(className.lastIndexOf(NESTED_CLASS_NAME_SEPARATOR) + 1)
		:
			className.substring(className.lastIndexOf('.') + 1);
	}
	
	/**
	 * Return the "toString()" name of the class.
	 */
	public static String toStringNameFor(Class<?> javaClass) {
		return toStringNameForClassNamed(javaClass.getName());
	}
	
	/**
	 * Return the package name of the class (e.g. "java.lang").
	 * Class#getPackageName()
	 */
	public static String packageNameFor(Class<?> javaClass) {
		return packageNameForClassNamed(javaClass.getName());
	}
	
	/**
	 * Return the package name of the class (e.g. "java.lang").
	 * Class#getPackageName()
	 */
	public static String packageNameForClassNamed(String className) {
		int lastPeriod = className.lastIndexOf('.');
		if (lastPeriod == -1) {
			return "";
		}
		return className.substring(0, lastPeriod);
	}
	
	/**
	 * Return the short name of the class,
	 * followed by its package name (e.g. "Object (java.lang)").
	 * Class#getShortNameWithPackage()
	 */
	public static String shortNameWithPackage(Class<?> javaClass) {
		StringBuilder sb = new StringBuilder(200);
		sb.append(shortNameFor(javaClass));
		if ( ! javaClass.isPrimitive()) {
			sb.append(" (");
			sb.append(packageNameFor(javaClass));
			sb.append(')');
		}
		return sb.toString();
	}
	
	/**
	 * Convenience method.
	 * Return a zero-argument, static method for the specified class
	 * and method name. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method staticMethod(Class<?> javaClass, String methodName) throws NoSuchMethodException {
		return staticMethod(javaClass, methodName, ZERO_PARAMETER_TYPES);
	}
	
	/**
	 * Return a static method for the specified class, method name,
	 * and formal parameter types. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method staticMethod(Class<?> javaClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
		Method method = method(javaClass, methodName, parameterTypes);
		if (Modifier.isStatic(method.getModifiers())) {
			return method;
		}
		throw new NoSuchMethodException(fullyQualifiedMethodSignature(javaClass, methodName, parameterTypes));
	}
	
	/**
	 * Convenience method.
	 * Return a static method for the specified class, method name,
	 * and formal parameter type. If the class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Set accessible to true, so we can access
	 * private/package/protected methods.
	 */
	public static Method staticMethod(Class<?> javaClass, String methodName, Class<?> parameterTypes) throws NoSuchMethodException {
		return staticMethod(javaClass, methodName, new Class[] {parameterTypes});
	}

	/**
	 * Return whether the specified class can be "declared" in code;
	 * i.e. it is either a "top-level" class or a "member" class, but it
	 * is not an "array" class. This method rolls together all the checks
	 * from the other methods for a bit of a performance tweak.
	 * Class#isDeclarable()
	 */
	public static boolean classNamedIsDeclarable(String className) {
		if (className.charAt(0) == ARRAY_INDICATOR) {
			return false;		// it is an "array" class
		}
		int index = className.indexOf(NESTED_CLASS_NAME_SEPARATOR);
		if (index == -1) {
			return true;		// it is a "top-level" class
		}
		do {
			// the character immediately after each dollar sign cannot be a digit
			index++;
			if (Character.isDigit(className.charAt(index))) {
				return false;
			}
			index = className.indexOf(NESTED_CLASS_NAME_SEPARATOR, index);
		} while (index != -1);
		return true;
	}
	
	/**
	 * Return whether the specified class is a "top-level" class,
	 * as opposed to a "member", "local", or "anonymous" class,
	 * using the standard jdk naming conventions (i.e. the class
	 * name does NOT contain a '$': "TopLevelClass").
	 * Class#isTopLevel()
	 */
	public static boolean classNamedIsTopLevel(String className) {
		if (classNamedIsArray(className)) {
			return false;
		}
		return className.indexOf(NESTED_CLASS_NAME_SEPARATOR) == -1;
	}

	/**
	 * Return whether the specified class is a "member" class,
	 * as opposed to a "top-level", "local", or "anonymous" class,
	 * using the standard jdk naming conventions (i.e. the class
	 * name contains at least one '$' and all the names between
	 * each '$' are legal class names:
	 * "TopLevelClass$MemberClass$NestedMemberClass").
	 * Class#isMember()
	 */
	public static boolean classNamedIsMember(String className) {
		if (classNamedIsArray(className)) {
			return false;
		}
		int index = className.indexOf(NESTED_CLASS_NAME_SEPARATOR);
		if (index == -1) {
			return false;	// it is a "top-level" class
		}
		do {
			// the character immediately after each dollar sign cannot be a digit
			index++;
			if (Character.isDigit(className.charAt(index))) {
				return false;
			}
			index = className.indexOf(NESTED_CLASS_NAME_SEPARATOR, index);
		} while (index != -1);
		return true;
	}

	/**
	 * Return whether the specified class is a "local" class,
	 * as opposed to a "top-level", "member", or "anonymous" class,
	 * using the standard jdk (or Eclipse) naming conventions.
	 * In the jdk, the class name ends with '$nnnXXX' where the '$' is
	 * followed by a series of numeric digits which are followed by the
	 * local class name: "TopLevelClass$1LocalClass".
	 * In Eclipse, the class name ends with '$nnn$XXX' where the '$' is
	 * followed by a series of numeric digits which are separated from
	 * the local class name by another '$': "TopLevelClass$1$LocalClass".
	 * Class#isLocal()
	 */
	public static boolean classNamedIsLocal(String className) {
		if (classNamedIsArray(className)) {
			return false;
		}
		int dollar = className.indexOf(NESTED_CLASS_NAME_SEPARATOR);
		if (dollar == -1) {
			return false;
		}
		if ( ! Character.isDigit(className.charAt(dollar + 1))) {
			return false;
		}
		int len = className.length();
		for (int i = dollar + 2; i < len; i++) {
			if (Character.isJavaIdentifierStart(className.charAt(i))) {
				return true;
			}
		}
		// all the characters past the $ are digits (anonymous)
		return false;
	}

	/**
	 * Return whether the specified class is an "anonymous" class,
	 * as opposed to a "top-level", "member", or "local" class,
	 * using the standard jdk naming conventions (i.e. the class
	 * name ends with '$nnn' where all the characters past the
	 * last '$' are numeric digits: "TopLevelClass$1").
	 * Class#isAnonymous()
	 */
	public static boolean classNamedIsAnonymous(String className) {
		if (classNamedIsArray(className)) {
			return false;
		}
		int dollar = className.indexOf(NESTED_CLASS_NAME_SEPARATOR);
		if (dollar == -1) {
			return false;
		}
		int start = dollar + 1;
		for (int i = className.length(); i-- > start; ) {
			if ( ! Character.isDigit(className.charAt(i))) {
				return false;
			}
		}
		// all the characters past the $ are digits
		return true;
	}

	/**
	 * Return the "array depth" of the specified class.
	 * The depth is the number of dimensions for an array type.
	 * Non-array types have a depth of zero.
	 * Class#getArrayDepth()
	 */
	public static int arrayDepthFor(Class<?> javaClass) {
		int depth = 0;
		while (javaClass.isArray()) {
			depth++;
			javaClass = javaClass.getComponentType();
		}
		return depth;
	}
	
	/**
	 * Return the "array depth" of the specified object.
	 * The depth is the number of dimensions for an array.
	 * Non-arrays have a depth of zero.
	 */
	public static int arrayDepthForObject(Object object) {
		return arrayDepthFor(object.getClass());
	}
	
	/**
	 * Return the "array depth" of the specified class.
	 * The depth is the number of dimensions for an array type.
	 * Non-array types have a depth of zero.
	 * @see java.lang.Class#getName()
	 * Class#getArrayDepth()
	 */
	public static int arrayDepthForClassNamed(String className) {
		int depth = 0;
		while (className.charAt(depth) == ARRAY_INDICATOR) {
			depth++;
		}
		return depth;
	}

	/**
	 * Return whether the specified class is an array type.
	 * @see java.lang.Class#getName()
	 */
	public static boolean classNamedIsArray(String className) {
		return className.charAt(0) == ARRAY_INDICATOR;
	}

	/**
	 * Return the "element type" of the specified class.
	 * The element type is the base type held by an array type.
	 * A non-array type simply returns itself.
	 * Class#getElementType()
	 */
	public static Class<?> elementTypeFor(Class<?> javaClass) {
		while (javaClass.isArray()) {
			javaClass = javaClass.getComponentType();
		}
		return javaClass;
	}

	/**
	 * Return the "element type" of the specified object.
	 * The element type is the base type held by an array.
	 * A non-array simply returns its class.
	 */
	public static Class<?> elementTypeForObject(Object object) {
		return elementTypeFor(object.getClass());
	}

	/**
	 * Return the "element type" of the specified class.
	 * The element type is the base type held by an array type.
	 * Non-array types simply return themselves.
	 * Class#getElementType()
	 */
	public static String elementTypeNameFor(Class<?> javaClass) {
		return elementTypeFor(javaClass).getName();
	}

	/**
	 * Return the "element type" of the specified class.
	 * The element type is the base type held by an array type.
	 * Non-array types simply return themselves.
	 * @see java.lang.Class#getName()
	 * Class#getElementType()
	 */
	public static String elementTypeNameForClassNamed(String className) {
		int depth = arrayDepthForClassNamed(className);
		if (depth == 0) {
			// the name is in the form: "java.lang.Object" or "int"
			return className;
		}
		int last = className.length() - 1;
		if (className.charAt(depth) == REFERENCE_CLASS_CODE) {
			// the name is in the form: "[[[Ljava.lang.Object;"
			return className.substring(depth + 1, last);	// drop the trailing ';'
		}
		// the name is in the form: "[[[I"
		return classNameForCode(className.charAt(last));
	}
	
	/**
	 * Return whether the specified class is a "reference"
	 * class (i.e. neither 'void' nor one of the primitive variable classes,
	 * ['boolean', 'int', 'float', etc.]).
	 * NB: void.class.isPrimitive() == true
	 */
	public static boolean classNamedIsReference(String className) {
		return ! classNamedIsPrimitive(className);
	}

	/**
	 * Return whether the specified class is a primitive
	 * class (i.e. 'void' or one of the primitive variable classes,
	 * ['boolean', 'int', 'float', etc.]).
	 * NB: void.class.isPrimitive() == true
	 */
	public static boolean classNamedIsPrimitive(String className) {
		if (classNamedIsArray(className) || (className.length() > maxPrimitiveClassNameLength())) {
			return false;  // performance tweak
		}
		Primitive[] codes = primitives();
		for (int i = codes.length; i-- > 0; ) {
			if (codes[i].javaClass.getName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified class is a "variable" primitive
	 * class (i.e. 'boolean', 'int', 'float', etc., but not 'void').
	 * NB: void.class.isPrimitive() == true
	 */
	public static boolean classNamedIsVariablePrimitive(String className) {
		return classNamedIsPrimitive(className)
			&& ( ! className.equals(VOID_CLASS_NAME));
	}

	/**
	 * Return whether the specified class is a primitive wrapper
	 * class (i.e. 'java.lang.Void' or one of the primitive variable wrapper classes,
	 * ['java.lang.Boolean', 'java.lang.Integer', 'java.lang.Float', etc.]).
	 * NB: void.class.isPrimitive() == true
	 */
	public static boolean classNamedIsPrimitiveWrapperClass(String className) {
		if (classNamedIsArray(className) || (className.length() > maxPrimitiveWrapperClassNameLength())) {
			return false;  // performance tweak
		}
		Primitive[] codes = primitives();
		for (int i = codes.length; i-- > 0; ) {
			if (codes[i].wrapperClass.getName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified class is a "variable" primitive
	 * class (i.e. 'boolean', 'int', 'float', etc., but not 'void').
	 * NB: void.class.isPrimitive() == true
	 */
	public static boolean classNamedIsVariablePrimitiveWrapperClass(String className) {
		return classNamedIsPrimitiveWrapperClass(className)
			&& ( ! className.equals(VOID_WRAPPER_CLASS_NAME));
	}

	/**
	 * Return whether the specified class is a primitive wrapper
	 * class (i.e. 'java.lang.Void' or one of the primitive variable wrapper classes,
	 * ['java.lang.Boolean', 'java.lang.Integer', 'java.lang.Float', etc.]).
	 * NB: void.class.isPrimitive() == true
	 */
	public static boolean classIsPrimitiveWrapperClass(Class<?> javaClass) {
		if (javaClass.isArray() || (javaClass.getName().length() > maxPrimitiveWrapperClassNameLength())) {
			return false;  // performance tweak
		}
		Primitive[] codes = primitives();
		for (int i = codes.length; i-- > 0; ) {
			if (codes[i].wrapperClass == javaClass) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified class is a "variable" primitive
	 * class (i.e. 'boolean', 'int', 'float', etc., but not 'void').
	 * NB: void.class.isPrimitive() == true
	 */
	public static boolean classIsVariablePrimitiveWrapperClass(Class<?> javaClass) {
		return classIsPrimitiveWrapperClass(javaClass)
			&& (javaClass != java.lang.Void.class);
	}

	/**
	 * Return the class name for the specified class code.
	 * @see java.lang.Class#getName()
	 */
	public static String classNameForCode(char classCode) {
		return classForCode(classCode).getName();
	}
	
	/**
	 * Return the class name for the specified class code.
	 * @see java.lang.Class#getName()
	 */
	public static String classNameForCode(int classCode) {
		return classNameForCode((char) classCode);
	}
	
	/**
	 * Return the class for the specified class code.
	 * @see java.lang.Class#getName()
	 */
	public static Class<?> classForCode(char classCode) {
		Primitive[] codes = primitives();
		for (int i = codes.length; i-- > 0; ) {
			if (codes[i].code == classCode) {
				return codes[i].javaClass;
			}
		}
		throw new IllegalArgumentException(String.valueOf(classCode));
	}

	/**
	 * Return the class for the specified class code.
	 * @see java.lang.Class#getName()
	 */
	public static Class<?> classForCode(int classCode) {
		return classForCode((char) classCode);
	}
	
	/**
	 * Return the class code for the specified class.
	 * @see java.lang.Class#getName()
	 */
	public static char codeForClass(Class<?> javaClass) {
		if (( ! javaClass.isArray()) && (javaClass.getName().length() <= maxPrimitiveClassNameLength())) {
			Primitive[] codes = primitives();
			for (int i = codes.length; i-- > 0; ) {
				if (codes[i].javaClass == javaClass) {
					return codes[i].code;
				}
			}
		}
		throw new IllegalArgumentException(javaClass.getName());		
	}
	
	/**
	 * Return the class code for the specified class.
	 * @see java.lang.Class#getName()
	 */
	public static char codeForClassNamed(String className) {
		if (( ! classNamedIsArray(className)) && (className.length() <= maxPrimitiveClassNameLength())) {
			Primitive[] codes = primitives();
			for (int i = codes.length; i-- > 0; ) {
				if (codes[i].javaClass.getName().equals(className)) {
					return codes[i].code;
				}
			}
		}
		throw new IllegalArgumentException(className);		
	}

	/**
	 * Return the class for the specified "type declaration".
	 */
	public static Class<?> classForTypeDeclaration(String typeDeclaration) throws ClassNotFoundException {
		return classForTypeDeclaration(typeDeclaration, ClassTools.class.getClassLoader());
	}
	
	/**
	 * Return the class for the specified "type declaration",
	 * using the specified class loader.
	 */
	public static Class<?> classForTypeDeclaration(String typeDeclaration, ClassLoader classLoader) throws ClassNotFoundException {
		TypeDeclaration td = typeDeclaration(typeDeclaration);
		return classForTypeDeclaration(td.elementTypeName, td.arrayDepth);
	}

	private static TypeDeclaration typeDeclaration(String typeDeclaration) {
		typeDeclaration = StringTools.removeAllWhitespace(typeDeclaration);
		int arrayDepth = arrayDepthForTypeDeclaration_(typeDeclaration);
		String elementTypeName = typeDeclaration.substring(0, typeDeclaration.length() - (arrayDepth * 2));
		return new TypeDeclaration(elementTypeName, arrayDepth);
	}

	/**
	 * Return the class for the specified "type declaration".
	 */
	public static Class<?> classForTypeDeclaration(String elementTypeName, int arrayDepth) throws ClassNotFoundException {
		return classForTypeDeclaration(elementTypeName, arrayDepth, null);
	}
	
	/**
	 * Return the class for the specified "type declaration",
	 * using the specified class loader.
	 */
	// see the "Evaluation" of jdk bug 6446627 for a discussion of loading classes
	public static Class<?> classForTypeDeclaration(String elementTypeName, int arrayDepth, ClassLoader classLoader) throws ClassNotFoundException {
		// primitives cannot be loaded via Class#forName(),
		// so check for a primitive class name first
		Primitive pcc = null;
		if (elementTypeName.length() <= maxPrimitiveClassNameLength()) {  // performance tweak
			Primitive[] codes = primitives();
			for (int i = codes.length; i-- > 0; ) {
				if (codes[i].javaClass.getName().equals(elementTypeName)) {
					pcc = codes[i];
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
			sb.append(ARRAY_INDICATOR);
		}
		if (pcc == null) {
			sb.append(REFERENCE_CLASS_CODE);
			sb.append(elementTypeName);
			sb.append(REFERENCE_CLASS_NAME_DELIMITER);
		} else {
			sb.append(pcc.code);
		}
		return Class.forName(sb.toString(), false, classLoader);
	}
	
	/**
	 * Return the class name for the specified "type declaration"; e.g.
	 *     "int[]" -> "[I"
	 * @see java.lang.Class#getName()
	 */
	public static String classNameForTypeDeclaration(String typeDeclaration) {
		TypeDeclaration td = typeDeclaration(typeDeclaration);
		return classNameForTypeDeclaration(td.elementTypeName, td.arrayDepth);
	}

	/**
	 * Return the array depth for the specified "type declaration"; e.g.
	 *     "int[]" -> 1
	 */
	public static int arrayDepthForTypeDeclaration(String typeDeclaration) {
		return arrayDepthForTypeDeclaration_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/*
	 * Assume no whitespace in the type declaration.
	 */
	private static int arrayDepthForTypeDeclaration_(String typeDeclaration) {
		int last = typeDeclaration.length() - 1;
		int depth = 0;
		int close = last;
		while (typeDeclaration.charAt(close) == TYPE_DECLARATION_ARRAY_CLOSE) {
			if (typeDeclaration.charAt(close - 1) == TYPE_DECLARATION_ARRAY_OPEN) {
				depth++;
			} else {
				throw new IllegalArgumentException("invalid type declaration: " + typeDeclaration);
			}
			close = last - (depth * 2);
		}
		return depth;
	}
	
	/**
	 * Return the class name for the specified "type declaration".
	 * @see java.lang.Class#getName()
	 */
	public static String classNameForTypeDeclaration(String elementTypeName, int arrayDepth) {
		// non-array
		if (arrayDepth == 0) {
			return elementTypeName;
		}

		if (elementTypeName.equals(VOID_CLASS_NAME)) {
			throw new IllegalArgumentException("'" + VOID_CLASS_NAME + "' must have an array depth of zero: " + arrayDepth + '.');
		}
		// array
		StringBuilder sb = new StringBuilder(100);
		for (int i = arrayDepth; i-- > 0; ) {
			sb.append(ARRAY_INDICATOR);
		}

		// look for a primitive first
		Primitive pcc = null;
		if (elementTypeName.length() <= maxPrimitiveClassNameLength()) {  // performance tweak
			Primitive[] codes = primitives();
			for (int i = codes.length; i-- > 0; ) {
				if (codes[i].javaClass.getName().equals(elementTypeName)) {
					pcc = codes[i];
					break;
				}
			}
		}

		if (pcc == null) {
			sb.append(REFERENCE_CLASS_CODE);
			sb.append(elementTypeName);
			sb.append(REFERENCE_CLASS_NAME_DELIMITER);
		} else {
			sb.append(pcc.code);
		}

		return sb.toString();
	}

	private static int maxPrimitiveClassNameLength() {
		if (MAX_PRIMITIVE_CLASS_NAME_LENGTH == -1) {
			MAX_PRIMITIVE_CLASS_NAME_LENGTH = calculateMaxPrimitiveClassNameLength();
		}
		return MAX_PRIMITIVE_CLASS_NAME_LENGTH;
	}

	private static int calculateMaxPrimitiveClassNameLength() {
		int max = -1;
		Primitive[] codes = primitives();
		for (int i = codes.length; i-- > 0; ) {
			int len = codes[i].javaClass.getName().length();
			if (len > max) {
				max = len;
			}
		}
		return max;
	}

	private static int maxPrimitiveWrapperClassNameLength() {
		if (MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH == -1) {
			MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH = calculateMaxPrimitiveWrapperClassNameLength();
		}
		return MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH;
	}

	private static int calculateMaxPrimitiveWrapperClassNameLength() {
		int max = -1;
		Primitive[] codes = primitives();
		for (int i = codes.length; i-- > 0; ) {
			int len = codes[i].wrapperClass.getName().length();
			if (len > max) {
				max = len;
			}
		}
		return max;
	}

	private static Primitive[] primitives() {
		if (PRIMITIVES == null) {
			PRIMITIVES = buildPrimitives();
		}
		return PRIMITIVES;
	}
	
	/**
	 * NB: void.class.isPrimitive() == true
	 */
	private static Primitive[] buildPrimitives() {
		Primitive[] result = new Primitive[9];
		result[0] = new Primitive(BYTE_CODE, java.lang.Byte.class);
		result[1] = new Primitive(CHAR_CODE, java.lang.Character.class);
		result[2] = new Primitive(DOUBLE_CODE, java.lang.Double.class);
		result[3] = new Primitive(FLOAT_CODE, java.lang.Float.class);
		result[4] = new Primitive(INT_CODE, java.lang.Integer.class);
		result[5] = new Primitive(LONG_CODE, java.lang.Long.class);
		result[6] = new Primitive(SHORT_CODE, java.lang.Short.class);
		result[7] = new Primitive(BOOLEAN_CODE, java.lang.Boolean.class);
		result[8] = new Primitive(VOID_CODE, java.lang.Void.class);
		return result;
	}

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ClassTools() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** member classes **********

	private static class Primitive {
		final char code;
		final Class<?> javaClass;
		final Class<?> wrapperClass;
		private static final String WRAPPER_CLASS_TYPE_FIELD_NAME = "TYPE";
		// e.g. java.lang.Boolean.TYPE => boolean.class
		Primitive(char code, Class<?> wrapperClass) {
			this.code = code;
			this.wrapperClass = wrapperClass;
			this.javaClass = (Class<?>) staticFieldValue(wrapperClass, WRAPPER_CLASS_TYPE_FIELD_NAME);
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

}
