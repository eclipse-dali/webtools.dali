/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.node;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
import org.eclipse.jpt.common.utility.node.Node;

/**
 * This implementation of the {@link org.eclipse.jpt.common.utility.node.Node.Validator}
 * interface implements the
 * pause/resume portion of the protocol, but delegates the actual
 * validation to a "pluggable" delegate.
 */
public class PluggableValidator
	implements Node.Validator
{
	private boolean pause;
	private boolean validateOnResume;
	private final Delegate delegate;


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
		this.validateOnResume = false;
		this.delegate = delegate;
	}

	public synchronized void validate() {
		if (this.pause) {
			this.validateOnResume = true;
		} else {
			this.delegate.validate();
		}
	}

	public synchronized void pause() {
		if (this.pause) {
			throw new IllegalStateException("already paused"); //$NON-NLS-1$
		}
		this.pause = true;
	}

	public synchronized void resume() {
		if ( ! this.pause) {
			throw new IllegalStateException("not paused"); //$NON-NLS-1$
		}
		this.pause = false;
		// validate any changes that occurred while the validation was paused
		if (this.validateOnResume) {
			this.validateOnResume = false;
			this.delegate.validate();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.delegate);
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
		final class Null
			implements Delegate, Serializable
		{
			public static final Delegate INSTANCE = new Null();
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
				return ObjectTools.singletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}
	}
}
