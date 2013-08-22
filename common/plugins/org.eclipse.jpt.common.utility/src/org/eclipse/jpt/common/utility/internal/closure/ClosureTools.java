/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.command.NullCommand;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Closure} utility methods.
 */
public final class ClosureTools {

	// ********** adapters **********

	/**
	 * Adapt the specified {@link Command} to the {@link Closure} interface.
	 * The closure's argument is ignored.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> adapt(Command command) {
		return new CommandClosure<A>(command);
	}

	/**
	 * Adapt the specified {@link Factory} to the {@link Closure} interface.
	 * The closure's argument and the factory's output are ignored. This really
	 * only useful for a factory that has side-effects.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> adapt(Factory<?> factory) {
		return new FactoryClosure<A>(factory);
	}

	/**
	 * Adapt the specified {@link Transformer} to the {@link Closure} interface.
	 * The transformer's output is ignored. This really only useful for a
	 * transformer that has side-effects.
	 * @param <A> the type of the object passed to the closure and forwarded to
	 *     the transformer
	 */
	public static <A> Closure<A> adapt(Transformer<? super A, ?> transformer) {
		return new TransformerClosure<A>(transformer);
	}


	// ********** thread local **********

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * closure will do nothing.
	 * @param <A> the type of the object passed to the closure
	 * @see ThreadLocalClosure
	 */
	public static <A> ThreadLocalClosure<A> threadLocalClosure() {
		return threadLocalClosure(NullClosure.instance());
	}

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * specified default closure is executed.
	 * @param <A> the type of the object passed to the closure
	 * @see ThreadLocalClosure
	 */
	public static <A> ThreadLocalClosure<A> threadLocalClosure(Closure<? super A> defaultClosure) {
		return new ThreadLocalClosure<A>(defaultClosure);
	}


	// ********** wrappers **********

	/**
	 * Return a closure that wraps the specified closure and checks
	 * for a <code>null</code> argument before forwarding the argument to the
	 * specified closure. If the argument is <code>null</code>, the closure will
	 * do nothing.
	 * @param <A> the type of the object passed to the closure
	 * @see #nullCheck(Closure, Command)
	 */
	public static <A> Closure<A> nullCheck(Closure<? super A> closure) {
		return nullCheck(closure, NullCommand.instance());
	}

	/**
	 * Return a closure that wraps the specified closure and checks
	 * for a <code>null</code> argument before forwarding the argument to the
	 * specified closure. If the argument is <code>null</code>,
	 * the closure will execute the specified command.
	 * @param <A> the type of the object passed to the closure
	 * @see #nullCheck(Closure)
	 */
	public static <A> Closure<A> nullCheck(Closure<? super A> closure, Command nullCommand) {
		return new NullCheckClosureWrapper<A>(closure, nullCommand);
	}

	/**
	 * Return a closure that can have its wrapped closure changed,
	 * allowing a client to change a previously-supplied closure's
	 * behavior mid-stream.
	 * @param <A> the type of the object passed to the closure
	 * @see ClosureWrapper
	 */
	public static <A> ClosureWrapper<A> wrap(Closure<? super A> closure) {
		return new ClosureWrapper<A>(closure);
	}


	// ********** safe **********

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure. If the wrapped closure throws an exception,
	 * the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}.
	 * @param <A> the type of the object passed to the closure
	 * @see #safe(Closure, ExceptionHandler)
	 */
	public static <A> Closure<A> safe(Closure<? super A> closure) {
		return safe(closure, DefaultExceptionHandler.instance());
	}

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure with the specified exception handler. If the
	 * wrapped closure throws an exception, the safe closure will handle
	 * the exception and return.
	 * @param <A> the type of the object passed to the closure
	 * @see #safe(Closure)
	 */
	public static <A> Closure<A> safe(Closure<? super A> closure, ExceptionHandler exceptionHandler) {
		return new SafeClosureWrapper<A>(closure, exceptionHandler);
	}


	// ********** reflection **********

	/**
	 * Return a closure that uses Java reflection to invoke the specified
	 * zero-argument method on the argument.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the closure can be used to emulate
	 * "duck typing".
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> execute(String methodName) {
		return execute(methodName, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a closure that uses Java reflection to invoke the specified
	 * single-argument method on the argument.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the closure can be used to emulate
	 * "duck typing".
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> execute(String methodName, Class<?> parameterType, Object argument) {
		return execute(methodName, new Class<?>[] { parameterType }, new Object[] { argument });
	}

	/**
	 * Return a closure that uses Java reflection to invoke the specified method
	 * on the argument.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the closure can be used to emulate
	 * "duck typing".
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> execute(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return new MethodClosure<A>(methodName, parameterTypes, arguments);
	}


	// ********** composite **********

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> composite(Closure<? super A>... closures) {
		return composite(ArrayTools.iterable(closures));
	}

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> composite(Iterable<Closure<? super A>> closures) {
		return new CompositeClosure<A>(closures);
	}


	// ********** conditional **********

	/**
	 * Return a closure that passes its argument to the specified predicate to
	 * determine whether to execute the specified closure.
	 * If the predicate evaluates to <code>false</code>, the closure will do
	 * nothing.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> conditionalClosure(Predicate<? super A> predicate, Closure<? super A> closure) {
		return conditionalClosure(predicate, closure, NullClosure.instance());
	}

	/**
	 * Return a closure that passes its argument to the specified predicate to
	 * determine which of the two specified closures to execute.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> conditionalClosure(Predicate<? super A> predicate, Closure<? super A> trueClosure, Closure<? super A> falseClosure) {
		return new ConditionalClosure<A>(predicate, trueClosure, falseClosure);
	}


	// ********** switch **********

	/**
	 * Return a closure that loops over the specified set of predicate/closure
	 * pairs, passing its argument to each predicate to determine
	 * which of the closures to execute. Only the first closure whose predicate
	 * evaluates to <code>true</code> is executed, even if other, following,
	 * predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the closure
	 * will do nothing.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> switchClosure(Iterable<Association<Predicate<? super A>, Closure<? super A>>> closures) {
		return switchClosure(closures, NullClosure.instance());
	}

	/**
	 * Return a closure that loops over the specified set of predicate/closure
	 * pairs, passing its argument to each predicate to determine
	 * which of the closures to execute. Only the first closure whose predicate
	 * evaluates to <code>true</code> is executed, even if other, following,
	 * predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the specified
	 * default closure is executed.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> switchClosure(Iterable<Association<Predicate<? super A>, Closure<? super A>>> closures, Closure<? super A> defaultClosure) {
		return new SwitchClosure<A>(closures, defaultClosure);
	}


	// ********** disabled **********

	/**
	 * Return a closure that will throw an
	 * {@link UnsupportedOperationException exception} when executed.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> disabledClosure() {
		return DisabledClosure.instance();
	}


	// ********** looping **********

	/**
	 * Return a closure that executes the specified closure the specified
	 * number of times.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> repeat(Closure<? super A> closure, int count) {
		return new RepeatingClosure<A>(closure, count);
	}

	/**
	 * Return a closure that executes the specified closure while the specified
	 * predicate evaluates to <code>true</code> when passed the argument.
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> while_(Predicate<? super A> predicate, Closure<? super A> closure) {
		return new WhileClosure<A>(predicate, closure);
	}

	/**
	 * Return a closure that executes the specified closure until the specified
	 * predicate evaluates to <code>true</code> when passed the argument. The
	 * specifie closure will always execute at least once.
	 * <p>
	 * <strong>NB:</strong> This is the inverse of the Java <code>do-while</code>
	 * statement (i.e. it executes until the predicate evaluates to
	 * <strong><code>true</code></strong>,
	 * <em>not</em> <code>false</code>).
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> until(Closure<? super A> closure, Predicate<? super A> predicate) {
		return new UntilClosure<A>(closure, predicate);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ClosureTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
