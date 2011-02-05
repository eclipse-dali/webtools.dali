/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.synchronizers;

import org.eclipse.jpt.common.utility.Command;
import org.eclipse.jpt.common.utility.internal.ConsumerThreadCoordinator;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.synchronizers.CallbackSynchronizer;

/**
 * Extend the asynchronous synchronizer to notify listeners
 * when a synchronization "cycle" is complete; i.e. the synchronization has,
 * for the moment, handled every outstanding "synchronize" request and quiesced.
 * This notification is <em>not</em> guaranteed to occur with <em>every</em>
 * synchronization "cycle"; since other, unrelated, synchronizations can be
 * triggered concurrently.
 * <p>
 * <strong>NB:</strong> Listeners should handle any exceptions
 * appropriately (e.g. log the exception and return gracefully so the thread
 * can continue the synchronization process).
 */
public class CallbackAsynchronousSynchronizer
	extends AsynchronousSynchronizer
	implements CallbackSynchronizer
{
	private final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);


	// ********** construction **********

	/**
	 * Construct a callback asynchronous synchronizer that uses the specified
	 * command to perform the synchronization. Allow the synchronization thread(s)
	 * to be assigned JDK-generated names.
	 */
	public CallbackAsynchronousSynchronizer(Command command) {
		super(command);
	}

	/**
	 * Construct a callback asynchronous synchronizer that uses the specified
	 * command to perform the synchronization. Assign the synchronization thread(s)
	 * the specified name.
	 */
	public CallbackAsynchronousSynchronizer(Command command, String threadName) {
		super(command, threadName);
	}

	/**
	 * Build a consumer that will let us know when the synchronization has
	 * quiesced.
	 */
	@Override
	ConsumerThreadCoordinator.Consumer buildConsumer(Command command) {
		return new CallbackConsumer(command);
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
	void synchronizationQuiesced() {
		for (Listener listener : this.listenerList.getListeners()) {
			listener.synchronizationQuiesced(this);
		}
	}


	// ********** synchronization thread runnable **********

	/**
	 * Extend {@link AsynchronousSynchronizer.Consumer}
	 * to notify the synchronizer when the synchronization has quiesced
	 * (i.e. the command has finished executing and there are no further
	 * requests for synchronization).
	 * Because synchronization is asynchronous, no other thread will be able to
	 * initiate another synchronization until the synchronizer's listeners have been
	 * notified. Note also, the synchronizer's listeners can, themselves,
	 * trigger another synchronization (by directly or indirectly calling
	 * {@link org.eclipse.jpt.common.utility.synchronizers.Synchronizer#synchronize()});
	 * but this synchronization will not occur until <em>after</em> all the
	 * listeners have been notified.
	 */
	class CallbackConsumer
		extends Consumer
	{
		CallbackConsumer(Command command) {
			super(command);
		}

		@Override
		public void execute() {
			super.execute();
			// hmmm - we will notify listeners even when we our thread is "interrupted";
			// that seems ok...  ~bjv
			if (CallbackAsynchronousSynchronizer.this.synchronizeFlag.isFalse()) {
				CallbackAsynchronousSynchronizer.this.synchronizationQuiesced();
			}
		}

	}

}
