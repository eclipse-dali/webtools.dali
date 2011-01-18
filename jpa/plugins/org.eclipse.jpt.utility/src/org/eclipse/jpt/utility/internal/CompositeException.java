/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Provide a way for multiple exceptions to be packaged and reported.
 */
public class CompositeException
	extends RuntimeException
{
	private final Throwable[] exceptions;
	private static final long serialVersionUID = 1L;


	/**
	 * The specified exceptions list must not be empty.
	 */
	public CompositeException(Collection<Throwable> exceptions) {
		this(exceptions.toArray(new Throwable[exceptions.size()]));
	}

	/**
	 * The specified exceptions list must not be empty.
	 */
	public CompositeException(Throwable[] exceptions) {
		// provide a list of the nested exceptions and
		// grab the first exception and make it the "cause"
		super(buildMessage(exceptions));
		this.exceptions = exceptions;
	}

	public Throwable[] getExceptions() {
		return this.exceptions;
	}

	private static String buildMessage(Throwable[] exceptions) {
		StringBuilder sb = new StringBuilder();
		sb.append(exceptions.length);
		sb.append(" exceptions: "); //$NON-NLS-1$
		sb.append('[');
		for (Throwable ex : exceptions) {
			sb.append(ex.getClass().getSimpleName());
			sb.append(", "); //$NON-NLS-1$
		}
		sb.setLength(sb.length() - 2);  // chop off trailing comma
		sb.append(']');
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
