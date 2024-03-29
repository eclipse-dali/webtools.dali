/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.utility;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.model.ModelTools;
import org.eclipse.jpt.jaxb.core.utility.CallbackSynchronizer;

/**
 * Extend the synchronous synchronizer to notify listeners
 * when a synchronization "cycle" is complete; i.e. the synchronization has,
 * for the moment, handled every "synchronize" request and quiesced.
 * This notification is <em>not</em> guaranteed to occur with <em>every</em>
 * synchronization "cycle";
 * since other, unrelated, synchronizations can be triggered concurrently.
 * <p>
 * <strong>NB:</strong> If another synchronization is initiated while we are
 * notifying the synchronizer's listeners (i.e. the 'again' flag is set), it will not
 * start until all the listeners are notified.
 * Note also, the synchronizer's listeners can, themselves,
 * trigger another synchronization (by directly or indirectly calling
 * {@link org.eclipse.jpt.jaxb.core.utility.Synchronizer#synchronize()});
 * but this synchronization will not occur until <em>after</em> all the
 * listeners have been notified.
 */
public class CallbackSynchronousSynchronizer
	extends SynchronousSynchronizer
	implements CallbackSynchronizer
{
	private final ListenerList<Listener> listenerList = ModelTools.listenerList();


	// ********** construction **********

	/**
	 * Construct a callback synchronous synchronizer that uses the specified
	 * command to perform the synchronization.
	 */
	public CallbackSynchronousSynchronizer(Command command) {
		super(command);
	}


	// ********** CallbackSynchronizer implementation **********

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}

	/**
	 * Notify our listeners.
	 */
	private void synchronizationQuiesced() {
		for (Listener listener : this.listenerList) {
			listener.synchronizationQuiesced(this);
		}
	}


	// ********** override **********

	@Override
	void execute_() {
		super.execute_();
		if (this.state.getValue() != State.REPEAT) {
			// hmmm - we will notify listeners even when we are "stopped";
			// that seems ok...  ~bjv
			this.synchronizationQuiesced();
		}
	}
}
