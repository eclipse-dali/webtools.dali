/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.synchronizers;

import java.io.Serializable;

/**
 * This interface defines the protocol for starting, stopping, and executing a
 * long-running, repeatable, and possibly recursive "synchronization" process.
 * The intent is for the synchronizer to synchronize a "secondary" model with
 * a "primary" model. Any change to the "primary" model will trigger the
 * synchronization. The synchronizer implementation will determine whether the
 * "secondary" model remains in sync synchronously or asynchronously.
 * <p>
 * The assumption is that the {@link #start()} and {@link #stop()} methods will be called from
 * a single master thread that would control the synchronizer's lifecycle and
 * the {@link #synchronize()} method will be called multiple times, possibly from
 * multiple threads.
 */
public interface Synchronizer {

	/**
	 * Enable the synchronizer to allow future synchronizations as requested
	 * by calls to {@link #synchronize()}.
	 */
	void start();

	/**
	 * Synchronize the dependent model with the primary model. Do nothing if
	 * {@link #start()} has not previously been called. Do nothing if {@link #stop}
	 * has been called (without any intermediate call to {@link #start()}.
	 */
	void synchronize();

	/**
	 * Stop the synchronizer immediately or, if a synchronization is currently
	 * in progress, when it completes. Return when the synchronizer is stopped.
	 * No further synchonizations will performed until {@link #start()} is called.
	 */
	void stop();


	/**
	 * Singleton implementation of the {@link Synchronizer} interface that will do
	 * nothing.
	 */
	final class Null implements Synchronizer, Serializable {
		public static final Synchronizer INSTANCE = new Null();
		public static Synchronizer instance() {
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
		@Override
		public String toString() {
			return "Synchronizer.Null"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
