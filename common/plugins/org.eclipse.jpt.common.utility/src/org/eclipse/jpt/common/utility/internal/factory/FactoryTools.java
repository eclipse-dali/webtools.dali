/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Factory} utility methods.
 */
public class FactoryTools {

	// ********** adapters **********

	/**
	 * Return a factory that returns the elements returned by the specified
	 * {@link Iterable}'s iterator. The returned factory will throw a
	 * {@link java.util.NoSuchElementException} if it is invoked more times
	 * than there are elements in the specified iterable.
	 * @param <T> the type of the object returned by the factory
	 * @see IteratorFactory
	 */
	public static <T> Factory<T> adapt(Iterable<? extends T> iterable) {
		return adapt(iterable.iterator());
	}

	/**
	 * Return a factory that returns the elements returned by the specified
	 * {@link Iterator}. The returned factory will throw a
	 * {@link java.util.NoSuchElementException} if it is invoked more times
	 * than there are elements in the specified iterator.
	 * @param <T> the type of the object returned by the factory
	 * @see IteratorFactory
	 */
	public static <T> Factory<T> adapt(Iterator<? extends T> iterator) {
		return new IteratorFactory<>(iterator);
	}

	/**
	 * Adapt the specified {@link Closure} to the {@link Factory} interface.
	 * The closure will always passed a <code>null</code> argument.
	 * The factory will always return <code>null</code>.
	 * @param <T> the type of the object returned by the factory
	 */
	public static <T> Factory<T> adapt(Closure<?> closure) {
		return new ClosureFactory<>(closure);
	}

	/**
	 * Adapt the specified {@link Command} to the {@link Factory} interface.
	 * The factory will always return <code>null</code>.
	 * @param <T> the type of the object returned by the factory
	 */
	public static <T> Factory<T> adapt(Command command) {
		return new CommandFactory<>(command);
	}

	/**
	 * Adapt the specified {@link Transformer} to the {@link Factory} interface.
	 * The transformer will always passed a <code>null</code> input and its
	 * output will be returned as the factory's output.
	 * @param <T> the type of the object returned by the factory
	 */
	public static <T> Factory<T> adapt(Transformer<?, ? extends T> transformer) {
		return new TransformerFactory<>(transformer);
	}


	// ********** thread local **********

	/**
	 * Return a factory that allows the client to specify a different factory
	 * for each thread. If there is no factory for the current thread, the
	 * factory will return <code>null</code>.
	 * @param <T> the type of the object returned by the factory
	 * @see ThreadLocalFactory
	 */
	public static <T> ThreadLocalFactory<T> threadLocalFactory() {
		return threadLocalFactory(NullFactory.<T>instance());
	}

	/**
	 * Return a factory that allows the client to specify a different factory
	 * for each thread. If there is no factory for the current thread, the
	 * specified default factory is used.
	 * @param <T> the type of the object returned by the factory
	 * @see ThreadLocalFactory
	 */
	public static <T> ThreadLocalFactory<T> threadLocalFactory(Factory<? extends T> defaultFactory) {
		return new ThreadLocalFactory<>(defaultFactory);
	}


	// ********** wrappers **********

	/**
	 * Return a factory that can have its wrapped factory changed,
	 * allowing a client to change a previously-supplied factory's
	 * behavior mid-stream.
	 * @param <T> the type of the object returned by the factory
	 * @see FactoryWrapper
	 */
	public static <T> FactoryWrapper<T> wrap(Factory<? extends T> factory) {
		return new FactoryWrapper<>(factory);
	}


	// ********** casting **********

	/**
	 * Return a factory that simply casts the specified factory's return type.
	 * @param <X> the type of object returned by the wrapped factory
	 * @param <T> the type of object returned by the factory - this
	 *   is the same object returned by the wrapped factory, simply
	 *   cast to <code>T</code>
	 * @see CastingFactoryWrapper
	 * @see #upcast(Factory)
	 * @see #downcast(Factory)
	 */
	public static <X, T> Factory<T> cast(Factory<X> factory) {
		return new CastingFactoryWrapper<>(factory);
	}

	/**
	 * Return a factory that simply downcasts the specified factory's return type.
	 * @param <X> the type of object returned by the wrapped factory
	 * @param <T> the type of object returned by the factory - this
	 *   is the same object returned by the wrapped factory, simply
	 *   cast to <code>T</code>
	 * @see DowncastingFactoryWrapper
	 * @see #cast(Factory)
	 * @see #upcast(Factory)
	 */
	public static <X, T extends X> Factory<T> downcast(Factory<X> factory) {
		return new DowncastingFactoryWrapper<>(factory);
	}

	/**
	 * Return a factory that simply upcasts the specified factory's return type.
	 * @param <X> the type of object returned by the wrapped factory
	 * @param <T> the type of object returned by the factory - this
	 *   is the same object returned by the wrapped factory, simply
	 *   cast to <code>T</code>
	 * @see UpcastingFactoryWrapper
	 * @see #cast(Factory)
	 * @see #downcast(Factory)
	 */
	public static <T, X extends T> Factory<T> upcast(Factory<X> factory) {
		return new UpcastingFactoryWrapper<>(factory);
	}


	// ********** safe **********

	/**
	 * Return a factory that wraps the specified factory and handles
	 * any exceptions thrown during the creation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream} and <code>null</code> will be
	 * returned.
	 * @param <T> the type of the object returned by the factory
	 * @see SafeFactoryWrapper
	 * @see DefaultExceptionHandler
	 */
	public static <T> Factory<T> safe(Factory<? extends T> factory) {
		return safe(factory, DefaultExceptionHandler.instance(), null);
	}

	/**
	 * Return a factory that wraps the specified factory and handles
	 * any exceptions thrown during the creation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}
	 * and specified value will be returned.
	 * @param <T> the type of the object returned by the factory
	 * @see SafeFactoryWrapper
	 * @see DefaultExceptionHandler
	 */
	public static <T> Factory<T> safe(Factory<? extends T> factory, T exceptionValue) {
		return safe(factory, DefaultExceptionHandler.instance(), exceptionValue);
	}

	/**
	 * Return a factory that wraps the specified factory and handles
	 * any exceptions thrown during the creation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and <code>null</code> will be returned.
	 * @param <T> the type of the object returned by the factory
	 * @see SafeFactoryWrapper
	 */
	public static <T> Factory<T> safe(Factory<? extends T> factory, ExceptionHandler exceptionHandler) {
		return safe(factory, exceptionHandler, null);
	}

	/**
	 * Return a factory that wraps the specified factory and handles
	 * any exceptions thrown during the creation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and specified value will be returned.
	 * @param <T> the type of the object returned by the factory
	 * @see SafeFactoryWrapper
	 */
	public static <T> Factory<T> safe(Factory<? extends T> factory, ExceptionHandler exceptionHandler, T exceptionValue) {
		return new SafeFactoryWrapper<>(factory, exceptionHandler, exceptionValue);
	}


	// ********** reflection **********

	/**
	 * Return a factory that clones (via reflection) the specified prototype.
	 * @param <T> the type of the object returned by the factory
	 * @see CloneFactory
	 */
	public static <T extends Cloneable> Factory<T> cloneFactory(T prototype) {
		return new CloneFactory<>(prototype);
	}

	/**
	 * Return a factory that returns instances of the specified {@link Class},
	 * using the class's zero-argument constructor.
	 * Checked exceptions are converted to {@link RuntimeException}s.
	 * @param <T> the type of the object returned by the factory
	 * @see InstantiationFactory
	 */
	public static <T> Factory<T> instantiationFactory(Class<? extends T> clazz) {
		return instantiationFactory(clazz, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a factory that returns instances of the specified {@link Class},
	 * using the class's specified single-argument constructor.
	 * Checked exceptions are converted to {@link RuntimeException}s.
	 * @param <T> the type of the object returned by the factory
	 * @see InstantiationFactory
	 */
	public static <T> Factory<T> instantiationFactory(Class<T> clazz, Class<?> parameterType, Object argument) {
		return instantiationFactory(clazz, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Return a factory that returns instances of the specified {@link Class},
	 * using the class's specified constructor.
	 * Checked exceptions are converted to {@link RuntimeException}s.
	 * @param <T> the type of the object returned by the factory
	 * @see InstantiationFactory
	 */
	public static <T> Factory<T> instantiationFactory(Class<? extends T> clazz, Class<?>[] parameterTypes, Object[] arguments) {
		return new InstantiationFactory<>(clazz, parameterTypes, arguments);
	}

	/**
	 * Return a factory that uses Java reflection to return the value of 
	 * the specified static field.
	 * Checked exceptions are converted to {@link RuntimeException}s.
	 * @param <T> the type of the object returned by the factory
	 * @see Class#getDeclaredField(String)
	 * @see java.lang.reflect.Field#get(Object)
	 * @see StaticFieldFactory
	 */
	public static <T> Factory<T> get(Class<?> clazz, String fieldName) {
		return get(ClassTools.field(clazz, fieldName));
	}

	/**
	 * Return a factory that uses Java reflection to return the value of 
	 * the specified static field.
	 * Checked exceptions are converted to {@link RuntimeException}s.
	 * @param <T> the type of the object returned by the factory
	 * @see java.lang.reflect.Field#get(Object)
	 * @see StaticFieldFactory
	 */
	public static <T> Factory<T> get(Field field) {
		return new StaticFieldFactory<>(field);
	}

	/**
	 * Return a factory that uses Java reflection to return the value
	 * returned by the specified zero-argument static method.
	 * @param <T> the type of the object returned by the factory
	 * @see StaticMethodFactory
	 */
	public static <T> Factory<T> execute(Class<?> clazz, String methodName) {
		return execute(clazz, methodName, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a factory that uses Java reflection to return the value
	 * returned by the specified single-argument static method.
	 * @param <T> the type of the object returned by the factory
	 * @see StaticMethodFactory
	 */
	public static <T> Factory<T> execute(Class<?> clazz, String methodName, Class<?> parameterType, Object argument) {
		return execute(clazz, methodName, new Class<?>[] { parameterType }, new Object[] { argument });
	}

	/**
	 * Return a factory that uses Java reflection to return the value
	 * returned by the specified static method.
	 * @param <T> the type of the object returned by the factory
	 * @see StaticMethodFactory
	 */
	public static <T> Factory<T> execute(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return execute(ClassTools.method(clazz, methodName, parameterTypes), arguments);
	}

	/**
	 * Return a factory that uses Java reflection to return the value
	 * returned by the specified static method.
	 * @param <T> the type of the object returned by the factory
	 * @see StaticMethodFactory
	 */
	public static <T> Factory<T> execute(Method method, Object[] arguments) {
		return new StaticMethodFactory<>(method, arguments);
	}


	// ********** disabled **********

	/**
	 * Return a factory that will throw an {@link UnsupportedOperationException exception}
	 * if {@link Factory#create()} is called. This is useful in
	 * situations where a factory is optional and the default factory
	 * should not be used.
	 * @param <T> the type of the object returned by the factory
	 * @see DisabledFactory
	 */
	public static <T> Factory<T> disabledFactory() {
		return DisabledFactory.instance();
	}


	// ********** static **********

	/**
	 * Return a factory that will always return <code>null</code>.
	 * @param <T> the type of the object returned by the factory
	 * @see NullFactory
	 */
	public static <T> Factory<T> nullFactory() {
		return NullFactory.instance();
	}

	/**
	 * Return a factory that will always return the specified value.
	 * @param <T> the type of the object returned by the factory
	 * @see StaticFactory
	 * @see #nullFactory()
	 */
	public static <T> Factory<T> staticFactory(T value) {
		return new StaticFactory<>(value);
	}


	// ********** empty array **********

	@SuppressWarnings("unchecked")
	public static <T> Factory<T>[] emptyArray() {
		return EMPTY_ARRAY;
	}

	@SuppressWarnings("rawtypes")
	public static final Factory[] EMPTY_ARRAY = new Factory[0];


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private FactoryTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
