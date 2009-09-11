/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.synchronizers;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.SynchronizedBoolean;

/**
 * Extend the asynchronous synchronizer to notify listeners
 * when a synchronization "cycle" is complete; i.e. the synchronization has,
 * for the moment, handled every "synchronize" request and quiesced.
 * This notification is <em>not</em> guaranteed to occur with <em>every</em>
 * synchronization "cycle";
 * since other, unrelated, synchronizations can be triggered concurrently.
 * <p>
 * <strong>NB:</strong> Listeners should handle any exceptions
 * appropriately (e.g. log the exception and return gracefully so the thread
 * can continue the synchronization process).
 */
public class CallbackAsynchronousSynchronizer
	extends AsynchronousSynchronizer
	implements CallbackSynchronizer
{
	protected final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);


	// ********** construction **********

	/**
	 * Construct a callback asynchronous synchronizer that uses the specified
	 * command to perform the synchronization. Allow the generated thread(s)
	 * to be assigned JDK-generated names.
	 */
	public CallbackAsynchronousSynchronizer(Command command) {
		super(command);
	}

	/**
	 * Construct a callback asynchronous synchronizer that uses the specified
	 * command to perform the synchronization. Assign the generated thread(s)
	 * the specified name.
	 */
	public CallbackAsynchronousSynchronizer(Command command, String threadName) {
		super(command, threadName);
	}

	/**
	 * Build a runnable that will let us know when the synchronization has
	 * quiesced.
	 */
	@Override
	protected Runnable buildRunnable(Command command) {
		return new RunnableCallbackSynchronization(command, this.synchronizeFlag);
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
	protected void synchronizationQuiesced() {
		for (Listener listener : this.listenerList.getListeners()) {
			listener.synchronizationQuiesced(this);
		}
	}


	// ********** synchronization thread runnable **********

	/**
	 * Extend {@link AsynchronousSynchronizer.RunnableSynchronization}
	 * to notify the synchronizer when the synchronization has quiesced
	 * (i.e. the command has finished executing and there are no further
	 * requests for synchronization).
	 * Because synchronization is asynchronous, no other thread will be able to
	 * initiate another synchronization until the synchronizer's listeners have been
	 * notified. Note also, the synchronizer's listeners can, themselves,
	 * trigger another synchronization (by directly or indirectly calling
	 * {@link org.eclipse.jpt.utility.internal.synchronizers.Synchronizer#synchronize());
	 * but this synchronization will not occur until <em>after</em> all the
	 * listeners have been notified.
	 */
	protected class RunnableCallbackSynchronization
		extends RunnableSynchronization
	{
		protected RunnableCallbackSynchronization(Command command, SynchronizedBoolean synchronizeFlag) {
			super(command, synchronizeFlag);
		}

		@Override
		protected void execute() {
			super.execute();
			// hmmm - we will notify listeners even when we our thread is "interrupted";
			// that seems ok...  ~bjv
			if (this.synchronizeFlag.isFalse()) {
				CallbackAsynchronousSynchronizer.this.synchronizationQuiesced();
			}
		}

	}

}
