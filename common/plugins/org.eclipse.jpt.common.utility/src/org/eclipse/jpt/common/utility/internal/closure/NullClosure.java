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
 * Closure that will do nothing when executed.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see AbstractClosure
 * @see ClosureAdapter
 * @see NullCheckClosureWrapper
 */
public final class NullClosure<A>
	implements Closure<A>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Closure INSTANCE = new NullClosure();

	@SuppressWarnings("unchecked")
	public static <A> Closure<A> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullClosure() {
		super();
	}

	public void execute(A argument) {
		// do nothing
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
