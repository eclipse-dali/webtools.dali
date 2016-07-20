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

import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.command.NullCommand;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Closure} utility methods.
 */
public final class ClosureTools {

	// ********** adapters **********

	/**
	 * Adapt the specified {@link Command} to the {@link Closure} interface.
	 * The closure's argument is ignored.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> adapt(Command command) {
		return new CommandClosure<>(command);
	}

	/**
	 * Adapt the specified {@link InterruptibleCommand} to the {@link InterruptibleClosure} interface.
	 * The closure's argument is ignored.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> adapt(InterruptibleCommand command) {
		return new InterruptibleCommandClosure<>(command);
	}

	/**
	 * Adapt the specified {@link Factory} to the {@link Closure} interface.
	 * The closure's argument and the factory's output are ignored. In practice,
	 * this is useful only for a factory that has side-effects.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> adapt(Factory<?> factory) {
		return new FactoryClosure<>(factory);
	}

	/**
	 * Adapt the specified {@link InterruptibleFactory} to the {@link InterruptibleClosure} interface.
	 * The closure's argument and the factory's output are ignored. In practice,
	 * this is useful only for a factory that has side-effects.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> adapt(InterruptibleFactory<?> factory) {
		return new InterruptibleFactoryClosure<>(factory);
	}

	/**
	 * Adapt the specified {@link Transformer} to the {@link Closure} interface.
	 * The transformer's output is ignored. In practice, this is useful only for a
	 * transformer that has side-effects.
	 * 
	 * @param <A> the type of the object passed to the closure and forwarded to
	 *     the transformer
	 */
	public static <A> Closure<A> adapt(Transformer<? super A, ?> transformer) {
		return new TransformerClosure<>(transformer);
	}

	/**
	 * Adapt the specified {@link InterruptibleTransformer} to the {@link InterruptibleClosure} interface.
	 * The transformer's output is ignored. In practice, this is useful only for a
	 * transformer that has side-effects.
	 * 
	 * @param <A> the type of the object passed to the closure and forwarded to
	 *     the transformer
	 */
	public static <A> InterruptibleClosure<A> adapt(InterruptibleTransformer<? super A, ?> transformer) {
		return new InterruptibleTransformerClosure<>(transformer);
	}


	// ********** null closure **********

	/**
	 * Return a closure that will do nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see NullClosure
	 */
	public static <A> Closure<A> nullClosure() {
		return NullClosure.instance();
	}


	// ********** thread local **********

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * closure will do nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see ThreadLocalClosure
	 */
	public static <A> ThreadLocalClosure<A> threadLocalClosure() {
		return threadLocalClosure(nullClosure());
	}

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * specified default closure is executed.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see ThreadLocalClosure
	 */
	public static <A> ThreadLocalClosure<A> threadLocalClosure(Closure<? super A> defaultClosure) {
		return new ThreadLocalClosure<>(defaultClosure);
	}

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * closure will do nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see ThreadLocalInterruptibleClosure
	 */
	public static <A> ThreadLocalInterruptibleClosure<A> threadLocalInterruptibleClosure() {
		return threadLocalInterruptibleClosure(nullClosure());
	}

	/**
	 * Return a closure that allows the client to specify a different closure
	 * for each thread. If there is no closure for the current thread, the
	 * specified default closure is executed.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see ThreadLocalInterruptibleClosure
	 */
	public static <A> ThreadLocalInterruptibleClosure<A> threadLocalInterruptibleClosure(InterruptibleClosure<? super A> defaultClosure) {
		return new ThreadLocalInterruptibleClosure<>(defaultClosure);
	}


	// ********** plain wrappers **********

	/**
	 * Return a closure that can have its wrapped closure changed,
	 * allowing a client to change a previously-supplied closure's
	 * behavior mid-stream.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see ClosureWrapper
	 */
	public static <A> ClosureWrapper<A> wrap(Closure<? super A> closure) {
		return new ClosureWrapper<>(closure);
	}

	/**
	 * Return a closure that can have its wrapped closure changed,
	 * allowing a client to change a previously-supplied closure's
	 * behavior mid-stream.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see InterruptibleClosureWrapper
	 */
	public static <A> InterruptibleClosureWrapper<A> wrap(InterruptibleClosure<? super A> closure) {
		return new InterruptibleClosureWrapper<>(closure);
	}


	// ********** null check wrappers **********

	/**
	 * Return a closure that wraps the specified closure and checks
	 * for a <code>null</code> argument before forwarding the argument to the
	 * specified closure. If the argument is <code>null</code>, the closure will
	 * do nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
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
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see #nullCheck(Closure)
	 */
	public static <A> Closure<A> nullCheck(Closure<? super A> closure, Command nullCommand) {
		return new NullCheckClosureWrapper<>(closure, nullCommand);
	}

	/**
	 * Return a closure that wraps the specified closure and checks
	 * for a <code>null</code> argument before forwarding the argument to the
	 * specified closure. If the argument is <code>null</code>, the closure will
	 * do nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see #nullCheck(InterruptibleClosure, InterruptibleCommand)
	 */
	public static <A> InterruptibleClosure<A> nullCheck(InterruptibleClosure<? super A> closure) {
		return nullCheck(closure, NullCommand.instance());
	}

	/**
	 * Return a closure that wraps the specified closure and checks
	 * for a <code>null</code> argument before forwarding the argument to the
	 * specified closure. If the argument is <code>null</code>,
	 * the closure will execute the specified command.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see #nullCheck(InterruptibleClosure)
	 */
	public static <A> InterruptibleClosure<A> nullCheck(InterruptibleClosure<? super A> closure, InterruptibleCommand nullCommand) {
		return new NullCheckInterruptibleClosureWrapper<>(closure, nullCommand);
	}


	// ********** safe wrappers **********

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure. If the wrapped closure throws an exception,
	 * the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}.
	 * @param <A> the type of the object passed to the closure
	 * 
	 * 
	 * @see #safeClosure(Closure, ExceptionHandler)
	 */
	public static <A> Closure<A> safeClosure(Closure<? super A> closure) {
		return safeClosure(closure, DefaultExceptionHandler.instance());
	}

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure with the specified exception handler. If the
	 * wrapped closure throws an exception, the safe closure will handle
	 * the exception and return.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see #safeClosure(Closure)
	 */
	public static <A> Closure<A> safeClosure(Closure<? super A> closure, ExceptionHandler exceptionHandler) {
		return new SafeClosureWrapper<>(closure, exceptionHandler);
	}

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure. If the wrapped closure throws an exception,
	 * the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}.
	 * @param <A> the type of the object passed to the closure
	 * 
	 * 
	 * @see #safeClosure(InterruptibleClosure, ExceptionHandler)
	 */
	public static <A> InterruptibleClosure<A> safeClosure(InterruptibleClosure<? super A> closure) {
		return safeClosure(closure, DefaultExceptionHandler.instance());
	}

	/**
	 * Return closure that will handle any exceptions thrown by the specified
	 * closure with the specified exception handler. If the
	 * wrapped closure throws an exception, the safe closure will handle
	 * the exception and return.
	 * 
	 * @param <A> the type of the object passed to the closure
	 * 
	 * @see #safeClosure(InterruptibleClosure)
	 */
	public static <A> InterruptibleClosure<A> safeClosure(InterruptibleClosure<? super A> closure, ExceptionHandler exceptionHandler) {
		return new SafeInterruptibleClosureWrapper<>(closure, exceptionHandler);
	}


	// ********** reflection **********

	/**
	 * Return a closure that uses Java reflection to invoke the specified
	 * zero-argument method on the argument.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the closure can be used to emulate
	 * "duck typing".
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> methodClosure(String methodName) {
		return methodClosure(methodName, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a closure that uses Java reflection to invoke the specified
	 * single-argument method on the argument.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the closure can be used to emulate
	 * "duck typing".
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> methodClosure(String methodName, Class<?> parameterType, Object argument) {
		return methodClosure(methodName, new Class<?>[] { parameterType }, new Object[] { argument });
	}

	/**
	 * Return a closure that uses Java reflection to invoke the specified method
	 * on the argument.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the closure can be used to emulate
	 * "duck typing".
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> methodClosure(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return new MethodClosure<>(methodName, parameterTypes, arguments);
	}


	// ********** composite **********

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	@SafeVarargs
	public static <A> Closure<A> compositeClosure(Closure<? super A>... closures) {
		return (closures.length != 0) ? compositeClosure_(ArrayTools.iterable(closures)) : nullClosure();
	}

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> compositeClosure(Iterable<? extends Closure<? super A>> closures) {
		return (closures.iterator().hasNext()) ? compositeClosure_(closures) : nullClosure();
	}

	/**
	 * assume non-empty
	 */
	private static <A> Closure<A> compositeClosure_(Iterable<? extends Closure<? super A>> closures) {
		return new CompositeClosure<>(closures);
	}

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	@SafeVarargs
	public static <A> InterruptibleClosure<A> compositeInterruptibleClosure(InterruptibleClosure<? super A>... closures) {
		return (closures.length != 0) ? compositeInterruptibleClosure_(ArrayTools.iterable(closures)) : nullClosure();
	}

	/**
	 * Return a composite of the specified closures. The composite's argument
	 * will be passed to each closure, in sequence.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> compositeInterruptibleClosure(Iterable<? extends InterruptibleClosure<? super A>> closures) {
		return (closures.iterator().hasNext()) ? compositeInterruptibleClosure_(closures) : nullClosure();
	}

	/**
	 * assume non-empty
	 */
	private static <A> InterruptibleClosure<A> compositeInterruptibleClosure_(Iterable<? extends InterruptibleClosure<? super A>> closures) {
		return new CompositeInterruptibleClosure<>(closures);
	}


	// ********** conditional **********

	/**
	 * Return a closure that passes its argument to the specified predicate to
	 * determine whether to execute the specified closure.
	 * If the predicate evaluates to <code>false</code>, the closure will do
	 * nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> conditionalClosure(Predicate<? super A> predicate, Closure<? super A> closure) {
		return conditionalClosure(predicate, closure, nullClosure());
	}

	/**
	 * Return a closure that passes its argument to the specified predicate to
	 * determine which of the two specified closures to execute.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> conditionalClosure(Predicate<? super A> predicate, Closure<? super A> trueClosure, Closure<? super A> falseClosure) {
		return new ConditionalClosure<>(predicate, trueClosure, falseClosure);
	}

	/**
	 * Return a closure that passes its argument to the specified predicate to
	 * determine whether to execute the specified closure.
	 * If the predicate evaluates to <code>false</code>, the closure will do
	 * nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> conditionalClosure(Predicate<? super A> predicate, InterruptibleClosure<? super A> closure) {
		return conditionalClosure(predicate, closure, nullClosure());
	}

	/**
	 * Return a closure that passes its argument to the specified predicate to
	 * determine which of the two specified closures to execute.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> conditionalClosure(Predicate<? super A> predicate, InterruptibleClosure<? super A> trueClosure, InterruptibleClosure<? super A> falseClosure) {
		return new ConditionalInterruptibleClosure<>(predicate, trueClosure, falseClosure);
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
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> switchClosure(Iterable<? extends Association<? extends Predicate<? super A>, ? extends Closure<? super A>>> closures) {
		return switchClosure(closures, nullClosure());
	}

	/**
	 * Return a closure that loops over the specified set of predicate/closure
	 * pairs, passing its argument to each predicate to determine
	 * which of the closures to execute. Only the first closure whose predicate
	 * evaluates to <code>true</code> is executed, even if other, following,
	 * predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the specified
	 * default closure is executed.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> switchClosure(Iterable<? extends Association<? extends Predicate<? super A>, ? extends Closure<? super A>>> closures, Closure<? super A> defaultClosure) {
		return new SwitchClosure<>(closures, defaultClosure);
	}

	/**
	 * Return a closure that loops over the specified set of predicate/closure
	 * pairs, passing its argument to each predicate to determine
	 * which of the closures to execute. Only the first closure whose predicate
	 * evaluates to <code>true</code> is executed, even if other, following,
	 * predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the closure
	 * will do nothing.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> switchInterruptibleClosure(Iterable<? extends Association<? extends Predicate<? super A>, ? extends InterruptibleClosure<? super A>>> closures) {
		return switchInterruptibleClosure(closures, nullClosure());
	}

	/**
	 * Return a closure that loops over the specified set of predicate/closure
	 * pairs, passing its argument to each predicate to determine
	 * which of the closures to execute. Only the first closure whose predicate
	 * evaluates to <code>true</code> is executed, even if other, following,
	 * predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the specified
	 * default closure is executed.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> switchInterruptibleClosure(Iterable<? extends Association<? extends Predicate<? super A>, ? extends InterruptibleClosure<? super A>>> closures, InterruptibleClosure<? super A> defaultClosure) {
		return new SwitchInterruptibleClosure<>(closures, defaultClosure);
	}


	// ********** boolean **********

	/**
	 * Return a closure that adapts the specified <code>boolean</code> "closure".
	 * If the closure's argument is <code>null</code>, the closure will throw a
	 * {@link NullPointerException}.
	 */
	public static Closure<Boolean> booleanClosure(BooleanClosure.Adapter adapter) {
		return new BooleanClosure(adapter);
	}

	/**
	 * Return a closure that adapts the specified <code>boolean</code> "closure".
	 * If the closure's argument is <code>null</code>, the <code>boolean</code> "closure"
	 * will be passed an argument of <code>false</code>.
	 */
	public static Closure<Boolean> nullableBooleanClosure(BooleanClosure.Adapter adapter) {
		return nullableBooleanClosure(adapter, false);
	}

	/**
	 * Return a closure that adapts the specified <code>boolean</code> "closure".
	 * If the closure's argument is <code>null</code>, the <code>boolean</code> "closure"
	 * will be passed the specified null argument value.
	 */
	public static Closure<Boolean> nullableBooleanClosure(BooleanClosure.Adapter adapter, boolean nullArgumentValue) {
		return new NullableBooleanClosure(adapter, nullArgumentValue);
	}


	// ********** disabled **********

	/**
	 * Return a closure that will throw an
	 * {@link UnsupportedOperationException exception} when executed.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> disabledClosure() {
		return DisabledClosure.instance();
	}


	// ********** interrupted **********

	/**
	 * Return a closure that will throw an
	 * {@link InterruptedException exception} when executed.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> interruptedClosure() {
		return InterruptedClosure.instance();
	}


	// ********** looping **********

	/**
	 * Return a closure that executes the specified closure the specified
	 * number of times.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> repeatingClosure(Closure<? super A> closure, int count) {
		return (count == 0) ? nullClosure() : new RepeatingClosure<>(closure, count);
	}

	/**
	 * Return a closure that executes the specified closure the specified
	 * number of times.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> repeatingClosure(InterruptibleClosure<? super A> closure, int count) {
		return (count == 0) ? nullClosure() : new RepeatingInterruptibleClosure<>(closure, count);
	}

	/**
	 * Return a closure that executes the specified closure while the specified
	 * predicate evaluates to <code>true</code> when passed the argument.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> whileClosure(Predicate<? super A> predicate, Closure<? super A> closure) {
		return new WhileClosure<>(predicate, closure);
	}

	/**
	 * Return a closure that executes the specified closure while the specified
	 * predicate evaluates to <code>true</code> when passed the argument.
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> whileClosure(Predicate<? super A> predicate, InterruptibleClosure<? super A> closure) {
		return new WhileInterruptibleClosure<>(predicate, closure);
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
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> Closure<A> untilClosure(Closure<? super A> closure, Predicate<? super A> predicate) {
		return new UntilClosure<>(closure, predicate);
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
	 * 
	 * @param <A> the type of the object passed to the closure
	 */
	public static <A> InterruptibleClosure<A> untilClosure(InterruptibleClosure<? super A> closure, Predicate<? super A> predicate) {
		return new UntilInterruptibleClosure<>(closure, predicate);
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
