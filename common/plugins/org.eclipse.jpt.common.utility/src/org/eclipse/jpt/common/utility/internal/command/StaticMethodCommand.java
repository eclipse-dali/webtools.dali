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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A command that uses Java reflection to execute a static method.
 * Checked exceptions are converted to {@link RuntimeException}s.
 * 
 * @see java.lang.reflect.Method#invoke(Object, Object...)
 */
public class StaticMethodCommand
	implements Command
{
	private final Method method;
	private final Object[] arguments;


	public StaticMethodCommand(Method method, Object[] arguments) {
		super();
		if ((method == null) || (arguments == null)) {
			throw new NullPointerException();
		}
		if ( ! Modifier.isStatic(method.getModifiers())) {
			throw new IllegalArgumentException("method must be static: " + method); //$NON-NLS-1$
		}
		if ( ! method.isAccessible()) {
			throw new IllegalArgumentException("method must be accessible: " + method); //$NON-NLS-1$
		}
		this.method = method;
		this.arguments = arguments;
	}

	public void execute() {
		try {
			this.method.invoke(null, this.arguments);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.method);
	}
}
