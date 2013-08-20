/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Closure that will throw an
 * {@link UnsupportedOperationException exception} when executed.
 * 
 * @param <A> the type of the object passed to the closure
 */
public final class DisabledClosure<A>
	implements Closure<A>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Closure INSTANCE = new DisabledClosure();

	@SuppressWarnings("unchecked")
	public static <A> Closure<A> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DisabledClosure() {
		super();
	}

	// throw an exception
	public void execute(A argument) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
