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
import org.eclipse.jpt.common.utility.command.NotifyingRepeatingCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the notifying repeating command interface
 * that will do nothing when executed.
 */
public final class NullNotifyingRepeatingCommand
	implements NotifyingRepeatingCommand, Serializable
{
	public static final NotifyingRepeatingCommand INSTANCE = new NullNotifyingRepeatingCommand();

	public static NotifyingRepeatingCommand instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullNotifyingRepeatingCommand() {
		super();
	}

	public void start() {
		// do nothing
	}

	public void execute() {
		// do nothing
	}

	public void stop() {
		// do nothing
	}

	public void addListener(Listener listener) {
		// do nothing
	}

	public void removeListener(Listener listener) {
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
