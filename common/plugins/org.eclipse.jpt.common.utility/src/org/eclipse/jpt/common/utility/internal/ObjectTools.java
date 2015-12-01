/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.eclipse.jpt.common.utility.internal.collection.RepeatingElementList;
import org.eclipse.jpt.common.utility.internal.iterable.ChainIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.RepeatingElementIterator;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Object} utility methods.
 * <p>
 * There are a number of convenience reflection methods.
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
public final class ObjectTools {

	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];


	// ********** object comparison **********

	/**
	 * Return whether the specified objects are equal, with the appropriate
	 * <code>null</code> checks.
	 * @see Object#equals(Object)
	 */
	public static boolean equals(Object object1, Object object2) {
		return (object1 == null) ?
				(object2 == null) :
				((object2 != null) && object1.equals(object2));
	}

	/**
	 * Return whether the specified objects are not equal, with the appropriate
	 * <code>null</code> checks.
	 * @see Object#equals(Object)
	 */
	public static boolean notEquals(Object object1, Object object2) {
		return ! equals(object1, object2);
	}


	// ********** hash code **********

	/**
	 * Return the hash code of the specified object, with the appropriate
	 * <code>null</code> check (i.e. return <code>0</code> if the specified
	 * object is <code>null</code>).
	 * @see Object#hashCode()
	 */
	public static int hashCode(Object object) {
		return hashCode(object, 0);
	}

	/**
	 * Return the hash code of the specified object, with the appropriate
	 * <code>null</code> check (i.e. return the specified <code>null</code>
	 * hash code if the specified object is <code>null</code>).
	 * @see Object#hashCode()
	 */
	public static int hashCode(Object object, int nullHashCode) {
		return (object == null) ? nullHashCode : object.hashCode();
	}


	// ********** iterables **********

	/**
	 * Return a chain iterable that starts with the specified object and uses
	 * the specified {@link Transformer transformer}.
	 * @see ChainIterable
	 */
	public static <E> Iterable<E> chain(E object, Transformer<? super E, ? extends E> transformer) {
		return IterableTools.chainIterable(object, transformer);
	}

	/**
	 * Return an iterable that will return the specified object followed
	 * by its children etc. as determined by the specified transformer.
	 */
	public static <E> Iterable<E> graph(E object, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return IterableTools.graphIterable(object, transformer);
	}

	/**
	 * Return a list the returns the specified object the specified number
	 * of times.
	 * @see RepeatingElementIterator
	 */
	public static <E> RepeatingElementList<E> repeat(E object, int size) {
		return new RepeatingElementList<>(object, size);
	}

	/**
	 * Construct an iterable that returns the nodes of a tree
	 * with the specified object as its root and transformer.
	 */
	public static <E> Iterable<E> tree(E object, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return IterableTools.treeIterable(object, transformer);
	}


	// ********** string representation **********

	/**
	 * Build a "Dali standard" {@link Object#toString() toString()} result for
	 * the specified object and additional information:<pre>
	 *     ClassName[00-F3-EE-42](add'l info)
	 * </pre>
	 * @see Object#toString()
	 */
	public static String toString(Object object, Object additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, Object[] additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		StringBuilderTools.append(sb, additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, boolean additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, char additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, char[] additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, CharSequence additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, double additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, float additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, int additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * @see #toString(Object, Object)
	 */
	public static String toString(Object object, long additionalInfo) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Build a "Dali standard" {@link Object#toString() toString()} result for
	 * the specified object:<pre>
	 *     ClassName[00-F3-EE-42]
	 * </pre>
	 * @see Object#toString()
	 */
	public static String toString(Object object) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, object);
		return sb.toString();
	}

	/**
	 * Build a "Java standard" {@link Object#toString() toString()} result for
	 * the specified object:<pre>
	 *     package.ClassName@F3EE42
	 * </pre>
	 * @see Object#toString()
	 */
	public static String identityToString(Object object) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendIdentityToString(sb, object);
		return sb.toString();
	}

	/**
	 * Return a string suitable for a <em>singleton</em>; which is the
	 * "qualified simple" name of the object's class, since there should only
	 * be one:<pre>
	 *     TopLevelClass.MemberClassName
	 * </pre>
	 * @see ClassTools#toStringName(Class)
	 * @see Object#toString()
	 */
	public static String singletonToString(Object object) {
		return ClassTools.toStringName(object.getClass());
	}


	// ********** field values **********

	/**
	 * Return the value of the specified object's field with the specified
	 * name.
	 * Useful for accessing private, package, or protected fields.
	 * @see Field#get(Object)
	 */
	public static Object get(Object object, String fieldName) {
		try {
			return get_(object, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, object, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, object, fieldName), ex);
		}
	}

	/**
	 * @see #get(Object, String)
	 */
	public static Object get_(Object object, String fieldName)
		throws NoSuchFieldException, IllegalAccessException
	{
		return field_(object, fieldName).get(object);
	}

	/**
	 * Set the value of the specified object's field with the specified
	 * name to the specified value.
	 * Useful for accessing private, package, or protected fields.
	 * @see Field#set(Object, Object)
	 */
	public static void set(Object object, String fieldName, Object value) {
		try {
			set_(object, fieldName, value);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, object, fieldName), ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, object, fieldName), ex);
		}
	}

	/**
	 * @see #set(Object, String, Object)
	 */
	public static void set_(Object object, String fieldName, Object value)
		throws NoSuchFieldException, IllegalAccessException
	{
		field_(object, fieldName).set(object, value);
	}


	// ********** fields **********

	/**
	 * @see ClassTools#field(Class, String)
	 */
	public static Field field(Object object, String fieldName) {
		try {
			return field_(object, fieldName);
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(buildFieldExceptionMessage(ex, object, fieldName), ex);
		}
	}

	/**
	 * @see ClassTools#field_(Class, String)
	 */
	public static Field field_(Object object, String fieldName)
		throws NoSuchFieldException
	{
		return ClassTools.field_(object.getClass(), fieldName);
	}

	/**
	 * Return a string representation of the specified field.
	 */
	private static String buildFieldExceptionMessage(Exception ex, Object object, String fieldName) {
		return ClassTools.buildFieldExceptionMessage(ex, object.getClass(), fieldName);
	}


	// ********** method execution **********

	/**
	 * Execute the specified zero-argument method.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 */
	public static Object execute(Object object, String methodName) {
		return execute(object, methodName, ClassTools.EMPTY_ARRAY, EMPTY_OBJECT_ARRAY);
	}

	/**
	 * @see #execute(Object, String)
	 */
	public static Object execute_(Object object, String methodName)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return execute_(object, methodName, ClassTools.EMPTY_ARRAY, EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Execute the specified one-argument method.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 */
	public static Object execute(Object object, String methodName, Class<?> parameterType, Object argument) {
		return execute(object, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * @see #execute(Object, String, Class, Object)
	 */
	public static Object execute_(Object object, String methodName, Class<?> parameterType, Object argument)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return execute_(object, methodName, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Execute the specified method.
	 * Return its result.
	 * Useful for invoking private, package, or protected methods.
	 */
	public static Object execute(Object object, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return execute(object, method(object, methodName, parameterTypes), arguments);
	}

	/**
	 * Execute the specified method, given the receiver and arguments.
	 * Return its result.
	 * Useful for invoking cached methods.
	 */
	public static Object execute(Object receiver, Method method, Object[] arguments) {
		try {
			return method.invoke(receiver, arguments);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex + StringTools.CR + method, ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(method + StringTools.CR + ex.getTargetException(), ex);
		}
	}

	/**
	 * @see #execute(Object, String, Class[], Object[])
	 */
	public static Object execute_(Object object, String methodName, Class<?>[] parameterTypes, Object[] arguments)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return method_(object, methodName, parameterTypes).invoke(object, arguments);
	}


	// ********** methods **********

	/**
	 * Return the zero-argument method for the specified object
	 * and method name. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method method(Object object, String methodName) {
		return ClassTools.method(object.getClass(), methodName);
	}

	/**
	 * @see #method(Object, String)
	 */
	public static Method method_(Object object, String methodName)
		throws NoSuchMethodException
	{
		return ClassTools.method_(object.getClass(), methodName);
	}

	/**
	 * Return the one-argument method for the specified object, method name,
	 * and formal parameter type. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method method(Object object, String methodName, Class<?> parameterType) {
		return ClassTools.method(object.getClass(), methodName, parameterType);
	}

	/**
	 * @see #method(Object, String, Class)
	 */
	public static Method method_(Object object, String methodName, Class<?> parameterType)
		throws NoSuchMethodException
	{
		return ClassTools.method_(object.getClass(), methodName, parameterType);
	}

	/**
	 * Return the method for the specified object, method name,
	 * and formal parameter types. If the object's class does not directly
	 * implement the method, look for it in the class's superclasses.
	 * Make any private/package/protected method accessible.
	 */
	public static Method method(Object object, String methodName, Class<?>[] parameterTypes) {
		return ClassTools.method(object.getClass(), methodName, parameterTypes);
	}

	/**
	 * @see #method(Object, String, Class[])
	 */
	public static Method method_(Object object, String methodName, Class<?>[] parameterTypes)
		throws NoSuchMethodException
	{
		return ClassTools.method_(object.getClass(), methodName, parameterTypes);
	}


	// ********** JSON **********

	/**
	 * @see #method(Object, String, Class[])
	 */
	public static String toJSON(Object object) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, object);
		return sb.toString();
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ObjectTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
