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

import java.util.EventListener;

/**
 * Extend {@link Synchronizer} to notify listeners
 * when a synchronization "cycle" is complete; i.e. the synchronization has,
 * for the moment, quiesced.
 */
public interface CallbackSynchronizer
	extends Synchronizer
{
	/**
	 * Add the specified listener to be notified whenever the synchronizer has
	 * quiesced.
	 * @see #removeListener(Listener)
	 */
	void addListener(Listener listener);

	/**
	 * Remove the specified listener.
	 * @see #addListener(Listener)
	 */
	void removeListener(Listener listener);


	// ********** listener **********

	/**
	 * Interface implemented by listeners to be notified whenever the
	 * synchronizer has quiesced.
	 */
	public interface Listener
		extends EventListener
	{
		/**
		 * The specified synchronizer has quiesced.
		 */
		void synchronizationQuiesced(CallbackSynchronizer synchronizer);
	}

}
