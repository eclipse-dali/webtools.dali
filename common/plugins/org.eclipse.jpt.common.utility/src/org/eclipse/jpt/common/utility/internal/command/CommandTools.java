/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.lang.reflect.Method;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Command} utility methods.
 */
public final class CommandTools {

	// ********** adapters **********

	/**
	 * Adapt a {@link Runnable} to the {@link Command} interface.
	 */
	public static Command adapt(Runnable runnable) {
		return new RunnableCommand(runnable);
	}

	/**
	 * Adapt a {@link Command} to the {@link Runnable} interface.
	 */
	public static Runnable runnable(Command command) {
		return new CommandRunnable(command);
	}

	/**
	 * Adapt a {@link Closure} to the {@link Command} interface.
	 * The closure is passed <code>null</code> for input.
	 * This really only useful for a transformer that accepts
	 * <code>null</code> input.
	 */
	public static Command adapt(Closure<?> closure) {
		return new ClosureCommand(closure);
	}

	/**
	 * Adapt a {@link Factory} to the {@link Command} interface.
	 * The factory's output is ignored. This really only useful
	 * for a factory that has side-effects.
	 */
	public static Command adapt(Factory<?> factory) {
		return new FactoryCommand(factory);
	}

	/**
	 * Adapt a {@link Transformer} to the {@link Command} interface.
	 * The transformer is passed <code>null</code> for input and its output is
	 * ignored. This really only useful for a transformer that accepts
	 * <code>null</code> input and has side-effects.
	 */
	public static Command adapt(Transformer<?, ?> transformer) {
		return new TransformerCommand(transformer);
	}


	// ********** composite **********

	/**
	 * @see #composite(Iterable)
	 */
	public static Command composite(Command... commands) {
		return composite(ArrayTools.iterable(commands));
	}

	/**
	 * Return a composite of the specified commands. The commands will be
	 * executed in sequence.
	 */
	public static  Command composite(Iterable<Command> commands) {
		return new CompositeCommand(commands);
	}


	// ********** thread local **********

	/**
	 * Return a command that allows the client to specify a different command
	 * for each thread. If there is no command for the current thread, the
	 * command will do nothing.
	 * @see ThreadLocalCommand
	 */
	public static ThreadLocalCommand threadLocalCommand() {
		return threadLocalCommand(NullCommand.instance());
	}

	/**
	 * Return a command that allows the client to specify a different command
	 * for each thread. If there is no command for the current thread, the
	 * specified default command is executed.
	 * @see ThreadLocalCommand
	 */
	public static ThreadLocalCommand threadLocalCommand(Command defaultCommand) {
		return new ThreadLocalCommand(defaultCommand);
	}


	// ********** wrappers **********

	/**
	 * Return a command that can have its wrapped command changed,
	 * allowing a client to change a previously-supplied command's
	 * behavior mid-stream.
	 * @see CommandWrapper
	 */
	public static CommandWrapper wrap(Command command) {
		return new CommandWrapper(command);
	}


	// ********** safe **********

	/**
	 * Return command that will handle any exceptions thrown by the specified
	 * command. If the wrapped command throws an exception,
	 * the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}.
	 * @see #safe(Command, ExceptionHandler)
	 */
	public static Command safe(Command command) {
		return safe(command, DefaultExceptionHandler.instance());
	}

	/**
	 * Return command that will handle any exceptions thrown by the specified
	 * command with the specified exception handler.
	 * @see #safe(Command)
	 */
	public static Command safe(Command command, ExceptionHandler exceptionHandler) {
		return new SafeCommandWrapper(command, exceptionHandler);
	}


	// ********** looping **********

	/**
	 * Return a command that executes the specified command the specified
	 * number of times.
	 */
	public static Command repeat(Command command, int count) {
		return new RepeatingCommand(command, count);
	}


	// ********** disabled **********

	/**
	 * Return a command that will throw an
	 * {@link UnsupportedOperationException exception} when executed.
	 */
	public static Command disabledCommand() {
		return DisabledCommand.instance();
	}


	// ********** null **********

	/**
	 * Return a command that does nothing.
	 */
	public static Command nullCommand() {
		return NullCommand.instance();
	}


	// ********** reflection **********

	/**
	 * Return a command that uses Java reflection to execute
	 * the specified zero-argument static method.
	 * @see StaticMethodCommand
	 */
	public static Command execute(Class<?> clazz, String methodName) {
		return execute(clazz, methodName, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a command that uses Java reflection to execute
	 * the specified single-argument static method.
	 * @see StaticMethodCommand
	 */
	public static Command execute(Class<?> clazz, String methodName, Class<?> parameterType, Object argument) {
		return execute(clazz, methodName, new Class<?>[] { parameterType }, new Object[] { argument });
	}

	/**
	 * Return a command that uses Java reflection to execute
	 * the specified static method.
	 * @see StaticMethodCommand
	 */
	public static Command execute(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return execute(ClassTools.method(clazz, methodName, parameterTypes), arguments);
	}

	/**
	 * Return a command that uses Java reflection to execute
	 * the specified static method.
	 * @see StaticMethodCommand
	 */
	public static Command execute(Method method, Object[] arguments) {
		return new StaticMethodCommand(method, arguments);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private CommandTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
