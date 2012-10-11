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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;

/**
 * This implementation of the {@link PluggableValidator.Delegate} interface
 * simply sets a shared "validate" flag to <code>true</code>. This should trigger a
 * separate "validation" thread to begin validating the appropriate
 * branch of nodes.
 */
public class AsynchronousValidator
	implements PluggableValidator.Delegate
{
	private SynchronizedBoolean validateFlag;

	/**
	 * Construct a validator delegate with the specified shared
	 * "validate" flag. This flag should be shared with
	 * another thread that will perform the actual validation.
	 */
	public AsynchronousValidator(SynchronizedBoolean validateFlag) {
		super();
		this.validateFlag = validateFlag;
	}

	/**
	 * Set the shared "validate" flag to true, triggering
	 * an asynchronous validation of the appropriate
	 * branch of nodes.
	 */
	public void validate() {
		this.validateFlag.setTrue();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.validateFlag);
	}

}
