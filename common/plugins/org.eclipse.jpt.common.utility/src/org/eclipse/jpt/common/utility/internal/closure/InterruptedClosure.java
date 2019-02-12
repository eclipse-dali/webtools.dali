/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Closure that will throw an {@link InterruptedException exception}
 * when executed.
 * 
 * @param <A> the type of the object passed to the closure
 */
public final class InterruptedClosure<A>
	implements InterruptibleClosure<A>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final InterruptibleClosure INSTANCE = new InterruptedClosure();

	@SuppressWarnings("unchecked")
	public static <A> InterruptibleClosure<A> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private InterruptedClosure() {
		super();
	}

	// throw an exception
	public void execute(A argument) throws InterruptedException {
		throw new InterruptedException();
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
