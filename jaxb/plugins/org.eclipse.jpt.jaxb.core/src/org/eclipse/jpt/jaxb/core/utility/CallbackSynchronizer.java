/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.utility;

import java.io.Serializable;
import java.util.EventListener;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

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


	/**
	 * Singleton implementation of the {@link CallbackSynchronizer} interface that will do
	 * nothing.
	 */
	final class Null
		implements CallbackSynchronizer, Serializable
	{
		public static final CallbackSynchronizer INSTANCE = new Null();
		public static CallbackSynchronizer instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void start() {
			// do nothing
		}
		public void synchronize() {
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
}
