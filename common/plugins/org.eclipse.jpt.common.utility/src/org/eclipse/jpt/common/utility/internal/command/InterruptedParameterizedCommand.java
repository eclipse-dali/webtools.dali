/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.command.InterruptibleParameterizedCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the interruptible parameterized command
 * interface that will throw an {@link InterruptedException exception}
 * when executed.
 */
public final class InterruptedParameterizedCommand<A>
	implements InterruptibleParameterizedCommand<A>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final InterruptibleParameterizedCommand INSTANCE = new InterruptedParameterizedCommand();

	@SuppressWarnings("unchecked")
	public static <A> InterruptibleParameterizedCommand<A> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private InterruptedParameterizedCommand() {
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
