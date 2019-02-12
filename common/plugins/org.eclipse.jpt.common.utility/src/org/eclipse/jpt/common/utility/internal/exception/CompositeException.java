/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * Provide a way for multiple exceptions to be packaged and reported.
 */
public class CompositeException
	extends RuntimeException
{
	private final Throwable[] exceptions;
	private static final long serialVersionUID = 1L;


	/**
	 * Gather the specified exceptions into a single exception.
	 */
	public CompositeException(Collection<Throwable> exceptions) {
		this(exceptions.toArray(new Throwable[exceptions.size()]));
	}

	/**
	 * Gather the specified exceptions into a single exception.
	 */
	public CompositeException(Throwable... exceptions) {
		// provide a list of the nested exceptions and
		// grab the first exception and make it the "cause"
		super(buildMessage(exceptions));
		this.exceptions = exceptions;
	}

	public Iterable<Throwable> getExceptions() {
		return IterableTools.iterable(this.exceptions);
	}

	private static String buildMessage(Throwable[] exceptions) {
		StringBuilder sb = new StringBuilder();
		sb.append(exceptions.length);
		sb.append(" exceptions"); //$NON-NLS-1$
		if (exceptions.length > 0) {
			sb.append(": ["); //$NON-NLS-1$
			for (Throwable ex : exceptions) {
				sb.append(ex.getClass().getSimpleName());
				sb.append(", "); //$NON-NLS-1$
			}
			sb.setLength(sb.length() - 2);  // chop off trailing comma
			sb.append(']');
		}
		return sb.toString();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			s.println(this);
			for (StackTraceElement element : this.getStackTrace()) {
				s.print("\tat "); //$NON-NLS-1$
				s.println(element);
			}
			int i = 1;
			for (Throwable ex : this.exceptions) {
				s.print("Nested exception "); //$NON-NLS-1$
				s.print(i++);
				s.print(": "); //$NON-NLS-1$
				ex.printStackTrace(s);
			}
		}
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		synchronized (s) {
			s.println(this);
			for (StackTraceElement element : this.getStackTrace()) {
				s.print("\tat "); //$NON-NLS-1$
				s.println(element);
			}
			int i = 1;
			for (Throwable ex : this.exceptions) {
				s.print("Nested exception "); //$NON-NLS-1$
				s.print(i++);
				s.print(": "); //$NON-NLS-1$
				ex.printStackTrace(s);
			}
		}
	}
}
