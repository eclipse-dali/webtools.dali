/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.node;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.SynchronizedBoolean;

/**
 * This implementation of the Validator interface implements the
 * pause/resume portion of the protocol, but delegates the actual
 * validation to a "pluggable" delegate.
 */
public class PluggableValidator implements Node.Validator {
	private boolean pause;
	private Delegate delegate;


	/**
	 * Convenience factory method.
	 */
	public static Node.Validator buildAsynchronousValidator(SynchronizedBoolean validateFlag) {
		return new PluggableValidator(new AsynchronousValidator(validateFlag));
	}

	/**
	 * Convenience factory method.
	 */
	public static Node.Validator buildSynchronousValidator(Node node) {
		return new PluggableValidator(new SynchronousValidator(node));
	}

	/**
	 * Construct a validator with the specified delegate.
	 */
	public PluggableValidator(Delegate delegate) {
		super();
		this.pause = false;
		this.delegate = delegate;
	}

	public synchronized void validate() {
		if ( ! this.pause) {
			this.delegate.validate();
		}
	}

	public synchronized void pause() {
		if (this.pause) {
			throw new IllegalStateException("already paused");
		}
		this.pause = true;
	}

	public synchronized void resume() {
		if ( ! this.pause) {
			throw new IllegalStateException("not paused");
		}
		this.pause = false;
		// validate all the changes that occurred while the validation was paused
		this.delegate.validate();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.delegate);
	}


	// ********** member interface **********

	/**
	 * Interface implemented by any delegates of a pluggable validator.
	 */
	public interface Delegate {

		/**
		 * The validator is not "paused" - perform the appropriate validation.
		 */
		void validate();


		/**
		 * This delegate does nothing.
		 */
		final class Null implements Delegate {
			@SuppressWarnings("unchecked")
			public static final Delegate INSTANCE = new Null();
			@SuppressWarnings("unchecked")
			public static Delegate instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public void validate() {
				// do nothing
			}
			@Override
			public String toString() {
				return "Delegate.Null";
			}
		}

	}

}
