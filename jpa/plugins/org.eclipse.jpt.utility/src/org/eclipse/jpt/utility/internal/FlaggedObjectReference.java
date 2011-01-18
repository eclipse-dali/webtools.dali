/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Provide a container for passing an object that can be changed by the
 * recipient. If the value is set at any time after construction of the
 * reference, the reference is marked "set". This allows the client to
 * detect whether the server/recipient ever set the value, even if it remains
 * unchanged. This is particularly useful when the value can be set to
 * <code>null</code>.
 * <p>
 * The reference can be set multiple times, but it can
 * never be "unset" once it is "set".
 */
public class FlaggedObjectReference<V>
	extends SimpleObjectReference<V>
{
	private volatile boolean set = false;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create an object reference with the specified initial value.
	 */
	public FlaggedObjectReference(V value) {
		super(value);
	}

	/**
	 * Create an object reference with an initial value of
	 * <code>null</code>.
	 */
	public FlaggedObjectReference() {
		super();
	}


	// ********** set **********

	public boolean isSet() {
		return this.set;
	}


	// ********** overrides **********

	@Override
	public V setValue(V value) {
		this.set = true;
		return super.setValue(value);
	}

	@Override
	public String toString() {
		String s = super.toString();
		return (this.set) ? '*' + s : s;
	}
}
