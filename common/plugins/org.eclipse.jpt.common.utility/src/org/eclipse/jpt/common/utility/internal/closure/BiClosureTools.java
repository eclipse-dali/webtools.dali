/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;

/**
 * {@link BiClosure} utility methods.
 */
public final class BiClosureTools {

	// ********** adapters **********

	/**
	 * Adapt the specified {@link Command} to the {@link BiClosure} interface.
	 * The closure's argument is ignored.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> BiClosure<A1, A2> adapt(Command command) {
		return new CommandBiClosure<>(command);
	}

	/**
	 * Adapt the specified {@link InterruptibleCommand} to the {@link InterruptibleBiClosure} interface.
	 * The closure's argument is ignored.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> InterruptibleBiClosure<A1, A2> adapt(InterruptibleCommand command) {
		return new InterruptibleCommandBiClosure<>(command);
	}

	/**
	 * Adapt the specified {@link Factory} to the {@link BiClosure} interface.
	 * The closure's argument and the factory's output are ignored. This really
	 * only useful for a factory that has side-effects.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> BiClosure<A1, A2> adapt(Factory<?> factory) {
		return new FactoryBiClosure<>(factory);
	}

	/**
	 * Adapt the specified {@link InterruptibleFactory} to the {@link InterruptibleBiClosure} interface.
	 * The closure's argument and the factory's output are ignored. This really
	 * only useful for a factory that has side-effects.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> InterruptibleBiClosure<A1, A2> adapt(InterruptibleFactory<?> factory) {
		return new InterruptibleFactoryBiClosure<>(factory);
	}


	// ********** null closure **********

	/**
	 * Return a closure that will do nothing.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * 
	 * @see NullBiClosure
	 */
	public static <A1, A2> BiClosure<A1, A2> nullBiClosure() {
		return NullBiClosure.instance();
	}


	// ********** thread local **********

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * closure will do nothing.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * @see ThreadLocalBiClosure
	 */
	public static <A1, A2> ThreadLocalBiClosure<A1, A2> threadLocalBiClosure() {
		return threadLocalBiClosure(NullBiClosure.instance());
	}

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * specified default closure is executed.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * @see ThreadLocalBiClosure
	 */
	public static <A1, A2> ThreadLocalBiClosure<A1, A2> threadLocalBiClosure(BiClosure<? super A1, ? super A2> defaultClosure) {
		return new ThreadLocalBiClosure<>(defaultClosure);
	}

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * closure will do nothing.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * @see ThreadLocalBiClosure
	 */
	public static <A1, A2> ThreadLocalInterruptibleBiClosure<A1, A2> threadLocalInterruptibleBiClosure() {
		return threadLocalInterruptibleBiClosure(NullBiClosure.instance());
	}

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * specified default closure is executed.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * @see ThreadLocalBiClosure
	 */
	public static <A1, A2> ThreadLocalInterruptibleBiClosure<A1, A2> threadLocalInterruptibleBiClosure(InterruptibleBiClosure<? super A1, ? super A2> defaultClosure) {
		return new ThreadLocalInterruptibleBiClosure<>(defaultClosure);
	}


	// ********** wrappers **********

	/**
	 * Return a closure that can have its wrapped closure changed,
	 * allowing a client to change a previously-supplied closure's
	 * behavior mid-stream.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * 
	 * @see BiClosureWrapper
	 */
	public static <A1, A2> BiClosureWrapper<A1, A2> wrap(BiClosure<? super A1, ? super A2> closure) {
		return new BiClosureWrapper<>(closure);
	}

	/**
	 * Return a closure that can have its wrapped closure changed,
	 * allowing a client to change a previously-supplied closure's
	 * behavior mid-stream.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * 
	 * @see InterruptibleBiClosureWrapper
	 */
	public static <A1, A2> InterruptibleBiClosureWrapper<A1, A2> wrap(InterruptibleBiClosure<? super A1, ? super A2> closure) {
		return new InterruptibleBiClosureWrapper<>(closure);
	}


	// ********** safe wrappers **********

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure. If the wrapped closure throws an exception,
	 * the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * 
	 * @see #safeBiClosure(BiClosure, ExceptionHandler)
	 */
	public static <A1, A2> BiClosure<A1, A2> safeBiClosure(BiClosure<? super A1, ? super A2> closure) {
		return safeBiClosure(closure, DefaultExceptionHandler.instance());
	}

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure with the specified exception handler. If the
	 * wrapped closure throws an exception, the safe closure will handle
	 * the exception and return.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * 
	 * @see #safeBiClosure(BiClosure)
	 */
	public static <A1, A2> BiClosure<A1, A2> safeBiClosure(BiClosure<? super A1, ? super A2> closure, ExceptionHandler exceptionHandler) {
		return new SafeBiClosureWrapper<>(closure, exceptionHandler);
	}

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure. If the wrapped closure throws an exception,
	 * the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * 
	 * @see #safeBiClosure(InterruptibleBiClosure, ExceptionHandler)
	 */
	public static <A1, A2> InterruptibleBiClosure<A1, A2> safeBiClosure(InterruptibleBiClosure<? super A1, ? super A2> closure) {
		return safeBiClosure(closure, DefaultExceptionHandler.instance());
	}

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure with the specified exception handler. If the
	 * wrapped closure throws an exception, the safe closure will handle
	 * the exception and return.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 * 
	 * @see #safeBiClosure(InterruptibleBiClosure)
	 */
	public static <A1, A2> InterruptibleBiClosure<A1, A2> safeBiClosure(InterruptibleBiClosure<? super A1, ? super A2> closure, ExceptionHandler exceptionHandler) {
		return new SafeInterruptibleBiClosureWrapper<>(closure, exceptionHandler);
	}


	// ********** composite **********

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	@SafeVarargs
	public static <A1, A2> BiClosure<A1, A2> compositeBiClosure(BiClosure<? super A1, ? super A2>... closures) {
		return (closures.length != 0) ? compositeBiClosure_(ArrayTools.iterable(closures)) : nullBiClosure();
	}

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> BiClosure<A1, A2> compositeBiClosure(Iterable<? extends BiClosure<? super A1, ? super A2>> closures) {
		return (closures.iterator().hasNext()) ? compositeBiClosure_(closures) : nullBiClosure();
	}

	/**
	 * assume non-empty
	 */
	private static <A1, A2> BiClosure<A1, A2> compositeBiClosure_(Iterable<? extends BiClosure<? super A1, ? super A2>> closures) {
		return new CompositeBiClosure<>(closures);
	}

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	@SafeVarargs
	public static <A1, A2> InterruptibleBiClosure<A1, A2> compositeInterruptibleBiClosure(InterruptibleBiClosure<? super A1, ? super A2>... closures) {
		return (closures.length != 0) ? compositeInterruptibleBiClosure_(ArrayTools.iterable(closures)) : nullBiClosure();
	}

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> InterruptibleBiClosure<A1, A2> compositeInterruptibleBiClosure(Iterable<? extends InterruptibleBiClosure<? super A1, ? super A2>> closures) {
		return (closures.iterator().hasNext()) ? compositeInterruptibleBiClosure_(closures) : nullBiClosure();
	}

	/**
	 * assume non-empty
	 */
	private static <A1, A2> InterruptibleBiClosure<A1, A2> compositeInterruptibleBiClosure_(Iterable<? extends InterruptibleBiClosure<? super A1, ? super A2>> closures) {
		return new CompositeInterruptibleBiClosure<>(closures);
	}


	// ********** disabled **********

	/**
	 * Return a closure that will throw an
	 * {@link UnsupportedOperationException exception} when executed.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> BiClosure<A1, A2> disabledBiClosure() {
		return DisabledBiClosure.instance();
	}


	// ********** interrupted **********

	/**
	 * Return a closure that will throw an
	 * {@link InterruptedException exception} when executed.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> InterruptibleBiClosure<A1, A2> interruptedBiClosure() {
		return InterruptedBiClosure.instance();
	}

	// ********** looping **********

	/**
	 * Return a closure that executes the specified closure the specified
	 * number of times.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> BiClosure<A1, A2> repeatingBiClosure(BiClosure<? super A1, ? super A2> closure, int count) {
		return new RepeatingBiClosure<>(closure, count);
	}

	/**
	 * Return a closure that executes the specified closure the specified
	 * number of times.
	 * 
	 * @param <A1> the type of the first object passed to the closure
	 * @param <A2> the type of the second object passed to the closure
	 */
	public static <A1, A2> InterruptibleBiClosure<A1, A2> repeatingBiClosure(InterruptibleBiClosure<? super A1, ? super A2> closure, int count) {
		return new RepeatingInterruptibleBiClosure<>(closure, count);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private BiClosureTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
