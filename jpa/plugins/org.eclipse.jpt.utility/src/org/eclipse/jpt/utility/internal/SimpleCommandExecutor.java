/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import org.eclipse.jpt.utility.Command;

/**
 * Straightforward implementation of {@link CallbackStatefulCommandExecutor}.
 */
public class SimpleCommandExecutor
	implements CallbackStatefulCommandExecutor
{
	private boolean active = false;
	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);

	public SimpleCommandExecutor() {
		super();
	}

	public void start() {
		if (this.active) {
			throw new IllegalStateException("Not stopped."); //$NON-NLS-1$
		}
		this.active = true;
	}

	public void execute(Command command) {
		if (this.active) {
			command.execute();
			this.commandExecuted(command);
		}
	}

	public void stop() {
		if ( ! this.active) {
			throw new IllegalStateException("Not started."); //$NON-NLS-1$
		}
		this.active = false;
	}

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}

	/**
	 * Notify our listeners.
	 */
	private void commandExecuted(Command command) {
		for (Listener listener : this.listenerList.getListeners()) {
			listener.commandExecuted(command);
		}
	}

}
